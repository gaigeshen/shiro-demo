package work.gaigeshen.shiro.demo.persistence;

import org.apache.ibatis.annotations.Mapper;
import work.gaigeshen.shiro.demo.persistence.record.Permission;

import java.util.List;
import java.util.Set;

/**
 *
 * @author gaigeshen
 */
@Mapper
public interface PermissionMapper {

    List<Permission> selectByIds(Set<Long> ids);
}
