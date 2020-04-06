import java.util.Properties
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.kafka.clients.producer._

case class Drone(
                  `_c0` : String,                             //_c0,
                  `Plate ID`: String,                         // Plate ID,
                  `Registration State`: String,               // Registration State,
                  `Plate Type`: String,                       // Plate Type,
                  `Issue Date`: String,                       // Issue Date,
                  `Violation Code`: Int,                   // Violation Code,
                  `Vehicle Body Type`: String,                // Vehicle Body Type,
                  `Vehicle Make`: String,                     // Vehicle Make,
                  `Issuing Agency`: String,                   // Issuing Agency,
                  `Street Code1`: String,                     // Street Code1,
                  `Violation Location`: Double,               // Violation Location,
                  `House Number`: String,                     // House Number,
                  `Street Name`: String,                      // Street Name,
                  `Vehicle Color`: String,                    // Vehicle Color,
                  `Latitude`: Double,                         // Latitude,
                  `Longitude`: Double,                        // Longitude,
                  `DroneID`: Int,                          // DroneID,
                  `Alert`: Int,                            // Alert,
                  `Battery` : Double                          // Battery
                )

object Producer {
  def main (args: Array[String]): Unit = {

    val pathToFile = "/Users/adinhrelja/Desktop/NEWscalaProject/data/nyc-parking-tickets/Parking_Violations2015.csv"

    val sparkConfig = new SparkConf()
      .setMaster("local[5]")
      .setAppName("Drone Simulator")

    val spark = SparkSession.builder
      .config(sparkConfig)
      .getOrCreate()

    import spark.implicits._

    val df = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(pathToFile)

    // from DF
    val drone1 = df.where(df("DroneID")===1)
    val drone2 = df.where(df("DroneID")===2)
    val drone3 = df.where(df("DroneID")===3)
    val drone4 = df.where(df("DroneID")===4)

    // drone simulator
    val d1 = drone1.as[Drone]
    val d2 = drone2.as[Drone]
    val d3 = drone3.as[Drone]
    val d4 = drone4.as[Drone]

    //to JSON
    val drone1Json = d1.toJSON.collect()
    val drone2Json = d2.toJSON.collect()
    val drone3Json = d3.toJSON.collect()
    val drone4Json = d4.toJSON.collect()

    val TOPIC="dronedataclean"

    val  props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    //val firstProducer = new KafkaProducer[String, String](props)

    val threadDrone1 = new Thread {

      override def run(): Unit = {
        val firstProducer = new KafkaProducer[String, String](props)

        drone1Json.foreach{ row =>
          //val record =
          firstProducer.send(new ProducerRecord(TOPIC, "key", row))

          println(row)
          Thread.sleep(100)
        }
        firstProducer.close()
      }
    }

    val threadDrone2 = new Thread {

      override def run(): Unit = {
        val secondProducer = new KafkaProducer[String, String](props)

        drone2Json.foreach{ row =>
          //val record =
          secondProducer.send(new ProducerRecord(TOPIC, "key", row))

          println(row)
          Thread.sleep(300)
        }
        secondProducer.close()
      }
    }
    val threadDrone3 = new Thread {

      override def run(): Unit = {
        val thirdProducer = new KafkaProducer[String, String](props)

        drone3Json.foreach{ row =>
          //val record =
          thirdProducer.send(new ProducerRecord(TOPIC, "key", row))

          println(row)
          Thread.sleep(200)
        }
        thirdProducer.close()
      }
    }
    val threadDrone4 = new Thread {

      override def run(): Unit = {
        val fourthProducer = new KafkaProducer[String, String](props)

        drone4Json.foreach{ row =>
          //val record =
          fourthProducer.send(new ProducerRecord(TOPIC, "key", row))

          println(row)
          Thread.sleep(150)
        }
        fourthProducer.close()
      }
    }

    threadDrone1.start()
    threadDrone2.start()
    threadDrone3.start()
    threadDrone4.start()
  }
}