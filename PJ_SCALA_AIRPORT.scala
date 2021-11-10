import scala.collection.MapView
import scala.io.BufferedSource
import com.sun.tools.javac.code.Symbol.MethodSymbol
import com.sun.tools.javac.code.TypeTag

import scala.concurrent.ExecutionContext.global
import scala.io.Source

object PJ_SCALA_AIRPORT extends App {

  //def main(): Unit ={
    println("Query 1 or Report 2 ?")



  def Init(option : String,airports: List[Airport], countries: List[Country], runways: List[Runway]): Unit = option match {
    case "1" => Query(airports,countries,runways)
    case "2" => Reports(airports,countries,runways)
    case _ => println("Fin du programme")
  }


  //def Query (Type) Read le fichier CSV et le parcourir
  //def Reports (Type airports ... csv)

    val option:Int =
    option match {
      case 1: query //display airports & runways at each airport#jQuery ("select * from airport")
      //soit avec pays, soit avec code (iata) => comparer ident avec airport_ident
      case 2: //report
      //soit les 10 pays avec le plus grand nb airports / idem pour plus faible (count)
      //type de pistes (column 'surface') par pays => pays comparé avec airport puis avec piste
      // les 10 most common runway latitude ("le_ident")
    }
  }

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


      //cols.map(x => Airport)
    }
    println(cols)
    //cols
  }

  def get_countries()={
    //println("Id, Code, Name, Continent, Wikipedia link, Keywords")
    val bufferedSource = Source.fromFile("/home/julie/Bureau/Scala/PJ/resources/countries.csv")
    val countries = bufferedSource.getLines().toList
    bufferedSource.close
    val cols: List[String] = null    //countries
    for (line <- countries) {
      val cols = line.split(",").map(_.trim).toList
    }
    println(cols)
    //cols
  }

  def filename()={
  }

}