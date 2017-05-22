<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<div id="queryNewsLst" style="display: none;"></div>
<c:if test="${materialMessageLst == null || weiXinMaterialCount == 0 }">
	<div class="empty_tips" id="js_empty">
		<p>暂无图文消息</p>
	</div>
</c:if>
<div class="empty_tips" id="js_search_empty" style="display: none;">
		没有搜索结果，请重新输入关键字或者查看<a href="javascript:searchMaterial('all');" id="reload">全部图文消息</a>
</div>
