//package com.gemframework.aspect;
//
//import java.lang.reflect.Method;
//import java.util.Map;
//import javax.servlet.http.HttpServletRequest;
//
//import com.gemframework.annotation.Log;
//import com.gemframework.model.common.BaseEntityVo;
//import com.gemframework.model.common.BaseResultData;
//import com.gemframework.model.entity.po.SysLogs;
//import com.gemframework.service.SysLogsService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ReflectionUtils;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//
///**
// * @Title: ControllerLogAopAspect
// * @Package: com.gemframework.aspect
// * @Date: 2020-04-04 16:42:37
// * @Version: v1.0
// * @Description: 这里写描述
// * @Author: nine QQ 769990999
// * @Copyright: Copyright (c) 2020 wanyong
// * @Company: www.gemframework.com
// */
//@Slf4j
//@Component
//@Aspect
//public class LogAopAspect {
//
//
//    //注入service,用来将日志信息保存在数据库
//    @Autowired
//    private SysLogsService sysLogsService;
//
//    //定义一个切入点
//    //指定controller的类进行切面　
//    @Pointcut("execution(* com.gemframework.controller..*.*(..))")
//    private void controllerAspect() {
//        log.info("point cut start");
//    }
//
//    @Around("controllerAspect()")
//    public Object around(ProceedingJoinPoint pjp) throws Throwable {
//        //常见日志实体对象
//        SysLogs sysLogs = new SysLogs();
//        //拦截的实体类，就是当前正在执行的controller
//        Object target = pjp.getTarget();
//        // 拦截的方法名称。当前正在执行的方法
//        String methodName = pjp.getSignature().getName();
//        // 拦截的方法参数
//        Object[] args = pjp.getArgs();
//        JSONArray operateParamArray = new JSONArray();
//        for (int i = 0; i < args.length; i++) {
//            Object paramsObj = args[i];
//            //通过该方法可查询对应的object属于什么类型：
//             String type = paramsObj.getClass().getName();
//            log.info("type=======================>"+type);
//            log.info("paramsObj=======================>"+paramsObj);
//            if (paramsObj instanceof String && paramsObj instanceof JSONObject) {
//                String str = (String) paramsObj;
//                log.info("=======================>"+str);
//                //将其转为jsonobject
//                JSONObject dataJson = JSONObject.parseObject(str);
//                if (dataJson == null || dataJson.isEmpty() || "null".equals(dataJson)) {
//                    break;
//                } else {
//                    operateParamArray.add(dataJson);
//                }
//            } else if (paramsObj instanceof Map) {
//                //get请求，以map类型传参
//                //1.将object的map类型转为jsonobject类型
//                Map<String, Object> map = (Map<String, Object>) paramsObj;
//                JSONObject json = new JSONObject(map);
//                operateParamArray.add(json);
//            }else if(paramsObj instanceof BaseEntityVo){
//                JSONObject dataJson = (JSONObject) JSONObject.toJSON(paramsObj);
//                operateParamArray.add(dataJson);
//            }
//        }
//        log.info("operateParamArray=======================>"+operateParamArray);
//        //设置请求参数
//        sysLogs.setParams(operateParamArray.toJSONString());
//        // 拦截注解参数类型
//        Signature signature = pjp.getSignature();
//        MethodSignature msig;
//        if (!(signature instanceof MethodSignature)) {
//            throw new IllegalArgumentException("该注解只能用于方法");
//        }
//        msig = (MethodSignature) signature;
//        Class[] parameterTypes = msig.getMethod().getParameterTypes();
//        Object object = null;
//        // 获得被拦截的方法
//        Method method = null;
//        try {
//            method = target.getClass().getMethod(methodName, parameterTypes);
//        } catch (NoSuchMethodException e1) {
//            log.error("ControllerLogAopAspect around error", e1);
//        } catch (SecurityException e1) {
//            log.error("ControllerLogAopAspect around error", e1);
//        }
//        if (null != method) {
//            // 判断是否包含自定义的注解，Log就是自定义的注解
//            if (method.isAnnotationPresent(Log.class)) {
//                //获取到自定义注解
//                Log annotationLog = method.getAnnotation(Log.class);
//                //请求查询操作前数据的spring bean
//                String serviceClass = annotationLog.serviceClass();
//                //请求查询数据的方法
//                String queryMethod = annotationLog.queryMethod();
//                //判断是否需要进行操作前的对象参数查询
//                if (StringUtils.isNotBlank(annotationLog.parameterKey())
//                        && StringUtils.isNotBlank(annotationLog.parameterType())
//                        && StringUtils.isNotBlank(annotationLog.queryMethod())
//                        && StringUtils.isNotBlank(annotationLog.serviceClass())) {
//                    //是否批量操作
//                    boolean isArrayResult = annotationLog.paramIsArray();
//                    //参数类型
//                    String paramType = annotationLog.parameterType();
//                    String key = annotationLog.parameterKey();
//
//                    if (isArrayResult) {//批量操作
//                        //从请求的参数中解析出查询key对应的value值
//                        JSONArray beforeParamArray = new JSONArray();
//                        for (int i = 0; i < operateParamArray.size(); i++) {
//                            JSONObject params = operateParamArray.getJSONObject(i);
//                            JSONArray paramArray = (JSONArray) params.get(key);
//                            if (paramArray != null) {
//                                for (int j = 0; j < paramArray.size(); j++) {
//                                    String paramId = paramArray.getString(j);
//                                    //在此处判断spring bean查询的方法参数类型
//                                    Object data = getOperateBeforeData(paramType, serviceClass, queryMethod, paramId);
//                                    JSONObject json = (JSONObject) JSON.toJSON(data);
//                                    beforeParamArray.add(json);
//                                }
//                            }
//                        }
//                        sysLogs.setBeforeParams(beforeParamArray.toJSONString());
//                    } else {//单量操作
//
//                        //从请求的参数中解析出查询key对应的value值
//                        String value = "";
//                        for (int i = 0; i < operateParamArray.size(); i++) {
//                            JSONObject params = operateParamArray.getJSONObject(i);
//                            value = params.getString(key);
//                            if (StringUtils.isNotBlank(value)) {
//                                break;
//                            }
//                        }
//                        //在此处获取操作前的spring bean的查询方法
//                        Object data = getOperateBeforeData(paramType, serviceClass, queryMethod, value);
//                        JSONObject beforeParam = (JSONObject) JSON.toJSON(data);
//                        sysLogs.setBeforeParams(beforeParam.toJSONString());
//                    }
//                }
//                try {
//                    //执行页面请求模块方法，并返回
//                    object = pjp.proceed();
//                    //将object 转化为controller封装返回的实体类：RequestResult
//                    BaseResultData resultData = (BaseResultData) object;
//                    if (resultData.getCode() == 0) {
//                        //操作流程成功
//                        if (StringUtils.isNotBlank(resultData.getMsg())) {
//                            sysLogs.setMsg(resultData.getMsg());
//                        } else if (resultData.getData() instanceof String) {
//                            sysLogs.setData((String) resultData.getData());
//                        } else {
//                            sysLogs.setMsg("执行成功");
//                        }
//                    } else {
//                        sysLogs.setMsg("失败");
//                    }
//                } catch (Throwable e) {
//                    sysLogs.setMsg(e.getMessage());
//                }
//
//                sysLogsService.save(sysLogs);
//            } else {
//                //没有包含注解
//                object = pjp.proceed();
//            }
//        } else {
//            //不需要拦截直接执行
//            object = pjp.proceed();
//        }
//        return object;
//    }
//
//    /**
//     * 获取操作前的参数
//     *
//     * @param paramType
//     * @param serviceClass
//     * @param queryMethod
//     * @param value
//     * @return
//     */
//    public Object getOperateBeforeData(String paramType, String serviceClass, String queryMethod, String value) {
//        Object obj = new Object();
//        //在此处解析请求的参数类型，根据id查询数据，id类型有四种：int，Integer,long,Long
//        if (paramType.equals("int")) {
//            int id = Integer.parseInt(value);
//            Method mh = ReflectionUtils.findMethod(SpringContextUtil.getBean(serviceClass).getClass(), queryMethod, Long.class);
//            //用spring bean获取操作前的参数,此处需要注意：传入的id类型与bean里面的参数类型需要保持一致
//            obj = ReflectionUtils.invokeMethod(mh, SpringContextUtil.getBean(serviceClass), id);
//
//        } else if (paramType.equals("Integer")) {
//            Integer id = Integer.valueOf(value);
//            Method mh = ReflectionUtils.findMethod(SpringContextUtil.getBean(serviceClass).getClass(), queryMethod, Long.class);
//            //用spring bean获取操作前的参数,此处需要注意：传入的id类型与bean里面的参数类型需要保持一致
//            obj = ReflectionUtils.invokeMethod(mh, SpringContextUtil.getBean(serviceClass), id);
//
//        } else if (paramType.equals("long")) {
//            long id = Long.parseLong(value);
//            Method mh = ReflectionUtils.findMethod(SpringContextUtil.getBean(serviceClass).getClass(), queryMethod, Long.class);
//            //用spring bean获取操作前的参数,此处需要注意：传入的id类型与bean里面的参数类型需要保持一致
//            obj = ReflectionUtils.invokeMethod(mh, SpringContextUtil.getBean(serviceClass), id);
//
//        } else if (paramType.equals("Long")) {
//            Long id = Long.valueOf(value);
//            log.info("queryMethod="+queryMethod);
//            log.info("SpringContextUtil.getBean(serviceClass).getClass()="+SpringContextUtil.getBean(serviceClass).getClass());
//            Method mh = ReflectionUtils.findMethod(SpringContextUtil.getBean(serviceClass).getClass(), queryMethod, Long.class);
//            //用spring bean获取操作前的参数,此处需要注意：传入的id类型与bean里面的参数类型需要保持一致
//            log.info("方法=="+mh);
//            obj = ReflectionUtils.invokeMethod(mh, SpringContextUtil.getBean(serviceClass), id);
//        }
//        return obj;
//    }
//}