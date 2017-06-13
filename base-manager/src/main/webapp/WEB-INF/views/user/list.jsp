<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>用户表</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<div class="form-wrap-tpl">
				<form role="form" class="form-tpl" method="POST" id="form">
					<table class="table table-condensed cell-border form-table">
						<tr>
							<td class="form-table-label">账号 :</td>
							<td class="form-table-input"><input type="text" class="form-control input-sm" name="search_LIKE_loginname"></td>
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
				<shiro:hasPermission name="home:system:user:toAddUser:view">
					<button class="btn btn-sm btn-success btn-white btn-round" id="addBtn">
						<i class="ace-icon fa fa-plus bigger-110 green"></i>
						添加
					</button>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="home:system:user:toEditUser:view">
					<button class="btn btn-sm btn-danger btn-white btn-round" id="editBtn">
						<i class="ace-icon fa fa-pencil-square-o bigger-110 green"></i>
						编辑
					</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="home:system:user:changePassword:view">
					<button class="btn btn-sm btn-danger btn-white btn-round" id="changePassword">
						<i class="ace-icon fa fa-pencil-square-o bigger-110 green"></i>
						修改密码
					</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="home:system:user:delete:view">
					<button class="btn btn-sm btn-danger btn-white btn-round" id="delBtn">
						<i class="ace-icon fa fa-trash-o bigger-110 red"></i>
						删除
					</button>
				</shiro:hasPermission>
			</div>
			<!-- table table-striped table-bordered table-hover  cell-border-->
			<table id="table" class="table table-condensed table-hover cell-border">
			</table>
			<!-- PAGE CONTENT ENDS -->
		</div><!-- /.col -->
	</div><!-- /.row -->
	<script type="text/javascript">
		var formData=$('#form').serializeArray();
		$(document).ready(function(){
			
			//表格查询
			var table=$('#table').DataTable({
				"bSort" : true,
				"processing" : true,
				"serverSide" : true,
				"ajax" : {
					url : '${ctx}/user/listUser',
					type : 'POST',
					data : function(d) {
						var s = {};
						var as = [];
						$.each(d.order, function(i, order) {
							if(order.column > 0 ){
								$.each(d.columns, function(i, column) {
									if(i == order.column ){
										 s.fileName = column.data;
										 s.sort = order.dir;
										 as.push(s);
									}
		                        });
							}
                        });
						if(as.length > 0){
							d["sort"] = JSON.stringify(as);
						}
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
							"bSortable": false,
							render:function(data, type, row,meta){
								return '<label class="position-relative">'
											+'<input type="checkbox" id="'+row.id+'"class="ace">'
											+'<span class="lbl"></span>'
										+'</label>';
							}},
							{"title" : "账号",'data' : 'loginname'}, 
							{"title" : "用户名称",'data' : 'name'}, 
							{"title" : "联系方式",'data' : 'telephone'}, 
							{"title" : "邮件",'data' : 'email'},
							{"title" : "角色",'data' : 'role',"bSortable": false,},
							{"title" : "创建人",'data' : 'parent.name',"bSortable": false,
								render:function(data, type, row,meta){
									if(row.parent){
										return row.parent.name;
									}else{
										return "默认用户";
									}
								}
							}
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
				//breadcrumb
				var breadcrumb_items = getBreadcrumb();
				breadcrumb_items.reverse();
				//取出当前页面导航添加链接
				var lastItem=breadcrumb_items[breadcrumb_items.length-1];
				lastItem['data-action']="${ctx}/user/preview";
				breadcrumb_items[breadcrumb_items.length-1]=lastItem;
				
				breadcrumb_items.push({'text': $(this).text(), 'data-action': '${ctx}/user/toAdd'});
				breadcrumb_items.reverse();
				//渲染breadcrumb
				refreshBreadcrumb(breadcrumb_items);
				
				$('div').find('.page-content').load('${ctx}/user/toAdd',function(){
					 //加一个遮罩在次
				});
			});
			
			$('#editBtn').on('click',function(){
				var selected_id=$('table input:checkbox:checked').attr('id');
				var length = $("table input:checkbox:checked").length;
				if(length != 1 || !selected_id){
                    alert("您必须选择一个来操作");
                    return ;
                }
				//breadcrumb
				var breadcrumb_items = getBreadcrumb();
				breadcrumb_items.reverse();
				//取出当前页面导航添加链接
				var lastItem=breadcrumb_items[breadcrumb_items.length-1];
				lastItem['data-action']="${ctx}/user/preview";
				breadcrumb_items[breadcrumb_items.length-1]=lastItem;
				
				breadcrumb_items.push({'text': $(this).text(), 'data-action': '${ctx}/user/toEditUser'});
				breadcrumb_items.reverse();
				//渲染breadcrumb
				refreshBreadcrumb(breadcrumb_items);
				$('div').find('.page-content').load('${ctx}/user/toEditUser/'+selected_id,function(){
					 //加一个遮罩在次
				});
			});
			$('#changePassword').on('click',function(){
				var selected_id=$('table input:checkbox:checked').attr('id');
				var length = $("table input:checkbox:checked").length;
				if(length != 1 || !selected_id){
					alert("您必须选择一个来操作");
					return ;
				}
				//breadcrumb
				var breadcrumb_items = getBreadcrumb();
				breadcrumb_items.reverse();
				//取出当前页面导航添加链接
				var lastItem=breadcrumb_items[breadcrumb_items.length-1];
				lastItem['data-action']="${ctx}/user/preview";
				breadcrumb_items[breadcrumb_items.length-1]=lastItem;
				
				breadcrumb_items.push({'text': $(this).text(), 'data-action': '${ctx}/user/changePassword'});
				breadcrumb_items.reverse();
				//渲染breadcrumb
				refreshBreadcrumb(breadcrumb_items);
				$('div').find('.page-content').load('${ctx}/user/changePassword/'+selected_id,function(){
					 //加一个遮罩在次
				});
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
				window.confirm("是否删除该用户?", function(_dialog){
					$.ajax({
						url : '${ctx}/user/deleteUser',
						type : 'POST',
						async : false,
						dataType : 'json',
						contentType : 'application/json;charset=UTF-8',
						data : JSON.stringify(rl)
					}).done(function(data) {
						$( _dialog ).dialog( "close" );
						if(data.result){
							table.ajax.reload();
							alert('删除成功');
							return;
						}else{
							alert(data.msg);
						}
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