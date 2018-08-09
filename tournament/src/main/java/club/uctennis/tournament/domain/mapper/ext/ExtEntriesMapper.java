package club.uctennis.tournament.domain.mapper.ext;

import java.util.List;
import club.uctennis.tournament.domain.model.Entries;
import club.uctennis.tournament.domain.model.ext.EntryNum;

/**
 * 申込みテーブル操作関連.
 *
 * @author uchiyama-shintaro
 *
 */
public interface ExtEntriesMapper {

  /**
   * メールアドレスが登録されているか確認する.
   *
   * @param email
   * @return
   */
  Entries selectByEmail(String email);

  /**
   * トーナメント毎の参加者数取得.
   *
   * @return
   */
  List<EntryNum> countByTournamenId();
}
