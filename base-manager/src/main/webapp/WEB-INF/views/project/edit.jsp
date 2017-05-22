<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑项目</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>编辑项目
			</h3>
				
			<form role="form" class="form-horizontal" method="POST" id="projectForm">
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
							<h4 class="header blue bolder smaller">项目</h4>
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name"> 唯一标识  </div>
									<div class="form-info-value">
										<input type="text"  placeholder=" 唯一标识" name="id" value="${model.id}" readonly="true" class="col-xs-10 col-sm-5">
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 项目名称  </div>
									<div class="form-info-value">
										<input type="text"  placeholder="工程名称" name="name" value="${model.name}" class="col-xs-10 col-sm-5">
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 模块名称  </div>
									<div class="form-info-value">
										<input type="text"  placeholder="模块名称" name="mName" value="${model.mName}" class="col-xs-10 col-sm-5">
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 项目位置 </div>
				
									<div class="form-info-value">
										<input type="text" placeholder="工程位置" name="location" value="${model.location}" class="col-xs-10 col-sm-5">
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 项目顶级包 </div>
									<div class="form-info-value">
										<input type="text"  placeholder="项目顶级包" name="rPackage" value="${model.rPackage}" class="col-xs-10 col-sm-5">
									</div>
								</div>
							</div>
							
							<h4 class="header blue bolder smaller">数据库</h4>
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name">Url地址</div>
									<div class="form-info-value"><input type="text" placeholder="Url" value="${model.url}" name="url" class="col-xs-10 col-sm-5"></div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name">用户名</div>
									<div class="form-info-value"><input type="text" placeholder="user" value="${model.user}" name="user" class="col-xs-10 col-sm-5"></div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name">密码</div>
									<div class="form-info-value"><input type="text" placeholder="password" value="${model.password}" name="password" class="col-xs-10 col-sm-5"></div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name">驱动类</div>
									<div class="form-info-value"><input type="text" placeholder="driver" value="${model.driver}" name="driver" class="col-xs-10 col-sm-5"></div>
								</div>
							</div>	
						</div>
						
						<div id="edit-advance" class="tab-pane">
							<h4 class="header blue bolder smaller">工程</h4>
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name"> 项目类型  </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_project_type_key" id="type" name="type" _class="chosen-select" value="${model.type}"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 项目结构  </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_project_struct_key" id="struct" name="struct" _class="chosen-select" value="${model.struct}"></sys:selectItem>
									</div>
								</div>
								
								<div class="form-info-row">
									<div class="form-info-name"> 静态资源目录 </div>
									<div class="form-info-value">
										<input type="text" placeholder="静态资源目录" name="staticDir" value="${model.staticDir}" class="col-xs-10 col-sm-5">
									</div>
								</div>
								
								<div class="form-info-row">
									<div class="form-info-name"> FTL模板目录 </div>
									<div class="form-info-value">
										<input type="text" placeholder="FTL模板目录" name="ftlDir" value="${model.ftlDir}" class="col-xs-10 col-sm-5">
									</div>
								</div>
								
								<div class="form-info-row">
									<div class="form-info-name"> 视图文件目录 </div>
									<div class="form-info-value">
										<input type="text" placeholder="视图文件目录" name="viewDir" value="${model.viewDir}" class="col-xs-10 col-sm-5">
									</div>
								</div>
								
								<div class="form-info-row">
									<div class="form-info-name"> 配置文件生成 </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" id="createConfig" _class="chosen-select" value="${model.createConfig}"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> JAVA文件生成  </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" id="createJava" _class="chosen-select" value="${model.createJava}"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 生成视图文件 </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" id="createJava" _class="chosen-select" value="${model.createJava}"></sys:selectItem>
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
			
			$('.chosen-select').chosen({allow_single_deselect:true,disable_search_threshold: 10}); 
			//resize the chosen on window resize
			$(window).on('resize.chosen', function() {
				//var w = $('.chosen-select').parent().width();
				$('.chosen-select').next().css({'width':339});
			}).trigger('resize.chosen');
			
			$('#projectForm').validate({
				rules:{
					name:{required: true}
				},
				messages:{
					name:{required: '请输入工程名'}
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
						
						$.ajax({
							url : '${ctx}/project/add',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							alert('编辑成功');
						}).fail(function(data) {
							alert('编辑失败');
						});
					}
				}
			});
		});
	</script>
	<script type="text/javascript">
		cancelBtn('${ctx}/project');
	</script>
</body>
</html>