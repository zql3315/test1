<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${r"${pageContext.request.contextPath}"}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加${table.description}</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>
				添加${table.description}
			</h3>
			<form role="form" class="form-horizontal" method="POST" id="form">
				<div class="form-info form-info-striped">
				
					<#list columns as column>
						<div class="form-info-row">
							<div class="form-info-name"> ${column.remarks} </div>
							<div class="form-info-value">
								<input type="text"  placeholder="${column.remarks}" name="${column.propertyName}" value="${r"${model."}${column.propertyName}${r"}"}" class="col-xs-10 col-sm-5">
							</div>
						</div>
					</#list>
					
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
	<script type="text/javascript">
		$(document).ready(function(){
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			//resize the chosen on window resize
			$(window).on('resize.chosen', function() {
				//var w = $('.chosen-select').parent().width();
				$(".chosen-select").next().css({"width":"41.66666667%","float":"left"});
			}).trigger('resize.chosen');
			
			$('#form').validate({
				rules:{
					<#list columns as column>
						${column.propertyName}:{required: true}<#if column_has_next>,</#if> 
					</#list>	
				},
				messages:{
					<#list columns as column>
						${column.propertyName}:{required: '请输入 ${column.remarks}'}<#if column_has_next>,</#if> 
					</#list>
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
							url : '${r"${ctx}"}/${table.sn}/add',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							showSuccess(function(){
                                $('.page-content').load('${r"${ctx}"}/${table.sn}/preview/',function(){
                                     //加一个遮罩在次
                                });
                            },"操作成功,您想要继续添加么？");
						}).fail(function(data) {
							alert('添加失败');
						});
					}
				}
			});
		});
	
	</script>	
	<script type="text/javascript">
		cancelBtn('${r"${ctx}"}/${table.sn}');
	</script>
</body>
</html>