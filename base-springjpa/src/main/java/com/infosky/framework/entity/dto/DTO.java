package com.infosky.framework.entity.dto;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.infosky.framework.View;

/**
 * 控制层与业务层传递数据的抽象模型
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@MappedSuperclass
public abstract class DTO<I extends Serializable> implements View {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -8941681623716663279L;

    private I id;

    /**
     * @return 返回 id
     */
    public I getId() {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(I id) {
        this.id = id;
    }

    /**
     * 重写tostring,格式化实体类tostring的风格
     * {@inheritDoc}
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
