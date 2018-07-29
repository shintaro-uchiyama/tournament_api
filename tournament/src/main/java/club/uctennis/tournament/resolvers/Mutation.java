package club.uctennis.tournament.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import club.uctennis.tournament.services.EntryService;
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

  /**
   * 大会への仮申込.
   *
   * @param teamName
   * @param representiveName
   * @param email
   * @param phone
   * @return
   */
  public PreEntryResponse preEntryTournament(String teamId, String teamName,
      String representiveName, String email, String phone) throws IllegalAccessException {
    return entryService.preEntry(teamId, teamName, representiveName, email, phone);
  }

}
