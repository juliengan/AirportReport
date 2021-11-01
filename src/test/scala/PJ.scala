import scala.io.Source

class PJ_Test extends org.scalatest.FunSuite {
  test("open function"){
    assert(PJ.get_airports() == Source.fromFile("/home/julie/Bureau/Scala/PJ/resources/airports.csv").getLines().toList)
  }

}
