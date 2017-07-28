<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑微信公众账号</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<i class="ace-icon fa fa-pencil-square-o"></i>微信公众账号配置
			</h3>
				
			<form role="form" class="form-horizontal" method="POST" id="weiXinPublicAccount">
				<div class="form-info form-info-striped">
					   <div class="form-info-row">
							<div class="form-info-name">商家id</div>
							<div class="form-info-value">
							    <input type="hidden" placeholder="id" name="id" value="${model.id}" class="col-xs-10 col-sm-5">
								<input type="text"  placeholder="商家id" name="userid" value="${model.userid}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">公共账号昵称 </div>
							<div class="form-info-value">
								<input type="text"  placeholder="公共账号昵称" name="accountname" value="${model.accountname}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">公共账号微信id</div>
							<div class="form-info-value">
								<input type="text"  placeholder="公共账号微信id" name="accountwxid" value="${model.accountwxid}" class="col-xs-10 col-sm-5">
							</div>
						</div>
					<%-- 	<div class="form-info-row">
							<div class="form-info-name">绑定默认回复关键字</div>
							<div class="form-info-value">
								<input type="text"  placeholder="绑定默认回复关键字" name="defaultreplykeyword" value="${model.defaultreplykeyword}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">绑定首次关注关键字</div>
							<div class="form-info-value">
								<input type="text"  placeholder="绑定首次关注关键字" name="firstreplykeyword" value="${model.firstreplykeyword}" class="col-xs-10 col-sm-5">
							</div>
						</div> --%>
						<div class="form-info-row">
							<div class="form-info-name">AppID(应用ID)</div>
							<div class="form-info-value">
								<input type="text"  placeholder="公共账号开发者模式下，第三方用户唯一凭证" name="appid" value="${model.appid}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">AppSecret(应用密钥)</div>
							<div class="form-info-value">
								<input type="text"  placeholder="公共账号开发者模式下，第三方用户唯一凭证密钥，即appsecret" name="appsecret" value="${model.appsecret}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">Token(令牌)</div>
							<div class="form-info-value">
								<input type="text"  placeholder="验证开发者模式下服务标识参数" name="token" value="${model.token}" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">获取access token</div>
							<div class="form-info-value">
								<input type="text"  placeholder="公众号的全局唯一票据" id="accesstoken" name="accesstoken" value="${model.accesstoken}" class="col-xs-10 col-sm-5" readonly="readonly">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">access token创建时间</div>
							<div class="form-info-value">
								<input type="text"  placeholder="access token创建时间" id="accesstokencreatetime" name="accesstokencreatetime" value="${model.accesstokencreatetime}" class="col-xs-10 col-sm-5" readonly="readonly">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">access token凭证有效时间(秒)</div>
							<div class="form-info-value">
								<input type="text"  placeholder="access token凭证有效时间" id="accesstokenexpiresin" name="accesstokenexpiresin" value="${model.accesstokenexpiresin}" class="col-xs-10 col-sm-5" readonly="readonly">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">公众号用于调用微信JS接口的临时票据</div>
							<div class="form-info-value">
								<input type="text"  placeholder="公众号用于调用微信JS接口的临时票据" id="ticket" name="ticket" value="${model.ticket}" readonly="readonly" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">jsapi_ticket创建时间</div>
							<div class="form-info-value">
								<input type="text"  placeholder="jsapi_ticket创建时间" id="ticketcreatetime" name="ticketcreatetime" value="${model.ticketcreatetime}" readonly="readonly" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">jsapi_ticket凭证有效时间(秒)</div>
							<div class="form-info-value">
								<input type="text"  placeholder="jsapi_ticket凭证有效时间" id="ticketexpiresin" name="ticketexpiresin" value="${model.ticketexpiresin}" readonly="readonly" class="col-xs-10 col-sm-5"> 
							</div>
						</div>
						<div class="form-info-row">
							<div class="form-info-name">公众账号配置时间</div>
							<div class="form-info-value">
								<input type="text"  placeholder="公众账号配置时间" name="createtime" value="${model.createtime}" readonly="readonly" class="col-xs-10 col-sm-5">
							</div>
						</div>
				</div>
				
				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9" style="margin-left: 20%;">
						<button class="btn btn-info" type="button" id="accessToken">
							<i class="ace-icon fa smaller-90"></i>重新获取AccessToken
						</button>
	
						&nbsp; &nbsp; &nbsp;
						<button class="btn btn-info" type="submit">
							<i class="ace-icon fa fa-check smaller-90"></i>提交
						</button>
	
						&nbsp; &nbsp; &nbsp;
						<button class="btn" type="button" id="cancel">
							<i class="ace-icon fa fa-undo smaller-90"></i>取消
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- validate -->
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#weiXinPublicAccount').validate({
				rules:{
						userid:{required: true},
						appid:{required: true}, 
						appsecret:{required: true},
					 	accountname:{required: true}, 
						accountwxid:{required: true},
						defaultreplykeyword:{required: true}, 
						firstreplykeyword:{required: true}, 
						token:{required: true}
				},
				messages:{
						userid:{required: '请输入商家id'},
						appid:{required: '请输入第三方用户唯一凭证'}, 
						appsecret:{required: '请输入第三方用户唯一凭证密钥'},
						accountname:{required: '请输入公共账号昵称'}, 
						accountwxid:{required: '请输入公共账号微信id'},
						defaultreplykeyword:{required: '请输入绑定默认回复关键字'}, 
						firstreplykeyword:{required: '请输入绑定首次关注关键字'}, 
						token:{required: '请输入验证开发者模式下服务标识参数'}
				},
				submitHandler:function(form){
					var formData = $(form).serializeArray();
					if ($.isArray(formData)) {
						var d={};
						$.each(formData, function(i, item) {
							if (item.name && item.value) {
								d[item.name] = item.value;
							}
						});
						
						$.ajax({
							url : '${ctx}/weiXin/weiXinPublicAccount/edit',
							type : 'POST',
							async : false,
							dataType : 'json',
							contentType : 'application/json;charset=UTF-8',
							data : JSON.stringify(d)
						}).done(function(data) {
							alert('编辑成功');
							//window.location.reload();//刷新当前页面
						}).fail(function(data) {
							alert('编辑失败');
						});
					}
				}
			});
			
			$('#accessToken').on('click',function(){
				$.ajax({
					url : '${ctx}/weiXin/weiXinPublicAccount/getAccessToken',
					type : 'POST',
					async : false,
					dataType : 'json',
					contentType : 'application/json;charset=UTF-8',
					data : null
				}).done(function(data) {
				    console.log(data.access_token);
				    $("#accesstoken").val(data.access_token);
				    $("#accesstokenexpiresin").val(data.access_token_expires_in);
				    $("#accesstokencreatetime").val(data.access_token_createtime);
				    $("#ticket").val(data.jsapi_ticket);
				    $("#ticketexpiresin").val(data.jsapi_ticket_expires_in);
				    $("#ticketcreatetime").val(data.jsTicket_createtime);
				}).fail(function(data) {
					alert('accesstoken获取失败!');
				});
	    	});
		});
	</script>
	<script type="text/javascript">
		$('#cancel').on('click',function(){
			window.location.reload();//刷新当前页面
	    });
	</script>
</body>
</html>