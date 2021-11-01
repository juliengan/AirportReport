import scala.io.Source
import PJ_SCALA_AIRPORT._
class PJ_SCALA_AIRPORT_Test extends org.scalatest.FunSuite {
  test("open function"){
    assert(PJ_SCALA_AIRPORT.get_airports() == Source.fromFile("/home/julie/Bureau/Scala/PJ/resources/airports.csv").getLines().toList)
  }

}
