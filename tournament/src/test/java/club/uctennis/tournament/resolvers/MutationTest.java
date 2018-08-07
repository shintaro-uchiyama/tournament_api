package club.uctennis.tournament.resolvers;

import static org.junit.Assert.*;
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
import club.uctennis.tournament.CsvDataSetLoader;
import club.uctennis.tournament.domain.mapper.PreEntriesMapper;
import club.uctennis.tournament.domain.model.PreEntries;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@Transactional
public class MutationTest {
  @Autowired
  private PreEntriesMapper preEntriesMapper;

  @Before
  public void setup() {

  }

  @After
  public void tearDown() {

  }

  @Test
  @DatabaseSetup(value = "/club/uctennis/tournament/resolvers/mutations/")
  public void testPreEntryTournamentDuplicate() {
    PreEntries PreEntries = preEntriesMapper.selectByPrimaryKey(5);
    assertEquals("email5", PreEntries.getEmail());
  }
}
