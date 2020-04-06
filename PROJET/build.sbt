name := "NEWscalaProject"

version := "0.1"

scalaVersion := "2.11.8"

val sparkVersion = "2.3.0"


dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.7"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.kafka" % "kafka_2.11" % "1.1.1",
  "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.2.0",
  "com.typesafe.play" %% "play-json" % "2.4.2",
  "org.apache.commons" % "commons-email" % "1.5",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.9.0",
  "org.mongodb" %% "casbah" % "3.1.1"
)