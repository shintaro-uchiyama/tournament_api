package club.uctennis.tournament.types;

import lombok.Data;

/**
 * GraphQLのQueryに紐付いたオブジェクト
 * 
 * @author uchiyama-shintaro
 *
 */
@Data
public class Tournament {
  private String id;
  private String title;
  private String subtitle;
  private String date;
  private String image;
  private String description;
}
