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
li {list-style-type:none;}

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

a:hover{
 text-decoration:none;  /*鼠标放上去有下划线*/
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

.file{position:relative;margin: 0;color:#e97220;   box-sizing: border-box;}
.file .textstyle{display: inline-block;padding: 5px 10px;background: #fff;color: #e60012;    font-weight: 700;border: 1px solid #e9e9e9;font-size: 14px;}
.file input{position: absolute;right:0;top:0;opacity: 0;width: 100%;}
</style>
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
</head>
<body class="zh_CN">
	<div id="body" class="body page_media_list page_appmsg_list">
		<div id="js_container_box" class="container_box cell_layout side_l">
			<div class="col_main" id="newsDiv">
				<div class="main_hd">
					<h2>素材管理</h2>
					<div class="title_tab" id="topTab">
						<ul class="tab_navs title_tab" data-index="0">
    						<li data-index="0" class="tab_nav first js_top selected" data-id="media10">
    							<a href="javascript:openimagetext();">图文消息</a>
    						</li>
    						<!-- <li data-index="1" class="tab_nav  js_top" data-id="media2">
    							<a href="javascript:openimage();">图片</a>
    						</li>
    						<li data-index="2" class="tab_nav  js_top" data-id="media3">
    							<a href="javascript:openvoice();">语音</a>
    						</li>
    						<li data-index="3" class="tab_nav  js_top" data-id="media15">
    							<a href="javascript:openvideo();">视频</a>
    						</li> -->
						</ul>
					</div>
				</div>
				<div class="main_bd" id="news_main">
					<!-- 查询图文消息(搜索) -->
					<div class="search_bar" id="searchDiv">
						<input type="text" id="search_LIKE_title" style="width:200px;font-size: 12px;" name="search_LIKE_title" placeholder="标题">
						<a href="javascript:searchNews();" class="frm_input_append jsSearchInputBt">搜索</a>
					</div>
					<!-- 动态图文消息开始 -->
					<div id="dynamic_info">
						<!-- 图文消息数量展示 -->
						<div class="sub_title_bar">
							<div class="info">
								<h3 id="page_title">
									<span id="query_tips">图文消息</span>(共<span id="js_count">${weiXinMaterialCount}</span>条)
								</h3>
								<div class="rank_style">
									<a href="javascript:;" class="btn_card_rank" id="js_cardview"
										alt="卡片式">卡片式</a> <a href="javascript:;"
										class="btn_list_rank current" id="js_listview" alt="列表式">列表式</a>
								</div>
							</div>
							<a target="_blank" class="btn1 btn_add btn_primary r btn_new"
								href="javascript:addMaterialMessage();">
								<i class="icon14_common add_white"></i>+新建图文消息
							</a>
						</div>
					    <!-- 图文消息列表 -->
					    <div id="newsLst">
							 <c:if test="${materialMessageLst != null && weiXinMaterialCount > 0 }">
								<div class="appmsg_list_v" id="js_list">
									<ul class="inner_list_v">
										<!-- 图文循环 -->
										<c:forEach var="weiXinMaterial" items="${materialMessageLst }">
											<li class="appmsg_item_v js_appmsgitem">
												<div class="inner">
													<div class="content">
														<img src="../b2r-build/${weiXinMaterial.picurl}" class="imgstyle">
														<div class="content_abstract">
															<p class="js_title">
																<a target="_blank" href="${weiXinMaterial.content_source_url}">${weiXinMaterial.title}</a>
															</p>
															<p class="js_title">
																<a target="_blank" href="javascript:void(0)">
																	${weiXinMaterial.author}
																</a>
															</p>
															<p class="js_title">
																<a target="_blank" href="javascript:void(0)">
																	${weiXinMaterial.digest}
																</a>
															</p>
														</div>
													</div>
													<div class="opr">
														<a href="javascript:void(0)" onclick="editMaterialMessage(this)" editValue="${weiXinMaterial.id}">编辑</a>
														<a href="javascript:void(0)" onclick="delMaterialMessage(this)" delValue="${weiXinMaterial.id}">删除</a>
														<input type="hidden" id="materialId" value="${weiXinMaterial.id}">
													</div>
													<div class="date">
														<p>${weiXinMaterial.failtime }</p>
													</div>
												</div>
											</li>
										</c:forEach>
									</ul>
								</div>
								<div class="empty_tips dn" id="js_empty">
									<p>暂无图文消息</p>
								</div>
							</c:if>
						</div>
						<!-- <div id="queryNewsLst" style="display: none;"></div> -->
						<c:if test="${materialMessageLst == null || weiXinMaterialCount == 0 }">
							<div class="empty_tips" id="js_empty">
								<p>暂无图文消息</p>
							</div>
						</c:if>
						<div class="empty_tips" id="js_search_empty" style="display: none;">
							没有搜索结果，请重新输入关键字或者查看<a href="javascript:searchMaterial('all');" id="reload">全部图文消息</a>
						</div>
					</div>
					<!-- 动态图文消息结束 -->
					<div id="query_dynamic_info" style="display: none;"></div>
					<!-- 图文消息分页 -->
					<div class="pagination" id="wxPagebar_1476340628586">
					    <span class="page_nav_area">
					        <a href="javascript:void(0);" class="btn page_first" style="display: none;"></a>
					        <a href="javascript:void(0);" class="btn page_prev" style="display: none;"><i class="arrow"></i></a>
					        
					            <span class="page_num">
					                <label>1</label>
					                <span class="num_gap">/</span>
					                <label>2</label>
					            </span>
					        
					        <a href="javascript:void(0);" class="btn page_next"><i class="arrow"></i></a>
					        <a href="javascript:void(0);" class="btn page_last" style="display: none;"></a>            
					    </span>
					    
					    <span class="goto_area">
					        <input type="text">
					        <a href="javascript:void(0);" class="btn page_go">跳转</a>
					    </span>
					    
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