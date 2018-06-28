package club.uctennis.tournament;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import club.uctennis.tournament.types.Tournament;

@Component
public class TournamentRepository {

	private List<Tournament> tournaments;

	public TournamentRepository() {
		Tournament tournament1 = new Tournament("1", "title1");
		Tournament tournament2 = new Tournament("2", "title2");

		List<Tournament> tournaments = new ArrayList<>();
		tournaments.add(tournament1);
		tournaments.add(tournament2);

		this.tournaments = tournaments;
	}

	public List<Tournament> getTournaments(){
		return this.tournaments;
	}

}
