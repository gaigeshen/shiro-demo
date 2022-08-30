package work.gaigeshen.shiro.demo.persistence;

import org.apache.ibatis.annotations.Mapper;
import work.gaigeshen.shiro.demo.persistence.record.Role;

import java.util.List;
import java.util.Set;

/**
 *
 * @author gaigeshen
 */
@Mapper
public interface RoleMapper {

    List<Role> selectByIds(Set<Long> ids);

}
