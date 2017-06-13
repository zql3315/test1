//override dialog's title function to allow for HTML titles
$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
	_title: function(title) {
		var $title = this.options.title || '&nbsp;'
		if( ("title_html" in this.options) && this.options.title_html == true )
			title.html($title);
		else title.text($title);
	}
}));

Date.prototype.pattern = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, // 小时
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
		// 毫秒
	};
	var week = {
		"0" : "/u65e5",
		"1" : "/u4e00",
		"2" : "/u4e8c",
		"3" : "/u4e09",
		"4" : "/u56db",
		"5" : "/u4e94",
		"6" : "/u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1)
						? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468")
						: "")
						+ week[this.getDay() + ""]);
	}
	for (var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1)
							? (o[k])
							: (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};

String.prototype.stripHTML = function() {
    var reTag = /<(?:.|\s)*?>/g;
    return this.replace(reTag,"");
  }

//修改系统的alert风格
window._originalAlert = window.alert;
window.alert = function(text,callbak) {
	//初始化html
	var init=function(){
		var alertHTML='<div id="dialog-message" class="hide">'+
						'<div class="alert alert-info bigger-110">'+
							text+
						'</div>'+
					'</div>';
		if($('#dialog-message').length == 1){
			$('#dialog-message div.alert-info').text(text);
			return true;
		}
		
		 $('body').append(alertHTML);
		 
		 return true;
	};
	
	if (init()) {
		var dialog = $( "#dialog-message" ).removeClass('hide').dialog({
			modal: true,
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-exclamation-triangle green'></i>提示</h4></div>",
			title_html: true,
			buttons: [ 
				{
					text: "OK",
					"class" : "btn btn-success btn-xs",
					click: function() {
						if(callbak){
							callbak();
						}
						$( this ).dialog( "close" ); 
					} 
				}
			]
		});
	}else{
		console.log('bootstrap was not found');
        window._originalAlert(text);
	}
	
};

//二次确认框风格定制
window._originalConfirm = window.confirm;
window.confirm = function(text, cb) {
	//初始化html
	var init=function(){
		var confirmHTML='<div id="dialog-confirm" class="hide">'+
							'<div class="alert alert-warning bigger-110">'+
								text+
							'</div>'+
							'<div class="space-6"></div>'+
						 '</div>';
		if($('#dialog-confirm').length == 1){
			$('#dialog-confirm div.alert-warning').text(text);
			return true;
		}
		
		$('body').append(confirmHTML);
		 
		return true;
	};
	
	if (init()) {
		$( "#dialog-confirm" ).removeClass('hide').dialog({
			resizable: false,
			modal: true,
			title: "<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-exclamation-triangle red'></i> 是否操作? </h4></div>",
			title_html: true,
			buttons: [
				{
					html: "<i class='ace-icon fa fa-trash-o bigger-110'></i>&nbsp; 确定",
					"class" : "btn btn-danger btn-xs",
					click: function() {
						cb(this);
					}
				},
				{
					html: "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; 取消",
					"class" : "btn btn-xs",
					click: function() {
						$( this ).dialog( "close" );
					}
				}
			]
		});
	}else{
		if (window._originalConfirm(text)) {
			cb;
		}
	}
};

function showSuccess(fun,text,confirmText,cancelText) {
	if(!text){
		text = "操作成功,是否留在当前页面?";
	}
	if(!confirmText){
		confirmText = "确认";
	}
	if(!cancelText){
		cancelText = "取消";
	}
	
	//初始化html
	var init=function(){
		var confirmHTML='<div id="dialog-success" class="hide">'+
							'<div class="alert alert-info bigger-110">'+
								text+
							'</div>'+
							'<div class="space-6"></div>'+
						 '</div>';
		if($('#dialog-success').length == 1){
			$('#dialog-success div.alert-info').text(text);
			return true;
		}
		
		$('body').append(confirmHTML);
		 
		return true;
	};
	
	if (init()) {
		$( "#dialog-success" ).removeClass('hide').dialog({
			resizable: false,
			modal: true,
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-exclamation-triangle green'></i>提示</h4></div>",
			title_html: true,
			buttons: [
				{
					html: "<i class='ace-icon fa fa-trash-o bigger-110'></i>&nbsp; "+confirmText,
					"class" : "btn btn-success btn-xs",
					click: function() {
						$(this).dialog("close"); 
					}
				},
				{
					html: "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; "+cancelText,
					"class" : "btn btn-xs",
					click: function() {
						$( this ).dialog( "close" );
						if(fun) {
							fun(this);
						}
					}
				}
			]
		});
	}else{
		if (window._originalConfirm(text)) {
			if(fun) {
				fun();
			}
		}
	}
};


function upload(name, url) {
	
	if($("#" + name).val()  == ""){
		alert("请选择一个文件");
		return ;
	}
    $.ajaxFileUpload({
	    url: url,
	    secureuri: false,
	    type : 'POST',
	    fileElementId: name,
	    dataType: 'json',
	    success: function(data) {
	     	if(data && data.result == "SUCCESS"){
	     		var input = $('input[name="' + name + '"]');
	     		input.val(data.fileUrl);
	     		
	     		$(".ace-file-name", input.parent()).attr("data-title",data.fileUrl);
	     		$("#" + name + "view").attr("src", data.fileUrl).show();
	     	}else{
	     		console.log(data);
	     		alert("上传失败");
	     	}
	    }
	});
}

