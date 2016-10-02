name := """cota-test"""

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies += "junit" % "junit" % "4.12"
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.8"


//resourceDirectory in Compile := baseDirectory.value / "resources"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

