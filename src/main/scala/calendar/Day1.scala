package calendar

import scala.annotation.tailrec

object Day1 {

  //Day 1 part 1
  def sumTo2020Part1(ls: List[Int]): Int = {
    @tailrec
    def aux(firstElem: Int, ls1: List[Int], ls2: List[Int]): Int = {
      (ls1, ls2) match {
        case (::(head, tl), _)                  => if (head + firstElem == 2020) head * firstElem else aux(firstElem, tl, ls2)
        case (Nil, ::(head, tl)) if tl.nonEmpty => aux(head, tl, tl.tail)
        case _                                  => 0
      }
    }

    if (ls.nonEmpty) aux(ls.head, ls, ls.tail) else 0
  }

  //Day 1 part 2
  def sumTo2020(ls: List[Int]): Int = {
    @tailrec
    def aux(currentElem: Int, nextElem: Int, list: List[Int], listHolder: List[Int], listHolder2: List[Int]): Int = {
      (list, listHolder) match {
        case (::(head, tl), _) =>
          if (currentElem + nextElem + head == 2020) currentElem * nextElem * head
          else aux(currentElem, nextElem, tl, listHolder, listHolder2)
        case (Nil, ::(_, tl)) if tl.nonEmpty => aux(currentElem, tl.head, listHolder, listHolder.tail, listHolder2)
        case _ =>
          aux(listHolder2.tail.head, listHolder2.tail.tail.head, listHolder2.tail, listHolder2.tail, listHolder2.tail)
      }
    }

    aux(ls.head, ls.tail.head, ls, ls, ls)
  }
}
