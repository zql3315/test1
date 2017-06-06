<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/static/fontawesome/css/font-awesome.min.css" />

<link rel="stylesheet" href="${ctx}/static/datetimepicker/css/bootstrap-datetimepicker.min.css"/>
	
	<!-- chosen -->
	<link rel="stylesheet" href="${ctx}/static/chosen/css/chosen.css"/>
	
	<!-- text fonts -->
	<link rel="stylesheet" href="${ctx}/static/ace/css/ace-fonts.css"/>
	<!-- ace styles -->
	<link rel="stylesheet" href="${ctx}/static/ace/css/ace.css" />
	<!--[if lte IE 9]>
		<link rel="stylesheet" href="${ctx}/static/css/ace-part2.min.css" />
	<![endif]-->
	<link rel="stylesheet" href="${ctx}/static/ace/css/ace-skins.min.css">
	<link rel="stylesheet" href="${ctx}/static/ace/css/ace-rtl.css">
	<link rel="stylesheet" href="${ctx}/static/style.css" />
</head>
<body class="no-skin">
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>
				添加
			</h3>
		</div>
		<form role="form" class="form-horizontal" method="POST" id="form">
			<div class="form-info form-info-striped">
			
					<div class="form-info-row">
						<div class="form-info-name"> 用户名  </div>
	
						<div class="form-info-value">
							<input type="text"  placeholder="用户名" name="loginname" value="${model.loginname}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name"> 用户名称  </div>
	
						<div class="form-info-value">
							<input type="text"  placeholder="用户名称" name="name" value="${model.name}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<shiro:hasPermission name="home:system:user:password:view">
						<div class="form-info-row">
							<div class="form-info-name"> 用户密码  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="用户密码" name="password" value="${model.password}" class="col-xs-10 col-sm-5">
							</div>
						</div>
					</shiro:hasPermission>
					
					<div class="form-info-row">
						<div class="form-info-name"> 加密盐  </div>
	
						<div class="form-info-value">
							<input type="text"  placeholder="加密盐" name="salt" value="${model.salt}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name"> 邮件  </div>
	
						<div class="form-info-value">
							<input type="text"  placeholder="邮件" name="email" value="${model.email}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name"> 状态  </div>
	
						<div class="form-info-value">
						</div>
					</div>
					
					<div class="form-info-row">
						<div class="form-info-name"> 角色  </div>
	
						<div class="form-info-value">
							<select class="chosen-select" id="role" multiple>
								
							</select>
						</div>
					</div>
				
			</div>
			<div class="clearfix form-actions">
				<div class="col-md-offset-3 col-md-9">
					<button class="btn btn-info" type="submit">
						<i class="ace-icon fa fa-check smaller-90"></i>提交
					</button>

					&nbsp; &nbsp; &nbsp;
					<button class="btn" type="reset">
						<i class="ace-icon fa fa-undo smaller-90"></i>取消
					</button>
				</div>
			</div>		
		</form>
	</div>
	<!-- validate -->
	<script src="${ctx}/static/datatable/js/jquery.js"></script>
	<script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<!-- chosen -->
	<script src="${ctx}/static/chosen/js/chosen.jquery.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			
			$('.chosen-select').chosen({
				allow_single_deselect:true,
				disable_search_threshold: 10}); 
			
			//resize the chosen on window resize
			$(window).on('resize.chosen', function() {
				//var w = $('.chosen-select').parent().width();
				$('.chosen-select').next().css({'width':'41.66666667%','float':'left'});
			}).trigger('resize.chosen');
			
			$.ajax({
				url : '${ctx}/role/find',
				type : 'POST',
				async : false,
				dataType : 'json',
				contentType : 'application/json;charset=UTF-8'
			}).done(function(data) {
				var html='';
				$.each(data,function(){
					html=html+'<option value="'+this.id+'">'+this.name+'</option>';
				});
				$('#role').html(html).trigger("chosen:updated");
			}).fail(function(data) {
				alert('添加失败');
			});
			
			$('#form').validate({
				rules:{
						loginname:{required: true}, 
						name:{required: true}, 
						password:{required: true}, 
						salt:{required: true}, 
						email:{required: true}
						, 
						status:{required: true}
						 
				},
				messages:{
						loginname:{required: '请输入 用户名'}
						, 
						name:{required: '请输入 用户名称'}
						, 
						password:{required: '请输入 用户密码'}
						, 
						salt:{required: '请输入 加密盐'}
						, 
						email:{required: '请输入 邮件'}
						, 
						status:{required: '请输入 状态'}
						 
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
						
						d.roles=[];
						
						var roles=$('#role').val();
						
						$.each(roles,function(){
							var roleUser={};
							roleUser.role={};
							roleUser.role.id=this;
							
							d.roles.push(roleUser);
						});
						
						$.ajax({
							url : '${ctx}/user/add',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							alert('添加成功');
						}).fail(function(data) {
							alert('添加失败');
						});
					}
				}
			});
		});
	
	</script>	
</body>
</html>