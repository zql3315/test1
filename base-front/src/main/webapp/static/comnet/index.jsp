<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="jquery-1.10.2.min.js"></script>
<title>Comet Chat Demo</title>
<script type="text/javascript">
var server = 'http://localhost:8089/base-manager/servlet/ChatComet?name=<%=request.getParameter("name")%>';

var comet = {
	connection   : false,
	iframediv    : false,
	initialize: function() {
		if (navigator.appVersion.indexOf("MSIE") != -1) {
			comet.connection = new ActiveXObject("htmlfile");
			comet.connection.open();
			comet.connection.write("<html>");
			comet.connection.write("<script>document.domain = '"+document.domain+"'");
			comet.connection.write("</html>");
			comet.connection.close();
			comet.iframediv = comet.connection.createElement("div");
			comet.connection.appendChild(comet.iframediv);
			comet.connection.parentWindow.comet = comet;
			comet.iframediv.innerHTML = "<iframe id='comet_iframe' src='"+server+"'></iframe>";

		} else if (navigator.appVersion.indexOf("KHTML") != -1) {
			comet.connection = document.createElement('iframe');
			comet.connection.setAttribute('id',     'comet_iframe');
			comet.connection.setAttribute('src',    server);
			
			with (comet.connection.style) {
				position   = "absolute";
				left       = top   = "-100px";
				height     = width = "1px";
				visibility = "hidden";
			}
		    document.body.appendChild(comet.connection);

		} else {
			comet.connection = document.createElement('iframe');
			comet.connection.setAttribute('id',     'comet_iframe');
			with (comet.connection.style) {
			    left       = top   = "-100px";
			    height     = width = "1px";
			    visibility = "hidden";
			    display    = 'none';
			}
			comet.iframediv = document.createElement('iframe');
			comet.iframediv.setAttribute('src', server);
			comet.connection.appendChild(comet.iframediv);
			document.body.appendChild(comet.connection);
		}
		//alert(server);
	},

	//添加用户
	newUser:function(data){
		var list = document.getElementById('userList');
		var li = document.createElement('li');
		li.setAttribute("id","u1"+data);
		li.innerHTML = data;
		list.appendChild(li);

		var user = document.getElementById('user');
		var option = document.createElement('option');
		option.setAttribute("id","u2"+data);
		option.innerHTML = data;
		user.appendChild(option);
	},

	//删除用户
	deleteUser:function(data){
		$('#u1'+data).remove();
		$('#u2'+data).remove();
	},

	//添加公共消息
	newMessage:function(data){
		var list = document.getElementById('messageList');
		var li = document.createElement('li');
		li.innerHTML = data;

		list.appendChild(li);
	},

	//添加私人消息
	privateMessage:function(data){
		var list = document.getElementById('privateMessage');
		var li = document.createElement('li');
		li.innerHTML = data;

		list.appendChild(li);
	},

	//退出
	onUnload: function() {
		if (comet.connection) {
			comet.connection = false;
		}
	}
}//comet end
			
if (window.addEventListener) {
	window.addEventListener("load", comet.initialize, false);
	window.addEventListener("unload", comet.onUnload, false);
} else if (window.attachEvent) {
	window.attachEvent("onload", comet.initialize);
	window.attachEvent("onunload", comet.onUnload);
}
</script>
</head>
<body>
<script type="text/javascript">
function sendAll(){
	var list = document.getElementById('privateMessage');
	var li = document.createElement('li');
	li.innerHTML = "I said to "+$("#user").val()+": " + $("#message").val();
	list.appendChild(li);
	
	 $.ajax({
		 type: "POST",
		 url: "/base-manager/servlet/MessageServlet", 
		 data: "message="+$("#message").val()+"&user="+$("#user").val()+"&from=<%=request.getParameter("name")%>"
	 });
}
</script>
<div class="container_12">
<div class="grid_10">
	<div>公共聊天</div>
	<div id="messageList" style="height:250px;overflow:scroll;border:solid 1px black;">
	
	</div>
	<br/>
	<div>个人聊天</div>
	<div id="privateMessage" style="height:150px;overflow:scroll;border:solid 1px black;">
	
	</div>
	<br/>
	<div>
		<select id="user" style="width:100px;overflow:scroll;">
			<option value="all">All</option>
		</select>
		<input type="text" id="message" size="40"></input>
		<input type="button" value="发送" onclick="sendAll()">
	</div>
</div>
<div class="grid_2">
	<h3>用户列表</h3>
	<ol id="userList">
	
	</ol>
</div>
</div>
</body>
</html>