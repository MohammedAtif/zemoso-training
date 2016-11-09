name := """keep"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.21",
  "org.json" % "json" % "20131018",
  "com.google.code.gson" % "gson" % "1.7.2",
  "org.mindrot" % "jbcrypt" % "0.3m"
)


