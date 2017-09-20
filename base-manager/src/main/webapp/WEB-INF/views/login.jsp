<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">

<style>
	.login-layout {
	  background-color: #394557;
	  background-image: url('${ctx}/static/image/layout.jpg');
	  background-repeat:repeat;
	}
</style>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<title>Login</title>
	<!-- bootstrap & fontawesome -->
	<link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" href="${ctx}/static/fontawesome/css/font-awesome.min.css" />
	
	<!-- text fonts -->
	<link rel="stylesheet" href="${ctx}/static/ace/css/ace-fonts.css"/>
	<!-- ace styles -->
	<link rel="stylesheet" href="${ctx}/static/ace/css/ace.css" />
	<!--[if lte IE 9]>
		<link rel="stylesheet" href="${ctx}/static/css/ace-part2.min.css" />
	<![endif]-->
	<link rel="stylesheet" href="${ctx}/static/ace/css/ace-skins.min.css">
	<link rel="stylesheet" href="${ctx}/static/ace/css/ace-rtl.css">
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="${ctx}/static/js/html5shiv.js"></script>
	<script src="${ctx}/static/js/respond.min.js"></script>
	<![endif]-->
	<script type="text/javascript">
		if(document.getElementById("navbar")) {
			location.href = "${ctx}";
		}
	</script>
