<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="slm" uri="http://www.b2r.com.cn/slm" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>修改密码
			</h3>
			
			<form role="form" class="form-horizontal" method="POST" id="form">
				<div class="form-info-row">
					<div class="form-info-value">
						<input type="hidden" name="operId" value="${model.operId}" class="col-xs-10 col-sm-5">
					</div>
					<div class="form-info-value">
						<input type="hidden" name="userId" value="${model.userId}" class="col-xs-10 col-sm-5">
					</div>
				</div>
				<div class="form-info form-info-striped">
					<div class="form-info-row">
							<div class="form-info-value" style="font-weight: bold;background: #99ccff !important"> 用户信息  </div>
							<div class="form-info-value">&nbsp;	</div>
							<div class="form-info-value">&nbsp;	</div>
							<div class="form-info-value">&nbsp;	</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name"> 账号</div>
						<div class="form-info-value">
							<input type="text" name="loginName" value="${model.loginName}" readonly="readonly" class="col-xs-10 col-sm-3">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name"> 昵称</div>
						<div class="form-info-value">
							<input type="text" name="userName" value="${model.userName}" readonly="readonly" class="col-xs-10 col-sm-3">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name"> 角色</div>
						<div class="form-info-value">
							<input type="text" name="roleName" value="${model.roleName}" readonly="readonly" class="col-xs-10 col-sm-3">
						</div>
					</div>
				</div>
				<div class="form-info form-info-striped">
					<div class="form-info-row">
							<div class="form-info-value" style="font-weight: bold;background: #99ccff !important"> 修改密码  </div>
							<div class="form-info-value">&nbsp;	</div>
							<div class="form-info-value">&nbsp;	</div>
							<div class="form-info-value">&nbsp;	</div>
					</div>
					<div class="form-info-row">
					</div>
					<div class="form-info-row">
						<div class="form-info-name"> 旧密码 <span style='color:red'>*</span></div>
						<div class="form-info-value">
								<input type="password" name="oldPassword" id="oldPassword" class="col-xs-10 col-sm-3">
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
					oldPassword:{required:true},
					password:{required: true},
					confirmPassword:{required: true, equalTo:"#password"}
				},
				messages:{
						oldPassword:{required:'请输入旧密码'},
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
							url : '${ctx}/user/saveChangePassword',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							if(data.code == 0001){
								alert("旧密码输入错误，请重新输入！");
								return;
							}
							if(data.code == 0002){
								alert("新密码不可与旧密码相同，请重新输入！");
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
		
		$('#cancel').on('click',function(){
			cancelBtn("${ctx}/user");
		});
	</script>
</body>
</html>