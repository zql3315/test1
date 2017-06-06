<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>	
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ztree demo</title>
<link href="${ctx}/static/ztree/css/zTreeStyle.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="row">
	<div class="col-xs-12">
		<div class="row">
			<div class="col-sm-12">
				<div class="widget-box widget-color-blue2">
					<div class="widget-header">
						<h4 class="widget-title lighter smaller">菜单权限设置</h4>
					</div>

					<div class="widget-body">
						<div class="widget-main padding-8">
							<input value="${model.role.id}" type="hidden"  id="role_id"/>
							<div id="demoTree" class="ztree"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div><!-- /.col -->
</div>
<button class="btn btn-sm btn-purple btn-round no-border" type="button" id="submit" ><i class="ace-icon fa fa-check"></i>提交</button>
<script src="${ctx}/static/ztree/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="${ctx}/static/ztree/jquery.ztree.excheck-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	var setting = {
					check:{
						enable: true,//设置 zTree 的节点上是否显示 checkbox / radio	默认值: false
						chkStyle: "checkbox",
						//Y 属性定义 checkbox 被勾选后的情况； //N 属性定义 checkbox 取消勾选后的情况；
						//"p" 表示操作会影响父级节点；	//"s" 表示操作会影响子级节点。
						chkboxType: { "Y": "ps", "N": "ps" }//勾选 checkbox 对于父子节点的关联关系  
					},
					view:{
						selectedMulti:true//设置是否允许同时选中多个节点。默认值: true
					},
					async: {
						enable: true,//设置 zTree 是否开启异步加载模式 默认值：false
						url:'${ctx}/resource/ztree',
						type:"post",
						otherParam: { "rid":$("#role_id").val()},
						autoParam: ["id"]//异步加载时需要自动提交父节点属性的参数
					},
					callback:{
						onCheck: zTreeOnCheck,
						onAsyncSuccess :roleAsyncSuccess
					}
				};
	var tree = $.fn.zTree.init($("#demoTree"), setting);
	
	function roleAsyncSuccess(event, treeId, treeNode, msg) {
        $(".widget-body").ace_scroll({size: 700});
    };
	function zTreeOnCheck(event, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("demoTree")
		nodes = zTree.getCheckedNodes(true);
		v = "";
		ids="";
		//循环勾选的菜单，取得菜单name及id
		for(var i=0, l=nodes.length; i<l; i++){
			var halfCheck = nodes[i].getCheckStatus().half;
			//console.log(!halfCheck+"==="+nodes[i].name+"==="+nodes[i].type);
		}
	};
	
	$("#submit").on("click",function(){
		var zTree = $.fn.zTree.getZTreeObj("demoTree")
		nodes = zTree.getCheckedNodes(true);
		var d=[];
		//循环勾选的菜单，取得菜单name及id
		for(var i=0, l=nodes.length; i<l; i++){
			var o={};
			o.role={};
			o.role.id=$("#role_id").val();
			o.resource={};
			o.resource.id=nodes[i].id;
			d.push(o);
		}
		$.ajax({
			url : "${ctx}/permission/addALL",
			type : "POST",
			async : false,
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify(d)
		}).done(function(data) {
			if(data && data.result){
				showSuccess(function(){
					openPage("${ctx}/role/preview");
					backBreadcrumb();
				});
			}else{
				alert("添加权限失败");
			}
			
		}).fail(function(data) {
			alert("添加权限失败");
		});
	});
	
});
</script>
</body>
</html>