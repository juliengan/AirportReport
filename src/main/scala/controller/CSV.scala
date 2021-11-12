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
}
