package club.uctennis.tournament.services;

import javax.mail.MessagingException;
import club.uctennis.tournament.types.PreEntryResponse;

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
   * @param tournamentId
   * @param teamName
   * @param representiveName
   * @param email
   * @param phone
   * @return
   * @throws IllegalAccessException
   */
  public PreEntryResponse preEntry(String tournamentId, String teamName, String representiveName,
      String email, String phone) throws MessagingException;

}
