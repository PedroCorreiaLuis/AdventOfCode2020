package calendar
import utils.ConversionUtils.safeToOptionInt

import scala.annotation.tailrec

object Day7 {

  //Day 7 part 1
  case class Bag(colour: String, quantity: Int)
  case class Bags(mostOuter: Bag, innerBags: List[Bag])

  def toBag(bag: String): Bag = {
    val rawBag = bag
      .replace("bags", "")
      .replace("bag", "")
      .replace(".", "")
      .trim
    val (quantity, cleanedBag) = rawBag.partition(_.isDigit)
    Bag(colour = cleanedBag.trim, quantity = safeToOptionInt(quantity).getOrElse(0))
  }

  def convertToBags(ls: List[String]): List[Bags] = {
    ls.map { rule =>
      val bagRules: Array[String] = rule.split("contain")
      val mostOuterBag: String = bagRules.head
      val innerBags: List[String] = bagRules.last.split(",").toList

      val outerBagParsed: Bag = toBag(mostOuterBag)
      val innerBagsParsed: List[Bag] = innerBags.map(toBag)
      Bags(mostOuter = outerBagParsed, innerBags = innerBagsParsed)
    }
  }

  def countValidBags(colour: String, quantity: Int, bagsList: List[Bags]): Int = {
    bagsList.count { bags =>
      val mostOuterColour: String = bags.mostOuter.colour
      mostOuterColour != colour && bags
        .innerBags
        .exists(bag => bag.colour == colour && quantity <= bag.quantity)
    }
  }

  def filterBags(colour: String, quantity: Int, bagsList: List[Bags]): List[Bags] = {
    val bagsFirst: List[Bags] = bagsList.filter { bags =>
      bags.innerBags.exists(bag => bag.colour.contains(colour) && quantity <= bag.quantity)
    }
    (bagsFirst ++ innerBagsSearch(bagsFirst, Nil, bagsList)).distinct
  }

  @tailrec
  def innerBagsSearch(iterBagsL: List[Bags], acc: List[Bags], allBagsList: List[Bags]): List[Bags] = {

    val mostOuterBagsColour: List[String] = iterBagsL.map(_.mostOuter.colour)

    val finalBags: List[Bags] =
      allBagsList.filter(
        bags => mostOuterBagsColour.exists(outerColours => bags.innerBags.exists(_.colour.contains(outerColours)))
      )
    if (finalBags == iterBagsL) acc else innerBagsSearch(finalBags, finalBags ++ acc, allBagsList)

  }

  //Day 7 part 2
  def dupeBags(listBags: List[Bags]): List[Bags] = {
    listBags.map { bags =>
      Bags(bags.mostOuter, bags.innerBags.flatMap { bag =>
        List.fill[Bag](bag.quantity)(Bag(bag.colour, 1))
      })
    }
  }

  def searchOuterColours(iterBagsL: List[Bags], fullBagsList: List[Bags]): List[Bags] = {
    val colours: List[String] = iterBagsL.flatMap(_.innerBags.map(_.colour))

    val firstIter: List[Bags] = dupeBags(colours.flatMap { colour =>
      fullBagsList.filter(bags => bags.mostOuter.colour.contains(colour))
    })

    @tailrec
    def innerMechanism(iterBagsL: List[Bags], acc: List[Bags], allBagsList: List[Bags]): List[Bags] = {
      val colours: List[String] = iterBagsL.flatMap(_.innerBags.map(_.colour))

      val finalBags: List[Bags] = dupeBags(colours.flatMap { colour =>
        fullBagsList.filter(bags => bags.mostOuter.colour.contains(colour))
      })

      if (finalBags == iterBagsL) acc else innerMechanism(finalBags, finalBags ++ acc, allBagsList)
    }
    firstIter ++ innerMechanism(firstIter, Nil, fullBagsList)

  }

  def countInnerBags(bagsList: List[Bags]): Int = {

    val shinnyGoldbags: List[Bags] = bagsList.filter(_.mostOuter.colour.contains("shiny gold"))
    val dupedGoldBags: List[Bags] = dupeBags(shinnyGoldbags)
    val innerBags: List[Bags] = searchOuterColours(dupedGoldBags, bagsList)

    (dupedGoldBags.flatMap(_.innerBags) ++ innerBags.flatMap(_.innerBags)).length

  }

}
