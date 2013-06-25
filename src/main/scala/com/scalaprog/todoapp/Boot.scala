package com.scalaprog.todoapp

import akka.actor.{ActorRef, Props, ActorSystem, actorRef2Scala}
import akka.io.IO
import spray.can.Http
import spray.routing.SimpleRoutingApp
import spray.json.DefaultJsonProtocol._
import com.google.gson.Gson

/**
 * User: soren
 */
object Boot extends App
/*{
  implicit val system = ActorSystem()

  val myListener: ActorRef = system.actorOf(Props[TodoServiceActor], name = "handler")

  IO(Http) ! Http.Bind(myListener, interface = "localhost", port = 8080)
  */
  with SimpleRoutingApp {

    implicit val system = ActorSystem("my-system")

    startServer(interface = "localhost", port = 8080) {
      path("hello") {
        get {
          complete {
            implicit val NotRegisteredUserFormat = jsonFormat2(Todo)
            val gson = new Gson()
            gson.toJson(Todo("name", "date"))
          }
        }
      }
    }
}

case class Todo(name: String, date: String)
