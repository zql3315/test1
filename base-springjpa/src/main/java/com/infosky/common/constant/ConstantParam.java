package com.infosky.common.constant;

import com.infosky.common.util.PropertiesConfig;

public class ConstantParam
{

    public static final String IMAGEPATH = PropertiesConfig.readValue("imagePath");
    
    /**
     * 公民身份号码
     */
    public static final String GMSFZ = "gmsfhm";
    /**
     * 用户虚拟账户
     */
    public static final String ACCOUNT = "account";

}
