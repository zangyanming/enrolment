<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
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
		<li class="active"><a href="${ctx}/mq/xs/">学生列表</a></li>
		<shiro:hasPermission name="mq:xs:edit"><li><a href="${ctx}/mq/xs/form">学生添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="xs" action="${ctx}/mq/xs/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="xm" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>性别</th>
				<th>学生证号</th>
				<th>手机号码</th>
				<th>微信号码</th>
				<th>电子邮箱</th>
				<th>QQ号码</th>
				<th>微博号码</th>
				<th>登记时间</th>
				<shiro:hasPermission name="mq:xs:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="xs">
			<tr>
				<td><a href="${ctx}/mq/xs/form?id=${xs.id}">
					${xs.xm}
				</a></td>
				<td>
					${xs.xb}
				</td>
				<td>
					${xs.xszh}
				</td>
				<td>
					${xs.sjhm}
				</td>
				<td>
					${xs.wxhm}
				</td>
				<td>
					${xs.email}
				</td>
				<td>
					${xs.qqhm}
				</td>
				<td>
					${xs.wbhm}
				</td>
				<td>
					<fmt:formatDate value="${xs.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="mq:xs:edit"><td>
    				<a href="${ctx}/mq/xs/form?id=${xs.id}">修改</a>
					<a href="${ctx}/mq/xs/delete?id=${xs.id}" onclick="return confirmx('确认要删除该学生吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>