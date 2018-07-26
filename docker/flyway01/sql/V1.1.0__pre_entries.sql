DROP TABLE IF EXISTS pre_entries;

-- 大会参加者を記録するテーブル
CREATE TABLE pre_entries
(
	id int NOT NULL AUTO_INCREMENT,
	tournament_id int NOT NULL,
	team_name varchar(255),
	representive_name varchar(255),
	email varchar(255),
	phone varchar(255),
	create_date datetime NOT NULL,
	update_date datetime,
	PRIMARY KEY (id)
) COMMENT = '大会参加者を記録するテーブル';

ALTER TABLE pre_entries
	ADD FOREIGN KEY (tournament_id)
	REFERENCES tournaments (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;
