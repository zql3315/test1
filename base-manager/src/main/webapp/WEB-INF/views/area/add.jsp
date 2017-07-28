<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx}/static/ztree/css/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<title>d</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>
				添加子区域
			</h3>
			<form role="form" class="form-horizontal" method="POST" id="form">
				<div class="form-info form-info1 form-info-striped">
					<div class="form-info-row">
						<div class="form-info-name">名称</div>
	
						<div class="form-info-value">
						    <input type="hidden" value="${param.id }" id="h_parent">
							<input type="text"  placeholder="区域名称" name="name" value="${model.name}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">编码 </div>
	
						<div class="form-info-value">
							<input type="text"  placeholder="10000" name="code" value="${model.code}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
                        <div class="form-info-name">权重 </div>
    
                        <div class="form-info-value">
                            <input type="text"  placeholder="" name="weight" value="0" class="col-xs-10 col-sm-5">
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
	<script src="${ctx}/static/ztree/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <script src="${ctx}/static/ztree/jquery.ztree.excheck-3.5.min.js" type="text/javascript"></script>
	<!-- validate -->
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<script type="text/javascript">
	   
		$(document).ready(function(){
			
			$(".chosen-select").chosen({allow_single_deselect:true,disable_search_threshold: 10}); 
            //resize the chosen on window resize
            $(window).on("resize.chosen", function() {
                //var w = $(".chosen-select").parent().width();
                $(".chosen-select").next().css({"width":"41.66666667%","float":"left"});
            }).trigger("resize.chosen");
			
			$("#form").validate({
				rules:{
						name:{required: true}
						, 
						code:{required: true}
				},
				messages:{
						name:{required: "请输入名称"}
						, 
						code:{required: "请输入编码"}
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
						var pid = $("#h_parent").val();
						if(pid){
							d.parent={};
							d.parent.id=pid;
						}
						$.ajax({
							url : "${ctx}/area/add",
							type : "POST",
							dataType : "json",
							contentType : "application/json;charset=UTF-8",
							data : JSON.stringify(d)
						}).done(function(data) {
							if (data.result){
                                showSuccess(function(){
                                    $('#areaEdit').load('${ctx}/area/toEdit/'+$("#h_parent").val(),function(){
                                         //加一个遮罩在次
                                    });
                                },"操作成功,您想要继续添加么？");
                                var parentNode = areaTree.getNodeByTId(areaTree.getSelectedNodes()[0].TId);
                                var newNode = {name:d.name,id:data.id,pId:d.parent.id};
                                areaTree.addNodes(areaTree.getSelectedNodes()[0],newNode);
                            } else {
                                alert(data.msg);
                            }
						}).fail(function(data) {
							alert(data.error);
						});
					}
				}
			});
		});
	
	</script>	
</body>
</html>