<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用系统申请管理</title>
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
		<li class="active"><a href="${ctx}/mq/umsSystem/">应用系统申请列表</a></li>
		<shiro:hasPermission name="mq:umsSystem:edit"><li><a href="${ctx}/mq/umsSystem/form?op=1">应用系统申请添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="umsSystem" action="${ctx}/mq/umsSystem/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>系统名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>系统编码：</label>
				<form:input path="sysCode" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>系统名称</th>
				<th>系统编码</th>
				<th>申请单位</th>
				<th>申请人</th>
				<th>申请人手机</th>
				<th>审核状态</th>
				<th>创建者</th>
				<th>创建时间</th>
				<th>更新者</th>
				<th>更新时间</th>
				<shiro:hasPermission name="mq:umsSystem:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="umsSystem">
			<tr>
				<td><a href="${ctx}/mq/umsSystem/form?id=${umsSystem.id}">
					${umsSystem.name}
				</a></td>
				<td>
					${umsSystem.sysCode}
				</td>
				<td>
					${umsSystem.applicationUnit}
				</td>
				<td>
					${umsSystem.applicant}
				</td>
				<td>
					${umsSystem.mobile}
				</td>
				<td>
					<c:if test="${umsSystem.auditStatus eq '0'}">
						申请
					</c:if>
					<c:if test="${umsSystem.auditStatus eq '1'}">
						通过
					</c:if>
					<c:if test="${umsSystem.auditStatus eq '2'}">
						不通过
					</c:if>
					<c:if test="${umsSystem.auditStatus eq '3'}">
						停用
					</c:if>
					<c:if test="${umsSystem.auditStatus eq '9'}">
						过期
					</c:if>
				</td>
				<td>
					${umsSystem.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${umsSystem.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${umsSystem.updateBy.name}
				</td>
				<td>
					<fmt:formatDate value="${umsSystem.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="mq:umsSystem:edit"><td>
					<a href="${ctx}/mq/umsSystem/form?id=${umsSystem.id}">查看</a>
					<c:if test="${umsSystem.auditStatus eq '0'}">
						<a href="${ctx}/mq/umsSystem/form?id=${umsSystem.id}&op=3">审核</a>
					</c:if>
					<c:if test="${umsSystem.auditStatus ne '0'}">
						<a href="${ctx}/mq/umsSystem/form?id=${umsSystem.id}&op=2">修改</a>
					</c:if>
					<a href="${ctx}/mq/umsSystem/delete?id=${umsSystem.id}" onclick="return confirmx('确认要删除该应用系统申请吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>