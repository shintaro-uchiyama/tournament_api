package club.uctennis.tournament.services.imp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import club.uctennis.tournament.domain.mapper.OnetimeTokensMapper;
import club.uctennis.tournament.domain.mapper.ext.ExtEntriesMapper;
import club.uctennis.tournament.domain.mapper.ext.ExtPreEntriesMapper;
import club.uctennis.tournament.domain.model.OnetimeTokens;
import club.uctennis.tournament.domain.model.PreEntries;
import club.uctennis.tournament.services.EntryService;
import club.uctennis.tournament.types.Error;
import club.uctennis.tournament.types.PreEntry;
import club.uctennis.tournament.types.PreEntryResponse;

/**
 * 大会申込み関連のロジック.
 *
 * @author uchiyama-shintaro
 *
 */
@Service
public class EntryServiceImpl implements EntryService {

  @Autowired
  private ExtPreEntriesMapper extPreEntriesMapper;

  @Autowired
  private ExtEntriesMapper extEntriesMapper;

  @Autowired
  private OnetimeTokensMapper onetimeTokensMapper;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private MailSender sender;

  /**
   * 大会への仮登録.
   *
   * @param tournamentId
   * @param teamName
   * @param representiveName
   * @param email
   * @param phone
   * @return
   */
  public PreEntryResponse preEntry(String tournamentId, String teamName, String representiveName,
      String email, String phone) throws IllegalAccessException {

    PreEntryResponse preEntryResponse = new PreEntryResponse();

    // 登録済みのメールアドレスか確認
    if (extEntriesMapper.selectByEmail(email) != null) {
      List<Error> errors = new ArrayList<Error>();
      Error error = new Error();
      error.setType("email duplicate");
      error.setMessage("entry email is duplicate");
      errors.add(error);
      preEntryResponse.setErrors(errors);
      return preEntryResponse;
    }

    // 仮登録
    //// 仮登録テーブルに登録
    PreEntries preEntries = new PreEntries();
    preEntries.setTournamentId(Integer.parseInt(tournamentId));
    preEntries.setTeamName(teamName);
    preEntries.setRepresentiveName(representiveName);
    preEntries.setEmail(email);
    preEntries.setPhone(phone);
    LocalDateTime now = LocalDateTime.now();
    preEntries.setCreateDate(now);
    preEntries.setUpdateDate(now);
    extPreEntriesMapper.insertPreEntry(preEntries);
    //// ワンタイムトークンを生成
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String digest = passwordEncoder.encode(String.valueOf(preEntries.getId()));
    OnetimeTokens onetimeToken = new OnetimeTokens();
    onetimeToken.setPreEntryId(preEntries.getId());
    onetimeToken.setToken(digest);
    onetimeToken.setLimitedDate(now.plusDays(1));
    onetimeTokensMapper.insertSelective(onetimeToken);

    // 仮登録完了メール送信
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom("test-from@mexample.com");
    msg.setTo("test-to@mexample.com");
    msg.setSubject("大会仮登録完了");
    msg.setText("大会登録完了");
    this.sender.send(msg);

    // レスポンスにセット
    preEntryResponse.setPreEntry(modelMapper.map(preEntries, PreEntry.class));
    return preEntryResponse;
  }
}
