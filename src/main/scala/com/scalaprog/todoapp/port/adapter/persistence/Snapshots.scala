package com.scalaprog.todoapp.port.adapter.persistence

import com.mongodb.{DBObject, BasicDBObject, DBCollection}
import java.util.UUID
import com.google.gson.GsonBuilder
import com.scalaprog.todoapp.domain.common.AggregateRoot

/**
 * User: soren
 */
trait Snapshots {

  def getCollection: DBCollection


  def saveSnapshot(id: UUID, obj: AggregateRoot) {
    val dataObject = new BasicDBObject()
    dataObject.put("aggregateId", id.toString)
    dataObject.put("version", obj.version)
    dataObject.put("data", jsonTool.toJson(obj))
    getCollection.insert(dataObject)
  }

  def getSnapshot(aggregateId: UUID): Option[(AggregateRoot, Int)] = {
    val searchObject = new BasicDBObject()
    searchObject.put("aggregateId", aggregateId.toString)
    val cursor = getCollection.find(searchObject)
    try {
      if (cursor.hasNext) {
        val mongoData: DBObject = cursor.next()
        Some(mongoData.get("data").asInstanceOf[AggregateRoot], mongoData.get("version").asInstanceOf[Int])
      } else {
        None
      }
    } finally {
      cursor.close();
    }

  }

  val jsonTool = new GsonBuilder()
    .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    .create()
}
