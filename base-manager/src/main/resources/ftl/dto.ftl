/*
 * 文 件 名:  ${table.eName}.java
 * 版    权:   Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  xx
 * 修改时间:  xx年xx月xx日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package ${project.rPackage}.${project.mName}.entity.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.infosky.framework.entity.dto.DTO;
import com.infosky.framework.entity.utils.CustomJsonDateDeserialize;
import com.infosky.framework.entity.utils.CustomJsonDateSerializer;

/**
 * ${table.description}
 * 
 * @author  xx
 * @version  [版本号, xx年xx月xx日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ${table.eName}DTO extends DTO<String>
{
    
     <#list columns as column>
    	/**
    	 * ${column.remarks}
    	 */
    	<#if column.javaType == "java.util.Date">
    	@JsonSerialize(using = CustomJsonDateSerializer.class)
    	@JsonDeserialize(using = CustomJsonDateDeserialize.class)
		</#if>
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
