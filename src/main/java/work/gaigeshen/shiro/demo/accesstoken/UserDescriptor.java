package work.gaigeshen.shiro.demo.accesstoken;

/**
 *
 * @author gaigeshen
 */
public class UserDescriptor {

  private final String userId;

  private final String username;

  public UserDescriptor(String userId, String username) {
    this.userId = userId;
    this.username = username;
  }

  public String getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }
}
