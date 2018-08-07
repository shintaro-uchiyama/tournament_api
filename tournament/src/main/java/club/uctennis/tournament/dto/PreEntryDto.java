package club.uctennis.tournament.dto;

import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
public class PreEntryDto {
  public enum PreEntryResult {
    DUPLICATE(0), SAVE(1);

    private int preEntryResult;

    private PreEntryResult(int preEntryResult) {
      this.preEntryResult = preEntryResult;
    }

    public int getPreEntryResult() {
      return this.preEntryResult;
    }
  }

  private PreEntryResult preEntryResult;
  private String tournamentId;
  private String teamName;
  private String representiveName;
  private String email;
  private String phone;
  private String createDate;
  private String updateDate;;
}
