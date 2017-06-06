<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<div class="page-header">
	<h1>
		Tables <small> <i class="ace-icon fa fa-angle-double-right"></i> Online </small>
	</h1>
</div>

<table id="sample-table-1" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>CookieId</th>
			<th><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>最后登陆时间</th>
			<th>登陆IP</th>
			<th>登陆用户</th>
			<th><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>最后操作日期</th>
			<th>操作</th>
		</tr>
	</thead>

	<tbody>
		<c:forEach items="${sessionList }" var="_sessionUser">
			<tr id="${_sessionUser.id}">
				<td>${_sessionUser.id}</td>
				<td>${_sessionUser.startTimestamp}</td>
				<td>${_sessionUser.host}</td>
				<td>${_sessionUser.username}</td>
				<td>${_sessionUser.lastAccessTime}</td>
				<td>
					<div class="hidden-sm hidden-xs btn-group">
						<button class="btn btn-xs btn-danger" title="强制下线" onclick="offline('${_sessionUser.id}')">
							<i class="ace-icon fa fa-trash-o bigger-120"></i>
						</button>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
    
<script type="text/javascript">

   function offline(id){
	   $.ajax({
           url : "${ctx}/user/offline",
           type : "POST",
           data : "sessionid="+id
       }).done(function(data) {
           if(data){
               alert("成功");
               $("#"+domid).remove();
           }else{
        	   alert("失败");
           }
       }).fail(function(data) {
           alert("失败");
       });
   }
</script>
