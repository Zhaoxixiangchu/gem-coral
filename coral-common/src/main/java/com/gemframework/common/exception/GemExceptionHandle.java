/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.common.exception;

import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: GemException.java
 * @Package: com.gemframework.gembasic.exception
 * @Date: 2019/11/27 21:53
 * @Version: v1.0
 * @Description: 异常统一捕获类
 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */
@ControllerAdvice
public class GemExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(GemExceptionHandle.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResultData handleExp(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        if (ex instanceof GemException) {
            logger.error("GemException: code:{},msg:{}" ,((GemException) ex).getCode(), ex.getMessage());
            GemException gemException = (GemException) ex;
            return BaseResultData.ERROR(gemException.getCode(), gemException.getMessage());
        } else {
            logger.error("System Exception: code:{},msg:{}" , ex.getStackTrace(),ex.getMessage());
            return BaseResultData.ERROR(ErrorCode.SYSTEM_EXCEPTION.getCode(), ErrorCode.SYSTEM_EXCEPTION.getMsg());
        }
    }
}
