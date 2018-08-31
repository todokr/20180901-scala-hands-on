# Scala Hands-on
### 現場で活きるスキルをCRUDアプリケーション開発で学ぶ

---

# 今日のゴール

- Scala + PlayFrameworkでCRUDアプリケーションが作れるようになる
- Scalaの関数型言語としてのエッセンスと、その嬉しさを理解する

---

# 自己紹介タイム

参加者全員でお互いに自己紹介をしましょう👍

---

## 岩松 竜也

- 2015年新卒入社

---

## 田所 駿佑

- 求人検索エンジン「スタンバイ」 プロダクトオーナー見習い
- 2015年新卒入社
- PlayFramework, ScalikeJDBC コントリビューター
- 「クローリングハック」(翔泳社) 共著

---

# 自己紹介タイム

参加者全員でお互いに自己紹介をしましょう👍

- お名前を教えてください
- 普段はどんなプログラムを書いていますか？
- 好きな言語はありますか？
- 「今日はこういうことについても聴いてみたい」という要望はありますか？

---

# よろしくお願いします👍

---

# 今回作るアプリケーション
ユーザ情報のCRUDを行う簡単なアプリケーションを作成します。

- ユーザ一覧を表示する
- 新規ユーザ登録を行う
- ユーザ情報を編集する
- ユーザを削除する

また、後半ではこのアプリケーションと同じCRUD処理を行うJSONベースのWeb APIも作成します。

---

# 今回作るアプリケーション
![Flow](slide/flow.png)

---

# WAF & DB Access

## Web Application Framework
PlayFramework 2.6.x

## DB Access Library
ScalikeJDBC 3.2.x

---

# プロジェクトの準備(1)
Scalaの基本文法の前に、予めプロジェクトを準備しておきましょう。
ご自身のターミナルで以下のコマンドを実行してください。

```sh
sbt new playframework/play-scala-seed.g8 --branch 2.6.x
```

---

# プロジェクトの準備(2)

プロジェクト名などを聞かれますが、今回はプロジェクト名を`play2-hands-on`とし、他の項目は初期値のままプロジェクトを作成します。

```sh
This template generates a Play Scala project

name [play-scala-seed]: play2-hands-on
organization [com.example]:
play_version [2.6.18]:
sbt_version [1.2.1]:
scalatestplusplay_version [3.1.2]:

Template applied in ./play2-hands-on
```

---

# プロジェクトの準備(3)

作成したプロジェクトのディレクトリに移動し、 `sbt` コマンドを実行し、sbtシェルを起動します。  
sbtシェルが起動したら `run` でアプリケーションを起動します。
プロジェクトの起動に必要な各種ツールやJarがダウンロードされ、アプリケーションが起動されます。

```sh
cd play2-hands-on
sbt
run
```

# プロジェクトの準備(4)

> **POINT**
>
> * `run`で実行している間はホットデプロイが有効になっているため、ソースを修正するとすぐに変更が反映されます
> * CTRL+Dで`run`での実行を終了し、sbtシェルに戻ることができます
> * `run`で実行中に何度も修正を行っているとヒープが不足してプロセスが終了してしまったりエラーが出たまま応答がなくなってしまう場合があります
> * プロセスが終了してしまった場合は再度`sbt`と`run`を実行してください
> * 応答しなくなってしまった場合は一度ターミナルを閉じ、再度起動して`sbt`と`run`を実行してください

初回の起動にはしばらく時間がかかるので、その間にScalaの基本文法について学びます👍

---

# Scalaのざっくり解説

---

# Scalaの基本文法

---

# 作成したPlayプロジェクトの確認

- ブラウザから`localhost:9000`にアクセスしてみましょう
- `Welcome to Play!` と表示されていれば準備はOKです！

---

# IntelliJにプロジェクトをインポート(1)
IntelliJでPlayプロジェクトを開発するために、まずはプロジェクトをインポートします。  
「Import Project」をクリックし、Play2プロジェクトのルートディレクトリを選択し、「Open」します。

![import](slide/import.png)

---

# IntelliJにプロジェクトをインポート(2)
SBTプロジェクトとしてインポートします。

![import](slide/import2.png)

---

# IntelliJにプロジェクトをインポート(3)
インポートする際に以下のダイアログが表示されます。初回は「Project JDK」が未選択の状態になっているかもしれません。「New…」をクリックしてJDKがインストールされているディレクトリを選択してから「OK」をクリックしてください。

