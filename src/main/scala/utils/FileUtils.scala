package utils

import scala.annotation.tailrec
import scala.io.BufferedSource
import scala.io.Source.fromFile

object FileUtils {

  def readFromTargetFile(input: String): List[String] = {
    val source: BufferedSource = fromFile(input)
    val lines: List[String] = source.getLines().toList
    source.close()
    lines
  }

  def readUntilSpaces(input: String): List[String] = {
    val lines: List[String] = readFromTargetFile(input)

    @tailrec
    def aux(lines: List[String], splitLines: List[String]): List[String] = {
      lines match {
        case Nil => splitLines.reverse
        case ::(head, tl) =>
          if (head.nonEmpty) aux(tl, (splitLines.headOption.getOrElse("") + " " + head) +: splitLines.drop(1))
          else aux(tl, "" +: splitLines)
      }
    }

    aux(lines, Nil)
  }
}
