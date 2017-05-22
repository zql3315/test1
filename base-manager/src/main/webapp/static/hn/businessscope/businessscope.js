// 加载分类列表		
function showBusinessScope(pid,businessname,showid){
	$.ajax({
		url : basePath+'/hn/businessscope/queryBusinessScopeList',
		type : 'POST',
		async : false,
		data : "pid="+pid+"&businessname="+businessname
	}).done(function(data) {
		$("#"+showid).html("").html(data);
	}).fail(function(data) {
		
	});
}
//<%-- 删除主营方向		--%>
function deleteBusinessScope(id,pid){
	window.confirm("是否确认删除?", function(_dialog){
		var r={};
		r.id=id;
		$.ajax({
			url :basePath+ '/hn/businessscope/delete',
			type : 'POST',
			async : false,
			dataType : 'json',
			contentType : 'application/json;charset=UTF-8',
			data : JSON.stringify(r)
		}).done(function(data) {
			if(pid === "0"){
				showBusinessScope(0,"","onebusinessscope");
				pid = $("#onebusinessscope div.active ").data("sid");
				showBusinessScope(pid,"","secendbusinessscope");
			}else{
				showBusinessScope(pid,"","secendbusinessscope");
			}
			alert('删除成功');
		}).fail(function(data) {
			alert('删除失败');
		});
		$( _dialog ).dialog( "close" );
	});
}
//<%-- 编辑主营方向名称		--%>
function editBusinessScope(id,pid,businessname){
	$("#businessname").val(businessname);
	var dialog1 = $( "#showmessage" ).removeClass('hide').dialog({
		modal: true,
		title: "<div class='widget-header widget-header-small'><h4 class='smaller'> 修改名称</h4></div>",
		title_html: true,
		buttons: [ 
			{
				text: "取消",
				"class" : "btn btn-xs",
				click: function() {
					$(".ui-dialog").remove();
					 $("#showmessage").css("display","none"); 
					 $("#dialog-message").css("display","none"); 
					 $("#dialog-confirm").css("display","none"); 
					 $("#dialog-success").css("display","none"); 
				} 
			},
			{
				text: "确定",
				"class" : "btn btn-primary btn-xs",
				click: function() {
					var d={};
					d.id=id;
					d.businessname=$("#businessname").val();
					if(d.businessname == "") return ;
					$.ajax({
						url : basePath+'/hn/businessscope/editName',
						type : 'POST',
						dataType : 'json',
						contentType : 'application/json;charset=UTF-8',
						data : JSON.stringify(d)
					}).done(function(data) {
						if(data.result && pid === "0"){
							showBusinessScope(pid,"","onebusinessscope");
						}else if(data.result){
							showBusinessScope(pid,"","secendbusinessscope");
						}else{
							alert(data.message);
						}
					}).fail(function(data) {
						alert("失败");
					});
					$(".ui-dialog").remove();
					 $("#showmessage").css("display","none"); 
					 $("#dialog-message").css("display","none"); 
					 $("#dialog-confirm").css("display","none"); 
					 $("#dialog-success").css("display","none"); 
				} 
			}
		]
	});
}
//查询一级分类
function findFirstBusinessScope(){
	var search_name = $("#search_name1").val();
	showBusinessScope("0",search_name,"onebusinessscope");
}
//查询二级分类
function findSecondBusinessScope(){
	var search_name = $("#search_name2").val();
	var pid = $("#onebusinessscope div.active ").data("sid");
	showBusinessScope(pid,search_name,"secendbusinessscope");
}
$(document).ready(function(){
	//点击一级分类加载对应的二级分类
	$( "#onebusinessscope" ).on("mousedown",".onebusinessscope", function(e) {
		var pid = $(this).data("sid");
		showBusinessScope(pid,"","secendbusinessscope");
	});
	//添加分类菜单
	$( ".add_cart_btn" ).on('click', function(e) {
		e.preventDefault();
		$("#businessname").val("");
	    var pid = $(this).data("pid");
	    if(pid === "") pid = $("#onebusinessscope div.active ").data("sid");
		var dialog1 = $( "#showmessage" ).removeClass('hide').dialog({
			modal: true,
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'> "+$(this).data("val")+"</h4></div>",
			title_html: true,
			buttons: [ 
				{
					text: "取消",
					"class" : "btn btn-xs",
					click: function() {
						$(".ui-dialog").remove();
						 $("#showmessage").css("display","none"); 
						 $("#dialog-message").css("display","none"); 
						 $("#dialog-confirm").css("display","none"); 
						 $("#dialog-success").css("display","none"); 
					} 
				},
				{
					text: "确定",
					"class" : "btn btn-primary btn-xs",
					click: function() {
						var d={};
						d.parentid=pid;
						d.businessname=$("#businessname").val();
						if(d.businessname == "") return ;
						$.ajax({
							url : basePath+'/hn/businessscope/add',
							type : 'POST',
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							if(data.result &&  pid == "0")	{
								showBusinessScope(pid,"","onebusinessscope");
								pid = $("#onebusinessscope div.active ").data("sid");
								showBusinessScope(pid,"","secendbusinessscope");
							}else if(data.result){
								showBusinessScope(pid,"","secendbusinessscope");
							}else{
								alert(data.message);
							}
						}).fail(function(data) {
							alert("失败");
						});
						$(".ui-dialog").remove();
						 $("#showmessage").css("display","none"); 
						 $("#dialog-message").css("display","none"); 
						 $("#dialog-confirm").css("display","none"); 
						 $("#dialog-success").css("display","none"); 
					} 
				}
			]
		});
	});
  	$(".businessscope").on("click",".cate_item" , function(){
        $(this).parent().find("div.cate_item").each(function () {
            $(this).removeClass("active");
        });
        $(this).addClass("active");
    });
	
	$('.chosen-select').chosen({allow_single_deselect:true,disable_search_threshold: 10}); 
	//resize the chosen on window resize
	$(window).on('resize.chosen', function() {
		var w = $('.chosen-select').parent().width();
		$('.chosen-select').next().css({'width':w});
	}).trigger('resize.chosen');
	
	$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
		_title: function(title) {
			var $title = this.options.title || '&nbsp;'
			if( ("title_html" in this.options) && this.options.title_html == true )
				title.html($title);
			else title.text($title);
		}
	}));
});