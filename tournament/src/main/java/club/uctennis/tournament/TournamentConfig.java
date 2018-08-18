package club.uctennis.tournament;

import org.modelmapper.ModelMapper;
import org.springframework.cloud.aws.mail.simplemail.SimpleEmailServiceMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

@Configuration
public class TournamentConfig {
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  @Profile("production")
  public AmazonSimpleEmailService amazonSimpleEmailService(
      AWSCredentialsProvider credentialsProvider) {
    return AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(credentialsProvider)
        // Replace US_WEST_2 with the AWS Region you're using for
        // Amazon SES.
        .withRegion(Regions.US_EAST_1).build();
  }

  @Bean
  @Profile("production")
  public MailSender mailSender(AmazonSimpleEmailService ses) {
    return new SimpleEmailServiceMailSender(ses);
  }
}
