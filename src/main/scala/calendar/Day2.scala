package calendar

import utils.FileUtils.readFromTargetFile

object Day2 {
  //Day 2 part 1
  case class Day2DTO(min: Int, max: Int, char: Char, password: String)

  def convertToCC: List[Day2DTO] = {
    readFromTargetFile("/Users/pedrocorreialuis/IdeaProjects/datastore-commons/base/resources/inputDay2")
      .map { line =>
        val split: Array[String] = line.split(" ")

        val range = split.head
        val splitRange = range.split("-")

        Day2DTO(
          min = splitRange.head.toInt,
          max = splitRange.last.toInt,
          char = split.drop(1).head.head,
          password = split.last
        )
      }
  }

  def countValidPasswords(ls: List[Day2DTO]): Int = {
    ls.count { dto =>
      val numberOfChars: Int = dto.password.count(_ == dto.char)
      numberOfChars >= dto.min && numberOfChars <= dto.max
    }
  }

  //Day 2 part 2
  def countValidPasswordsPart2(ls: List[Day2DTO]): Int = {
    ls.count { dto =>
      val minChar: Char = dto.password(dto.min - 1)
      val maxChar: Char = dto.password(dto.max - 1)
      (minChar == dto.char || maxChar == dto.char) && minChar != maxChar
    }
  }
}
