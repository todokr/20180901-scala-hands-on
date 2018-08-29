package controllers

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc.JdbcProfile

import models.Tables
import Tables.profile.api._

class UserController @Inject()(
  controllerComponents: ControllerComponents,
  val dbConfigProvider: DatabaseConfigProvider
) extends AbstractController(controllerComponents) with HasDatabaseConfigProvider[JdbcProfile] {

  // 一覧画面の表示
  def list(authority: Option[String]) = Action { implicit request =>
    val users = Await.result(db.run(Tables.User.sortBy(_.userId).result), Duration.Inf)

    val result = authority.map { a =>
      users.filter(_.authority == a.toUpperCase)
    }.getOrElse(users)

    Ok(views.html.user.list(result))
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
