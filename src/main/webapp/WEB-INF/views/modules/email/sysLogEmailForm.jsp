<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>邮件日志管理</title>
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
		<li><a href="${ctx}/email/sysLogEmail/">邮件日志列表</a></li>
		<li class="active"><a href="${ctx}/email/sysLogEmail/form?id=${sysLogEmail.id}">邮件日志<shiro:hasPermission name="email:sysLogEmail:edit">${not empty sysLogEmail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="email:sysLogEmail:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysLogEmail" action="${ctx}/email/sysLogEmail/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">日志标题：</label>
			<div class="controls">
				<label>${sysLogEmail.title}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮件发送者：</label>
			<div class="controls">
				<label>${sysLogEmail.sendBy}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮件接收者：</label>
			<div class="controls">
				<label>${sysLogEmail.receiveBy}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发送时间：</label>
			<div class="controls">
				<label><fmt:formatDate value="${sysLogEmail.sendDate}" type="both"/></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件名称：</label>
			<div class="controls">
				<label>${sysLogEmail.attachName}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息状态：</label>
			<div class="controls">
				<label>
					<c:if test="${sysLogEmail.success eq '0'}">失败</c:if>
					<c:if test="${sysLogEmail.success eq '1'}">成功</c:if>
				</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作时间：</label>
			<div class="controls">
				<label><fmt:formatDate value="${sysLogEmail.createDate}" type="both"/></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作IP地址：</label>
			<div class="controls">
				<label>${sysLogEmail.remoteAddr}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户代理：</label>
			<div class="controls">
				<label>${sysLogEmail.userAgent}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请求URI：</label>
			<div class="controls">
				<label>${sysLogEmail.requestUri}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作方式：</label>
			<div class="controls">
				<label>${sysLogEmail.method}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作提交的数据：</label>
			<div class="controls">
				<label>${sysLogEmail.params}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">异常信息：</label>
			<div class="controls">
				<label>${sysLogEmail.exception}</label>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="email:sysLogEmail:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>