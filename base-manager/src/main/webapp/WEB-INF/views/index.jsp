<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.b2r.com.cn/sys" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>网络后台管理系统基线版本</title>
	<meta name="decorator" content="default">
</head>
<body>  
	<div id="tip1" class="tips_top"></div>
	<!-- #section:basics/navbar.layout -->
	<!-- 页面头部 -->
	<div id="navbar" class="navbar navbar-default">
		<script type="text/javascript">
			try{ace.settings.check('navbar' , 'fixed')}catch(e){}
		</script>
		<!-- 页面头部右侧系统级的设置 -->
		<div class="navbar-container" id="navbar-container">
				<!-- #section:basics/sidebar.mobile.toggle -->
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler">
					<span class="sr-only">Toggle sidebar</span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<!-- /section:basics/sidebar.mobile.toggle -->
				<div class="navbar-header pull-left">
					<!-- #section:basics/navbar.layout.brand -->
<%--					<div class="logo" style="float: left;">--%>
<%--                        <img src='${ctx}/static/image/logo2.png'/>--%>
<%--					</div>--%>
					<a href="#" class="navbar-brand">
						<small>
							<i class="fa"></i>
							 <img src='${ctx}/static/image/logo2.png'/>
							&nbsp;网络后台管理系统基线版本
						</small>
					</a>
					<!-- /section:basics/navbar.layout.brand -->

					<!-- #section:basics/navbar.toggle -->

					<!-- /section:basics/navbar.toggle -->
				</div>

				<!-- #section:basics/navbar.dropdown -->
				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<!-- 通知-->
						<li class="purple">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="ace-icon fa fa-bell icon-animated-bell"></i>
								<span class="badge badge-important"></span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-exclamation-triangle"></i>
								</li>
							</ul>
						</li>
						<!-- 个人登录信息 -->
						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
							 	<!--  <img class="nav-user-photo" src="//placehold.it/300x200">-->
								<img class="nav-user-photo" src="./assets/avatars/user.jpg" alt="Jason's Photo">
								<span class="user-info">
									<small>欢迎,</small>
									<span class="username"><shiro:principal property="name"></shiro:principal></span>
								</span>
								<i class="ace-icon fa fa-caret-down"></i>
							</a>
							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="${ctx}/login/logout">
										<i class="ace-icon fa fa-sign-out"></i>
										Logout
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>

				<!-- /section:basics/navbar.dropdown -->
			</div><!-- /.navbar-container -->
	</div>
	
	<!-- 页面头部以下部分 -->
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try{ace.settings.check('main-container' , 'fixed')}catch(e){}
		</script>
		
		<!-- 左侧菜单部分 -->
		<!-- #section:basics/sidebar -->
		<div id="sidebar" class="sidebar                  responsive">
			<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
			</script>
	
			<!-- <div class="sidebar-shortcuts" id="sidebar-shortcuts">
				<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
	
					/section:basics/sidebar.layout.shortcuts
				</div>
	
				<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
					<span class="btn btn-success"></span>
	
					<span class="btn btn-info"></span>
	
					<span class="btn btn-warning"></span>
	
					<span class="btn btn-danger"></span>
				</div>
			</div>/.sidebar-shortcuts -->
			
			<sys:menu></sys:menu>
			<!-- /.nav-list -->
	
			<!-- #section:basics/sidebar.layout.minimize -->
			<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
				<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
			</div>
	
			<!-- /section:basics/sidebar.layout.minimize -->
			<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
			</script>
		</div>
	
		<!-- 右侧内容部分 -->
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<!-- 导航部分 -->
			<!-- #section:basics/content.breadcrumbs -->
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
				</script>
	
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon active"></i>
						<a href="#" data-action="#">首页</a>
					</li>
				</ul><!-- /.breadcrumb -->
				<!-- /section:basics/content.searchbox -->
			</div>
	
			<!-- /section:basics/content.breadcrumbs -->
			<div class="page-content">
				<div class="alert alert-block alert-success">
					<button type="button" class="close" data-dismiss="alert">
						<i class="ace-icon fa fa-times"></i>
					</button>

					<i class="ace-icon fa fa-check green"></i>
						欢迎登陆
					<strong class="green">
						网络后台管理系统基线版本
						<small>(v1.0)</small>
					</strong>
				</div>
			</div><!-- /.page-content -->
		</div><!-- /.main-content -->
	
		<!-- 页脚部分 -->
		<div class="footer">
			<div class="footer-inner">
				<!-- #section:basics/footer -->
				<div class="footer-content">
					<span class="bigger-120">
						<span class="blue bolder">网络后台管理系统基线版本</span>
						Application © 2014-2015
					</span>
	
				</div>
	
				<!-- /section:basics/footer -->
			</div>
		</div>
	
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
									
	<!-- /.main-container -->
	<!-- basic scripts -->
	<script src="${ctx}/static/datatable/js/jquery.js"></script>
	<script type="text/javascript">

		//重置选择文件按钮
		$(".page-content").on('click',"button[type='reset']",function(e){
			$(".search-choice-close").remove();
			$(".btn-purple + input").val("");
			$(".ace-file-container").attr("data-title","选择文件");
			$(".ace-file-container").removeClass("selected");
			$(".ace-file-container  .ace-file-name").attr("data-title","请选择一个图片");
			$(".ace-file-container  .ace-file-name .ace-icon").removeClass().addClass(" ace-icon fa fa-upload ");
			$.each($(".chosen-single"), function(i, item) {
				$(this).removeClass("chosen-single-with-deselect").addClass("chosen-default");
				var str = $(this).parent().parent().find(".chosen-select").attr("data-placeholder");
				$(this ).find("span").text(str);
			});
		});
	
		$.ajaxSetup ({
		    cache: false //关闭AJAX相应的缓存
		});
		
		//左边菜单点击
		$(document).on('click'+'.ace.submenu', '.sidebar .nav-list', function (ev) {
			var nav_list = this;
			//check to see if we have clicked on an element which is inside a .dropdown-toggle element?!
			//if so, it means we should toggle a submenu
			var link_element = $(ev.target).closest('a');
			if(!link_element || link_element.length == 0) return;//return if not clicked inside a link element

			var minimized  = ace.vars['minimized'] && !ace.vars['collapsible'];
			//if .sidebar is .navbar-collapse and in small device mode, then let minimized be uneffective

			if( !link_element.hasClass('dropdown-toggle') ) {
				$('.nav-list li.active').removeClass('active');
				link_element.addClass('active').parents('.nav-list li').addClass('active');
				//link_element.parent().addClass('active');
			}
			
			if(link_element.attr('data-action')!='#'){
				 $('div').find('.page-content').load(link_element.attr('data-action'),function(){
					 //加一个遮罩在次
				});
			}
			
			//横向导航菜单
			var breadcrumb_items = [];
			//$(this) is a reference to our clicked/selected element
			link_element.parents('.nav-list li').each(function() {
			  var link = $(this).find('> a');
			  var text = link.text();
			  var href = link.attr('data-action');
			  breadcrumb_items.push({'text': text, 'data-action': href});
			})
			
			breadcrumb_items.push({'text': '首页', 'data-action': '#'});
			
			//渲染breadcrumb
			refreshBreadcrumb(breadcrumb_items);
			
		});
		
		//点击横向导航条触发事件
		$(document).on('click','.breadcrumbs .breadcrumb',function(ev){
			var link_element = $(ev.target).closest('a');
			var breadcrumb_items = getBreadcrumb();
			
			if (link_element.attr('data-action')) {
				
				if(link_element.attr('data-action')=='#'){
					return;
				}
				//当前导航节点位置
				var currentIndex=0;
				//循环去掉当前点击的导航以后的节点
				for(var i=breadcrumb_items.length-1;i>=0;i--){
					if (link_element.text()==breadcrumb_items[i]['text']) {
						currentIndex=i;
						break;
					}
				}
				
				breadcrumb_items.splice(0,currentIndex);
				//渲染breadcrumb
				refreshBreadcrumb(breadcrumb_items);
				//加载页面
				$('div').find('.page-content').load(link_element.attr('data-action'),function(){
					 //加一个遮罩在次
				});
			}
			
		});
		
		//渲染横向导航条
		var refreshBreadcrumb=function(items){
			
			var html='';
			var breadcrumb_items=items;
			for(var i=breadcrumb_items.length-1;i>=0;i--){
				if(i==0){
					html+='<li>'+breadcrumb_items[i]['text']+'</li>';
				}else if(i==breadcrumb_items.length-1){
					html+='<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#" data-action="'+breadcrumb_items[i]['data-action']+'">'+breadcrumb_items[i]['text']+'</a></li>';
				}else{
					html+='<li> <a href="#" data-action="'+breadcrumb_items[i]['data-action']+'">'+breadcrumb_items[i]['text']+'</a></li>';
				}
			}
			
			$('.breadcrumb').empty();
			$('.breadcrumb').append(html);
		};
		
		//获取导航所有节点信息
		var getBreadcrumb=function(){
			//获取当前breadcrumb
			var breadcrumb_items = [];	
			$('.breadcrumb').find('li').each(function(){
				var text = $(this).text();
				var href = '';
				if ($(this).children('a').length>0) {
					text = $(this).find('> a').text();
					href = $(this).find('> a').attr('data-action');
				}
				breadcrumb_items.push({'text': text, 'data-action': href});
			});
			
			//反转
			breadcrumb_items.reverse();
			return breadcrumb_items;
		};
		/**
		* 重置导航
		*/
		var setBreadcrumb = function(lasturl, text) {
			if(!text) {
				text = "";
			}
			//breadcrumb
			var breadcrumb_items = getBreadcrumb();
			breadcrumb_items.reverse();
			//取出当前页面导航添加链接
			var lastItem=breadcrumb_items[breadcrumb_items.length-1];
			lastItem['data-action']= lasturl;
			breadcrumb_items[breadcrumb_items.length-1]=lastItem;
			breadcrumb_items.push({'text': text});
			breadcrumb_items.reverse();
			
			//渲染breadcrumb
			refreshBreadcrumb(breadcrumb_items);
		}
		/* 
		$(document).ready(function(){
			var pollingUrl="${ctx}/deferredResult/polling";
			longPolling(pollingUrl, function(data) {
			    if(data) {
			    	
			    	$("li.dropdown-header").text(data.count+'个任务待处理 。');
					$("span.badge-important").text(data.count);
					
					var notifyHTML="";
					
					$.each(data.notifications,function(){
						notifyHTML+='<li>'+
										'<a href="#">'+
										'<div class="clearfix">'+
											'<span class="pull-left">'+
												this.title
											'</span>'+
										'</div>'+
									'</a>'+
								'</li>';
					});
					
					$("li.dropdown-header").after(notifyHTML);
			    }
			});
		});
		
		var longPolling = function(url, callback) {
		    $.ajax({
		        url: url,
		        async: true,
		        cache: false,
		        global: false,
		        type : 'POST',
		        timeout: 30 * 1000,
		       // dataType : "json",
		        success: function (data, status, request) {
		            callback(data);
		            data = null;
		            status = null;
		            request = null;
		            setTimeout(
		                function () {
		                    longPolling(url, callback);
		                },
		                10
		            );
		        },
		        error: function (xmlHR, textStatus, errorThrown) {
		            xmlHR = null;
		            textStatus = null;
		            errorThrown = null;

		            setTimeout(
		                function () {
		                    longPolling(url, callback);
		                },
		                30 * 1000
		            );
		        }
		    });
		};*/
	</script>
</body>
</html>
