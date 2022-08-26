package work.gaigeshen.shiro.demo;

/**
 *
 * @author gaigeshen
 */
public interface UserService {

  UserProfile findUserProfile(String username);

  UserAuthorization findUserAuthorization(String username);

  UserPassword findUserPassword(String username);
}
