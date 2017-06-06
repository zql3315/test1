<?xml version="1.0"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
  <groupId>${project.rPackage}</groupId>
  <artifactId>${project.name}</artifactId>
  <packaging>war</packaging>
  <version>1.2</version>
  <name>${project.name}</name>
  <url>http://maven.apache.org</url>

   <!-- 版本变量 -->
  <properties>
  	<junit-version>4.11</junit-version>
  	<spring-version>3.2.6.RELEASE</spring-version>
  	<spring-jpa-version>1.4.3.RELEASE</spring-jpa-version>
  	<hibernate-version>4.2.8.Final</hibernate-version>
  	<!-- <hibernate-annotations>4.0.2.Final</hibernate-annotations> -->
  	<hibernate-validator>4.3.1.Final</hibernate-validator>
  	<aspectj-version>1.7.3</aspectj-version>
  	<assertj.version>1.6.1</assertj.version>
  	<mysql-version>5.1.9</mysql-version>
  	<commons-dbcp-version>1.4</commons-dbcp-version>
  	<commons-lang3-version>3.1</commons-lang3-version>
  	<commons-io-version>2.4</commons-io-version>
  	<commons-codec-version>1.7</commons-codec-version>
  	<commons-beanutils-version>1.8.3</commons-beanutils-version>
  	<dozer.version>5.5.1</dozer.version>
  	<guava.version>17.0</guava.version>
  	<!-- 文件上传 -->
	<commons-fileupload-version>1.2.2</commons-fileupload-version>
	<jackson-version>2.3.0</jackson-version>
	<!-- sitemesh -->
	<sitemesh-version>2.4.2</sitemesh-version>
	<!-- shiro -->
	<shiro-version>1.2.2</shiro-version>
	<!-- slf4j -->
	<slf4j-version>1.7.5</slf4j-version>
	<logback-version>1.0.13</logback-version>
	<freemarker-version>2.3.20</freemarker-version>
	<mockito-version>2.0.2-beta</mockito-version>
  </properties>
  
  	<dependencies>
  		<dependency>
	      <groupId>junit</groupId>
	      <artifactId>junit</artifactId>
	      <version>${r"${junit-version}"}</version>
	      <scope>test</scope>
	    </dependency>
	    
	    <!-- SPRING MVC -->
	    <dependency>
	    	<groupId>org.springframework</groupId>
	    	<artifactId>spring-aop</artifactId>
	    	<version>${r"${spring-version}"}</version>
	    </dependency>
	    <dependency>
	    	<groupId>org.springframework</groupId>  
	        <artifactId>spring-context-support</artifactId> 
	        <version>${r"${spring-version}"}</version>
	    </dependency>
	    <dependency>
	    	<groupId>org.springframework</groupId>  
	        <artifactId>spring-jdbc</artifactId> 
	        <version>${r"${spring-version}"}</version>
	    </dependency>
	    <dependency>
	    	<groupId>org.springframework</groupId>  
	        <artifactId>spring-webmvc</artifactId>
	        <version>${r"${spring-version}"}</version>
	    </dependency>
	   
	    <dependency>
	    	<groupId>org.springframework</groupId>
			<artifactId>spring-orm</artifactId>
			<version>${r"${spring-version}"}</version> 
	    </dependency>
	    
	    <dependency>
	    	<groupId>org.springframework</groupId>
			<artifactId>spring-test</artifactId>
			<version>${r"${spring-version}"}</version> 
	    </dependency>
		<dependency>
			<groupId>org.mockito</groupId>
			<artifactId>mockito-all</artifactId>
			<version>${r"${mockito-version}"}</version>
		</dependency>
	    <!-- aop/tx -->
	    <dependency>
	    	<groupId>org.aspectj</groupId>
			<artifactId>aspectjrt</artifactId>
			<version>${r"${aspectj-version}"}</version>
	    </dependency>
	    <dependency>
	    	<groupId>org.aspectj</groupId>
			<artifactId>aspectjweaver</artifactId>
			<version>${r"${aspectj-version}"}</version>
	    </dependency>
	    
	    <!-- SPRING JPA -->
	    <dependency>
	    	<groupId>org.springframework.data</groupId>  
	        <artifactId>spring-data-jpa</artifactId>  
	        <version>${r"${spring-jpa-version}"}</version> 
	    </dependency>
	    
	    <!-- Hibernate -->
	    <dependency>
	    	<groupId>org.hibernate</groupId>  
	        <artifactId>hibernate-core</artifactId>  
	        <version>${r"${hibernate-version}"}</version>  
	    </dependency>
	    <dependency>
	    	<groupId>org.hibernate</groupId>  
	        <artifactId>hibernate-entitymanager</artifactId> 
	        <version>${r"${hibernate-version}"}</version>
	    </dependency>
	    <dependency>
	    	<groupId>org.hibernate</groupId>  
	        <artifactId>hibernate-validator</artifactId>
	        <version>${r"${hibernate-validator}"}</version>
	    </dependency>
	    <dependency>
	    	<groupId>org.hibernate</groupId>
			<artifactId>hibernate-ehcache</artifactId>
			<version>${r"${hibernate-version}"}</version>
	    </dependency>
	    
	    <!-- SECURITY begin -->
		<dependency>
			<groupId>org.apache.shiro</groupId>
			<artifactId>shiro-core</artifactId>
			<version>${r"${shiro-version}"}</version>
		</dependency>
		<dependency>
			<groupId>org.apache.shiro</groupId>
			<artifactId>shiro-spring</artifactId>
			<version>${r"${shiro-version}"}</version>
		</dependency>
		<dependency>
			<groupId>org.apache.shiro</groupId>
			<artifactId>shiro-web</artifactId>
			<version>${r"${shiro-version}"}</version>
		</dependency>
		<dependency>
			<groupId>org.apache.shiro</groupId>
			<artifactId>shiro-ehcache</artifactId>
			<version>${r"${shiro-version}"}</version>
		</dependency>
		<!-- SECURITY end -->
		
		<!-- dozer -->
		<dependency>
			<groupId>net.sf.dozer</groupId>
			<artifactId>dozer</artifactId>
			<version>${r"${dozer.version}"}</version>
		</dependency>
		
		<!-- google java library -->
		<dependency>
			<groupId>com.google.guava</groupId>
			<artifactId>guava</artifactId>
			<version>${r"${guava.version}"}</version>
		</dependency>	
		
		<!-- assertj -->
		<dependency>
			<groupId>org.assertj</groupId>
			<artifactId>assertj-core</artifactId>
			<version>${r"${assertj.version}"}</version>
			<scope>test</scope>
		</dependency>
				
	    <!-- DATABASE -->
	    <dependency>
	    	<groupId>mysql</groupId>  
	        <artifactId>mysql-connector-java</artifactId>
	        <version>${r"${mysql-version}"}</version>  
	    </dependency>
	    
	    <!-- optional datasource pool -->
		<dependency>
			<groupId>commons-dbcp</groupId>
			<artifactId>commons-dbcp</artifactId>
			<version>${r"${commons-dbcp-version}"}</version>
		</dependency>
				
	    <!-- JSTL -->
	    <dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>jstl</artifactId>
			<version>1.2</version>
		</dependency>
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>servlet-api</artifactId>
			<version>2.5</version>
		</dependency>
		<!-- general utils -->
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-lang3</artifactId>
			<version>${r"${commons-lang3-version}"}</version>
		</dependency>
		<dependency>
			<groupId>commons-codec</groupId>
			<artifactId>commons-codec</artifactId>
			<version>${r"${commons-codec-version}"}</version>
		</dependency>
		<dependency>
			<groupId>commons-beanutils</groupId>
			<artifactId>commons-beanutils</artifactId>
			<version>${r"${commons-beanutils-version}"}</version>
		</dependency>
		
		<!-- fileupload -->
		<dependency>
			<groupId>commons-fileupload</groupId>
			<artifactId>commons-fileupload</artifactId>
			<version>${r"${commons-fileupload-version}"}</version>
		</dependency>
		<dependency>
			<groupId>commons-io</groupId>
			<artifactId>commons-io</artifactId>
			<version>${r"${commons-io-version}"}</version>
		</dependency>
		<!-- template engine -->
		<dependency>
			<groupId>org.freemarker</groupId>
			<artifactId>freemarker</artifactId>
			<version>${r"${freemarker-version}"}</version>
		</dependency>
			
		<!-- JSON begin -->
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-core</artifactId>
			<version>${r"${jackson-version}"}</version>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>${r"${jackson-version}"}</version>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.module</groupId>
			<artifactId>jackson-module-jaxb-annotations</artifactId>
			<version>${r"${jackson-version}"}</version>
		</dependency>
		<!-- JSON end -->
		<dependency>
			<groupId>opensymphony</groupId>
			<artifactId>sitemesh</artifactId>
			<version>${r"${sitemesh-version}"}</version>
		</dependency>
		<!-- slf4j+logback日志 begin -->
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>${r"${slf4j-version}"}</version>
		</dependency>
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>jcl-over-slf4j</artifactId>
			<version>${r"${slf4j-version}"}</version>
		</dependency>
		<dependency>
			<groupId>ch.qos.logback</groupId>
			<artifactId>logback-core</artifactId>
			<version>${r"${logback-version}"}</version>
		</dependency>
		<dependency>
			<groupId>ch.qos.logback</groupId>
			<artifactId>logback-classic</artifactId>
			<version>${r"${logback-version}"}</version>
		</dependency>
		<!-- slf4j+logback日志 end -->
  	</dependencies>
  <build>
    <finalName>${project.name}</finalName>
  </build>	
</project>