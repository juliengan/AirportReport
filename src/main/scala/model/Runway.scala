package model

import scala.util.Try

final case class Runway(
                         id: String,
                         airport_ref: String,
                         airport_ident: String,
                         length_ft: String,
                         width_ft: String,
                        surface: String,
                         lighted: String,
                         closed: String,
                         le_ident: String,
                         le_latitude_deg: String,
                         le_longitude_deg: String,
                        le_elevation_ft: String,
                         le_heading_degT: String,
                         le_displaced_threshold_ft: String,
                         he_ident: String,
                         he_latitude_deg: String,
                        he_longitude_deg: String,
                         he_elevation_ft: String,
                         he_heading_degT: String,
                         he_displaced_threshold_ft: String
                       )

// Companion object
object Runway {
  //pattern matching is forbose so you can implement detailed error msg if you feel like it
  def parseRunway(line: Array[String]):Option[Runway] = {
    (Try(line(0)).toOption, Try(line(1)).toOption,Try(line(2)).toOption, Try(line(3)).toOption,
      Try(line(4)).toOption, Try(line(5)).toOption,Try(line(6)).toOption, Try(line(7)).toOption,
      Try(line(8)).toOption, Try(line(9)).toOption,Try(line(10)).toOption, Try(line(11)).toOption,
      Try(line(12)).toOption, Try(line(13)).toOption,Try(line(14)).toOption, Try(line(15)).toOption,
      Try(line(16)).toOption, Try(line(17)).toOption,Try(line(18)).toOption, Try(line(19)).toOption )
    match {
      case (Some(a), Some(b), Some(c), Some(d), Some(e), Some(f), Some(g), Some(h),
      Some(i), Some(j), Some(k), Some(l), Some(m), Some(n), Some(o), Some(p), Some(q), Some(r),Some(s),Some(t))
      => Some(Runway(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t))
      case _ => None
    }
  }
}
