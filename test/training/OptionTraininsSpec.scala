package training

import scala.util.Random

import org.scalatest.{ MustMatchers, WordSpec }

// $ testOnly training.OptionTrainingSpec
class OptionTrainingSpec extends WordSpec with MustMatchers {

  "Option" must {

    "Optionがない世界" in {
      // 頭文字をそれぞれ確認したい
      val str1: String = "AAA"
      val str2: String = null
      val str3: String = "CCC"

       // そのままやるとNullPointerException(ぬるぽ)
      try {
        str1.head mustBe 'A'
        str2.head mustBe 'B'
        str3.head mustBe 'C'
      } catch {
        case e: Throwable =>
          println("ぬるぽ！😡")
      }

      // nullチェックが必要 => 忘れたときに事故りやすい
      if (str1 != null) {
        str1.head mustBe 'A'
      }
      if (str2 != null) {
        str2.head mustBe 'B' // 実行されない
      }
      if (str2 != null) {
        str3.head mustBe 'C'
      }
    }

    "Optionがある世界" in {
      // 頭文字をそれぞれ確認したい
      val strOpt1: Option[String] = Some("AAA")
      val strOpt2: Option[String] = None
      val strOpt3: Option[String] = Some("CCC")

      // そのままでは扱えないので、値チェックが仕組み化されている
      strOpt1.foreach { str1 =>
        str1.head mustBe 'A'
      }
      strOpt2.foreach { str2 =>
        str2.head mustBe 'B' // 実行されない
      }
      strOpt3.foreach { str3 =>
        str3.head mustBe 'C'
      }

      // mapをうまく使えば…
      strOpt1.map(_.head) mustBe Some('A')
      strOpt2.map(_.head) mustBe None // 値の確認もできる
      strOpt3.map(_.head) mustBe Some('C')
    }

    // 入力した値をOptionで返してくれる、たまにNoneになる
    val random = new Random
    def whimsyWrapper(number: Int): Option[Int] = {
      if (random.nextBoolean)
        Some(number)
      else
        None
    }

    /**
      * FIXME
      * - whimsyWrapperに入力した値が返ってきていることをテストしてみよう
      */
    "whimsyWrapper" in {
      (0 to 10000).foreach { number =>
        whimsyWrapper(number)
      }
    }

    "Optionの値操作" in {
      // 値があれば頭文字、値がなければ'Z'を返したい
      val strOpt1: Option[String] = Some("AAA")
      val strOpt2: Option[String] = None

      // if else
      if (strOpt1.nonEmpty)
        strOpt1.get.head
      else
        'Z'

      if (strOpt2.nonEmpty)
        strOpt2.get.head
      else
        'Z'

      // match case
      strOpt1 match {
        case Some(str) => str.head
        case None => 'Z'
      }
      strOpt2 match {
        case Some(str) => str.head
        case None => 'Z'
      }

      // map, getOrElse
      strOpt1.map(_.head).getOrElse('Z')
      strOpt2.map(_.head).getOrElse('Z')
    }

    /**
      * FIXME
      * - Optionの文字列を結合するmerge関数を完成させよう
      * - Noneが入ってきたら😡に変換すること
      */
    "Optionの値操作練習" in {
      def merge(strOpt1: Option[String], strOpt2: Option[String]): String = ???

      merge(Some("hoge"), Some("bar")) mustBe "foobar"
      merge(Some("hoge"), None) mustBe "foo😡"
      merge(None, Some("bar")) mustBe "😡bar"
      merge(None, None) mustBe "😡😡"
    }

    "Optionのまま値操作" in {
      val strOpt1: Option[String] = Some("AAA")
      val strOpt2: Option[String] = None
      val strOpt3: Option[String] = Some("CCC")

      // flatMapとmapで結合できる
      strOpt1.flatMap { str1 =>
        strOpt3.map { str3 =>
          str1 + str3
        }
      } mustBe Some("AAACCC")

      strOpt1.flatMap { str1 =>
        strOpt2.map { str2 =>
          str1 + str2
        }
      } mustBe None

      // for式
      val result1 = for {
        str1 <- strOpt1
        str3 <- strOpt3
      } yield str1 + str3
      result1 mustBe Some("AAACCC")

      val result2 = for {
        str1 <- strOpt1
        str2 <- strOpt2
      } yield str1 + str2
      result2 mustBe None
    }

    /**
      * FIXME
      * - Option内の頭文字を結合するheadMerge関数を完成させよう
      * - 引数にNoneが混じっていればNoneを返すこと
      */
    "Optionのまま値操作練習" in {
      def headMerge(
        strOpt1: Option[String],
        strOpt2: Option[String],
        strOpt3: Option[String],
        strOpt4: Option[String]
      ): Option[String] = ???

      headMerge(Some("AAA"), Some("BBB"), Some("CCC"), Some("DDD")) mustBe Some("ABCD")
      headMerge(Some("AAA"), None, Some("CCC"), None) mustBe None
    }

    /**
      * FIXME
      * - Option内の頭文字を結合するheadMerge関数を完成させよう
      * - (Extra)引数にNoneが混じっていればその文字列は無視すること
      */
    "Optionのまま値操作練習(Extra)" in {
      def headMerge(
        strOpt1: Option[String],
        strOpt2: Option[String],
        strOpt3: Option[String],
        strOpt4: Option[String]
      ): Option[String] = ???

      headMerge(Some("AAA"), Some("BBB"), Some("CCC"), Some("DDD")) mustBe Some("ABCD")
      headMerge(Some("AAA"), None, Some("CCC"), None) mustBe Some("AC")
    }
  }
}
