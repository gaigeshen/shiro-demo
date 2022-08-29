package work.gaigeshen.shiro.demo.token;

import work.gaigeshen.shiro.demo.UserProfile;

/**
 *
 * @author gaigeshen
 */
public interface TokenManager {

  void invalidate(String token);

  String createToken(UserProfile profile);

  UserProfile parseToken(String token);
}
