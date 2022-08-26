package work.gaigeshen.shiro.demo;

/**
 *
 * @author gaigeshen
 */
public interface BearerTokenCreator {

  String createToken(UserProfile userDescriptor);

  UserProfile parseToken(String token);

}
