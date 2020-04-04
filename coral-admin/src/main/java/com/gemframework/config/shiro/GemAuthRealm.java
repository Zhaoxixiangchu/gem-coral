/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.config.shiro;

import com.gemframework.common.config.GemSystemProperties;
import com.gemframework.common.config.redis.GemRedisProperties;
import com.gemframework.common.constant.GemConstant;
import com.gemframework.common.utils.GemRedisUtils;
import com.gemframework.model.entity.po.Right;
import com.gemframework.model.entity.po.Role;
import com.gemframework.model.entity.po.User;
import com.gemframework.model.enums.ErrorCode;
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
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.gemframework.constant.GemRedisKeys.Auth.USER_RIGHTS;
import static com.gemframework.constant.GemRedisKeys.Auth.USER_ROLES;

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

    @Qualifier("shiroUserServiceImpl")
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RightService rightService;

    @Autowired
    private GemRedisUtils gemRedisUtils;

    @Autowired
    private GemRedisProperties gemRedisProperties;
    @Autowired
    private GemSystemProperties gemSystemProperties;

    public String getUserRolesKey(String username){
        return username + "_" + USER_ROLES;
    }
    public String getUserRightsKey(String username){
        return username  + "_" + USER_RIGHTS;
    }

    /**
     * 实现授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        //授权验证
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取拥有角色标识组
        Set<String> rolesFlagSet = roleService.findRolesFlagByUsername(username);
        authorizationInfo.setRoles(rolesFlagSet);
        Set<String> rightsSet = new HashSet<>();
        //最高管理员
        if(rolesFlagSet.contains(GemConstant.Auth.ADMIN_ROLE_FLAG)){
            List<Right> rights = rightService.list();
            if(rights!=null && !rights.isEmpty()){
                for(Right right:rights){
                    if(right!=null && StringUtils.isNotBlank(right.getFlags())){
                        rightsSet.addAll(Arrays.asList(right.getFlags().trim().split(",")));
                    }
                }
            }
        }else{
            //获取拥有角色
            Set<Role> roles = roleService.findRolesByFlags(rolesFlagSet);
            rightsSet = rightService.findRightsByRoles(roles);
        }
        authorizationInfo.setStringPermissions(rightsSet);

        //如果开启了redis则设置用户角色，权限到redis
        if(gemRedisProperties.isOpen()){
            gemRedisUtils.set(getUserRolesKey(username),rolesFlagSet);
            gemRedisUtils.set(getUserRightsKey(username),rightsSet);
        }else{
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute(getUserRolesKey(username),rolesFlagSet);
            session.setAttribute(getUserRightsKey(username),rightsSet);
        }
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
                throw new LockedAccountException(ErrorCode.LOGIN_FAIL_LOCKEDACCOUNT.getMsg());
            }
            // 把当前用户存到 Session 中
            SecurityUtils.getSubject().getSession().setAttribute("user", user);
            SecurityUtils.getSubject().getSession().setAttribute("session_runtime",gemSystemProperties.getRuntime());
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
