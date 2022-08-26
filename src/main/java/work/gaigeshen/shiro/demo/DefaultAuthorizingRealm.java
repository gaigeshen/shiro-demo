package work.gaigeshen.shiro.demo;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Objects;

/**
 *
 * @author gaigeshen
 */
public class DefaultAuthorizingRealm extends AuthorizingRealm {

  private final BearerTokenCreator bearerTokenCreator;

  private final UserService userService;

  private final PasswordMatcher passwordMatcher;

  public DefaultAuthorizingRealm(BearerTokenCreator bearerTokenCreator,
                                 UserService userService, PasswordService passwordService) {
    this.bearerTokenCreator = bearerTokenCreator;
    this.userService = userService;
    this.passwordMatcher = new PasswordMatcher();
    this.passwordMatcher.setPasswordService(passwordService);
    setCredentialsMatcher((token, info) -> {
      if (token instanceof UsernamePasswordToken) {
        return passwordMatcher.doCredentialsMatch(token, info);
      }
      return token instanceof BearerToken;
    });
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    UserProfile userProfile = (UserProfile) principals.getPrimaryPrincipal();
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    authorizationInfo.setRoles(userProfile.getRoles());
    authorizationInfo.setStringPermissions(userProfile.getPermissions());
    return authorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    if (token instanceof UsernamePasswordToken) {
      UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
      UserProfile userProfile = userService.findUserProfile(usernamePasswordToken.getUsername());
      UserPassword userPassword = userService.findUserPassword(usernamePasswordToken.getUsername());
      if (Objects.isNull(userProfile) || Objects.isNull(userPassword)) {
        throw new UnknownAccountException(usernamePasswordToken.getUsername());
      }
      return new SimpleAuthenticationInfo(userProfile, userPassword.getPassword(), "default");
    }
    else if (token instanceof BearerToken) {
      String tokenValue = ((BearerToken) token).getToken();
      UserProfile userProfile = bearerTokenCreator.parseToken(tokenValue);
      if (Objects.isNull(userProfile)) {
        throw new CredentialsException(tokenValue);
      }
      return new SimpleAuthenticationInfo(userProfile, tokenValue, "default");
    }
    throw new UnsupportedTokenException();
  }

  @Override
  public boolean supports(AuthenticationToken token) {
    Class<?> tokenClass = token.getClass();
    return BearerToken.class == tokenClass || UsernamePasswordToken.class == tokenClass;
  }

}
