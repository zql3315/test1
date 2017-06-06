<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统通知</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>
				添加
			</h3>
			<form role="form" class="form-horizontal" method="POST" id="form">
				<div class="form-info form-info1  form-info-striped">
				
						<div class="form-info-row">
							<div class="form-info-name"> 通知对象标识 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="通知对象标识" name="targetId" value="${model.targetId}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 通知所属系统 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="通知所属系统" name="system" value="${model.system}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 通知时间 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="通知时间" name="date" value="${model.date}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 通知标题 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="通知标题" name="title" value="${model.title}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 通知附件 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="通知附件" name="attachment" value="${model.attachment}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 通知内容 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="通知内容" name="content" value="${model.content}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 是否已读 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="是否已读" name="isRead" value="${model.isRead}" class="col-xs-10 col-sm-5">
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
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			//resize the chosen on window resize
			$(window).on('resize.chosen', function() {
				//var w = $('.chosen-select').parent().width();
				$('.chosen-select').next().css({'width':368});
			}).trigger('resize.chosen');
			
			$('#form').validate({
				rules:{
						targetId:{required: true}, 
						system:{required: true}, 
						date:{required: true}, 
						title:{required: true}, 
						attachment:{required: true}, 
						content:{required: true}, 
						isRead:{required: true} 
				},
				messages:{
						targetId:{required: '请输入 通知对象标识'}, 
						system:{required: '请输入 通知所属系统'}, 
						date:{required: '请输入 通知时间'}, 
						title:{required: '请输入 通知标题'}, 
						attachment:{required: '请输入 通知附件'}, 
						content:{required: '请输入 通知内容'}, 
						isRead:{required: '请输入 是否已读'} 
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
							url : '${ctx}/ notification/add',
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