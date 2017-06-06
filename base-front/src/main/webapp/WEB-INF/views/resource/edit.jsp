<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑项目</title>
<link href="${ctx}/static/ztree/css/zTreeStyle.css" rel="stylesheet" type="text/css"/>
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
			<form role="form" class="form-horizontal" method="POST" id="projectForm">
				<div class="form-info form-info1  form-info-striped">
						<div class="form-info-row">
							<div class="form-info-name"> 资源名称  </div>
							<div class="form-info-value">
							    <input type="hidden" name="id" value="${model.id}">
								<input type="text"  placeholder="资源名称" name="name" value="${model.name}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 资源简称  </div>
							<div class="form-info-value">
								<input type="text"  placeholder="资源简称" name="sn" value="${model.sn}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 资源类型  </div>
							<div class="form-info-value">
								<sys:selectItem datakey="dd_resource_type_key" name="type" value="${model.type}" _class="chosen-select"></sys:selectItem>
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 资源URL  </div>
							<div class="form-info-value">
								<input type="text"  placeholder="资源URL" name="url" value="${model.url}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 资源图标  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="资源图标" name="icons" value="${model.icons}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 资源优先级  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="资源优先级" name="priority" value="${model.priority}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name"> 描述  </div>
		
							<div class="form-info-value">
								<input type="text"  placeholder="描述" name="description" value="${model.description}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
                            <div class="form-info-name"> 父级标识  </div>
        
                            <div class="form-info-value">
                                <input type="text"  placeholder="父级" id="parent"  value="${model.parent.name }" class="col-xs-10 col-sm-5" readonly>
                                <span class="input-group-btn">
                                    <button class="btn btn-sm btn-default" type="button" id="resourceBtn" onclick="return false;">
                                        <i class="ace-icon fa fa-table bigger-110"></i>
                                    </button>
                                </span>
                                <div class="widget-box widget-color-blue2" id="menuContent" style="height:180px;overflow:scroll;position:absolute; display: none;background-color: #DCEBF7;z-index: 9">
                                    <div class="widget-body" style="margin-top:0; width:365px;">
                                        <div id="resourceTree" class="ztree"></div>
                                    </div>
                                </div>
                                <input type="hidden" id="h_parent" value="${model.parent.id }">
                            </div>
                        </div>
					
				</div>
				
				<div class="clearfix form-actions">
					<div class="col-md-offset-5">
						<button class="btn btn-info" type="submit">
							<i class="ace-icon fa fa-check smaller-90"></i>保存
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<script src="${ctx}/static/ztree/jquery.ztree.core-3.5.js" type="text/javascript"></script>
	<script src="${ctx}/static/ztree/jquery.ztree.excheck-3.5.min.js" type="text/javascript"></script>
	<!-- validate -->
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<script src="${ctx}/static/bootstrap/js/common.js"></script>
	<script type="text/javascript">
	jQuery(function($){
	   var setting = {
            view: {
                dblClickExpand: false
            },
            async: {
                enable: true,
                url:'${ctx}/resource/ztree',
                type:'post',
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
            var check = (treeNode && treeNode.type!='3');
            if (!check) alert("不能选择元素节点作为父节点...");
            check = (treeNode.id!="${model.id}");
            if (!check) alert("不能选择自己作为父节点...");
            return check;
        }
        
        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("resourceTree"),
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

        $('#resourceBtn').on('click',function(e){
            var parent = $("#parent");
            var position=$("#parent").position();
            $("#menuContent").css({left:position.left + "px", top:position.top+parent.outerHeight() + "px"}).slideDown("fast");

            $("body").bind("mousedown", onBodyDown);
            
            var zTree = $.fn.zTree.getZTreeObj("resourceTree")
            nodes = zTree.getNodes();
            
            var ids = $("#h_parent").val();
            var result = ids.split(",");
            for(var i=0;i<result.length;i++){
                var temp = result[i];
                var nodes = zTree.getNodeByParam("id", temp, null);
                zTree.selectNode(nodes, true, true, true);
            }
        });
        
        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }
        function onBodyDown(event) {
            if (!(event.target.id == "resourceBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
                hideMenu();
            }
        }
        $.fn.zTree.init($("#resourceTree"), setting);
    });
    $(document).ready(function(){
			
			$('.chosen-select').chosen({
				allow_single_deselect:true,
				disable_search_threshold: 10,
				default_single_text:'请选择',
				default_multiple_text:'请选择'}); 
			//resize the chosen on window resize
			$(window).on('resize.chosen', function() {
				//var w = $('.chosen-select').parent().width();
				$('.chosen-select').next().css({'width':'41.66666667%','float':'left'});
			}).trigger('resize.chosen');
			
			$('#addBtn').on('click',function(){
				$('#resourceEdit').load('${ctx}/resource/toAdd?id=${model.id}',function(){
                     //加一个遮罩在次
                });
            });
			
			$('#delBtn').on('click',function(e){
				var rl=[];
                var r={};
                r.id="${model.id}";
                rl.push(r);
                e.preventDefault();
                window.confirm("是否删除?", function(_dialog){
                    $.ajax({
                        url : '${ctx}/resource/deleteByID',
                        type : 'POST',
                        async : false,
                        dataType : 'json',
                        contentType : 'application/json;charset=UTF-8',
                        data : JSON.stringify(rl)
                    }).done(function(data) {
                        $( _dialog ).dialog( "close" );
                        if(data.result){
                        	alert('删除成功');
                        	$("#channelName").text(channelTree.getNodeByTId(channelTree.getSelectedNodes()[0].parentTId).name);
                        	channelTree.removeNode(channelTree.getSelectedNodes()[0]);
                            $('#resourceEdit').load('${ctx}/resource/toEdit/'+$("#h_parent").val(),function(){
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
            
			
			$('#projectForm').validate({
				rules:{
						name:{required: true},
						sn:{required: true,lettleLimitValidator:true},
						type:{required: true},
						url:{required: true},
						priority:{required: true,digits:true},
						desciption:{required: true}
				},
				messages:{
						name:{required: '请输入 资源名称'},
						sn:{required: '请输入 资源简称',lettleLimitValidator:"请输入4-20位的大小写字母"},
						type:{required: '请输入 资源类型'},
						url:{required: '请输入 资源URL'},
						priority:{required: '请输入 资源优先级',digits:"去输入合法的整数"},
						desciption:{required: '请输入 描述'}
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
							url : '${ctx}/resource/edit',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							if(data && data.result){
                                alert('编辑成功');
                                $("#channelName").text(d.name);
                                channelTree.getSelectedNodes()[0].name=d.name;
                                channelTree.updateNode(channelTree.getSelectedNodes()[0]);
                            }else{
                                alert(data.msg);
                            }
						}).fail(function(data) {
							alert('编辑失败');
						});
					}
				}
			});
		});
	</script>
	
</body>
</html>