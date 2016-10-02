package com.dima.cota

case class Output(duration: Int, gender: String, sentiment: String)

object SentenceParser {

  val duration = new MyDuration

  val gender = new ClassTokenFeature(
    Map("Male"->Set("he","his"),
    "Female"->Set("she","her"))) {

    override def output =
      if(counters.size!=1) "unknown"
      else counters.keys.head
  }

  val sentiment = new ClassTokenFeature(
    Map("Positive"->Set("happy","glad","jubilant","satisfied"),
      "Negative"->Set("sad","disappointed","angry","frustrated")))

  val features =  List(duration, gender, sentiment)

  def parse(rawText: String): Output = {
    rawText.split(" \n").foreach(token=>applyFeatures(token, features))
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

object CotaTestMain extends App {
  val lines = scala.io.Source.fromInputStream(getClass.getClassLoader
    .getResourceAsStream("sentences.txt")).mkString.trim.replaceAll("^[a-zA-Z0-9/]"," ").split("\r\n\r\n")

  lines.map(SentenceParser.parse).foreach(println)
}
