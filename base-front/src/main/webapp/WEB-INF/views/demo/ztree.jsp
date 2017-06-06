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

<div id="demoTree" class="ztree"></div>
<select class="chosen-select" id="operation" multiple>
	<c:forEach items="${operations}" var="item">
		<option value="${item.id}">${item.name}</option>
	</c:forEach>
</select>
<button class="btn btn-sm btn-purple btn-round no-border" type="button" id="test" ><i class="ace-icon fa fa-check smaller-40"></i>测试</button>
<button class="btn btn-sm btn-purple btn-round no-border" type="button" id="testreset" ><i class="ace-icon fa fa-check smaller-40"></i>重置</button>

<script src="${ctx}/static/ztree/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="${ctx}/static/ztree/jquery.ztree.excheck-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	var setting = {
			check:{
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "ps" }},
			view:{selectedMulti:false,addDiyDom: addOperHtml},
			data:{simpleData:{enable:true}},
			async: {
				enable: true,
				url:'${ctx}/demo/ztree',
				type:'post',
				autoParam: ["id"]
			},
			callback:{
				beforeClick:function(id, node){
				tree.checkNode(node, !node.checked, true, true);
				//node.open=true;
				return false;
				}
			}
			};
	var tree = $.fn.zTree.init($("#demoTree"), setting);
	
	$('#test').on('click',function(){
		var treeObj = $.fn.zTree.getZTreeObj("demoTree");
		var nodes = treeObj.getCheckedNodes(true);
		console.log(nodes);
	});
	
	function addOperHtml(treeId, treeNode){
		//<a>标签
		var aObj = $("#" + treeNode.tId + "_a");
		var oper_array=[{'id':'1','name':'view'},{'id':'2','name':'add'},{'id':'3','name':'modify'},{'id':'4','name':'delete'}];
		
		$.each(oper_array,function(i){
			
			if ($("#" + treeNode.tId + "_"+this.id+"_chx").length>0) return;
			
			//var html="<input name='operateId' type='checkbox' style='position: absolute;' id='"+treeNode.tId+ "_"+oper_array[i].id+"_chx'/><span style='line-height: 20px;  position: relative;margin-left: 15px;'>  "+oper_array[i].name+"  </span>";
			//var html="<input name='form-field-checkbox' type='checkbox' class='ace' id='"+treeNode.tId+ "_"+oper_array[i].id+"_chx'/><span class='lbl'>  "+oper_array[i].name+"  </span>";
			  var html="<span id='"+ treeNode.tId + "_"+this.id+"_chx' class='button chk checkbox_false_full'></span> <span>  "+oper_array[i].name+"  </span>";
			
			aObj.after(html);
			var btn = $("#" + treeNode.tId + "_"+this.id+"_chx");
			if (btn) {
				btn.bind("click", function(){
					alert("diy Button for " + oper_array[i].name);
					
					if ($(this).hasClass('checkbox_false_full')) {
						$(this).toggleClass('checkbox_true_full');
					}else if($(this).hasClass('checkbox_true_full')){
						$(this).toggleClass('checkbox_false_full');
					}
					
				});
			}
		});
	};
	
	$('.chosen-select').chosen({allow_single_deselect:true}); 
	$(window).on('resize.chosen', function() {
		var w = $('.chosen-select').parent().width();
		$('.chosen-select').next().css({'width':'50%'});
	}).trigger('resize.chosen');
	
	$('#test').on('click',function(){
		$('.chosen-select').val(['1','2']).trigger("chosen:updated");;
		
		alert($('.chosen-select').val());
	});
	
	$('#testreset').on('click',function(){
		$('.chosen-select').val('').trigger("chosen:updated");;
		
		alert($('.chosen-select').val());
	});
});
</script>
</body>
</html>