/**
  * Created by AnthonyAbercrombie on 7/5/17.
  */

import org.apache.spark
import org.apache.spark._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.sql.cassandra
import org.apache.spark.sql.SparkSession
import com.datastax.spark
import com.datastax.spark._
import com.datastax.spark.connector
import com.datastax.spark.connector._
import com.datastax.spark.connector.cql
import com.datastax.spark.connector.cql._
import com.datastax.spark.connector.cql.CassandraConnector
import com.datastax.spark.connector.cql.CassandraConnector._
import com.datastax.spark.connector.rdd.CassandraTableScanRDD
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer

import scala.util.parsing.json.JSON.parseFull

import scala.util.parsing.json.JSONObject

abstract class CassandraTable

def KafkaParser(kafkaLog : String) : Seq[String] = {
  // Parses a Kafka log [kafkaLog] and returns a sequence of strings with json
  // structure [plumeRecords]
  ???
}

def PlumeParser(plumeRecords : Seq[String]) : Seq[CassandraRow] = {
  // Parses a sequence of strings with json structure [plumeRecords], extracts
  // geographically relevant data, and returns a sequence of Cassandra
  // rows for a geographically enriched Cassandra table [plumeGeoRows].
  ???
}

/**
 * Takes a latitude coord [lat], a longitude coord [long], and a
 * Google Maps API key [GOOGLEMAPS]. These arguements are then
 *  factored into an API call. The results of the API call are then
 *  returned as a json string [googleMapsResult]
  */
def GoogleMapsRequester(lat: Double, long: Double, GOOGLEMAPS: String) : String = {
  // Input: lat --- latitude, long --- longitude, GOOGLEMAPS --- API key
  // Output: googleMapsResult --- raw result from google maps API
  val base_url = f"https://maps.googleapis.com/maps/api/geocode/json?latlng=$lat%f,$long%f&key=$GOOGLEMAPS%s"
  ???
}

/**
  * Executes a query that gets distinct lat/long pairs from the raw Plume Cassandra Table.
  */
def GetDistinctLatLongs() : Seq[Set[Double]] = {
  // Input : No input
  // OUTPUT : coordinates ---- Seq[Set]
  ???
}

/**
  * Applies the GoogleMapsRequester helper function to a Sequence of lat/long pairs.
  * Organizes the results into the GeoDictionary Cassandra Table, which is created on the spot. The table
  * is composed with the following columns:
  *
  * latitude : Double
  * longitude: Double
  * country: String
  * political: String
  * administrative_1 : String
  * geodata : UDT --- ask Daniel Gorham
  *
  */
def Geocoder(coordinates: Seq[Set[Double]]) : CassandraTable = {
// Input : coordinates ---- Sequence of unique lat/long pairs generated by GetDistinctLatLongs()
  // Output: GeoDictionary ---- Cassandra Table that maps each unique lat long to geographic metadata
  ???
}

val CreateGeoDictionary =
  """CREATE TABLE geodictionary (
    latitude FLOAT,
    longitude FLOAT,
      country TEXT,
      admin1 TEXT,
      locality TEXT,
      geodata TEXT,
      PRIMARY KEY (latitude, longitude) WITH CLUSTERING ORDER BY (country DESC);
  """

  case class PlumeGeodata(latitude: Double, longitude: Double, country: String, admin1: String, locality: String, geodata: String)

val record = sc.parallelize(Seq(new PlumeGeodata))

saveAsCassandraTable


def SelectDistinctPoints() : CassandraTable = {
  val SelectDistinctPoints = """SELECT latitude, longitude
    FROM geodictionary
  """

  case class PlumeStationCoords(latitude: Double, longitude: Double)
  val geoDictionary = sc.cassandraTable[PlumeStationCoords]("CartesianProduct", "geodictionary")
                        .select("latitude", "longitude")
}
