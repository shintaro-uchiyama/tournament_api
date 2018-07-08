package club.uctennis.tournament.services.imp;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import club.uctennis.tournament.domain.mapper.ext.ExtTournamentsMapper;
import club.uctennis.tournament.domain.model.Tournaments;
import club.uctennis.tournament.services.TournamentService;
import club.uctennis.tournament.types.Tournament;

/**
 * トーナメント関連の操作はここに書く.
 * 
 * @author uchiyama-shintaro
 *
 */
public class TournamentServiceImpl implements TournamentService {

  @Autowired
  private ExtTournamentsMapper tournamentsMapper;

  @Autowired
  private ModelMapper modelMapper;

  /**
   * DBのトーナメント一覧をGraphQLの形式で返却.
   *
   * @return トーナメント一覧情報
   */
  public List<Tournament> getTournaments() {

    List<Tournaments> tournamentData = this.tournamentsMapper.selectAll();
    List<Tournament> tournamentResponse =
        tournamentData.stream().map(tournament -> modelMapper.map(tournament, Tournament.class))
            .collect(Collectors.toList());
    return tournamentResponse;
  }

}
