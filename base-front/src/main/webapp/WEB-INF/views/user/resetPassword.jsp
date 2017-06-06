<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="slm" uri="http://www.b2r.com.cn/slm" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重置密码</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>重置密码
			</h3>
			
			<form role="form" class="form-horizontal" method="POST" id="form">
				<div class="form-info form-info-striped">
					<div class="form-info-row">
						<div class="form-info-name"> 账号 <span style='color:red'>*</span></div>
						<div class="form-info-value">
							<input type="text" name="loginName" id="loginName" class="col-xs-10 col-sm-3">
						</div>
					</div>
					<div class="form-info-row">
							<div class="form-info-name">新密码 <span style='color:red'>*</span></div>
							<div class="form-info-value">
								<input type="password" name="password" id="password" class="col-xs-10 col-sm-3">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">确认新密码 <span style='color:red'>*</span></div>
							<div class="form-info-value">
								<input type="password" name="confirmPassword" id="confirmPassword" class="col-xs-10 col-sm-3">
							</div>
					</div>
					
				</div>
				
				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button class="btn btn-info" type="submit">
							<i class="ace-icon fa fa-check smaller-90"></i>提交
						</button>
						&nbsp; &nbsp; &nbsp;
						<button class="btn" type="button" id="cancel">
							<i class="ace-icon fa fa-undo smaller-90"></i>取消
						</button>
					</div>
				</div>
		</form>
		</div>
	</div>
	<!-- validate -->
	<script src="${ctx}/static/custom/b2r.js"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			
			$('#form').validate({
				rules:{
					loginName:{required:true},
					password:{required: true},
					confirmPassword:{required: true, equalTo:"#password"}
				},
				messages:{
						loginName:{required:'请输入需要重置密码的账号'},
						password:{required: '请输入密码'},
						confirmPassword:{required:'请再次输入密码', equalTo:"两次输入的密码不一致，请重新输入！"}
				},
				submitHandler:function(form){
					var formData = $(form).serializeArray();
					if ($.isArray(formData)) {
						var d={};
						$.each(formData, function(i, item) {
							if (item.name && item.value) {
								d[item.name] = item.value;
							}
						});
						
						$.ajax({
							url : '${ctx}/user/saveResetPassword',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							if(data.code == 0001){
								alert("账号不存在，请重新输入！");
								return;
							}
							if(data.code == 0002){
								alert("admin账号为系统预置，无法操作！");
								return;
							}
							alert("修改成功");
						}).fail(function(data) {
							alert("修改失败");
						});
					}
				}
			});
		});
	</script>
	<script type="text/javascript">
	$('#cancel').on('click',function(){
		location.reload();
	});
	</script>
</body>
</html>