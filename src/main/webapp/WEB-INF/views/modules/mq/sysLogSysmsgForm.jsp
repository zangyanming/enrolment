<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用系统消息日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/mq/sysLogSysmsg/">应用系统消息日志列表</a></li>
		<li class="active"><a href="${ctx}/mq/sysLogSysmsg/form?id=${sysLogSysmsg.id}">应用系统消息日志<shiro:hasPermission name="mq:sysLogSysmsg:edit">${not empty sysLogSysmsg.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="mq:sysLogSysmsg:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysLogSysmsg" action="${ctx}/mq/sysLogSysmsg/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">日志类型：</label>
			<div class="controls">
				<%--<form:radiobuttons path="type" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>--%>
				<label>
					<c:if test="${sysLogSysmsg.type eq '1'}">发送</c:if>
					<c:if test="${sysLogSysmsg.type eq '2'}">接收</c:if>
				</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日志标题：</label>
			<div class="controls">
				<%--<form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
				<label>${sysLogSysmsg.title}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息发送者：</label>
			<div class="controls">
				<%--<form:input path="sendBy" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
				<label>${sysLogSysmsg.sendBy}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息接收者：</label>
			<div class="controls">
				<%--<form:input path="receiveBy" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
				<label>${sysLogSysmsg.receiveBy}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息状态：</label>
			<div class="controls">
				<%--<form:radiobuttons path="success" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>--%>
				<label>
					<c:if test="${sysLogSysmsg.success eq '0'}">失败</c:if>
					<c:if test="${sysLogSysmsg.success eq '1'}">成功</c:if>
				</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息数量：</label>
			<div class="controls">
				<%--<form:input path="msgCount" htmlEscape="false" maxlength="8" class="input-xlarge  digits"/>--%>
				<label>${sysLogSysmsg.msgCount}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作时间：</label>
			<div class="controls">
				<label><fmt:formatDate value="${sysLogSysmsg.createDate}" type="both"/></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作IP地址：</label>
			<div class="controls">
				<%--<form:input path="remoteAddr" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
				<label>${sysLogSysmsg.remoteAddr}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户代理：</label>
			<div class="controls">
				<%--<form:input path="userAgent" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
				<label>${sysLogSysmsg.userAgent}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请求URI：</label>
			<div class="controls">
				<%--<form:input path="requestUri" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
				<label>${sysLogSysmsg.requestUri}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作方式：</label>
			<div class="controls">
				<%--<form:input path="method" htmlEscape="false" maxlength="5" class="input-xlarge "/>--%>
				<label>${sysLogSysmsg.method}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作提交的数据：</label>
			<div class="controls">
				<%--<form:input path="params" htmlEscape="false" class="input-xlarge "/>--%>
				<label>${sysLogSysmsg.params}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">异常信息：</label>
			<div class="controls">
				<%--<form:input path="exception" htmlEscape="false" class="input-xlarge "/>--%>
				<label>${sysLogSysmsg.exception}</label>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="mq:sysLogSysmsg:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>