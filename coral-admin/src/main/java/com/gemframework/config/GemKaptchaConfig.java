/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @Description Kaptcha配置类 用于生成图形验证码
 * @Author chenlinghong
 * @Date 2019/1/25 10:31
 **/
@Component
public class GemKaptchaConfig {

    @Bean
    public DefaultKaptcha getDefaultKapcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
//        properties.setProperty("kaptcha.border", "yes");
//        properties.setProperty("kaptcha.border.color", "white");
//        properties.setProperty("kaptcha.textproducer.font.color", "green");
//        properties.setProperty("kaptcha.textproducer.font.size", "30");
//        properties.setProperty("kaptcha.textproducer.font.names", "微软雅黑");
//        properties.setProperty("kaptcha.textproducer.char.length", "5");
//        properties.setProperty("kaptcha.image.width", "100");
//        properties.setProperty("kaptcha.image.height", "40");
//        properties.setProperty("kaptcha.session.key", "code");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}