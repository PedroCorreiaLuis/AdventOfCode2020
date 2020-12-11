package calendar

import scala.annotation.tailrec

object Day10 {

  def convertToVoltages(ls: List[String]): List[Int] = {
    ls.map(_.toInt).sorted
  }

  def countVoltage(ls: List[Int]): Int = {

    @tailrec
    def auxVoltageCount(
      voltageList: List[Int],
      currentAdapter: Int,
      acc1: Int,
      acc3: Int
    ): Int = {
      voltageList match {
        case Nil =>
          acc1 * acc3
        case ::(head, tl) =>
          val newDiff: Int = head - currentAdapter
          newDiff match {
            case 1 => auxVoltageCount(tl, head, acc1 + 1, acc3)
            case 2 => auxVoltageCount(tl, head, acc1, acc3)
            case 3 => auxVoltageCount(tl, head, acc1, acc3 + 1)
            case _ => 0
          }
      }
    }
    auxVoltageCount(ls, 0, 0, 1)
  }

  def countArrangementsBruteForce(ls: List[Int], occurrences1: Int, occurrences3: Int): Int = {
    (occurrences1 to occurrences1 + occurrences3)
      .flatMap(ls.combinations)
      .distinct
      .withFilter(_.contains(ls.last))
      .map(countVoltage)
      .count(_ != 0)
  }

  // had a little help, this formula:
  // path_count(Adapter) = sum(path_count(Adapter - 1), path_count(Adapter - 2), (Adapter - 3))
  def countArrangements(voltageList: List[Int]): Long = {
    val maxVoltage: Int = voltageList.max

    @tailrec
    def aux(voltsList: List[Int], aggregatorLookup: Map[Int, Long]): Long = {
      voltsList match {
        case Nil => aggregatorLookup(maxVoltage)
        case ::(head, tl) =>
          val voltageValue: Long = aggregatorLookup(head - 1) + aggregatorLookup(head - 2) + aggregatorLookup(head - 3)
          aux(tl, aggregatorLookup + (head -> voltageValue))
      }
    }

    aux(voltageList, Map(0 -> 1L).withDefaultValue(0))
  }

}
