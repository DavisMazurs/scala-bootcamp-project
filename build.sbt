scalaVersion := "2.13.1"

name := "scala-bootcamp-project"
organization := "ch.epfl.scala"
version := "1.0"

val akkaVersion = "2.6.5"
val akkaHttpVersion = "10.2.0"
val akkaHttpJsonSerializersVersion = "1.34.0"
val circeVersion = "0.12.3"
val scalatestVersion = "3.2.0"
val doobieVersion = "0.9.4"

libraryDependencies ++= Seq(
  // Testing
  "org.scalatest" %% "scalatest" % scalatestVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion % "test",

  // Akka
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,

  // Circe
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,

  // Akka http
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "de.heikoseeberger" %% "akka-http-circe" % akkaHttpJsonSerializersVersion,

  // Logs
  "org.slf4j" % "slf4j-api" % "1.7.1",
  "org.slf4j" % "log4j-over-slf4j" % "1.7.1",  // for any java classes looking for this
  "ch.qos.logback" % "logback-classic" % "1.0.3",

  // DB
  "org.tpolecat"          %% "doobie-core"            % doobieVersion,
  "org.tpolecat"          %% "doobie-h2"              % doobieVersion,
  "org.tpolecat"          %% "doobie-hikari"          % doobieVersion,
  "org.tpolecat"          %% "doobie-specs2"          % doobieVersion,
  "org.tpolecat"          %% "doobie-postgres"        % doobieVersion,
  "org.tpolecat"          %% "doobie-scalatest"       % doobieVersion       % "test",
  "io.monix"              %% "monix"                  % "3.2.2",
  
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
)