import calendar.Day9._
import utils.FileUtils.readFromTargetFile

object Runner extends App {

  val lines = readFromTargetFile("/Users/pedrocorreialuis/IdeaProjects/AdventOfCode2020/src/resources/inputDay9")

  println(findOutlier(lines, 25))

  val res = findSubList(lines.map(_.toLong), 1038347917).sorted
  println(findEncryptedValue(res))
}
