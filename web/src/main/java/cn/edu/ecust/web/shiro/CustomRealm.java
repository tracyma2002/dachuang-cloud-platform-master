package cn.edu.ecust.web.shiro;


import cn.edu.ecust.domain.entity.Permission;
import cn.edu.ecust.domain.entity.Role;
import cn.edu.ecust.service.user.UserService;
import cn.edu.ecust.web.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Shen Yifan
 * @Description ***
 * @Date 2020/10/25 19:09
 */
@Component
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String userId = JWTUtil.getUserId(token);
        if (userId == null || !JWTUtil.verify(token, userId)) {
            throw new AuthenticationException("token认证失败！");
        }
        String password = userService.getPasswordByUserId(userId);
        if (password == null) {
            throw new AuthenticationException("该用户不存在！");
        }

        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("————权限认证————");
        String userId = JWTUtil.getUserId(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        //0表示管理员，1表示学生，2表示教师
        Role role = userService.getRoleByUserId(userId);
        String roleName = role.getRoleName();

        List<String> permissions = userService.getPermissionListByRoleId(role.getRoleId()).stream().map(Permission::getPermissionCode).collect(Collectors.toList());
        System.out.println(permissions);
//                userMapper.getRole(username);
        //每个角色拥有默认的权限


        //需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
        Set<String> roleSet = new HashSet<>();
        Set<String> permissionSet = new HashSet<>();
        //设置该用户拥有的角色和权限
        roleSet.add(roleName);
        permissionSet.addAll(permissions);
        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
        return info;
    }
}
