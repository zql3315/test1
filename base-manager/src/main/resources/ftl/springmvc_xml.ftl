<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-3.2.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.2.xsd">
	
	<!-- 自动扫描且只扫描@Controller -->
	<context:component-scan base-package="${project.rPackage}">
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
		<!-- @ControllerAdvice注解内部使用@ExceptionHandler、@InitBinder、@ModelAttribute注解的方法应用到所有的 @RequestMapping注解的方法 -->
		<context:include-filter type="annotation" expression="org.springframework.web.bind.annotation.ControllerAdvice"/>
	</context:component-scan>

	<mvc:annotation-driven>
		<mvc:message-converters register-defaults="true">
			<!-- 将StringHttpMessageConverter的默认编码设为UTF-8 -->
			<bean class="org.springframework.http.converter.StringHttpMessageConverter">
		    	<constructor-arg value="UTF-8" />
			</bean>
			<bean class="org.springframework.http.converter.ByteArrayHttpMessageConverter" />
			<!-- 将Jackson2HttpMessageConverter的默认格式化输出设为true -->
			<bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="prettyPrint" value="true"/>
            </bean>
  		</mvc:message-converters>
	</mvc:annotation-driven>
	
	<!-- 定义JSP文件的位置 -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/${project.viewDir}/" />
		<property name="suffix" value=".jsp" />
	</bean>
	
	<!-- 容器默认的DefaultServletHandler处理 所有静态内容与无RequestMapping处理的URL-->
	<mvc:default-servlet-handler/>
	<!-- 定义无需Controller的url<->view直接映射 -->
	<mvc:view-controller path="/" view-name="index"/>
	
	<!-- 将Controller抛出的异常转到特定View, 保持SiteMesh的装饰效果
	<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
		<property name="exceptionMappings">
			<props>
				<prop key="java.lang.Throwable">error/500</prop>
			</props>
		</property>
	</bean> -->
</beans>