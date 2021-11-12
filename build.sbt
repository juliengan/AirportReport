name := "PJ_SCALA_AIRPORT"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies += "org.scalatest" % "scalatest" % "3.2.9" % "test"
//started testing the gui swing dependency
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"

onChangedBuildSource := ReloadOnSourceChanges
