<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加关键字</title>
<style type="text/css">
.frm_checkbox_span {margin-left: 10px;}
.js_imgitem {float: left;margin-left: 10px;margin-top: 10px;border: 1px solid #e7e7eb;width: 169px;height: 200px;text-align: center;position:relative;}
.content_cover {width: 150px;height: 150px;margin-top:20px;}
.add-style{line-height:200px}
.add-style a{display:inline-block;width: 169px;}
#shade{background:rgba(0,0,0,0.5);position:fixed;left:0;top:0;width:100%;height:100%;z-index:1;display:none}
#showdialog{position:fixed;top:50%;left:50%;transform:translate(-50%,-50%);z-index:2;width: 60%;height: 80%;}
#showdialog .dialog_hd{background-color: #f4f5f9;position: relative;line-height: 52px;height: 52px;width:100%;box-sizing: border-box;padding:0 20px}
#showdialog .dialog_hd h3{margin:0;line-height: 52px;}
#showdialog .dialog_hd .pop_closed{position: absolute;top:18%;margin-top: -8px;right: 20px;}
#showdialog .dialog_bd{background: #fff}
#showdialog .dialog_bd .btn_primary,#showdialog .dialog_ft .btn{background-color: #44b549 !important;border-color: #44b549 !important;color: #fff !important;cursor: pointer;    padding: 0 22px;display: inline-block;    height: 30px;vertical-align: middle;text-align: center;border-radius: 3px;}
#showdialog .dialog_bd .btn_primary i{background:url(../base-manager/static/image/plus.png) no-repeat;width: 14px;height: 14px;vertical-align: middle;display: inline-block;line-height: 100px;overflow: hidden;}
#showdialog .dialog_media_container{position: relative;}
#showdialog .sub_title_bar.in_dialog{padding: 0 20px;border-bottom: 1px solid #e7e7eb;line-height: 55px;}
#showdialog .search_bar{float: left;line-height: 1.6;margin-top: 13px;}
#showdialog .frm_input_box.search.with_del{padding-right: 60px;display: inline-block;position: relative;height: 30px;line-height: 30px;vertical-align: middle;width: 278px;font-size: 14px;padding: 0 10px;border: 1px solid #e7e7eb;}
#showdialog .frm_input_box #msgSearchInput{border:none;padding:0;background: none;}
#showdialog .frm_input_box.search .frm_input_append{right: 0;width: 30px;text-align: center;position: absolute;top:0;}
#showdialog .icon16_common.search_gray{background:url(../base-manager/static/image/search_img.png) no-repeat center center;width: 16px;height: 16px;display: inline-block;    vertical-align:-webkit-baseline-middle;margin-top: -0.8em;}
#showdialog .tr {text-align: right;}
#showdialog .dialog_ft {padding: 16px 0;background-color: #f4f5f9;text-align: center;border-top: 1px solid transparent;}
#showdialog .dialog_ft .btn {margin-left: .3em;margin-right: .3em;}
#showdialog .btn.btn_input {min-width: 104px;}
#showdialog .btn button {display: block;height: 100%;background-color: transparent;border: 0;margin: 0 6px;outline: 0;overflow: visible;}
#showdialog .dialog_ft .btn_default {background-color: #fff !important;background-image: linear-gradient(to bottom,#fff 0,#fff 100%) !important;border-color: #e7e7eb !important;color: #222;border-width: 1px;}
#showdialog .dialog_ft .btn_default button {color: #222;}
.appmsg_media_dialog .dialog_media_inner{height: 453px;position: relative;}
.media_dialog.appmsg_list{position: relative;padding: 28px 140px;height: 345px;margin: 0;overflow-y: auto;text-align: justify;}
.appmsg_col {display: inline-block;vertical-align: top;width: 32%;text-align: left;font-size: 14px;letter-spacing: normal;}
.media_dialog .appmsg_col {width:30%;margin-right: 3%;}
.appmsg {position: relative;overflow: hidden;margin-bottom: 20px;border: 1px solid #e7e7eb;background-color: #fff;color: #666;}
.appmsg_content {position: relative;padding:0}
.appmsg_date {font-weight: 400;font-style: normal;}
.undone_tips {color: #ffbe00;padding-top: 3px;}
.cover_appmsg_item {position: relative;margin: 12px 14px;}
.appmsg_title {font-weight: 400;font-style: normal;font-size: 14px;line-height: 1.6;max-height: 3.2;overflow: hidden;word-wrap: break-word;word-break: break-all;color: #222;}
.appmsg_cover_desc {color: #666;max-height: 45px;overflow: hidden;}
.multi .appmsg_item {border-top: 1px solid #e7e7eb;}
.appmsg_item {position: relative;padding: 12px 14px;}
.appmsg_thumb_wrp {height:140px;width: 80%;margin-left: 10%;overflow: hidden;background-repeat: no-repeat;background-position: center center;background-size: cover;}
.edit_mask {font-size: 0;position: absolute;top: 0;left: 0;right: 0;bottom: 0;background: rgba(0,0,0,0.6)!important;filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#99000000',endcolorstr = '#99000000');color: #fff;z-index: 1;text-align: center;padding: 14px;}
.edit_mask_content {display: inline-block;vertical-align: middle;font-size: 14px;}
.vm_box {display: inline-block;height: 100%;vertical-align: middle;}
.edit_mask.appmsg_mask {display: none;}
.appmsg:hover .edit_mask.appmsg_mask{display:block;cursor: pointer;}
.appmsg .icon_card_selected {position: absolute;top: 50%;left: 50%;margin-top: -23px;margin-left: -23px;line-height: 999em;overflow: hidden;z-index: 1;}
.icon_card_selected {background: url(../base-manager/static/image/check.png) no-repeat; width: 46px;height: 46px;vertical-align: middle;display: inline-block;}
.selected .edit_mask.appmsg_mask{display:block;cursor: pointer;}
.close-style{position:absolute;top:1%;right:4%;cursor: pointer;}
.close-style img{width: 10px;}
</style>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>添加关键字
			</h3>
			<form role="form" class="form-horizontal" method="POST" id="wechat">
				<div class="form-info form-info-striped">
					<div class="form-info-row">
						<div class="form-info-name">关键字</div>
						<div class="form-info-value">
							<input type="text" placeholder="keyword" name="keyword" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">自动回复类型</div>
						<div class="form-info-value">
							<select type="text" placeholder="msgtype" name="msgtype" id="msgtype" class="col-xs-10 col-sm-5" onchange="changeMsgType(this);">
								<option value="text">文本</option>
								<option value="news">图文</option>
							</select>
							<input type="hidden" placeholder="创建时间" name="createtime" value="" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<!-- 文字 -->
					<div class="form-info-row description">
						<div class="form-info-name">自动回复内容</div>
						<div class="form-info-value">
							<textarea rows="3" placeholder="描述信息" name="description" class="col-xs-10 col-sm-5"></textarea>
						</div>
					</div>
					<!-- 图文消息 -->
					<div class="form-info-row weiXinMaterial">
						<div class="form-info-name">图文消息</div>
						<div class="form-info-value">
							<div id="weiXinMaterialLst">
								<input type="hidden" placeholder="素材" name="weiXinKeywordMaterials" value="" class="col-xs-10 col-sm-5">
								<!-- 展示的图文消息 -->
								<div class="news_show" style="display: none;"></div>
							    <!-- +开始 -->
								<div class="js_imgitem add-style">
									<a href="javascript:void(0);" class="add-dialog"><img src="${ctx }/static/image/jia.png"></a>
								</div>
								 <!-- +结束 -->
								<!------------------------------------------------ new dialog 选择素材对话框开始 ------------------------------------>
								<div id="shade"></div>
								<div id="showdialog" style="display:none;">
									<div class="dialog">
										<div class="dialog_hd">
											<h3>选择素材</h3>
											<a href="javascript:void(0);" class="closed pop_closed"><img src="${ctx }/static/image/close.png" /></a>
										</div>
										<div class="dialog_bd">
											<div class="dialog_media_container appmsg_media_dialog">
												<!-- <div class="sub_title_bar in_dialog">
													<div class="search_bar">
														<span class="frm_input_box search with_del"> 
															<a class="del_btn dn" href="javascript:" id="searchCloseBt"><i class="icon_search_del"></i>&nbsp;</a>
															<a id="msgSearchBtn" href="javascript:" class="frm_input_append"><i class="icon16_common search_gray"></i>&nbsp;</a>
															<input id="msgSearchInput" type="text" placeholder="标题" value="" class="frm_input"> 
														</span>
													</div>
													<div class="appmsg_create tr">
														<a class="btn btn_primary btn_add" target="_blank" href="/cgi-bin/appmsg?t=media/appmsg_edit&amp;action=edit&amp;type=10&amp;isMul=1&amp;lang=zh_CN&amp;token=1171054307"><i class="icon14_common add_white"></i>新建图文消息 </a>
													</div>
												</div> -->
												<div class="dialog_media_inner">
													<div class="appmsg_list media_dialog">
														<!----------- 素材列表开始 ----->
														<c:if test="${weiXinMaterialLst != null && weiXinMaterialCount > 0 }">
															<c:forEach var="weiXinMaterial" items="${weiXinMaterialLst }" varStatus="s">
																<div class="appmsg_col">
																	<div class="inner">
																		<div id="appmsg_material">
																			<div class="appmsg">
																				<div class="appmsg_content">
																					<div class="appmsg_item">
																						<input type="hidden" id="news_id" value="${weiXinMaterial.id}">
																						<input type="hidden" id="news_title" value="${weiXinMaterial.title}">
																						<input type="hidden" id="news_picurl" value="${weiXinMaterial.picurl}">
																						<h4 class="appmsg_title">${weiXinMaterial.title}</h4>
																						<div class="appmsg_thumb_wrp" style="background-image:url('../b2r-build/${weiXinMaterial.picurl}')"></div>
																						<p>${weiXinMaterial.digest}</p>
																					</div>
																				</div>
																				<div class="edit_mask appmsg_mask">
																					<i class="icon_card_selected">已选择</i>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</c:forEach>
														</c:if>
														<c:if test="${weiXinMaterialLst == null || weiXinMaterialCount == 0 }">
															<div class="empty_tips" id="js_empty">
																<p>暂无图文消息</p>
															</div>
														</c:if>
														<!------- 素材列表结束 ------->
													</div>
												</div>
											</div>
										</div>
										<div class="dialog_ft">
											<span class="btn btn_primary btn_input js_btn_p">
												<button type="button" class="js_btn">确定</button>
											</span>
											<span class="btn btn_default btn_input js_btn_p">
												<button type="button" class="js_btn">取消</button>
											</span>
										</div>
									</div>
								</div>
								<!----------------------------------------------------------------- 选择素材对话框结束----------------------------------------->
							</div>
						</div>
					</div>
					<!-- 按钮 -->
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
	<script>
	$(function(){
	    //弹出对话框
		$(".add-dialog").click(function(){
			$("#showdialog").show();
			$("#shade").show();
		});
		
		//关闭对话框
		$(".closed").click(function(){
			$("#showdialog").hide();
			$("#shade").hide();
		});
		
		var weiXinKeywordMaterials = [];
		
		//选中信息
		$(".appmsg").click(function(){
			$(this).toggleClass("selected");
			
			//获取选中值
			if($(this).hasClass("selected")){
				var news_id = $(this).find("#news_id").val();
				var news_title = $(this).find("#news_title").val();
				var news_picurl = $(this).find("#news_picurl").val();
				
				var weiXinKeywordMaterial = {};
		        weiXinKeywordMaterial.weiXinMaterial = {};
			    weiXinKeywordMaterial.weiXinMaterial.id = news_id;
			    weiXinKeywordMaterial.weiXinMaterial.title = news_title;
			    weiXinKeywordMaterial.weiXinMaterial.picurl = news_picurl;
			    weiXinKeywordMaterials.push(weiXinKeywordMaterial);
			}
		});
		
		//确认
		$(".btn_primary").click(function(){
			$("#showdialog").hide();
			$("#shade").hide();
			$(".news_show").show();
			$(".news_show").empty();
			
			for(var i = 0; i < weiXinKeywordMaterials.length; i++){
				 var weiXinKeywordMaterial = weiXinKeywordMaterials[i];
			     var news = weiXinKeywordMaterial.weiXinMaterial;
				 var news_id = news.id;
				 var news_title = news.title;
				 var news_picurl = news.picurl;
				 $(".news_show").append("<div class='js_imgitem'><input type='hidden' name='materialmsgid' value="+news_id+"><div class='img_item_bd'><img src='../b2r-build/"+news_picurl+"' class='content_cover'><br>" 
					+"<span class='check_content'>"
					+"<span class='lbl_content'>"+news_title+"</span>"
					+"<input type='hidden' placeholder='图片地址' name='picture' value='' class='col-xs-10 col-sm-5'>"
					+"</span></div><div class='closeed close-style'><img src='"+ctx+"/static/image/close.png'/></div></div>");
			
			$(".news_show").find(".closeed").click(function(){
				$(this).parent().remove();
			});
		
			}
		});
		
		//取消
		$(".btn_default").click(function(){
			$("#showdialog").hide();
			$("#shade").hide();
			$(".news_show").hide();
		});
		
		
	});
	</script>
		<script type="text/javascript">
		$(document).ready(function(){
			
			$(".chosen-select").chosen({
				allow_single_deselect:true,
				disable_search_threshold: 10}); 
			
			//resize the chosen on window resize
			$(window).on("resize.chosen", function() {
				//var w = $(".chosen-select").parent().width();
				$(".chosen-select").next().css({"width":"41.66666667%","float":"left"});
			}).trigger("resize.chosen");
			
			//隐藏图文消息
			$(".weiXinMaterial").hide();
			
			$(".form_datetime").datetimepicker({
				format : "yyyy-mm-dd",
				autoclose : true,
				todayBtn : true,
				todayHighlight : true,
				pickerReferer : "input",
				language : "zh-CN",
				minView : 2,
				maxView : 3,
				cleanBtn: true,
				pickerPosition : "bottom-left"
		    });

			$(".form_datetime input").val(new Date().pattern("yyyy-MM-dd HH:mm:ss"));
			
			$("#wechat").validate({
				rules:{
						keyword:{required: true}, 
						description:{required: true}
				},
				messages:{
						keyword:{required: "请输入 keyword"}, 
						description:{required: "请输入 description"}
				},
				submitHandler:function(form){
					var formData = $(form).serializeArray();
					//是否选中素材
					var weiXinKeywordMaterials = getSelectedValue();
					
					if ($.isArray(formData)) {
						var d={};
						$.each(formData, function(i, item) {
						    if (item.name) {
						        if(item.name != "ck" && item.name != "ck_") {
						            if (item.name == "createtime") {
										d[item.name] = curentDate();
									}
									else if(item.name == "weiXinKeywordMaterials") {
										if (weiXinKeywordMaterials.length > 0) {
											d[item.name] = getSelectedValue();
										}
									} 
									else{
									    if(item.name != "materialmsgid"){
											d[item.name] = item.value;
									    }
									}
						        } 
							} 
						});
						
						console.log(JSON.stringify(d));
						
						//新增文本
						if (weiXinKeywordMaterials.length == 0) {
							$.ajax({
								url : "${ctx}/weiXin/weiXinKeyword/add",
								type : "POST",
								async : false,
								dataType : "json",
								contentType : "application/json;charset=UTF-8",
								data : JSON.stringify(d)
							}).done(function(data) {
							    if(data.result == false){
							    	alert(data.msg);
							    }
							    else{
							    	$('div').find('.page-content').load('${ctx}/weiXin/weiXinKeyword/preview',function(){
										 //加一个遮罩在次
									});
							    }
							}).fail(function(data) {
								alert("添加失败");
							});
						}
						else{
						    //新增图文消息
							$.ajax({
								url : "${ctx}/weiXin/weiXinKeyword/addKeyword",
								type : "POST",
								async : false,
								dataType : "json",
								contentType : "application/json;charset=UTF-8",
								data : JSON.stringify(d)
							}).done(function(data) {
								if(data.result == false){
							    	alert(data.msg);
							    }
							    else{
							    	$('div').find('.page-content').load('${ctx}/weiXin/weiXinKeyword/preview',function(){
										 //加一个遮罩在次
									});
							    }
							}).fail(function(data) {
								alert("添加失败");
							});
						}
						
					    
					}
				}
			});
		});
		
		//切换素材类型
		function changeMsgType(text)
		{
		    var msgType = $("#msgtype").val();
		    
		    if(msgType == "text")
		    {
				//消息文本
				$(".description").show();
			    //隐藏图文消息
				$(".weiXinMaterial").hide();
		    }
		    
		    if(msgType == "news")
		    {
			    //消息文本
				$(".description").hide();
			    //隐藏图文消息
				$(".weiXinMaterial").show();
		    }
		}
		
		//获取选中的图文消息
		function getSelectedValue()
		{
			var materialMsgs = document.getElementsByName("materialmsgid");
			var weiXinKeywordMaterials = [];
			
			for(var i = 0; i < materialMsgs.length; i++){
			    var weiXinKeywordMaterial = {};
		        weiXinKeywordMaterial.weiXinMaterial = {};
			    weiXinKeywordMaterial.weiXinMaterial.id = materialMsgs[i].value;
			    weiXinKeywordMaterials.push(weiXinKeywordMaterial);
			}
			
			console.log(weiXinKeywordMaterials);
			
			return weiXinKeywordMaterials;
		}
	</script>
	<script type="text/javascript">
		function curentDate()
	    { 
	        var now = new Date();
	        var year = now.getFullYear();       //年
	        var month = now.getMonth() + 1;     //月
	        var day = now.getDate();            //日
	       
	        var clock = year + "-";
	       
	        if(month < 10)
	            clock += "0";
	       
	        clock += month + "-";
	       
	        if(day < 10)
	            clock += "0";
	           
	        clock += day;
	       
	        return(clock); 
	    } 
	</script>
	<script type="text/javascript">
	    cancelBtn('${ctx}/weiXin/weiXinKeyword');
	</script>
</body>
</html>