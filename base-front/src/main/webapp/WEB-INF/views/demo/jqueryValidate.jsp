<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<%@ include file="../common/path.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/lib/jquery.js"></script>
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
<style>
.error {
	color: red;
}
</style>
</head>
<body>
	http://www.runoob.com/jquery/jquery-plugin-validate.html
	<p>将校验规则写到控件中</p>
	<form class="cmxform" id="commentForm" method="get" action="">
		<fieldset>
			<legend>输入您的名字，邮箱，URL，备注。</legend>
			<p>
				<label for="cname">Name (必需, 最小两个字母)</label> <input id="cname" name="name" minlength="2" type="text" required>
			</p>
			<p>
				<label for="cemail">E-Mail (必需)</label> <input id="cemail" type="email" name="email" required>
			</p>
			<p>
				<label for="curl">URL (可选)</label> <input id="curl" type="url" name="url">
			</p>
			<p>
				<label for="ccomment">备注 (必需)</label>
				<textarea id="ccomment" name="comment" required></textarea>
			</p>
			<p>
				<input class="submit" type="submit" value="Submit">
			</p>
		</fieldset>
	</form>
	<script>
		$.validator.setDefaults({
			debug : true, //只检查不提交
			submitHandler : function() {
				alert("提交事件!");
			}
		});
		$().ready(function() {
			$("#commentForm").validate({
				debug : true
			//只检查不提交
			});
		});
	</script>
	<p>将校验规则写到 js 代码中</p>
	<form class="cmxform" id="signupForm" method="get" action="">
		<fieldset>
			<legend>验证完整的表单</legend>
			<p>
				<label for="firstname">名字</label> <input id="firstname" name="firstname" type="text">
			</p>
			<p>
				<label for="lastname">姓氏</label> <input id="lastname" name="lastname" type="text">
			</p>
			<p>
				<label for="username">用户名</label> <input id="username" name="username" type="text">
			</p>
			<p>
				<label for="password">密码</label> <input id="password" name="password" type="password">
			</p>
			<p>
				<label for="confirm_password">验证密码</label> <input id="confirm_password" name="confirm_password" type="password">
			</p>
			<p>
				<label for="email">Email</label> <input id="email" name="email" type="email">
			</p>
			<p>
				<label for="agree">请同意我们的声明</label> <input type="checkbox" class="checkbox" id="agree" name="agree">
			</p>
			<p>
				<label for="newsletter">我乐意接收新信息</label> <input type="checkbox" class="checkbox" id="newsletter" name="newsletter">
			</p>
			<fieldset id="newsletter_topics">
				<legend>主题 (至少选择两个) - 注意：如果没有勾选“我乐意接收新信息”以下选项会隐藏，但我们这里作为演示让它可见</legend>
				<label for="topic_marketflash"> <input type="checkbox" id="topic_marketflash" value="marketflash" name="topic[]">Marketflash
				</label> <label for="topic_fuzz"> <input type="checkbox" id="topic_fuzz" value="fuzz" name="topic[]">Latest fuzz
				</label> <label for="topic_digester"> <input type="checkbox" id="topic_digester" value="digester" name="topic[]">Mailing list digester
				</label> <label for="topic" class="error" style="display:none">至少选择两个</label>
			</fieldset>
			<p>
				<input class="submit" type="submit" value="提交">
			</p>
		</fieldset>
	</form>
	<script type="text/javascript">
		$().ready(function() {
			// 在键盘按下并释放及提交后验证提交表单
			$("#signupForm").validate({
				rules : {
					firstname : "required",
					lastname : "required",
					username : {
						required : true,
						minlength : 2
					},
					password : {
						required : true,
						minlength : 5
					},
					confirm_password : {
						required : true,
						minlength : 5,
						equalTo : "#password"
					},
					email : {
						required : true,
						email : true
					},
					"topic[]" : {
						required : "#newsletter:checked",
						minlength : 2
					},
					agree : "required"
				},
				messages : {
					firstname : "请输入您的名字",
					lastname : "请输入您的姓氏",
					username : {
						required : "请输入用户名",
						minlength : "用户名必需由两个字母组成"
					},
					password : {
						required : "请输入密码",
						minlength : "密码长度不能小于 5 个字母"
					},
					confirm_password : {
						required : "请输入密码",
						minlength : "密码长度不能小于 5 个字母",
						equalTo : "两次密码输入不一致"
					},
					email : "请输入一个正确的邮箱",
					agree : "请接受我们的声明",
					topic : "请选择两个主题"
				},
				/* 重写错误显示消息方法,以alert方式弹出错误消息 */
				showErrors : function(errorMap, errorList) {
					console.log(errorMap);
					console.log(errorList);
					var msg = "";
					$.each(errorList, function(i, v) {
						msg += (v.message + "\r\n");
					});
					if (msg != "")
						alert(msg);
				},
				/* 失去焦点时不验证    */
				onfocusout : false
			});
		});
	</script>

	<form method="get" class="cmxform" id="form1" action="">
		<fieldset>
			<legend>登录框</legend>
			<p>
				<label for="user">用户名</label> <input id="user" name="user" autocomplete="off" required minlength="3">
			</p>
			<p>
				<label for="password">密码</label> <input id="password" type="password" autocomplete="off" maxlength="12" name="password" required minlength="5">
			</p>
			<p>
				<input class="submit" type="submit" value="Login">
			</p>
		</fieldset>
	</form>
	<script>
		$().ready(function() {
			// 提交时验证表单
			var validator = $("#form1").validate({
				errorPlacement : function(error, element) {
					// Append error within linked label
					$(element).closest("form").find("label[for='" + element.attr("id") + "']").append(error);
				},
				errorElement : "span",
				messages : {
					user : {
						required : " (必需字段)",
						minlength : " (不能少于 3 个字母)"
					},
					password : {
						required : " (必需字段)",
						minlength : " (字母不能少于 5 个且不能大于 12 个)",
						maxlength : " (字母不能少于 5 个且不能大于 12 个)"
					}
				}
			});

			$(".cancel").click(function() {
				validator.resetForm();
			});
		});
	</script>
</body>
</html>

