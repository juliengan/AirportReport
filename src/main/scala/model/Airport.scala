package model
import model.Query.CodetoCountryMap

import scala.util.Try

case class Airport(id: String, ident: String, airport_type: String, name: String, lat_deg: String,
                   lon_deg: String, elevation_dt: String, continent: String, iso_country: String,
                   iso_region: String, municipality: String, scheduled_service: String, gps_code: String,
                   iata_code: String, local_code: String, home_link: String, wikipedia_link: String, keywords: String) {
  val cs = this.getClass.getConstructors

  def createFromList(params: List[Any]) =
    cs(0).newInstance(params map {
      _.asInstanceOf[AnyRef]
    }: _*).asInstanceOf[Airport]
}

object Airport {

  /**
   * A method that gets country name from country code
   */
  def countryFromCode(countryCode: String): String = Try { CodetoCountryMap.get(countryCode).get }.getOrElse("")

  //pattern matching is forbose so you can implement detailed error msg if you feel like it
  def parseAirport(line: Array[String]):Option[Airport] = {
    (Try(line(0)).toOption, Try(line(1).toString).toOption,Try(line(2).toString).toOption, Try(line(3).toString).toOption,
      Try(line(4)).toOption, Try(line(5).toString).toOption,Try(line(6).toString).toOption, Try(line(7).toString).toOption,
      Try(line(8).toString).toOption, Try(line(9).toString).toOption,Try(line(10).toString).toOption, Try(line(11).toString).toOption,
      Try(line(12).toString).toOption, Try(line(13).toString).toOption,Try(line(14).toString).toOption, Try(line(15).toString).toOption,
      Try(line(16).toString).toOption, Try(line(17).toString).toOption)
    match {
      case (Some(a), Some(b), Some(c), Some(d), Some(e), Some(f), Some(g), Some(h),
      Some(i), Some(j), Some(k), Some(l), Some(m), Some(n), Some(o), Some(p), Some(q), Some(r))
      => Some(Airport(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r))
      case _ => None
    }
  }
}