<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <title>未授权</title>
  </head>
  <body>
  	<div class="container">
  	  <div class="row">
  	  	<div class="col-xs-12 col-md-12">
  	  		<h1>未授权 ${message}</h1>
  	  	</div>
			</div>
		</div>
  </body>
</html>