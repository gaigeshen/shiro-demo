package work.gaigeshen.shiro.demo;

/**
 *
 * @author gaigeshen
 */
public class UserPassword {

  private final String userId;

  private final String username;

  private final String password;

  public UserPassword(String userId, String username, String password) {
    this.userId = userId;
    this.username = username;
    this.password = password;
  }

  public String getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
