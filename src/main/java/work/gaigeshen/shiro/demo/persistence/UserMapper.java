package work.gaigeshen.shiro.demo.persistence;

import org.apache.ibatis.annotations.Mapper;
import work.gaigeshen.shiro.demo.persistence.record.User;

/**
 *
 * @author gaigeshen
 */
@Mapper
public interface UserMapper {

    User selectByUsername(String username);

}
