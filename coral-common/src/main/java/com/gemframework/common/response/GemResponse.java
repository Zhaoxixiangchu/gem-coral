/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.common.response;

import com.alibaba.fastjson.JSON;
import com.gemframework.common.constant.GemConstant;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.model.enums.ErrorURL;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title: CopyUtils.java
 * @Package: com.gemframework.util
 * @Date: 2019/11/28 23:12
 * @Version: v1.0
 * @Description: 对象返回

 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */
@Slf4j
public class GemResponse {

    public static void returnResult(HttpServletRequest request, HttpServletResponse response, ErrorCode errorCode, ErrorURL url) throws IOException {
        request.setCharacterEncoding(GemConstant.Character.UTF8);
        response.setCharacterEncoding(GemConstant.Character.UTF8);

        //判断请求头。如果包含"Authorization"则为第三方应用请求
        if(request.getHeader(GemConstant.Auth.HEADER_AUTHOR) == null){
            log.debug("访问类型：pages");
            String redirectUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+url.getUrl();
            response.sendRedirect(redirectUrl);
        }else{
            log.debug("访问类型：api");
            response.getWriter().println(JSON.toJSONString(BaseResultData.ERROR(errorCode)));
        }
    }
}
