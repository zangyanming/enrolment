<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>家长通讯录管理</title>
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
		<li class="active"><a href="${ctx}/mq/parentAddressBook/">家长通讯录列表</a></li>
		<shiro:hasPermission name="mq:parentAddressBook:edit"><li><a href="${ctx}/mq/parentAddressBook/form">家长通讯录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="parentAddressBook" action="${ctx}/mq/parentAddressBook/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
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
				<th>姓名</th>
				<th>QQ号码</th>
				<th>微信号码</th>
				<th>电子邮箱</th>
				<th>手机号码</th>
				<th>创建时间</th>
				<th>创建者</th>
				<shiro:hasPermission name="mq:parentAddressBook:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="parentAddressBook">
			<tr>
				<td><a href="${ctx}/mq/parentAddressBook/form?id=${parentAddressBook.id}">
					${parentAddressBook.name}
				</a></td>
				<td>
					${parentAddressBook.qqhm}
				</td>
				<td>
					${parentAddressBook.wxhm}
				</td>
				<td>
					${parentAddressBook.email}
				</td>
				<td>
					${parentAddressBook.tel}
				</td>
				<td>
					<fmt:formatDate value="${parentAddressBook.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${parentAddressBook.createBy.id}
				</td>
				<shiro:hasPermission name="mq:parentAddressBook:edit"><td>
    				<a href="${ctx}/mq/parentAddressBook/form?id=${parentAddressBook.id}">修改</a>
					<a href="${ctx}/mq/parentAddressBook/delete?id=${parentAddressBook.id}" onclick="return confirmx('确认要删除该家长通讯录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>