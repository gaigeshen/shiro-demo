package work.gaigeshen.shiro.demo.token;

import work.gaigeshen.shiro.demo.UserProfile;

/**
 *
 * @author gaigeshen
 */
public interface BearerTokenCreator {

  void invalidate(String accessToken);

  String createToken(UserProfile profile);

  UserProfile parseToken(String token);
}
