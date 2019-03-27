<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>短信消息管理</title>
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
		<li><a href="${ctx}/mq/shortMessage/">短信消息列表</a></li>
		<li class="active"><a href="${ctx}/mq/shortMessage/form?id=${shortMessage.id}">短信消息<shiro:hasPermission name="mq:shortMessage:edit">${not empty shortMessage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="mq:shortMessage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shortMessage" action="${ctx}/mq/shortMessage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<%--<form:textarea path="content" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>--%>
				<label>${shortMessage.content}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发送者单位：</label>
			<div class="controls">
				<%--<form:input path="senderOfficeId" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
				<label>${shortMessage.senderOfficeId}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发送者：</label>
			<div class="controls">
				<%--<form:input path="senderId" htmlEscape="false" maxlength="30" class="input-xlarge "/>--%>
				<label>${shortMessage.senderId}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接收者：</label>
			<div class="controls">
				<%--<form:input path="receiverIds" htmlEscape="false" maxlength="5000" class="input-xlarge "/>--%>
				<label>${shortMessage.receiverIds}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否需要审核：</label>
			<div class="controls">
				<%--<form:input path="ischeck" htmlEscape="false" maxlength="10" class="input-xlarge "/>--%>
				<%--<label>
					<c:if test="${shortMessage.audit}">审核</c:if>
					<c:if test="${!shortMessage.audit}">不审核</c:if>
				</label>--%>
				<label>
					<c:if test="${shortMessage.ischeck eq '1'}">是</c:if>
					<c:if test="${shortMessage.ischeck eq '0'}">否</c:if>
				</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息审核人：</label>
			<div class="controls">
				<%--<form:input path="checkerId" htmlEscape="false" maxlength="30" class="input-xlarge "/>--%>
				<label>${shortMessage.checkerId}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核状态：</label>
			<div class="controls">
				<%--<form:input path="checkState" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
				<label>
					<c:if test="${shortMessage.checkState eq '0'}">未审核</c:if>
					<c:if test="${shortMessage.checkState eq '1'}">审核通过</c:if>
					<c:if test="${shortMessage.checkState eq '2'}">审核未通过</c:if>
				</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核未通过原因：</label>
			<div class="controls">
				<%--<form:input path="reason" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
				<label>${shortMessage.reason}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核时间：</label>
			<div class="controls">
				<%--<input name="checkDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${shortMessage.checkDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
				<label><fmt:formatDate value="${shortMessage.checkDate}" type="both"/></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核人单位：</label>
			<div class="controls">
				<%--<form:input path="checkerOfficeId" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
				<label>${shortMessage.checkerOfficeId}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发送时间：</label>
			<div class="controls">
				<%--<input name="sendDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${shortMessage.sendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
				<label><fmt:formatDate value="${shortMessage.sendDate}" type="both"/></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>--%>
				<label>${shortMessage.remarks}</label>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="mq:shortMessage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>