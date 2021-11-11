import Main.AirportAndRunways
import controller.CSV
import model.{Airport, Country, Runway}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.{a, convertToAnyShouldWrapper}

object ScalaTesting


class CSVSpec extends AnyFlatSpec with Matchers {
  CSV.read("runways.csv", Runway.parseRunway).lines shouldBe a[Iterator[Runway]]
  /*CSV.read("airports.csv", Airport.parseAirport).lines shouldBe a[Iterator[Airport]]
  CSV.read("country.csv", Country.parseCountry).lines shouldBe a[Iterator[Country]]*/

  Main.getAirportsWithRunways("AM") shouldBe a [List[AirportAndRunways]]
  //Main.getInput() shouldBe a [Int]
  //Main.getInput() shouldBe a [Int]
}

/*class suite extends AnyFunSuite{
  Main.main() == "OK"
}*/
