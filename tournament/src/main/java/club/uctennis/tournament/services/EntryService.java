package club.uctennis.tournament.services;

import club.uctennis.tournament.types.PreEntry;

/**
 * 大会申込み関連のロジックインターフェース.
 *
 * @author uchiyama-shintaro
 *
 */
public interface EntryService {

  /**
   * 大会への仮登録.
   *
   * @param teamId
   * @param teamName
   * @param representiveName
   * @param email
   * @param phone
   * @return
   */
  public PreEntry preEntry(String teamId, String teamName, String representiveName, String email,
      String phone);

}
