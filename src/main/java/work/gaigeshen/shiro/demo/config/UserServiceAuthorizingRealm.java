package work.gaigeshen.shiro.demo.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import work.gaigeshen.shiro.demo.*;

import java.util.Objects;

/**
 *
 * @author gaigeshen
 */
public class UserServiceAuthorizingRealm extends AuthorizingRealm {

  private final PasswordService passwordService;

  private final UserService userService;

  private final BearerTokenCreator bearerTokenCreator;

  public UserServiceAuthorizingRealm(PasswordService passwordService,
                                     UserService userService, BearerTokenCreator bearerTokenCreator) {
    this.passwordService = passwordService;
    this.userService = userService;
    this.bearerTokenCreator = bearerTokenCreator;
    setCredentialsMatcher(new InternalCredentialsMatcher());
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    UserProfile principal = (UserProfile) principals.getPrimaryPrincipal();
    UserAuthorization userAuthorization = userService.findUserAuthorization(principal.getUsername());
    if (Objects.isNull(userAuthorization)) {
      return null;
    }
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    authorizationInfo.setRoles(userAuthorization.getRoles());
    authorizationInfo.setStringPermissions(userAuthorization.getPermissions());
    return authorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    if (token instanceof UsernamePasswordToken) {
      return doGetAuthenticationInfo((UsernamePasswordToken) token);
    } else if (token instanceof BearerToken) {
      return doGetAuthenticationInfo((BearerToken) token);
    }
    throw new UnsupportedTokenException();
  }

  private AuthenticationInfo doGetAuthenticationInfo(UsernamePasswordToken usernamePasswordToken) {
    String username = usernamePasswordToken.getUsername();
    UserProfile userProfile = userService.findUserProfile(username);
    if (Objects.isNull(userProfile)) {
      return null;
    }
    UserPassword userPassword = userService.findUserPassword(username);
    if (Objects.isNull(userPassword)) {
      return null;
    }
    return new SimpleAuthenticationInfo(userProfile, userPassword.getPassword(), getClass().getName());
  }

  private AuthenticationInfo doGetAuthenticationInfo(BearerToken bearerToken) {
    UserProfile userProfile = bearerTokenCreator.parseToken(bearerToken.getToken());
    if (Objects.isNull(userProfile)) {
      return null;
    }
    return new SimpleAuthenticationInfo(userProfile, bearerToken.getToken(), getClass().getName());
  }

  /**
   *
   * @author gaigeshen
   */
  private class InternalCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
      if (token instanceof UsernamePasswordToken) {
        return passwordService.passwordsMatch(token.getCredentials(), (String) info.getCredentials());
      }
      return token instanceof BearerToken;
    }
  }
}
