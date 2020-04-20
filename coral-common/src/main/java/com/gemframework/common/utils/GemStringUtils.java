/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: GemStringUtils.java
 * @Package: com.gemframework.util
 * @Date: 2019/11/28 23:12
 * @Version: v1.0
 * @Description: String工具类

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


    public static  String getKVFromJson(String jsonArr,String key){
        JSONArray jsonArray = JSONArray.parseArray(jsonArr);
        for (Object object : jsonArray) {
            if (object instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) object;
                if (jsonObject.containsKey(key)) {
                    String value = jsonObject.getString(key);
                    if(value !=null){
                        return value;
                    }
                }
            }
        }
        return null;
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
