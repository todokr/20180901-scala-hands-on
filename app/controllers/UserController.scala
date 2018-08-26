package controllers

import com.google.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class UserController @Inject()(controllerComponents: ControllerComponents)
  extends AbstractController(controllerComponents) {

  // 一覧画面の表示
  def list = TODO

  // 編集画面の表示
  def edit(id: Option[Long]) = TODO

  // 登録処理の実行
  def create  = TODO

  // 更新処理の実行
  def update = TODO

  // 削除処理の実行
  def remove(id: Long) = TODO

}
