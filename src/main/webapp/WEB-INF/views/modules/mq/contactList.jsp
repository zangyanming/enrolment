<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>联系人管理</title>
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
		<li class="active"><a href="${ctx}/mq/contact/">联系人列表</a></li>
		<shiro:hasPermission name="mq:contact:edit"><li><a href="${ctx}/mq/contact/form">联系人添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="contact" action="${ctx}/mq/contact/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>群组ID：</label>
				<form:input path="groupId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>群组ID</th>
				<th>姓名</th>
				<th>QQ号码</th>
				<th>微信号码</th>
				<th>电子邮箱</th>
				<th>手机号码</th>
				<th>创建时间</th>
				<shiro:hasPermission name="mq:contact:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="contact">
			<tr>
				<td><a href="${ctx}/mq/contact/form?id=${contact.id}">
					${contact.groupId}
				</a></td>
				<td>
					${contact.name}
				</td>
				<td>
					${contact.qqhm}
				</td>
				<td>
					${contact.wxhm}
				</td>
				<td>
					${contact.email}
				</td>
				<td>
					${contact.tel}
				</td>
				<td>
					<fmt:formatDate value="${contact.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="mq:contact:edit"><td>
    				<a href="${ctx}/mq/contact/form?id=${contact.id}">修改</a>
					<a href="${ctx}/mq/contact/delete?id=${contact.id}" onclick="return confirmx('确认要删除该联系人吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>