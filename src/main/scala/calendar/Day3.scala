package calendar

import utils.FileUtils.readFromTargetFile

import scala.annotation.tailrec

object Day3 {

  //Day 3 part 1/2
  def countNumberTrees(horizontalMovementScale: Int = 3, verticalMovement: Int = 1): Long = {
    val lines: List[String] = readFromTargetFile(
      "/Users/pedrocorreialuis/IdeaProjects/datastore-commons/base/resources/inputDay3"
    )

    @tailrec
    def aux(lines: List[String], horizontalMovement: Int, count: Int): Long = {
      val nextMovement: Int = horizontalMovement + horizontalMovementScale
      lines match {
        case Nil => count
        case ::(head, _) =>
          val nextLine: List[String] = lines.drop(verticalMovement)
          if (head(horizontalMovement % head.length) == '#') aux(nextLine, nextMovement, count + 1)
          else aux(nextLine, nextMovement, count)
      }

    }
    aux(lines.drop(verticalMovement), horizontalMovementScale, 0)
  }
}
