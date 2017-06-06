<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目新增</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-floppy-o"></i>新增项目
			</h3>
			<div class="well well-sm">
				<button type="button" class="close" data-dismiss="alert">×</button>&nbsp;
				<div class="inline middle blue smaller-90"> 项目创建进度 </div>&nbsp; &nbsp; &nbsp;
				<div style="width:200px;" data-percent="50%" class="inline middle no-margin progress progress-striped active">
					<div class="progress-bar progress-bar-success" style="width:50%"></div>
				</div>
			</div>
			<div class="space"></div>
			<form class="form-horizontal" role="form"  method="POST"  id="form">
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
									<div class="form-info-name"> 项目名称  </div>
									<div class="form-info-value">
										<input type="text"  placeholder="项目名称" name="name" class="col-xs-10 col-sm-5">
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 模块名称  </div>
									<div class="form-info-value">
										<input type="text"  placeholder="模块名称" name="mName" class="col-xs-10 col-sm-5">
									</div>
								</div>
				
								<div class="form-info-row">
									<div class="form-info-name"> 项目位置 </div>
				
									<div class="form-info-value">
										<input type="text" placeholder="项目位置" name="location" class="col-xs-10 col-sm-5">
									</div>
								</div>
				
								<div class="form-info-row">
									<div class="form-info-name"> 项目顶级包 </div>
				
									<div class="form-info-value">
										<input type="text"  placeholder="项目顶级包" name="rPackage" class="col-xs-10 col-sm-5">
									</div>
								</div>
							</div>
							
							<h4 class="header blue bolder smaller">数据库</h4>
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name">Url地址</div>
									<div class="form-info-value"><input type="text" placeholder="Url" name="url" class="col-xs-10 col-sm-5"></div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name">用户名</div>
									<div class="form-info-value"><input type="text" placeholder="user" name="user" class="col-xs-10 col-sm-5"></div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name">密码</div>
									<div class="form-info-value"><input type="text" placeholder="password" name="password" class="col-xs-10 col-sm-5"></div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name">驱动类</div>
									<div class="form-info-value"><input type="text" placeholder="driver" name="driver" class="col-xs-10 col-sm-5"></div>
								</div>
							</div>	
						</div>
						<div id="edit-advance" class="tab-pane">
							<h4 class="header blue bolder smaller">工程</h4>
							<div class="form-info">
								<div class="form-info-row">
									<div class="form-info-name"> 项目类型  </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_project_type_key" id="type" name="type" _class="chosen-select" value="1"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 项目结构  </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_project_struct_key" id="struct" name="struct" _class="chosen-select" value="1"></sys:selectItem>
									</div>
								</div>
								
								<div class="form-info-row">
									<div class="form-info-name"> 静态资源目录 </div>
									<div class="form-info-value">
										<input type="text" placeholder="静态资源目录" name="staticDir" value="F:\wks\work\b2r\b2r-build\src\main\webapp\static" class="col-xs-10 col-sm-5">
									</div>
								</div>
								
								<div class="form-info-row">
									<div class="form-info-name"> FTL模板目录 </div>
									<div class="form-info-value">
										<input type="text" placeholder="FTL模板目录" name="ftlDir" value="classpath:/ftl/" class="col-xs-10 col-sm-5">
									</div>
								</div>
								
								<div class="form-info-row">
									<div class="form-info-name"> 视图文件目录 </div>
									<div class="form-info-value">
										<input type="text" placeholder="视图文件目录" name="viewDir" value="views" class="col-xs-10 col-sm-5">
									</div>
								</div>
								
								<div class="form-info-row">
									<div class="form-info-name"> 配置文件生成 </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" id="createConfig" _class="chosen-select" value="1"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> JAVA文件生成  </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" id="createJava" _class="chosen-select" value="1"></sys:selectItem>
									</div>
								</div>
								<div class="form-info-row">
									<div class="form-info-name"> 生成视图文件 </div>
									<div class="form-info-value">
										<sys:selectItem datakey="dd_whether_key" id="createJava" _class="chosen-select" value="1"></sys:selectItem>
									</div>
								</div>
							</div>	
						</div>
					</div>
				</div>	
				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button class="btn btn-info" type="submit">
							<i class="ace-icon fa fa-check bigger-110"></i>
							保存
						</button>

						&nbsp; &nbsp;
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
			
			$('#form').validate({
				rules:{
						name:{required: true}, 
						mName:{required: true}, 
						location:{required: true}, 
						rPackage:{required: true}, 
						url:{required: true},
						user:{required: true},
						password:{required: true},
						driver:{required: true}
				},
				messages:{
						name:{required: '请输入 名称'}, 
						mName:{required: '请输入 模块名称'}, 
						location:{required: '请输入项目位置'}, 
						rPackage:{required: '请输入 顶级包名'}, 
						url:{required: '请输入 数据库URL'},
						user:{required: '请输入 数据库用户名'},
						password:{required: '请输入数据库密码'},
						driver:{required: '请输入驱动'}
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
		cancelBtn('${ctx}/project');
	</script>
</body>
</html>