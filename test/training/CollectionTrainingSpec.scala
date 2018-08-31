package training

import org.scalatest.{ MustMatchers, WordSpec }

// $ testOnly training.CollectionTrainingSpec
class CollectionTrainingSpec extends WordSpec with MustMatchers {

  /**
    * http://aperiodic.net/phil/scala/s-99/
    *
    * FIXME
    * - テストケースが通るように各メソッドを実装してみよう
    * - (Extra)foldLeftだけで実装してみよう
    * - (Extra)再帰処理で実装してみよう
    * - (Extra)末尾再帰で実装してみよう
    */
  "Ninety-Nine Scala Problems" must {

    def last(list: List[Int]): Int = ???

    def penultimate(list: List[Int]): Int = ???

    def nth(index: Int, list: List[Int]): Int = ???

    def length(list: List[Int]): Int = ???

    def reverse(list: List[Int]): List[Int] = ???

    def isPalindrome(list: List[Int]): Boolean = ???

    def flatten(list: List[Any]): List[Int] = ???

    def compress(list: List[Symbol]): List[Symbol] = ???

    def pack(list: List[Symbol]): List[List[Symbol]] = ???

    def encode(list: List[Symbol]): List[(Int, Symbol)] = ???

    val numList = List(1, 1, 2, 3, 5, 8)
    val symbolList = List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)

    "P01 last: Find the last element of a list" in {
      last(numList) mustBe 8
    }

    "P02 penultimate:  Find the last but one element of a list" in {
      penultimate(numList) mustBe 5
    }

    "P03 nth: Find the Kth element of a list" in {
      nth(0, numList) mustBe 1
      nth(2, numList) mustBe 2
      nth(5, numList) mustBe 8
    }

    "P04 length: Find the number of elements of a list" in {
      length(numList) mustBe 6
    }

    "P05 reverse: Reverse a list" in {
      reverse(numList) mustBe List(8, 5, 3, 2, 1, 1)
    }

    "P06 isPalindrome: Find out whether a list is a palindrome" in {
      isPalindrome(List(1, 2, 3, 2, 1)) mustBe true
      isPalindrome(List(1, 2, 3, 4, 1)) mustBe false
    }

    "P07 flatten: Flatten a nested list structure" in {
      flatten(List(List(1, 1), 2, List(3, List(5, 8)))) mustBe List(1, 1, 2, 3, 5, 8)
    }

    "P08 compress: Eliminate consecutive duplicates of list elements" in {
      compress(symbolList) mustBe List('a, 'b, 'c, 'a, 'd, 'e)
    }

    "P09 pack: Pack consecutive duplicates of list elements into sublists" in {
      pack(symbolList) mustBe List(List('a, 'a, 'a, 'a), List('b), List('c, 'c), List('a, 'a), List('d), List('e, 'e, 'e, 'e))
    }

    "P10 encode: Run-length encoding of a list" in {
      encode(symbolList) mustBe List((4,'a), (1,'b), (2,'c), (2,'a), (1,'d), (4,'e))
    }
  }
}
