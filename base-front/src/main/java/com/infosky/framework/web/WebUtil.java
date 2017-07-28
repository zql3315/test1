package com.infosky.framework.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WebUtil implements ApplicationContextAware {

    private static ApplicationContext cx;

    /** {@inheritDoc} */

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        cx = applicationContext;
    }

    public static ApplicationContext getContext() {
        return cx;
    }
}
