package com.gemframework.common.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @Title: GemAuthRealm
 * @Package: com.gemframework.common.shiro
 * @Date: 2020-03-08 15:41:42
 * @Version: v1.0
 * @Description: 这里写描述
 * @Author: zysh666
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
public class GemAuthRealm extends AuthorizingRealm {


    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {


        return null;
    }
}
