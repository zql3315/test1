<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
				
			<form role="form" class="form-horizontal" method="POST" id="article">
				<input type="hidden" placeholder="id" name="id" value="${model.id}" class="col-xs-10 col-sm-5">
				<div class="form-info form-info-striped">
						<div class="form-info-row">
							<div class="form-info-name">标题</div>
							<div class="form-info-value">
								<input type="text"  placeholder="" name="title" value="${model.title}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">內容</div>
	                        <div class="form-info-value">
	                            <input type="text"  placeholder="內容" name="content" value="${model.content}" class="col-xs-10 col-sm-5">
	                        </div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">更新时间</div>
							<div class="form-info-value">
							    <div class="input-group date form_datetime col-xs-10 col-sm-5">
							       <fmt:formatDate value="${model.updatetime}" pattern="yyyy-MM-dd HH:mm:ss" var="updatetime"/>
                                   <input type="text"  placeholder="更新时间" name="updatetime" value="${updatetime }" class="col-xs-12" readonly>
                                   <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>
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
                format : "yyyy-mm-dd hh:ii:ss",
                autoclose : true,
                todayBtn : true,
                todayHighlight : true,
                language : 'zh-CN',
                minView: 0,
                maxView : 3,
                cleanBtn: true,
                pickerPosition : "bottom-left"
            });
			
			$('#article').validate({
				rules:{
						title:{required: true},
			            content:{required: true}
				},
				messages:{
						title:{required: '请输入 '},
						content:{required: '请输入 '}
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
							url : '${ctx}/cms/article/edit',
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
		cancelBtn('${ctx}/cms/article');
	</script>
</body>
</html>