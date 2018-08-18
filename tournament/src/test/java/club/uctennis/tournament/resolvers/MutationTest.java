package club.uctennis.tournament.resolvers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import javax.mail.MessagingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import club.uctennis.tournament.CsvDataSetLoader;
import club.uctennis.tournament.TournamentTestConfig;
import club.uctennis.tournament.domain.mapper.OnetimeTokensMapper;
import club.uctennis.tournament.domain.mapper.ext.ExtEntriesMapper;
import club.uctennis.tournament.domain.mapper.ext.ExtPreEntriesMapper;
import club.uctennis.tournament.dto.PreEntryDto;
import club.uctennis.tournament.services.imp.EntryServiceImpl;
import club.uctennis.tournament.types.PreEntryResponse;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest(EntryServiceImpl.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = TournamentTestConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@Transactional
@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*"})
public class MutationTest {
  @InjectMocks
  private Mutation mutation;

  @Spy
  EntryServiceImpl entryServiceMock = new EntryServiceImpl();

  @Autowired
  private ExtEntriesMapper extEntriesMapper;
  @Autowired
  private PreEntryDto preEntryDto;
  @Autowired
  private ExtPreEntriesMapper extPreEntriesMapper;
  @Autowired
  private OnetimeTokensMapper onetimeTokensMapper;
  private ModelMapper modelMapper = new ModelMapper();

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(entryServiceMock, "extEntriesMapper", extEntriesMapper);
    ReflectionTestUtils.setField(entryServiceMock, "preEntryDto", preEntryDto);
    ReflectionTestUtils.setField(entryServiceMock, "extPreEntriesMapper", extPreEntriesMapper);
    ReflectionTestUtils.setField(entryServiceMock, "onetimeTokensMapper", onetimeTokensMapper);
    ReflectionTestUtils.setField(entryServiceMock, "modelMapper", modelMapper);

    ReflectionTestUtils.setField(mutation, "modelMapper", modelMapper);
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
  public void testPreEntryTournamentSave() throws Exception {
    // mock
    PowerMockito.doNothing().when(entryServiceMock, "sendPreEntryMail", anyString(), anyString());

    // execute test method
    PreEntryResponse preEntryResponse = mutation.preEntryTournament("1", "team2", "representive2",
        "shintaro.0112@gmail.com", "phone2");

    // assertion
    assertEquals("1", preEntryResponse.getPreEntry().getTournamentId());
    assertEquals("team2", preEntryResponse.getPreEntry().getTeamName());
    assertEquals("representive2", preEntryResponse.getPreEntry().getRepresentiveName());
    assertEquals("shintaro.0112@gmail.com", preEntryResponse.getPreEntry().getEmail());
    assertEquals("phone2", preEntryResponse.getPreEntry().getPhone());
    assertNull(preEntryResponse.getErrors());
  }
}
