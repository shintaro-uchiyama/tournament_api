# 概要
テニス大会ページ用のAPI実行用プログラム
# 実行方法
## dockerの実行環境立ち上げ
nginxとtomcatを立ち上げてコンテナが立ち上がったこと確認
```
docker-compose up -d --build
docker-compose ps
```
## ビルド（テストパスキップ）&パッケージ&デプロイ
gradleでビルド（テストパスキップ）してwarファイルパッケージングそしてtomcatにデプロイ
```
cd tournament
gradle -Dskip.tests build war && cp build/libs/tournament-0.0.1-SNAPSHOT.war ../docker/tomcat01/deployment/tournament.war
```
## ブラウザから疎通確認
以下URLをブラウザに打ち込んで内容確認  
※ローカル環境のhostsファイルに「127.0.0.1 tournament.local」を追記
```
http://tournament.local/
```
## GraphiqlによるGraphql確認
以下URLにアクセスして再生ボタン風の物を押すと/graphqlにPOST通信が飛び実行される
```
http://tournament.local/graphiql
```
