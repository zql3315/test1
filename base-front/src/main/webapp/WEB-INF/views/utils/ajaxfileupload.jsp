<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<%@ include file="../common/path.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>异步上传demo</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1">
    <script src="${ctx }/static/jquery/js/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="${ctx }/static/jquery/js/ajaxfileupload.js" type="text/javascript"></script>
    <script type="text/javascript">
        function ajaxFileUpload() {
            $.ajaxFileUpload
            (
                {
                    url: '${ctx}/fileUpload/image', //用于文件上传的服务器端请求地址
                    secureuri: false, //一般设置为false
                    fileElementId: 'file', //文件上传空间的id属性  <input type="file" id="file" name="file" />
                    dataType: 'json', //返回值类型 一般设置为json
                    success: function (data, status)  //服务器成功响应处理函数
                    {
                       alert(data.msg);
                       if(data.result){
                    	   $("#img1").attr("src", imagePath+data.fileUrl);
                       }
                       console.log(data);
                    },
                    error: function (data, status, e)//服务器响应失败处理函数
                    {
                        alert(e);
                    }
                }
            )
            return false;
        }
    </script>
</head>
<body>
    <input type="file" id="file" name="file"  value="上传"   onchange="ajaxFileUpload()" />
    <p><img id="img1" alt="上传成功啦" src="" width="300" height="300"/></p>
</body>
</html>
