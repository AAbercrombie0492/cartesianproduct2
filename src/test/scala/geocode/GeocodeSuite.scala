package geocode

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import geocode.ReverseGeocode._

import org.scalatest.Assertions._

//implicit val formats = DefaultFormats // Brings in default date formats etc.

// Functions to test
//KafkaParser
//PlumeParser
//GoogleMapsRequester
//GetDistinctLatLongs
//Geocoder

/**
Created by Anthony Abercrombie on 07/10/2017
 */

@RunWith(classOf[JUnitRunner])
class ReverseGeocodeSuite extends FunSuite {
  trait TestGeo {
    val latitudeTest = 40.714224
    val longitudeTest = -73.961452
    val plumeLogTest = ""
    val plumeSeqTest = Seq()
    // GOOGLEMAPS API key
    // access google maps token https://github.com/zipfian/cartesianproduct2/wiki/TOKEN
    lazy val GOOGLEMAPS:Option[String] = sys.env.get("GOOGLEMAPS") orElse {
      println("No token found. Check how to set it up at https://github.com/zipfian/cartesianproduct2/wiki/TOKEN")
      None
    }
  }

  test("Google Maps API returns a String") {
    new TestGeo {
      val result = GoogleMapsRequester(latitudeTest, longitudeTest, GOOGLEMAPS, "locality")
      assert(result.getClass.getName == "java.lang.String")
    }

  }

//    test("Kafka parser returns a sequence of strings") {
//      new TestGeo{
//        val result = KafkaParser(plumeLogTest).getClass.getName
//        assert(result).getClass.getName = "scala.collection.Seq$")
//  //      assert(result.getClass.getName = "scala.collection.immutable.$colon$colon")
//        assert(result(0).getClass.getName = "java.lang.String")
//
//      }
//    }
//
//    test("Plume Parser returns a sequence of CassandraRows") {
//      new TestGeo{
//        val result = Geocoder(plumeSeqTest)
//        assert(result.getClass.getName = "scala.collection.Seq$")
//  //      assert(result.getClass.getName = "scala.collection.immutable.$colon$colon")
//        assert(result(0).getClass.getName = "CassandraRow")
//      }
//    }
//
//    test("G")

}