package work.gaigeshen.shiro.demo.user;

import java.util.Collections;
import java.util.Set;

/**
 *
 * @author gaigeshen
 */
public class UserAuthorization {

  private final long userId;

  private final Set<String> roles;

  private final Set<String> permissions;

  public UserAuthorization(long userId, Set<String> roles, Set<String> permissions) {
    this.userId = userId;
    this.roles = roles;
    this.permissions = permissions;
  }

  public long getUserId() {
    return userId;
  }

  public Set<String> getRoles() {
    return Collections.unmodifiableSet(roles);
  }

  public Set<String> getPermissions() {
    return Collections.unmodifiableSet(permissions);
  }
}
