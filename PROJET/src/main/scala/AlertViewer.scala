import java.util

import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.collection.JavaConverters._
import play.api.libs.json._
import play.api.libs.functional.syntax._


object AlertViewer {
  def main(args: Array[String]): Unit = {
    consumeFromKafka("alerts")
  }

  def consumeFromKafka(topic: String) = {
    val props = new Properties()

    props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("auto.offset.reset", "latest")
    props.put("group.id", "1")

    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)

    consumer.subscribe(util.Arrays.asList(topic))

    while (true) {
      val record = consumer.poll(1000).asScala

      record.foreach{ record =>
        val record_json = preprocessToJSON(record.value())
        display(record_json)
      }
    }
  }



  /*
  Get the streamed data and transform it to JSON format for further process
   */
  def preprocessToJSON(record : String ): JsObject = {
    return Json.parse(record).as[JsObject]
  }

  /*
  Check if the data retreived from the stream is an alert
    -> If it is an Alert:
      -> Send it to the topic Alert
    -> If it is not an Alert
      -> Do anything

      TESTING PHASE !! CHANGING IT LATER -> WHEN THE DATA IS PROCESSES IN THE STEP 1
   */
  def display(data : JsObject): Unit = {
    val alert = (data)
    println(alert)
  }
}