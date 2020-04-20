/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.model.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gemframework.model.common.BaseEntityVo;
import lombok.Data;

import java.util.Date;

/**
 * @Title: SessionVo
 * @Date: 2020-04-18 21:24:41
 * @Version: v1.0
 * @Description: 系统会话表VO对象
 * @Author: gem
 * @Email: gemframe@163.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Data
public class SessionVo extends BaseEntityVo {

    /**
     * sessionId
     */
    private String sessionId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户IP
     */
    private String userIp;
    /**
     * 客户端系统 win linux macos ios andriod...
     */
    private String clientOs;
    /**
     * 客户端类型 pc moblie pad...
     */
    private String clientDevice;
    /**
     * 浏览器类型 类型+引擎+厂商
     */
    private String browser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startAccessTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastAccessTime;

    //默认超时时间
    private Long timeOut;

}
