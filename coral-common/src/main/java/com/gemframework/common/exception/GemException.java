/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.common.exception;

import com.gemframework.model.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @Title: GemException.java
 * @Package: com.gemframework.exception
 * @Date: 2019/11/27 22:16
 * @Version: v1.0
 * @Description: 系统约定业务异常
 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */
@Getter
@Setter
public class GemException extends RuntimeException {

    private Integer code;

    public GemException(ErrorCode resultCode){
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    public GemException(Integer code,String msg){
        super(msg);
        this.code = code;
    }
}
