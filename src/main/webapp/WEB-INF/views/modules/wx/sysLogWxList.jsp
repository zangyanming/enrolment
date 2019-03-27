<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信消息日志信息管理</title>
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
		<li class="active"><a href="${ctx}/wx/sysLogWx/">微信消息日志信息列表</a></li>
		<shiro:hasPermission name="wx:sysLogWx:edit"><li><a href="${ctx}/wx/sysLogWx/form">微信消息日志信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysLogWx" action="${ctx}/wx/sysLogWx/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>发送时间：</label>
				<input name="beginSendDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${sysLogWx.beginSendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endSendDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${sysLogWx.endSendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>消息状态：</label>
				<form:input path="success" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>日志标题</th>
				<th>操作IP地址</th>
				<th>请求URI</th>
				<th>操作方式</th>
				<th>操作提交的数据</th>
				<th>微信发送者单位</th>
				<th>微信发送者</th>
				<th>微信接收者</th>
				<th>发送时间</th>
				<th>消息状态</th>
				<th>异常信息</th>
				<shiro:hasPermission name="wx:sysLogWx:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysLogWx">
			<tr>
				<td><a href="${ctx}/wx/sysLogWx/form?id=${sysLogWx.id}">
					${sysLogWx.title}
				</a></td>
				<td>
					${sysLogWx.remoteAddr}
				</td>
				<td>
					${sysLogWx.requestUri}
				</td>
				<td>
					${sysLogWx.method}
				</td>
				<td>
					${sysLogWx.params}
				</td>
				<td>
					${sysLogWx.sendOfficeId}
				</td>
				<td>
					${sysLogWx.sendBy}
				</td>
				<td>
					${sysLogWx.receiveBy}
				</td>
				<td>
					<fmt:formatDate value="${sysLogWx.sendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sysLogWx.success}
				</td>
				<td>
					${sysLogWx.exception}
				</td>
				<shiro:hasPermission name="wx:sysLogWx:edit"><td>
    				<a href="${ctx}/wx/sysLogWx/form?id=${sysLogWx.id}">修改</a>
					<a href="${ctx}/wx/sysLogWx/delete?id=${sysLogWx.id}" onclick="return confirmx('确认要删除该微信消息日志信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>