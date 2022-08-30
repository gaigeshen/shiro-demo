package work.gaigeshen.shiro.demo.persistence;

import org.apache.ibatis.annotations.Mapper;
import work.gaigeshen.shiro.demo.persistence.record.UserRole;

import java.util.List;
import java.util.Set;

/**
 * @author gaigeshen
 */
@Mapper
public interface UserRoleMapper {

    List<UserRole> selectByUserIds(Set<Long> userIds);
}
