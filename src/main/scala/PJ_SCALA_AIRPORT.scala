import scala.collection.MapView
import scala.io.BufferedSource
import com.sun.tools.javac.code.Symbol.MethodSymbol
import com.sun.tools.javac.code.TypeTag

import scala.concurrent.ExecutionContext.global
import scala.io.Source

object PJ_SCALA_AIRPORT extends App {

  case class Airport(id:Int,ident:String,airport_type:String,name:String,lat_deg:Float,
                     lon_deg:Float,elevation_dt:String,continent:String,iso_country:String,
    iso_region:String,municipality:String,scheduled_service:String,gps_code:String,
  iata_code:String,local_code:String,home_link:String,wikipedia_link:String,keywords:String)

  def get_airports()={
    //println("Id, Identity, Type name, Latitude, Longitude, Elevation, Continent, " +
      //"ISO_country, ISO_region, Municipality, Scheduled service, gps code, iata code, local code, Home Link")
    val bufferedSource = Source.fromFile("/home/julie/Bureau/Scala/PJ/resources/airports.csv")
    val airports = bufferedSource.getLines().toList
    bufferedSource.close
    val cols: List[String] = null
    for (line <- airports) {
      val cols = line.split(",").map(_.trim).toList
      """val airports_list = airports.map { airports => Airport(id = airports(cols(0)), ident = cols(1),
        airport_type = cols(2),name = cols(3),lat_deg=cols(4),lon_deg=cols(5),elevation_dt = cols(6),
        continent = cols(7),iso_country = cols(8),iso_region=cols(9),municipality = cols(10),scheduled_service = cols(11),
        gps_code = cols(12),iata_code = cols(13),local_code = cols(14),home_link = cols(15),wikipedia_link = cols(16),
        keywords = cols(17))}"""
      println(cols)

      //cols.map(x => Airport)
    }
    //cols
  }

  def get_countries()={
    //println("Id, Code, Name, Continent, Wikipedia link, Keywords")
    val bufferedSource = Source.fromFile("/home/julie/Bureau/Scala/PJ/resources/countries.csv")
    val countries = bufferedSource.getLines().toList
    bufferedSource.close
    //println(countries)
    //countries
    val cols : List[String] = null
    for (line <- countries) {
      val cols = line.split(",").map(_.trim).toList
    }
    println(s"${cols(0)}|${cols(1)}|${cols(2)}|${cols(3)}|${cols(4)}|${cols(5)}|${cols(6)}|${cols(7)}" +
      s"|${cols(8)}|${cols(9)}|${cols(10)}|${cols(11)}|${cols(12)}|${cols(13)}|${cols(14)}|${cols(15)}")
    cols
  }

  def filename()={
  }

}