DROP TABLE IF EXISTS tournaments;

-- トーナメント一覧を管理するテーブル
CREATE TABLE tournaments
(
  id int NOT NULL AUTO_INCREMENT,
  title varchar(255) NOT NULL,
  subtitle varchar(255) NOT NULL,
  date datetime NOT NULL,
  image varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  create_date datetime DEFAULT NOW() NOT NULL,
  update_date datetime,
  PRIMARY KEY (id)
) COMMENT = 'トーナメント一覧を管理するテーブル';
