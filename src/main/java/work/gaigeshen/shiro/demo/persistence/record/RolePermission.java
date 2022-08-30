package work.gaigeshen.shiro.demo.persistence.record;

import lombok.Data;

/**
 * @author gaigeshen
 */
@Data
public class RolePermission {

    private long id;

    private long roleId;

    private long permissionId;

}
