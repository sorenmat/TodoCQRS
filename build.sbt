name := "TodoCQRS"

version := "1.0"

scalaVersion := "2.10.2"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"

libraryDependencies += "org.mongodb" % "mongo-java-driver" % "2.11.2"

libraryDependencies += "com.google.code.gson" % "gson" % "2.2.4"

libraryDependencies += "io.spray" % "spray-can" % "1.1-M8"

libraryDependencies += "io.spray" % "spray-io" % "1.1-M8"

libraryDependencies += "io.spray" %% "spray-json" % "1.2.5"

libraryDependencies += "io.spray" %  "spray-routing" % "1.1-M8"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.1.4"

//libraryDependencies += "com.typesafe.akka" %% "akka-io" % "2.1.4"
