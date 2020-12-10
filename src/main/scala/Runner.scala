import calendar.Day10._
import utils.FileUtils.readFromTargetFile

object Runner extends App {

  val lines = readFromTargetFile("/Users/pedrocorreialuis/IdeaProjects/AdventOfCode2020/src/resources/inputDay10Test")

  println(countVoltage(convertToVoltages(lines)))
  // 22 10
  // 7 5
  println(countArrangementsBruteForce(convertToVoltages(lines), 7, 5))

}
