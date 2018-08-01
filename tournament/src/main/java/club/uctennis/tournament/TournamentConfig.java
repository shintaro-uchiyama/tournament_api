package club.uctennis.tournament;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TournamentConfig {
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
