package training

import org.scalatest.{ MustMatchers, WordSpec }

// $ testOnly training.CaseClassTrainingSpec
class CaseClassTrainingSpec extends WordSpec with MustMatchers {

  /**
    * FIXME
    * - Branch, Leaf
    * - size, max, min, sum, avgを実装してテストケースも書いてみよう
    * - (Extra)findを実装してみよう
    */
  trait Node
  case class Branch() extends Node
  case class Leaf() extends Node

  case class BTree(node: Node) {
    // すべてのNode数
    def size: Int = ???

    // すべてのNodeが持つ値の最大
    def max: Int = ???

    // すべてのNodeが持つ値の最小
    def min: Int = ???

    // すべてのNodeが持つ値の合計
    def sum: Int = ???

    // すべてのNodeが持つ値の平均
    def avg: Int = ???

    // 指定した値を持つNodeを返す
    def find(number: Int): Option[Node] = ???
  }

  "BTree" must {

    "apply" in {
      ???
    }

    "size" in {
      ???
    }

    "max" in {
      ???
    }

    "min" in {
      ???
    }

    "sum" in {
      ???
    }

    "avg" in {
      ???
    }

    "find" in {
      ???
    }
  }
}
