package training

import java.util.Date
import java.util.concurrent.Executors

import scala.concurrent._

import org.scalatest.{ MustMatchers, WordSpec }

// $ testOnly training.FutureTrainingSpec
class FutureTrainingSpec extends WordSpec with MustMatchers {

  def doHeavyTask(id: Int) = {
    (0 until 3).foreach { _ =>
      Thread.sleep(1000)
      println(s"$id: ${new Date()}")
    }
  }

  def doFailure() = {
    throw new Exception("Failed!")
  }

  /**
    * - 処理が直列で実行される事を確認しよう
    */
  def doSequential() = {
    (1 to 10).foreach { id =>
      doHeavyTask(id)
    }
  }

  /**
    * - Threadにより処理がすべて並列で実行される事を確認しよう
    */
  def usePlainThread() = {
    (1 to 10).foreach { id =>
      new Thread {
        override def run(){
          doHeavyTask(id)
        }
      }.start()
    }
    Thread.sleep(3000)
  }

  /**
    * FIXME
    * - Futureを使って並列で実行してみよう
    */
  def useGlobal() = {
    (1 to 10).foreach { id =>
      ???
    }
    Thread.sleep(3000)
  }

  /**
    * FIXME
    * - FixedThreadPoolにより、処理が5個ずつ並列で実行される事を確認しよう
    * - FixedThreadPoolにより、処理がすべて並列で実行される事を確認しよう
    *
    * https://docs.oracle.com/javase/jp/8/docs/api/java/util/concurrent/Executors.html
    */
  def useFixedThreadPool(threadCount: Int) = {
    implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(
      ???
    )
    (1 to 10).foreach { id =>
      ???
    }
    Thread.sleep(3000)
  }

  /**
    * FIXME
    * - CachedThreadPoolにより、処理がすべて並列で実行される事を確認しよう
    *
    * https://docs.oracle.com/javase/jp/8/docs/api/java/util/concurrent/Executors.html
    */
  def useCachedThreadPool() = {
    implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(
      ???
    )
    (1 to 10).foreach { id =>
      ???
    }
    Thread.sleep(3000)
  }

  /**
    * FIXME
    * - doFailureを実行して例外となる事を確認しよう
    * - 例外が起きても処理が中断されないようにしてみよう
    * - Futureで実行すると例外が発生しても、処理が中断されていない事を確認してみよう
    * - Future内での例外をonCompleteで拾い、例外メッセージを表示してみよう
    */
  def doTaskSafety() = {
    ???
  }

  "Future" must {
    "check difference of async techniques" in {
      // doSequential()
      // usePlainThread()
      // useGlobal()
      // useFixedThreadPool(5)
      // useFixedThreadPool(10)
      // useCachedThreadPool()
      // doTaskSafety()
    }
  }
}
