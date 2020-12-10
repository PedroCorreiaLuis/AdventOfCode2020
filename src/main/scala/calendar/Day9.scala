package calendar

import scala.annotation.tailrec

object Day9 {

  def findOutlier(ls: List[String], preambleQuantity: Int): Long = {
    val numbers: List[Long] = ls.map(_.toLong)
    @tailrec
    def aux(numbs: List[Long], auxPreambleQuantityMin: Int, auxPreambleQuantityMax: Int): Long = {
      val preambleNumbers: List[Long] =
        numbers.slice(auxPreambleQuantityMin, auxPreambleQuantityMin + auxPreambleQuantityMax)

      numbs match {
        case Nil => 0
        case ::(head, tl) if preambleNumbers.combinations(2).map(_.sum).contains(head) =>
          aux(tl, auxPreambleQuantityMin + 1, auxPreambleQuantityMax + 1)
        case ::(head, _) => head
      }
    }
    aux(numbers.drop(preambleQuantity), 0, preambleQuantity)
  }

  def findSubList(ls: List[Long], number: Long): List[Long] = {
    @tailrec
    def findSubListAux(list: List[Long], acc: Long, accList: List[Long]): List[Long] = {
      list match {
        case Nil => Nil
        case ::(head, tl) =>
          val newAcc: Long = head + acc
          if (newAcc > number) Nil
          else if (newAcc == number) accList
          else findSubListAux(tl, newAcc, head +: accList)
      }
    }

    def runner(lsRunner: List[Long]): List[Long] = {
      @tailrec
      def auxRunner(auxLs: List[Long], resultList: List[Long]): List[Long] = {
        auxLs match {
          case Nil => Nil
          case ::(_, tl) =>
            if (resultList.nonEmpty) resultList
            else {
              val nextIter: List[Long] = findSubListAux(tl, 0, Nil)
              auxRunner(tl, nextIter)
            }
        }
      }
      auxRunner(lsRunner, Nil)
    }

    runner(ls)

  }

  def findEncryptedValue(ls: List[Long]): Long = {
    val sortedList: List[Long] = ls.sorted
    (sortedList.headOption ++ sortedList.lastOption).toList.sum
  }
}
