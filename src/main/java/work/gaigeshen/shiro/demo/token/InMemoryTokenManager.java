package work.gaigeshen.shiro.demo.token;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import work.gaigeshen.shiro.demo.UserProfile;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author gaigeshen
 */
public class InMemoryTokenManager implements TokenManager {

  private final Cache<String, UserProfile> tokenCache;

  public InMemoryTokenManager(long timeToLive) {
    this.tokenCache = CacheBuilder.newBuilder()
            .maximumSize(1000).expireAfterAccess(timeToLive, TimeUnit.SECONDS)
            .build();
  }

  @Override
  public void invalidate(String token) {
    tokenCache.invalidate(token);
  }

  @Override
  public String createToken(UserProfile profile) {
    String token = UUID.randomUUID().toString().replace("-", "");
    tokenCache.put(token, profile);
    return token;
  }

  @Override
  public UserProfile parseToken(String token) {
    return tokenCache.getIfPresent(token);
  }

}
