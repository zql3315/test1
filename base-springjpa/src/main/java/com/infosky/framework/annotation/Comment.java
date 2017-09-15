package com.infosky.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用来反向生成数据库表或字段时时插入描述
 * <p>指示注释类型所适用的程序元素的种类，如果注释类型声明中不存在 Target 元注释， 
 * <p>则声明的类型可以用在任一程序元素上。 
 * <p>ElementType.ANNOTATION_TYPE：注释类型声明 
 * <p>ElementType.CONSTRUCTOR：构造方法声明 
 * <p>ElementType.FILED：字段声明 
 * <p>ElementType.LOCAL_VARIABLE：局部变量声明 
 * <p>ElementType.METHOD：方法声明 
 * <p>ElementType.PACKAGE：包声明 
 * <p>ElementType.PARAMETER：参数声明 
 * <p>ElementType.TYPE：类、借口或枚举声明 
 * 
 * @author n004881
 *
 */
@Target({
        ElementType.TYPE, ElementType.FIELD
})
/** 
 * @Retention 指定注释的生存时期 
 * CLASS:注释记录在类文件中，但在运行时 VM 不需要保留注释。 
 * RUNTIME:注释记录在类文件中，在运行时 VM 将保留注释，因此可以使用反射机制读取注释内容。 
 * SOURCE:编译器要丢弃的注释。 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Comment {

    String value() default "";
}
