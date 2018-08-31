package training

import org.scalatest.{ MustMatchers, WordSpec }

// $ testOnly training.CaseMatchTrainingSpec
class CaseMatchTrainingSpec extends WordSpec with MustMatchers {

  /**
    * FIXME
    * - パターンマッチを使ってみよう
    * - (Extra)ワンライナーにしてみよう
    */
  def fizzbuzz(number: Int): String = ???

  "FizzBuzz" must {
    "return Fizz by multiples of 3" in {
      fizzbuzz(3) mustBe "Fizz"
      fizzbuzz(6) mustBe "Fizz"
    }

    "return Buzz by multiples of 5" in {
      fizzbuzz(5) mustBe "Buzz"
      fizzbuzz(10) mustBe "Buzz"
    }

    "return FizzBuzz by multiples of 3 and 5" in {
      fizzbuzz(15) mustBe "FizzBuzz"
      fizzbuzz(30) mustBe "FizzBuzz"
    }

    "return number by others" in {
      fizzbuzz(1) mustBe "1"
      fizzbuzz(2) mustBe "2"
    }
  }
}
