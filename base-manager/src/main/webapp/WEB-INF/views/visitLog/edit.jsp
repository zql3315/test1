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
				
			<form role="form" class="form-horizontal" method="POST" id="visitLog">
				<div class="form-info form-info-striped">
					
						<div class="form-info-row">
							<div class="form-info-name">具体操作</div>
							<div class="form-info-value">
								<input type="text"  placeholder="具体操作" name="operate" value="${model.operate}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">访问模块</div>
							<div class="form-info-value">
								<input type="text"  placeholder="访问模块" name="visitModule" value="${model.visitModule}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">访问时间 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="访问时间" name="visitTime" value="${model.visitTime}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">访问者</div>
							<div class="form-info-value">
								<input type="text"  placeholder="访问者" name="visitor" value="${model.visitor}" class="col-xs-10 col-sm-5">
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
			$('#visitLog').validate({
				rules:{
						operate:{required: true}, 
						visitModule:{required: true}, 
						visitTime:{required: true}, 
						visitor:{required: true} 
				},
				messages:{
						operate:{required: '请输入 '}, 
						visitModule:{required: '请输入 '}, 
						visitTime:{required: '请输入 '}, 
						visitor:{required: '请输入 '} 
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
							url : '${ctx}/visitLog/edit',
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
		cancelBtn('${ctx}/visitLog');
	</script>
</body>
</html>