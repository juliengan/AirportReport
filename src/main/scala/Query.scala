import Main.{airports, countries, runways}
import Report.countriesData
import controller.CSV
import model.Airport.countryFromCode
import model.{Airport, Country, Runway}

import scala.io.StdIn.readLine
import scala.util.Try

object Query {

  /** *********** Main function of Query => Query output : returns airports and runways at each airports as a list ****************** */

  /** ***************** container for Airports with country, airport name, identifier and Runways at each of these airports */
  case class AirportAndRunways(country: String, airportName: String, airportIdentifier: String, runways: List[String] = List[String]())


}
