package club.uctennis.tournament.services.imp;

import java.io.StringWriter;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
import club.uctennis.tournament.types.PreEntry;
import club.uctennis.tournament.types.PreEntryResponse;

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
  private JavaMailSender javaMailSender;

  // @Autowired
  // private VelocityEngine velocityEngine;

  private String schema;
  private String host;
  private String port;

  /**
   * @param schema セットする schema
   */
  public void setSchema(String schema) {
    this.schema = schema;
  }

  /**
   * @param port セットする port
   */
  public void setPort(String port) {
    this.port = port;
  }

  /**
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
    try {
      UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
      URI location = builder.scheme(schema).host(host).port(port).path("/regist").build().toUri();

      MimeMessage msg = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(msg, true);
      VelocityContext context = new VelocityContext();
      context.put("representiveName", representiveName);
      context.put("validationUrl", location.toString());
      StringWriter writer = new StringWriter();
      VelocityEngine velocityEngine = new VelocityEngine();
      velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
      velocityEngine.setProperty("classpath.resource.loader.class",
          ClasspathResourceLoader.class.getName());
      velocityEngine.init();

      velocityEngine.mergeTemplate("/templates/ValidationMail.vm", "UTF-8", context, writer);

      helper.setFrom("test-from@mexample.com");
      helper.setTo("test-to@mexample.com");
      helper.setSubject("大会仮登録完了");
      msg.setText(writer.toString());
      javaMailSender.send(msg);
    } catch (MessagingException e) {
      e.printStackTrace();
    }

    // レスポンスにセット
    preEntryResponse.setPreEntry(modelMapper.map(preEntries, PreEntry.class));
    return preEntryResponse;
  }
}
