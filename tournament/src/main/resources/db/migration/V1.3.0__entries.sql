DROP TABLE IF EXISTS entries;

-- 大会参加者を記録するテーブル
CREATE TABLE entries
(
  id int NOT NULL AUTO_INCREMENT,
  tournament_id int NOT NULL,
  team_name varchar(255) NOT NULL,
  representive_name varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  phone varchar(255) NOT NULL,
  create_date datetime DEFAULT NOW() NOT NULL,
  update_date datetime,
  PRIMARY KEY (id),
  UNIQUE (email)
) COMMENT = '大会参加者を記録するテーブル';

ALTER TABLE entries
    ADD FOREIGN KEY (tournament_id)
    REFERENCES tournaments (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
;
