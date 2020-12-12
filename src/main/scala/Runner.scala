import calendar.Day11._
import utils.FileUtils.readFromTargetFile

object Runner extends App {

  val lines = readFromTargetFile("/Users/pedrocorreialuis/IdeaProjects/AdventOfCode2020/src/resources/inputDay11")

  val finalMap = runner2(constructMap(lines))

  println(countOccupiedSeats(finalMap))

}
