<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys"%>
<%@ page import="cn.b2r.common.constant.ConstantParam"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<c:set var="imagePath" value="<%=cn.b2r.common.constant.ConstantParam.IMAGEPATH%>" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑广告位</title>
<style type="text/css">
.div1 img{
    max-width: 600px;
}
</style>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>编辑广告位
			</h3>

			<form role="form" class="form-horizontal" method="POST" id="projectForm">
				<div class="form-info  form-info1  form-info-striped">
					<input type="hidden" value="${model.id}" name="id">
					<div class="form-info-row">
						<div class="form-info-name">
							广告位名称 
						</div>

						<div class="form-info-value">
							<input type="text" placeholder="广告位名称" name="advname" value="${model.advname}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">地区</div>
						<div class="form-info-value">
							<sys:selectArea level="1" name="citycode" value="${model.citycode}" _class="col-xs-10 col-sm-5 chosen-select"></sys:selectArea>

						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">
							广告位编号
						</div>

						<div class="form-info-value">
							<select name="advno" id="advno" class="chosen-select col-xs-10 col-sm-5">
								<option value="1" <c:if test="${model.advno == 1}">selected="selected"</c:if>>1</option>
								<option value="2" <c:if test="${model.advno == 2}">selected="selected"</c:if>>2</option>
								<option value="3" <c:if test="${model.advno == 3}">selected="selected"</c:if>>3</option>
								<option value="4" <c:if test="${model.advno == 4}">selected="selected"</c:if>>4</option>
								<option value="5" <c:if test="${model.advno == 5}">selected="selected"</c:if>>5</option>
								<option value="6" <c:if test="${model.advno == 6}">selected="selected"</c:if>>6</option>
								<option value="7" <c:if test="${model.advno == 7}">selected="selected"</c:if>>7</option>
								<option value="8" <c:if test="${model.advno == 8}">selected="selected"</c:if>>8</option>
								<option value="9" <c:if test="${model.advno == 9}">selected="selected"</c:if>>9</option>
								<option value="10" <c:if test="${model.advno == 10}">selected="selected"</c:if>>10</option>
							</select>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">
							终端类型
						</div>

						<div class="form-info-value">
							<select name="terminaltype" id="terminaltype" class=" chosen-select col-xs-10 col-sm-5 changeAdvSpecification">
								<option value="1" <c:if test="${model.terminaltype == 1}">selected="selected"</c:if>>手机</option>
								<option value="2" <c:if test="${model.terminaltype == 2}">selected="selected"</c:if>>PC</option>
							</select>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">
							广告位位置 
						</div>

						<div class="form-info-value">
							<sys:selectItem datakey="dd_adv_advposition" value="${model.advposition}" id="advposition" name="advposition" _class="col-xs-10 col-sm-5 chosen-select changeAdvSpecification"></sys:selectItem>
						</div>
					</div>

					<div class="form-info-row">
						<div class="form-info-name">
							广告位类型
						</div>

						<div class="form-info-value">
							<sys:selectItem datakey="dd_adv_type" value="${model.advtype}" id="advtype" name="advtype" _class="col-xs-10 col-sm-5 chosen-select changeAdvSpecification"></sys:selectItem>
						</div>
					</div>

					<div class="form-info-row">
						<div class="form-info-name">
							广告位规格
						</div>

						<div class="form-info-value">
							<select class="col-xs-10 col-sm-5 chosen-select" id="id_advspecification" name="advspecification"></select>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">
							广告位图片
						</div>

						<div class="form-info-value  col-sm-5">
							<div>
								<input placeholder="LOGO" id="file" class="picfile" name="file" style="display: none;" type="file"> <input id="logo" name="imgpath" value="${model.imgpath}" type="hidden">
							</div>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">
							广告位地址 
						</div>

						<div class="form-info-value">
							<input type="text" placeholder="广告位地址" name="advurl" value="${model.advurl}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">
							开始时间
						</div>

						<div class="form-info-value">
							<div class="input-group date form_datetime col-xs-10 col-sm-5 inputstyle" data-date-format="yyyy-MM-dd">
								<fmt:formatDate value="${model.starttime}" pattern="yyyy-MM-dd" var="starttime" />
								<input type="text" placeholder="开始时间" name="starttime" value="${starttime}" class="form-control" readonly> <span class="input-group-addon"><span
									class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>
						</div>
					</div>

					<div class="form-info-row">
						<div class="form-info-name">
							结束时间
						</div>

						<div class="form-info-value">
							<div class="input-group date form_datetime col-xs-10 col-sm-5 inputstyle" data-date-format="yyyy-MM-dd">
								<fmt:formatDate value="${model.endtime}" pattern="yyyy-MM-dd" var="endtime" />
								<input type="text" placeholder="结束时间" name="endtime" value="${endtime}" class="form-control" readonly> <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">
							是否显示
						</div>

						<div class="form-info-value">
							<input id="isdisplay" type="checkbox" <c:if test="${model.isdisplay ==1 }"> checked </c:if> name="isdisplay" class="ace ace-switch ace-switch-4 btn-flat" /> <span class="lbl"></span>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">
							内容描述 
						</div>

						<div class="form-info-value">
							<input type="text" placeholder="内容描述" name="description" value="${model.description}" class="col-xs-10 col-sm-5">
						</div>
					</div>

					<div class="form-info-row" id="preview" <c:if test="${empty model.imgpath}"> style="display: none;" </c:if>>
						<div class="form-info-name">图片预览</div>
						<div id="animationlist" class="form-info-value">
							<div class="div">
								<div class="div1">
									<img src="${imagePath}/${model.imgpath}" id="animation" />
								</div>
							</div>
							<button type="button" class="btn  btn-purple btn-minier " onclick="deleteimg()" id="deleteimg">删除</button>
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
	<script src="${ctx}/static/jquery/js/ajaxfileupload.js" type="text/javascript"></script>
	<script type="text/javascript">
		(function (){
			$("#advno").val("${model.advno}");
			$("#terminaltype").val("${model.terminaltype}");
			
			$(".form-info-value").on("change","#file",function(){
		        if($("#file").val()  == ""){
		            alert("请选择一个文件");
		            return ;
		        }
		        var size = $("#id_advspecification").val();
		        $.ajaxFileUpload({
		            url: "${ctx}/fileUpload/image?folderName=adv&nosy=1",
		            secureuri: false,
		            type : 'POST',
		            fileElementId: "file",
		            data:{whitelist_norms:size},
		            dataType: 'json',
		            beforeSend: function() {
		
		            },
		            complete: function() {
		
		            },
		            success: function(data) {
		            	if(data && data.result){
		                    $("#preview").show();
		                    $(".ace-file-name").attr("data-title",data.fileUrl);
		                    $("#logo").val(data.fileUrl);
		                    addlist();
		                }else if(data){
		                    alert(data.msg);
		                }else{
		                    alert("上传失败");
		                }
		            },
		            error: function(data, status, e) {
		                alert(e);
		            }
		        });
		        return false;
			});
			
			$(".picfile").ace_file_input("reset_input");
				
			$(document).on("click", "#deleteimg", function() {
		        $("#preview").hide();
		        $("#logo").val("");
		    });
		
			function addlist() {
			    var logo =imagePath+$("#logo").attr("value");
			    $("#animation").attr("src",logo);
			}
			
			$(document).ready(function(){
				
				$('.chosen-select').chosen({allow_single_deselect:true,disable_search_threshold: 10}); 
				//resize the chosen on window resize
				$(window).on('resize.chosen', function() {
					//var w = $('.chosen-select').parent().width();
					$('.chosen-select').next().css({'width':'41.66666667%','float':'left'});
				}).trigger('resize.chosen');
				
				$('.form_datetime').datetimepicker({
					format : "yyyy-mm-dd",
					autoclose : true,
					todayBtn : true,
					todayHighlight : true,
					pickerReferer : 'input',
					language : 'zh-CN',
					minView : 2,
					maxView : 3,
					cleanBtn: true,
					pickerPosition : "bottom-left"
			    });
				
				var whitelist_ext = [ "jpeg", "jpg", "png", "gif", "bmp" ];
	            var whitelist_mime = [ "image/jpg", "image/jpeg", "image/png", "image/gif",
	                    "image/bmp" ];
	            $(".picfile").ace_file_input("update_settings", {
	                "btn_choose" : "选择文件",
	                "btn_change" : "重新选择",
	                "no_file" : "请选择一个LOGO图片",
	                //maxSize: 100000, // ~100 KB
	                "allowExt" : whitelist_ext,
	                "allowMime" : whitelist_mime
	            });
	            $(".picfile").show();
	            $(".picfile").ace_file_input("reset_input");
	            
	            
				
				$('#projectForm').validate({
					rules:{
						advname:{required: true}, 
						advno:{required: true},
						advurl:{required: true}
					},
					messages:{
						advname:{required: '请输入 广告位名称'}, 
						advno:{required: '请输入 广告位编号'},
						advurl:{required: '请输入 广告位地址'}
							 
					},
					submitHandler:function(form){
						var formData = $(form).serializeArray();
						if ($.isArray(formData)) {
							var d={};
							$.each(formData, function(i, item) {
								if (item.name && item.value) {
									d[item.name] = item.value;
								}
								d.modifytime= new Date().pattern("yyyy-MM-dd HH:mm:ss");
							});
							d["isdisplay"]=$("#isdisplay").is(':checked')? 1: 0;
							$.ajax({
								url : '${ctx}/adv/edit',
								type : 'POST',
								async : false,
								dataType : 'json',
								contentType : 'application/json;charset=UTF-8',
								data : JSON.stringify(d)
							}).done(function(data) {
								if(data && data.result){
                                    showSuccess(function(){
                                        openPage('${ctx}/adv/preview');
                                        backBreadcrumb();
                                    },"操作成功,您想要继续编辑么?");
                                }else{
                                    alert(data.msg);
                                }
							}).fail(function(data) {
								alert('编辑失败');
							});
						}
					}
				});
				
				//切换广告规格
				$('.changeAdvSpecification').on('change',function(){
					changeAdvSpecification();
				});
				
				//初始化广告规格
				function changeAdvSpecification(){
					var terminaltype = $("#terminaltype").val();
					var advposition = $("#advposition").val();
					var advtype = $("#advtype").val();
					
					$.ajax({
						url : basePath+'/dictionary/find',
						type : 'POST',
						async : false,
						data:"search_EQ_datakey=dd_adv_specification_"+terminaltype+"_"+advposition+"_"+advtype,
						dataType : 'json',
					}).done(function(data) {
						var obj = $("#id_advspecification");
						$("#id_advspecification").html("");
						$("#id_advspecification").chosen("destroy");
						
						if(data){
						    if(data.length == 0){
								$("<option value='0*0'>无广告位请更换</option>").appendTo("#id_advspecification");
						    }else{
						    	for(var i=0;i<data.length;i++ ){
									$("<option value='"+data[i].itemcode+"'>"+data[i].itemvalue+"</option>").appendTo("#id_advspecification");
								}
						    }
					    }
					    else{
					    	$("<option value='0*0'>无广告位请更换</option>").appendTo("#id_advspecification");
					    }
					    $("#id_advspecification").chosen({width:'41.66666667%'});
					}).fail(function(data) {
						alert('查询失败!');
					});
				};
				
				changeAdvSpecification();
			});	
		cancelBtn('${ctx}/adv');
		})();
	</script>


</body>
</html>