package club.uctennis.tournament.domain.mapper.ext;

import java.util.List;
import club.uctennis.tournament.domain.model.Tournaments;

/**
 * トーナメントテーブル操作関連.
 * 
 * @author uchiyama-shintaro
 *
 */
public interface ExtTournamentsMapper {

  /**
   * トーナメント情報をすべて取得.
   *
   * @return DBのトーナメント情報一覧
   */
  List<Tournaments> selectAll();
}
