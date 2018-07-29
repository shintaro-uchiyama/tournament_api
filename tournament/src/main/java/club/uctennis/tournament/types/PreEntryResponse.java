package club.uctennis.tournament.types;

import java.util.List;
import lombok.Data;

/**
 * GraphQL(Mutation) preEntryTournamentメソッドの戻り値.
 *
 * @author uchiyama-shintaro
 *
 */
@Data
public class PreEntryResponse {
  private PreEntry preEntry;
  private List<Error> errors;
}
