<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>短信消息管理</title>
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
		<li class="active"><a href="${ctx}/mq/shortMessage/">短信消息列表</a></li>
		<shiro:hasPermission name="mq:shortMessage:edit"><li><a href="${ctx}/mq/shortMessage/form">短信消息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shortMessage" action="${ctx}/mq/shortMessage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>发送者单位：</label>
				<form:input path="senderOfficeName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>发送者：</label>
				<form:input path="senderName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>接收者：</label>
				<form:input path="receiverNames" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>发送时间：</label>
				<input name="beginSendDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${sysLogEmail.beginSendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endSendDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${sysLogEmail.endSendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>发送者单位</th>
				<th>发送者</th>
				<th>接收者</th>
				<th>发送时间</th>
				<shiro:hasPermission name="mq:shortMessage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shortMessage">
			<tr>
				<td><a href="${ctx}/mq/shortMessage/form?id=${shortMessage.id}">
					${shortMessage.senderOfficeName}
				</a></td>
				<td>
					${shortMessage.senderName}
				</td>
				<td>
					${shortMessage.receiverNames}
				</td>
				<td>
					<fmt:formatDate value="${shortMessage.sendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="mq:shortMessage:edit"><td>
    				<a href="${ctx}/mq/shortMessage/form?id=${shortMessage.id}">修改</a>
					<a href="${ctx}/mq/shortMessage/delete?id=${shortMessage.id}" onclick="return confirmx('确认要删除该短信消息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>