<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>角色</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<div class="form-wrap-tpl">
				<form role="form" class="form-tpl" method="POST" id="form">
					<table class="table table-condensed cell-border form-table">
						<tr>
							<td class="form-table-label">名称 :</td>
							<td class="form-table-input"><input type="text" class="form-control input-sm" name="search_LIKE_name"></td>
							<td>
								<div class="btn-group">
									<button class="btn btn-sm btn-purple btn-round no-border" type="submit"><i class="ace-icon fa fa-check smaller-40"></i>查询</button>
									<button class="btn btn-sm btn-purple btn-round no-border" type="reset"><i class="ace-icon fa fa-undo smaller-40"></i>重置</button>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="content-bottom"></div>
			<div class="btn-group">
				<shiro:hasPermission name="home:system:role:add:view">
					<button class="btn btn-sm btn-success btn-white btn-round" id="addBtn">
						<i class="ace-icon fa fa-plus bigger-110 green"></i>
						添加
					</button>
				</shiro:hasPermission>
	
				<shiro:hasPermission name="home:system:role:edit:view">
					<button class="btn btn-sm btn-danger btn-white btn-round" id="editBtn">
						<i class="ace-icon fa fa-pencil-square-o bigger-110 green"></i>
						编辑
					</button>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="home:system:role:delete:view">
					<button class="btn btn-sm btn-danger btn-white btn-round" id="delBtn">
						<i class="ace-icon fa fa-trash-o bigger-110 red"></i>
						删除
					</button>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="home:system:role:permission:view">
					<button class="btn btn-sm btn-danger btn-white btn-round" id="rightBtn">
						<i class="ace-icon fa fa-user-secret bigger-120 green"></i>
						权限
					</button>
				</shiro:hasPermission>
				
			</div>
			<!-- table table-striped table-bordered table-hover  cell-border-->
			<table id="table" class="table table-condensed table-hover cell-border" >
			</table>
			<!-- PAGE CONTENT ENDS -->
		</div><!-- /.col -->
	</div><!-- /.row -->
	<script src="${ctx}/static/custom/b2r.js"></script>
	
	<script type="text/javascript">
		var formData=$('#form').serializeArray();
		$(document).ready(function(){
			
			//表格查询
			var table=$('#table').DataTable({
				"bSort" : false,
				"processing" : true,
				"serverSide" : true,
				"ajax" : {
					url : '${ctx}/role/list',
					type : 'POST',
					data : function(d) {
						if (formData && typeof formData == 'object') {
							$.each(formData, function(i, item) {
								if (item.name && item.value) {
									d[item.name] = item.value;
								}
							});
							
						}
					}
				},
				'columns' :[{"title" : '<label class="position-relative">'
											+'<input type="checkbox" class="ace">'
											+'<span class="lbl"></span>'
										+'</label>','data':'id',
							render:function(data, type, row,meta){
								return '<label class="position-relative">'
											+'<input type="checkbox" id="'+row.id+'"class="ace">'
											+'<span class="lbl"></span>'
										+'</label>';
							}},
								{"title" : "名称",'data' : 'name'}
								, 
								{"title" : "简称",'data' : 'sn'}
								, 
								{"title" : "描述",'data' : 'description'}
								, 
								{"title" : "创建时间",'data' : 'createdDate',render:function(data, type, row,meta){
									return new Date(data).pattern("yyyy-MM-dd");
								}}
								, 
								{"title" : "创建者",'data' : 'creator'}
								 
				           ]
			});
			
			$(document).on('click', 'th input:checkbox' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					//$(this).closest('tr').toggleClass('selected');
				});
			});

			$('#addBtn').on('click',function(){
				$("#addBtn").attr('disabled', 'disabled');
				$('div').find('.page-content').load('${ctx}/role/toAdd',function(){
					 //加一个遮罩在次
				});
				//breadcrumb
				var breadcrumb_items = getBreadcrumb();
				breadcrumb_items.reverse();
				//取出当前页面导航添加链接
				var lastItem=breadcrumb_items[breadcrumb_items.length-1];
				lastItem['data-action']="${ctx}/role/preview";
				breadcrumb_items[breadcrumb_items.length-1]=lastItem;
				
				breadcrumb_items.push({'text': $(this).text(), 'data-action': '${ctx}/role/toAdd'});
				breadcrumb_items.reverse();
				//渲染breadcrumb
				refreshBreadcrumb(breadcrumb_items);
				
			});
			
			$('#editBtn').on('click',function(){
				var selected_id=$('table input:checkbox:checked').attr('id');
				var length = $("table input:checkbox:checked").length;
				if(length != 1){
					alert("您必须选择一个来操作");
					return ;
				}
				
				$("#editBtn").attr('disabled', 'disabled');
				$('div').find('.page-content').load('${ctx}/role/toEdit/'+selected_id,function(){
					 //加一个遮罩在次
				});
				//breadcrumb
				var breadcrumb_items = getBreadcrumb();
				breadcrumb_items.reverse();
				//取出当前页面导航添加链接
				var lastItem=breadcrumb_items[breadcrumb_items.length-1];
				lastItem['data-action']="${ctx}/role/preview";
				breadcrumb_items[breadcrumb_items.length-1]=lastItem;
				
				breadcrumb_items.push({'text': $(this).text(), 'data-action': '${ctx}/role/toEdit'});
				breadcrumb_items.reverse();
				//渲染breadcrumb
				refreshBreadcrumb(breadcrumb_items);
				
			});
			
			
			$('#rightBtn').on('click',function(){
				var selected_id=$('table input:checkbox:checked').attr('id');
				var length = $("table input:checkbox:checked").length;
				if(length != 1){
					alert("您必须选择一个来操作");
					return ;
				}
				$("#rightBtn").attr('disabled', 'disabled');
				$('div').find('.page-content').load('${ctx}/permission/right/'+selected_id,function(){
					 //加一个遮罩在次
				});
				//breadcrumb
				var breadcrumb_items = getBreadcrumb();
				breadcrumb_items.reverse();
				//取出当前页面导航添加链接
				var lastItem=breadcrumb_items[breadcrumb_items.length-1];
				lastItem['data-action']="${ctx}/role/preview";
				breadcrumb_items[breadcrumb_items.length-1]=lastItem;
				
				breadcrumb_items.push({'text': $(this).text(), 'data-action': '${ctx}/permission/right'});
				breadcrumb_items.reverse();
				//渲染breadcrumb
				refreshBreadcrumb(breadcrumb_items);
				
			});
			
			$('#delBtn').on('click',function(e){
				var rl=[];
				$('table tbody input:checkbox:checked').each(function(){
					var r={};
					r.id=$(this).attr('id');
					rl.push(r);
				});
				if(rl.length <=0 ){
					alert("您必须至少选择一个来操作"); return ;
				}
				e.preventDefault();
				window.confirm("是否删除角色?", function(_dialog){
					$.ajax({
						url : '${ctx}/role/deleteRole',
						type : 'POST',
						async : false,
						dataType : 'json',
						contentType : 'application/json;charset=UTF-8',
						data : JSON.stringify(rl)
					}).done(function(data) {
						table.ajax.reload();
						$( _dialog ).dialog( "close" );
						if(data.code == 0001){
							alert("选择的角色已经被用户关联，无法删除！");
							return;
						}
						alert('删除成功');
					}).fail(function(data) {
						alert('删除失败');
					});
				});
				
			});
			
			$('#form').on('submit', function(event) {
				event.preventDefault();
				formData = $(this).serializeArray();
				table.ajax.reload();
			});
		});
	</script>
</body>
</html>