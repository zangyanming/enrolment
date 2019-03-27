<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信消息审核信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/wx/sysAuditWx/">微信消息审核信息列表</a></li>
		<shiro:hasPermission name="wx:sysAuditWx:edit"><li><a href="${ctx}/wx/sysAuditWx/form">微信消息审核信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysAuditWx" action="${ctx}/wx/sysAuditWx/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>微信发送部门id：</label>
				<form:input path="senderOfficeId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>微信发送者id：</label>
				<form:input path="senderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>审核状态：</label>
				<form:input path="auditStatus" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>微信发送部门id</th>
				<th>微信发送者id</th>
				<th>微信接收者ids</th>
				<th>微信接收者openids</th>
				<th>微信消息内容</th>
				<th>审核状态</th>
				<shiro:hasPermission name="wx:sysAuditWx:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysAuditWx">
			<tr>
				<td><a href="${ctx}/wx/sysAuditWx/form?id=${sysAuditWx.id}">
					${sysAuditWx.senderOfficeId}
				</a></td>
				<td>
					${sysAuditWx.senderId}
				</td>
				<td>
					${sysAuditWx.receiverIds}
				</td>
				<td>
					${sysAuditWx.receiverOpenids}
				</td>
				<td>
					${sysAuditWx.content}
				</td>
				<td>
					${sysAuditWx.auditStatus}
				</td>
				<shiro:hasPermission name="wx:sysAuditWx:edit"><td>
    				<a href="${ctx}/wx/sysAuditWx/form?id=${sysAuditWx.id}">修改</a>
					<a href="${ctx}/wx/sysAuditWx/delete?id=${sysAuditWx.id}" onclick="return confirmx('确认要删除该微信消息审核信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>