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

import com.gemframework.common.exception.GemException;
import com.gemframework.model.annotation.ValidToken;
import com.gemframework.model.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect // 使用@Aspect注解声明一个切面
@Component
public class TokenAspect {
 
    /**
     * 这里我们使用注解的形式 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method 切点表达式:
     * execution(...)
     */
    @Pointcut(value = "@annotation(com.gemframework.model.annotation.ValidToken)")
    public void permitPointCut() {
        
    }
 
    /**
     * 环绕通知 @Around ， 当然也可以使用 @Before (前置通知) @After (后置通知)
     * 
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("permitPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //2.角色权限校验
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        if(method.isAnnotationPresent(ValidToken.class)){
            //获取方法上注解中表明的权限
            ValidToken validPermit = method.getAnnotation(ValidToken.class);
            if (validPermit != null) {
                // 注解上的描述
                if(!validPermit.isValid()){
                    //如果通过执行业务逻辑，放行
                    log.info("校验通过，进入业务层处理！");
                    return point.proceed();
                }else {
                    //如果没有权限,抛出异常,由Spring框架捕获,跳转到错误页面
                    log.info("验证token无效");
                    throw new GemException(ErrorCode.SYSTEM_EXCEPTION);
                }
            }
        }
        //如果通过执行业务逻辑，放行
        log.info("进入业务层处理！");
        return point.proceed();
    }

}