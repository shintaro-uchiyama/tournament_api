-- トーナメント一覧を管理するテーブル
CREATE TABLE tournaments
(
	id int NOT NULL AUTO_INCREMENT,
	title varchar(255),
	subtitle varchar(255),
	date datetime,
	image varchar(255),
	description varchar(255),
	PRIMARY KEY (id)
) COMMENT = 'トーナメント一覧を管理するテーブル';
