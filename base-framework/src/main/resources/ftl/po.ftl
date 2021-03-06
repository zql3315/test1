/*
 * 文 件 名:  ${table.eName}.java
 * 版    权:  XXXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  xx
 * 修改时间:  xx年xx月xx日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package ${project.rPackage}.${project.mName}.entity.po;

import com.infosky.framework.annotation.Comment;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.infosky.framework.entity.po.PO;

/**
 * 
 * ${table.description}
 *
 * @author  xx
 * @version  [版本号, xx年xx月xx日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name="${table.name}")
@Comment("${table.description}")
public class ${table.eName} extends PO<String>
{
    
    <#list columns as column>
    	/**
    	 * ${column.remarks}
    	 */
    	@Column(name="${column.columnName}")
    	@Comment("${column.remarks}")
    	private ${column.javaType} ${column.propertyName};
    	
    	/**
	     * @return 返回 ${column.propertyName}
	     */
	    public ${column.javaType} ${column.getter}()
	    {
	        return ${column.propertyName};
	    }
	
	    /**
	     * @param 对${column.propertyName}进行赋值
	     */
	    public void ${column.setter}(${column.javaType} ${column.propertyName})
	    {
	        this.${column.propertyName} = ${column.propertyName};
	    }
    </#list>
}
