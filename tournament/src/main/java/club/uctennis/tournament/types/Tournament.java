package club.uctennis.tournament.types;

public class Tournament {
	private String id;
	private String title;

	public Tournament(String id, String title) {
		this.id = id;
		this.title = title;
	}
	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id セットする id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title セットする title
	 */
	public void setTitle(String title) {
		this.title = title;
	}


}
