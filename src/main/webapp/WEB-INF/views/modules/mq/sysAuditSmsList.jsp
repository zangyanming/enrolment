<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>短信消息审核信息管理</title>
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
		<li class="active"><a href="${ctx}/mq/sysAuditSms/">短信消息审核信息列表</a></li>
		<shiro:hasPermission name="mq:sysAuditSms:edit"><li><a href="${ctx}/mq/sysAuditSms/form">短信消息审核信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysAuditSms" action="${ctx}/mq/sysAuditSms/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>短信发送部门id：</label>
				<form:input path="senderOfficeId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>短信发送者id：</label>
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
				<th>短信发送部门id</th>
				<th>短信发送者id</th>
				<th>短信接收者ids</th>
				<th>短信接收者姓名</th>
				<th>短信接收者号码</th>
				<th>短信内容</th>
				<th>审核状态</th>
				<shiro:hasPermission name="mq:sysAuditSms:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysAuditSms">
			<tr>
				<td><a href="${ctx}/mq/sysAuditSms/form?id=${sysAuditSms.id}">
					${sysAuditSms.senderOfficeId}
				</a></td>
				<td>
					${sysAuditSms.senderId}
				</td>
				<td>
					${sysAuditSms.receiverIds}
				</td>
				<td>
					${sysAuditSms.receiverNames}
				</td>
				<td>
					${sysAuditSms.receiverPhones}
				</td>
				<td>
					${sysAuditSms.content}
				</td>
				<td>
					${sysAuditSms.auditStatus}
				</td>
				<shiro:hasPermission name="mq:sysAuditSms:edit"><td>
    				<a href="${ctx}/mq/sysAuditSms/form?id=${sysAuditSms.id}">修改</a>
					<a href="${ctx}/mq/sysAuditSms/delete?id=${sysAuditSms.id}" onclick="return confirmx('确认要删除该短信消息审核信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>