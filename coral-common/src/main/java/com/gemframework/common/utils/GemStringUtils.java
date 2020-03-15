package com.gemframework.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Title: CopyUtils.java
 * @Package: com.gemframework.util
 * @Date: 2019/11/28 23:12
 * @Version: v1.0
 * @Description: 复制对象工具类

 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */
public class GemStringUtils {

    /**
     * 下划线转驼峰
     */
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 驼峰转下划线
     */
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    public static void main(String[] args) throws Exception {
//        SysLog log = new SysLog();
//        log.setAccount("a");
//        log.setClientIp("b");
//        log.setCreateTime(new Date());
//
//        SysLogVo sysLogVo = GemBeanUtils.copyProperties(log,SysLogVo.class);
//        System.out.println("1==="+sysLogVo.toString());
//
//        SysLogVo sysLogVo2 = new SysLogVo();
//        GemBeanUtils.cpoyObjAttr(log,sysLogVo2,log.getClass());
//        System.out.println("2==="+sysLogVo2.toString());
//
//        SysLogVo sysLogVo3 = new SysLogVo();
//        GemBeanUtils.copyProperties(log,sysLogVo3);
//        System.out.println("3==="+sysLogVo3.toString());
//
//        SysLogVo sysLogVo4 = new SysLogVo();
//        sysLogVo4 = BeanMapper.map(log,SysLogVo.class);
//        System.out.println("4==="+sysLogVo4.toString());
    }
}
