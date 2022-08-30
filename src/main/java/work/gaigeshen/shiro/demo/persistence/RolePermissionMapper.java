package work.gaigeshen.shiro.demo.persistence;

import org.apache.ibatis.annotations.Mapper;
import work.gaigeshen.shiro.demo.persistence.record.RolePermission;

import java.util.List;
import java.util.Set;

/**
 * @author gaigeshen
 */
@Mapper
public interface RolePermissionMapper {

    List<RolePermission> selectByRoleIds(Set<Long> roleIds);
}
