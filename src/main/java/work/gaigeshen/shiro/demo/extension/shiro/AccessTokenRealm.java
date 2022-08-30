package work.gaigeshen.shiro.demo.extension.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import work.gaigeshen.shiro.demo.user.UserAuthorization;
import work.gaigeshen.shiro.demo.user.UserDescriptor;
import work.gaigeshen.shiro.demo.user.UserPassword;
import work.gaigeshen.shiro.demo.user.UserService;
import work.gaigeshen.shiro.demo.user.accesstoken.AccessTokenCreator;
import work.gaigeshen.shiro.demo.user.accesstoken.DefaultAccessTokenCreator;

import java.util.Objects;

/**
 *
 * @author gaigeshen
 */
public class AccessTokenRealm extends AuthorizingRealm {

    private final PasswordService passwordService;

    private final UserService userService;

    private final AccessTokenCreator accessTokenCreator;

    private AccessTokenRealm(PasswordService passwordService, UserService userService, AccessTokenCreator accessTokenCreator) {
        this.passwordService = passwordService;
        this.userService = userService;
        this.accessTokenCreator = accessTokenCreator;
        setCredentialsMatcher(new InternalCredentialsMatcher());
    }

    public static AccessTokenRealm create(PasswordService passwordService, UserService userService, AccessTokenCreator accessTokenCreator) {
        return new AccessTokenRealm(passwordService, userService, accessTokenCreator);
    }

    public static AccessTokenRealm create(PasswordService passwordService, UserService userService) {
        return create(passwordService, userService, DefaultAccessTokenCreator.create());
    }

    public static AccessTokenRealm create(UserService userService) {
        return create(Md5PasswordService.INSTANCE, userService);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserDescriptor principal = (UserDescriptor) principals.getPrimaryPrincipal();
        UserAuthorization userAuthorization = userService.findUserAuthorization(principal.getUsername());
        if (Objects.isNull(userAuthorization)) {
            return null;
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(userAuthorization.getRoles());
        authorizationInfo.setStringPermissions(userAuthorization.getPermissions());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token instanceof UsernamePasswordToken) {
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
            String username = usernamePasswordToken.getUsername();
            UserDescriptor userDescriptor = userService.findUserDescriptor(username);
            if (Objects.isNull(userDescriptor)) {
                return null;
            }
            UserPassword userPassword = userService.findUserPassword(username);
            if (Objects.isNull(userPassword)) {
                return null;
            }
            return new SimpleAuthenticationInfo(userDescriptor, userPassword.getPassword(), getClass().getName());
        }
        else if (token instanceof AccessToken) {
            AccessToken accessToken = (AccessToken) token;
            UserDescriptor userDescriptor = accessTokenCreator.validateToken(accessToken.getToken());
            if (Objects.isNull(userDescriptor)) {
                return null;
            }
            return new SimpleAuthenticationInfo(userDescriptor, accessToken.getToken(), getClass().getName());
        }
        throw new UnsupportedTokenException();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken || token instanceof AccessToken;
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
          return token instanceof AccessToken;
      }
    }
}
