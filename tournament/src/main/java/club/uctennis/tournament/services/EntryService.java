package club.uctennis.tournament.services;

import club.uctennis.tournament.types.PreEntry;

public interface EntryService {

  /**
   * 大会への仮登録.
   *
   * @param teamName
   * @param representiveName
   * @param email
   * @param phone
   * @return
   */
  public PreEntry preEntry(String teamName, String representiveName, String email, String phone);

}
