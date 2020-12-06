package calendar

import utils.ConversionUtils.{safeToOptionInt, safeToOptionLong}

object Day4 {
  //Day 4 part 1
  def countValidPassports(passports: List[String]): Int = {
    val characteristics: List[String] = List("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    passports.count(passport => characteristics.forall(passport.contains))
  }

  // Day 4 part 2
  case class Passport(
   birthYear: Option[Int],
   issueYear: Option[Int],
   expirationYear: Option[Int],
   height: Option[Int],
   hairColour: Option[String],
   eyeColour: Option[String],
   pid: Option[Long]
 )

  def convertToPassport(passports: List[String]): List[Passport] = {
    def getStringAfterColons(str: Option[String]): Option[String] = {
      str.flatMap(_.split(":").lastOption)
    }
    def locateTargetString(key: String, ls: List[String]): Option[String] = {
      ls.find(_.contains(key))
    }

    val validEyeColours: List[String] = List("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

    passports.map { passport =>
      val passportInfo: List[String] = passport.split(" ").toList

      val birthYear: Option[String] = getStringAfterColons(locateTargetString("byr", passportInfo))
      val issueYear: Option[String] = getStringAfterColons(locateTargetString("iyr", passportInfo))
      val expirationYear: Option[String] = getStringAfterColons(locateTargetString("eyr", passportInfo))
      val height: Option[String] = getStringAfterColons(locateTargetString("hgt", passportInfo))
      val hairColour: Option[String] = getStringAfterColons(locateTargetString("hcl", passportInfo))
      val eyeColour: Option[String] = getStringAfterColons(locateTargetString("ecl", passportInfo))
      val pid: Option[String] = getStringAfterColons(locateTargetString("pid", passportInfo))

      Passport(
        birthYear = birthYear.flatMap(safeToOptionInt).filter(year => year >= 1920 && year <= 2002),
        issueYear = issueYear.flatMap(safeToOptionInt).filter(year => year >= 2010 && year <= 2020),
        expirationYear = expirationYear.flatMap(safeToOptionInt).filter(year => year >= 2020 && year <= 2030),
        height = height
          .map { height =>
            val (heigthValue, heightMetric) = height.partition(_.isDigit)
            (heightMetric, safeToOptionInt(heigthValue))
          }
          .filter {
            case ("cm", Some(numb)) if numb >= 150 && numb <= 193 => true
            case ("in", Some(numb)) if numb >= 59 && numb <= 76   => true
            case _                                                => false
          }
          .flatMap(_._2),
        hairColour = hairColour.filter(
          hairC => hairC.startsWith("#") && hairC.drop(1).forall(_.isLetterOrDigit) && hairC.length == 7
        ),
        eyeColour = eyeColour.filter(eyeC => validEyeColours.contains(eyeC)),
        pid = pid.filter(_.length == 9).flatMap(safeToOptionLong)
      )

    }
  }

  def countValidPassports2(passports: List[Passport]): Int = {
    passports.count(
      passport =>
        passport.birthYear.isDefined && passport.expirationYear.isDefined && passport.eyeColour.isDefined && passport
          .hairColour
          .isDefined && passport.height.isDefined && passport.issueYear.isDefined && passport.pid.isDefined
    )
  }
}
