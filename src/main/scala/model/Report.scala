import controller.CSV
import model.{Airport, Country, Runway}
import model.Airport.countryFromCode
import scala.collection.MapView
import scala.util.Try
object Report {
  
  /** **************************** Parse CSV : countries, airports and runways associated as Iterators of case classes ********************* */
  val airports: Iterator[Airport] = CSV.read("airports.csv", Airport.parseAirport).lines
  val countries: Iterator[Country] = CSV.read("countries.csv", Country.parseCountry).lines
  val runways: Iterator[Runway] = CSV.read("runways.csv", Runway.parseRunway).lines
  /** ******************************************************************************************************* */
  

  def getInput() = {
    Console.println("***********Report****************" +
      "\" \" +\n      \"/*************** Choice / Menu : ************************************\\n     \" +" +
      "\n      \"* 1)    either 10 countries with highest or lowest airports (2) \\n     \" +\n      \"" +
      "* 2)    type of runways (\\\"surface\\\") / country\\n     \" +\n      \"" +
      "* 3)    top 10 most common runway latitude (\\\"le_ident\\\")\\n     \" +\n      " +
      "\"********************************************************************/\"")
    val option_rep = Console.in.read()
    println("Vous avez choisi, " + option_rep)
    option_rep
  }
  
  
  def InitReport(option_rep: Int): Unit = option_rep match {
    case 1 => //HighestAirports()
    case 2 => //LowestAirports()
    case 3 => TypeRunway()
    case 4 => Top10Identity()
  }
  
  //List of tuples country name and the number of airports
  //Iterator[Airport] != List[Airport] ;
  /*val groupByMapAirportsCount: List[(String, Int)] = airports.map(x => x.iso_country).toList.groupBy(identity).mapValues { airports.length }
  def HighestAirports(): List[(String, Int)] ={
    /*** List of 10 countries with highest number of airports
     * MapReduce => * 1) Map : retrieves tuples from countries (iso_country) and from airports (code)
                    * => compare the two and increment the number of airports => we get at the end a list
                    * of tuples containing the (name of the country, number of airports)
                    * 2) Reduce : we sort the list in descending order to keep the first first tuples and return them */
    val reverseSortedCountries: List[(String, Int)] = groupByMapAirportsCount.sortWith(-_._2 < -_._2)
    reverseSortedCountries.take(10).map { case (code, count) => (countryFromCode(code), count) }
  }

  def LowestAirports(): List[(String, Int)] ={
    /*** List of 10 countries with lowest number of airports ***/
    val sortedCountries: List[(String, Int)] = groupByMapAirportsCount.sortWith(_._2 < _._2)
    sortedCountries.take(10).map { case (code, count) => (countryFromCode(code), count) }
  }*/
  
  
  def TypeRunway(): MapView[String, Set[String]] = {
    /** * Type of Runways ("surface") per Country ** */
    val SurfaceType: Map[String, String] = runways.map { x => (x.airport_ident, x.surface) }.toMap
    val listCountryType: List[(String, String)] = airports.map { x => (countryFromCode(x.iso_country), Try {
      SurfaceType(x.ident)
    }.getOrElse(""))
    }.toList
    
    val runwaysSurfacePerCountry: MapView[String, Set[String]] = listCountryType groupBy (_._1) mapValues { x => x.filterNot {
      _._2 == ""
    }.map(_._2).toSet
    }
    runwaysSurfacePerCountry
  }
  
  def Top10Identity(): List[String] = {
    /** ** The 10 most common runway identity ("le_ident") *** */
    // runways : table
    // select * from runwys
    //  on veut common identity => select identity from runways group by
    val mostCommonRunways: List[String] = runways.map(x => Try {
      x.le_ident
    }.getOrElse("")).toList.groupBy(identity)
      .mapValues {
        _.size
      }.toList.sortWith(-_._2 < -_._2).take(10).map { case (id, count) => id }
    mostCommonRunways
  }
}
