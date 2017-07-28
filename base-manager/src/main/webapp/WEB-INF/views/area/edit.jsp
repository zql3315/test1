<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx}/static/ztree/css/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<title>d</title>
</head>
<body>
    <div class="row">
        <div class="col-xs-12">
            <div class="btn-group">
                <button class="btn btn-sm btn-success btn-white btn-round" id="addBtn">
                    <i class="ace-icon fa fa-plus bigger-110 green"></i>
                                                             添加
                </button>
                <button class="btn btn-sm btn-danger btn-white btn-round" id="delBtn">
                    <i class="ace-icon fa fa-trash-o bigger-110 red"></i>
                                                             删除
                </button>
            </div>
            <div class="content-bottom"></div>
            <form role="form" class="form-horizontal" method="POST" id="form">
                <div class="form-info form-info1 form-info-striped">
                    <div class="form-info-row">
                        <div class="form-info-name">名称</div>
    
                        <div class="form-info-value">
                            <input type="hidden"  name="id" value="${model.id}" >
                            <input type="text"  placeholder="北京" name="name" value="${model.name}" class="col-xs-10 col-sm-5">
                            <input type="hidden" name="level" value="${model.level}" >
                        </div>
                    </div>
                    <div class="form-info-row">
                        <div class="form-info-name">编码 </div>
    
                        <div class="form-info-value">
                            <input type="text"  placeholder="10000" name="code" value="${model.code}" class="col-xs-10 col-sm-5">
                        </div>
                    </div>
                    <div class="form-info-row">
						<div class="form-info-name">权重 </div>
	
						<div class="form-info-value">
							<input type="text"  placeholder="" name="weight" value="${model.weight }" class="col-xs-10 col-sm-5">
						</div>
					</div>
                    <div class="form-info-row">
                        <div class="form-info-name"> 父级  </div>
                        <div class="form-info-value">
                            <input type="text"  placeholder="父级" id="parent" value="${model.parent.name }"  class="col-xs-10 col-sm-5" readonly>
                            <span class="input-group-btn">
                                <button class="btn btn-sm btn-default" type="button" id="areaBtn" onclick="showMenu(); return false;">
                                    <i class="ace-icon fa fa-table bigger-110"></i>
                                </button>
                            </span>
                            <div class="widget-box widget-color-blue2" id="menuContent" style="height:350px;overflow:scroll;position:absolute; display: none;background-color: #DCEBF7;z-index: 9">
                                <div class="widget-body" style="margin-top:0; width:365px;">
                                    <div id="dictionaryTree" class="ztree"></div>
                                </div>
                            </div>
                            <input type="hidden" id="h_parent" value="${model.parent.id }">
                        </div>
                    </div>
                </div>
                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <button class="btn btn-info" type="submit">
                            <i class="ace-icon fa fa-check smaller-90"></i>提交
                        </button>
    
                        &nbsp; &nbsp; &nbsp;
                        <button class="btn" type="reset">
                            <i class="ace-icon fa fa-undo smaller-90"></i>取消
                        </button>
                    </div>
                </div>      
            </form>
        </div>
    </div>
    <script src="${ctx}/static/ztree/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <script src="${ctx}/static/ztree/jquery.ztree.excheck-3.5.min.js" type="text/javascript"></script>
    <!-- validate -->
    <script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
    <script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
    <script type="text/javascript">
       var setting = {
            view: {
                dblClickExpand: false
            },
            async: {
                enable: true,
                url:"${ctx}/area/ztree",
                type:"post",
                otherParam: ["open", false],
                autoParam: ["id"]
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                beforeClick: beforeClick,
                onClick: onClick
            }
        };
        function beforeClick(treeId, treeNode) {
           var check = (treeNode && treeNode.type<2);
           if (!check) alert("不能选择元素节点作为父节点...");
           return check;
        }
        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("dictionaryTree"),
            nodes = zTree.getSelectedNodes(),
            v = "";
            ids="";
            nodes.sort(function compare(a,b){return a.id-b.id;});
            for (var i=0, l=nodes.length; i<l; i++) {
                v += nodes[i].name + ",";
                ids+=nodes[i].id + ",";
            }
            if (v.length > 0 ) v = v.substring(0, v.length-1);
            var visibleElement = $("#parent");
            visibleElement.attr("value", v);
            
            if (ids.length > 0 ) ids = ids.substring(0, ids.length-1);
            var hiddenElement=$("#h_parent");
            hiddenElement.val(ids);
            
            $("#menuContent").fadeOut("fast");
        }
        
        function showMenu() {
            var parent = $("#parent");
            var position=$("#parent").position();
            $("#menuContent").css({left:position.left + "px", top:position.top+parent.outerHeight() + "px"}).slideDown("fast");

            $("body").bind("mousedown", onBodyDown);
        }
        
        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }
        function onBodyDown(event) {
            if (!(event.target.id == "areaBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
                hideMenu();
            }
        }
        $(document).ready(function(){
            
            $(".chosen-select").chosen({allow_single_deselect:true,disable_search_threshold: 10}); 
            //resize the chosen on window resize
            $(window).on("resize.chosen", function() {
                //var w = $(".chosen-select").parent().width();
                $(".chosen-select").next().css({"width":"41.66666667%","float":"left"});
            }).trigger("resize.chosen");
            
            $.fn.zTree.init($("#dictionaryTree"), setting);
            
            $('#addBtn').on('click',function(){
                $('#areaEdit').load('${ctx}/area/toAdd?id=${model.id}',function(){
                     //加一个遮罩在次
                });
            });
            
            $('#delBtn').on('click',function(e){
                e.preventDefault();
                var r={};
                r.id="${model.id}";
                window.confirm("是否删除?", function(_dialog){
                    $.ajax({
                        url : '${ctx}/area/delete',
                        type : 'POST',
                        async : false,
                        dataType : 'json',
                        contentType : 'application/json;charset=UTF-8',
                        data : JSON.stringify(r)
                    }).done(function(data) {
                        $( _dialog ).dialog( "close" );
                        if(data.result){
                            alert('删除成功');
                            $("#areaName").text(areaTree.getNodeByTId(areaTree.getSelectedNodes()[0].parentTId).name);
                            areaTree.removeNode(areaTree.getSelectedNodes()[0]);
                            $('#areaEdit').load('${ctx}/area/toEdit/'+$("#h_parent").val(),function(){
                                 //加一个遮罩在次
                            });
                        }else{
                            alert('删除失败:'+data.msg);
                        }
                    }).fail(function(data) {
                        alert('删除失败');
                    });
                });
                
            });
            
            $("#form").validate({
                rules:{
                        name:{required: true}
                        , 
                        code:{required: true}
                },
                messages:{
                        name:{required: "请输入名称"}
                        , 
                        code:{required: "请输入编码"}
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
                        var pid = $("#h_parent").val();
                        if(pid){
                            d.parent={};
                            d.parent.id=pid;
                        }
                        $.ajax({
                            url : "${ctx}/area/edit",
                            type : "POST",
                            async : false,
                            dataType : "json",
                            contentType : "application/json;charset=UTF-8",
                            data : JSON.stringify(d)
                        }).done(function(data) {
                        	if (data.result){
                        		alert('编辑成功');
                        		$("#areaName").text(d.name);
                        		areaTree.getSelectedNodes()[0].name=d.name;
                        		areaTree.updateNode(areaTree.getSelectedNodes()[0]);
                            } else {
                                alert(data.msg);
                            }
                        }).fail(function(data) {
                            alert(data.error);
                        });
                    }
                }
            });
        });
    
    </script>   
</body>
</html>