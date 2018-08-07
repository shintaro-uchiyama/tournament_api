package club.uctennis.tournament.types;

import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * GraphQLのエラー戻り値.
 *
 * @author uchiyama-shintaro
 *
 */
@Data
@Component
public class Error {
  private String type;
  private String message;
}
