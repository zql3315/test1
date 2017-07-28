<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<%@ taglib prefix="slm" uri="http://www.b2r.com.cn/slm" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入信息</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>
				导入信息
			</h3>
		</div>
		<form role="form" class="col-xs-12 form-horizontal" method="POST" id="form">
			<div class="form-info form-info-striped">
				<div class="form-info-name"> 导入信息 <span style='color:red'>*</span></div>
				<div class="form-info-value">
					<div class="form-info-value  col-xs-5" >
						<input type="file"  placeholder="" id="file" name="file" onchange="changeFileName('file')">
						<button type="button" class="btn  btn-purple btn-sm" onclick="ajaxFileUpload()"><i class="ace-icon fa fa-cloud-upload smaller-40"></i> 导入</button>
						&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn  ace-icon btn-sm" id="cancel"><i class="ace-icon fa fa-undo smaller-40"></i> 取消</button>
						<input id="excelPath" name="excelPath" type="hidden">
					</div>
				</div>
			</div>
					
		</form>
	</div>
	<!-- validate -->
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<script src="${ctx}/static/jquery/js/ajaxfileupload.js" type="text/javascript"></script>
	<script type="text/javascript">
	function ajaxFileUpload() {
        	if($("#file").val()  == ""){
        		alert("请选择一个excel文件");
        		return ;
        	}
            $.ajaxFileUpload({
			    url: "${ctx}/upload/fileUploads?folderName=importExcel",
			    secureuri: false,
			    type : 'POST',
			    fileElementId: "file",
			    dataType: 'json',
			    beforeSend: function() {

			    },
			    complete: function() {

			    },
			    success: function(data) {
			     	if(data && data.result == "SUCCESS"){
			     		$(".ace-file-name").attr("data-title",data.fileUrl);
			     		$('#excelPath').val(data.fileUrl);
			     		importExcel();
			     	}else{
			     		alert("上传失败");
			     	}
			    },
			    error: function(data, status, e) {
			        alert(e);
			    }
			});
            return false;
        }
        
        var whitelist_ext = ["xls","xlsx"];
			$('input[name="file"]').ace_file_input("update_settings",{
				"btn_choose":"选择文件",
				"btn_change":"重新选择",
				"no_file":"请选择一个excel文件",
				"allowExt": whitelist_ext
				});
			$('input[name="file"]').show();
			$('input[name="file"]').ace_file_input("reset_input");
			
			function importExcel()
			{
				 $.ajax({
		            //提交数据的类型 POST GET
		            type:"POST",
		            //提交的网址
		            url:'${ctx}/demo/importExcel?excelPath=' + $('#excelPath').val(),
		            //成功返回之后调用的函数             
		            success:function(data){
		          		alert('导入成功！'); 
		            },
		            //调用出错执行的函数
		            error: function(){
		                //请求出错处理
		                alert('导入失败！');
		            }         
		        }); 
			}
			
		$(document).ready(function(){
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			//resize the chosen on window resize
			$(window).on('resize.chosen', function() {
				//var w = $('.chosen-select').parent().width();
				$('.chosen-select').next().css({'width':310});
			}).trigger('resize.chosen');
		});
	
	</script>
	<script type="text/javascript">
		cancelBtn('${ctx}/demo');
	</script>	
</body>
</html>