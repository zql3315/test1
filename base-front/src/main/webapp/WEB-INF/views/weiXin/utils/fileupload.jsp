<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>上传头像</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
	<input type="button" id="touxiang" value="上传头像" />
	<p>
		<img id="img1" alt="上传成功啦" src="" width="300" height="300" />
	</p>
	<script type="text/javascript">
		$.post("${ctx}/share/getJsapiSign", {"url" : url}, function(data) {
			//通过config接口注入权限验证配置
			wx.config({
				debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				appId : data.appid, // 必填，公众号的唯一标识
				timestamp : data.timestamp, // 必填，生成签名的时间戳
				nonceStr : data.nonceStr, // 必填，生成签名的随机串
				signature : data.signature,// 必填，签名，见附录1
				jsApiList : [ 'chooseImage', 'uploadImage' ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			});

		});

		$("#touxiang").click(function() {
			wx.chooseImage({
				count : 1, // 默认9
				sizeType : [ 'original', 'compressed' ], // 可以指定是原图还是压缩图，默认二者都有
				sourceType : [ 'album', 'camera' ], // 可以指定来源是相册还是相机，默认二者都有
				success : function(res) {
					var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
					uploadImage(localIds[0]);
				}
			});

		});

		function uploadImage(localId) {
			wx.uploadImage({
				localId : localId, // 需要上传的图片的本地ID，由chooseImage接口获得
				isShowProgressTips : 1, // 默认为1，显示进度提示
				success : function(res) {
					var serverId = res.serverId; // 返回图片的服务器端ID
					$.ajax({
						type : "POST",
						url : "${ctx}/wxImageUpload/savehead",
						data : "serverId=" + serverId,
						success : function(data) {
							$("#img1").attr("src", localId);
						},
						error : function(XMLHttpRequest, textStatus,errorThrown) {
							alert(errorThrown);
						}
					});
				}
			});
		}
	</script>
</body>
</html>