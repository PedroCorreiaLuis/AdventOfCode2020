package calendar

import scala.annotation.tailrec

object Day12 {
  case class Instruction(direction: String, value: Int)
  case class Pos(x: Int, y: Int)

  def convertToInstructions(lines: List[String]): List[Instruction] = {
    lines.map { instruction =>
      val (direction, value): (String, String) = instruction.partition(_.isLetter)
      Instruction(direction, value.toInt)
    }
  }

  def calculateNewDirection(direction: String, side: String, values: Int): String = {
    direction match {
      case "N" =>
        side match {
          case "R" =>
            values match {
              case 90  => "E"
              case 180 => "S"
              case 270 => "W"
              case 360 => "N"
            }
          case "L" =>
            values match {
              case 90  => "W"
              case 180 => "S"
              case 270 => "E"
              case 360 => "N"
            }
        }
      case "S" =>
        side match {
          case "R" =>
            values match {
              case 90  => "W"
              case 180 => "N"
              case 270 => "E"
              case 360 => "S"
            }
          case "L" =>
            values match {
              case 90  => "E"
              case 180 => "N"
              case 270 => "W"
              case 360 => "S"
            }
        }
      case "E" =>
        side match {
          case "R" =>
            values match {
              case 90  => "S"
              case 180 => "W"
              case 270 => "N"
              case 360 => "E"
            }
          case "L" =>
            values match {
              case 90  => "N"
              case 180 => "W"
              case 270 => "S"
              case 360 => "E"
            }
        }
      case "W" =>
        side match {
          case "R" =>
            values match {
              case 90  => "N"
              case 180 => "E"
              case 270 => "S"
              case 360 => "W"
            }
          case "L" =>
            values match {
              case 90  => "S"
              case 180 => "E"
              case 270 => "N"
              case 360 => "W"
            }
        }
    }
  }

  def translateInstruction(instruction: Instruction, currentDirection: String, posAcc: Pos): (Pos, String) = {

    val northIncrement: Pos = Pos(posAcc.x, posAcc.y + instruction.value)
    val southIncrement: Pos = Pos(posAcc.x, posAcc.y - instruction.value)
    val eastIncrement: Pos = Pos(posAcc.x + instruction.value, posAcc.y)
    val westIncrement: Pos = Pos(posAcc.x - instruction.value, posAcc.y)

    instruction.direction match {
      case "N" => (northIncrement, currentDirection)
      case "S" => (southIncrement, currentDirection)
      case "E" => (eastIncrement, currentDirection)
      case "W" => (westIncrement, currentDirection)
      case "F" =>
        currentDirection match {
          case "N" => (northIncrement, currentDirection)
          case "S" => (southIncrement, currentDirection)
          case "E" => (eastIncrement, currentDirection)
          case "W" => (westIncrement, currentDirection)
        }
      case side => (Pos(posAcc.x, posAcc.y), calculateNewDirection(currentDirection, side, instruction.value))
    }
  }

  def runInstructions(instructions: List[Instruction], initialDirection: String): Pos = {
    @tailrec
    def aux(auxInstructions: List[Instruction], currentPos: Pos, currentDirection: String): Pos = {
      auxInstructions match {
        case Nil => currentPos
        case ::(head, tl) =>
          val (pos, direction): (Pos, String) = translateInstruction(head, currentDirection, currentPos)
          println(pos, direction)
          aux(tl, pos, direction)
      }
    }
    aux(instructions, Pos(0, 0), initialDirection)
  }

  def calculateManhattanDistance(pos: Pos): Int = {
    Math.abs(pos.x) + Math.abs(pos.y)
  }

}
