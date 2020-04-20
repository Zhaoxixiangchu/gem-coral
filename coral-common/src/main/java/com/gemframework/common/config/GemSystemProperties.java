/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.common.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Title: GemConfig.java
 * @Package: com.gemframework.config
 * @Date: 2019/11/27 23:31
 * @Version: v1.0
 * @Description: 自定义配置文件
 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */
@Data
@Component
@ConfigurationProperties(prefix = "gem.system")
public class GemSystemProperties {

    private BigDecimal min;
    private BigDecimal max;
    private String desc;
    //配置
    private String generateCodeServerPath;
    //运行环境 test 测试、demo演示、dev开发、prod生产
    private String runtime;
    private boolean cluster;
}
