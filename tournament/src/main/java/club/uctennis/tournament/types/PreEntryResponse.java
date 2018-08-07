package club.uctennis.tournament.types;

import java.util.List;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * GraphQL(Mutation) preEntryTournamentメソッドの戻り値.
 *
 * @author uchiyama-shintaro
 *
 */
@Data
@Component
public class PreEntryResponse {
  private PreEntry preEntry;
  private List<Error> errors;
}
