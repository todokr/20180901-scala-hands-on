package generator


import scala.concurrent.Await
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.Duration

import slick.codegen.SourceCodeGenerator
import slick.jdbc.meta.MTable
import slick.model.Model

object SlickModelGen extends App {

  // 接続先
  val url = "jdbc:mysql://127.0.0.1/play2_hands_on?useSSL=false&nullNamePatternMatchesAll=true"
  // 出力するディレクトリ
  val outputDir = "app"
  // 出力するパッケージ
  val pkg = "models"
  // traitの名前
  val topTraitName = "Tables"
  // ファイル名
  val scalaFileName = "Tables.scala"
  // 生成するテーブルを指定、今回は全テーブルModelを作成するのでNone
  val tableNames: Option[Seq[String]] = None

  val slickProfile = "slick.jdbc.MySQLProfile"
  val profile = slick.jdbc.MySQLProfile
  val db = profile.api.Database.forURL(url, driver = "com.mysql.cj.jdbc.Driver", user = "root", password = "")

  try {
    import scala.concurrent.ExecutionContext.Implicits.global
    val mTablesAction = MTable.getTables
    val allModels = Await.result(db.run(profile.createModel(Some(mTablesAction), false)(ExecutionContext.global).withPinnedSession), Duration.Inf)

    new SourceCodeGeneratorEx(allModels).writeToFile(slickProfile, outputDir, pkg, topTraitName, scalaFileName)
  } finally db.close

  class SourceCodeGeneratorEx(model: Model) extends SourceCodeGenerator(model) {
    override def Table = new Table(_) {
      //auto_incrementを識別できるようにする
      //生成されるモデルはOption型になる
      override def autoIncLastAsOption = true
      override def Column = new Column(_) {
        override def rawType = model.tpe match {
          case "java.sql.Blob" =>
            "Array[Byte]"
          case _ =>
            super.rawType
        }
      }
    }
  }
}