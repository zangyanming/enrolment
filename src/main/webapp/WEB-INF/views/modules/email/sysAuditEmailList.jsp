<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>邮件消息审核信息管理</title>
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
		<li class="active"><a href="${ctx}/email/sysAuditEmail/">邮件消息审核信息列表</a></li>
		<shiro:hasPermission name="email:sysAuditEmail:edit"><li><a href="${ctx}/email/sysAuditEmail/form">邮件消息审核信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysAuditEmail" action="${ctx}/email/sysAuditEmail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>邮件主题：</label>
				<form:input path="subject" htmlEscape="false" class="input-medium"/>
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
				<th>邮件发送者</th>
				<th>邮件接收者ids</th>
				<th>邮件接收者</th>
				<th>邮件主题</th>
				<th>邮件内容</th>
				<th>附件名称</th>
				<th>创建者</th>
				<th>审核者</th>
				<th>审核时间</th>
				<th>审核状态</th>
				<shiro:hasPermission name="email:sysAuditEmail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysAuditEmail">
			<tr>
				<td><a href="${ctx}/email/sysAuditEmail/form?id=${sysAuditEmail.id}">
					${sysAuditEmail.sendBy}
				</a></td>
				<td>
					${sysAuditEmail.receiverIds}
				</td>
				<td>
					${sysAuditEmail.receiverEmails}
				</td>
				<td>
					${sysAuditEmail.subject}
				</td>
				<td>
					${sysAuditEmail.content}
				</td>
				<td>
					${sysAuditEmail.attachName}
				</td>
				<td>
					${sysAuditEmail.createBy.id}
				</td>
				<td>
					${sysAuditEmail.auditBy}
				</td>
				<td>
					<fmt:formatDate value="${sysAuditEmail.auditDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sysAuditEmail.auditStatus}
				</td>
				<shiro:hasPermission name="email:sysAuditEmail:edit"><td>
    				<a href="${ctx}/email/sysAuditEmail/form?id=${sysAuditEmail.id}">修改</a>
					<a href="${ctx}/email/sysAuditEmail/delete?id=${sysAuditEmail.id}" onclick="return confirmx('确认要删除该邮件消息审核信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>