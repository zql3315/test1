package com.infosky.shiro.service;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 扩展shiro的认证,创建验证码异常类.
 * 
 * @author n004881
 * 
 */
public class CaptchaException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public CaptchaException() {

        super();

    }

    public CaptchaException(String message, Throwable cause) {

        super(message, cause);

    }

    public CaptchaException(String message) {

        super(message);

    }

    public CaptchaException(Throwable cause) {

        super(cause);

    }

}