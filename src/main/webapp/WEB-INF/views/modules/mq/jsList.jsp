<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>教师管理</title>
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
		<li class="active"><a href="${ctx}/mq/js/">教师列表</a></li>
		<shiro:hasPermission name="mq:js:edit"><li><a href="${ctx}/mq/js/form">教师添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="js" action="${ctx}/mq/js/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>学校名称：</label>
				<form:input path="xxmc" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="xm" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>学校名称</th>
				<th>姓名</th>
				<th>身份证号码</th>
				<th>性别</th>
				<th>手机号码</th>
				<th>电子邮箱</th>
				<th>微信号码</th>
				<th>QQ号码</th>
				<th>微博号码</th>
				<th>登记时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="mq:js:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="js">
			<tr>
				<td><a href="${ctx}/mq/js/form?id=${js.id}">
					${js.xxmc}
				</a></td>
				<td>
					${js.xm}
				</td>
				<td>
					${js.sfzh}
				</td>
				<td>
					${fns:getDictLabel(js.xb, 'sex', '')}
				</td>
				<td>
					${js.sjhm}
				</td>
				<td>
					${js.email}
				</td>
				<td>
					${js.wxhm}
				</td>
				<td>
					${js.qqhm}
				</td>
				<td>
					${js.wbhm}
				</td>
				<td>
					<fmt:formatDate value="${js.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${js.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="mq:js:edit"><td>
    				<a href="${ctx}/mq/js/form?id=${js.id}">修改</a>
					<a href="${ctx}/mq/js/delete?id=${js.id}" onclick="return confirmx('确认要删除该教师吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>