//显示图文消息
function openimagetext(){
	$('div').find('.page-content').load(ctx+'/weiXin/weiXinMaterial/preview',function(){
	 	//加一个遮罩在次
	});
}

//显示图片
function openimage(){
	$('div').find('.page-content').load(ctx+'/weiXin/weiXinMaterial/imagesPreview',function(){
	 	//加一个遮罩在次
	});
}

/*查询图文消息*/
function searchNews() {
	var search_LIKE_title = $("#search_LIKE_title").val();
	$("#dynamic_info").hide();
	$("#query_dynamic_info").show();
	
	var action = ctx+'/weiXin/weiXinMaterial/queryNewsPreview?search_LIKE_title='+search_LIKE_title;

	$.ajax({
		url : action,
		type : 'POST',
		async : false,
		dataType : 'html',
		contentType : 'application/json;charset=UTF-8',
		data : {}
	}).done(function(data) {
		//设置查询消息数量
		var q_weiXinMaterialCount = data.q_weiXinMaterialCount;
		$("#js_count").html(q_weiXinMaterialCount);
		$("#query_dynamic_info").empty();
		$("#query_dynamic_info").append(data);
	}).fail(function(data) {
		alert('查询失败');
	});
}

//添加图文消息
function addMaterialMessage() {
	//breadcrumb
	var breadcrumb_items = getBreadcrumb();
	breadcrumb_items.reverse();
	//取出当前页面导航添加链接
	var lastItem = breadcrumb_items[breadcrumb_items.length - 1];
	lastItem['data-action'] = ctx+"/weiXin/weiXinMaterial/preview";
	console.log(lastItem);
	breadcrumb_items[breadcrumb_items.length - 1] = lastItem;

	breadcrumb_items.push({
		'text' : $(this).text(),
		'data-action' : ctx+'/weiXin/weiXinMaterialMessage/toAdd'
	});
	breadcrumb_items.reverse();
	//渲染breadcrumb
	refreshBreadcrumb(breadcrumb_items);

	$('div').find('.page-content').load(
		ctx+'/weiXin/weiXinMaterialMessage/toAdd', function() {
		//加一个遮罩在次
	});
}

/*编辑图文消息*/
function editMaterialMessage(edit) {
	//breadcrumb
	var breadcrumb_items = getBreadcrumb();
	breadcrumb_items.reverse();
	//取出当前页面导航添加链接
	var lastItem = breadcrumb_items[breadcrumb_items.length - 1];
	lastItem['data-action'] = ctx+"/weiXin/weiXinMaterial/preview";
	breadcrumb_items[breadcrumb_items.length - 1] = lastItem;

	breadcrumb_items.push({
		'text' : $(this).text(),
		'data-action' : ctx+'/weiXin/weiXinMaterialMessage/toEdit'
	});
	breadcrumb_items.reverse();
	//渲染breadcrumb
	refreshBreadcrumb(breadcrumb_items);

	var selected_id = $(edit).attr("editValue");

	$('div').find('.page-content').load(
			ctx+'/weiXin/weiXinMaterialMessage/toEdit/' + selected_id,
			function() {
				//加一个遮罩在次
			});
}

/*删除图文消息*/
function delMaterialMessage(del) {
	window.confirm("确定删除此图文消息吗?", function(_dialog) {
		var r = {};
		r.id = $(del).attr("delValue");

		$.ajax({
			url : ctx+'/weiXin/weiXinMaterialMessage/delete',
			type : 'POST',
			async : false,
			dataType : 'json',
			contentType : 'application/json;charset=UTF-8',
			data : JSON.stringify(r)
		}).done(
				function(data) {
					if (data.result == true) {
						$('div').find('.page-content').load(
								ctx+'/weiXin/weiXinMaterial/preview',
								function() {
									//加一个遮罩在次
								});
					} else {
						alert(data.result);
					}

					$(_dialog).dialog("close");
				}).fail(function(data) {
			alert('删除失败');
		});
	});
}

//获取复选框选中值
function getCheckboxCheckedValue()
{
	//判断复选框是否被选中
	var coll = document.getElementsByName("ck");
	var imageids = [];
	
	for(var i = 0; i < coll.length; i++){
		if(coll[i].checked){
		    imageids.push(coll[i].value);
		}
	}
	
	return imageids;
}

function changeMsgType(text)
{
    var msgType = $("#msgtype").val();
    
    if(msgType == "text")
    {
		//消息文本
		$(".description").show();
	    //隐藏图文消息
		$(".weiXinMaterial").hide();
    }
    
    if(msgType == "image")
    {
	    //消息文本
		$(".description").hide();
	    //隐藏图文消息
		$(".weiXinMaterial").show();
    }
}

