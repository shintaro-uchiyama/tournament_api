package club.uctennis.tournament.domain.mapper.ext;

import club.uctennis.tournament.domain.model.Entries;

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
}
