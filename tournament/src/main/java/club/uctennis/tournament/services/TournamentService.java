package club.uctennis.tournament.services;

import java.util.List;

import club.uctennis.tournament.types.Tournament;

public interface TournamentService {

    /**
     * 大会一覧情報を取得
     * @return
     */
    public List<Tournament> getTournaments();
}
