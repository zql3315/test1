$(function() {
	$("#datepicker").datepicker();
	
	$.ajaxSetup ({
	    cache: false, //关闭AJAX相应的缓存
	    global: false,
	    type: "POST",
	    complete: function (XMLHttpRequest, textStatus) {
	    	var repTest = XMLHttpRequest.getResponseHeader("result");
	    	if(repTest && repTest == "sessionInvalidation"){
	    		alert("请求超时！请重新登录！",function(){
	    			window.top.location = basePath+"login";
	    		});
	    	}else if(repTest && repTest == "kickoutsession"){
    	    	window.alert("您已在另一个客户端登陆！",function(){
    	    		window.top.location = basePath+"login";
    	    	});
	    	}else if(repTest!=null && repTest == "nopermission"){
	    		window.alert("未授权联系管理员！",function(){
	    			
	    		});
	    	}
	    }
	});
});
var meter1 ;
function checkUserSession(){  
	$.ajax({
		   type: "POST",
		   url: basePath+"checkUserSession",
		   success: function(msg){
		     if(msg == "SUCCESS")
		    	 meter1 = setTimeout("checkUserSession()",1000*60*30);  
		     else
		    	 clearTimeout(meter1);
		   }
		});
}
checkUserSession();

jQuery(function($) {
	$.datepicker.regional['zh-CN'] = {
		closeText : '关闭',
		prevText : '<上月',
		nextText : '下月>',
		currentText : '今天',
		monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月',
				'十月', '十一月', '十二月' ],
		monthNamesShort : [ '一', '二', '三', '四', '五', '六', '七', '八', '九', '十',
				'十一', '十二' ],
		dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ],
		dayNamesShort : [ '周日', '周一', '周二', '周三', '周四', '周五', '周六' ],
		dayNamesMin : [ '日', '一', '二', '三', '四', '五', '六' ],
		weekHeader : '周',
		dateFormat : 'yy-mm-dd',
		firstDay : 1,
		isRTL : false,
		showMonthAfterYear : true,
		yearSuffix : '年'
	};
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
});
jQuery.validator.addMethod("lettleLimitValidator", function(value, element) {
	var reg = /^[a-zA-Z]{4,20}$/;
    return reg.test(value);   
  }, "请输入4-20位的大小写字母");

jQuery.validator.addMethod("lettleNumberValidator", function(value, element) {
	var reg = /^[a-zA-Z][\w]{3,19}$/;
    return reg.test(value);   
  }, "请输入正确的字母或者数字.必须以字母开头，4-20位");


jQuery.validator.addMethod("telephoneValidator", function(value, element) {
		if(value == ''){
			return true;
		}	
	
		var reg = /^1[3|4|5|7|8]\d{9}$/;
		var regTel = /^0[\d]{2,3}-[\d]{7,8}$/;
		
		var mflag = reg.test(value);
		var tflag = regTel.test(value);
		
	    return (mflag||tflag);
	  }, "请输入正确的手机号码或座机号码."); 

