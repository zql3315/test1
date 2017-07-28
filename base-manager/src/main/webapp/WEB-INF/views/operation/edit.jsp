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
				
			<form role="form" class="form-horizontal" method="POST" id="projectForm">
				<div class="form-info  form-info1  form-info-striped">
					
						<div class="form-info-row">
							<div class="form-info-name"> 名称  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="名称" name="name" value="${model.name}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 简称  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="简称" name="sn" value="${model.sn}" class="col-xs-10 col-sm-5">
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
	</div>
	<!-- validate -->
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#projectForm').validate({
				rules:{
						name:{required: true}
						, 
						sn:{required: true}
						 
				},
				messages:{
						name:{required: '请输入 名称'}
						, 
						sn:{required: '请输入 简称'}
						 
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
							url : '${ctx}/operation/add',
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
</body>
</html>