package com.dima.cota

trait TokenFeature {
  def processToken(s: String): Boolean
}

class ClassTokenFeature(mappings: Map[String, Set[String]]) extends TokenFeature {
  val counters = scala.collection.mutable.Map[String, Int]()

  def processToken(s: String): Boolean =
    updateCounters(s)

  def updateCounters(s: String): Boolean = {
    def checkMappings(e: List[(String, Set[String])]): Option[String] = {
      e match {
        case Nil => None
        case (stype, tokens) :: tail =>
          if(tokens.contains(s)) Some(stype)
          else checkMappings(tail)
      }
    }

    val opt = checkMappings(mappings.toList)

    opt match {
      case None => false
      case Some(stype) =>
        counters.update(stype,counters.getOrElse(stype,0))
        true
    }
  }

  def output =
    if(counters.isEmpty) "unknown"
    else if(counters.size==1) counters.keys.head
    else "mixed"
}