<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>
				添加表
			</h3>
			<form role="form" class="form-horizontal" method="POST" id="tableForm">
				<div class="tabbable">
					<ul class="nav nav-tabs padding-16">
						<li class="active">
							<a data-toggle="tab" href="#edit-basic">
								<i class="green ace-icon fa fa-info bigger-125"></i>
								基本信息
							</a>
						</li>
						<li>
							<a data-toggle="tab" href="#edit-advance">
								<i class="blue ace-icon fa fa-external-link  bigger-125"></i>
								高级信息
							</a>
						</li>
					</ul>
					
					<div class="tab-content profile-edit-tab-content">
						<div id="edit-basic" class="tab-pane in active">
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name"> 所属项目  </div>
									<div class="form-info-value">
										<select class="chosen-select" id="project">
											<c:forEach items="${projects}" var="item">
												<option value="${item.id}">${item.name} -> ${item.mName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 数据表名  </div>
				
									<div class="form-info-value">
										<input type="text"  placeholder="数据表名" name="name" class="col-xs-10 col-sm-5">
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 实体对象名  </div>
				
									<div class="form-info-value">
										<input type="text"  placeholder="实体对象名 " name="eName" class="col-xs-10 col-sm-5">
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 简称  </div>
				
									<div class="form-info-value">
										<input type="text"  placeholder="简称 " name="sn" class="col-xs-10 col-sm-5">
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 描述  </div>
				
									<div class="form-info-value">
										<input type="text"  placeholder="描述" name="description" class="col-xs-10 col-sm-5">
									</div>
								</div>
							</div>
						</div>
						
						<div id="edit-advance" class="tab-pane">
							<h4 class="header blue bolder smaller">PO</h4>
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name"> 是否生成持久层对象  </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" name="createPO" _class="chosen-select" value="1"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 持久层对象模板  </div>
									<div class="form-info-value">
										<input type="text"  placeholder="实体对象名 " name="poTemplate" value="po.ftl" class="col-xs-10 col-sm-5">
									</div>
								</div>
							</div>
							
							<h4 class="header blue bolder smaller">DTO</h4>
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name"> 是否生成数据传输层对象  </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" name="createDTO" _class="chosen-select" value="1"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 数据传输层对象模板  </div>
									<div class="form-info-value">
										<input type="text"  placeholder="数据传输层对象模板" name="dtoTemplate" value="dto.ftl" class="col-xs-10 col-sm-5">
									</div>
								</div>
							</div>
							
							<h4 class="header blue bolder smaller">DAO</h4>
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name"> 是否生成数据访问层  </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" name="createDAO" _class="chosen-select" value="1"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 数据访问层模板  </div>
									<div class="form-info-value">
										<input type="text"  placeholder="数据访问层模板" name="daoTemplate" value="dao.ftl" class="col-xs-10 col-sm-5">
									</div>
								</div>
							</div>
							
							<h4 class="header blue bolder smaller">Service</h4>
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name"> 是否生成服务层  </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" name="createService" _class="chosen-select" value="1"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 服务层模板  </div>
									<div class="form-info-value">
										<input type="text"  placeholder="服务层模板" name="serviceTemplate" value="service.ftl" class="col-xs-10 col-sm-5">
									</div>
								</div>
							</div>
							
							<h4 class="header blue bolder smaller">视图控制层</h4>
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name"> 是否生成视图控制层 </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" name="createController" _class="chosen-select" value="1"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 视图控制层模板  </div>
									<div class="form-info-value">
										<input type="text"  placeholder="视图控制层模板" name="controllerTemplate" value="controller.ftl" class="col-xs-10 col-sm-5">
									</div>
								</div>
							</div>
							
							<h4 class="header blue bolder smaller">新增视图</h4>
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name"> 是否生成新增视图 </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" name="createAddView" _class="chosen-select" value="1"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 新增视图模板  </div>
									<div class="form-info-value">
										<input type="text"  placeholder="新增视图模板" name="addViewTemplate" value="add_view.ftl" class="col-xs-10 col-sm-5">
									</div>
								</div>
							</div>
							
							<h4 class="header blue bolder smaller">修改视图</h4>
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name"> 是否生成修改视图 </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" name="createModView" _class="chosen-select" value="1"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 修改视图模板  </div>
									<div class="form-info-value">
										<input type="text"  placeholder="新增视图模板" name="modViewTemplate" value="edit_view.ftl" class="col-xs-10 col-sm-5">
									</div>
								</div>
							</div>
							
							<h4 class="header blue bolder smaller">列表查询视图</h4>
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name"> 是否生成列表查询视图 </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" name="createListView" _class="chosen-select" value="1"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 列表查询视图模板  </div>
									<div class="form-info-value">
										<input type="text"  name="listViewTemplate" value="list_view.ftl" class="col-xs-10 col-sm-5">
									</div>
								</div>
							</div>
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
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			//resize the chosen on window resize
			$(window).on('resize.chosen', function() {
				//var w = $('.chosen-select').parent().width();
				$('.chosen-select').next().css({'width':339});
			}).trigger('resize.chosen');
			
			$('#tableForm').validate({
				rules:{
						description:{required: true}, 
						eName:{required: true}, 
						name:{required: true},
						sn:{required: true}
				},
				messages:{
						description:{required: '请输入描述'}, 
						eName:{required: '请输入表对应的实体对象名'}, 
						name:{required: '请输入数据表名'},
						sn:{required: '请输入简称'}
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
						
						d.projects=[];
						var b={};
						b.project={};
						b.project.id=$('#project').val();
						d.projects.push(b);
						//console.log(d);
						$.ajax({
							url : '${ctx}/table/add',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							alert('添加成功');
						}).fail(function(data) {
							alert('添加失败');
						});
					}
				}
			});
		});
	
	</script>
	<script type="text/javascript">
		cancelBtn('${ctx}/table');
	</script>
</body>
</html>