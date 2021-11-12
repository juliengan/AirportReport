import Main.{airports, countries, runways}
import controller.CSV
import model.Airport.countryFromCode
import model.{Airport, Country, Runway}

import scala.io.StdIn.readLine
import scala.util.Try

object Query {

  def getInput: String = {
    /** *************************    Returns country code if country entered             ************************ */
    Console.println("***********************Query to display list of airports and runways at each airport \n" +
      "Choose the country : \n********************")
    // User input, either code or country name
    scala.io.StdIn.readLine("Enter either code or country name : ")
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
  val CountryToCodeMap: Map[String, String] = CodetoCountryMap.map { case (code, country) => (country, code) }

  /** ********************* Retrieves the country code from the country name ********* */
  def codeFromCountry(countryName: String): String = Try {
    CountryToCodeMap(countryName)
  }.getOrElse("")

  val CodetoCountryMap: Map[String, String] = countries.map { x => (x.code, x.name) }.toMap


  def deriveCountryCode(country: String): String = country.length match {
    case 2 => country
    case _ => codeFromCountry(country) //codeFromCountry(FuzzyMatching(country))
  }

  /** ***************************************************END NAME MATCHING / FUZZY ********************************************************************* */
  /** *********** Main function of Query => Query output : returns airports and runways at each airports as a list ****************** */
  /** ***************** container for Airports with country, airport name, identifier and Runways at each of these airports */
  case class AirportAndRunways(country: String, airportName: String, airportIdentifier: String, runways: List[Runway] = List[Runway]())


}
