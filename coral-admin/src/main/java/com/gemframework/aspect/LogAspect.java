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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gemframework.annotation.Log;
import com.gemframework.common.queue.GemQueueMessage;
import com.gemframework.common.queue.RedisMQProducer;
import com.gemframework.common.utils.GemHttpUtils;
import com.gemframework.common.utils.GemIPHandler;
import com.gemframework.model.common.BaseEntityVo;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.entity.po.SysLogs;
import com.gemframework.service.queue.MapQueueMessage;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.gemframework.common.constant.GemCommonRedisKeys.Queue.LOG_SYNC_DB;
import static com.gemframework.common.constant.GemCommonRedisKeys.Queue.LOG_SYNC_DB_SAVE;

@Component  //声明组件
@Aspect //  声明切面
@ComponentScan  //组件自动扫描
@EnableAspectJAutoProxy //spring自动切换JDK动态代理和CGLIB
@Slf4j
public class LogAspect {
    private long startTime;
    private long endTime;
    private String username;

    @Autowired
    RedisMQProducer<Map<String, Object>> redisMQProducer;

    /**
     * 在方法执行前进行切面
     */
    @Pointcut("execution(* com.gemframework.controller..*.*(..))" +
            "&& (@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public void log() {
    }


    /**
     * 程序执行之前
     */
    @Before("log()")
    public void before() {
        this.startTime = System.currentTimeMillis();
        this.username = (String) SecurityUtils.getSubject().getPrincipal();
        log.debug("程序执行前");
    }

    /**
     * 程序执行之后
     */
    @After("log()")
    public void after(){
        this.endTime = System.currentTimeMillis();
        log.debug("程序执行后");
    }


    @Around("log()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        this.endTime = System.currentTimeMillis();
        //保存日志
        saveLog(point, result, (endTime - startTime));
        log.debug("程序执行时间 {}-{}={}", endTime,startTime,endTime - startTime);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, Object result, long time) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogs logs = new SysLogs();
        Log log = method.getAnnotation(Log.class);
        if(StringUtils.isBlank(username)){
            this.username = (String) SecurityUtils.getSubject().getPrincipal();
        }
        if(log != null && StringUtils.isNotBlank(username)){
            //用户名
            logs.setUsername(username);

            //注解上的描述
            logs.setOperation(log.value());
            //请求的方法名
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = signature.getName();
            logs.setMethod(className + "." + methodName + "()");
            //请求的参数
            Object[] args = joinPoint.getArgs();
            try{
                String params = new Gson().toJson(args[0]);
                logs.setParams(params);

                //将object 转化为controller封装返回的实体类：RequestResult
                BaseResultData resultData = (BaseResultData) result;
                if (resultData.getCode() == 0) {
                    //操作流程成功
                    if (StringUtils.isNotBlank(resultData.getMsg())) {
                        logs.setMsg(resultData.getMsg());
                    } else if (resultData.getData() instanceof String) {
                        logs.setData(resultData.getData());
                    } else {
                        logs.setMsg("成功");
                    }
                } else {
                    logs.setMsg("失败");
                }
            }catch (Exception e){
                logs.setMsg(e.getMessage());
            }catch (Throwable e) {
                logs.setMsg(e.getMessage());
            }

            //获取request
            HttpServletRequest request = GemHttpUtils.getHttpServletRequest();
            //设置IP地址
            logs.setIp(GemIPHandler.getIpAddr(request));
            logs.setTimes(time);
            logs.setCreateTime(new Date());
            logs.setUpdateTime(new Date());
            GemQueueMessage<Map<String,Object>> mapQueueMessage = new MapQueueMessage();
            Map<String,Object> map = new HashMap<>();
            map.put(LOG_SYNC_DB_SAVE,logs);
            mapQueueMessage.setData(map);
            //向key为cacheKey的队列发送消息
            redisMQProducer.send(LOG_SYNC_DB,mapQueueMessage);
        }
    }


    public static String getParams(ProceedingJoinPoint point){
        // 拦截的方法参数
        Object[] args = point.getArgs();
        JSONArray operateParamArray = new JSONArray();
        for (int i = 0; i < args.length; i++) {
            Object paramsObj = args[i];
            //通过该方法可查询对应的object属于什么类型：
            String type = paramsObj.getClass().getName();
            if (paramsObj instanceof String && paramsObj instanceof JSONObject) {
                String str = (String) paramsObj;
                //将其转为jsonobject
                JSONObject dataJson = JSONObject.parseObject(str);
                if (dataJson == null || dataJson.isEmpty() || "null".equals(dataJson)) {
                    break;
                } else {
                    operateParamArray.add(dataJson);
                }
            } else if (paramsObj instanceof Map) {
                //get请求，以map类型传参
                //1.将object的map类型转为jsonobject类型
                Map<String, Object> map = (Map<String, Object>) paramsObj;
                JSONObject json = new JSONObject(map);
                operateParamArray.add(json);
            }else if(paramsObj instanceof BaseEntityVo){
                JSONObject dataJson = (JSONObject) JSONObject.toJSON(paramsObj);
                operateParamArray.add(dataJson);
            }
        }
        return operateParamArray.toJSONString();
    }
}