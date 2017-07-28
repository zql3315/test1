package com.infosky.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述注解,用于标识控制层类对应模块或内部方法实现功能
 * 
 * @author  n004881
 * @version  [版本号, 2015年4月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {
        ElementType.TYPE, ElementType.METHOD
})
public @interface Description {

    String value() default "";
}
