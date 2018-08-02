package club.uctennis.tournament.services.imp;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import club.uctennis.tournament.domain.mapper.OnetimeTokensMapper;
import club.uctennis.tournament.domain.mapper.ext.ExtEntriesMapper;
import club.uctennis.tournament.domain.mapper.ext.ExtPreEntriesMapper;
import club.uctennis.tournament.domain.model.OnetimeTokens;
import club.uctennis.tournament.domain.model.PreEntries;
import club.uctennis.tournament.services.EntryService;
import club.uctennis.tournament.types.Error;
import club.uctennis.tournament.types.MailSendForm;
import club.uctennis.tournament.types.PreEntry;
import club.uctennis.tournament.types.PreEntryResponse;
import club.uctennis.tournament.utils.MailBuilderUtils;

/**
 * 大会申込み関連のロジック.
 *
 * @author uchiyama-shintaro
 *
 */
@Service
@ConfigurationProperties(prefix = "app")
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
  private MailSender mailSender;

  private String schema;
  private String host;
  private String port;

  /**
   * ConfigurationPropertiesはsetterが必要.
   *
   * @param schema セットする schema
   */
  public void setSchema(String schema) {
    this.schema = schema;
  }

  /**
   * ConfigurationPropertiesはsetterが必要.
   *
   * @param port セットする port
   */
  public void setPort(String port) {
    this.port = port;
  }

  /**
   * ConfigurationPropertiesはsetterが必要.
   *
   * @param host セットする host
   */
  public void setHost(String host) {
    this.host = host;
  }

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
      String email, String phone) throws MessagingException {

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
    PreEntries preEntries =
        createPreEntries(tournamentId, teamName, representiveName, email, phone);
    extPreEntriesMapper.insertPreEntry(preEntries);
    //// ワンタイムトークンテーブルに登録
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String token = passwordEncoder.encode(String.valueOf(preEntries.getId()));
    onetimeTokensMapper.insertSelective(
        createOnetimeToken(preEntries.getId(), token, LocalDateTime.now().plusDays(1)));

    // 仮登録完了メール送信
    UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
    URI location =
        builder.scheme(schema).host(host).port(port).path("/regist/" + token).build().toUri();
    SimpleMailMessage mailMessage = createMailMessage(representiveName, location.toString());
    mailSender.send(mailMessage);

    // レスポンスにセット
    preEntryResponse.setPreEntry(modelMapper.map(preEntries, PreEntry.class));
    return preEntryResponse;
  }

  /**
   * 仮申込テーブル用のDTO生成.
   *
   * @param tournamentId
   * @param teamName
   * @param representiveName
   * @param email
   * @param phone
   * @return
   */
  private PreEntries createPreEntries(String tournamentId, String teamName, String representiveName,
      String email, String phone) {
    PreEntries preEntries = new PreEntries();
    preEntries.setTournamentId(Integer.parseInt(tournamentId));
    preEntries.setTeamName(teamName);
    preEntries.setRepresentiveName(representiveName);
    preEntries.setEmail(email);
    preEntries.setPhone(phone);
    return preEntries;
  }

  /**
   * ワンタイムトークンテーブル用のDTO生成.
   *
   * @param preEntryId
   * @param token
   * @param limitedDate
   * @return
   */
  private OnetimeTokens createOnetimeToken(Integer preEntryId, String token,
      LocalDateTime limitedDate) {
    OnetimeTokens onetimeToken = new OnetimeTokens();
    onetimeToken.setPreEntryId(preEntryId);
    onetimeToken.setToken(token);
    onetimeToken.setLimitedDate(limitedDate);
    return onetimeToken;
  }

  /**
   * 仮登録完了メール作成.
   * 
   * @param representiveName
   * @param validationUrl
   * @return
   */
  private SimpleMailMessage createMailMessage(String representiveName, String validationUrl) {
    Map<String, Object> model = new HashMap<>();
    model.put("representiveName", representiveName);
    model.put("validationUrl", validationUrl);

    MailSendForm mailSendForm = new MailSendForm();
    mailSendForm.setFrom("test-from@mexample.com");
    mailSendForm.setTo("test-to@mexample.com");
    mailSendForm.setSubject("大会仮登録完了");
    return MailBuilderUtils.build().setMailSendForm(mailSendForm)
        .setTemplateLocation("/templates/ValidationMail.vm").setTemplateVariables(model).create();
  }
}
