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
## flywayによるDB Migration
以下コマンド実行で./docker/flyway01/sql配下のsqlが反映される
```
docker-compose up -d dbmigrate01
docker logs flyway01
```
## MybatisGeneratorによるMapperXml,MapperClass,DomainClass生成
以下コマンドでクラスやxmlを生成
```
./gradlew mbGenerator
```
## GraphiqlによるGraphql確認
以下URLにアクセスしてqueryに記載された内容を追記&再生ボタン風の物を押すと/graphqlにPOST通信が飛び実行される  
[githubのsample](https://github.com/graphql-java/graphql-spring-boot/blob/master/example/src/main/java/com/embedler/moon/graphql/boot/sample/ApplicationBootConfiguratisampleon.java) を真似したので記載された「responseee」が返却される
```
http://tournament.local/graphiql
```
query
```
{
  tournaments {
    id
    title
  }
}
```

## PostmanによるGraphql確認
あんまりちゃんと見てないけど、以下のようなjsonをPOSTリクエストするとちゃんと帰ってきた
```
{
  "query": "query{test}",
  "operationName": "",
  "variables": {}
}
```
