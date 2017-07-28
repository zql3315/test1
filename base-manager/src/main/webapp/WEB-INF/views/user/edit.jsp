<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑用户</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>编辑用户
			</h3>
				
			<form role="form" class="form-horizontal" method="POST" id="user">
				<div class="form-info form-info-striped">
					
						<div class="form-info-row">
							<div class="form-info-name"> 登陆账号  <span style='color:red'>*</span></div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="用户名" name="loginname" value="${model.loginname}" class="col-xs-10 col-sm-5" readonly="readonly">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 用户名称  <span style='color:red'>*</span></div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="用户名称" name="name" value="${model.name}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 联系方式 </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="联系方式" name="telephone" value="${model.telephone }" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 邮件  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="邮件" name="email" value="${model.email}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 角色  <span style='color:red'>*</span></div>
		
							<div class="form-info-value">
								<sys:selectRole name="role" value="${model.role}">
									
								</sys:selectRole>
							</div> 
							
						</div>
							<input type="hidden" name="id" value="${model.id}" class="col-xs-10 col-sm-5">
					
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
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<script type="text/javascript">
	jQuery.validator.addMethod("lettleValidator", function(value, element) {
		var reg = /[a-zA-Z0-9]$/;
	    return reg.test(value);   
	  }, "请输入正确的字母或者数字.必须以字母开头，4-20位");
	
	jQuery.validator.addMethod("telephoneValidator", function(value, element) {
			if(value == ''){
				return true;
			}	
		
			var reg = /^1[3|4|5|7|8]\d{9}$/;
			var regTel = /^0[\d]{2,3}-[\d]{7,8}$/;
			
			var mflag = reg.test(value);
			var tflag = regTel.test(value);
			
		    return (mflag||tflag);
		  }, "请输入正确的手机号码或座机号码."); 
	</script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#user').validate({
				ignore: "",
				rules:{
						loginname:{required: true,lettleValidator:true}, 
						name:{required: true},
						email:{email:true},
						telephone:{telephoneValidator: true}
				},
				messages:{
						loginname:{required: '请输入账号',lettleValidator:'请输入正确的字母或者数字.必须以字母开头，4-20位'}, 
						name:{required: '请输入 用户名称'},
						email:{email:"请输入正确格式的电子邮件"},
						telephone:{telephoneValidator: '请输入正确的手机号码或座机号码！'}
				},
				submitHandler:function(form){
					var formData = $(form).serializeArray();
					if ($.isArray(formData)) {
						var d={};
						d.roles=[];
						var temp='';
						$.each(formData, function(i, item) {
							if (item.name && item.value) {
								if(item.name == 'role'){
									var roleUser={};
									roleUser.role={};
									roleUser.role.id=item.value;
									
									d.roles.push(roleUser);
									temp = temp + item.value + ",";
								}else{
									d[item.name] = item.value;	
								}
								
							}
						});
						d.role = temp;
						$.ajax({
							url : '${ctx}/user/editUser',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							if(data.code == 0001){
								alert("账号已存在，请重新输入！");
								return;
							}
							if(data.code == 0002){
								alert("最少指派一个角色！");
								return;
							}
							if(data.result){
								showSuccess(function(){
									$(".user-info > .username").text(d.name);
									openPage('${ctx}/user/preview');
									backBreadcrumb();
								});
							}else{
								alert('编辑失败');
							}
						}).fail(function(data) {
							alert('编辑失败');
						});
					}
				}
			});
		});
	</script>
	<script type="text/javascript">
		cancelBtn('${ctx}/user');
	</script>
</body>
</html>