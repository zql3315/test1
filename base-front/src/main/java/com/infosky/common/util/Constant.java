package com.infosky.common.util;

import java.io.Serializable;

/**
 * 错误编码常量类
 * @author n005843
 *
 */
public class Constant implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7904696886134626445L;

    /**
     * 表示成功
     */
    public static final String SUCCESS = "0000";

    /**
     * 表示用户名或者密码错误
     */
    public static final String LOGINFAIL = "0001";

    /**
     * 表示账号不存在
     */
    public static final String NOTFOUND = "0002";

    /**
     * 表示参数缺少
     */
    public static final String PARAMFAIL = "0003";

    /**
     * 表示参数格式不正确
     */
    public static final String PARAMERROR = "00031";

    /**
     * 表示服务器异常
     */
    public static final String SERVEREXCEPTION = "0004";

    /**
     * 表示数据不存在
     */
    public static final String DATANOTFOUND = "0005";

    /**
     * 签名错误
     */
    public static final String SINGERROR = "0006";

    /**
     * 其他
     */
    public static final String ELSE = "0007";

    public static final String DESKEY = "wang!@#$%";

}
