<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*,java.io.*,java.net.URLDecoder,java.net.URLEncoder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="imagePath" value="<%=com.infosky.common.constant.ConstantParam.IMAGEPATH%>" />
<c:set var="domainUrl" value="<%=com.infosky.common.constant.ConstantParam.DOMAINURL%>" />