![import](slide/import3.png)

---

# IntelliJとbuild.sbt
- IntelliJはbuild.sbtの設定を元にプロジェクトをインポートします
- build.sbtを編集した場合は、 `Refresh project` でプロジェクトの再インポートを行う必要があります
- または、「Enable auto-import」を選択するとbuild.sbtを変更するたびに自動的に再インポートされるようになります

---

# build.sbtを編集してみよう
試しにbuild.sbtを編集してみましょう。
まずはMySQLのJDBCドライバを依存関係に追加してみます。
build.sbtを下記のように変更してください。

```
libraryDependencies += guice
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.47" // 追加
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
```

---

# build.sbtを編集してみよう
先程の例は `libraryDependencies +=` が何度も出てきました。これは下記のように書き換えることもできます。

```
libraryDependencies ++= Seq(
  guice,
  "mysql" % "mysql-connector-java" % "5.1.47",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
)

```
@[1](libraryDependencies ++= Seq({依存ライブラリをカンマ区切りで}))

---

# 早速つくりはじめよう

---

# Bootstrapを使う準備(1)
`sbt new` コマンドで作成されたプロジェクトには、デフォルトのレイアウトテンプレートとして `app/views/main.scala.html` が生成されています。ここにBootstrapで使用するCSSとJavaScriptを追加します。

```html
<link rel="stylesheet" media="screen" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<link rel="stylesheet" media="screen" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
```

---

# Bootstrapを使う準備(2)
追加したら `localhost:9000` をリロードしてみましょう。 JavaScriptコンソールにエラーが出ているかと思います。
これはPlayが提供している「セキュリティヘッダフィルタ」が、HTTPレスポンスに「同一生成元ではない」リソースを無効にするヘッダ(`Content-Security-Policy`)を付与しているためです。

