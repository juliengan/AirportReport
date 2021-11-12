import Query.getAirportsWithRunways
import controller.CSV
import model.Airport.countryFromCode
import model.{Airport, Country, Runway}

import scala.collection.IterableOnce.iterableOnceExtensionMethods
import scala.io.StdIn.readLine
import scala.util.Try

object Main {
  /** **************************** Parse CSV : countries, airports and runways associated as Iterators of case classes ********************* */

  val airports: Iterator[Airport] = CSV.read("airports.csv", Airport.parseAirport).lines
  val countries: Iterator[Country] = CSV.read("countries.csv", Country.parseCountry).lines
  val runways: Iterator[Runway] = CSV.read("runways.csv", Runway.parseRunway).lines

  /** ******************************************************************************************************* */
  def menu(): Any = scala.io.StdIn.readLine("Please enter your choice > ") match {
    case "1" =>
      val query = readLine("Please enter your query> ")
      countries.foreach(x => {
        if (x.name.toUpperCase() == query.toUpperCase() || x.code.toUpperCase() == query.toUpperCase()) {

          val code = x.code;

          airports.foreach(a => {
            if (a.iso_country.toUpperCase() == code.toUpperCase()) {
              println(a);
              println("Runways : ");
              val run_list = runways.foreach(r => {
                if (r.airport_ref.toUpperCase() == a.ident.toUpperCase()) {
                  println(r);
                }
              });

            }
          });
        }
      });

    //val airportsAndRunways = getAirportsWithRunways("AM")
    /*val airportsAndRunways = getAirportsWithRunways(scala.io.StdIn.readLine("Enter either code or country name : "))
      airportsAndRunways.foreach(x=> println(x))*/
    case "2" => Report.InitReport(Report.getInput())

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
