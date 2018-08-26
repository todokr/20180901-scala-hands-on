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
- PlayFramework コントリビューター
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

---

# 今回作るアプリケーション
![Flow](slide/flow.jpg)

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

作成したプロジェクトのディレクトリに移動し、`sbt run`コマンドを実行します。
プロジェクトの起動に必要な各種ツールやJarがダウンロードされ、アプリケーションが起動されます。

```sh
cd play2-hands-on
sbt run
```

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

# IntelliJにプロジェクトをインポート
IntelliJでPlayプロジェクトを開発するために、まずはプロジェクトをインポートします。
講師と一緒にやってみましょう！

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
libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.6" // 追加
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
```

---

# build.sbtを編集してみよう
先程の例は `libraryDependencies +=` が何度も出てきました。これは下記のように書き換えることもできます。

```
libraryDependencies ++= Seq(
  guice,
  "mysql" % "mysql-connector-java" % "6.0.6",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
)

```
@[1](libraryDependencies ++= Seq({依存ライブラリをカンマ区切りで}))

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

import com.google.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class UserController @Inject()(controllerComponents: ControllerComponents)
  extends AbstractController(controllerComponents) {

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
@[6](`@Inject` アノテーションは、Dependency Injectionのためのもの)
@[7](ControllerとなるClassは `AbstractController` を継承します)
@[10](各ActionはScalaのメソッドとして定義します)
@[10](`TODO` はPlayが提供している開発用お役立ちメソッドで、未実装なActionを表します)




















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
