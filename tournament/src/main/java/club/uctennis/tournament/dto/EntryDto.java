package club.uctennis.tournament.dto;

import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
public class EntryDto {
  public enum EntryResult {
    NOT_EXIST(0), SAVE(1);

    private int entryResult;

    private EntryResult(int entryResult) {
      this.entryResult = entryResult;
    }

    public int getEntryResult() {
      return this.entryResult;
    }
  }

  private EntryResult entryResult;
  private String tournamentId;
  private String teamName;
  private String representiveName;
  private String email;
  private String phone;
  private String createDate;
  private String updateDate;;
}
