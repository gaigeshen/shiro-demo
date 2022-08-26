package work.gaigeshen.shiro.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 *
 * @author gaigeshen
 */
public class DefaultBearerTokenCreator implements BearerTokenCreator {

  private final String secret;

  private final long expiresInSeconds;

  public DefaultBearerTokenCreator(String secret, long expiresInSeconds) {
    if (Objects.isNull(secret)) {
      throw new IllegalArgumentException("secret cannot be null");
    }
    if (expiresInSeconds <= 0) {
      throw new IllegalArgumentException("expiresInSeconds is invalid");
    }
    this.secret = secret;
    this.expiresInSeconds = expiresInSeconds;
  }

  @Override
  public String createToken(UserProfile userDescriptor) {
    if (Objects.isNull(userDescriptor)) {
      throw new IllegalArgumentException("user descriptor cannot be null");
    }
    String userId = userDescriptor.getUserId();
    String username = userDescriptor.getUsername();

    LocalDateTime currentTime = LocalDateTime.now();
    LocalDateTime expiresTime = currentTime.plusSeconds(expiresInSeconds);

    ZoneId zoneId = ZoneId.systemDefault();
    Instant currentInstant = currentTime.atZone(zoneId).toInstant();
    Instant expiresInstant = expiresTime.atZone(zoneId).toInstant();

    JWTCreator.Builder jwtBuilder = JWT.create()
            .withIssuedAt(currentInstant).withExpiresAt(expiresInstant)
            .withClaim("userId", userId).withClaim("username", username);
    return jwtBuilder.sign(Algorithm.HMAC256(secret));
  }

  @Override
  public UserProfile parseToken(String token) {
    if (Objects.isNull(token)) {
      throw new IllegalArgumentException("token cannot be null");
    }
    DecodedJWT decoded;
    try {
      decoded = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    } catch (Exception e) {
      return null;
    }
    String userId = decoded.getClaim("userId").asString();
    String username = decoded.getClaim("username").asString();
    List<String> roles = decoded.getClaim("roles").asList(String.class);
    List<String> permissions = decoded.getClaim("permissions").asList(String.class);

    return new UserProfile(userId, username, new HashSet<>(roles), new HashSet<>(permissions));
  }

}