</head>
<!-- .login-layout.blur-login light-login-->
<body class="login-layout">
	<c:if test="${msg!=null }">
		<p style="color: red; margin-left: 10px;">${msg }</p>
	</c:if>
	<div class="main-container" style="position:absolute; ;top: 50% ; left:50%;margin-top:-250px; margin-left:-280px;">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container" >
					  	<!-- page content goes here -->
						<div class="center">
							<!-- page header and text -->
							<h1>
								<img src='${ctx}/static/image/logo_151x41.png'/>
								<span class="red">网络后台管理系统基线版本</span>
								<span class="white" id="id-text2"></span>
							</h1>
							<h3 class="blue" id="id-company-text">&copy; 南京诚迈科技网络技术有限公司</h3>
	
						</div>
						<div class="space-6"></div><!-- optional space -->
						<div class="position-relative" style="width: 375px; margin: auto;">
							<!-- a position relative container -->
							<div class="login-box visible widget-box no-border" id="login-box">
								<!-- login area -->
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header blue lighter bigger">
											<i class="ace-icon fa fa-coffee green"></i>
												欢迎登陆
										</h4>
										<div class="space-6"></div>
										<form id="loginForm" name="loginForm" action="${ctx}/login" method="POST">
											<fieldset>
												<label class="block clearfix">
													<span class="block input-icon input-icon-right">
														<input type="text" id="username" class="form-control" placeholder="用户名" name="username"/>
														<i class="ace-icon fa fa-user"></i>
													</span>
												</label>
												<label class="block clearfix">
													<span class="block input-icon input-icon-right">
														<input type="password" id="password" class="form-control" placeholder="密码" name="password"/>
														<i class="ace-icon fa fa-lock"></i>
													</span>
												</label>
												<label class="block clearfix">
													<span class="block input-icon input-icon-right">
														<input id="captcha" autocomplete="OFF" name="captcha" size="4" maxlength="4" class="form-control" placeholder="验证码" name="password"/>
														<i class="ace-icon fa fa-key"></i>
													</span>
												</label>
												<label class="block clearfix">
													<div class="field">
														<label for="codeImg" class="field"></label> 
															<img title="点击更换" id="img_captcha" onclick="javascript:refreshCaptcha();" src="${ctx }/servlet/captchaCode">
															(看不清<a href="javascript:void(0)" onclick="javascript:refreshCaptcha()">换一张</a>)
													</div>
												</label>
												<div class="space"></div>
												<div class="clearfix">
													<label class="inline">
														<input type="checkbox" class="ace" name="rememberMe" />
														<span class="lbl"> 下次自动登录</span>
													</label>
													<button id="login" type="button"   class="width-35 pull-right btn btn-sm btn-primary">
														<i class="ace-icon fa fa-sign-in bigger-110"></i>
														<span class="bigger-110">登录</span>
													</button>
												</div>
												<div class="space-4"></div>
											</fieldset>
										</form>
									</div>
								</div>
								<!-- 
								<form action="${ctx}/login" method="post">
									<input type="text" name="username"/>
									<input type="text" name="password"/>
									<input type="submit" value="登录"/>
								</form>
								 -->
							</div>
							<div class="forgot-box widget-box no-border" id="forgot-box">
					       		<!-- forgot area -->
					    	</div>
					    	<div class="signup-box widget-box no-border" id="signup-box">
						        <!-- signup area -->
						    </div>
						</div>
					</div>
				</div><!-- /.col -->
			</div><!-- /.row -->
		</div><!-- /.main-content -->
	</div><!-- /.main-container -->
	
	<!--[if !IE]>-->
	<script type="text/javascript">
		window.jQuery || document.write("<script src='${ctx}/static/jquery/js/jquery.min.js'>"+"<"+"/script>");
	</script>
	<!--<![endif]-->
	
	<!--[if IE]>
	<script type="text/javascript">
	 window.jQuery || document.write("<script src='${ctx}/static/jquery/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
	</script>
	<![endif]-->
	
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/static/jquery/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	<script type="text/javascript">
		//新建cookie。   
		//hours为空字符串时,cookie的生存期至浏览器会话结束。hours为数字0时,建立的是一个失效的cookie,这个cookie会覆盖已经建立过的同名、同path的cookie（如果这个cookie存在）。  
		function setCookie(name, value, hours, path) {
			var name = escape(name);
			var value = escape(value);
			var expires = new Date();
			expires.setTime(expires.getTime() + hours * 3600000);
			path = path == "" ? "" : ";path=" + path;
			_expires = (typeof hours) == "string" ? "" : ";expires="
					+ expires.toUTCString();
			document.cookie = name + "=" + value + _expires + path;
		}
		//获取cookie值
		function getCookieValue(name) {
			var name = escape(name);
			// 读cookie属性，这将返回文档的所有cookie
			var allcookies = document.cookie;
			// 查找名为name的cookie的开始位置
			name += "=";
			var pos = allcookies.indexOf(name);
			// 如果找到了具有该名字的cookie，那么提取并使用它的值
			if (pos != -1) { // 如果pos值为-1则说明搜索"version="失败
				var start = pos + name.length; // cookie值开始的位置
				var end = allcookies.indexOf(";", start); // 从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
				if (end == -1)
					end = allcookies.length; // 如果end值为-1说明cookie列表里只有一个cookie
				var value = allcookies.substring(start, end); // 提取cookie的值
				return unescape(value); // 对它解码
			} else
				return ""; // 搜索失败，返回空字符串
		}
		//删除cookie
		function deleteCookie(name, path) {
			var name = escape(name);
			var expires = new Date(0);
			path = path == "" ? "" : ";path=" + path;
			document.cookie = name + "=" + ";expires=" + expires.toUTCString() + path;
		}
		var _captcha_id = "#img_captcha";
		function refreshCaptcha() {
			$(_captcha_id).attr("src","${ctx}/servlet/captchaCode?t=" + Math.random());
		}
		$(document).ready(function(){
			
<%--			//分析cookie值，显示上次的登陆信息   --%>
<%--		    var userNameValue = getCookieValue("userName");--%>
<%--			$('input[name="username"]').val(userNameValue);   --%>
<%--		   var passwordValue = getCookieValue("password");   --%>
<%--		   	$('input[name="password"]').val(passwordValue); --%>
<%--			--%>
			$("#username").focus();
			
			$('#login').on('click',function(){
				//$('loginForm').attr('action','${ctx}/login');
				login();
			});
			$("#captcha").on("focus",function(){
				//refreshCaptcha();
			});
			
		    $('#username').bind('keyup', function(event) {
		        if (event.keyCode == "13") {
		        	login();
		        }
		    });
		    
		    $('#password').bind('keyup', function(event) {
		        if (event.keyCode == "13") {
		        	login();
		        }
		    });
		});
		
		function login() {
			var username=$('input[name="username"]').val();
			var password=$('input[name="password"]').val();
			
<%--			if( $("input:checkbox:checked").length > 0){--%>
<%--                setCookie("userName",username,24,"/");   --%>
<%--                setCookie("password",password,24,"/");--%>
<%--            }else{--%>
<%--            	deleteCookie("userName", "/");--%>
<%--            	deleteCookie("password", "/");--%>
<%--            }--%>
			
			if ( $('div').find('.page-content')[0]) {
				 $('div').find('.page-content').load('${ctx}/login',{"username":username,"password":password});
			}else{
				$('#loginForm').submit();
			}
		}
		
	</script>
</body>
</html>