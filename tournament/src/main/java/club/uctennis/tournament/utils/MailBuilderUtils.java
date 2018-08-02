package club.uctennis.tournament.utils;

import java.util.Map;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import club.uctennis.tournament.types.MailSendForm;

@Component
public class MailBuilderUtils {
  private MailSendForm mailSendForm;
  private String templateLocation;
  private Map<String, Object> templateVariables;

  public static MailBuilderUtils build() {
    return new MailBuilderUtils();
  }

  public MailBuilderUtils setMailSendForm(MailSendForm mailSendForm) {
    this.mailSendForm = mailSendForm;
    return this;
  }

  public MailBuilderUtils setTemplateLocation(String templateLocation) {
    this.templateLocation = templateLocation;
    return this;
  }

  public MailBuilderUtils setTemplateVariables(Map<String, Object> templateVariables) {
    this.templateVariables = templateVariables;
    return this;
  }

  public SimpleMailMessage create() {
    VelocityUtils velocityUtils = new VelocityUtils();
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom(mailSendForm.getFrom());
    mailMessage.setTo(mailSendForm.getTo());
    mailMessage.setSubject(mailSendForm.getSubject());
    mailMessage.setText(velocityUtils.merge(this.templateLocation, templateVariables));
    return mailMessage;
  }
}
