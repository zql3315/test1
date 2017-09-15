package com.infosky.framework.service.exception;

/**
 * 
 * 自定义非业务处理异常
 * 
 * @author  n004881
 * @version  [版本号, 2014年8月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceException extends RuntimeException {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2760119236917743386L;

    public ServiceException() {
        super();
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(Throwable t) {
        super(t);
    }

    public ServiceException(String msg, Throwable t) {
        super(msg, t);
    }
}
