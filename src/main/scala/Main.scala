import Query.AirportAndRunways
import Report.{airportsData, countriesData, runwaysData}
import controller.CSV
import model.Airport.countryFromCode
import model.{Airport, Country, Runway}

import scala.io.StdIn.readLine
import scala.io.Source
import scala.util.Try

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
    Source.fromFile("src/main/scala/data/countries.csv").getLines.drop(1).toList.map {
      _.replaceAll("\"", "")

    }
  }

  def parseRunway():List[String]={
    Source.fromFile("src/main/scala/data/runways.csv").getLines.drop(1).toList map {_.replaceAll("\"", "")}
  }

  /*****************************************                END PARSE           ***************************************/

  def correctInput(country: String): String = country match {
    /** ************** Secures the user input country and name matching partial/fuzzy if necessary ****** */
    case "" => "Please enter country name or code"
    case x => deriveCountryCode(x.toUpperCase())
  }

  /** ******************************************* Name matching partial/fuzzy ***************************************************** */

  // Check the country or code entered exists in the database countries (Iterator[Country]) and returns it
  def FuzzyMatching(passedInput: String, matchLogic: String = "FULL"): String = matchLogic match {
    case "FULL" => Try {
      countriesData.find { _.split(",")(2).toLowerCase() == passedInput.toLowerCase() }.
        map { _.split(",")(2) }.toList.head
    }.getOrElse(FuzzyMatching(passedInput, "PARTIAL"))
    case "PARTIAL" => Try {
      countriesData.find { _.split(",")(2).substring(0, passedInput.length).toLowerCase() == passedInput.toLowerCase() }.
        map { _.split(",")(2) }.toList.head
    }.getOrElse(FuzzyMatching(passedInput, "UNMATCHED"))
    case _ => ""
  }

  /** *********** Retrieves code from country_name ********* */
  val CodetoCountryMap: Map[String, String] = countries.map { x => (x.code, x.name) }.toMap

  val CountryToCodeMap: Map[String, String] = CodetoCountryMap.map { case (code, country) => (country, code) }

  /** ********************* Retrieves the country code from the country name ********* */
  def codeFromCountry(countryName: String): String = Try {
    CountryToCodeMap(countryName)
  }.getOrElse("")


  def deriveCountryCode(country: String): String = country.length match {
    case 2 => country
    case _ => codeFromCountry(FuzzyMatching(country)) //codeFromCountry(FuzzyMatching(country))
  }

  def getAirportsWithRunways(query: String): Option[AirportAndRunways] = {
    val countryFromCountryCode: String = countryFromCode(query)

    //filter airports in a specific country
    val output: List[AirportAndRunways] = airportsData.filter{_.split(",")(8) == query}
      .map { x => AirportAndRunways(countryFromCountryCode, x.split(",")(3), x.split(",")(1)) }
    //println(output.lastOption)
    val listRequiredAirports: List[String] = output.map { x => x.airportIdentifier }
    val listRequiredRunways: List[String] = runwaysData.filter { x => listRequiredAirports.contains(x.split(",")(2)) }

    output.map { x =>
      val runwaysList: List[String] = listRequiredRunways.filter{ _.split(",")(2) == x.airportIdentifier}
      x.copy(runways = runwaysList)
    }

    // We print only the first value because there a lot of airports and runways in output
    println(output.headOption)
    output.headOption
  }

  /** ******************************************************************************************************* */
  def menu(): Any = scala.io.StdIn.readLine("Please enter your choice > ") match {
    case "1" => Console.println("***********************Query to display list of airports and runways at each airport******************** \n" +
      "Choose the country : \n*************************************************************")
      println("Please enter the country among all of these")
      countries.foreach(x => {
        println(x.name + "--" + x.code)
      })
      val query = scala.io.StdIn.readLine("Please enter a country > ")
      getAirportsWithRunways(deriveCountryCode(query))

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
