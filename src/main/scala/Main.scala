import Main.countries
import Query.{AirportAndRunways}
import controller.CSV
import model.Airport.countryFromCode
import model.{Airport, Country, Runway}

import scala.io
import scala.collection.IterableOnce.iterableOnceExtensionMethods
import scala.io.StdIn.readLine
import scala.util.Try
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

import scala.io.Source
import scala.reflect.io.File

object Main {
  /** **************************** Parse CSV : countries, airports and runways associated as Iterators of case classes ********************* */

  val airports: List[Airport] = CSV.read("airports.csv", Airport.parseAirport).lines.toList
  val countries: List[Country] = CSV.read("countries.csv", Country.parseCountry).lines.toList
  val runways: List[Runway] = CSV.read("runways.csv", Runway.parseRunway).lines.toList

  def parseAirport(): List[String] = {
    Source.fromFile("src/main/scala/data/airports.csv").getLines.drop(1).toList map {
      _.replaceAll("\"", "")
    }
  }

  def parseCountry(): List[String] ={
    Source.fromFile("src/main/scala/data/countries.csv").getLines.drop(1).toList map {
      _.replaceAll("\"", "")

    }
  }

  def parseRunway():List[String]={
    Source.fromFile("src/main/scala/data/runways.csv").getLines.drop(1).toList map {_.replaceAll("\"", "")}
  }

  def getAirportsWithRunways() = {
    println("Please enter the country among all of these")
    countries.foreach(x => {
      println(x.name + "--" + x.code)
    })
    val query = readLine("Please enter your country in partial name> ")

    //filter airports in a specific country
    val output: List[AirportAndRunways] = airports.filter(x => {
      x.lon_deg.toFloat > 40.07080078125
    }).map { x => AirportAndRunways(query, x.name, x.ident) }.toList
    //println(output.lastOption)
    val listRequiredAirports: List[String] = output.map { x => x.airportIdentifier }
    val listRequiredRunways: List[Runway] = runways.filter { x => listRequiredAirports.contains(x.airport_ident) }.toList

    output.map { x =>
      val runwaysList: List[Runway] = listRequiredRunways.filter(y => {
        x.airportIdentifier == y.airport_ident
      })
      x.copy(runways = runwaysList)
    }
    println(output.headOption)
    "test checked "
  }

  /** ******************************************************************************************************* */
  def menu(): Any = scala.io.StdIn.readLine("Please enter your choice > ") match {
    case "1" => getAirportsWithRunways()

    case "2" => Console.println("***********Report****************" +
      "*************** Choice / Menu : ************************************n" +
      "\n      \"* 1)    either 10 countries with highest or lowest airports (2)\n"  +
      "* 3)    type of runways (surface) country\n " +
      "* 4)    top 10 most common runway latitude (le_ident)\n"+
      "********************************************************************/")
      Report.InitReport()

    case "3" => airports.foreach(println)

    case "4" => countries.foreach(println)

    case "5" => runways.foreach(println)
    case _ => "Invalid input"
  }

  def main(args: Array[String]): Unit = {
    Console.println("*************Menu****************\n" +
      "1) Query\n" +
      "2) Report\n" +
      "3) Show all airports\n" +
      "4) Show all countries\n" +
      "5) Show all runways\n" +
      "" +
      "***************************")

    menu()

  }
}
