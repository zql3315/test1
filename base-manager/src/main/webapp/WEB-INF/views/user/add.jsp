<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加用户</title>
</head>
<body>
    <div class="row">
        <div class="col-xs-12">
            <h3 class="header smaller lighter blue">
                <i class="ace-icon fa fa-pencil-square-o"></i>
                添加用户
            </h3>
            <form role="form" class="form-horizontal" method="POST" id="form">
                <div class="form-info form-info1 form-info-striped">
                
                        <div class="form-info-row">
                            <div class="form-info-name">登陆账号  <span style='color:red'>*</span></div>
        
                            <div class="form-info-value">
                                <input type="text"  placeholder="登陆账号" name="loginname" value="${model.loginname}" class="col-xs-10 col-sm-5">
                            </div>
                        </div>
                        <div class="form-info-row">
                            <div class="form-info-name"> 用户名称  <span style='color:red'>*</span></div>
        
                            <div class="form-info-value">
                                <input type="text"  placeholder="用户名称" name="name" value="${model.name}" class="col-xs-10 col-sm-5">
                            </div>
                        </div>
                        <div class="form-info-row">
                            <div class="form-info-name"> 用户头像 </div>
                            <div class="form-info-value  col-sm-5">
                                <div>
                                    <input placeholder="LOGO" id="file" onchange="ajaxFileUpload()" class="picfile" name="file" style="display: none;" type="file">
                                    <input id="logo" name="headpic" type="hidden">
                                </div>
                            </div>
                        </div>
                        <div class="form-info-row">
                            <div class="form-info-name"> 用户密码  <span style='color:red'>*</span></div>
        
                            <div class="form-info-value">
                                <input type="password" id="userPassword"  placeholder="用户密码" name="password" class="col-xs-10 col-sm-5">
                            </div>
                        </div>
                        <div class="form-info-row">
                            <div class="form-info-name"> 密码确认  <span style='color:red'>*</span></div>
        
                            <div class="form-info-value">
                                <input type="password"  placeholder="密码确认" name="confirmpassword"  class="col-xs-10 col-sm-5">
                            </div>
                        </div>
                        
                        <div class="form-info-row">
                            <div class="form-info-name"> 联系方式 </div>
        
                            <div class="form-info-value">
                                <input type="text"  placeholder="联系方式" name="telephone" class="col-xs-10 col-sm-5">
                            </div>
                        </div>
                        <div class="form-info-row">
                            <div class="form-info-name"> 邮件  </div>
        
                            <div class="form-info-value">
                                <input type="text"  placeholder="邮件" name="email" value="${model.email}" class="col-xs-10 col-sm-5">
                            </div>
                        </div>
                        <div class="form-info-row">
                            <div class="form-info-name"> 角色  <span style='color:red'>*</span></div>
        
                            <div class="form-info-value">
                                <sys:selectRole name="role" value="">
                                    
                                </sys:selectRole>
                            </div> 
                        </div>
                        <div class="form-info-row" style="display:none" id="preview">
                        <div class="form-info-name"  > 图片预览 </div>
                            <div id="animationlist" class="form-info-value">
                                 <div class="div" >
                                      <div class="div1">
                                         <img src="" id="animation" height="200" width="200" />
                                      </div>
                                  </div>
                                  <button type="button" class="btn  btn-purple btn-minier " id="deleteimg">
                                                                                                             删除
                                  </button>
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

    <script src="${ctx}/static/ace/js/ace-elements.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
    <script src="${ctx}/static/jquery/js/ajaxfileupload.js" type="text/javascript"></script>
    <script src="${ctx}/static/ace/js/fileupload.js" type="text/javascript"></script>
    
    <script type="text/javascript">
        jQuery.validator.addMethod("confirmpassword", function(value, element) {
            var oldpassword = $("#userPassword").val();
            if(oldpassword == value ){
        	   return true;
            }else{
            	return false;
            }
        }, "确认密码和原先密码不一样");
        $(document).ready(function(){
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
            
            $("#form").validate({
                ignore: "",
                rules:{
                        loginname:{required: true,lettleNumberValidator:true}, 
                        name:{required: true}, 
                        password:{required: true}, 
                        confirmpassword:{required: true,confirmpassword:true}, 
                        email:{email:true},
                        telephone:{telephoneValidator: true},
                        roleArr:{required:true}
                },
                messages:{
                        loginname:{required: "请输入账号",lettleNumberValidator:"请输入正确的字母或者数字.必须以字母开头，4-20位"}, 
                        name:{required: "请输入 用户名称"}, 
                        password:{required: "请输入 用户密码"}, 
                        confirmpassword:{required: "请输入确认户密码"}, 
                        email:{email:"请输入正确格式的电子邮件"},
                        telephone:{telephoneValidator: "请输入正确的手机号码或座机号码！"},
                        roleArr:{required:"请勾选角色"}
                },
                submitHandler:function(form){
                    var formData = $(form).serializeArray();
                    if ($.isArray(formData)) {
                        var d={};
                        d.roles=[];
                        var temp="";
                        $.each(formData, function(i, item) {
                            if (item.name && item.value) {
                                if(item.name == "role"){
                                    var roleUser={};
                                    roleUser.role={};
                                    roleUser.role.id=item.value;
                                    
                                    d.roles.push(roleUser);
                                    temp = temp + item.value + ",";
                                }else{
                                    d[item.name] = item.value;  
                                }
                                
                            }
                        });
                        
                        
                        $.ajax({
                            url : "${ctx}/user/addUser",
                            type : "POST",
                            async : false,
                            dataType : "json",
                            contentType : "application/json;charset=UTF-8",
                            data : JSON.stringify(d)
                        }).done(function(data) {
                            if(data.code == 0001){
                                alert("账号已存在，请重新输入！");
                                return;
                            }
                            showSuccess(function(){
                                openPage("${ctx}/user/preview");
                                backBreadcrumb();
                            });
                        }).fail(function(data) {
                            alert("添加失败");
                        });
                    }
                }
            });
        });
        
        function ajaxFileUpload() {
            if($("#file").val()  == ""){
                alert("请选择正确类型的一个文件");
                return ;
            }
            $.ajaxFileUpload({
                url: "${ctx }/fileUpload/image?folderName=user",
                secureuri: false,
                type : 'POST',
                fileElementId: "file",
                data:{whitelist_norms:"200*200"},
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
        }
        
        $(".picfile").ace_file_input("reset_input");
        
        $(document).on("click", "#deleteimg", function() {
            $("#preview").hide();
            $("#logo").val("");
        });

        function addlist() {
            var logo =imagePath+$("#logo").attr("value");
            $("#animation").attr("src",logo);
        }
        
        $("input[type='checkbox']").change(function(){
            if($(this).is(":checked")){
                $("#roleArr").val($("input[name='role']:checked"));
            }else{
                $("#roleArr").val("");
            }
        });
    
        cancelBtn("${ctx}/user");
    </script>
    <script src="${ctx}/static/bootstrap/js/common.js"></script>
</body>
</html>