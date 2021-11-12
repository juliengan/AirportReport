package model

import scala.util.Try

case class Country(
                    id: String,
                    code: String,
                    name: String,
                    continent: String,
                    wikipedia_link: String,
                    keywords: String)

// Companion object
object Country {
  //pattern matching is forbose so you can implement detailed error msg if you feel like it
  def parseCountry(line: Array[String]):Option[Country] = {
    (Try(line(0)).toOption, Try(line(1).toString).toOption,Try(line(2).toString).toOption, Try(line(3).toString).toOption,
      Try(line(4)).toOption, Try(line(5).toString).toOption)

    match {
      case (Some(a), Some(b), Some(c), Some(d), Some(e), Some(f)) => Some(Country(a,b,c,d,e,f))
      case _ => None
    }
  }
}
