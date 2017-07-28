<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑项目</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h1 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>编辑项目
			</h1>
				
			<form role="form" class="form-horizontal" method="POST" id="task">
				<div class="form-info form-info-striped">
					
						<div class="form-info-row">
							<div class="form-info-name"> 名称 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="名称" name="creator" value="${model.creator}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 类型 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="类型" name="lastmodtime" value="${model.lastmodtime}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 状态 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="状态" name="name" value="${model.name}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 请求消息 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="请求消息 " name="requestbody" value="${model.requestbody}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 响应消息 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="响应消息" name="responsebody" value="${model.responsebody}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 创建者 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="创建者" name="status" value="${model.status}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 最后更新时间 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="最后更新时间" name="type" value="${model.type}" class="col-xs-10 col-sm-5">
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
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#task').validate({
				rules:{
						name:{required: true}, 
						type:{required: true}, 
						status:{required: true}, 
						requestbody:{required: true}, 
						responsebody:{required: true}, 
						creator:{required: true}, 
						lastmodtime:{required: true} 
				},
				messages:{
						name:{required: '请输入 名称'}, 
						type:{required: '请输入 类型'}, 
						status:{required: '请输入 状态'}, 
						requestbody:{required: '请输入 请求消息'}, 
						responsebody:{required: '请输入 响应消息'}, 
						creator:{required: '请输入 创建者'}, 
						lastmodtime:{required: '请输入 最后更新时间'} 
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
							url : '${ctx}/task/edit',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							alert('编辑成功');
						}).fail(function(data) {
							alert('编辑失败');
						});
					}
				}
			});
		});
	</script>
	<script type="text/javascript">
		cancelBtn('${ctx}/task');
	</script>
</body>
</html>