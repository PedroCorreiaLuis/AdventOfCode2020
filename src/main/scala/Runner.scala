import calendar.Day12._
import utils.FileUtils.readFromTargetFile

object Runner extends App {

  val lines = readFromTargetFile("/Users/pedrocorreialuis/IdeaProjects/AdventOfCode2020/src/resources/inputDay12")

  println(calculateManhattanDistance(runInstructions(convertToInstructions(lines), "E")))
  //1877

}
