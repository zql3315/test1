$(document).ready(
				function() {
					$(".chosen-select").chosen({
						allow_single_deselect : true,
						disable_search_threshold : 10
					});

					// resize the chosen on window resize
					$(window).on("resize.chosen", function() {
						// var w = $(".chosen-select").parent().width();
						$(".chosen-select").next().css({"width" : "41.66666667%","float" : "left"
						});
					}).trigger("resize.chosen");

					
					$("#user").validate({
						rules:{
//								username:{required: true},
//								phonenumber:{required: true,digits:true,rangelength:[11,11]},
//								usermail:{required: true,email:true},
								province:{required: true},
								cityname:{required: true},
								county:{required: true},
								town:{required: true},
								addresseinfo:{required: true}
						},
						messages:{
//								username:{required: "请输入 用户名"},
//								phonenumber:{required: "请输入 手机号",digits:"请输入整数",rangelength:"请输入11位有效手机号号码"},
//								usermail:{required: "请输入 邮箱",email:"请输入邮箱邮箱"},
								province:{required: "请输入 省份"},
								cityname:{required: "请输入 城市"},
								county:{required: "请输入 区县"},
								town:{required: "请输入 街道"},
								addresseinfo:{required: "请输入 详细地址"}
						},
						submitHandler:function(form){
							var formData = $(form).serializeArray();
							if ($.isArray(formData)) {
								var d={};
								$.each(formData, function(i, item) {
									if (item.name && item.value && item.name.indexOf("_") != 0) {
										 if(item.name.indexOf(".") > 0 ){
												var temp={};
												temp[item.name.substr(item.name.indexOf(".")+1)] = item.value;
												d[item.name.substr(0,item.name.indexOf("."))] = temp;
											}else{
												d[item.name] = item.value;
											}
									}
								});
								$.ajax({
									url : basePath+"hn/user/edit",
									type : "POST",
									async : false,
									dataType : "json",
									contentType : "application/json;charset=UTF-8",
									data : JSON.stringify(d)
								}).done(function(data) {
									if(data && data.result){
											showSuccess(function(){
												openPage(basePath+"/hn/user/preview");
												backBreadcrumb();
											});
									}else{
										alert("编辑失败");
									}

								}).fail(function(data) {
									alert("编辑失败");
								});
							}
						}
					});
					
					var $overflow = '';
					var colorbox_params = {
						rel : 'colorbox',
						reposition : true,
						scalePhotos : true,
						scrolling : false,
						previous : '<i class="ace-icon fa fa-arrow-left"></i>',
						next : '<i class="ace-icon fa fa-arrow-right"></i>',
						close : '&times;',
						current : '{current} of {total}',
						maxWidth : '100%',
						maxHeight : '100%',
						onOpen : function() {
							$overflow = document.body.style.overflow;
							document.body.style.overflow = 'hidden';
						},
						onClosed : function() {
							document.body.style.overflow = $overflow;
						},
						onComplete : function() {
							$.colorbox.resize();
						}
					};
					$('.tab-pane [data-rel="colorbox"]').colorbox(colorbox_params);
					// <%-- 审核认证用户 --%>
					$(".isauth").on("click",
					function() {
						var isauth = $(this).data("isauth");
						var _this = $(this);
						$.ajax({
									url : basePath+ "hn/user/authenticationUser",
									type : "POST",
									data : "userid="+ userid+ "&isauth="+ isauth+ "&cancelauthdesc="+ $("#cancelauthdesc").val(),
								}).done(
										function(data) {
											if (data&& data.result) {
												if (isauth == 1) {
													if (paramflag == 3) {
														var copperativeid = $(this).data("cid");
														var breadcrumb_items = getBreadcrumb();
														// 翻转
														breadcrumb_items.reverse();
														// 去除最后一个导航
														breadcrumb_items.splice(breadcrumb_items.length - 1,1);
														breadcrumb_items.reverse();
														// 渲染breadcrumb
														refreshBreadcrumb(breadcrumb_items);

														$('div').find('.page-content').load(basePath+ "/hn/authenticate/preview/",
															function() {
																// 加一个遮罩在次
															});
													} else {
														$("#_isauth").val("已认证");
														_this.removeClass("btn-info");
														$("#cancelauthdesc").attr("readonly",false);
														_this.html('<i class="ace-icon fa fa-undo smaller-90"></i>取消认证');
														_this.data('isauth',0);
													}
												} else {
													$("#_isauth").val("未认证");
													_this.addClass("btn-info");
													$("#cancelauthdesc").attr("readonly",true);
													_this.html('<i class="ace-icon fa fa-check smaller-90"></i>确认认证');
													_this.data('isauth',1);
												}
												alert("成功");
											} else {
												alert(data.message);
											}

								}).fail(function(data) {
									alert("失败");
							});
					});
					var d = {};
					d.userid = userid;
					// 获取主营范围数据
					$.ajax({
							url : basePath+ "hn/user/businessscope/getUserBusinessScopeList",
							type : "POST",
							async : false,
							dataType : "json",
							data : "userid=" + userid
						}).done( function(data) {
								if (data && data.result) {
									for ( var i = 0; i < data.result.length; i++) {
										var obj = data.result[i];
										$("#business").append("<span class='tag'>"+ obj.businessname+ "</span>");
									}
								}
						}).fail(function(data) {
								alert("失败");
						});
					//	获取用户关注方向
					$ .ajax({
								url : basePath + "hn/user/attentionscope/getUserAttentionScopeList",
								type : "POST",
								async : false,
								dataType : "json",
								data : "userid=" + userid
							}).done(
									function(data) {
										if (data && data.result) {
											for ( var i = 0; i < data.result.length; i++) {
												var obj = data.result[i];
												$("#attention").append( "<span class='tag'>" + obj.attentionname + "</span>");
											}
										}
							}).fail(function(data) {
								alert("失败");
							});
					//	获取用户擅长领域
					$.ajax({
							url : basePath + "hn/user/userstrongscope/getUserStrongScopeList",
							type : "POST",
							async : false,
							dataType : "json",
							data : "userid=" + userid
						}).done(function(data) {
									if (data && data.result) {
										for ( var i = 0; i < data.result.length; i++) {
											var obj = data.result[i];
											$("#userstrong").append( "<span class='tag'>" + obj.strongname + "</span>");
										}
									}
	
						}).fail(function(data) {
							alert("失败");
						});
				});