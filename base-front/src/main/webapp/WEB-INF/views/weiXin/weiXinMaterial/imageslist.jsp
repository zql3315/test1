<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">

<title>微信公众平台-素材管理</title>
<link onerror="wx_loaderror(this)" rel="stylesheet" type="text/css" href="${ctx}/static/wechat/layout_head2880f5.css">
<link onerror="wx_loaderror(this)" rel="stylesheet" type="text/css" href="${ctx}/static/wechat/base2c31e8.css">
<link onerror="wx_loaderror(this)" rel="stylesheet" type="text/css" href="${ctx}/static/wechat/lib2968da.css">
<link onerror="wx_loaderror(this)" rel="stylesheet" href="${ctx}/static/wechat/media_list2a4e1d.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/wechat/media2b357f.css">
<link rel="stylesheet" href="${ctx}/static/fontawesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="${ctx}/static/jquery/css/jquery-ui.min.css"/>
<link rel="stylesheet" href="${ctx}/static/datatable/css/jquery.dataTables.css"/> 
<link rel="stylesheet" href="${ctx}/static/datatable/css/dataTables.bootstrap.css"/>
<link rel="stylesheet" href="${ctx}/static/datatable/css/jquery.dataTables_themeroller.css"/>
<link rel="stylesheet" href="${ctx}/static/datetimepicker/css/bootstrap-datetimepicker.min.css"/>
<link rel="stylesheet" href="${ctx}/static/chosen/css/chosen.css"/>
<link rel="stylesheet" href="${ctx}/static/ace/css/ace-fonts.css"/>
<style type="text/css">
li {
	list-style-type: none;
}

.btn1 {
	display: inline-block;
	overflow: visible;
	padding: 0 22px;
	height: 30px;
	line-height: 30px;
	vertical-align: middle;
	text-align: center;
	text-decoration: none;
	-webkit-border-radius: 3px;
	font-size: 13px;
	border-width: 1px;
	border-style: solid;
	cursor: pointer;
}

a:hover {
	text-decoration: none; /*鼠标放上去有下划线*/
}

.wxmImg {
	max-width: 100%;
	display: block;
	width: 100px;
	height: 100px;
}

.frm_checkbox_span {
	margin-left: 10px;
}

.js_imgitem {
	float: left;
	margin-left: 10px;
	margin-top: 10px;
	border: 1px solid #e7e7eb;
	width: 160px;
	height: 190px;
	text-align: center;
}

.imgstyle {
	width: 130px;
	height: 130px;
	margin-top: 10px;
}

.file {
	position: relative;
	margin: 0;
	color: #e97220;
	box-sizing: border-box;
}

.file .textstyle {
	display: inline-block;
	padding: 5px 10px;
	background: #fff;
	color: #e60012;
	font-weight: 700;
	border: 1px solid #e9e9e9;
	font-size: 14px;
}

