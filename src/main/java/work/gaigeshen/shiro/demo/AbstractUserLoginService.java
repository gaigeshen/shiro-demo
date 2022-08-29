package work.gaigeshen.shiro.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author gaigeshen
 */
public abstract class AbstractUserLoginService implements UserLoginService {

  private final BearerTokenCreator bearerTokenCreator;

  protected AbstractUserLoginService(BearerTokenCreator bearerTokenCreator) {
    this.bearerTokenCreator = bearerTokenCreator;
  }

  @Override
  public final UserLoginResponse login(UserLoginParameters loginParameters) {
    String username = loginParameters.getUsername();
    String password = loginParameters.getPassword();
    String host = loginParameters.getHost();

    Subject subject = SecurityUtils.getSubject();

    subject.login(new UsernamePasswordToken(username, password, host));

    UserProfile profile = (UserProfile) subject.getPrincipals().getPrimaryPrincipal();

    String bearerToken = bearerTokenCreator.createToken(profile);

    return loginResponseOverride(new DefaultUserLoginResponse(bearerToken, profile));
  }

  protected UserLoginResponse loginResponseOverride(UserLoginResponse loginResponse) {
    return loginResponse;
  }
}
