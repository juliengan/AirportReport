import Main.{parseAirport, parseCountry, parseRunway}
import Query.AirportAndRunways
import Report.airportsData
import controller.CSV
import model.{Airport, Country, Runway}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.{a, convertToAnyShouldWrapper}


object  ScalaTesting
class ScalaTesting extends Matchers {

  //CSV.read("runways.csv", Runway.parseRunway).lines shouldBe a[Iterator[Runway]]
  //CSV.read("airports.csv", Airport.parseAirport).lines shouldBe a[Iterator[Airport]]
  //CSV.read("country.csv", Country.parseCountry).lines shouldBe a[Iterator[Country]]
  Main.parseAirport() shouldBe a [List[String]]
  Report.InitReport() shouldBe a [List[(String, Int)]]
}

class ScalaTesting2 extends AnyFunSuite{
  test("InitReportTest"){
    assert(Report.InitReport() == "test checked ")
  }

  test("QueryTest"){
    assert(Main.getAirportsWithRunways("VA").contains(AirportAndRunways("VA", "Vatican City Heliport", "VA-0001", List())))
  }


  test("parseCSV as List of String Test"){
    val airportsData = parseAirport()
    assert(airportsData.map(_.split(",")(0)).head(0).toInt == 6523)
    assert(airportsData.map(_.split(",")(1)).head(0).toString == "00A")
    assert(airportsData.map(_.split(",")(2)).head(0).toString == "heliport")
    assert(airportsData.map(_.split(",")(3)).head(0).toString == "Total Rf Heliport")
    assert(airportsData.map(_.split(",")(4)).head(0) == 40.07080078125)
    assert(airportsData.map(_.split(",")(5)).head(0) == -74.93360137939453)
    assert(airportsData.map(_.split(",")(6)).head(0) == 11)
    assert(airportsData.map(_.split(",")(7)).head(0).toString == "NA")
    assert(airportsData.map(_.split(",")(8)).head(0).toString == "US")
    assert(airportsData.map(_.split(",")(9)).head(0).toString == "US-PA")
    assert(airportsData.map(_.split(",")(10)).head(0).toString == "Bensalem")
    assert(airportsData.map(_.split(",")(11)).head(0).toString == "no")
    assert(airportsData.map(_.split(",")(12)).head(0).toString == "00A")
    assert(airportsData.map(_.split(",")(13)).head(0).toString == "")
    assert(airportsData.map(_.split(",")(14)).head(0).toString == "00A")
    assert(airportsData.map(_.split(",")(15)).head(0).toString == "")
    assert(airportsData.map(_.split(",")(16)).head(0).toString == "")
    assert(airportsData.map(_.split(",")(17)).head(0).toString == "")
  }

  test("parseCSV as List of case class (Airport, Country, Runway)"){
    val airports: List[Airport] = CSV.read("airports.csv", Airport.parseAirport).lines.toList
    assert(airports.map(x=>(x.id)).head(0).toInt == 6523)
    assert(airports.map(x=>(x.ident)).head(0).toString == "00A")
    assert(airports.map(x=>(x.airport_type)).head(0).toString == "heliport")
    assert(airports.map(x=>(x.name)).head(0).toString == "Total Rf Heliport")
    assert(airports.map(x=>(x.lat_deg)).head(0) == 40.07080078125)
    assert(airports.map(x=>(x.lon_deg)).head(0) == -74.93360137939453)
    assert(airports.map(x=>(x.elevation_dt)).head(0) == 11)
    assert(airports.map(x=>(x.continent)).head(0).toString == "NA")
    assert(airports.map(x=>(x.iso_country)).head(0).toString == "US")
    assert(airports.map(x=>(x.iso_region)).head(0).toString == "US-PA")
    assert(airports.map(x=>(x.municipality)).head(0).toString == "Bensalem")
    assert(airports.map(x=>(x.scheduled_service)).head(0).toString == "no")
    assert(airports.map(x=>(x.gps_code)).head(0).toString == "00A")
    assert(airports.map(x=>(x.iata_code)).head(0) == 6523)
    assert(airports.map(x=>(x.local_code)).head(0) == 6523)
    assert(airports.map(x=>(x.home_link)).head(0) == 6523)
    assert(airports.map(x=>(x.wikipedia_link)).head(0) == 6523)
    assert(airports.map(x=>(x.keywords)).head(0) == 6523)
  }

}
