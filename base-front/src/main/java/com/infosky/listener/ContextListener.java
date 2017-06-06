package com.infosky.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * ServletContext监听器和ServletContext属性监听器
 * 
 * @author n004881
 */
@WebListener
public class ContextListener implements ServletContextAttributeListener, ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("ServletContext destroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("ServletContext initialized");
    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        logger.info("ServletContext attribute added");
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        logger.info("ServletContext attribute removed");
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        logger.info("ServletContext attribute replaced");
    }

}
