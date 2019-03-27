<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班级管理</title>
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
		<li class="active"><a href="${ctx}/mq/bj/">班级列表</a></li>
		<shiro:hasPermission name="mq:bj:edit"><li><a href="${ctx}/mq/bj/form">班级添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bj" action="${ctx}/mq/bj/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>学校代码：</label>
				<form:input path="xxbm" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>学校名称：</label>
				<form:input path="xxmc" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>年级名称：</label>
				<form:input path="njname" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>班级名称：</label>
				<form:input path="bjname" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>班主任：</label>
				<form:input path="master" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>学校代码</th>
				<th>学校名称</th>
				<th>年级名称</th>
				<th>班级名称</th>
				<th>班主任</th>
				<th>等级时间</th>
				<th>登记人</th>
				<th>更新时间</th>
				<shiro:hasPermission name="mq:bj:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bj">
			<tr>
				<td><a href="${ctx}/mq/bj/form?id=${bj.id}">
					${bj.xxbm}
				</a></td>
				<td>
					${bj.xxmc}
				</td>
				<td>
					${bj.njname}
				</td>
				<td>
					${bj.bjname}
				</td>
				<td>
					${bj.master}
				</td>
				<td>
					<fmt:formatDate value="${bj.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${bj.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${bj.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="mq:bj:edit"><td>
    				<a href="${ctx}/mq/bj/form?id=${bj.id}">修改</a>
					<a href="${ctx}/mq/bj/delete?id=${bj.id}" onclick="return confirmx('确认要删除该班级吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>