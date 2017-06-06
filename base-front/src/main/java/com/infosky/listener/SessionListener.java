package com.infosky.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * HttpSession监听器和HttpSession属性监听器
 * 
 * @author n004881
 */
@WebListener
public class SessionListener implements HttpSessionAttributeListener, HttpSessionListener {

    private static Logger logger = LoggerFactory.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("session created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("session destroyed");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        logger.info("session attribute added");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        logger.info("session attribute removed");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        logger.info("session attribute replaced");
    }

}
