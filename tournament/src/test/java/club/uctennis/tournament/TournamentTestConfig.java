package club.uctennis.tournament;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@ComponentScan("club.uctennis.tournament")
@MapperScan({"club.uctennis.tournament.domain.mapper"})
public class TournamentTestConfig {
}
