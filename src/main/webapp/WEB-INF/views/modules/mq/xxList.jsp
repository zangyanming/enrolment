<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校管理</title>
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
		<li class="active"><a href="${ctx}/mq/xx/">学校列表</a></li>
		<shiro:hasPermission name="mq:xx:edit"><li><a href="${ctx}/mq/xx/form">学校添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="xx" action="${ctx}/mq/xx/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>学校名称：</label>
				<form:input path="xxmc" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>归属名称：</label>
				<form:input path="gsdwmc" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>校长：</label>
				<form:input path="xz" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>学校编码</th>
				<th>学校名称</th>
				<th>学校类别</th>
				<th>学校性质</th>
				<th>归属名称</th>
				<th>校长</th>
				<th>登记时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="mq:xx:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="xx">
			<tr>
				<td><a href="${ctx}/mq/xx/form?id=${xx.id}">
					${xx.xxbm}
				</a></td>
				<td>
					${xx.xxmc}
				</td>
				<td>
					${fns:getDictLabel(xx.xxlb, 'xxlb', '')}
				</td>
				<td>
					${fns:getDictLabel(xx.xxxz, 'xxxz', '')}
				</td>
				<td>
					${xx.gsdwmc}
				</td>
				<td>
					${xx.xz}
				</td>
				<td>
					<fmt:formatDate value="${xx.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${xx.updateDate}
				</td>
				<shiro:hasPermission name="mq:xx:edit"><td>
    				<a href="${ctx}/mq/xx/form?id=${xx.id}">修改</a>
					<a href="${ctx}/mq/xx/delete?id=${xx.id}" onclick="return confirmx('确认要删除该学校吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>