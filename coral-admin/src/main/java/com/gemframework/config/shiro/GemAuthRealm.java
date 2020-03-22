package com.gemframework.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gemframework.common.constant.GemConstant;
import com.gemframework.model.entity.po.Right;
import com.gemframework.model.entity.po.Role;
import com.gemframework.model.entity.po.User;
import com.gemframework.service.RightService;
import com.gemframework.service.RoleService;
import com.gemframework.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@Slf4j
public class GemAuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RightService rightService;

    /**
     * 实现授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        log.info(username+"授权验证======================");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> rolesFlagSet = roleService.findRolesFlagByUsername(username);
        authorizationInfo.setRoles(rolesFlagSet);
        Set<Role> roles = roleService.findRolesByFlags(rolesFlagSet);
        log.info(username+"拥有角色标识======================"+rolesFlagSet);
        log.info(username+"拥有角色======================"+roles);
        // 把角色集合存到 Session 中
        SecurityUtils.getSubject().getSession().setAttribute("roleFlags", rolesFlagSet);
        SecurityUtils.getSubject().getSession().setAttribute("roles", roles);

        Set<String> rightsSet = new HashSet<>();
        //最高管理员
        if(rolesFlagSet.contains(GemConstant.Auth.ADMIN_ROLE_FLAG)){
            List<Right> rights = rightService.list();
            if(rights!=null && !rights.isEmpty()){
                for(Right right:rights){
                    if(right!=null && StringUtils.isNotBlank(right.getFlag())){
                        rightsSet.add(right.getFlag());
                    }
                }
            }
        }else{
            rightsSet = rightService.findRightsByRoles(roles);
        }
        log.info(username+"拥有权限======================"+rightsSet);
        authorizationInfo.setStringPermissions(rightsSet);
        // 把权限集合存到 Session 中
        SecurityUtils.getSubject().getSession().setAttribute("rights", rightsSet);
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
        User user = userService.getByUserName(username);
        if(user != null) {
            //账号锁定
            if(user.getStatus() == 1){
                throw new LockedAccountException("账号被锁定,请联系管理员");
            }
            // 把当前用户存到 Session 中
            SecurityUtils.getSubject().getSession().setAttribute("user", user);
            AuthenticationInfo authc = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()),"gemRealm");
            //认证成功就授权
            doGetAuthorizationInfo(authc.getPrincipals());
            return authc;
        } else {
            throw new UnknownAccountException("用户名或密码错误");
        }
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.HASH_ALGORITHM_NAME);
        shaCredentialsMatcher.setHashIterations(ShiroUtils.HASH_ITERATIONS);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}
