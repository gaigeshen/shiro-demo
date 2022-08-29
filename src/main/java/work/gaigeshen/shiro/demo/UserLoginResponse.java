package work.gaigeshen.shiro.demo;

/**
 *
 * @author gaigeshen
 */
public interface UserLoginResponse {

  String getToken();

  UserProfile getProfile();
}
