package com.infosky.shiro.session.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.infosky.common.util.SerializeUtil;
import com.infosky.redis.RedisUtil;

/**
 * 用redis重写shrio的sessionDAO
 * 
 * @author n004881
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    /**
     * 保存session的key的列表的表名
     */
    private String keyPrefix = "shiro_redis_session";

    /**
     * 过滤
     */
    private String filterUrl = "";

    /**
     * 过期时间（秒）
     */
    private int expire = 30 * 60;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HttpServletRequest request;

    /**
     * 创建会话
     * 
     * @see org.apache.shiro.session.mgt.eis.AbstractSessionDAO#doCreate(org.apache.shiro.session.Session)
     **/
    @Override
    protected Serializable doCreate(Session session) {
        logger.debug("doCreate");
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    /**
     * //TODO 添加override说明
     * @see org.apache.shiro.session.mgt.eis.SessionDAO#update(org.apache.shiro.session.Session)
     **/
    @Override
    public void update(Session session) throws UnknownSessionException {
        List<String> filterUrls = Arrays.asList(filterUrl.split(","));
        if (filterUrls.contains(request.getRequestURI()) && !filterUrls.contains(".jsp")) {
            return;
        }
        logger.debug("update");
        this.saveSession(session);
    }

    /**
     * 删除 session
     * 
     * @see org.apache.shiro.session.mgt.eis.SessionDAO#delete(org.apache.shiro.session.Session)
     **/
    @Override
    public void delete(Session session) {
        logger.debug("delete");
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        redisUtil.del(session.getId().toString());
        redisUtil.hdel(this.keyPrefix, session.getId().toString());

    }

    /**
     * 获取所有在线session
     * 
     * @see org.apache.shiro.session.mgt.eis.SessionDAO#getActiveSessions()
     **/
    @Override
    public Collection<Session> getActiveSessions() {
        logger.debug("getActiveSessions");
        Set<Session> sessions = new HashSet<Session>();
        Set<String> fields = redisUtil.hkeys(this.keyPrefix);
        if (fields != null && fields.size() > 0) {
            for (String key : fields) {
                byte[] bytes = redisUtil.get(key.getBytes());
                if (bytes != null) {
                    Session s = (Session) SerializeUtil.deserialize(redisUtil.get(key.getBytes()));
                    sessions.add(s);
                } else {
                    redisUtil.hdel(this.keyPrefix, key);
                }
            }
        }

        return sessions;
    }

    /**
     * 这个地方有一个bug:一次请求会调用10几次改方法 网上介绍说这个是shiro在设计上的问题， 主要是因为shiro 框架获取 session里面的属性时，每次都去拿取session， 
     * 一次请求中会有很多次 获取 session 里面的属性，所以有很多次，这个如果是本地缓存到无所谓，因为本地缓存是直接放置session对象的， 但是如果是共享缓存比如 redis ,
     * 这个就郁闷了，每次获取session都要从redis 里面获取然后反序列化。 这个只有修改shiro 才能把问题解决 待解决
     * 
     * @see org.apache.shiro.session.mgt.eis.AbstractSessionDAO#doReadSession(java.io.Serializable)
     **/
    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.error("session id is null");
            return null;
        }
        List<String> filterUrls = Arrays.asList(filterUrl.split(","));
        if (filterUrls.contains(request.getRequestURI()) && !filterUrls.contains(".jsp")) {
            return null;
        }
        logger.debug("doReadSession");
        Session s = (Session) SerializeUtil.deserialize(redisUtil.get(this.getByteKey(sessionId)));
        return s;
    }

    /**
     * 保存 session
     * 
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        byte[] key = getByteKey(session.getId());
        byte[] value = SerializeUtil.serialize(session);
        session.setTimeout(expire * 1000);
        //redisUtil.hset(keyPrefix, session.getId().toString(), "");
        redisUtil.setex(key, expire, value);
    }

    /**
     * 获得byte[]型的key
     * 
     * @param key
     * @return
     */
    private byte[] getByteKey(Serializable sessionId) {
        String preKey = sessionId.toString();
        return preKey.getBytes();
    }

    /**
     * Returns the Redis session keys prefix.
     * 
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key prefix.
     * 
     * @param keyPrefix
     *            The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public String getFilterUrl() {
        return filterUrl;
    }

    public void setFilterUrl(String filterUrl) {
        this.filterUrl = filterUrl;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

}
