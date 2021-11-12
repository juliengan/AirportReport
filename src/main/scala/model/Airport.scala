package model

import controller.CSV
import scala.util.Try

case class Airport(
                    id: String,
                    ident: String,
                    airport_type: String,
                    name: String,
                    lat_deg: String,
                   lon_deg: String,
                    elevation_dt: String,
                    continent: String,
                    iso_country: String,
                   iso_region: String,
                    municipality: String,
                    scheduled_service: String,
                    gps_code: String,
                   iata_code: String,
                    local_code: String,
                    home_link: String,
                    wikipedia_link: String,
                    keywords: String
                  )
// Object companion
object Airport {
  /****** Gets country name from country code ********/
  val countries: Iterator[Country] = CSV.read("countries.csv", Country.parseCountry).lines

  val CodetoCountryMap: Map[String, String] = countries.map { x => (x.code, x.name) }.toMap

  def countryFromCode(countryCode: String): String = Try { CodetoCountryMap.get(countryCode).get }.getOrElse("")

  //pattern matching is forbose so you can implement detailed error msg if you feel like it
  def parseAirport(line: Array[String]):Option[Airport] = {
    (Try(line(0)).toOption, Try(line(1)).toOption,Try(line(2)).toOption, Try(line(3)).toOption,
      Try(line(4)).toOption, Try(line(5)).toOption,Try(line(6)).toOption, Try(line(7)).toOption,
      Try(line(8)).toOption, Try(line(9)).toOption,Try(line(10)).toOption, Try(line(11)).toOption,
      Try(line(12)).toOption, Try(line(13)).toOption,Try(line(14)).toOption, Try(line(15)).toOption,
      Try(line(16)).toOption, Try(line(17)).toOption)

    match {
      case (Some(a), Some(b), Some(c), Some(d), Some(e), Some(f), Some(g), Some(h),
      Some(i), Some(j), Some(k), Some(l), Some(m), Some(n), Some(o), Some(p), Some(q), Some(r))

      => Some(Airport(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r))

      case _ => None
    }
  }
}
