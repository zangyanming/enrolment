<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>群组管理</title>
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
		<li class="active"><a href="${ctx}/mq/group/">群组列表</a></li>
		<shiro:hasPermission name="mq:group:edit"><li><a href="${ctx}/mq/group/form">群组添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="group" action="${ctx}/mq/group/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>群组名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>创建者：</label>
				<form:input path="createBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>群组名称</th>
				<th>创建时间</th>
				<th>创建者</th>
				<shiro:hasPermission name="mq:group:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="group">
			<tr>
				<td><a href="${ctx}/mq/group/form?id=${group.id}">
					${group.name}
				</a></td>
				<td>
					<fmt:formatDate value="${group.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${group.createBy.id}
				</td>
				<shiro:hasPermission name="mq:group:edit"><td>
    				<a href="${ctx}/mq/group/form?id=${group.id}">修改</a>
					<a href="${ctx}/mq/group/delete?id=${group.id}" onclick="return confirmx('确认要删除该群组吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>