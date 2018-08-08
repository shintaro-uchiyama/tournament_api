package club.uctennis.tournament.resolvers;

import static org.junit.Assert.*;
import javax.mail.MessagingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import club.uctennis.tournament.CsvDataSetLoader;
import club.uctennis.tournament.types.PreEntryResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@Transactional
public class MutationTest {
  @Autowired
  private Mutation mutation;

  @Before
  public void setup() {

  }

  @After
  public void tearDown() {

  }

  @Test
  @DatabaseSetup(value = "/club/uctennis/tournament/resolvers/mutations/")
  public void testPreEntryTournamentDuplicate() throws MessagingException {
    PreEntryResponse preEntryResponse =
        mutation.preEntryTournament("1", "team1", "representive1", "email1", "phone1");
    assertEquals("001", preEntryResponse.getErrors().get(0).getType());
    assertNull(preEntryResponse.getPreEntry());
  }

  @Test
  @DatabaseSetup(value = "/club/uctennis/tournament/resolvers/mutations/")
  @ExpectedDatabase(value = "/club/uctennis/tournament/resolvers/mutations/expected/",
      table = "pre_entries", assertionMode = DatabaseAssertionMode.NON_STRICT)
  public void testPreEntryTournamentSave() throws MessagingException {
    PreEntryResponse preEntryResponse =
        mutation.preEntryTournament("1", "team2", "representive2", "email2", "phone2");
    assertEquals("1", preEntryResponse.getPreEntry().getTournamentId());
    assertEquals("team2", preEntryResponse.getPreEntry().getTeamName());
    assertEquals("representive2", preEntryResponse.getPreEntry().getRepresentiveName());
    assertEquals("email2", preEntryResponse.getPreEntry().getEmail());
    assertEquals("phone2", preEntryResponse.getPreEntry().getPhone());
    assertNull(preEntryResponse.getErrors());
  }
}
