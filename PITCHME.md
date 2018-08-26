
## Scalaハンズオン ~ PlayFramework

## 今日のゴール

- Scala + PlayFrameworkでCRUDアプリケーションが作れるようになる
- Scalaの関数型言語としてのエッセンスと、その嬉しさを理解する


## 自己紹介タイム

参加者全員でお互いに自己紹介をしましょう👍


## 講師について

### 岩松 竜也

- 2015年新卒入社

### 田所 駿佑

- 求人検索エンジン「スタンバイ」 プロダクトオーナー見習い
- 2015年新卒入社
- PlayFramework コントリビューター
- 「クローリングハック」(翔泳社) 共著


## 自己紹介タイム

参加者全員でお互いに自己紹介をしましょう👍

- お名前を教えてください
- 普段はどんなプログラムを書いていますか？
- 好きな言語はありますか？
- 「今日はこういうことについても聴いてみたい」という要望はありますか？


## よろしくお願いします👍


## プロジェクトの準備(1)

ご自身のターミナルで以下のコマンドを実行してください。

```
sbt new playframework/play-scala-seed.g8 --branch 2.6.x
```


## プロジェクトの準備(2)

プロジェクト名などを聞かれますが、今回はプロジェクト名を`play2-hands-on`とし、他の項目は初期値のままプロジェクトを作成します。

```
This template generates a Play Scala project

name [play-scala-seed]: play2-hands-on
organization [com.example]:
play_version [2.6.18]:
sbt_version [1.2.1]:
scalatestplusplay_version [3.1.2]:

Template applied in ./play2-hands-on
```


## プロジェクトの準備(3)

作成したプロジェクトのディレクトリに移動し、`sbt run`コマンドを実行します。
プロジェクトの起動に必要な各種ツールやJarがダウンロードされ、アプリケーション起動されます。

```
cd play2-hands-on
sbt run
```

初回の起動にはしばらく時間がかかるので、その間にScalaの基本文法について学びます👍

## Scalaのざっくり解説

## Scalaの基本文法


## 作成したPlayプロジェクトの確認

- ブラウザから`localhost:9000`にアクセスしてみましょう
- `Welcome to Play!` と表示されていれば準備はOKです！
