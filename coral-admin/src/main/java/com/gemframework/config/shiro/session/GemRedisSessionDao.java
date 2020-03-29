/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.config.shiro.session;

import com.gemframework.common.utils.GemRedisUtils;
import com.gemframework.common.utils.GemSerializeUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GemRedisSessionDao extends AbstractSessionDAO {

    @Autowired
    GemRedisUtils<String> gemRedisUtils;

    private final String SHIRO_SESSION_PERFIX = "shiro-session:";
    private String getKey(String key){
        return SHIRO_SESSION_PERFIX + key;
    }

    protected void saveSession(Session session) throws IOException {
        if(session != null && session.getId() != null){
            String key = getKey(session.getId().toString());
            gemRedisUtils.set(key, GemSerializeUtils.serialize(session));
            gemRedisUtils.expire(key,30, TimeUnit.MINUTES);
        }
    }

    @SneakyThrows
    @Override
    protected Serializable doCreate(Session session) {
        SessionIdGenerator sessionIdGenerator = getSessionIdGenerator();
        Serializable sessionId = sessionIdGenerator.generateId(session);
        assignSessionId(session,sessionId);
        saveSession(session);
        log.debug("创建sessionId:"+sessionId+"==session:"+session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            return null;
        }
        String key = getKey(sessionId.toString());
        Session session = null;
        String value = gemRedisUtils.get(key);
        if(value != null){
            try {
                session = (Session) GemSerializeUtils.serializeToObject(value);
//                log.info("读取key:"+key+"==session:"+session);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return session;
    }


    @SneakyThrows
    @Override
    public void update(Session session) throws UnknownSessionException {
        log.debug("更新session:"+session);
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        log.debug("删除session:"+session);
        if(session == null || session.getId() == null){
            return;
        }
        String key = getKey(session.getId().toString());
        gemRedisUtils.delete(key);
    }

    /**
     * 获取所有存活的seesion
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions() {
        Set<String> keys = gemRedisUtils.keys(SHIRO_SESSION_PERFIX+"*");
        Set<Session>  sessions = new HashSet<>();
        if(keys == null && keys.isEmpty()){
            return sessions;
        }
        for(String key : keys){
            Session session = null;
            try {
                session = (Session) GemSerializeUtils.serializeToObject(gemRedisUtils.get(key));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            sessions.add(session);
        }
        return sessions;
    }
}
