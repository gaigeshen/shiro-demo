package work.gaigeshen.shiro.demo;

import java.util.Collections;
import java.util.Set;

/**
 *
 * @author gaigeshen
 */
public class UserAuthorization {

  private final String userId;

  private final Set<String> roles;

  private final Set<String> permissions;

  public UserAuthorization(String userId, Set<String> roles, Set<String> permissions) {
    this.userId = userId;
    this.roles = roles;
    this.permissions = permissions;
  }

  public String getUserId() {
    return userId;
  }

  public Set<String> getRoles() {
    return Collections.unmodifiableSet(roles);
  }

  public Set<String> getPermissions() {
    return Collections.unmodifiableSet(permissions);
  }
}
