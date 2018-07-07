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

    public List<Tournament> getTournaments(){
        List<Tournament> tournaments = new ArrayList<>();

        Tournament tournament1 = new Tournament();
        Tournaments tournaments1 = tournamentsMapper.selectByPrimaryKey(1);
        tournament1.setId(tournaments1.getId().toString());
        tournament1.setTitle(tournaments1.getTitle());
        tournament1.setSubtitle(tournaments1.getSubtitle());
        tournament1.setDate(tournaments1.getDate().toString());
        tournament1.setImage(tournaments1.getImage());
        tournament1.setDescription(tournaments1.getDescription());

        tournaments.add(tournament1);

        Tournaments tournaments2 = tournamentsMapper.selectByPrimaryKey(2);
        Tournament tournament2 = new Tournament();
        tournament2.setId(tournaments2.getId().toString());
        tournament2.setTitle(tournaments2.getTitle());
        tournament2.setSubtitle(tournaments1.getSubtitle());
        tournament2.setDate(tournaments1.getDate().toString());
        tournament2.setImage(tournaments1.getImage());
        tournament2.setDescription(tournaments1.getDescription());
        tournaments.add(tournament2);

        return tournaments;
    }

}
