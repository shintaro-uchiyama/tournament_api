package club.uctennis.tournament.domain.mapper.ext;

import club.uctennis.tournament.domain.model.PreEntries;

/**
 * 仮申込みテーブル操作関連.
 *
 * @author uchiyama-shintaro
 *
 */
public interface ExtPreEntriesMapper {

  /**
   * メールアドレスが登録されているか確認する
   *
   * @param email
   * @return
   */
  int insertPreEntry(PreEntries preEntries);
}
