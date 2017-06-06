<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
<title>自定义菜单配置</title>
<link rel="stylesheet" href="${ctx}/static/wechat/base2c31e8.css" />
<link rel="stylesheet" href="${ctx}/static/wechat/css.css" />
<link rel="stylesheet" href="${ctx}/static/jquery/css/jquery-ui.custom.min.css" />
<script type="text/javascript" src="${ctx}/static/jquery/js/jquery-ui.custom.min.js"></script>
<style type="text/css">
.frm_input_box{border:none !important;}
.frm_input_box .frm_input{margin:0;padding: 0;height: auto;width: 400px;}
.frm_controls{text-align:left;}
.widget-title1{font-size:18px;line-height: 18px;}
.btn-primary, .btn-primary:focus{padding:0 22px}
.menu_content_tips{text-align:left;}
</style>
</head>

<body>
	<div id="mask" class="mask"></div>
	<!-- 手机菜单预览 -->
	<div id="mobileDiv" class="mobile_preview" style="display: none;">
		<div class="mobile_preview_hd">
			<strong class="nickname">讯天运营服务</strong>
		</div>
		<div class="mobile_preview_bd">
			<ul class="show_list" id="viewShow"></ul>
		</div>
		<div class="mobile_preview_ft">
			<ul id="viewList" class="pre_menu_list grid_line"></ul>
		</div>
		<a id="viewClose" class="mobile_preview_closed _btn _btn_default"
			href="javascript:void(0);">退出预览</a>
	</div>
	<!-- 说明 -->
	<div class="page-header">
		<h1>
			说明 <small><i class="ace-icon fa fa-angle-double-right"></i> 自定义菜单可以创建最多3个一级菜单，每个一级菜单可以创建最多5个二级菜单。编辑中的菜单不会马上被用户看到，可以放心调试。 </small>
		</h1>
	</div>
    <!-- 菜单管理 -->
	<div class="row wechat">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-sm-6" style="width: 40%;margin-bottom: 50px;">
					<div class="widget-box">
						<div class="widget-header widget-header">
							<h4 class="widget-title">菜单管理</h4>
							<div class="widget-toolbar">
								<div class="addMenu pull-right action-buttons" data-pid="0" name="一级菜单">
									<label> 
										<a data-original-title="添加一级菜单" href="javascript:void(0)" class="tooltip-info" data-rel="tooltip" data-placement="left">
											<i class="ace-icon fa fa-plus bigger-130"></i>
										</a>
									</label>
								</div>
								<label>&nbsp;&nbsp;</label> 
								<label>
									<input type="checkbox" class="ace ace-switch ace-switch-6" id="id-check-horizontal"> 
									<span class="lbl middle" id="sorts" data-rel="tooltip" data-placement="right" data-original-title="排序"></span>
								</label>
							</div>
						</div>
						<div id="sortables">
							<ul class="move-ul li-count1">
								<c:forEach items="${model }" var="obj" varStatus="v">
									<li id="${ v.index+1}" data-id="${obj.id }">
										<div class="dd-handle firstmenu" name="一级菜单">
											<span class="menu1">${obj.menuName }</span>
											<input type="hidden" class="type1" id="type1" value="${obj.triggerType }">
											<input type="hidden" class="content1" id="content1" value="${obj.triggerContent }">
											<div class="addMenu pull-right action-buttons" data-pid="${obj.id }" dateid="0" name="二级菜单">
												<a data-original-title="添加二级菜单" href="javascript:void(0)" class="tooltip-info" id="tooltip" data-rel="tooltip" data-placement="left">
													<i class="ace-icon fa fa-plus bigger-130"></i>
												</a>
											</div>
										</div>
										<ul class="move-ul li-count2">
											<c:forEach items="${obj.children }" var="children">
												<li id="${children.id }" data-id="${children.id }">
													<div class=" dd-handle secondmenu" name="二级菜单">
														<span class="menu2">${children.menuName }</span>
														<input type="hidden" class="type2" id="type2" value="${children.triggerType }">
											            <input type="hidden" class="content2" id="content2" value="${children.triggerContent }">
														<div class="pull-right action-buttons" data-pid="${children.id }" dateid="1"></div>
													</div>
												</li>
											</c:forEach>
										</ul>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-sm-6" style="width: 60%">
					<div class="widget-box">
						<div class="widget-header widget-header-flat">
							<h4 class="widget-title1">
								<span id="title1"></span> 
								<span id="title2"></span> 
								<span id="title3" style="display: none;"> 
								    <a id="jsDelBt" class="btn_ctrl" href="javascript:void(0);" style="position: absolute;left: 95%;">删除</a> 
								    <a id="jsChangeBt" class="btn_ctrl" href="javascript:void(0);" style="position: absolute;left: 88%;">重命名</a> 
								    <a id="jsSaveBt" class="btn_ctrl" href="javascript:void(0);" style="position: absolute;left: 83%;">保存</a> 
								    <!-- <a id="jsReturnBt" class="btn_ctrl" href="javascript:history.back(-1)" style="position: absolute;left: 78%;">返回</a>  -->
								</span>
							</h4>
						</div>
						<div class="widget-body">
							<div class="widget-main">
							    <!-- 无自定义菜单时显示（你可以点击左侧菜单或添加一个新菜单，然后设置菜单内容） -->
								<div class="row action_content init default " id="context">
									<p class="action_tips">你可以点击左侧菜单或添加一个新菜单，然后设置菜单内容</p>
									<br /> <br /> <br /> <br />
								</div>
								<!-- 有二级菜单，点击一级菜单显示（添加二级菜单） -->
								<div class="row action_content init default " id="context0" style="display: none;">
									<p class="action_tips"></p>
									<a class="initialCreate" id="initialCreate" href="javascript:void(0);" style="display: inline-block;">
										<i class="icon_menu_action add"></i><strong>添加二级菜单</strong> 
									</a> <br />
									<br />
								</div>
								<!-- 编辑二级菜单时显示（发送信息、跳转到网页） -->
								<div class="row action_content init default " id="context1" style="display: none;">
									<p>请设置<span></span>的内容</p>
									<br /> <br /> 
									<a class="initialCreate" id="sendMsg" href="javascript:void(0);" style="display: inline-block;">
										<i class='icon_menu_action add'></i> 
										<strong>发送信息</strong> 
									</a>
									<a class="initialCreate" id="goPage" href="javascript:void(0);" style="display: inline-block;">
										<i class="icon_menu_action add"></i> <strong>跳转到网页</strong> 
									</a> 
									<br />
									<br />
								</div>
								<!-- 无二级菜单，编辑一级菜单时显示（添加二级菜单、发送信息、跳转到网页） -->
								<div class="row action_content init default " id="context2" style="display: none;">
									<p>
										请设置<span></span>的内容
									</p>
									<br /> <br /> 
									<a class="initialCreate" id="initialCreate" href="javascript:void(0);" style="display: inline-block;">
										<i class="icon_menu_action add"></i><strong>添加二级菜单</strong> 
									</a> 
									<a class="initialCreate" id="sendMsg" href="javascript:void(0);" style="display: inline-block;"> 
										<i class="icon_menu_action add"></i> <strong>发送信息</strong> 
									</a>
									<a class="initialCreate" id="goPage" href="javascript:void(0);" style="display: inline-block;"> 
										<i class="icon_menu_action add"></i> <strong>跳转到网页</strong> 
									</a> 
									<br />
									<br />
								</div>
								<!-- 二级菜单发送跳转的页面 -->
								<div class="row action_content init default " id="context3" style="display: none;">
									<div class="menu_content url jsMain" id="url" style="display: block;">
										<form action="" id="urlForm" onsubmit="return false;">
											<div class="frm_control_group">
												<label for="" class="frm_label">关键词</label>
												<div class="frm_controls">
													<span class="frm_input_box"> 
														<input type="hidden" id="triggerType_click" value="click">
														<!-- <input type="text" class="frm_input" id="triggerContent_click" name="triggerContent">  -->
														<select type="text" placeholder="响应类型" id="triggerContent_click" name="triggerContent"  class="col-xs-10 col-sm-8">
															<option value="">请选择关键词</option>
															<c:forEach items="${weiXinKeywordLst }" var="weiXinKeyword">
																<option value="${weiXinKeyword.keyword }">${weiXinKeyword.keyword }</option>
															</c:forEach>
														</select>
													</span>
												</div>
												<div style="margin-top: 15px;margin-left: 10px;">
													<p class="menu_content_tips tips_global">用户可以选择关键词列表中已存在的关键词进行设置,如果不满足条件可进入关键词列表中重新添加配置.
													<%-- <a style="text-decoration: underline;color: #669fc7;" href="${ctx }/weiXin/weiXinKeyword/preview">关键词列表</a> --%>
													</p>
												</div>
											</div>
										</form>
									</div>
								</div>
								<!-- 二级菜单跳转到网页 -->
								<div class="row action_content init default " id="context4" style="display: none;">
									<div class="menu_content url jsMain" id="url" style="display: block;">
										<form action="" id="urlForm" onsubmit="return false;">
											<p class="menu_content_tips tips_global">订阅者点击该子菜单会跳到以下链接</p>
											<div class="frm_control_group">
												<label for="" class="frm_label">页面地址</label>
												<div class="frm_controls">
													<span class="frm_input_box"> 
														<input type="hidden" id="triggerType_view" value="view">
														<input type="text" class="frm_input" id="triggerContent_view" name="triggerContent">
													</span>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- PAGE CONTENT ENDS -->
			<div class="row">
				<div class="tool_bar tc js_totaltool_bar">
					<a id="pubBt" class="_btn _btn_primary" href="javascript:void(0);">保存并发布</a>
					<a id="viewBt" class="_btn _btn_default" href="javascript:void(0);">预览</a>
				</div>
			</div>
			<div style="position:relative; left:0px; width:940px; height:45px; top: 0px;margin-top: 20px;line-height:20px">
				<span style="color: red">注意:&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<span style="color: #939393; font-size: 12px;"> 只有“发布”后自定义菜单才能生效！<br>公众平台规定，菜单发布24小时后自动生效。如果想提早看到效果，可以重新关注该公众。 </span>
			</div>
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	<!-- 弹出对话框 -->
	<div id="dialog-confirm" class="hide">
		<div style="display: none;" id="confirmtitle">
			<div class='widget-header'>
				<h4 class='smaller'>
					<i class='ace-icon fa fa-exclamation-triangle red'></i>温馨提示
				</h4>
			</div>
		</div>
		<div class="space-6"></div>
		<p class="bigger-110 bolder center grey">
			<i class="ace-icon fa fa-hand-o-right blue bigger-120"></i>
			<span id="dialog-confirm-context"></span>
		</p>
	</div>
	<!-- #dialog-confirm -->
	<div id="dialog-msg" class="hide">
		<div style="display: none;" id="messagetitle">
			<div class='widget-header'>
				<h4 class='smaller'>
					<i class='ace-icon fa fa-exclamation-triangle red'></i>温馨提示
				</h4>
			</div>
		</div>
		<p id="p1" class="text-muted"></p>
		<p id="p2">
			<input id="menuName" type="text" class="js_menu_name form-control" placeholder="菜单名称" name="menuName" />
		</p>
		<p class="js_titleEorTips" style="color: red;">字数超过上限</p>
		<p class="js_titlenoTips" style="color: red;">请输入菜单名称</p>
		<p id="p3" class="text-muted"></p>
	</div>
	<!-- #dialog-msg -->
	<script src="${ctx }/static/jquery/js/spin.js"></script>
	<script src="${ctx }/static/jquery/js/jquery.spin.js"></script>
	<script src="${ctx }/static/wechat/js/menu.js"></script>
</body>
</html>
