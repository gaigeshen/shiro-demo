package work.gaigeshen.shiro.demo.user;

/**
 *
 * @author gaigeshen
 */
public interface UserService {

  UserDescriptor findUserDescriptor(String username);

  UserAuthorization findUserAuthorization(String username);

  UserPassword findUserPassword(String username);
}
