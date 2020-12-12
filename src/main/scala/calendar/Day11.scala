package calendar

import scala.annotation.tailrec

object Day11 {

  def constructMap(lines: List[String]): Array[Array[Char]] = {
    lines.map(_.toCharArray).toArray
  }

  def iterateMap(map: Array[Array[Char]]): Array[Array[Char]] = {

    @tailrec
    def auxIterateMap(
      mapAux: Array[Array[Char]],
      constructionMap: Array[Array[Char]],
      xAxys: Int,
      yAxys: Int
    ): Array[Array[Char]] = {

      val actualPos: Char = mapAux.lift(yAxys).flatMap(_.lift(xAxys)).getOrElse('.')
      val pos1: Option[Char] = mapAux.lift(yAxys + 1).flatMap(_.lift(xAxys))
      val pos2: Option[Char] = mapAux.lift(yAxys - 1).flatMap(_.lift(xAxys))
      val pos3: Option[Char] = mapAux.lift(yAxys).flatMap(_.lift(xAxys + 1))
      val pos4: Option[Char] = mapAux.lift(yAxys).flatMap(_.lift(xAxys - 1))
      val pos5: Option[Char] = mapAux.lift(yAxys + 1).flatMap(_.lift(xAxys + 1))
      val pos6: Option[Char] = mapAux.lift(yAxys - 1).flatMap(_.lift(xAxys - 1))
      val pos7: Option[Char] = mapAux.lift(yAxys + 1).flatMap(_.lift(xAxys - 1))
      val pos8: Option[Char] = mapAux.lift(yAxys - 1).flatMap(_.lift(xAxys + 1))

      val possiblePos: List[Char] = List(pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8).flatten

      val updatedChar: Char = actualPos match {
        case 'L' if !possiblePos.contains('#')       => '#'
        case '#' if possiblePos.count(_ == '#') >= 4 => 'L'
        case c                                       => c
      }

      val updatedConstructionMap: Array[Array[Char]] =
        constructionMap.updated(yAxys, constructionMap(yAxys).updated(xAxys, updatedChar))

      if (xAxys >= map.head.length - 1) {
        if (yAxys >= map.length - 1) updatedConstructionMap
        else auxIterateMap(mapAux, updatedConstructionMap, 0, yAxys + 1)
      } else auxIterateMap(mapAux, updatedConstructionMap, xAxys + 1, yAxys)

    }

    auxIterateMap(map, map, 0, 0)

  }

  def countOccupiedSeats(map: Array[Array[Char]]): Int = {
    map.map(_.count(_ == '#')).sum
  }

  @tailrec
  def runner(oldMap: Array[Array[Char]]): Array[Array[Char]] = {
    val newMap: Array[Array[Char]] = iterateMap(oldMap)
    println(newMap.toList.map(_.toList.mkString("")).mkString("\n"))
    println("BREAK")
    if (newMap.zip(oldMap).forall { case (i, j) => i.sameElements(j) }) newMap
    else runner(iterateMap(newMap))
  }

  @tailrec
  def runner2(oldMap: Array[Array[Char]]): Array[Array[Char]] = {
    val newMap: Array[Array[Char]] = iterateMap2(oldMap)
    println(newMap.toList.map(_.toList.mkString("")).mkString("\n"))
    println("BREAK")
    if (newMap.zip(oldMap).forall { case (i, j) => i.sameElements(j) }) newMap
    else runner2(iterateMap(newMap))
  }

  def findNextChar(
    map: Array[Array[Char]],
    cardinalPoint: String,
    xAxys: Int,
    yAxys: Int
  ): Option[Char] = {

    @tailrec
    def aux(auxXAxys: Int, auxYAxys: Int): Option[Char] = {
      val nextChar: Option[Char] = map.lift(auxYAxys).flatMap(_.lift(auxXAxys))
      nextChar match {
        case Some('.') =>
          cardinalPoint match {
            case "N"  => aux(auxXAxys, auxYAxys - 1)
            case "S"  => aux(auxXAxys, auxYAxys + 1)
            case "E"  => aux(auxXAxys + 1, auxYAxys)
            case "O"  => aux(auxXAxys - 1, auxYAxys)
            case "NE" => aux(auxXAxys + 1, auxYAxys - 1)
            case "NO" => aux(auxXAxys - 1, auxYAxys - 1)
            case "SE" => aux(auxXAxys + 1, auxYAxys + 1)
            case "SO" => aux(auxXAxys - 1, auxYAxys + 1)
          }
        case char => char
      }
    }

    aux(xAxys, yAxys)
  }

  def iterateMap2(map: Array[Array[Char]]): Array[Array[Char]] = {

    @tailrec
    def auxIterateMap2(
      mapAux: Array[Array[Char]],
      constructionMap: Array[Array[Char]],
      xAxys: Int,
      yAxys: Int
    ): Array[Array[Char]] = {

      val actualPos: Char = mapAux.lift(yAxys).flatMap(_.lift(xAxys)).get

      val nPos: Option[Char] = findNextChar(mapAux, "N", xAxys, yAxys - 1)
      val sPos: Option[Char] = findNextChar(mapAux, "S", xAxys, yAxys + 1)
      val ePos: Option[Char] = findNextChar(mapAux, "E", xAxys + 1, yAxys)
      val oPos: Option[Char] = findNextChar(mapAux, "O", xAxys - 1, yAxys)
      val nePos: Option[Char] = findNextChar(mapAux, "NE", xAxys + 1, yAxys - 1)
      val noPos: Option[Char] = findNextChar(mapAux, "NO", xAxys - 1, yAxys - 1)
      val sePos: Option[Char] = findNextChar(mapAux, "SE", xAxys + 1, yAxys + 1)
      val soPos: Option[Char] = findNextChar(mapAux, "SO", xAxys - 1, yAxys + 1)

      val possiblePos: List[Char] = List(nPos, sPos, ePos, oPos, nePos, noPos, sePos, soPos).flatten

      val updatedChar: Char = actualPos match {
        case 'L' if !possiblePos.contains('#')       => '#'
        case '#' if possiblePos.count(_ == '#') >= 5 => 'L'
        case c                                       => c
      }

      val updatedConstructionMap: Array[Array[Char]] =
        constructionMap.updated(yAxys, constructionMap(yAxys).updated(xAxys, updatedChar))

      if (xAxys >= map.head.length - 1) {
        if (yAxys >= map.length - 1) updatedConstructionMap
        else auxIterateMap2(mapAux, updatedConstructionMap, 0, yAxys + 1)
      } else auxIterateMap2(mapAux, updatedConstructionMap, xAxys + 1, yAxys)

    }

    auxIterateMap2(map, map, 0, 0)

  }
}
