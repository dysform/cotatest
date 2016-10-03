package com.dima.cota

object ClassMappings {
  class Genders extends Enumeration
  object Genders extends Enumeration{
    val Male = Value("male")
    val Female = Value("female")
  }

  class Sentiments extends Enumeration
  object Sentiments extends Enumeration {
    val Positive = Value("positive")
    val Negative = Value("negative")
  }

  def genderMappings = Map(
    Genders.Male->Set("he","his"),
    Genders.Female->Set("she","her"))

  def sentimentMappings = Map(
    Sentiments.Positive->Set("happy","glad","jubilant","satisfied"),
    Sentiments.Negative->Set("sad","disappointed","angry","frustrated"))
}