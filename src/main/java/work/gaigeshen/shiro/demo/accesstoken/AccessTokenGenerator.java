package work.gaigeshen.shiro.demo.accesstoken;

/**
 * @author gaigeshen
 */
public interface AccessTokenGenerator {

  String generateToken(String userId, String username);

  boolean verifyToken(String token);
}