.file input {
	position: absolute;
	right: 0;
	top: 0;
	opacity: 0;
	width: 100%;
}
</style>
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
</head>
<body class="zh_CN">
	<div id="body" class="body page_media_list page_appmsg_list">
		<div id="js_container_box" class="container_box cell_layout side_l">
			<div class="col_main" id="imagesDiv">
				<div class="col_main">
					<div class="main_hd">
						<h2>素材管理</h2>
						<div class="title_tab" id="topTab">
							<ul class="tab_navs title_tab" data-index="0">
								<li data-index="0" class="tab_nav first js_top" data-id="media10">
									<a href="javascript:openimagetext();">图文消息</a></li>
								<li data-index="1" class="tab_nav  js_top selected" data-id="media2">
									<a href="javascript:openimage();">图片</a></li>
								<!-- <li data-index="2" class="tab_nav  js_top" data-id="media3"><a
									href="javascript:openvoice();">语音</a></li>
								<li data-index="3" class="tab_nav  js_top" data-id="media15"><a
									href="javascript:openvideo();">视频</a></li> -->
							</ul>
						</div>
					</div>
					<div class="img_pick_panel">
						<div class="inner_container_box side_r cell_layout">
							<div class="inner_main">
								<div class="bd">
									<div class="media_list">
										<div class="global_mod float_layout">
											<div class="global_extra">
												<div class="upload_box align_right r">
													<div class="file">
														<span class="textstyle" id="choseImg">本地上传</span>
														<input type="file"  placeholder="点击上传" id="file" name="file" onchange="ajaxFileUpload();"/>
													</div>
												</div>
												<div class="mini_tips weak_text icon_after img_water_tips r">
													大小不超过5M<span id="js_water">，已开启图片水印</span> <i
														id="js_water_tips" class="icon_msg_mini ask"></i>
												</div>
											</div>
										</div>
										<div class="oper_group group">
											<!-- 全选 -->
											<div class="check_all" style="float: left;width: 8%;">
												<span class="frm_checkbox_span"> <input id="ck_all" name="ck_" type="checkbox" onclick="checkAll('ck');">
													<span>&nbsp;全选</span>
												</span>
											</div>
											<div class="content_group" style="float: left;">
												<!-- <a id="js_batchmove" class="btn btn_default btn_disabled oper_ele l js_popover" href="javascript:;">移动分组</a>  -->
												<a id="js_batchdel" class="btn btn_default btn_disabled oper_ele l js_popover" href="javascript:delCheckImages();">删除</a>
											</div>
										</div>
										<div id="materialMessageLst">
										    <c:if test="${materialMessageLst == null || weiXinMaterialCount <= 0 }">
												<div class="empty_tips" id="js_empty">
													<p>暂无图片</p>
												</div>
											</c:if>
											<c:if test="${materialMessageLst != null && weiXinMaterialCount > 0 }">
												<!-- 图文循环 -->
												<c:forEach var="materialMessage" items="${materialMessageLst }"
													varStatus="s">
													<div class="js_imgitem">
														<div class="img_item_bd">
															<img src="../b2r-build/${materialMessage.picurl}"
																class="imgstyle"><br> <span
																class="check_content"> <input type="checkbox"
																name="ck" value="${materialMessage.id}"
																onclick="checkSingle('ck');"> <span
																class="lbl_content">${materialMessage.title}</span> <input
																type="hidden" placeholder="图片地址" name="picture" value=""
																class="col-xs-10 col-sm-5"> </span>
														</div>
														<div class="msg_card_ft">
															<a class="js_del" href="javascript:void(0)" onclick="delImage(this)" data-tooltip="删除" materialid="${materialMessage.id}">删除</a>
														</div>
													</div>
												</c:forEach>
												<input type="hidden" placeholder="素材" name="weiXinKeywordMaterials" value="" class="col-xs-10 col-sm-5">
											</c:if>
										</div>
										<!-- <div class="pagination_wrp pageNavigator" id="js_pagebar">
											<div class="pagination" id="wxPagebar_1463725339003">
												<span class="page_nav_area"> <a
													href="javascript:void(0);" class="btn page_first"
													style="display: none;"></a> <a href="javascript:void(0);"
													class="btn page_prev" style="display: none;"><i
														class="arrow"></i> </a> <span class="page_num"> <label>1</label>
														<span class="num_gap">/</span> <label>14</label> </span> <a
													href="javascript:void(0);" class="btn page_next"><i
														class="arrow"></i> </a> <a href="javascript:void(0);"
													class="btn page_last" style="display: none;"></a> </span> <span
													class="goto_area"> <input type="text"> <a
													href="javascript:void(0);" class="btn page_go">跳转</a> </span>
											</div>
										</div> -->
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
<script src="${ctx}/static/jquery/js/ajaxfileupload.js" type="text/javascript"></script>
<script src="${ctx}/static/wechat/js/material.js" type="text/javascript"></script>
</body>
</html>