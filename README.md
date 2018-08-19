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
以下コマンド実行でsrc/main/resources/db/migration配下のsqlが反映される
```
cd tournament
./gradlew flywayClean flywayMigrate -Dflyway.url=jdbc:mysql://localhost:3306/tournament -Dflyway.user=root -Dflyway.password=root
```
## MybatisGeneratorによるMapperXml,MapperClass,DomainClass生成
EclipseのMarketPlaceでversion1.3.7以上のMyBatis Generatorをインストール  
generatorConfig.xml上で右クリックして実行→MyBatisGenerator実行！
## GraphiqlによるGraphql確認
### Query
以下URLにアクセスしてqueryに記載された内容を追記&再生ボタン風の物を押すと/graphqlにPOST通信が飛び実行される  
[githubのsample](https://github.com/graphql-java/graphql-spring-boot/blob/master/example/src/main/java/com/embedler/moon/graphql/boot/sample/ApplicationBootConfiguratisampleon.java) を真似したので記載された「responseee」が返却される
```
http://tournament.local/graphiql
```
query
```
query GetTournaments {
  tournaments {
    id
    subtitle
    date
    image
    description
  }
}
```
### Mutation
Mutationの例（エラー付）

```
# mutation
mutation PreEntryTournament($tournamentId: String!, $teamName: String!, $representiveName: String!, $email: String!, $phone: String!) {
  preEntryTournament(tournamentId: $tournamentId, teamName: $teamName, representiveName: $representiveName, email: $email, phone: $phone) {
    preEntry{
      teamName
      representiveName
      email
      phone
    }
    errors {
      type
      message
    }
  }
}

# variables
{
  "tournamentId": "1",
  "teamName": "team",
  "representiveName": "rep",
  "email": "email",
  "phone": "030"
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

## mailcatcherによるメール送信確認
以下URLにアクセスするとメールが送信できたこと確認できる！

```
http://tournament.local:1080/
```

## 単体テスト
以下コマンドでテストやfindbugs実行

```
./gradlew cleanTest test jacocoTestReport check
```


