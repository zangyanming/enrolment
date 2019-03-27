<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用系统消息审核信息管理</title>
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
		<li class="active"><a href="${ctx}/mq/sysAuditSysmsg/">应用系统消息审核信息列表</a></li>
		<shiro:hasPermission name="mq:sysAuditSysmsg:edit"><li><a href="${ctx}/mq/sysAuditSysmsg/form">应用系统消息审核信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysAuditSysmsg" action="${ctx}/mq/sysAuditSysmsg/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>请求系统名称：</label>
				<form:input path="sendName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>请求系统编码：</label>
				<form:input path="sendCode" htmlEscape="false" maxlength="100" class="input-medium"/>
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
				<th>请求系统名称</th>
				<th>请求系统编码</th>
				<th>请求系统密钥</th>
				<th>目标系统ids</th>
				<th>目标系统名称</th>
				<th>目标系统编码</th>
				<th>消息内容</th>
				<th>审核者</th>
				<th>审核时间</th>
				<th>审核状态</th>
				<shiro:hasPermission name="mq:sysAuditSysmsg:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysAuditSysmsg">
			<tr>
				<td><a href="${ctx}/mq/sysAuditSysmsg/form?id=${sysAuditSysmsg.id}">
					${sysAuditSysmsg.sendName}
				</a></td>
				<td>
					${sysAuditSysmsg.sendCode}
				</td>
				<td>
					${sysAuditSysmsg.sendKey}
				</td>
				<td>
					${sysAuditSysmsg.receiverIds}
				</td>
				<td>
					${sysAuditSysmsg.receiverNames}
				</td>
				<td>
					${sysAuditSysmsg.receiverCodes}
				</td>
				<td>
					${sysAuditSysmsg.content}
				</td>
				<td>
					${sysAuditSysmsg.auditBy}
				</td>
				<td>
					<fmt:formatDate value="${sysAuditSysmsg.auditDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sysAuditSysmsg.auditStatus}
				</td>
				<shiro:hasPermission name="mq:sysAuditSysmsg:edit"><td>
    				<a href="${ctx}/mq/sysAuditSysmsg/form?id=${sysAuditSysmsg.id}">修改</a>
					<a href="${ctx}/mq/sysAuditSysmsg/delete?id=${sysAuditSysmsg.id}" onclick="return confirmx('确认要删除该应用系统消息审核信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>