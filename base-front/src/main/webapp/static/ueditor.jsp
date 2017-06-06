<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'edit.jsp' starting page</title>
    <meta charset="UTF-8">
    <script type="text/javascript">
    	window.UEDITOR_HOME_URL = "/hn-manager/static/bdedit/";
    </script>
  </head>
  
  <body>
    <form action="server.php" method="post">
        <!-- 加载编辑器的容器 -->
        <script id="container" name="content" type="text/plain">
            这里写你的初始化内容
        </script>
    </form>
    <!-- 配置文件 -->
    <script type="text/javascript" src="bdedit/ueditor.config.js"></script>
    <script type="text/javascript" src="bdedit/third-party/jquery-1.10.2.min.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="bdedit/ueditor.all.js"></script>
    <!-- 实例化编辑器 -->
    <script type="text/javascript">
	    var ue = UE.getEditor('container', {
	        autoHeightEnabled: true,
	        autoFloatEnabled: true
	    });
        ue.ready(function() {
            //设置编辑器的内容
            ue.setContent('hello');
            //获取html内容，返回: <p>hello</p>
            var html = ue.getContent();
            //获取纯文本内容，返回: hello
            var txt = ue.getContentTxt();
        });
    </script>
  </body>
</html>
