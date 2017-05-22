<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>权限</title>
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
							<td></td>
							<td></td>
						</tr>
					</table>
					<div class="form-toolbar">
						<div class="btn-group">
							<button class="btn btn-sm btn-purple btn-round no-border" type="submit"><i class="ace-icon fa fa-check smaller-40"></i>查询</button>
						</div>
					</div>
				</form>
			</div>
			<div class="content-bottom"></div>
			<div class="btn-group">
				<button class="btn btn-sm btn-success btn-white btn-round" id="addBtn">
					<i class="ace-icon fa fa-plus bigger-110 green"></i>
					添加
				</button>
	
				<button class="btn btn-sm btn-danger btn-white btn-round" id="editBtn">
					<i class="ace-icon fa fa-pencil-square-o bigger-110 green"></i>
					编辑
				</button>
				
				<button class="btn btn-sm btn-danger btn-white btn-round" id="delBtn">
					<i class="ace-icon fa fa-trash-o bigger-110 red"></i>
					删除
				</button>
				
			</div>
			<!-- table table-striped table-bordered table-hover  cell-border-->
			<table id="table" class="table table-condensed table-hover cell-border">
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
					url : '${ctx}/operation/list',
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

			
			$('#editBtn').on('click',function(){
				var selected_id=$('table input:checkbox:checked').attr('id');
				if(!selected_id){
					alert("您必须选择一个来操作");
					return ;
				}
				//breadcrumb
				var breadcrumb_items = getBreadcrumb();
				breadcrumb_items.reverse();
				//取出当前页面导航添加链接
				var lastItem=breadcrumb_items[breadcrumb_items.length-1];
				lastItem['data-action']="${ctx}/operation/preview";
				breadcrumb_items[breadcrumb_items.length-1]=lastItem;
				
				breadcrumb_items.push({'text': $(this).text(), 'data-action': '${ctx}/operation/toEdit'});
				breadcrumb_items.reverse();
				//渲染breadcrumb
				refreshBreadcrumb(breadcrumb_items);
				
				$('div').find('.page-content').load('${ctx}/operation/toEdit/'+selected_id,function(){
					 //加一个遮罩在次
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