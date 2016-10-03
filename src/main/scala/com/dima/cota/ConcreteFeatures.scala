package com.dima.cota

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.Duration

class MyDuration extends TokenFeature {

  var begin = LocalDate.MAX
  var end = LocalDate.MIN

  val dateFormat = DateTimeFormatter.ofPattern("MM/dd/uuuu")

  def processToken(s: String): Boolean = {
    try {
      if(s.length==10 && s.contains("/")) { // prescreen tokens to make things a little faster
        val date = LocalDate.parse(s, dateFormat)
        if (date.isBefore(begin)) begin = date
        if (date.isAfter(end)) end = date
        true
      } else false
    } catch {
      case _: Throwable => false
    }
  }

  def output: Int =
    if(begin==LocalDate.MAX) 0
    else Duration.between(begin.atStartOfDay(), end.atStartOfDay()).toDays.toInt + 1
}




