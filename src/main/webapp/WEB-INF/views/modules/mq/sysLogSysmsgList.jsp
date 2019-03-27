<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用系统消息日志管理</title>
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
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/mq/sysLogSysmsg/">应用系统消息日志列表</a></li>
		<shiro:hasPermission name="mq:sysLogSysmsg:edit"><li><a href="${ctx}/mq/sysLogSysmsg/form">应用系统消息日志添加</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="sysLogSysmsg" action="${ctx}/mq/sysLogSysmsg/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>日志类型：</label>
				<%--<form:radiobuttons path="type" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<form:radiobutton path="type" value="1"/>发送
				<form:radiobutton path="type" value="2"/>接收
			</li>
			<li><label>日志标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${sysLogSysmsg.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${sysLogSysmsg.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="clearfix"></li>
			<li><label>消息状态：</label>
					<%--<form:radiobuttons path="success" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<form:radiobutton path="success" value="0"/>失败
				<form:radiobutton path="success" value="1"/>成功
			</li>
			<li><label>消息发送者：</label>
				<form:input path="sendBy" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>消息接收者：</label>
				<form:input path="receiveBy" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>日志类型</th>
				<th>日志标题</th>
				<th>消息发送者</th>
				<th>消息接收者</th>
				<th>消息状态</th>
				<th>消息数量</th>
				<th>操作时间</th>
				<th>操作IP地址</th>
				<th>操作提交的数据</th>
				<th>异常信息</th>
				<shiro:hasPermission name="mq:sysLogSysmsg:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysLogSysmsg">
			<tr>
				<td>
					<c:if test="${sysLogSysmsg.type eq '1'}">发送</c:if>
					<c:if test="${sysLogSysmsg.type eq '2'}">接收</c:if>
				</td>
				<td>
					<a href="${ctx}/mq/sysLogSysmsg/form?id=${sysLogSysmsg.id}">
						${sysLogSysmsg.title}
					</a>
				</td>
				<td>
					${sysLogSysmsg.sendBy}
				</td>
				<td>
					${sysLogSysmsg.receiveBy}
				</td>
				<td>
					<c:if test="${sysLogSysmsg.success eq '0'}">失败</c:if>
					<c:if test="${sysLogSysmsg.success eq '1'}">成功</c:if>
				</td>
				<td>
					${sysLogSysmsg.msgCount}
				</td>
				<td>
					<fmt:formatDate value="${sysLogSysmsg.createDate}" type="both"/>
				</td>
				<td>
					${sysLogSysmsg.remoteAddr}
				</td>
				<td>
					<c:if test="${fn:length(sysLogSysmsg.params) <= 40}">
						${sysLogSysmsg.params}
					</c:if>
					<c:if test="${fn:length(sysLogSysmsg.params) > 40}">
						${fn:substring(sysLogSysmsg.params, 0, 40)}......
					</c:if>
				</td>
				<td>
					<c:if test="${fn:length(sysLogSysmsg.exception) <= 30}">
						${sysLogSysmsg.exception}
					</c:if>
					<c:if test="${fn:length(sysLogSysmsg.exception) > 30}">
						${fn:substring(sysLogSysmsg.exception, 0, 30)}......
					</c:if>
				</td>
				<shiro:hasPermission name="mq:sysLogSysmsg:edit"><td>
    				<a href="${ctx}/mq/sysLogSysmsg/form?id=${sysLogSysmsg.id}">修改</a>
					<a href="${ctx}/mq/sysLogSysmsg/delete?id=${sysLogSysmsg.id}" onclick="return confirmx('确认要删除该应用系统消息日志吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>