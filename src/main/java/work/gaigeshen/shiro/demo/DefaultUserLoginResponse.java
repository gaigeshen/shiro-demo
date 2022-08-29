package work.gaigeshen.shiro.demo;

/**
 * @author gaigeshen
 */
public class DefaultUserLoginResponse implements UserLoginResponse {

  private final String token;

  private final UserProfile profile;

  public DefaultUserLoginResponse(String token, UserProfile profile) {
    this.token = token;
    this.profile = profile;
  }

  @Override
  public String getToken() {
    return token;
  }

  @Override
  public UserProfile getProfile() {
    return profile;
  }
}
