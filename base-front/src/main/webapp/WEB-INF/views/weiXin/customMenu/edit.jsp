<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑菜单</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>编辑菜单
			</h3>
				
			<form role="form" class="form-horizontal" method="POST" id="customMenu">
				<div class="form-info form-info-striped">
					
						<input type="hidden" name="id" value="${model.id }"/>
						<input type="hidden" name="pid" value="${model.pid }"/>
						<div class="form-info-row">
							<div class="form-info-name"> 菜单名称  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="菜单名称" name="menuName" value="${model.menuName}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 响应类型  </div>
		
							<div class="form-info-value">
								<select type="text"  placeholder="响应类型" name="triggerType"  class="col-xs-10 col-sm-5">
									<option value="click" ${model.triggerType=="click"?"selected":""}>关键字</option>
									<option value="view" ${model.triggerType=="view"?"selected":""}>链接</option>
									<c:if test="${model.pid == '0' }">
									<option value="menu" ${model.triggerType=="menu"?"selected":""}>菜单</option>
									</c:if>
								</select>
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 响应内容  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="响应内容" name="triggerContent" value="${model.triggerContent}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 序号  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="序号" name="orderNum" value="${model.orderNum }" class="col-xs-10 col-sm-5">
							</div>
						</div>
					
				</div>
				
				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						&nbsp; &nbsp; &nbsp;
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
			
			$(".chosen-select").chosen({
				allow_single_deselect:true,
				disable_search_threshold: 10}); 
			
			//resize the chosen on window resize
			$(window).on("resize.chosen", function() {
				//var w = $(".chosen-select").parent().width();
				$(".chosen-select").next().css({"width":"41.66666667%","float":"left"});
			}).trigger("resize.chosen");
			
			$("#customMenu").validate({
				rules:{
						menuName:{required: true}, 
						triggerType:{required: true}, 
						triggerContent:{required: true}, 
						orderNum:{required: true}
				},
				messages:{
						menuName:{required: "请输入 菜单名称"}, 
						triggerType:{required: "请输入 菜单类型"}, 
						triggerContent:{required: "请输入 响应内容"}, 
						orderNum:{required: "请输入 序号"}
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
							url : "${ctx}/wechat/customMenu/edit",
							type : "POST",
							async : false,
							dataType : "json",
							contentType : "application/json;charset=UTF-8",
							data : JSON.stringify(d)
						}).done(function(data) {
							alert("编辑成功");
						}).fail(function(data) {
							alert("编辑失败");
						});
					}
				}
			});
		});
		
		$("#menu2manager").on("click",function(){
				$("div").find(".page-content").load("${ctx}/wechat/customMenu/toMenu2manager/${model.id}",function(){
// 					 加一个遮罩在次
				});
		});
	</script>
</body>
</html>