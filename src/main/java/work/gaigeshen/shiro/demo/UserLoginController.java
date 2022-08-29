package work.gaigeshen.shiro.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author gaigeshen
 */
@RequestMapping("/users")
@RestController
public class UserLoginController {

  private final BearerTokenCreator bearerTokenCreator;

  public UserLoginController(BearerTokenCreator bearerTokenCreator) {
    this.bearerTokenCreator = bearerTokenCreator;
  }

  @PostMapping("/login")
  public UserLoginResponse login(UserLoginParameters loginParameters, HttpServletRequest httpRequest) {
    String username = loginParameters.getUsername();
    String password = loginParameters.getPassword();

    Subject subject = SecurityUtils.getSubject();
    subject.login(new UsernamePasswordToken(username, password, httpRequest.getRemoteHost()));

    UserProfile profile = (UserProfile) subject.getPrincipals().getPrimaryPrincipal();

    String bearerToken = bearerTokenCreator.createToken(profile);

    return new DefaultUserLoginResponse(bearerToken, profile);
  }
}
