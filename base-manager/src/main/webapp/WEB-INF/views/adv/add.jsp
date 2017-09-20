<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>广告位增加</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i> 广告位增加
			</h3>
			<form role="form" class="form-horizontal" method="POST" id="form">
				<div class="form-info form-info1 form-info-striped">
					<div class="form-info-row">
						<div class="form-info-name">广告位名称</div>
						<div class="form-info-value">
							<input type="text" placeholder="广告位名称" name="advname" value="${model.advname}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">广告位地区</div>
						<div class="form-info-value">
							<sys:selectArea level="1" name="citycode" _class="col-xs-10 col-sm-5 chosen-select"></sys:selectArea>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">广告位编号</div>
						<div class="form-info-value">
							<select name="advno" class=" chosen-select col-xs-10 col-sm-5">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
								<option value="10">10</option>
							</select>
							<%-- <input type="text" placeholder="广告位id" name="advno"
								value="${model.advno}" class="col-xs-10 col-sm-5"> --%>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">终端类型</div>
						<div class="form-info-value">
							<select name="terminaltype" id="terminaltype" class=" chosen-select col-sm-5 changeAdvSpecification">
								<option value="1">手机</option>
								<option value="2">PC</option>
							</select>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">广告位位置</div>
						<div class="form-info-value">
							<sys:selectItem datakey="dd_adv_advposition" id="advposition" name="advposition" _class="col-xs-10 col-sm-5 chosen-select changeAdvSpecification"></sys:selectItem>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">广告位类型</div>
						<div class="form-info-value">
							<sys:selectItem datakey="dd_adv_type" name="advtype" id="advtype" _class="col-xs-10 col-sm-5 chosen-select changeAdvSpecification"></sys:selectItem>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">广告位规格</div>
						<div class="form-info-value">
							<select class="col-xs-10 col-sm-5 chosen-select" id="id_advspecification" name="advspecification"></select>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">广告位图片</div>
						<div class="form-info-value  col-sm-5">
							<div>
								<input placeholder="LOGO" id="file" class="picfile" name="file" style="display: none;" type="file"> <input id="logo" name="imgpath" type="hidden">
							</div>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">广告位地址</div>
						<div class="form-info-value">
							<input type="text" placeholder="广告位地址" name="advurl" value="${model.advurl}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">开始时间</div>
						<div class="form-info-value">
							<div class="input-group date form_datetime col-xs-10 col-sm-5 inputstyle" data-date-format="yyyy-MM-dd">
								<input type="text" placeholder="开始时间" name="starttime" value="" class="form-control" readonly> <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
							</div>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">结束时间</div>
						<div class="form-info-value">
							<div class="input-group date form_datetime col-xs-10 col-sm-5 inputstyle" data-date-format="yyyy-MM-dd">
								<input type="text" placeholder="结束时间" name="endtime" value="" class="form-control" readonly> <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
							</div>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">是否显示</div>
						<div class="form-info-value">
							<input id="isdisplay" type="checkbox" checked="checked" name="isdisplay" class="ace ace-switch ace-switch-4 btn-flat" /> <span class="lbl"></span>
						</div>
					</div>
					<div class="form-info-row">
						<div class="form-info-name">内容描述</div>
						<div class="form-info-value">
							<input type="text" placeholder="内容描述" name="description" value="${model.description}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<div class="form-info-row" style="display:none" id="preview">
						<div class="form-info-name">图片预览</div>
						<div id="animationlist" class="form-info-value">
							<div class="div">
								<div class="div1">
									<img src="" id="animation" height="200" width="200" />
								</div>
							</div>
							<button type="button" class="btn  btn-purple btn-minier " id="deleteimg">删除</button>
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
		
	
	(function(){
		$(".form-info-value").on("change","#file",function(){

	        if($("#file").val()  == ""){
	            alert("请选择一个文件");
	            return ;
	        }
	        var size = $("#id_advspecification").val();
	        var sizearr = size.split("*");
	        var width = sizearr[0];
	        var height = sizearr[1];
	        
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
		})
		
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

			$('#form').validate({
				rules:{
						advname:{required: true}, 
						advno:{required: true},
						advurl:{url:true}
				},
				messages:{
						advname:{required: '请输入 广告位名称'}, 
						advno:{required: '请输入 广告位编号'}, 
						advurl:{url:"必须输入正确格式的网址"}
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
						d.createtime= new Date().pattern("yyyy-MM-dd HH:mm:ss");
						d["isdisplay"]=$("#isdisplay").is(':checked')? 1: 0;
						$.ajax({
							url : '${ctx}/adv/add',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							if (data.result){
								showSuccess(function(){
									openPage('${ctx}/adv/preview');
									backBreadcrumb();
								});
							} else {
								alert(data.msg);
							}
								
						}).fail(function(data) {
							alert('添加失败');
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
				var id_advspecification = $("#id_advspecification").val();
				
				
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