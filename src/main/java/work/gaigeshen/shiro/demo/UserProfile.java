package work.gaigeshen.shiro.demo;

/**
 *
 * @author gaigeshen
 */
public class UserProfile {

  private final String userId;

  private final String username;

  public UserProfile(String userId, String username) {
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
