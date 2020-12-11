import calendar.Day10._
import utils.FileUtils.readFromTargetFile

object Runner extends App {

  val lines = readFromTargetFile("/Users/pedrocorreialuis/IdeaProjects/AdventOfCode2020/src/resources/inputDay10")

  println(countArrangements(convertToVoltages(lines)))

}
