<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'demo.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body>
    <div>
                参数：<input value="http://www.baidu.com" id="content" placeholder="二维码信息" >
                边距：<input value="1" id="margin" placeholder="边距" >
        <button onclick="refreshQRCode()">提交</button>
    </div>
    <div style="background: aliceblue ;padding: 10px;">
	    <img id="qrcode" src="${ctx }/demo/qrcode/getQRCode?content=参数123123123123">
	    <span></span>
	    <img id="logoqrcode" src="${ctx }/demo/qrcode/getLogQRCode?content=参数123123123123">
    </div> 
    <script type="text/javascript">
	    function refreshQRCode() {
	    	var content = $("#content").val();
	    	var margin = $("#margin").val();
	        $("#qrcode").attr("src","${ctx }/demo/qrcode/getQRCode?content=" + content +"&margin="+margin);
	        $("#logoqrcode").attr("src","${ctx }/demo/qrcode/getLogQRCode??content=" + content +"&margin="+margin);
	    }
    </script>
  </body>
</html>
