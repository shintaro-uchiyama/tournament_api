package club.uctennis.tournament.types;

import lombok.Data;

/**
 * GraphQLのエラー戻り値.
 * 
 * @author uchiyama-shintaro
 *
 */
@Data
public class Error {
  private String type;
  private String message;
}
