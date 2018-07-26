package club.uctennis.tournament.services.imp;

import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import club.uctennis.tournament.domain.mapper.PreEntriesMapper;
import club.uctennis.tournament.domain.model.PreEntries;
import club.uctennis.tournament.services.EntryService;
import club.uctennis.tournament.types.PreEntry;

@Service
public class EntryServiceImpl implements EntryService {

  @Autowired
  private PreEntriesMapper preEntriesMapper;

  @Autowired
  private ModelMapper modelMapper;

  public PreEntry preEntry(String teamName, String representiveName, String email, String phone) {

    PreEntries preEntries = new PreEntries();
    preEntries.setTournamentId(1);
    preEntries.setTeamName(teamName);
    preEntries.setRepresentiveName(representiveName);
    preEntries.setEmail(email);
    preEntries.setPhone(phone);

    LocalDateTime now = LocalDateTime.now();
    preEntries.setCreateDate(now);
    preEntries.setUpdateDate(now);

    preEntriesMapper.insertSelective(preEntries);

    return modelMapper.map(preEntries, PreEntry.class);
  }
}
