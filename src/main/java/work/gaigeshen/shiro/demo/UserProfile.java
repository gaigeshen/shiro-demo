package work.gaigeshen.shiro.demo;

import java.util.Collections;
import java.util.Set;

/**
 *
 * @author gaigeshen
 */
public class UserProfile {

  private final String userId;

  private final String username;

  private final Set<String> roles;

  private final Set<String> permissions;

  public UserProfile(String userId, String username,
                     Set<String> roles, Set<String> permissions) {
    this.userId = userId;
    this.username = username;
    this.roles = roles;
    this.permissions = permissions;
  }

  public String getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public Set<String> getRoles() {
    return Collections.unmodifiableSet(roles);
  }

  public Set<String> getPermissions() {
    return Collections.unmodifiableSet(permissions);
  }
}
