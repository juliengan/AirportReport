package model
import model.Airport.countryFromCode
import model.Query.{airports, deriveCountryCode, runways}

import scala.collection.MapView
import scala.collection.immutable.Nil.groupBy
import scala.util.Try
object Report{
  def getInput() = {
    Console.println("***********Report****************" +
      "\" \" +\n      \"/*************** Choice / Menu : ************************************\\n     \" +" +
      "\n      \"* 1)    either 10 countries with highest or lowest airports (2) \\n     \" +\n      \"" +
      "* 2)    type of runways (\\\"surface\\\") / country\\n     \" +\n      \"" +
      "* 3)    top 10 most common runway latitude (\\\"le_ident\\\")\\n     \" +\n      " +
      "\"********************************************************************/\"")
    val option_rep = Console.in.read()
    println("Vous avez choisi, "+ option_rep)
    option_rep

  }
  def InitReport(option_rep: Int): Unit = option_rep match {
    case 1 => HighestAirports()
    case 2 => LowestAirports()
    case 3 => TypeRunway()
    case 4 => Top10Identity()
  }

  /**
   * A List with Tuple2 of country name and the count of airports.
   */
  val groupByMapAirportsCount: List[(String, Int)] = airports.map(x => x.iso_country).toList.groupBy(identity).mapValues { airports.size }

  /***
   * List of 10 countries with highest number of airports
   *
   * MapReduce =>
   *
   * 1) Map : retrieves tuples from countries (iso_country) and from airports (code)
   * => compare the two and increment the number of airports => we get at the end a list
   * of tuples containing the (name of the country, number of airports)
   *
   * 2) Reduce : we sort the list in descending order to keep the first first tuples and return them
   *
   *
   */
  def HighestAirports(): List[Airport] ={
    val reverseSortedCountries: List[(String, Int)] = groupByMapAirportsCount.sortWith(-_._2 < -_._2)
    val topTenAirportCountries: List[(String, Int)] = reverseSortedCountries.take(10).map { case (code, count) => (countryFromCode(code), count) }
  }

  /***
   * List of 10 countries with lowest number of airports
   *
   * MapReduce =>
   *
   * 1) Map : retrieves tuples from countries (iso_country) and from airports (code)
   * => compare the two and increment the number of airports => we get at the end a list
   * of tuples containing the (name of the country, number of airports)
   *
   * 2) Reduce : we sort the list in ascending order to keep the first first tuples and return them
   *
   *
   */
  def LowestAirports(): List[(String, Int)] ={
    val sortedCountries: List[(String, Int)] = groupByMapAirportsCount.sortWith(_._2 < _._2)
    sortedCountries
  }

  /******
   * Type of Runways ("surface") per Country
   */
  def TypeRunway(): MapView[String,Set[String]] ={
    val SurfaceType: Map[String, String] = runways.map { x => (x.airport_ident, x.surface) }.toMap
    val listCountryType: List[(String, String)] = airports.map { x => (countryFromCode(x.iso_country),Try { SurfaceType(x.ident) }.getOrElse(""))}.toList
    /***** grouping the above list structure to obtain a country with its set of runways.
     * Also filtering out countries for which surface information is not available.
     */
    val runwaysSurfacePerCountry: MapView[String, Set[String]] = listCountryType groupBy (_._1) mapValues
      { x => x.filterNot { _._2 == "" }.map(_._2).toSet }
    runwaysSurfacePerCountry
  }
    /**
     * grouping the above list structure to obtain a country with its set of runways.
     * Also filtering out countries for which surface information is not available.
     */
  /******
   * The 10 most common runway identity ("le_ident")
   */
  def Top10Identity(): List[String] ={
    // runways : table
    // select * from runways
    //  on veut common identity => select identity from runways group by
    val mostCommonRunways: List[String] = runways.map(x => Try { x.le_ident }.getOrElse("")).toList.groupBy(identity)
      .mapValues { _.size }.toList.sortWith(-_._2 < -_._2).take(10).map { case (id, count) => id }
    mostCommonRunways
    //val mostCommonRunways: List[String] = runways.map(x => x.airport_ident)}.getOrElse("").toList.groupBy(identity).mapValues { runways.size }.toList.sortWith(-_._2 < -_._2).take(10).map { case (id, count) => id }
  }
}