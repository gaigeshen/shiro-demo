package work.gaigeshen.shiro.demo;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Objects;

/**
 * 默认的认证授权处理器，只支持用户名和密码的方式以及访问令牌的方式
 *
 * @author gaigeshen
 */
public class DefaultAuthorizingRealm extends AuthorizingRealm {

  private final BearerTokenCreator bearerTokenCreator;

  private final UserService userService;

  /**
   * 创建此认证授权处理器
   *
   * @param bearerTokenCreator 访问令牌生成器用于在认证的时候校验访问令牌是否合法
   * @param userService 用户服务用于查询用户信息和密码
   * @param passwordService 密码服务用于在认证的时候校验密码是否合法
   */
  public DefaultAuthorizingRealm(BearerTokenCreator bearerTokenCreator,
                                 UserService userService, PasswordService passwordService) {
    this.bearerTokenCreator = bearerTokenCreator;
    this.userService = userService;
    setCredentialsMatcher(new InternalCredentialsMatcher(passwordService));
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
      UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
      UserProfile userProfile = userService.findUserProfile(usernamePasswordToken.getUsername());
      if (Objects.isNull(userProfile)) {
        return null;
      }
      UserPassword userPassword = userService.findUserPassword(usernamePasswordToken.getUsername());
      if (Objects.isNull(userPassword)) {
        return null;
      }
      return new SimpleAuthenticationInfo(userProfile, userPassword.getPassword(), "default");
    } else if (token instanceof BearerToken) {
      String tokenValue = ((BearerToken) token).getToken();
      UserProfile userProfile = bearerTokenCreator.parseToken(tokenValue);
      if (Objects.isNull(userProfile)) {
        throw new CredentialsException(tokenValue);
      }
      return new SimpleAuthenticationInfo(userProfile, tokenValue, "default");
    }
    throw new UnsupportedTokenException();
  }

  /**
   *
   * @author gaigeshen
   */
  private static class InternalCredentialsMatcher implements CredentialsMatcher {

    private final PasswordMatcher passwordMatcher;

    private InternalCredentialsMatcher(PasswordService passwordService) {
      PasswordMatcher passwordMatcher = new PasswordMatcher();
      passwordMatcher.setPasswordService(passwordService);
      this.passwordMatcher = passwordMatcher;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
      if (token instanceof UsernamePasswordToken) {
        return passwordMatcher.doCredentialsMatch(token, info);
      }
      return token instanceof BearerToken;
    }
  }
}
