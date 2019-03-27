<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>邮件日志管理</title>
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
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/email/sysLogEmail/">邮件日志列表</a></li>
		<shiro:hasPermission name="email:sysLogEmail:edit"><li><a href="${ctx}/email/sysLogEmail/form">邮件日志添加</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="sysLogEmail" action="${ctx}/email/sysLogEmail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>发送时间：</label>
				<input name="beginSendDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${sysLogEmail.beginSendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endSendDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${sysLogEmail.endSendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>消息状态：</label>
				<%--<form:input path="success" htmlEscape="false" maxlength="1" class="input-medium"/>--%>
				<form:radiobutton path="success" value="0"/>失败
				<form:radiobutton path="success" value="1"/>成功
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>日志标题</th>
				<th>邮件发送者</th>
				<th>邮件接收者</th>
				<th>发送时间</th>
				<th>消息状态</th>
				<th>操作IP地址</th>
				<th>操作提交的数据</th>
				<th>异常信息</th>
				<shiro:hasPermission name="email:sysLogEmail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysLogEmail">
			<tr>
				<td><a href="${ctx}/email/sysLogEmail/form?id=${sysLogEmail.id}">
					${sysLogEmail.title}
				</a></td>
				<td>
					${sysLogEmail.sendBy}
				</td>
				<td>
					${sysLogEmail.receiveBy}
				</td>
				<td>
					<fmt:formatDate value="${sysLogEmail.sendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:if test="${sysLogEmail.success eq '0'}">失败</c:if>
					<c:if test="${sysLogEmail.success eq '1'}">成功</c:if>
				</td>
				<td>
					${sysLogEmail.remoteAddr}
				</td>
				<td>
					<c:if test="${fn:length(sysLogEmail.params) <= 40}">
						${sysLogEmail.params}
					</c:if>
					<c:if test="${fn:length(sysLogEmail.params) > 40}">
						${fn:substring(sysLogEmail.params, 0, 40)}......
					</c:if>
				</td>
				<td>
					<c:if test="${fn:length(sysLogEmail.exception) <= 30}">
						${sysLogEmail.exception}
					</c:if>
					<c:if test="${fn:length(sysLogEmail.exception) > 30}">
						${fn:substring(sysLogEmail.exception, 0, 30)}......
					</c:if>
				</td>
				<shiro:hasPermission name="email:sysLogEmail:edit"><td>
    				<a href="${ctx}/email/sysLogEmail/form?id=${sysLogEmail.id}">修改</a>
					<a href="${ctx}/email/sysLogEmail/delete?id=${sysLogEmail.id}" onclick="return confirmx('确认要删除该邮件日志吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>