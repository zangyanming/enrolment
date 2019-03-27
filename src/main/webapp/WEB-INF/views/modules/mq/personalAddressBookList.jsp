<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人通讯录管理</title>
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
		<li class="active"><a href="${ctx}/mq/personalAddressBook/">个人通讯录列表</a></li>
		<shiro:hasPermission name="mq:personalAddressBook:edit"><li><a href="${ctx}/mq/personalAddressBook/form">个人通讯录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="personalAddressBook" action="${ctx}/mq/personalAddressBook/" method="post" class="breadcrumb form-search">
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
				<th>创建者</th>
				<shiro:hasPermission name="mq:personalAddressBook:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personalAddressBook">
			<tr>
				<td><a href="${ctx}/mq/personalAddressBook/form?id=${personalAddressBook.id}">
					${personalAddressBook.name}
				</a></td>
				<td>
					${personalAddressBook.qqhm}
				</td>
				<td>
					${personalAddressBook.wxhm}
				</td>
				<td>
					${personalAddressBook.email}
				</td>
				<td>
					${personalAddressBook.tel}
				</td>
				<td>
					${personalAddressBook.createBy.id}
				</td>
				<shiro:hasPermission name="mq:personalAddressBook:edit"><td>
    				<a href="${ctx}/mq/personalAddressBook/form?id=${personalAddressBook.id}">修改</a>
					<a href="${ctx}/mq/personalAddressBook/delete?id=${personalAddressBook.id}" onclick="return confirmx('确认要删除该个人通讯录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>