//全选
function checkAll(Obj)
{
	var collid = document.getElementById("ck_all");
    
    var coll = document.getElementsByName(Obj);
	if (collid.checked)
	{
		for(var i = 0; i < coll.length; i++)
	    	coll[i].checked = true;
	}
	else
	{
	    for(var i = 0; i < coll.length; i++)
	       coll[i].checked = false;
	}
}

//取消全选
function checkSingle(Obj)
{
	var collid = document.getElementById("ck_all");
    
    var coll = document.getElementsByName(Obj);
	if (collid.checked)
	{
		for(var i = 0; i < coll.length; i++)
		    if(coll[i].checked)
		    {
		    	collid.checked = false;
		    }
	}
}

$(document).ready(function(){
    // 触发上传图片
	$("#choseImg").on("click", function() {
		("#file").click();
	});
});

//点击上传图片
function ajaxFileUpload() 
{
    $("#file").show();
	if ($("#file").val() == "") {
		alert("请上传图片！");
		return;
	}

	$.ajaxFileUpload({
	    url: ctx+"/fileUpload?folderName=materialImage",
		secureuri : false,
		type : "POST",
		fileElementId : "file",
		dataType : "json",
		beforeSend : function() {
			
		},
		complete : function() {
			
		},
		success : function(data) {
			if (data && data.result == "SUCCESS") {
				var fileUrl = data.fileUrl;
				addImage(fileUrl);
			} else {
				alert("图片上传失败！");
			}
		},
		error : function(data, status, e) {

		}
	});
	return false;
}

//添加图片
function addImage(fileUrl)
{
    var d={};
    d["picurl"] = fileUrl;
    d["failtime"] = curentDate();
    d["materialid"] = 2;
    
    var flag = fileUrl.lastIndexOf("/");
    if(fileUrl != "" || fileUrl != null){
    	fileUrl = fileUrl.substring(flag+1);
    }
    
    d["title"] = fileUrl;
    $.ajax({
		url : ctx+'/weiXin/weiXinMaterialMessage/add',
		type : 'POST',
		async : false,
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		data : JSON.stringify(d)
	}).done(function(data) {
		//alert('添加成功');
		$('div').find('.page-content').load(ctx+'/weiXin/weiXinMaterial/imagesPreview',function(){
		 	//加一个遮罩在次
		});
	}).fail(function(data) {
		alert('添加失败');
	});
}

//删除图文消息
function delImage(del)
{
	window.confirm("确认删除选中信息?", function(_dialog){
		var r={};
		r.id=$(del).attr("materialid");

		$.ajax({
			url : ctx+'/weiXin/weiXinMaterialMessage/delete',
			type : 'POST',
			async : false,
			dataType : 'json',
			contentType : 'application/json;charset=UTF-8',
			data : JSON.stringify(r)
		}).done(function(data) {
			if (data.result == true) {
				$('div').find('.page-content').load(ctx+'/weiXin/weiXinMaterial/imagesPreview',function() {
					//加一个遮罩在次
				});
			} else {
				alert(data.result);
			}

			$(_dialog).dialog("close");
		}).fail(function(data) {
			alert('删除失败');
		});
	});
}

//删除选中的图片
function delCheckImages(del)
{
	var imageids = getCheckboxCheckedValue();
	window.confirm("确认删除选中信息?", function(_dialog){
		var rl=[];
		$(imageids).each(function(index){
			var r={};
			r.id=this;
			rl.push(r);
		});
		
		$.ajax({
			url : ctx+'/weiXin/weiXinMaterialMessage/deleteByIds',
			type : 'POST',
			async : false,
			dataType : 'json',
			contentType : 'application/json;charset=UTF-8',
			data : JSON.stringify(rl)
		}).done(function(data) {
			if (data.result == true) {
				$('div').find('.page-content').load(ctx+'/weiXin/weiXinMaterial/imagesPreview',function() {
					//加一个遮罩在次
				});
			} else {
				alert(data.result);
			}

			$(_dialog).dialog("close");
		}).fail(function(data) {
			alert('删除失败');
		});
	});
}

function curentDate()
{ 
    var now = new Date();
   
    var year = now.getFullYear() + 2;
    var month = now.getMonth() + 1;
    var day = now.getDate();
   
    var clock = year + "年";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "月";
   
    if(day < 10)
        clock += "0";
       
    clock += day + "日";
   
    return(clock); 
} 
