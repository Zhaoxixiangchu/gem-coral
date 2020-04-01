/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.config.shiro.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @Title: RedisSessionDao
 * @Package: com.gemframework.config.session
 * @Date: 2020-03-29 10:39:48
 * @Version: v1.0
 * @Description: 父级提供了缓存功能，实现Redis持久化方案
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
public class GemCacheSessionDao extends EnterpriseCacheSessionDAO {

    @Autowired
    private GemRedisSessionDao gemRedisSessionDao;

    @Override
    protected Serializable doCreate(Session session) {
        log.debug("Create-SessionId:"+session);
        return gemRedisSessionDao.doCreate(session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {

        log.debug("Read-SessionId:"+sessionId);
        return gemRedisSessionDao.doReadSession(sessionId);
    }

    @Override
    protected void doUpdate(Session session) {
        log.debug("Update-Session:"+session);
        gemRedisSessionDao.update(session);
    }

    @Override
    protected void doDelete(Session session) {
        log.debug("Delete-Session:"+session);
        gemRedisSessionDao.delete(session);
    }
}
