package club.uctennis.tournament;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan({"club.uctennis.tournament.domain.mapper"})
public class TournamentApplication {
  public static void main(String[] args) {
    SpringApplication.run(TournamentApplication.class, args);
  }
}
