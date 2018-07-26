package club.uctennis.tournament.types;

import lombok.Data;

/**
 * GraphQLのMutationに紐付いたオブジェクト
 *
 * @author uchiyama-shintaro
 *
 */
@Data
public class PreEntry {
  private String id;
  private String teamName;
  private String representiveName;
  private String email;
  private String phone;
  private String createDate;
  private String updateDate;;
}
