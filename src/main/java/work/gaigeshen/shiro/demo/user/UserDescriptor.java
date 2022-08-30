package work.gaigeshen.shiro.demo.user;

import java.util.Objects;

/**
 *
 * @author gaigeshen
 */
public class UserDescriptor {

  private final long userId;

  private final String username;

  public UserDescriptor(long userId, String username) {
    this.userId = userId;
    this.username = username;
  }

  public long getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (Objects.isNull(other) || getClass() != other.getClass()) {
      return false;
    }
    UserDescriptor that = (UserDescriptor) other;
    return Objects.equals(userId, that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }

  @Override
  public String toString() {
    return username + "(" + userId + ")";
  }
}
