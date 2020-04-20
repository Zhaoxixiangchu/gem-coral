/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.model.enums;

import lombok.Getter;

/**
 * @Title: CodeType.java
 * @Package: com.gemframework.enum
 * @Date: 2019/11/27 22:28
 * @Version: v1.0
 * @Description: 枚举类

 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */
@Getter
public enum ChannelType {

    //注册渠道 1 手动添加 2 自助注册 3 oauth
    ADD(1,"手动添加"),
    ONESELF(2,"自助注册"),
    OAUTH(3,"oauth"),
    OTHER(99,"其他"),
    ;


    private Integer code;
    private String msg;

    ChannelType(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
