package club.uctennis.tournament.services;

import javax.mail.MessagingException;
import club.uctennis.tournament.dto.EntryDto;
import club.uctennis.tournament.dto.PreEntryDto;

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
  public PreEntryDto preEntry(String tournamentId, String teamName, String representiveName,
      String email, String phone) throws MessagingException;

  /**
   * 大会への本登録.
   *
   * @param token
   * @return
   */
  public EntryDto entry(String token);

}
