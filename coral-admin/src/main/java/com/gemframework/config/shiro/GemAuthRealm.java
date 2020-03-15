package com.gemframework.config.shiro;

import com.gemframework.model.entity.po.User;
import com.gemframework.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title: GemAuthRealm
 * @Package: com.gemframework.common.shiro
 * @Date: 2020-03-08 15:41:42
 * @Version: v1.0
 * @Description: 自定义Realm
 * @Author: qq769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
public class GemAuthRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;

    /**
     * 实现授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 给该用户设置角色，角色信息存在 gem_role 表中取
        authorizationInfo.setRoles(userService.getRoles(username));
        // 给该用户设置权限，权限信息存在 gem_right 表中取
        authorizationInfo.setStringPermissions(userService.getRights(username));
        return authorizationInfo;
    }

    /**
     * 实现认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        UsernamePasswordToken token = new UsernamePasswordToken();
        User user = userService.getByUserName(username);
        if(user != null) {
            // 把当前用户存到 Session 中
            SecurityUtils.getSubject().getSession().setAttribute("user", user);
            AuthenticationInfo authc = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), "gemRealm");
            return authc;
        } else {
            return null;
        }
    }
}
