package calendar

import scala.annotation.tailrec

object Day5 {
  //Day 5 part 1
  def getSeatIds(ls: List[String], numberOfRows: Int = 127, numberOfSeats: Int = 7): List[Int] = {
    ls.map { seat =>
      val rowNumber: Int = getPatternNumber(seat.take(7), numberOfRows)
      val seatNumber: Int = getPatternNumber(seat.drop(7), numberOfSeats)

      rowNumber * 8 + seatNumber
    }
  }

  def getPatternNumber(rowPattern: String, numberOfRows: Int): Int = {
    @tailrec
    def aux(minRange: Int, maxRange: Int, rowPattern: List[Char]): Int = {
      rowPattern match {
        case ::(head, Nil) => if (head == 'F' || head == 'L') minRange else maxRange
        case ::(head, tl) =>
          val newCalculatedRange: Double = (minRange + maxRange).toDouble / 2
          if (head == 'F' || head == 'L') aux(minRange, newCalculatedRange.floor.toInt, tl)
          else aux(newCalculatedRange.ceil.toInt, maxRange, tl)
      }
    }
    aux(0, numberOfRows, rowPattern.toCharArray.toList)
  }

  def highestSeatId(seatIds: List[Int]): Int = seatIds.max

  //Day 5 part 2
  def findMyNumber(seatIds: List[Int]): Int = {
    seatIds.sorted.find(seatId => seatIds.contains(seatId + 2) && !seatIds.contains(seatId + 1))
  }.getOrElse(0) + 1
}
