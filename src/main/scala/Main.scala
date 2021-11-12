import Query.getAirportsWithRunways
import controller.CSV
import model.Airport.countryFromCode
import model.{Airport, Country, Runway}

import scala.collection.IterableOnce.iterableOnceExtensionMethods
import scala.io.StdIn.readLine
import scala.util.Try

object Main {

  def menu():Any = scala.io.StdIn.readLine("Please enter your choice > ") match {
    case "1" =>
      val query= readLine("Please enter your query> ")
      val countries = CSV.read("countries.csv", Country.parseCountry)
      val airports = CSV.read("airports.csv", Airport.parseAirport)
      val runways = CSV.read("runways.csv", Runway.parseRunway)
      countries.lines.foreach(x => {
        if(x.name.toUpperCase() == query.toUpperCase()  || x.code.toUpperCase() == query.toUpperCase()){

          val code = x.code;

          airports.lines.foreach(a =>{
            if(a.iso_country.toUpperCase() == code.toUpperCase() ){
              println(a);
              println("Runways : ");
              val run_list =  runways.lines.foreach(r => {
                if(r.airport_ref.toUpperCase() == a.ident.toUpperCase() ){
                  println(r);
                }
              });



            }
          });
        }
      } );


    //val valeur = Console.in.read();
    //val airportsAndRunways = getAirportsWithRunways("AM")
      /*val airportsAndRunways = getAirportsWithRunways(scala.io.StdIn.readLine("Enter either code or country name : "))
      airportsAndRunways.foreach(x=> println(x))*/
    case "2" => Report.InitReport(Report.getInput())

    case "3" => val airports = CSV.read("airports.csv", Airport.parseAirport)
      println(airports.nbInvalidLine)
      airports.lines.foreach(println)

    case "4" => val countries = CSV.read("countries.csv", Country.parseCountry)
      println(countries.nbInvalidLine)
      countries.lines.foreach(println)

    case "5" => val runways = CSV.read("runways.csv", Runway.parseRunway)
      println(runways.nbInvalidLine)
      runways.lines.foreach(println)
    case _ => "Invalid input"
  }

  def main(args : Array[String]):Unit ={
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

  /** **************************** Parse CSV : countries, airports and runways associated as Iterators of case classes ********************* */

  val airports: Iterator[Airport] = CSV.read("airports.csv", Airport.parseAirport).lines
  val countries: Iterator[Country] = CSV.read("countries.csv", Country.parseCountry).lines
  val runways: Iterator[Runway] = CSV.read("runways.csv", Runway.parseRunway).lines

  /** ******************************************************************************************************* */


  def getInput: String = {
    /** *************************    Returns country code if country entered             ************************ */
    Console.println("***********************Query to display list of airports and runways at each airport \n" +
      "Choose the country : \n********************")
    // User input, either code or country name
    val country = scala.io.StdIn.readLine("Enter either code or country name : ")
    country
  }

  def correctInput(country: String): String = country match {
    /** ************** Secures the user input country and name matching partial/fuzzy if necessary ****** */
    case "" => "Please enter country name or code"
    case x => deriveCountryCode(x.toUpperCase())
  }

  /** ******************************************* Name matching partial/fuzzy ***************************************************** */

  // Check the country or code entered exists in the database countries (Iterator[Country]) and returns it
  /*def FuzzyMatching(country: String, inputType: String = "NAME"): String = inputType match {
    //NAME => user input = country_name
      /***************!!!!!PROBLEM =>   returns the Iterator[Country], list of all countries matching with the input code/country name
       * this is not what is expected !!!!
       */
    case "NAME" => Try {countries.filter(x => {x.name.toLowerCase() == country.toLowerCase() })}.getOrElse(FuzzyMatching(country, "CODE"))

    // CODE => user input = code
    case "CODE" => Try {countries.filter(x => {x.name.substring(0, country.length).toLowerCase() == country.toLowerCase()})}
    .getOrElse(FuzzyMatching(country, "UNMATCHED"))
    case _ => ""
  }*/

  /** *********** Retrieves code from country_name ********* */
  val CodetoCountryMap: Map[String, String] = countries.map { x => (x.code, x.name) }.toMap
  val CountryToCodeMap: Map[String, String] = CodetoCountryMap.map { case (code, country) => (country, code) }

  /** ********************* Retrieves the country code from the country name ********* */
  def codeFromCountry(countryName: String): String = Try {
    CountryToCodeMap(countryName)
  }.getOrElse("")




  def deriveCountryCode(country: String): String = country.length match {
    case 2 => country
    case _ => codeFromCountry(country) //codeFromCountry(FuzzyMatching(country))
  }

  /** ***************************************************END NAME MATCHING / FUZZY ********************************************************************* */


  /** *********** Main function of Query => Query output : returns airports and runways at each airports as a list ****************** */

  /** ***************** container for Airports with country, airport name, identifier and Runways at each of these airports */
  case class AirportAndRunways(country: String, airportName: String, airportIdentifier: String, runways: List[String] = List[String]())

  def getAirportsWithRunways(inputCountryCode: String): List[AirportAndRunways] = {
    val countryFromCountryCode: String = countryFromCode(inputCountryCode)
    val queryOutput: List[AirportAndRunways] = airports.filter(x => {
      x.iso_country == inputCountryCode
    })
      .map { x => AirportAndRunways(countryFromCountryCode, x.name, x.ident) }.toList

    val listRequiredAirports: List[String] = queryOutput.map { x => x.airportIdentifier }
    val listRequiredRunways: List[Runway] = runways.filter { x => listRequiredAirports.contains(x.airport_ident) }.toList

    /** ****** Add runways to the airport list **** */
    //AirportsAndRunways.foreach { println }
    queryOutput
    /*AirportsAndRunways.map { x =>
      val runwaysList: List[Runway] = listRequiredRunways.filter(y=> {x.airportIdentifier == y.airport_ident})
      x..add(runways = runwaysList)
    }*/
  }





  }

