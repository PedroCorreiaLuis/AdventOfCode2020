package calendar

import scala.annotation.tailrec

object Day6 {

  //Day 6 part 1
  def sumAnyonesYes(ls: List[String]): Int = {
    ls.map(_.filter(_.isLetter).distinct.length).sum
  }

  //Day 6 part 2
  def intersectAll(ls: List[String]): String = {
    @tailrec
    def aux(lsAux: List[String], acc: String): String = {
      lsAux match {
        case Nil          => acc
        case ::(head, tl) => aux(tl, acc.intersect(head))
      }
    }
    aux(ls, ls.mkString(""))
  }

  def sumEveryonesYes(ls: List[String]): Int = {
    ls.map { str =>
      val groupAnswers: List[String] = str.split(" ").toList.filter(_.nonEmpty)
      intersectAll(groupAnswers).length
    }.sum
  }
}
