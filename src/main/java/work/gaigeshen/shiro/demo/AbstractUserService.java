package work.gaigeshen.shiro.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author gaigeshen
 */
public abstract class AbstractUserService implements UserService {

  private final BearerTokenCreator bearerTokenCreator;

  protected AbstractUserService(BearerTokenCreator bearerTokenCreator) {
    this.bearerTokenCreator = bearerTokenCreator;
  }

  @Override
  public UserLoginResponse login(UserLoginParameters loginParameters) {
    String username = loginParameters.getUsername();
    String password = loginParameters.getPassword();
    String host = loginParameters.getHost();

    Subject subject = SecurityUtils.getSubject();
    subject.login(new UsernamePasswordToken(username, password, host));

    UserProfile profile = (UserProfile) subject.getPrincipal();
    return new DefaultUserLoginResponse(bearerTokenCreator.createToken(profile), profile);
  }

  @Override
  public void logout(UserLogoutParameters logoutParameters) {
    SecurityUtils.getSubject().logout();
  }
}
