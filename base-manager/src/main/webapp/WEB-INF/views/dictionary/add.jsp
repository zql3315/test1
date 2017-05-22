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
				添加
			</h3>
			<form role="form" class="form-horizontal" method="POST" id="form">
				<div class="form-info form-info1 form-info-striped">
					<div class="form-info-row">
						<div class="form-info-name"> KEY  </div>
	
						<div class="form-info-value">
							<input type="text"  placeholder="KEY" name="datakey" value="${model.datakey}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name"> 编码  </div>
	
						<div class="form-info-value">
							<input type="text"  placeholder="编码" name="itemcode" value="${model.itemcode}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name"> 名称  </div>
	
						<div class="form-info-value">
							<input type="text"  placeholder="值" name="itemvalue" value="${model.itemvalue}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
                        <div class="form-info-name">父级</div>
                        <div class="form-info-value">
                            <sys:selectItem datakey="" firstIsBlank="true"  isIdValue="true" name="parent.id" _class="col-xs-10 col-sm-5 chosen-select"></sys:selectItem>
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
            
            //$.fn.zTree.init($("#dictionaryTree"), setting);
			
			$("#form").validate({
				rules:{
						datakey:{required: true}
						, 
						itemcode:{required: true}
						, 
						itemvalue:{required: true}
						 
				},
				messages:{
						datakey:{required: "请输入 KEY"}
						, 
						itemcode:{required: "请输入 编码"}
						, 
						itemvalue:{required: "请输入 值"}
						 
				},
				submitHandler:function(form){
					var formData = $(form).serializeArray();
					if ($.isArray(formData)) {
						var d={};
						$.each(formData, function(i, item) {
							if(item.name.indexOf(".") > 0 && item.value ){
                                var temp={};
                                temp[item.name.substr(item.name.indexOf(".")+1)] = item.value;
                                d[item.name.substr(0,item.name.indexOf("."))] = temp;
                            }else if (item.name && item.value) {
								d[item.name] = item.value;
							}
						});
						$.ajax({
							url : "${ctx}/dictionary/add",
							type : "POST",
							async : false,
							dataType : "json",
							contentType : "application/json;charset=UTF-8",
							data : JSON.stringify(d)
						}).done(function(data) {
							if(data && data.result)
								showSuccess(function(){
                                    openPage('${ctx}/dictionary/preview');
                                    backBreadcrumb();
                                });
							else
							 alert("添加失败");
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