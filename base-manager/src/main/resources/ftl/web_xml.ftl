<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	version="2.5">
  <display-name>${project.name}</display-name>
  
  <context-param>
  	<param-name>contextConfigLocation</param-name>
  	<param-value>classpath*:/applicationContext.xml</param-value>
  </context-param>
  
  <context-param>
	<param-name>spring.profiles.default</param-name>
	<param-value>development</param-value>
  </context-param>
  
  <listener>
  	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  
   <!-- 核心控制器 -->  
   <servlet>  
       <servlet-name>springServlet</servlet-name>  
       <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>  
       <init-param>  
           <param-name>contextConfigLocation</param-name>  
           <param-value>/WEB-INF/spring-mvc.xml</param-value>  
       </init-param>  
       <load-on-startup>1</load-on-startup>  
   </servlet>
   
   <servlet-mapping>  
       <servlet-name>springServlet</servlet-name>  
       <url-pattern>/</url-pattern>  
   </servlet-mapping>
   
   <filter>
		<filter-name>sitemeshFilter</filter-name>
		<filter-class>com.opensymphony.sitemesh.webapp.SiteMeshFilter</filter-class>
   </filter>
   <filter-mapping>
		<filter-name>sitemeshFilter</filter-name>
		<url-pattern>/*</url-pattern>
   </filter-mapping>
</web-app>
