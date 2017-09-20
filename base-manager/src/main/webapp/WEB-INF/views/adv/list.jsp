<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>广告位管理</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<div class="form-wrap-tpl">
				<form role="form" class="form-tpl" method="POST" id="form">
					<table class="table table-condensed cell-border form-table">
						<tr>
							<td class="form-table-label">广告位名称 :</td>
							<td class="form-table-input"><input type="text" class="form-control input-sm" name="search_LIKE_advname" style="width: 80%;"></td>
							<td class="form-table-label">广告位位置 :</td>
							<td class="form-table-input">
								<sys:selectItem datakey="dd_adv_advposition" firstIsBlank="true" name="search_EQ_advposition" _class="chosen-select"></sys:selectItem>
							</td>
							<td class="form-table-label">广告位类型 :</td>
							<td class="form-table-input">
								<sys:selectItem datakey="dd_adv_type" firstIsBlank="true" name="search_EQ_advtype" _class="chosen-select"></sys:selectItem>
							</td>
						</tr>
						<tr>
							<td class="form-table-label">终端类型 :</td>
                            <td class="form-table-input">
                                <select name="search_EQ_terminaltype" placeholder="请选择"  class=" chosen-select col-xs-10 col-sm-5">
                                  <option value=""></option>
                                  <option value="1">手机</option>
                                  <option value="2">PC</option>
                                </select>   
                            </td>
							<td class="form-table-label">所属城市</td>
							<td class="form-table-input">
								<sys:selectArea firstIsBlank="true" level="1" name="search_EQ_citycode" _class="col-xs-10 col-sm-5 chosen-select"></sys:selectArea>
							</td>
							<td class="form-table-label"></td>
							<td class="form-table-input">
							</td>
						</tr>
					</table>
					<div class="form-toolbar">
                        <div class="btn-group">
                            <button class="btn btn-sm btn-purple btn-round no-border" type="submit"><i class="ace-icon fa fa-check smaller-40"></i>查询</button>
                            <button class="btn btn-sm btn-purple btn-round no-border" type="reset"><i class="ace-icon fa fa-undo smaller-40"></i>重置</button>
                        </div>
                    </div>
				</form>
			</div>
			<div class="content-bottom"></div>
			<div class="btn-group">
				<shiro:hasPermission name="home:advmanager:advposition:toAdd:view">
					<button class="btn btn-sm btn-success btn-white btn-round" id="addBtn">
						<i class="ace-icon fa fa-plus bigger-110 green"></i>
						添加
					</button>
				</shiro:hasPermission>
	
				<shiro:hasPermission name="home:advmanager:advposition:toEdit:view">
					<button class="btn btn-sm btn-danger btn-white btn-round" id="editBtn">
						<i class="ace-icon fa fa-pencil-square-o bigger-110 green"></i>
						编辑
					</button>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="home:advmanager:advposition:deleteByIds:view">
					<button class="btn btn-sm btn-danger btn-white btn-round" id="delBtn">
						<i class="ace-icon fa fa-trash-o bigger-110 red"></i>
						删除
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
			
			$('.chosen-select').chosen({allow_single_deselect:true,disable_search_threshold: 10}); 
			$(window).on('resize.chosen', function() {
				$('.chosen-select').next().css({'width':'80.66666667%','float':'left'});
			}).trigger('resize.chosen');
			
			//表格查询
			var table=$('#table').DataTable({
				"bSort" : false,
				"processing" : true,
				"serverSide" : true,
				"ajax" : {
					url : '${ctx}/adv/list',
					type : 'POST',
					data : function(d) {
						if (formData && typeof formData == 'object') {
							$.each(formData, function(i, item) {
								if (item.name && item.value) {
									d[item.name] = item.value;
								}
							});
							d["sort"] ='{"data":[{"fileName":"createtime","sort":"desc"}]}';
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
								{"title" : "广告位名称",'data' : 'advname'}
								, 
								{"title" : "广告位位置",'data' : 'advpositionname'
								}, 
								{"title" : "广告位类型",'data' : 'advtypename'}
								, 
								{"title" : "广告位规格",'data' : 'advspecification'}
								, 
								{"title" : "广告位地址",'data' : 'advurl'}
								, 
								{"title" : "是否显示",'data' : 'isdisplay',
									render:function(data, type, row,meta){
										if(data =='1')
											return "是";
										else 
											return "否";
									}
								}, 
								{"title" : "广告位编号",'data' : 'advid'}
								,
								{"title" : "创建时间",'data' : 'createtime',
									render:function(data, type, row,meta){
										return new Date(data).pattern("yyyy-MM-dd HH:mm:ss");
									}							
								}, 
								{"title" : "内容描述",'data' : 'description'}
								 
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
				$('div').find('.page-content').load('${ctx}/adv/toAdd',function(){
					 //加一个遮罩在次
				});
				//breadcrumb
				var breadcrumb_items = getBreadcrumb();
				breadcrumb_items.reverse();
				//取出当前页面导航添加链接
				var lastItem=breadcrumb_items[breadcrumb_items.length-1];
				lastItem['data-action']="${ctx}/adv/preview";
				breadcrumb_items[breadcrumb_items.length-1]=lastItem;
				
				breadcrumb_items.push({'text': $(this).text(), 'data-action': '${ctx}/adv/toAdd'});
				breadcrumb_items.reverse();
				//渲染breadcrumb
				refreshBreadcrumb(breadcrumb_items);
				
			});
			
			$('#editBtn').on('click',function(){
				var selected_id=$('table input:checkbox:checked').attr('id');
				var length = $("table input:checkbox:checked").length;
				 if(length != 1 || !selected_id){
					alert("您必须选择一个来操作");
					return ;
				}
				
				$("#editBtn").attr('disabled', 'disabled');
				$('div').find('.page-content').load('${ctx}/adv/toEdit/'+selected_id,function(){
					 //加一个遮罩在次
				});
				//breadcrumb
				var breadcrumb_items = getBreadcrumb();
				breadcrumb_items.reverse();
				//取出当前页面导航添加链接
				var lastItem=breadcrumb_items[breadcrumb_items.length-1];
				lastItem['data-action']="${ctx}/adv/preview";
				breadcrumb_items[breadcrumb_items.length-1]=lastItem;
				
				breadcrumb_items.push({'text': $(this).text(), 'data-action': '${ctx}/adv/toEdit'});
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
				window.confirm("是否删除?", function(_dialog){
					$.ajax({
						url : '${ctx}/adv/deleteByIds',
						type : 'POST',
						async : false,
						dataType : 'json',
						contentType : 'application/json;charset=UTF-8',
						data : JSON.stringify(rl)
					}).done(function(data) {
						table.ajax.reload();
						$( _dialog ).dialog( "close" );
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