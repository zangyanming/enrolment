<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>家长管理</title>
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
		<li class="active"><a href="${ctx}/mq/jz/">家长列表</a></li>
		<shiro:hasPermission name="mq:jz:edit"><li><a href="${ctx}/mq/jz/form">家长添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="jz" action="${ctx}/mq/jz/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="xm" htmlEscape="false" maxlength="60" class="input-medium"/>
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
				<th>身份证号</th>
				<th>手机号码</th>
				<th>电子邮箱</th>
				<th>微博号码</th>
				<th>QQ号码</th>
				<th>微博号码</th>
				<th>更新时间</th>
				<shiro:hasPermission name="mq:jz:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="jz">
			<tr>
				<td><a href="${ctx}/mq/jz/form?id=${jz.id}">
					${jz.xm}
				</a></td>
				<td>
					${jz.sfzh}
				</td>
				<td>
					${jz.sjhm}
				</td>
				<td>
					${jz.email}
				</td>
				<td>
					${jz.wxhm}
				</td>
				<td>
					${jz.qqhm}
				</td>
				<td>
					${jz.wbhm}
				</td>
				<td>
					<fmt:formatDate value="${jz.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="mq:jz:edit"><td>
    				<a href="${ctx}/mq/jz/form?id=${jz.id}">修改</a>
					<a href="${ctx}/mq/jz/delete?id=${jz.id}" onclick="return confirmx('确认要删除该家长吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>