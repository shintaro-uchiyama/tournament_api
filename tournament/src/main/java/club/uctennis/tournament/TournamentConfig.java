package club.uctennis.tournament;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;

@Configuration
public class TournamentConfig {
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Autowired
  AmazonSimpleEmailService amazonSimpleEmailService(
      AmazonSimpleEmailService amazonSimpleEmailService) {
    return amazonSimpleEmailService;
  }
}
