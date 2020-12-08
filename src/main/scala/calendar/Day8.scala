package calendar

import utils.ConversionUtils.safeToOptionInt

import scala.annotation.tailrec

object Day8 {
  //Day 8 part 1

  def combineNumbers(str: String, number: Int): Int = {
    val signedNumb: String = str.drop(3)
    val numbValue: Int = str.drop(3).filter(_.isDigit).toInt
    if (signedNumb.contains("-")) number - numbValue else number + numbValue
  }

  def program(instructions: List[String]): Int = {
    @tailrec
    def aux(instructionAux: List[String], acc: Int, visited: List[Int], currentIndex: Int): Int = {

      if (visited.contains(currentIndex)) {
        println(visited)
        println(instructions(currentIndex))
        acc
      } else {
        instructionAux match {
          case Nil => acc
          case ::(head, _) if head.startsWith("nop") =>
            val nextIndex: Int = currentIndex + 1
            aux(instructions.drop(nextIndex), acc, visited :+ currentIndex, nextIndex)
          case ::(head, _) if head.startsWith("acc") =>
            val nextIndex: Int = currentIndex + 1
            aux(instructions.drop(nextIndex), combineNumbers(head, acc), visited :+ currentIndex, nextIndex)
          case ::(head, _) if head.startsWith("jmp") =>
            val nextIndex: Int = combineNumbers(head, currentIndex)
            aux(
              instructions.drop(nextIndex),
              acc,
              visited :+ currentIndex,
              nextIndex
            )
        }
      }
    }
    aux(instructions, 0, Nil, 0)
  }

  //Day 8 part 2
  //Ran n times with each line of this output changed until I find one that didn't enter in the if condition
  // (304, jmp - 47) was the lucky instruction
  def getAllJmpAndNopInstructions(instructions: List[String], indexes: List[Int]): List[(Int, String)] = {
    indexes.map(index => (index, instructions(index))).filterNot(_._2.startsWith("acc"))
  }

  def changeInstruction(instruction: String): String = {
    val reshapedInstruction = instruction.drop(3)
    if (instruction.startsWith("nop")) "jmp" + reshapedInstruction
    else if (instruction.startsWith("jmp")) "nop" + reshapedInstruction
    else instruction
  }

  //Made this automatic runner after the solution
  def runner(maybeFaultyOperations: List[Int], instructions: List[String]): Unit = {
    maybeFaultyOperations.foreach { index =>
      val oldInstruction: String = instructions(index)
      val newInstruction: String = changeInstruction(oldInstruction)
      val newInstructions: List[String] = instructions.take(index) ++ List(newInstruction) ++ instructions.drop(
        index + 1
      )
      println(s"Program numb: $index, old instruction $oldInstruction, new instruction $newInstruction")
      println(s"new instructions: $newInstructions")
      program(newInstructions)
      println("program ended")
    }

  }

}
