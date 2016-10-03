package com.dima.cota

import CotaTestApi.Output

object TextAnalyser {

  def analyse(rawText: String): Output = {
    val gender = new ClassTokenFeature[ClassMappings.Genders.Value](ClassMappings.genderMappings) {
      override def output = {
        val o = super.output
        if(o=="mixed") "unknown"
        else o
      }
    }
    val sentiment = new ClassTokenFeature[ClassMappings.Sentiments.Value](ClassMappings.sentimentMappings)
    val duration = new MyDuration

    val features =  List(duration, gender, sentiment)

    rawText
      .toLowerCase
      .replaceAll("[^a-z0-9/]"," ")
      .split("[ +\r\n]")
      .foreach(token=>applyFeatures(token, features))

    Output(duration.output, gender.output, sentiment.output)
  }

  def applyFeatures(s: String, features: List[TokenFeature]): Boolean = {
    features match {
      case Nil => false
      case head :: tail =>
        if(head.processToken(s)) false
        else applyFeatures(s, tail)
    }
  }
}
