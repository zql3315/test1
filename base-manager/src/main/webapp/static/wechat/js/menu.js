jQuery(function($) {
	//编辑一级菜单
	$(".firstmenu").on('click', function() {
        var secondMenuNum = $(this).next("ul").children("li").length;//当前一级菜单下面有几个二级菜单
		var secondMenuResidueNum = (5 - $(this).next("ul").children("li").length);// 计算一级菜单下面有几个二级菜单
		var menuName = $(this).children(".menu1").text();//菜单名
		var type1 = $(this).children(".type1").val();//菜单类型
		var content1 = $(this).children(".content1").val();//菜单内容
		
		var name = $(this).attr("name");
		var pid = $(this).children("div").data("pid");
		var id = $(this).children("div").attr("dateid");
		
		//菜单重命名
		rename(pid);
		
		//删除菜单
		remove(pid);
		
		//添加二级菜单div层
		$("#context0").siblings().hide();
		$("#context0").show();
		
		$("#title1").html(name + ":");
		$("#title2").html(menuName);
		$("#title3").show();
		
		$("#triggerContent_click").val("");
		$("#triggerContent_view").val("");
		
		//显示内容
		if(type1 == "click"){
			$("#triggerContent_click").val(content1);
		}else if(type1 == "view"){
			$("#triggerContent_view").val(content1);
		}
		
		$("#dialog-msg").children("#p1").show();
		
		if (secondMenuResidueNum > 0) {
			if (secondMenuResidueNum == 5) {
				//无二级菜单，编辑一级菜单时显示添加二级菜单、发送信息、跳转到网页
				$("#context2").siblings().hide();
				$("#context2").show();
				$("#context2 span").html(menuName);
				
				$("#context2 #initialCreate").on('click', function(e) {
					e.stopPropagation();
					$("#p1").html("还能添加" + secondMenuResidueNum + "个二级菜单");
					dialog(pid,"0",secondMenuNum);
				});
				
				//编辑发送页面
				$("#context2 #sendMsg").unbind('click');
				$("#context2 #sendMsg").bind('click', function(e) {
					e.stopPropagation();
					$("#context3").siblings().hide();
					$("#context3").show();
					//保存菜单
					save(pid,"click");
					
				});
				
				//编辑跳转链接页
				$("#context2 #goPage").unbind('click');
				$("#context2 #goPage").bind('click', function(e) {
					e.stopPropagation();
					$("#context4").siblings().hide();
					$("#context4").show();
					//保存菜单
					save(pid,"view");
				});
				
			} else {
				$("#context0").children("p").siblings().show();
				$("#context0").children("p").html("已为\"" + menuName + "\"添加了二级菜单，无法设置其他内容。你还可以添加" + secondMenuResidueNum + "个二级菜单");
				
				$("#context0 #initialCreate").on('click', function(e) {
					e.stopPropagation();
					$("#p1").html("还能添加" + secondMenuResidueNum + "个二级菜单");
					
					dialog(pid,"0",secondMenuNum);
				});
			}
		} else {
			$("#context0").children("p").siblings().hide();
			$("#context0").children("p").html("您已加满5个二级菜单！<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>");
		}
	});

	// 编辑二级菜单
	$(".secondmenu").on('click', function() {
		var pid = $(this).children("div").data("pid");
		var id = $(this).children("div").attr("dateid");
		
		rename(pid);
		remove(pid);
		
		var secondMenuName = $(this).children(".menu2").text();//菜单名
		var type2 = $(this).children(".type2").val();//菜单类型
		var content2 = $(this).children(".content2").val();//菜单内容
		
		//二级菜单
		var name = $(this).attr("name");
		
		$("#title3").show();
		$("#context1 span").html(secondMenuName);
		$("#context1").siblings().hide();
		$("#context1").show();
		$("#title1").html(name + ":");
		$("#title2").html(secondMenuName);
		
		$("#triggerContent_click").val("");
		$("#triggerContent_view").val("");
		
		if(type2 == "click"){
			$("#triggerContent_click").val(content2);
		}else if(type2 == "view"){
			$("#triggerContent_view").val(content2);
		}
		
		//编辑二级菜单发送消息
		$("#context1 #sendMsg").unbind('click');
		$("#context1 #sendMsg").bind('click', function(e) {
			e.stopPropagation();
			$("#context3").siblings().hide();
			$("#context3").show();
			//保存菜单
			save(pid,"click");
		});

		//编辑二级菜单跳转链接
		$("#context1 #goPage").unbind('click');
		$("#context1 #goPage").bind('click', function(e) {
			e.stopPropagation();
			//siblings() 获得匹配集合中每个元素的同胞
			$("#context4").siblings().hide();
			$("#context4").show();
			
			//保存菜单
			save(pid,"view");
		});
		
	});
	

	// override dialog's title function to allow for HTML titles
	$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
		_title : function(title) {
			var $title = this.options.title;
			if (("title_html" in this.options) && this.options.title_html == true)
				title.html($title);
			else
				title.text($title);
		}
	}));
	
	// 添加菜单
	$(".addMenu").on('click', function(e) {
		e.preventDefault();
		var subnum = $(this).parent(".firstmenu").next("ul").children("li").length;//计算一级菜单下面有几个二级菜单
		var residue_subnum = (5 - $(this).parent(".firstmenu").next("ul").children("li").length);////计算一级菜单下面剩余几个二级菜单
		var name = $(this).attr("name");
		var id = $(this).attr("dateid");
		
		if ("一级菜单" == name) {
			var pid = $(this).data("pid");
			var subnum = $(".firstmenu span").length;
			var residue_menunum = (3 - $(".firstmenu span").length);//剩余一级菜单数
			
			if (residue_menunum) {
				$("#p1").html("还能添加" + residue_menunum + "个" + name + "，请输入名称");
				dialog(pid, id, subnum);
			}else {
				$("#tip1").html("最多只能添加3个一级菜单，当前已达设置上限！");
				$("#tip1").fadeIn(500).delay(2000).fadeOut(500);
			}
		}else if ("二级菜单" == name) {
			var pid = $(this).data("pid");
			
			if (residue_subnum > 0) {
				$("#p1").html("还能添加" + residue_subnum + "个" + name);
				dialog(pid, id, subnum);
			}else {
				$("#tip1").html("同一级一级菜单下只能添加5个二级菜单，当前已达设置上限！");
				$("#tip1").fadeIn(500).delay(2000).fadeOut(500);
			}
		}
	});

	// 删除
	function remove(pid) {
		$("#jsDelBt").on('click', function() {
			$("#dialog-confirm-context").html("删除后该菜单下设置的消息将不会被保存,您确认删除吗？");
			$("#dialog-confirm").removeClass('hide').dialog({
				resizable : false,
				modal : true,
				title : $("#confirmtitle").html(),
				title_html : true,
				buttons : [{
					html : "<i class='ace-icon fa fa-check-square-o bigger-110'></i>&nbsp; 确认",
					"class" : "btn btn-danger btn-xs",
					click : function() {
						var d = {};
						d["pid"] = pid;
						var data = JSON.stringify(d);
						$.ajax({
							url : ctx + "/weiXin/customMenu/deleteMenu",
							type : "POST",
							async : false,
							contentType : "application/json;charset=UTF-8",
							data : data,
							success : function(data, textStatus) {
								alert("删除成功！");
								refresh();
							},
							complete : function(XMLHttpRequest,textStatus) {},
							error : function(e) {
								alert("删除失败！");
							}
						});
						$(this).dialog("close");
					}
				}, {
					html : "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; 取消",
					"class" : "btn btn-xs",
					click : function() {
						$(this).dialog("close");
					}
				}]
			});
		});
	}
	
	/** 保存 */
	function save(pid,type) {
		$("#jsSaveBt").unbind('click');
		$("#jsSaveBt").bind('click', function() {
			var triggerContent = $("#triggerContent_click").val();
			if(type =="view"){
				triggerContent = $("#triggerContent_view").val();
			}
			
			var d = {};
			d["pid"] = pid;
			d["triggerContent"] = triggerContent;
			d["triggerType"] = type;

			var data = JSON.stringify(d);
			
			$.ajax({
				url : ctx + "/weiXin/customMenu/editMenu",
				type : "POST",
				async : false,
				contentType : "application/json;charset=UTF-8",
				data : data,
				success : function(data, textStatus) {
					alert("保存成功！");
					refresh();
				},
				complete : function(XMLHttpRequest,textStatus) {},
				error : function(e) {
					alert("保存失败！");
				}
			});
		});
	}

	/** 重命名 */
	function rename(pid) {
		//$("#jsChangeBt").off('click');
		$("#jsChangeBt").on('click', function() {
			//隐藏提示语
			$(".js_titleEorTips").hide();
			$(".js_titlenoTips").hide();
			
			var con = $(this).parent("span").siblings("#title2").html().trim();
			var text = $(this).parent("span").siblings("#title1").text();
			
			$("#menuName").val(con);
			
			$("#dialog-msg").children("#p1").text("重命名"+text);
			$("#dialog-msg").removeClass('hide').dialog({
				modal : true,
				title : $("#messagetitle").html(),
				title_html : true,
				buttons : [{
					text : "取消",
					"class" : "btn btn-xs",
					click : function() {
						$(this).dialog("close");
					}
				}, {
					text : "确定",
					"class" : "btn btn-primary btn-xs",
					click : function() {
						var menuName = $(this).children("#p2").children("#menuName").val();
						var d = {};
						d["pid"] = pid;
						d["menuName"] = menuName;

						var data = JSON.stringify(d);
						
						$.ajax({
							url : ctx + "/weiXin/customMenu/renameMenu",
							type : "POST",
							async : false,
							contentType : "application/json;charset=UTF-8",
							data : data,
							success : function(data, textStatus) {
								alert("重命名成功！");
								refresh();
							},
							complete : function(XMLHttpRequest,textStatus) {},
							error : function(e) {
								alert("重命名失败！");
							}
						});
						$(this).dialog("close");
					}
				}]
			});
		});
		
	
	}

	/** 添加菜单弹出框 */
	function dialog(pid, id, subnum) {
		var text = "添加一级菜单";
		var tips = "字数不超过4个汉字或8个字母";
		
		if (id == "0") {
			text = "添加二级菜单";
			tips = "字数不超过8个汉字或16个字母";
		}
		
		//菜单字数提示
		$("#p3").html(tips);
		
		//隐藏提示语
		$(".js_titleEorTips").hide();
		$(".js_titlenoTips").hide();
		
		$("#dialog-msg").removeClass('hide').dialog({
			modal : true,
			title : "<div class='widget-header widget-header-small'><h4 class='smaller'> "
					+ "<i class='ace-icon fa fa-check'></i> "
					+ text
					+ "</h4></div>",
			title_html : true,
			buttons : [{
						text : "取消",
						"class" : "btn btn-xs",
						click : function() {
							$("#menuName").val("");
							$(".js_titleEorTips").hide();
							$(".js_titlenoTips").hide();
							$(this).dialog("close");
						}
					}, {
						text : "确定",
						"class" : "btn btn-primary btn-xs",
						click : function() {
							var menuName = $(this).children("#p2").children("#menuName").val();
							var vv = menuName.replace(/[^\x00-\xff]/g, "aa");
							
							if (menuName == "")
							{
								$(".js_titlenoTips").show();
							}
							else if (menuName != "") 
							{
								var d = {};
								d["pid"] = pid;
								d["menuName"] = menuName;// menuName
								d["triggerType"] = "view";// triggerType默认为view
								d["state"] = "1";// state
								d["subnum"] = subnum;
								
								var data = JSON.stringify(d);
								
								$.ajax({
									url : ctx + "/weiXin/customMenu/addMenu",
									type : "POST",
									async : false,
									contentType : "application/json;charset=UTF-8",
									data : data,
									success : function(data, textStatus) {
										alert("添加成功！");
										refresh();
									},
									complete : function(XMLHttpRequest,textStatus) {},
									error : function(e) {
										alert("添加失败！");
									}
								});
								$("#menuName").val("");
								$(this).dialog("close");
							} 
							else 
							{
								alert("添加失败！");
							}

						}
					}]
		});
	}

	$(".nestable-handle a").on("mousedown", function(e) {
		e.stopPropagation();
	});
	
	$(".jsViewLi").on('click', function() {
		$(".jsSubViewDiv").css("display", "none");
		$(this).children(".jsSubViewDiv").css("display", "block");
	});
	
	// 预览
	$("#viewBt").on('click', function() {
		$("#mask").css("display", "block");
		$("#mask").spin({color : "#fff"});
		$("#mobileDiv").css("display", "block");
		
		$.ajax({
			url : ctx + "/weiXin/customMenu/getPreview",
			type : "POST",
			async : false,
			dataType : "json",
			traditional : true,
			contentType : "application/json;charset=UTF-8",
			data : "",
			success : function(data, textStatus) {
				var obj = data.previewlist;
				var tbody = "";
				var tb = "";
				$.each(obj, function(n, value) {
					var trs = "";
					var children = value.children;
					$.each(children, function(m, value) {
						var sts = "";
						sts += '<li id="subMenu_menu_0_0"><a title="'
								+ value.menuName
								+ '" class="jsSubView"href="javascript:void(0);">'
								+ value.menuName + '</a></li>';
						tb += sts;
					});
					trs += '<li id="menu_0" class="pre_menu_item grid_item size1of3 jsViewLi">'
							+ '<a title="'
							+ value.menuName
							+ '" class="jsView pre_menu_link"	href="javascript:void(0);"> <i class="icon_menu_dot"></i>'
							+ value.menuName
							+ ' </a>'
							+ '<div style="" class="sub_pre_menu_box jsSubViewDiv">'
							+ '<ul class="sub_pre_menu_list">'
							+ tb
							+ '</ul>'
							+ '<i class="arrow arrow_out"></i> <i class="arrow arrow_in"></i>'
							+ '</div>' + '</li>'
					tb = "";
					tbody += trs;
				});
				$("#viewList").html(tbody);
			},
			complete : function(XMLHttpRequest, textStatus) {
			},
			error : function(e) {
				alert("error啦");
			}
		});
	});
	
	// 退出预览
	$("#viewClose").on('click', function() {
		$("#mask").css("display", "none");
		$("#mobileDiv").css("display", "none");
	});

	// 排序
	var data = [];
	$("#id-check-horizontal").removeAttr("checked").on("click", function() {
		$(".action_tips").html("请通过拖拽左边的菜单进行排序");
		if (this.checked) {
			$("#sorts").attr("data-original-title", "取消");
			$(".move-ul").sortable({
				opacity : 0.6, // 拖动时，透明度为0.6
				delay : 10,
				stop : function() {
					var depth = 0, list = $("#sortables");
					step = function(level) {
						var array = [], items = level.children("ul").children("li");
						items.each(function() {
									var item = {};
									var li = $(this), sub = li.children("ul").children("li");
									item.id = li.data("id");
									if (sub.length) {
										item.children = step(li);
									}
									array.push(item);
								});
						return array;
					};
					data = step(list);
				}
			});
			$(".move-ul").sortable("enable");
		} else {
			$("#sorts").attr("data-original-title", "排序");
			$(".action_tips").html("你可以点击左侧菜单或添加一个新菜单，然后设置菜单内容");
			$(".move-ul").sortable("disable");
		}
	});

	$("[data-rel='tooltip']").tooltip();

	//发布菜单
	$("#pubBt").on('click', function(e) {
		e.preventDefault();
		
		var obj = {};
		obj.data = data;
		var d = JSON.stringify(obj);
		$.ajax({
			url : ctx + "/weiXin/customMenu/sortMenu",
			type : "POST",
			async : false,
			// dataType : "json",
			traditional : true,
			contentType : "application/json;charset=UTF-8",
			data : d,
			success : function(data, textStatus) {
				refresh();
			},
			complete : function(XMLHttpRequest, textStatus) {
			},
			error : function(e) {
				alert("error啦");
			}
		});
		
		
		$("#dialog-confirm-context").html("发布确认,本次发布将在24小时内对所有用户生效。确认发布？");
		
		$("#dialog-confirm").removeClass('hide').dialog({
			resizable : false,
			modal : true,
			title : "<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-exclamation-triangle red'></i>温馨提示</h4></div>",
			title_html : true,
			buttons : [{
				html : "<i class='ace-icon fa fa-check-square-o bigger-110'></i>&nbsp; 确认",
				"class" : "btn btn-danger btn-xs",
				click : function() {
					$.ajax({
						url : ctx + "/weiXin/customMenu/release",
						type : "POST",
						async : false,
						contentType : "application/json;charset=UTF-8",
						success : function(data, textStatus) {
							
							if(data.result){
								alert("发布成功！");
							}else{
								alert("发布失败，请检查菜单项信息是否配置合理！");
							}
						},
						complete : function(XMLHttpRequest,textStatus) {
						},
						error : function(e) {
							alert("发布失败，请检查菜单项信息是否配置合理！");
						}
					});
					
					$(this).dialog("close");
				}
			}, {
				html : "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; 取消",
				"class" : "btn btn-xs",
				click : function() {
					$(this).dialog("close");
					refresh();
					//点击取消，排序回复之前状态
				}
			}]
		});

	});

});