function changeFileName(id) {
	
	var fileInput = $("#" + id);
	var fileName = fileInput.val();
	if(!isEmpty(fileName)) {
		var parent = fileInput.parent();
		$(".ace-file-name", parent).attr("data-title", fileName.substr(fileName.lastIndexOf('\\') + 1));
		$("a", parent).attr("style", "display: inline;");
		$(".ace-file-container", parent).addClass("selected");
		$(".ace-file-container", parent).attr("data-title", "重新选择");
	} 
} 

function bindFileRemove(id) {
	$("a", $("#" + id).parent()).click(function(){
		if($(this).hasClass("remove")) {
			$(this).hide();
			$("input[name='" + id + "']").val("");
			$("#" + id + "view").removeAttr("src").hide();
		}
	});
}

/**
 * 以POST方式异步提交数据
 * @param url
 * @param data
 * @param fun
 */
function post(url, data, fun) {
	
	$.ajax({
		url : url,
		type : 'POST',
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		data : data
	}).done(function(data) {
		if(fun) {
			fun(data);
		}
	});
}

function initBtn(modelUrl, table) {
	$('#addBtn').on('click', function(){
		$("#addBtn").attr('disabled', 'disabled');
		setBreadcrumb(modelUrl + '/preview', $(this).text());
		openPage(modelUrl + '/toAdd');
	});
	
	$('#editBtn').on('click', function(){
		
		
		var tables = getSelectedIds();
		
		if(tables.length != 1) {
			alert("请选择一条记录");
		} else {
			$("#editBtn").attr('disabled', 'disabled');
			setBreadcrumb(modelUrl + '/preview', $(this).text());
			openPage(modelUrl + '/toEdit/'+ tables[0].id);
		}
	});
	
	$('#delBtn').on('click',function(){
		
		var tables= getSelectedIds();
		
		if(tables.length == 0) {
			alert("请选择一条记录");
			return;
		}
		
		data = JSON.stringify(tables);
		
		//console.log(data);

		window.confirm("是否确认删除此信息?",function(_dialog)
		{   
			post(modelUrl + "/deleteByIds", data, function(data)
			{
		
				if(data.result) {
					$( _dialog ).dialog( "close" );
					alert("操作成功");
				}
				
				if(table) {
					table.ajax.reload();
				}
			});
		});
	});
	
	$('#viewBtn').on('click', function(){

		var tables = getSelectedIds();
		if(tables.length != 1) {			
			alert("请选择一条记录");
		} else {
			$("#viewBtn").attr('disabled', 'disabled');
			setBreadcrumb(modelUrl + '/preview', $(this).text());
			openPage(modelUrl + '/view/'+tables[0].id);
		}
	});
	
	
}

function cancelBtn(modelUrl) {
	$('#cancel').on('click',function(){
		//console.log("11111");
		//setBreadcrumb(modelUrl + '/preview', $(this).text());
		backBreadcrumb();
		openPage(modelUrl + '/preview');
	});
}

function getSelectedIds() {
	var tables=[];
	$('table input:checkbox').each(function(){
		if ($(this).prop('checked')) {
			if (!isEmpty($(this).attr('id'))) {
				var table={};
				table.id=$(this).attr('id');
				tables.push(table);
			}
		}
	});
	
	return tables;
}

function setBreadcrumb(lasturl, text) {
	
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

/**
 * 导航回退
 */
function backBreadcrumb() {
	var breadcrumb_items = getBreadcrumb();
	breadcrumb_items.reverse();
	breadcrumb_items.pop();
	breadcrumb_items.reverse();
	refreshBreadcrumb(breadcrumb_items);
}

function openPage(url, fun) {
	$('div').find('.page-content').load(url, function(){
		 //加一个遮罩在次
		if(fun) {
			fun();
		}
	});
}

function loadSelectRemoteData(id, url, param) {
	
	$.post(url, function(data){
		
		var select = $("#" + id);
		
		var key = select.attr("datakey");
		
		if(!key) {
			key = "key";
		}
		
		var value = select.attr("datavalue");
		
		if(!value) {
			value = "value";
		}
		
		$.each(data, function(i, item) {
			select.append("<option value='" + item[key] + "'>" + item[value] + "</option> ");
		});
	});
}

function loadSelectData(id, data, value, notContainEmpty) {
	
	if(!data) {
		return;
	}
	
	var select = $("#" + id);
	
	if(!notContainEmpty) {
		select.append('<option value="">--请选择--</option>');
	}
	
	
	for(var key in data) {
		
		var option = "<option value='" + key + "'";
		
		if(value && value == key) {
			option += " selected='selected'";
		}
		
		option += ">" + data[key] + "</option>";
		
		select.append(option);
	}
}

function formateValue(id, map) {
	var element = $("#" + id);
	var value = element.text().trim();
	element.text(map[value]);
}

function formatDate(data){
	return new Date(data).pattern("yyyy-MM-dd");
}

function formatDatetime(data){
	return new Date(data).pattern("yyyy-MM-dd HH:mm:ss");
}

function isEmpty(value) {
	
	if(typeof(value) ==  "undefined" || value == '' || $.isEmptyObject(value)) {
		return true;
	}
	
	return false;
}
