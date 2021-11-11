package controller

import model.{Airport, Country, Runway}

import java.nio.file.{Files, Path}
import scala.jdk.CollectionConverters.IteratorHasAsScala
import scala.io.Source

final case class ReadResult[A](lines: Iterator[A], nbInvalidLine: Int)

object CSV {
  def read[A](fileName: String, parseLine: Array[String] => Option[A], regex: String = ","):ReadResult[A] = {
    val (parsedLine, invalidLine) = Option(Files.lines(Path.of(s"src/main/scala/data/$fileName")))
      .map(_.iterator().asScala)
      .getOrElse(Iterator.empty) // if file can't be read option will be a none.
      .drop(1)  // drop csv header
      .map(_.split(regex))
      .map(_.map(_.trim))
      .map(parseLine)
      .partition(_.isDefined)

    ReadResult(parsedLine.flatten, invalidLine.size)
  }

  def parse_airport_csv():List[Airport]={
    val bufferedSource = Source.fromFile("/home/julie/Bureau/Scala/PJ/resources/airports.csv")
    val list = bufferedSource.getLines().drop(1).toList
    bufferedSource.close
    val rows = list.map(line => line.split(",").map(_.trim).toList )
    val airports = rows.map(row => Airport(row(0),row(1),row(2),row(3),row(4),row(5),row(6),row(7), row(8),row(9),row(10),row(11),row(12),row(13),row(14),row(15),row(16),row(18)))
    airports
  }

  def parse_countries_csv(): List[Country]={
    val bufferedSource = Source.fromFile("/home/julie/Bureau/Scala/PJ/resources/countries.csv")
    val list = bufferedSource.getLines().toList
    bufferedSource.close
    val cols = list.map(line => line.split(",").map(_.trim).toList )
    val countries = cols.map(col => Country(col.head,col(1),col(2),col(3),col(4),col(5)))
    countries
  }

  def parse_runways_csv(): List[Runway]={
    val bufferedSource = Source.fromFile("/home/julie/Bureau/Scala/PJ/resources/runways.csv")
    val list = bufferedSource.getLines().toList
    bufferedSource.close
    val cols = list.map(line => line.split(",").map(_.trim).toList )
    val countries = cols.map(col => Runway(col.head,col(1),col(2),col(3),col(4),col(5),col(6),col(7), col(8),col(9),col(10),col(11),col(12),col(13),col(14),col(15),col(16),col(18),col(19),col(20)))
    countries
  }
}
