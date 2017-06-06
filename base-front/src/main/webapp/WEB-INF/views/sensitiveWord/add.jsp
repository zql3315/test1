<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加敏感字</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>添加敏感字
			</h3>
				
			<form role="form" class="form-horizontal" method="POST" id="expressCompany">
				<div class="form-info form-info-striped">
					
						<div class="form-info-row">
							<div class="form-info-name"> 敏感字  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="敏感字" name="sensitiveWord"  class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 替换字  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="替换字" name="replaceWord"  class="col-xs-10 col-sm-5">
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
			
			$('.chosen-select').chosen({
				allow_single_deselect:true,
				disable_search_threshold: 10}); 
			
			//resize the chosen on window resize
			$(window).on('resize.chosen', function() {
				//var w = $('.chosen-select').parent().width();
				$('.chosen-select').next().css({'width':'41.66666667%','float':'left'});
			}).trigger('resize.chosen');
			
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

			$(".form_datetime input").val(new Date().pattern("yyyy-MM-dd HH:mm:ss"));
			
			$('#expressCompany').validate({
				rules:{
						sensitiveWord:{required: true}
						, 
						replaceWord:{required: true}
				},
				messages:{
						sensitiveWord:{required: '请输入 敏感字'}
						, 
						replaceWord:{required: '请输入 替换文字'}
						 
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
							url : '${ctx}/sensitiveWord/add',
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