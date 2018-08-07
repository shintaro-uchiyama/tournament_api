package club.uctennis.tournament.resolvers;

import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import club.uctennis.tournament.dto.PreEntryDto;
import club.uctennis.tournament.dto.PreEntryDto.PreEntryResult;
import club.uctennis.tournament.services.EntryService;
import club.uctennis.tournament.types.Error;
import club.uctennis.tournament.types.PreEntry;
import club.uctennis.tournament.types.PreEntryResponse;

/**
 * GraphQL更新系.
 *
 * @author uchiyama-shintaro
 *
 */
@Component
public class Mutation implements GraphQLMutationResolver {

  @Autowired
  private EntryService entryService;
  @Autowired
  private Error error;
  @Autowired
  private PreEntryResponse preEntryResponse;
  @Autowired
  private ModelMapper modelMapper;

  /**
   * 大会への仮申込.
   *
   * @param tournamentId
   * @param teamName
   * @param representiveName
   * @param email
   * @param phone
   * @return
   */
  @Transactional
  public PreEntryResponse preEntryTournament(String tournamentId, String teamName,
      String representiveName, String email, String phone) throws MessagingException {
    PreEntryDto preEntryDto =
        entryService.preEntry(tournamentId, teamName, representiveName, email, phone);
    if (preEntryDto.getPreEntryResult() == PreEntryResult.DUPLICATE) {
      List<Error> errors = new ArrayList<Error>();
      error.setType("001");
      error.setMessage("entry email is duplicate");
      errors.add(error);
      preEntryResponse.setErrors(errors);
    } else if (preEntryDto.getPreEntryResult() == PreEntryResult.SAVE) {
      preEntryResponse.setPreEntry(modelMapper.map(preEntryDto, PreEntry.class));
    }

    return preEntryResponse;
  }
}
