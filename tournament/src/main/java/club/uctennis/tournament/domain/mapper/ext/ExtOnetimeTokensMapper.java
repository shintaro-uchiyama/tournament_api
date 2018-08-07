package club.uctennis.tournament.domain.mapper.ext;

import java.time.LocalDateTime;
import java.util.List;
import club.uctennis.tournament.domain.model.OnetimeTokens;

/**
 * ワンタイムトークンテーブル操作関連.
 *
 * @author uchiyama-shintaro
 *
 */
public interface ExtOnetimeTokensMapper {

  /**
   * 有効なトークンが存在するか確認する.
   *
   * @param token
   * @param now
   * @return
   */
  List<OnetimeTokens> selectValidByToken(String token, LocalDateTime now);
}
