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
    assert(Main.getAirportsWithRunways() == "test checked ")
  }

  test("check values of  case class"){

  }

}
