package work.gaigeshen.shiro.demo.user.accesstoken;

import work.gaigeshen.shiro.demo.user.UserDescriptor;

/**
 *
 * @author gaigeshen
 */
public interface AccessTokenCreator {

  void invalidate(String token);

  String createToken(UserDescriptor descriptor);

  UserDescriptor validateToken(String token);
}
