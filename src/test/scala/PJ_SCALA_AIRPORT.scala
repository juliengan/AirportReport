import scala.io.Source
import PJ_SCALA_AIRPORT._
class PJ_SCALA_AIRPORT_Test extends org.scalatest.FunSuite {
  test("parse airports function"){
    assert(PJ_SCALA_AIRPORT.get_airports() == Source.fromFile("/home/julie/Bureau/Scala/PJ/resources/airports.csv").getLines().toList)
  }
  test("parse countries function"){
    assert(PJ_SCALA_AIRPORT.get_countries() == Source.fromFile("/home/julie/Bureau/Scala/PJ/resources/countries.csv").getLines().toList)
  }

}
