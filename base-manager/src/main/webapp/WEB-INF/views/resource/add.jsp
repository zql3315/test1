<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统资源</title>
<link href="${ctx}/static/ztree/css/zTreeStyle.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>
				添加栏目
			</h3>
			<form role="form" class="form-horizontal" method="POST" id="tableForm">
				<div class="form-info  form-info1  form-info-striped">
				
						<div class="form-info-row">
							<div class="form-info-name"> 资源名称  </div>
							<div class="form-info-value">
								<input type="hidden" value="${param.id }" id="h_parent">
								<input type="text"  placeholder="资源名称" name="name" value="${model.name}" class=" col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 资源简称  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="资源简称" name="sn" value="${model.sn}" class=" col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 资源类型  </div>
		
							<div class="form-info-value">
								<sys:selectItem datakey="dd_resource_type_key" name="type" _class=" col-sm-5 chosen-select"></sys:selectItem>
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 资源URL  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="资源URL" name="url" value="${model.url}" class=" col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 资源图标  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="资源图标" name="icons"class=" col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 资源优先级  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="资源优先级" name="priority" class=" col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 描述  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="描述" name="description" class=" col-sm-5">
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
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<script type="text/javascript">

		$(document).ready(function(){
			
			$('.chosen-select').chosen({allow_single_deselect:true,disable_search_threshold: 10}); 
			//resize the chosen on window resize
			$(window).on('resize.chosen', function() {
				//var w = $('.chosen-select').parent().width();
				$('.chosen-select').next().css({'width':'41.66666667%','float':'left'});
			}).trigger('resize.chosen');
			
			$('#tableForm').validate({
				rules:{
						name:{required: true},
						sn:{required: true,lettleLimitValidator:true},
						type:{required: true},
						priority:{required: true,digits:true},
						desciption:{required: true}
				},
				messages:{
						name:{required: '请输入 资源名称'},
						sn:{required: '请输入 资源简称',lettleLimitValidator:"请输入4-20位的大小写字母"},
						type:{required: '请输入 资源类型'},
						priority:{required: '请输入 资源优先级',digits:"去输入合法的整数"},
						desciption:{required: '请输入 描述'}
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
							url : '${ctx}/resource/add',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							if(data && data.result){
							    showSuccess(function(){
							    	$('#resourceEdit').load('${ctx}/resource/toEdit/'+$("#h_parent").val(),function(){
	                                     //加一个遮罩在次
	                                });
                                },"操作成功,您想要继续添加么？");
							    var parentNode = channelTree.getNodeByTId(channelTree.getSelectedNodes()[0].TId);
							    var iconSkin = "ico_open";
							    if(d.type == 1)iconSkin = " ico_open ";
							    else if(d.type == 2)iconSkin = " ico_docu ";
							    else if(d.type == 3)iconSkin = " modelbutton ";
                                var newNode = {name:d.name,id:data.id,pId:d.parent.id,iconSkin:iconSkin};
                                channelTree.addNodes(channelTree.getSelectedNodes()[0],newNode);
							}else{
								alert(data.msg);
							}
						}).fail(function(data) {
							alert('添加失败');
						});
					}
				}
			});
			
		});
	
	$('#cancel').on('click',function(){
		$('#resourceEdit').load('${ctx}/resource/toEdit/'+$("#h_parent").val(),function(){
             //加一个遮罩在次
        });
	});
	</script>
</body>
</html>