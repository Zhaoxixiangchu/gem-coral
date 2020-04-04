/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.common.utils;


import com.gemframework.common.queue.GemQueueMessage;
import com.gemframework.common.queue.RedisMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: GemHttpUtils.java
 * @Package: com.gemframework.util
 * @Date: 2019/11/28 23:12
 * @Version: v1.0
 * @Description: Redis队列工具

 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */
public class GemRedisMQUtils {

    RedisMQProducer redisMQProducer = new RedisMQProducer();


    /**
     * 生产map消息
     * @param gemQueueMessage 消息体模版
     * @param cacheKey 缓存key
     * @param mapKay mapKey
     * @param obj 消息对象
     * @throws Exception
     */
    public void mapMQProducer(GemQueueMessage<Map<String,Object>> gemQueueMessage,
                                     String cacheKey, String mapKay, Object obj) throws Exception {
        //先组装map
        Map<String,Object> map = new HashMap<>();
        map.put(mapKay,obj);
        gemQueueMessage.setData(map);
        //向key为cacheKey的队列发送消息
        redisMQProducer.send(cacheKey,gemQueueMessage);
    }
}
