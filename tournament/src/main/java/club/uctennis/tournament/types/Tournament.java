package club.uctennis.tournament.types;

import lombok.Data;

@Data
public class Tournament {
    private String id;
    private String title;
    private String subtitle;
    private String date;
    private String image;
    private String description;
}
