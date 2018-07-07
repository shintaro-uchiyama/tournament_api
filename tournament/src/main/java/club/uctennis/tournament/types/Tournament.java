package club.uctennis.tournament.types;

import lombok.Data;

@Data
public class Tournament {
	private String id;
	private String title;

	public Tournament(String id, String title) {
		this.id = id;
		this.title = title;
	}
}