/** 刷新当前页面 */
function refresh() {
	// breadcrumb
	var breadcrumb_items = getBreadcrumb();
	breadcrumb_items.reverse();
	// 取出当前页面导航添加链接
	var lastItem = breadcrumb_items[breadcrumb_items.length - 1];
	lastItem['data-action'] = ctx + "/weiXin/customMenu/preview";
	
	breadcrumb_items[breadcrumb_items.length - 1] = lastItem;
	breadcrumb_items.reverse();
	// 渲染breadcrumb
	refreshBreadcrumb(breadcrumb_items);

	$('div').find('.page-content').load(ctx + '/weiXin/customMenu/preview',
		function() {
				// 加一个遮罩在次
	});
	
}

//菜单名称输入框提示语
$(".js_menu_name").on("input propertychange", function() {
	var parent = $(this).parent().prev().text();
	var menuname = "一级菜单";
	if(parent != "" && parent.indexOf("二级菜单") > 0){
		menuname = "二级菜单";
	}
	
	var tips = "字数不超过4个汉字或8个字母";
	
	if (menuname == "二级菜单") {
		tips = "字数不超过8个汉字或16个字母";
	}
	
	
	
	var vv = $(this).val().replace(/[^\x00-\xff]/g, "aa");
	
	if (vv.length == 0) {
		$(".js_titlenoTips").show();
	}
	else {
		$(".js_titlenoTips").hide();
	}
	
	if (menuname == "一级菜单" && vv.length > 8) {
		$(".js_titleEorTips").show();
		$("#p3").html(tips);
	}
	else if (menuname == "二级菜单" && vv.length > 16) {
		$(".js_titleEorTips").show();
		$("#p3").html(tips);
	}
	else {
		$(".js_titleEorTips").hide();
	}
});