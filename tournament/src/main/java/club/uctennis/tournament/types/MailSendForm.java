package club.uctennis.tournament.types;

import lombok.Data;

@Data
public class MailSendForm {
  private String from;

  private String to;

  private String subject;
}
