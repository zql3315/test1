<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>demo</title>
</head>
<body>
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
						<div class="form-info-name"> 示例名称 </div>
						<div class="form-info-value">
							<input type="text"  placeholder="示例名称" name="name" value="${model.name}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name"> 示例时长 </div>
						<div class="form-info-value">
							<input type="text"  placeholder="示例时长" name="age" value="${model.age}" class="col-xs-10 col-sm-5">
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
	<!-- validate -->
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			//resize the chosen on window resize
			$(window).on('resize.chosen', function() {
				//var w = $('.chosen-select').parent().width();
				$('.chosen-select').next().css({'width':368});
			}).trigger('resize.chosen');
			
			$('#form').validate({
				rules:{
						name:{required: true}, 
						age:{required: true} 
				},
				messages:{
						name:{required: '请输入示例名称'}, 
						age:{required: '请输入示例时长'} 
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
							url : '${ctx}/demo/add',
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
	<script type="text/javascript">
		cancelBtn('${ctx}/demo');
	</script>
</body>
</html>