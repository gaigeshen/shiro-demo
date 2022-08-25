package work.gaigeshen.shiro.demo.accesstoken;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;

/**
 *
 * @author gaigeshen
 */
public class JwtAccessTokenGenerator implements AccessTokenGenerator {

  private final String secret;

  public JwtAccessTokenGenerator(String secret) {
    this.secret = secret;
  }

  @Override
  public String generateToken(String userId, String username) {
    return JWT.create().withIssuedAt(new Date())
            .withClaim("username", username).withClaim("userId", userId)
            .sign(Algorithm.HMAC256(secret));
  }

  @Override
  public boolean verifyToken(String token) {
    try {
      JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    } catch (JWTVerificationException e) {
      return false;
    }
    return true;
  }
}
