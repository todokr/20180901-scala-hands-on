package controllers

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import javax.inject.Inject
import scalikejdbc._
import models._

class JsonController @Inject()(components: ControllerComponents)
  extends AbstractController(components) {

  import JsonController._

  // 一覧表示
  def list = Action { implicit request =>
    val u = User.syntax("u")

    DB.readOnly { implicit session =>
      // ユーザのリストを取得
      val users = withSQL {
        select.from(User as u).orderBy(u.id.asc)
      }.map(User(u.resultName)).list.apply()

      // ユーザの一覧をJSONで返す
      Ok(Json.obj("users" -> users))
    }
  }

  // ユーザー登録
  def create = Action(parse.json) { implicit request =>
    request.body.validate[UserForm].map { form =>
      // OKの場合はユーザを登録
      DB.localTx { implicit session =>
        User.create(form.name, form.email, form.authority, form.companyId)
        Ok(Json.obj("result" -> "success"))
      }
    }.recoverTotal { e =>
      // NGの場合はバリデーションエラーを返す
      BadRequest(Json.obj("result" -> "failure", "error" -> JsError.toJson(e)))
    }
  }

  // ユーザー更新
  def update = Action(parse.json) { implicit request =>
    request.body.validate[UserForm].map { form =>
      // OKの場合はユーザ情報を更新
      DB.localTx { implicit session =>
        User.find(form.id.get).foreach { user =>
          User.save(user.copy(name = form.name, email = form.email, authority = form.authority, companyId = form.companyId))
        }
        Ok(Json.obj("result" -> "success"))
      }
    }.recoverTotal { e =>
      // NGの場合はバリデーションエラーを返す
      BadRequest(Json.obj("result" -> "failure", "error" -> JsError.toJson(e)))
    }
  }


  // ユーザー削除
  def remove(id: Long) = Action { implicit request =>
    DB.localTx { implicit session =>
      // ユーザーを削除
      User.find(id).foreach { user =>
        User.destroy(user)
      }
      Ok(Json.obj("result" -> "success"))
    }
  }
}

object JsonController {
  // UsersをJSONに変換するためのWritesを定義
  implicit val userWrites: Writes[User] = (
    (__ \ "id"       ).write[Long]   and
    (__ \ "name"     ).write[String] and
    (__ \ "email"    ).write[String] and
    (__ \ "authority").write[String] and
    (__ \ "companyId").write[Long]
  )(unlift(User.unapply))

  // ユーザ情報を受け取るためのケースクラス
  case class UserForm(id: Option[Long], name: String, email: String, authority: String, companyId: Long)

  // JSONをUserFormに変換するためのReadsを定義
  implicit val userFormReads: Reads[UserForm] = (
    (__ \ "id"       ).readNullable[Long] and
    (__ \ "name"     ).read[String]       and
    (__ \ "email"    ).read[String]       and
    (__ \ "authority").read[String]       and
    (__ \ "companyId").read[Long]
  )(UserForm)
}