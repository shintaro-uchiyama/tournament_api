package club.uctennis.tournament.types;

import lombok.Data;

/**
 * GraphQL(Mutation) preEntryTournamentメソッドの結果戻り値.
 *
 * @author uchiyama-shintaro
 *
 */
@Data
public class PreEntry {
  private String tournamentId;
  private String teamName;
  private String representiveName;
  private String email;
  private String phone;
  private String createDate;
  private String updateDate;;
}
