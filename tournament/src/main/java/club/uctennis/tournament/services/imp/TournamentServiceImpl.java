package club.uctennis.tournament.services.imp;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import club.uctennis.tournament.domain.mapper.ext.ExtEntriesMapper;
import club.uctennis.tournament.domain.mapper.ext.ExtTournamentsMapper;
import club.uctennis.tournament.domain.model.Tournaments;
import club.uctennis.tournament.domain.model.ext.EntryNum;
import club.uctennis.tournament.services.TournamentService;
import club.uctennis.tournament.types.Tournament;

/**
 * トーナメント関連の操作はここに書く.
 *
 * @author uchiyama-shintaro
 *
 */
@Service
public class TournamentServiceImpl implements TournamentService {

  @Autowired
  private ExtTournamentsMapper tournamentsMapper;
  @Autowired
  private ExtEntriesMapper extEntriesMapper;
  @Autowired
  private ModelMapper modelMapper;

  /**
   * DBのトーナメント一覧をGraphQLの形式で返却.
   *
   * @return トーナメント一覧情報
   */
  public List<Tournament> getTournaments() {

    // トーナメント一覧取得
    List<Tournaments> tournamentData = tournamentsMapper.selectAll();
    // 参加者数取得
    List<EntryNum> entryNums = extEntriesMapper.countByTournamenId();
    List<Tournament> tournamentResponse = new ArrayList<>();;
    for (Tournaments tournaments : tournamentData) {
      Tournament tournament = modelMapper.map(tournaments, Tournament.class);
      for (EntryNum entryNum : entryNums) {
        if (tournaments.getId() == entryNum.getTournamentId()) {
          tournament.setParticipantNum(String.valueOf(entryNum.getEntryCount()));
        } else {
          tournament.setParticipantNum(String.valueOf(0));
        }
      }
      tournamentResponse.add(tournament);
    }
    return tournamentResponse;
  }

}
