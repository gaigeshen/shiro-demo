package work.gaigeshen.shiro.demo.accesstoken;

/**
 * @author gaigeshen
 */
public interface AccessTokenGenerator {

  String generateToken(UserDescriptor userDescriptor);

  boolean verifyToken(String token);
}
