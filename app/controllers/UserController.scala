package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import javax.inject.Inject
import scalikejdbc._
import models._

class UserController @Inject()(components: MessagesControllerComponents)
  extends MessagesAbstractController(components) {

  // 一覧画面の表示
  def list(authority: Option[String]) = Action { implicit request =>
    val u = User.syntax("u")

    val where = authority.map(a => sqls"${u.authority} = $a").getOrElse(sqls"")

    val where2 = if (authority.isDefined) {
      sqls"${u.authority} = ${authority.get}"
    } else sqls""

    DB.readOnly { implicit session =>
      // ユーザのリストを取得
      val users = withSQL {
        select.from(User as u).where(where).orderBy(u.id.asc)
      }.map(User(u.resultName)).list.apply()

      // 一覧画面を表示
      Ok(views.html.user.list(users))
    }
  }

  // 編集画面の表示
  def edit(id: Option[Long]) = TODO

  // 登録処理の実行
  def create  = TODO

  // 更新処理の実行
  def update = TODO

  // 削除処理の実行
  def remove(id: Long) = TODO

}
