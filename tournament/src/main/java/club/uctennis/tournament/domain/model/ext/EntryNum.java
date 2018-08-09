package club.uctennis.tournament.domain.model.ext;

import lombok.Data;

@Data
public class EntryNum {

  private Integer id;
  private Integer tournamentId;
  private Integer entryCount;
}
