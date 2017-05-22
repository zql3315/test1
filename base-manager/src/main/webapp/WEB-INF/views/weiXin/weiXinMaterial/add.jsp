<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信图文消息</title>
<style type="text/css">
	.content_cover {
	    width: 100px;
	    height: 100px;
	    margin-bottom: 5px;
	}
</style>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>
				添加
			</h3>
		</div>
		<form role="form" class="form-horizontal" method="POST" id="form">
			<div class="form-info form-info-striped">
			        <!-- 标题 -->
					<div class="form-info-row">
						<div class="form-info-name"> 标题 </div>
						<div class="form-info-value">
							<input type="text"  placeholder="自动回复消息标题" name="title" value="${model.title}" class="col-xs-10 col-sm-5">
							<input type="hidden"  placeholder="创建时间" name="createtime" value="" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<!-- 作者 -->
					<div class="form-info-row">
						<div class="form-info-name"> 作者 </div>
						<div class="form-info-value">
							<input type="text"  placeholder="作者" name="author" value="${model.author}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<!-- 描述信息 -->
					<div class="form-info-row">
						<div class="form-info-name"> 描述 </div>
						<div class="form-info-value">
						    <textarea rows="3" placeholder="描述信息" name="description" class="col-xs-10 col-sm-5">${model.description}</textarea>
						</div>
					</div>
					<!-- 链接地址 -->
					<div class="form-info-row">
						<div class="form-info-name"> 链接 </div>
						<div class="form-info-value">
							<input type="text"  placeholder="跳转链接地址" name="linkurl" value="${model.linkurl}" class="col-xs-10 col-sm-5">
						</div>
					</div>
					<!-- 上传图片 -->
					<div class="form-info-row">
						<div class="form-info-name"> 图片  </div>
						<div class="form-info-value  col-sm-5"  >
						    <img id="imgbg" src="${ctx}/static/image/img_bg.gif" class="content_cover">
							<input type="file"  placeholder="图片" id="file" name="file"  style="display: none;" >
							<button type="button" class="btn  btn-purple btn-minier" onclick="ajaxFileUpload()"><i class="ace-icon fa fa-cloud-upload bigger-200"></i> 上传</button>
							<input type="hidden"  id="picurl" name="picurl" >
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
	<!-- validate -->
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<script src="${ctx}/static/jquery/js/ajaxfileupload.js" type="text/javascript"></script>
	<script type="text/javascript">
        function ajaxFileUpload()
        {
        	if($("#file").val() == "")
        	{
        		alert("请选择一个文件");
        		return ;
        	}
            
            $.ajaxFileUpload({
			    url: "${ctx}/upload/fileUpload?folderName=keywordImage",
			    secureuri: false,
			    type : "POST",
			    fileElementId: "file",
			    dataType: "json",
			    beforeSend: function() {
				<%--$("#loading").show();--%>
			    },
			    complete: function() {
				<%--$("#loading").hide();--%>
			    },
			    success: function(data) {
			     	if(data && data.result == "SUCCESS"){
			     		alert("上传成功");
			     		$(".ace-file-name").attr("data-title",data.fileUrl);
			     		$("#picurl").val(data.fileUrl);
			     		$("#imgbg").attr("src","../b2r-build/" + data.fileUrl);
			     	}else{
			     		alert("上传失败");
			     	}
			    },
			    error: function(data, status, e) {
			      
			    }
			});
            return false;
        }
    </script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			
			var whitelist_ext = ["jpeg", "jpg", "png", "gif", "bmp"];
			var whitelist_mime = ["image/jpg", "image/jpeg", "image/png", "image/gif", "image/bmp"];
			$("#file").ace_file_input("update_settings",{
				"btn_choose":"选择文件",
				"btn_change":"重新选择",
				"no_file":"请选择一个背景图片",
				"allowExt": whitelist_ext,
				"allowMime": whitelist_mime
				});
			$("#file").show();
			$("#file").ace_file_input("reset_input");
			
			//resize the chosen on window resize
			$(window).on('resize.chosen', function() {
				//var w = $('.chosen-select').parent().width();
				$('.chosen-select').next().css({'width':368});
			}).trigger('resize.chosen');
			
			$('#form').validate({
				rules:{
						author:{required: true}, 
						description:{required: true}, 
						linkurl:{required: true}, 
						picurl:{required: true}, 
						title:{required: true}
				},
				messages:{
						author:{required: '请输入 作者'}, 
						description:{required: '请输入 描述信息'}, 
						linkurl:{required: '请输入 跳转链接地址'}, 
						picurl:{required: '请输入 图片链接地址'}, 
						title:{required: '请输入 自动回复消息标题'}
				},
				submitHandler:function(form){
					var formData = $(form).serializeArray();
					if ($.isArray(formData)) {
						var d={};
						$.each(formData, function(i, item) {
							if (item.name && item.value) {
								d[item.name] = item.value;
							}
							
							if (item.name == "createtime") {
								d[item.name] = curentDate();
							}
						});
						console.log("d:" + JSON.stringify(d));
						
						$.ajax({
							url : '${ctx}/weiXin/weiXinMaterial/add',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							//alert('添加成功');
							$('div').find('.page-content').load('${ctx}/weiXin/weiXinMaterial/preview',function(){
							 	//加一个遮罩在次
							});
						}).fail(function(data) {
							alert('添加失败');
						});
					}
				}
			});
		});
	
	</script>	
	<script type="text/javascript">
		function curentDate()
	    { 
	        var now = new Date();
	       
	        var year = now.getFullYear();       //年
	        var month = now.getMonth() + 1;     //月
	        var day = now.getDate();            //日
	       
	        var clock = year + "年";
	       
	        if(month < 10)
	            clock += "0";
	       
	        clock += month + "月";
	       
	        if(day < 10)
	            clock += "0";
	           
	        clock += day + "日";
	       
	        return(clock); 
	    } 
	</script>
	<script type="text/javascript">
	    $('#cancel').on('click',function(){
			$('div').find('.page-content').load('${ctx}/weiXin/weiXinMaterial/preview',function(){
				 //加一个遮罩在次
			});
		});
	</script>
</body>
</html>