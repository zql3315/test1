package com.infosky.sys.entity.dto;

import com.infosky.framework.entity.dto.DTO;

/**
 * 工程项目
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OperationDTO extends DTO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * 名称
     */
    private String name;

    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }

    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 简称
     */
    private String sn;

    /**
     * @return 返回 sn
     */
    public String getSn() {
        return sn;
    }

    /**
     * @param 对sn进行赋值
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

}
