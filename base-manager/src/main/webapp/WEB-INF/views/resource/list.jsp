<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx}/static/ztree/css/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<title>系统资源</title>
</head>
<body>
    <!-- /section:settings.box -->
    <div class="page-header">
        <h1>
                                    资源管理 <small> <i class="ace-icon fa fa-angle-double-right"></i> 资源查询 </small>
        </h1>
    </div>
    <!-- /.page-header -->
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <!-- #section:plugins/fuelux.treeview -->
            <div class="row">
                <div class="col-sm-6">
                   <div class="widget-box widget-color-green2">
                        <div class="widget-header">
                            <h4 class="widget-title lighter smaller">资源树</h4>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main " style="min-height: 600px;">
                                <div id="channelTree" class="ztree"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6">
                    <div class="widget-box widget-color-green2">
                        <div class="widget-header">
                            <h4 class="widget-title lighter smaller" id="channelName">栏目</h4>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main " style="min-height: 600px;" id="resourceEdit">
                                                                                        请选择左边的栏目进行编辑
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- PAGE CONTENT ENDS -->
        </div><!-- /.col -->
    </div><!-- /.row -->
    <script src="${ctx}/static/custom/b2r.js"></script>
    <script src="${ctx}/static/ztree/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="${ctx}/static/ztree/jquery.ztree.excheck-3.5.min.js" type="text/javascript"></script>
    <!-- inline scripts related to this page -->
        <script type="text/javascript">
        
<%--        jQuery(function($){--%>
           	var resourceSetting = {
                       check:{
                           enable: false,//设置 zTree 的节点上是否显示 checkbox / radio   默认值: false
                           chkStyle: "checkbox",
                           //Y 属性定义 checkbox 被勾选后的情况； //N 属性定义 checkbox 取消勾选后的情况；
                           //"p" 表示操作会影响父级节点；  //"s" 表示操作会影响子级节点。
                           chkboxType: { "Y": "ps", "N": "ps" }//勾选 checkbox 对于父子节点的关联关系  
                       },
                       view:{
                           selectedMulti:false//设置是否允许同时选中多个节点。默认值: true
                       },
                       async: {
                           enable: true,//设置 zTree 是否开启异步加载模式 默认值：false
                           url:'${ctx}/resource/ztree',
                           type:"post",
                           otherParam: { "rid":$("#role_id").val()},
                           autoParam: ["id"]//异步加载时需要自动提交父节点属性的参数
                       },
                       callback:{
                           onCheck: zTreeOnCheck,//用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
                           onClick: zTreeOnClick, //用于捕获节点被点击的事件回调函数
                           onAsyncSuccess :asyncSuccess
                       }
                   };
              var channelTree = $.fn.zTree.init($("#channelTree"), resourceSetting,null);
              function zTreeOnCheck(event, treeId, treeNode) {
                  var zTree = $.fn.zTree.getZTreeObj("channelTree"),
                  nodes = zTree.getCheckedNodes(true);
                  v = "";
                  ids="";
                  //循环勾选的菜单，取得菜单name及id
                  for(var i=0, l=nodes.length; i<l; i++){
                      var halfCheck = nodes[i].getCheckStatus().half;
                  }
              };
              function zTreeOnClick(event, treeId, treeNode) {
            	 $("#channelName").text(treeNode.name);
            	 $('#resourceEdit').load('${ctx}/resource/toEdit/'+treeNode.id,function(){
                      //加一个遮罩在次
                 });
              };
              function asyncSuccess(event, treeId, treeNode, msg) {
            	  $(".widget-body").ace_scroll({size: 600});
              };
              
<%--        });--%>
        </script>
</body>
</html>