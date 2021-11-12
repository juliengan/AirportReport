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
  test("ReportTest"){
    assert(Report.commonIdentity() == "commonIdentity() checked")
    assert(Report.runwayTypes() == "runwayTypes() checked")
    assert(Report.highest() == "highest() checked")
    assert(Report.lowest() == "lowest() checked")

  }

  test("QueryTest"){
    assert(Main.getAirportsWithRunways("VA").contains(AirportAndRunways("", "Vatican City Heliport", "VA-0001", List())))
  }


  test("parseCSV as List of String Test"){
    val airportsData = parseAirport().head
    assert(airportsData.split(",")(0).toInt == 6523)
    assert(airportsData.split(",")(1).equals("\"00A\""))
    assert(airportsData.split(",")(2).equals("\"heliport\""))
    assert(airportsData.split(",")(3)equals("\"Total Rf Heliport\""))
    assert(airportsData.split(",")(4).toFloat == 40.07080078125)
    assert(airportsData.split(",")(5).toFloat == -74.93360137939453)
    assert(airportsData.split(",")(6).toInt == 11)
    assert(airportsData.split(",")(7).equals("\"NA\""))
    assert(airportsData.split(",")(8).equals("\"US\""))
    assert(airportsData.split(",")(9).equals("\"US-PA\""))
    assert(airportsData.split(",")(10).equals("\"Bensalem\""))
    assert(airportsData.split(",")(11).equals("\"no\""))
    assert(airportsData.split(",")(12).equals("\"00A\""))
    assert(airportsData.split(",")(13).equals(""))
    assert(airportsData.split(",")(14).equals("\"00A\""))
    assert(airportsData.split(",")(15).equals(""))
    assert(airportsData.split(",")(16).equals(""))
    assert(airportsData.split(",")(17).equals(""))
  }

  test("parseCSV as List of case class (Airport, Country, Runway)"){
    val airport: Airport = CSV.read("airports.csv", Airport.parseAirport).lines.next()
    assert(airport.id.toInt == 6530)
    assert(airport.ident.equals("\"00A\""))
    assert(airport.airport_type.equals("\"heliport\""))
    assert(airport.name.equals("\"Total Rf Heliport\""))
    assert(airport.lat_deg.toFloat == 40.07080078125)
    assert(airport.lon_deg.toFloat == -74.93360137939453)
    assert(airport.elevation_dt.toInt == 11)
    assert(airport.continent.equals("\"NA\""))
    assert(airport.iso_country.equals("\"US\""))
    assert(airport.iso_region.equals("\"US-PA\""))
    assert(airport.municipality.equals("\"Bensalem\""))
    assert(airport.scheduled_service.equals("\"no\""))
    assert(airport.gps_code.equals("\"00A\""))
    assert(airport.iata_code.equals(""))
    assert(airport.local_code.equals("\"00A\""))
    assert(airport.home_link.equals(""))
    assert(airport.wikipedia_link.equals(""))
    assert(airport.keywords.equals(""))
  }

}
