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

    val where: Option[SQLSyntax] = authority.map(a => sqls"${u.authority} = $a")

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
        case Some(id) => User.find(id) match {
          case Some(user) => userForm.fill(UserForm(Some(user.id), user.name, user.email, user.authority, user.companyId))
          case None => userForm
        }
      }

      // プルダウンに表示する会社のリストを取得
      val companies = withSQL {
        select.from(Company as c).orderBy(c.id.asc)
      }.map(Company(c.resultName)).list().apply()

      Ok(views.html.user.edit(form, companies, authorities))
    }
  }

  // 登録処理の実行
  def create = Action { implicit request =>
    DB.localTx { implicit session =>
      // リクエストの内容をバインド
      userForm.bindFromRequest.fold(
        // エラーの場合
        error => {
          BadRequest(views.html.user.edit(error, Company.findAll(), authorities))
        },
        // OKの場合
        form  => {
          // ユーザを登録
          User.create(form.name, form.email, form.authority, form.companyId)
          // 一覧画面へリダイレクト
          Redirect(routes.UserController.list(None))
        }
      )
    }
  }

  // 更新処理の実行
  def update = Action { implicit request =>
    DB.localTx { implicit session =>
      // リクエストの内容をバインド
      userForm.bindFromRequest.fold(
        // エラーの場合は編集画面に戻す
        error => {
          BadRequest(views.html.user.edit(error, Company.findAll(), authorities))
        },
        // OKの場合は更新を行い一覧画面にリダイレクトする
        form => {
          // ユーザ情報を更新
          User.find(form.id.get).foreach { user =>
            User.save(user.copy(name = form.name, email = form.email, authority = form.authority, companyId = form.companyId))
          }
          // 一覧画面にリダイレクト
          Redirect(routes.UserController.list(None))
        }
      )
    }
  }

  // 削除処理の実行
  def remove(id: Long) = Action { implicit request =>
    // ユーザーの削除
    User.find(id).foreach(user => User.destroy(user))

    // 一覧画面へリダイレクト
    Redirect(routes.UserController.list(None))
  }

}


object UserController {
  // フォームの値を格納するケースクラス
  case class UserForm(id: Option[Long], name: String, email: String, authority: String, companyId: Long)

  // formから送信されたデータ ⇔ ケースクラスの変換を行う
  val userForm = Form(
    mapping(
      "id"        -> optional(longNumber),
      "name"      -> nonEmptyText(maxLength = 20),
      "email"          -> nonEmptyText(maxLength = 200),
      "authority"      -> nonEmptyText,
      "companyId" -> longNumber
    )(UserForm.apply)(UserForm.unapply)
  )

  // 併せて選択できるユーザー種別も定義します
  val authorities = Set("ADMIN", "EDITOR", "READONLY")
}