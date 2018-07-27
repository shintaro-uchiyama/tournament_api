package club.uctennis.tournament.services.imp;

import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import club.uctennis.tournament.domain.mapper.PreEntriesMapper;
import club.uctennis.tournament.domain.model.PreEntries;
import club.uctennis.tournament.services.EntryService;
import club.uctennis.tournament.types.PreEntry;

/**
 * 大会申込み関連のロジック.
 *
 * @author uchiyama-shintaro
 *
 */
@Service
public class EntryServiceImpl implements EntryService {

  @Autowired
  private PreEntriesMapper preEntriesMapper;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private MailSender sender;

  /**
   * 大会への仮登録.
   *
   * @param teamId
   * @param teamName
   * @param representiveName
   * @param email
   * @param phone
   * @return
   */
  public PreEntry preEntry(String teamId, String teamName, String representiveName, String email,
      String phone) {

    // 登録済みのメールアドレスか確認

    // 仮登録
    PreEntries preEntries = new PreEntries();
    preEntries.setTournamentId(Integer.parseInt(teamId));
    preEntries.setTeamName(teamName);
    preEntries.setRepresentiveName(representiveName);
    preEntries.setEmail(email);
    preEntries.setPhone(phone);
    LocalDateTime now = LocalDateTime.now();
    preEntries.setCreateDate(now);
    preEntries.setUpdateDate(now);
    preEntriesMapper.insertSelective(preEntries);

    // 仮登録完了メール送信
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom("test-from@mexample.com");
    msg.setTo("test-to@mexample.com");
    msg.setSubject("大会仮登録完了");
    msg.setText("大会登録完了");
    this.sender.send(msg);

    return modelMapper.map(preEntries, PreEntry.class);
  }
}
