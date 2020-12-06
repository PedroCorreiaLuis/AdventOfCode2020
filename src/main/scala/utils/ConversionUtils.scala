package utils

import scala.util.Try

object ConversionUtils {
  def safeToOptionInt(in: String): Option[Int] = {
    Try(in.toInt).toOption
  }

  def safeToOptionLong(in: String): Option[Long] = {
    Try(in.toLong).toOption
  }
}
