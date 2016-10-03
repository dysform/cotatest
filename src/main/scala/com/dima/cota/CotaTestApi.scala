package com.dima.cota

import spray.json._
import fommil.sjs.FamilyFormats._

object CotaTestApi {
  case class Input(text: String)
  case class Output(duration: Int, gender: String, sentiment: String)

  def analyseText(jsonParagraph: String): String = {
    try {
      val input = jsonParagraph.parseJson.convertTo[Input]
      TextAnalyser.analyse(input.text).toJson.compactPrint
    } catch {
      case e: Throwable => Output(0,"unknown","unknown").toJson.compactPrint
    }
  }
}