- [Playのセキュリティヘッダフィルタについてのドキュメント](https://www.playframework.com/documentation/2.6.x/SecurityHeaders)
- [同一生成元ポリシーについて](https://developers.google.com/web/fundamentals/security/csp/)

---

# Bootstrapを使う準備(2)
CDNから配信されるリソースを利用するためには、CDNのドメインを「この配信元は信用できる」というホワイトリストに追加する必要があります。
`conf/application.conf` に下記の一行を追加します。

```
play.filters.headers.contentSecurityPolicy = "script-src 'self' netdna.bootstrapcdn.com cdnjs.cloudflare.com"
```

これでBootstrapを使う準備は完了です。

---

# Controllerの雛形を作る
ではさっそくアプリケーションを作っていきます。まずはControllerの雛形を用意します。

```scala
package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import javax.inject.Inject

class UserController @Inject()(components: MessagesControllerComponents)
  extends MessagesAbstractController(components) {

  // 一覧画面の表示
  def list = TODO

  // 編集画面の表示
  def edit(id: Option[Long]) = TODO

  // 登録処理の実行
  def create = TODO

  // 更新処理の実行
  def update = TODO

  // 削除処理の実行
  def delete(id: Long) = TODO
}
```
@[8](PlayFrameworkではControllerをclassとして実装します)
@[8](@Injectアノテーションと2つの引数が定義されていますが、これはPlay 2.4から導入されたGoogle GuiceによるDI機能を使用するためのもの)
@[9](ControllerとなるClassは `MessagesAbstractController` を継承します)
@[9](MessagesControllerComponents: Playの国際化機能を使用するために必要)
@[12](各ActionはScalaのメソッドとして定義します)
@[12](`TODO` はPlayが提供している開発用お役立ちメソッドで、未実装なActionを表します)


---

# ルーティングの雛形を作る

クライアントから送信されたリクエストは、 `conf/routes` の設定に従ってコントローラのメソッドへルーティングされます。
以下の設定を追記します。

```
# Mapping to /user/list
GET     /user/list                  controllers.UserController.list

# Mapping to /user/edit or /user/edit?id=<number>
GET     /user/edit                  controllers.UserController.edit(id: Option[Long] ?= None)

# Mapping to /user/create
POST    /user/create                controllers.UserController.create

# Mapping to /user/update
POST    /user/update                controllers.UserController.update

# Mapping to /user/remove/<number>
POST    /user/remove/:id            controllers.UserController.remove(id: Long)
```
@[1](`#` からはじまる行はコメント)
@[2](`/user/list` へのGETリクエストは `controllers.UserController` クラスの `list` メソッドが処理する)
@[4,5](`id` というクエリパラメータを受け取る。クエリパラメータの型は `Option[Long]` で、デフォルトはNone)

---

# アクセスしてみよう
- `localhost:9000/user/list` にアクセスしてみましょう
- 未実装であることを示す紫色の画面が表示されればOKです

---

# ここまでのまとめ

- ControllerはAbstractControllerを継承したclassとして実装する
- 各Actionはメソッドとして実装する
- 未実装のActionは `TODO` としておける
- ルーティング設定は `conf/routes` で行う
  
---

# MySQLの準備をする(1)
まずはユーザー一覧画面を実装していきましょう。しかしその前にデータベースを用意する必要があります。

---

# MySQLの準備をする(2)
今回はMySQLをDockerから使います。プロジェクトのルートディレクトリに下記のような `docker-compose.yml` を用意します。

```yaml
version: '2.0'

services:
    mysql:
        container_name: play2-hands-on_mysql
        image: mysql/mysql-server:5.7
        ports:
            - "3306:3306"
        command: [
        "mysqld",
        "--character-set-server=utf8mb4",
        "--lower_case_table_names=1",
        "--sql_mode=TRADITIONAL",
        "--explicit_defaults_for_timestamp=ON"
        ]
        environment:
            MYSQL_ALLOW_EMPTY_PASSWORD: 1
        volumes:
            - ./mysql/:/docker-entrypoint-initdb.d

```
@[6](dockerhubの公式のMySQLのイメージを使う。versionは5.7)
@[19](MySQLの初回起動時に実行するSQLを指定。この場合、 `mysql/` 以下のSQLを実行する)

---

# MySQLの準備をする(3)
次に、MySQLの初回起動時に実行するSQLを用意します。
下記のような `mysql/init.sql` というSQLファイルを作成します。

```sql
charset utf8mb4;

CREATE USER 'root'@'%' IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';

CREATE SCHEMA IF NOT EXISTS `play2_hands_on` DEFAULT CHARACTER SET utf8mb4;
USE `play2_hands_on` ;

-- -----------------------------------------------------
-- Table `COMPANY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_unique` ON `company` (`id` ASC);


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `authority` VARCHAR(45) NOT NULL,
  `company_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`, `company_id`),
  CONSTRAINT `fk_user_company`
    FOREIGN KEY (`company_id`)
    REFERENCES `company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_unique` ON `user` (`id` ASC);

CREATE INDEX `fk_user_company_idx` ON `user` (`company_id` ASC);

-- -----------------------------------------------------
-- Data for table `company`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `company` (`id`, `name`) VALUES (1, '株式会社AAA');
INSERT INTO `company` (`id`, `name`) VALUES (2, 'BBBコーポレーション');

COMMIT;

-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `user` (`id`, `name`, `email`, `authority`, `company_id`) VALUES (1, '田中 太郎', 'tanaka@example.com', 'ADMIN', 1);
INSERT INTO `user` (`id`, `name`, `email`, `authority`, `company_id`) VALUES (2, '鈴木 次郎', 'suzuki@example.com', 'READONLY', 1);
INSERT INTO `user` (`id`, `name`, `email`, `authority`, `company_id`) VALUES (3, '佐藤 三郎', 'sato@example.com', 'EDITOR', 1);
INSERT INTO `user` (`id`, `name`, `email`, `authority`, `company_id`) VALUES (4, '藤原 四郎', 'fujiwara@example.com', 'EDITOR', 2);
INSERT INTO `user` (`id`, `name`, `email`, `authority`, `company_id`) VALUES (5, '野口 五郎', 'noguchi@example.com', 'READONLY', 2);

COMMIT;
```

---

# MySQLの準備をする(4)
下記のようにSQLが叩けるようになったら準備OKです👍

```
mysql -h '127.0.0.1' -uroot -p
Enter password: (Enterを押す)

mysql> select count(*) from play2_hands_on.user;
+----------+
| count(*) |
+----------+
|        5 |
+----------+
1 row in set (0.00 sec)

```

---

# DBアクセスライブラリ「ScalikeJDBC」を導入する
MySQLの準備ができたら、次はDBアクセスライブラリであるScalikeJDBCを依存関係に追加しましょう。
`build.sbt` のlibraryDependenciesを下記のように編集してください。

```

libraryDependencies ++= Seq(
  guice,
  "mysql" % "mysql-connector-java" % "5.1.47",
  "org.scalikejdbc" %% "scalikejdbc" % "3.2.2", // 追加
  "org.scalikejdbc" %% "scalikejdbc-config" % "3.2.2", // 追加
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.6.0-scalikejdbc-3.2", // 追加
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
)
```

---

# Modelを自動生成する(1)
ScalikeJDBCではタイプセーフなAPIを使用するためにモデルクラスを用意する必要がありますが、ScalikeJDBCがsbtプラグインとして提供しているジェネレータを使用することでモデルクラスをDBスキーマから自動生成することができます。

まずはsbtプラグインを依存関係に追加します。  
`project/plugins.sbt` に以下の設定を追加してください。

```
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.47"
addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "3.2.2")
```

---

# Modelを自動生成する(2)

続いて、 `project/scalikejdbc.properties` というファイルを以下の内容で作成します。

```
# ---
# jdbc settings

jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/play2_hands_on?useSSL=false&nullNamePatternMatchesAll=true
jdbc.username=root
jdbc.password=
jdbc.schema=

# ---
# source code generator settings

generator.packageName=models
# generator.lineBreak: LF/CRLF
generator.lineBreak=LF
# generator.template: interpolation/queryDsl
generator.template=queryDsl
# generator.testTemplate: specs2unit/specs2acceptance/ScalaTestFlatSpec
generator.testTemplate=ScalaTestFlatSpec
generator.encoding=UTF-8
# When you're using Scala 2.11 or higher, you can use case classes for 22+ columns tables
generator.caseClassOnly=true
# Set AutoSession for implicit DBSession parameter's default value
generator.defaultAutoSession=true
# Use autoConstruct macro (default: false)
generator.autoConstruct=false
# joda-time (org.joda.time.DateTime) or JSR-310 (java.time.ZonedDateTime java.time.OffsetDateTime)
generator.dateTimeClass=java.time.OffsetDateTime
```

---

# Modeを自動生成する(3)
最後に `build.sbt` に以下の記述を追加します。これでModeの自動生成を行うscalikejdbcGenタスクが使用できるようになります。

```
enablePlugins(ScalikejdbcPlugin)
```

---

# Modelを自動生成する(4)
ではコードを自動生成してみましょう。`play2-hands-on`プロジェクトのルートディレクトリで以下のコマンドを実行します。

```sh
sbt "scalikejdbcGenAll"
```

するとplay2-hands-onプロジェクトの `app/models` パッケージに `Company` と `User` の2つのモデルクラスが生成されます。

---

# DB接続の設定
`play2-hands-on` プロジェクトの `conf/application.conf` に以下の設定を追加します。  
データベースの接続情報に加え、PlayとScalikeJDBCを連携させるための設定が含まれています。

```

db.default.driver=com.mysql.jdbc.Driver
db.default.url="jdbc:mysql://127.0.0.1:3306/play2_hands_on?useSSL=false&nullNamePatternMatchesAll=true"
db.default.username=root
db.default.password=""

scalikejdbc.global.loggingSQLAndTime.enabled=true
scalikejdbc.global.loggingSQLAndTime.singleLineMode=false
scalikejdbc.global.loggingSQLAndTime.logLevel=debug
scalikejdbc.global.loggingSQLAndTime.warningEnabled=true
scalikejdbc.global.loggingSQLAndTime.warningThresholdMillis=5
scalikejdbc.global.loggingSQLAndTime.warningLogLevel=warn

play.modules.enabled += "scalikejdbc.PlayModule"
# scalikejdbc.PlayModule doesn't depend on Play's DBModule
play.modules.disabled += "play.api.db.DBModule"
```

---

# ここまでのまとめ

- ScalikeJDBCはScalaのDBアクセスライブラリ
- scalikejdbcGenAllタスクでDBのスキーマからModelを自動生成できる

---

# ユーザー一覧画面を実装する(1)
modelが生成できたので、ユーザー一覧画面を実装しましょう。
テンプレートはviewsパッケージに作成します。`app` ディレクトリ配下に `views.user` パッケージを作成し、以下の内容で `list.scala.html` を作成します。

```html
@* このテンプレートの引数 *@
@(users: Seq[models.User])(implicit request: RequestHeader)

@* テンプレートで利用可能なヘルパーをインポート *@
@import helper._

@* main.scala.htmlを呼び出す *@
@main("ユーザ一覧") {

<div>
  <a href="@routes.UserController.edit()" class="btn btn-success" role="button">新規作成</a>
</div>

<div class="col-xs-6">
  <table class="table table-hover">
    <thead>
      <tr>
        <th>ID</th>
        <th>名前</th>
        <th>&nbsp;</th>
      </tr>
    </thead>
    <tbody>
    @* ユーザの一覧をループで出力 *@
    @users.map { user =>
      <tr>
        <td>@user.id</td>
        <td><a href="@routes.UserController.edit(Some(user.id))">@user.name</a></td>
        <td>@helper.form(CSRF(routes.UserController.remove(user.id))){
          <input type="submit" value="削除" class="btn btn-danger btn-xs"/>
        }
        </td>
      </tr>
    }
    </tbody>
  </table>
</div>

}
```
@[1](`@* ... *@`で囲まれな内容はコメントです)
@[2](テンプレートの最初にはコントローラから受け取る引数を記述します)
@[5](@importでインポート文を記述することができます。@import helper._でPlayが提供する標準ヘルパー（フォームなどを出力する関数）をインポートしています)
@[11](リンクやフォームのURLは、@routes.・・・と記述することでルーティングから生成することができます)
@[29](デフォルトでCSRFフィルタが有効になっているため、フォームの送信先はCSRF(...)で囲む必要があります)

---

# ユーザー一覧画面を実装する(2)
アプリケーションの設計としてはあまり良くありませんが、まずはControllerにすべてのロジックを書いてしまいます。

```scala
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

    val dbioAction: DBIO[Seq[UserRow]] = User.sortBy(_.userId).result
    val futureResult: Future[Seq[UserRow]] = db.run(dbioAction)
    val users: Seq[UserRow] = Await.result(futureResult, Duration.Inf)

    Ok(views.html.user.list(users))
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
```
@[27](DBにどのように問い合わせるかを定義。 `UserRow` はuserテーブルの1レコードを表す)
@[28](DBに実際に問い合わせを実行。返り値の型はFuture)
@[29](`Await.result()` でFutureが完了するのを待ち、結果を `UserRow` のコレクションとして取得)

---

# ユーザー一覧画面にアクセスしてみる
再度、 `http://localhost:9000/user/list` にアクセスしてみましょう。下記のように一覧画面が表示されればOKです👍

![ユーザー一覧](slide/user-list.png)

---

# ここまでのまとめ
- Slickは「どのようにDBに問い合わせるか」と「実際のDBへの問い合わせ」を分離している
  - `DBIO` は「DBにどのように問い合わせるか」を表す型
  - `db.run(...)` は実際のDBへの問い合わせ
  - 実はここに関数型のエッセンスが隠れている...！が詳しくは後ほど
- `db.run(...)` の返り値は `Future` で、これは「引換券」のようなもの。これも後ほど詳しく
- DBアクセスをするためには `DatabaseConfigProvider` のインスタンスを取得し、`HasDatabaseConfigProvider` をミックスインする

---

# 機能を追加してみよう
一覧ページに、ユーザー種別でフィルターする機能を追加しましょう。
そのためにまず「コレクション操作」を学びます👍

---

# コレクション操作に入門する
- map, filter, collect
(いわまっちゃんお願いします)

---

# ユーザー種別でフィルターしてみよう
DBのuserテーブルのAUTHORITYカラムには「ADMIN」「EDITOR」「READONLY」の3種類のユーザー種別があります。
アプリケーションを改修し、一覧画面において特定のユーザーだけを表示できるようにしてみましょう。

具体的には、
- `http://localhost/user/list?authority=admin` のときは、「ADMIN」のユーザーだけの一覧を表示
- `http://localhost/user/list?authority=editor` のときは、「EDITOR」のユーザーだけの一覧を表示
- `http://localhost/user/list?authority=readonly` のときは、「READONLY」のユーザーだけの一覧を表示
- クエリパラメータがないときは、全ユーザー種別の一覧を表示

とします。

---

# クエリパラメータをControllerで受け取れるようにする(1)
フィルタリングを行うためには、 `http://localhost/user/list?authority=admin` の `?authority=admin` のようにクエリパラメータとして渡している情報をControllerで受け取れるようにする必要があります。
まずは `conf/route` のユーザー一覧用ルーティングを下記のように修正します。

```
# Mapping to /user/list
GET  /user/list  controllers.UserController.list(authority: Option[String] ?= None)
```

---

# クエリパラメータをControllerで受け取れるようにする(2)
続いてControllerのlistメソッドに引数ブロックを追加します。

```scala
// 一覧画面の表示
  def list(authority: Option[String]) = Action { implicit request =>
```

これで `?authority=XXX` とリクエストが来た場合に、Controllerで `XXX` を受け取れるようになります。

---

# Option[String]の謎を解く
`Option[String]` という見慣れない型について見ていきましょう。  
`?authority=XXX` のクエリパラメータはURLに含まれることもあれば、含まれないこともあります。
「存在するかもしれないし、しないかもしれない」を表現する必要があるわけです。

---

# ないときはnull
- Javaでよくあるのは「ないときはnull」
- しかし、これはバグの元
- 呼び出し元はnullチェック必要?必要じゃない?
- NullPointerException

---

# 「あるかもしれないし、ないかもしれない」を表す型がOption
- Optionとは「あるかもしれないし、ないかもしれない」を表す型
- どのように使うのか、なぜうれしいのかを練習問題で感じてみよう！

---

# Optionに入門する
(いわまっちゃんお願いします)

---

# 受け取ったクエリパラメータでユーザーをフィルタリングする
Controllerが受け取った `authority` がSomeなら中身の文字列を使ってフィルタリング、
Noneならフィルタリングしないそのままの結果を使うように改修してみましょう。

```scala
  // 一覧画面の表示
  def list(authority: Option[String]) = Action { implicit request =>
    val users = Await.result(db.run(User.sortBy(_.userId).result), Duration.Inf)

    val result = if (authority.isDefined) { 
      users.filter(_.authority == authority.get.toUpperCase)
    } else {
      users
    }

    Ok(views.html.user.list(result))
  }

```

---

# フィルタリング処理をリファクタしてみよう
`Option#get` はNoneに対して呼んでしまうと `java.util.NoSuchElementException` の例外が発生します。Noneに対するgetのエラーはコンパイル時に発見できないため、なるべく避けるのがScalaらしいスタイルです。  
大抵は `Option#map` と `Option#getOrElse` の組み合わせでgetを撲滅することができます。

```scala
val result = authority.map { a =>
  // authorityがSomeのときはその値を使ってフィルタリングする
  users.filter(_.authority == a.toUpperCase)
}.getOrElse(users)
```
---

# Slickでフィルタリングしよう(1)
ここまでの実装では、DBからユーザーを全件取得したうえで、アプリケーション側でフィルタリングを行っていました。ユーザー数が少ないうちは良いですが、取得する件数が多くなるとアプリケーション側に大きな負荷がかかってしまいます。  

---

# Slickでフィルタリングしよう(2)
Slickが生成するSQLの `where` 句でユーザーを絞り込むようにしてみましょう。  
Slickは極力Scalaのコレクションと同じように操作できるように設計されています。

```scala
// 一覧画面の表示
def list(authority: Option[String]) = Action { implicit request =>
  val dbAction = authority.map { a =>
    // authorityがSomeのときはその値を使ってフィルタリングする
    User.filter(_.authority === a.bind).sortBy(_.userId).result
  }.getOrElse(User.sortBy(_.userId).result)

  val result = Await.result(db.run(dbAction), Duration.Inf)

  Ok(views.html.user.list(result))
}
```

---

# Slickでフィルタリングしよう(3)

```scala
User.filter(_.authority === a.bind).sortBy(_.userId).result
```

- コレクション操作と同じように `filter` ができます
- `===` は比較のためのSlickのメソッドです
- `bind` をつけるとバインド変数になります

---

# バインド変数にする目的

- prepared statementによるパフォーマンス向上
- SQLインジェクションの防止

詳しくはこちらの記事がおすすめ: https://www.ibm.com/developerworks/jp/security/library/se-bindvariables/index.html

---

# ここまでのまとめ


---

# build.sbtを編集してみよう
さらに依存関係を追加してみましょう。
jQueryとBootstrapを依存関係に追加します。

```
libraryDependencies ++= Seq(
  guice,
  "mysql" % "mysql-connector-java" % "6.0.6",
  "org.webjars" % "jquery" % "3.3.1-1", // 追加
  "org.webjars" % "bootstrap" % "4.1.3", // 追加
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
)

```

# WebJar
- jQueryやBootstrapといったフロントエンドのライブラリをJAR(Java Archive)としてパッケージングして提供している
- フロントエンドのライブラリの依存関係が、Javaのライブラリと同様に管理できるようになる
- SPAとかやるならnpm/yarn使った方がいいかと思うけど、jQuery程度ならこちらの方法が楽
