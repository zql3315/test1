<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="../common/base.jsp"%>
<!doctype html>
<html>
<head>
<title>支付测试</title>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, height=device-height" />
<script type="text/javascript" src="static/jquery/js/jquery.min.js"></script>
<script type="text/javascript" src="static/layer/layer.js"></script>
</head>

<body>
	<center>
		<button value="10元">10元</button>
		<button value="20元">20元</button>
		<button value="30元">30元</button>
		<button value="100元">100元</button>
		<br>

		<button onclick="paytest();">立即充值</button>
		<p>======${openid }--------</p>
		<p>timeStamp=${timeStamp}&nonceStr=${nonceStr}</p>
		<script type="text/javascript">
   	
   function paytest(){
	   var ua = navigator.userAgent.toLowerCase();
		if (ua.match(/MicroMessenger/i)  == "micromessenger") {//判断是否微信浏览器
			ua = ua.toLowerCase();
			var version =ua.substring(ua.indexOf("micromessenger/")+15);
			version = version.substring(0, version.indexOf("."));
			if(version > 5 ){
				layer.alert("版本符合，进入下一步",12,!1);
				getPrepayId();
			}else{
				layer.alert("请升级到微信5.0以上版本",12,!1);
			}
		}else{
			layer.alert("请用微信浏览器打开",12,!1);
		}
   }
   <%--  获取预支付id --%>
   function getPrepayId(){
	   layer.alert("获取预支付id",12,!1);
	   var d = new Date();
	   $.ajax({
		   url:"${basePath}/wechat/pay/getUniFiedorder",
		   data:"openid=${openid}&body=支付测试&price=1&out_trade_no="+d.getTime(),
		   cache: false,
		   success:function(msg){
			   console.log(msg);
				if(msg && msg.prepay_id){
					layer.alert("预支付id:"+msg.prepay_id,12,!1);
					 getPaySign(msg.prepay_id);
				}else{
					 layer.alert(msg.errorMsg,5,!1);
				}
		   },
		   error:function(e){
			   layer.alert("系统繁忙："+e,5,!1);
		   }
	   });
   }
   <%--  获取支付签名 --%>
   function getPaySign(prepay_id){
	   layer. alert("获取支付签名",12,!1);
	   $.ajax({
		   url:"${basePath}/wechat/pay/paySign",
		   data:"prepay_id="+prepay_id+"&timeStamp=${timeStamp}&nonceStr=${nonceStr}",
		   cache: false,
		   success:function(msg){
			   console.log(msg);
				if(msg && msg.paySign){
					layer.alert("加密签名:"+msg.paySign,12,!1);
					PayRequest(msg.paySign,prepay_id);
				}else{
					 layer.alert(msg.errorMsg,5,!1);
				}
		   },
		   error:function(e){
			   layer.alert("系统繁忙："+e,5,!1);
		   }
	   });
   }
   <%--   开始微信支付--%>
   function PayRequest(paySign,packages){
	   layer.alert("开始微信支付",5,!1);
	   WeixinJSBridge.invoke("getBrandWCPayRequest", {
			"appId" : "${appId}",
			"timeStamp" : "${timeStamp}",
			"nonceStr" : "${nonceStr}",
			"package" : "prepay_id="+packages,
			"signType" : "${signType}",
			"paySign" : ""+paySign
			}, function(res) {
				if (res.err_msg == "get_brand_wcpay_request:ok") { <%-- 支付成功 --%>
				 	layer.alert("支付成功",12,!1);
				 	<%--	     使用以上方式判断前端返回 , 微信团队郑重提示： res.err_msg 将在用户支付成功后 返 回 ok  但并不保证它绝对可靠。--%>
				}else if(res.err_msg == "get_brand_wcpay_request:cancel"){<%--支付过程中用户取消--%>
				 	layer.alert("已取消支付",5,!1);
				}else if(res.err_msg == "get_brand_wcpay_request:fail"){<%--支付失败--%>
				 	layer.alert("支付失败",5,!1);
				}else{
					 layer.alert(res.err_msg);
				}
			});
   }
   
   </script>
	</center>
</body>
</html>
