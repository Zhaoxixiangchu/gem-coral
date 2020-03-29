/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.config.shiro;
import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.gemframework.config.shiro.session.GemCacheSessionDao;
import com.gemframework.config.shiro.session.GemSessionListener;
import com.gemframework.config.shiro.session.GemSessionManager;
import com.gemframework.config.shiro.session.GemRedisSessionDao;
import com.gemframework.config.shiro.cache.GemCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: ShiroConfig
 * @Package: com.gemframework.config.shiro
 * @Date: 2020-03-27 13:16:03
 * @Version: v1.0
 * @Description: Shiro配置
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@Configuration
public class ShiroConfig {

    @Autowired
    GemCacheManager shiroRedisCacheManager;

    /**
     * 配置shiro过滤器
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //注册securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置登录URL
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // authc:所有url都必须认证通过才可以访问;
        // anon:所有url都都可以匿名访问;
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/login1", "anon");
        filterChainDefinitionMap.put("/coral/**", "anon");
        filterChainDefinitionMap.put("/assets/**", "anon");
        filterChainDefinitionMap.put("/katcha/code", "anon");

        //注意：这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "authc");//剩余的都需要认证
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 注入安全管理器
     * @return SecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注册自定义realm
        securityManager.setRealm(gemAuthRealm());
        //注册记住我
        securityManager.setRememberMeManager(cookieRememberMeManager());
        //配置自定义session管理，使用redis
        securityManager.setSessionManager(sessionManager());
//        securityManager.setCacheManager(shiroRedisCacheManager);
        log.info("====SecurityManager注册完成====");
        return securityManager;
    }


    //注入自定义realm
    @Bean
    public GemAuthRealm gemAuthRealm() {
        GemAuthRealm gemAuthRealm = new GemAuthRealm();
        return gemAuthRealm;
    }

    //记住我管理器
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie simpleCookie = new SimpleCookie(ShiroUtils.REMEMBERME_COOKIE_NAME);
        simpleCookie.setMaxAge(ShiroUtils.REMEMBERME_COOKIE_MAXAGE);
        cookieRememberMeManager.setCookie(simpleCookie);
        cookieRememberMeManager.setCipherKey(ShiroUtils.REMEMBERME_CIPHERKEY.getBytes());
        return cookieRememberMeManager;
    }


    /**
     * 配置会话管理器，设定会话超时及保存
     * @return
     */
    @Bean("sessionManager")
    public SessionManager sessionManager() {
        GemSessionManager sessionManager = new GemSessionManager();
        //配置监听
//        Collection<SessionListener> listeners = new ArrayList<>();
//        listeners.add(sessionListener());
//        sessionManager.setSessionListeners(listeners);

        sessionManager.setSessionDAO(redisSessionDao());
//        sessionManager.setSessionDAO(cacheSessionDao());

        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
//        sessionManager.setGlobalSessionTimeout(1000 * 60 * 30);
//        //是否开启删除无效的session对象  默认为true
//        sessionManager.setDeleteInvalidSessions(true);
//        //是否开启定时调度器进行检测过期session 默认为true
//        sessionManager.setSessionValidationSchedulerEnabled(true);
//        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
//        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler
//        //底层也是默认自动调用ExecutorServiceSessionValidationScheduler
//        //暂时设置为 5秒 用来测试
//        sessionManager.setSessionValidationInterval(5000);
//        //取消url 后面的 JSESSIONID
//        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * 配置session监听
     * @return
     */
    @Bean("sessionListener")
    public GemSessionListener sessionListener(){
        GemSessionListener sessionListener = new GemSessionListener();
        return sessionListener;
    }

    /**
     * 配置session持久化
     * @return
     */
    @Bean("redisSessionDao")
    public GemRedisSessionDao redisSessionDao(){
        GemRedisSessionDao gemRedisSessionDao = new GemRedisSessionDao();
        return gemRedisSessionDao;
    }

    /**
     * 配置开启缓存+session持久化
     * @return
     */
    @Bean("cacheSessionDao")
    public GemCacheSessionDao cacheSessionDao(){
        GemCacheSessionDao gemCacheSessionDao = new GemCacheSessionDao();
        return gemCacheSessionDao;
    }



    //Shiro方言 用于页面标签/表达式
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    //开启注解
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}