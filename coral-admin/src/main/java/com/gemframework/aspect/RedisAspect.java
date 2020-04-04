/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */

package com.gemframework.aspect;
import com.gemframework.common.config.redis.GemRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * Redis切面处理类
 *
 * @author gemteam
 * @email 769990999@qq.com
 * @date 2016-07-17 23:30
 */
@Slf4j
@Aspect
@Component
public class RedisAspect {
    @Autowired
    private GemRedisProperties gemRedisProperties;

    @Around("execution(* com.gemframework.common.utils.GemRedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        log.info("==== RedisUtils ===================== ===="+gemRedisProperties.isOpen());
        if(gemRedisProperties.isOpen()){
            log.info("==== RedisUtils 已开启 ====");
            try{
                result = point.proceed();
            }catch (Exception e){
                log.error("redis error", e);
            }
        }else{
            log.info("==== RedisUtils 已关闭 ====");
        }
        return result;
    }
}
