package club.uctennis.tournament;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.aws.mail.simplemail.SimpleEmailServiceMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ContextConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

@ContextConfiguration
@ComponentScan("club.uctennis.tournament")
@MapperScan({"club.uctennis.tournament.domain.mapper"})
public class TournamentTestConfig {
  @Mock
  AmazonS3Client client;

  public TournamentTestConfig() {
    MockitoAnnotations.initMocks(this);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }


  @Bean
  public AmazonS3Client amazonS3Client() {
    return client;
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
