package work.gaigeshen.shiro.demo.user;

/**
 *
 * @author gaigeshen
 */
public class UserPassword {

  private final String userId;

  private final String password;

  public UserPassword(String userId, String password) {
    this.userId = userId;
    this.password = password;
  }

  public String getUserId() {
    return userId;
  }

  public String getPassword() {
    return password;
  }
}
