package work.gaigeshen.shiro.demo;

/**
 *
 * @author gaigeshen
 */
public interface UserService {

  UserProfile findUserProfile(String username);

  UserAuthorization findUserAuthorization(String username);

  UserPassword findUserPassword(String username);

  UserRegisterResponse register(UserRegisterParameters registerParameters);

  UserLoginResponse login(UserLoginParameters loginParameters);

  void logout(UserLogoutParameters logoutParameters);
}
