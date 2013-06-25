package com.scalaprog.todoapp.port.adapter.persistence

import com.mongodb._
import com.scalaprog.todoapp.domain.common.AbstractEvent
import java.util.UUID
import java.util.Date
import com.scalaprog.todoapp.port.adapter.eventbus.EventBus

class EventStoreProvider extends Snapshots {
  val MONGOHQ_URL: String = "MONGOHQ_URL"

  val db = {
    val url = System.getenv(MONGOHQ_URL)
    val client = if (url == null || url.isEmpty) {
      new MongoClient("localhost")
    } else {
      val mongoClient = new MongoClient(System.getenv(MONGOHQ_URL));
      mongoClient
    }
    client.getDB("eventStore");

  }
  val eventCollection = db.getCollection("events")

  def getCollection = {
    db.getCollection("snapshots")
  }


  /*
  val jsonTool = new GsonBuilder()
    .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    .create()
    */

  def append(eventId : EventId, event: AbstractEvent) {
    val currentVersion = eventId.version + 1

    val doc = new BasicDBObject()
    doc.put("id", eventId.id.toString)
    doc.put("version", currentVersion)
    doc.put("eventClass", event.getClass().getName())
    doc.put("event", jsonTool.toJson(event))
    doc.put("date", new Date())
    eventCollection.insert(doc)
    EventBus.publishEvent(event) // publish the event to the eventbus, should not be here..
  }

  def append(eventId : EventId, events: List[AbstractEvent]) {
    var currentVersion = eventId.version + 1
    events.foreach(event => {
      append(EventId(eventId.id, currentVersion), event)
      currentVersion = currentVersion + 1
    })

    //ProjectionEngine.publishEvent(event)
  }

  def getEvents(aggregateId: UUID): List[AbstractEvent] = {
    var events = List[AbstractEvent]()
    try {
      val coll = db.getCollection("events")

      val query = new BasicDBObject()
      query.put("id", aggregateId.toString)

      val cursor = coll.find(query)

      try {
        while (cursor.hasNext()) {
          val next = cursor.next()
          val eventClassStr = next.get("eventClass").toString()
          val event = next.get("event").toString()
          val eventObj = jsonTool.fromJson(event, Class.forName(eventClassStr)).asInstanceOf[AbstractEvent]
          events = eventObj :: events
        }
      } finally {
        cursor.close()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    return events
  }


  def getEventLog: List[AbstractEvent] = {
    var events = List[AbstractEvent]()
    try {
      val coll = db.getCollection("events")

      val query = new BasicDBObject()

      val cursor = coll.find(query)

      try {
        while (cursor.hasNext()) {
          val next = cursor.next()
          val eventClassStr = next.get("eventClass").toString()
          val event = next.get("event").toString()

          val eventObj = jsonTool.fromJson(event, Class.forName(eventClassStr)).asInstanceOf[AbstractEvent]
          events = eventObj :: events
        }
      } finally {
        cursor.close()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    return events.reverse
  }

  def clear {
    // doesn't make sense in Mongo
  }

}
case class EventId(id: UUID, version: Int)
