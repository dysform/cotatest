import org.scalatest.junit.JUnitSuite
import org.junit.Test
import com.dima.cota._
import com.dima.cota.ClassMappings._
import com.dima.cota.CotaTestApi.Output
import com.dima.cota.CotaTestApi.Input

import spray.json._
import fommil.sjs.FamilyFormats._

class CotaTestTest extends JUnitSuite  {

  @Test def testDuration() {
    val testData = List(
      (1,List("10/15/2001")),
      (0,List("Asdfasdf","sdgfsdgf")),
      (1,List("10/15/2001","asfasd")),
      (7,List("10/15/2001","10/21/2001")),
      (7,List("10/21/2001","10/15/2001")),
      (8,List("10/22/2001","10/15/2001")),
      (7,List("10/15/2001","10/16/2001","10/21/2001")),
      (7,List("10/16/2001","10/15/2001","10/21/2001")),
      (7,List("blah","10/21/2001","10/15/2001","10/16/2001")))

    testData.foreach(r=> {
      val duration = new MyDuration
      r._2.foreach(duration.processToken)
      assert(r._1===duration.output)
    })
  }

  @Test def testClassTokenFeature() {
    class ClassTypes extends Enumeration
    object ClassTypes extends Enumeration {
      val classa = Value("classa")
      val classb = Value("blassb")
    }

    val testData = List(
      ("unknown","adf asdf wer sdgfdfg tre".split(" ")),
      (ClassTypes.classa.toString,"adf asdf a1 a2 sdgfdfg a3".split(" ")),
      (ClassTypes.classa.toString,"adf asdf a2 a2 sdgfdfg".split(" ")),
      (ClassTypes.classb.toString,"adf asdf b2 b3 sdgfdfg".split(" ")),
      ("mixed","adf asdf b2 sdgfdfg a1".split(" ")),
      ("mixed","blah yyy hhh hhh a1 b1".split(" ")))


    val classMappings = Map(
      ClassTypes.classa->Set("a1","a2","a3"),
      ClassTypes.classb->Set("b1","b2","b3"))

    testData.foreach(d=> {
      val testFeature = new ClassTokenFeature(classMappings)
      d._2.foreach(testFeature.processToken)
      assert(d._1===testFeature.output)
    })
  }

  @Test def testSentenceParser() = {
    val testData =List(
      (Output(20,Genders.Male.toString,Sentiments.Positive.toString),"Alex started *his day happy, on '01/01/2000' he ate ice cream and on - 01/20/2000 he won the lottery"),
      (Output(1,Genders.Male.toString,Sentiments.Negative.toString),"Alex ended his day very disappointed, he ate ice cream and on (01/20/2000), he lost a lot of money"),
      (Output(0,Genders.Female.toString,Sentiments.Positive.toString),"Alex was satisfied, she ate cake!"),
      (Output(8781,Genders.Female.toString,Sentiments.Negative.toString),"Alex was Frustrated, she ate cake! 01/01/2000, 01/02/2005, 12/19/1980"),
      (Output(0,"unknown","mixed"),"Alex was angry and jubilant at the same time, he shouted, while her sign expression was up 00/12/0000")
    )

    testData.map(d=>(d._1,TextAnaliser.analyse(d._2))).foreach(o=>assert(o._1===o._2))
  }

  @Test def apiTest() = {
    val paragraphs = io.Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("paragraphs.txt"))
      .mkString.split("\r\n\r\n")

    val testData = List(
      (Output(8,"male","mixed").toJson.compactPrint,Input(paragraphs(0)).toJson.compactPrint),
      (Output(6857,"female","positive").toJson.compactPrint,Input(paragraphs(1)).toJson.compactPrint))

    testData.foreach(testPair=>
      assert(testPair._1===CotaTestApi.analyseText(testPair._2).parseJson.convertTo[Output].toJson.compactPrint)
    )
  }
}
