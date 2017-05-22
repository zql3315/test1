<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>	
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ztree demo</title>
<link href="${ctx}/static/ztree/css/zTreeStyle.css" rel="stylesheet" type="text/css"/>
</head>
<body>

	<div class="alert alert-block alert-success">
		<button class="close" type="button" data-dismiss="alert">
			<i class="ace-icon fa fa-times"></i>
		</button>
	
		<i class="ace-icon fa fa-check green"></i>
	
		Welcome to
		<strong class="green">
			Ace
			<small>(v1.3)</small>
		</strong>,
			the lightweight, feature-rich and easy to use admin template.
	</div>

<script src="${ctx}/static/ztree/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="${ctx}/static/ztree/jquery.ztree.excheck-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	
});

</script>
</body>
</html>