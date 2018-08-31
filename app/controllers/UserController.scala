package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import javax.inject.Inject
import scalikejdbc._
import models._

class UserController @Inject()(components: MessagesControllerComponents)
  extends MessagesAbstractController(components) {

  import UserController._

  private val u = User.syntax("u")
  private val c = Company.syntax("c")

  // 一覧画面の表示
  def list(authority: Option[String]) = Action { implicit request =>

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
  def edit(id: Option[Long]) = Action { implicit request =>
    DB.readOnly { implicit session =>
      val form = id match {
        // IDが渡されなかった場合は新規登録フォーム
        case None => userForm
        // IDからユーザ情報を1件取得してフォームに詰める
        case Some(id) => User.findBy(sqls"${u.id} = $id") match {
          case Some(user) => userForm.fill(UserForm(Some(user.id), user.name, user.companyId))
          case None => userForm
        }
      }

      // プルダウンに表示する会社のリストを取得
      val companies = withSQL {
        select.from(Company as c).orderBy(c.id.asc)
      }.map(Company(c.resultName)).list().apply()

      Ok(views.html.user.edit(form, companies))
    }
  }

  // 登録処理の実行
  def create  = TODO

  // 更新処理の実行
  def update = TODO

  // 削除処理の実行
  def remove(id: Long) = TODO

}


object UserController {
  // フォームの値を格納するケースクラス
  case class UserForm(id: Option[Long], name: String, companyId: Long)

  // formから送信されたデータ ⇔ ケースクラスの変換を行う
  val userForm = Form(
    mapping(
      "id"        -> optional(longNumber),
      "name"      -> nonEmptyText(maxLength = 20),
      "companyId" -> longNumber
    )(UserForm.apply)(UserForm.unapply)
  )
}