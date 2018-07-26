package club.uctennis.tournament.services;

import club.uctennis.tournament.types.PreEntry;

public interface EntryService {

  public PreEntry preEntry(String teamName, String representiveName, String email, String phone);

}
