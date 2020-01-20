package test.shiro权限管理;

import java.util.Set;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/8
 * @描述
 */
public interface UserService  {
    public Set<String> getRoles(String username);

    Set<String> getPermissions(String username);

    User getByUsername(String username);
}
