import calendar.Day7._
import utils.FileUtils.readFromTargetFile

object Runner extends App {

  val lines = readFromTargetFile("/Users/pedrocorreialuis/IdeaProjects/AdventOfCode2020/src/resources/inputDay7")

  val bags: List[Bags] = convertToBags(lines)

  println(countInnerBags(bags))

}
