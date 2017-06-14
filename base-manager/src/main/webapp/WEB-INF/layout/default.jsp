<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<c:set var="imagePath" value="<%=com.infosky.common.constant.ConstantParam.IMAGEPATH%>"/> 
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- http-equiv="X-UA-Compatible"这个是IE8的专用标记，是用来指定Internet Explorer 8 浏览器模拟某个特定版本IE浏览器的渲染方式，以此来解决IE浏览器的兼容问题。 -->
	<!-- 如果安装了GCF，则使用GCF来渲染页面「"chrome=1"」，如果没有安装GCF，则使用最高版本的IE内核进行渲染「"IE=edge"」 -->
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<!-- 控制移动终端触摸 maximum-scale - 允许用户缩放到的最大比例 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
	
	<!-- bootstrap & fontawesome -->
	<link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctx}/static/fontawesome/css/font-awesome.min.css" />

	<!-- jquery ui -->
	<link rel="stylesheet" href="${ctx}/static/jquery/css/jquery-ui.min.css"/>
	<!-- -->
	<link rel="stylesheet" href="${ctx}/static/datatable/css/jquery.dataTables.css"/> 
	<link rel="stylesheet" href="${ctx}/static/datatable/css/dataTables.bootstrap.css"/>
	<link rel="stylesheet" href="${ctx}/static/datatable/css/jquery.dataTables_themeroller.css"/>
	
	<link rel="stylesheet" href="${ctx}/static/datetimepicker/css/bootstrap-datetimepicker.min.css"/>
	
	<!-- chosen -->
	<link rel="stylesheet" href="${ctx}/static/chosen/css/chosen.css"/>
	
	<!-- text fonts -->
	<link rel="stylesheet" href="${ctx}/static/ace/css/ace-fonts.css"/>
	<!-- ace styles -->
	<link rel="stylesheet" href="${ctx}/static/ace/css/ace.css" />
	<!--[if lte IE 9]>
		<link rel="stylesheet" href="${ctx}/static/css/ace-part2.min.css" />
	<![endif]-->
	<link rel="stylesheet" href="${ctx}/static/ace/css/ace-skins.min.css">
	<link rel="stylesheet" href="${ctx}/static/ace/css/ace-rtl.css">
	<!--[if lte IE 9]>
		  <link rel="stylesheet" href="../assets/css/ace-ie.min.css" />
		<![endif]-->
	<!-- ace settings handler -->
	<script src="${ctx}/static/ace/js/ace-extra.min.js"></script>
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="${ctx}/static/js/html5shiv.js"></script>
	<script src="${ctx}/static/js/respond.min.js"></script>
	<![endif]-->

	<link rel="stylesheet" href="${ctx}/static/style.css" />
<decorator:head/>
<title><decorator:title/></title>
</head>
<body class="no-skin">
<decorator:body></decorator:body>

<!--   <script src="${ctx}/static/jquery/js/jquery.min.js"></script> -->
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
<script type="text/javascript">
	if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/static/jquery/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	var basePath = '${ctx}/';
	var imagePath = '${imagePath}/';
	window.UEDITOR_HOME_URL = "${ctx }/static/bdedit/";
</script>
<script src="${ctx}/static/jquery/js/jquery-ui.min.js"></script>
<script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<%--<script src="${ctx}/static/bootstrap/js/common.js"></script>--%>
<!--  <script src="${ctx}/static/jquery/js/jquery-ui.custom.min.js"></script>
<script src="${ctx}/static/jquery/js/jquery.ui.touch-punch.min.js"></script>-->
<!-- ace scripts -->
<script src="${ctx}/static/ace/js/ace-elements.min.js"></script>
<script src="${ctx}/static/ace/js/ace.min.js"></script>

<!-- page specific plugin scripts -->
<script src="${ctx}/static/datatable/js/jquery.dataTables.js"></script>
<script src="${ctx}/static/datatable/js/dataTables.bootstrap.js"></script>

<script src="${ctx}/static/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="${ctx}/static/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

<!-- chosen -->
<script src="${ctx}/static/chosen/js/chosen.jquery.js"></script>

<!-- custom -->
<script src="${ctx}/static/custom/b2r.min.js"></script>
<script src="${ctx}/static/bootstrap/js/common.js"></script>
</body>
</html>