import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test
import org.junit.Before
import com.dima.cota._

class CotaTestTest extends AssertionsForJUnit {

  @Before def initialize() {

  }

  @Test def testDuration() {
    val testData = List(
      (0,List("10/15/2001")),
      (0,List("Asdfasdf","sdgfsdgf")),
      (0,List("10/15/2001","asfasd")),
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
    val testData = List(
      ("class1","adf asdf t1. t2 sdgfdfg t3".split(" ")),
      ("class1","adf asdf t2. t2 sdgfdfg".split(" ")),
      ("unknown","adf asdf b2, sdgfdfg t1!".split(" ")),
      ("unknown","adf asdf wer, sdgfdfg tre!".split(" ")),
      ("mixed","b;ah yyy hhh, hhh t1 b1!".split(" ")))

    testData.foreach(d=> {
      val testFeature = new ClassTokenFeature(
        Map("class1"->Set("t1","t2","t3"),
          "class2"->Set("b1","b2","b3")))
      d._2.foreach(testFeature.processToken)
      println(d._1,testFeature.output)
      assert(d._1===testFeature.output)
    })
  }
}
