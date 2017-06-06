<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>错误提示</title>
  </head>
  
  <body>
     服务器现在很忙，真的很忙！请耐心等候或者联系客服 <br>
    <div style="display: none;">
	    <% 
	    Exception ex = (Exception) request.getAttribute("exception");
	    if(ex !=null) out.println(ex.fillInStackTrace());
	    out.clear();  
        out = pageContext.pushBody(); 
	    %>
    </div>
  </body>
</html>
