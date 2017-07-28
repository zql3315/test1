<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑角色</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>编辑角色
			</h3>
				
			<form role="form" class="form-horizontal" method="POST" id="projectForm">
				<div class="form-info  form-info1  form-info-striped">
						<div class="form-info-row">
							<div class="form-info-name"> 唯一标识  </div>
		
							<div class="form-info-value">
								<input type="text" readonly="readonly" placeholder="唯一标识" name="id" value="${model.id}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						
						<div class="form-info-row">
							<div class="form-info-name"> 名称 <span style='color:red'>*</span></div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="名称" name="name" value="${model.name}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 简称 <span style='color:red'>*</span></div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="简称" name="sn" value="${model.sn}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 描述 <span style='color:red'>*</span></div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="描述" name="description" value="${model.description}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 创建时间 </div>
							
							<div class="form-info-value">
								<div class="input-group date form_datetime col-xs-10 col-sm-5">
				                    <input type="text"  placeholder="创建时间" name="createdDate" value="<spring:eval expression='model.createdDate'/>" class="col-xs-12" readonly>
				                   <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 创建者 <span style='color:red'>*</span></div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="创建者" name="creator" value="${model.creator}" readonly="readonly" class="col-xs-10 col-sm-5">
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
			$('.form_datetime').datetimepicker({
				format : "yyyy-mm-dd",
				autoclose : true,
				todayBtn : true,
				todayHighlight : true,
				pickerReferer : 'input',
				language : 'zh-CN',
				minView : 2,
				maxView : 3,
				cleanBtn: true,
				pickerPosition : "bottom-left"
		    });
			
			$('#projectForm').validate({
				rules:{
						name:{required: true}
						, 
						sn:{required: true}
						, 
						description:{required: true}
						, 
						createdDate:{required: true}
						, 
						creator:{required: true}
						 
				},
				messages:{
						name:{required: '请输入 名称'}
						, 
						sn:{required: '请输入 简称'}
						, 
						description:{required: '请输入 描述'}
						, 
						createdDate:{required: '请输入 创建时间'}
						, 
						creator:{required: '请输入 创建者'}
						 
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
							url : '${ctx}/role/editRole',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							if(data.code == 0001){
								alert("角色已存在，请重新输入！");
								return;
							}
							showSuccess(function(){
								openPage('${ctx}/role/preview');
								backBreadcrumb();
							});
						}).fail(function(data) {
							alert('编辑失败');
						});
					}
				}
			});
		});
	</script>
	<script type="text/javascript">
		cancelBtn('${ctx}/role');
	</script>
</body>
</html>