/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.model.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gemframework.model.common.BaseEntityPo;
import lombok.Data;

/**
 * @Title: Session
 * @Date: 2020-04-18 21:24:41
 * @Version: v1.0
 * @Description: 系统会话表实体
 * @Author: gem
 * @Email: gemframe@163.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */

@TableName("gem_sys_session")
@Data
public class Session extends BaseEntityPo {

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
     * 客户端系统 0 win 1 mac os 2 iOS 3 andriod 4 linux 99 other
     */
    private Integer clientOs;
    /**
     * 客户端类型 0 谷歌 1 Firefox 2 IE 3 X5 4 Safari 5 webview 99 other
     */
    private Integer clientType;

}

