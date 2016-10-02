package com.dima.cota

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MyDuration extends TokenFeature {

  var begin = LocalDate.MAX
  var end = LocalDate.MIN

  val dateFormat = DateTimeFormatter.ofPattern("MM/dd/uuuu")

  def processToken(s: String): Boolean = {
    try {
      val date = LocalDate.parse(s,dateFormat)
      if(date.isBefore(begin)) begin = date
      if(date.isAfter(end)) end = date
      true
    } catch {
      case _: Throwable => false
    }
  }

  def output: Int =
    if(begin==end || begin==LocalDate.MAX)
      0
    else
      java.time.Duration.between(begin.atStartOfDay(), end.atStartOfDay()).toDays.toInt + 1
}




