package club.uctennis.tournament;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import club.uctennis.tournament.domain.mapper.TournamentsMapper;
import club.uctennis.tournament.domain.model.Tournaments;
import club.uctennis.tournament.types.Tournament;

@Component
public class TournamentRepository {

	@Autowired
	private TournamentsMapper tournamentsMapper;

	private List<Tournament> tournaments;

	public TournamentRepository() {
		Tournament tournament1 = new Tournament("1", "title2");

		List<Tournament> tournaments = new ArrayList<>();
		tournaments.add(tournament1);
		this.tournaments = tournaments;
	}

	public List<Tournament> getTournaments(){
		Tournaments tournaments2 = tournamentsMapper.selectByPrimaryKey(2);
		Tournament tournament2 = new Tournament("2", tournaments2.getTitle());
		this.tournaments.add(tournament2);

		return this.tournaments;
	}

}
