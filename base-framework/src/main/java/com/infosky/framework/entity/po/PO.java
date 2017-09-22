package com.infosky.framework.entity.po;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.infosky.framework.annotation.Comment;

/**
 * 抽象持久对象
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@MappedSuperclass
public abstract class PO<I extends Serializable> implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -5979559917779705765L;

    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuidGenerator")
    @Comment(value = "主键")
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

}
