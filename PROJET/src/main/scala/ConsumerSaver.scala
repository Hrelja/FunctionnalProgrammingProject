import com.mongodb.DBObject
import com.mongodb.casbah.{MongoClient, MongoClientURI, MongoCollection}
import com.mongodb.util.JSON
import java.util

import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.Properties

import com.mongodb.casbah.commons.MongoDBObject

import scala.collection.JavaConverters._


object ConsumerSaver {
  def main(args: Array[String]): Unit = {
    consumeFromKafka("dronedata")
  }
  def consumeFromKafka(topic: String) = {
    val props = new Properties()

    props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("group.id", "key")

    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)

    consumer.subscribe(util.Arrays.asList(topic))

    val mongo =  MongoClient(MongoClientURI("mongodb://127.0.0.1:27017/"))

    val db = mongo.getDB("DroneData")

    val collection = db.getCollection("data")

    collection.remove(MongoDBObject.empty)

    while (true) {
      val record = consumer.poll(1000).asScala

      record.foreach{ rec =>
        val data = rec.value()
        val dbObject : DBObject = JSON.parse(data).asInstanceOf[DBObject]
        val buffer = new util.ArrayList[DBObject]()

        buffer.add(dbObject)

        collection.insert(buffer)

        buffer.clear()
      }
    }
  }
}