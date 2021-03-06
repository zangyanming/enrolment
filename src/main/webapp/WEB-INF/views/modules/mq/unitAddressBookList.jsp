<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位通讯录管理</title>
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
		<li class="active"><a href="${ctx}/mq/unitAddressBook/">单位通讯录列表</a></li>
		<shiro:hasPermission name="mq:unitAddressBook:edit"><li><a href="${ctx}/mq/unitAddressBook/form">单位通讯录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="unitAddressBook" action="${ctx}/mq/unitAddressBook/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>单位名称：</label>
				<form:input path="dwbm" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>单位名称</th>
				<th>姓名</th>
				<th>手机号码</th>
				<th>QQ号码</th>
				<th>微信号码</th>
				<th>电子邮箱</th>
				<th>创建者</th>
				<shiro:hasPermission name="mq:unitAddressBook:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="unitAddressBook">
			<tr>
				<td><a href="${ctx}/mq/unitAddressBook/form?id=${unitAddressBook.id}">
					${unitAddressBook.dwbm}
				</a></td>
				<td>
					${unitAddressBook.name}
				</td>
				<td>
					${unitAddressBook.tel}
				</td>
				<td>
					${unitAddressBook.qqhm}
				</td>
				<td>
					${unitAddressBook.wxhm}
				</td>
				<td>
					${unitAddressBook.email}
				</td>
				<td>
					${unitAddressBook.createBy.id}
				</td>
				<shiro:hasPermission name="mq:unitAddressBook:edit"><td>
    				<a href="${ctx}/mq/unitAddressBook/form?id=${unitAddressBook.id}">修改</a>
					<a href="${ctx}/mq/unitAddressBook/delete?id=${unitAddressBook.id}" onclick="return confirmx('确认要删除该单位通讯录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>