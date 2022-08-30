package work.gaigeshen.shiro.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.gaigeshen.shiro.demo.persistence.*;
import work.gaigeshen.shiro.demo.persistence.record.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author gaigeshen
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRoleMapper userRoleMapper;

    private final RoleMapper roleMapper;

    private final RolePermissionMapper rolePermissionMapper;

    private final PermissionMapper permissionMapper;

    @Override
    public UserDescriptor findUserDescriptor(String username) {
        User user = userMapper.selectByUsername(username);
        if (Objects.isNull(user)) {
            return null;
        }
        return new UserDescriptor(user.getId(), user.getUsername());
    }

    @Override
    public UserAuthorization findUserAuthorization(String username) {
        User user = userMapper.selectByUsername(username);
        if (Objects.isNull(user)) {
            return null;
        }
        List<UserRole> userRoles = userRoleMapper.selectByUserIds(Collections.singleton(user.getId()));
        if (userRoles.isEmpty()) {
            return new UserAuthorization(user.getId(), Collections.emptySet(), Collections.emptySet());
        }
        Set<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        List<Role> roles = roleMapper.selectByIds(roleIds);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByRoleIds(roleIds);
        if (rolePermissions.isEmpty()) {
            return new UserAuthorization(user.getId(), roles.stream().map(Role::getName).collect(Collectors.toSet()), Collections.emptySet());
        }
        List<Permission> permissions = permissionMapper.selectByIds(rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toSet()));
        if (permissions.isEmpty()) {
            return new UserAuthorization(user.getId(), roles.stream().map(Role::getName).collect(Collectors.toSet()), Collections.emptySet());
        }
        return new UserAuthorization(user.getId(), roles.stream().map(Role::getName).collect(Collectors.toSet()), permissions.stream().map(Permission::getName).collect(Collectors.toSet()));
    }

    @Override
    public UserPassword findUserPassword(String username) {
        return null;
    }
}
