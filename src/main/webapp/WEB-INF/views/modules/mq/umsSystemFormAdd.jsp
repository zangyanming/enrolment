<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>应用系统申请管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        // 手机号码验证
        jQuery.validator.addMethod("mobile", function(value, element) {
            var length = value.length;
            var mobile =  /^(((13[0-9]{1})|(149)|(15[0-9]{1})|(166)|(17[0-9]{1})|(18[0-9]{1})|(19[8-9]{1}))+\d{8})$/
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, "手机号码格式错误");

        // 电话号码验证
        jQuery.validator.addMethod("phone", function(value, element) {
            var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
            return this.optional(element) || (tel.test(value));
        }, "电话号码格式错误");

        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                rules: {
                    name: {remote: "${ctx}/mq/umsSystem/checkName?id=${umsSystem.id}&" + encodeURIComponent('${umsSystem.name}')},
                    sysCode: {remote: "${ctx}/mq/umsSystem/checkSysCode?id=${umsSystem.id}&" + encodeURIComponent('${umsSystem.sysCode}')}
                },
                messages: {
                    name: {remote: "系统名称已存在"},
                    sysCode: {remote: "系统编码已存在"}
                },
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
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
    <li><a href="${ctx}/mq/umsSystem/">应用系统申请列表</a></li>
    <li class="active"><a href="${ctx}/mq/umsSystem/form?id=${umsSystem.id}">应用系统申请<shiro:hasPermission
            name="mq:umsSystem:edit">${not empty umsSystem.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="mq:umsSystem:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="umsSystem" action="${ctx}/mq/umsSystem/save?op=1" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">系统名称：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">系统编码：</label>
        <div class="controls">
            <form:input path="sysCode" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请单位：</label>
        <div class="controls">
            <form:input path="applicationUnit" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请人：</label>
        <div class="controls">
            <form:input path="applicant" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请人邮箱：</label>
        <div class="controls">
            <form:input path="email" htmlEscape="false" maxlength="200" class="input-xlarge required email" value="email@"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请人手机：</label>
        <div class="controls">
            <form:input path="mobile" htmlEscape="false" maxlength="11" class="input-xlarge required mobile"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请人电话：</label>
        <div class="controls">
            <form:input path="phone" htmlEscape="false" maxlength="20" class="input-xlarge phone"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">审核状态：</label>
        <div class="controls">
            <form:radiobutton path="auditStatus" value="1" checked="true"/>通过
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <%--<div class="control-group">
        <label class="control-label">审核人：</label>
        <div class="controls">
            <form:input path="auditBy" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">审核时间：</label>
        <div class="controls">
            <input name="auditDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${umsSystem.auditDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </div>
    </div>--%>
    <div class="control-group" id="validDate">
        <label class="control-label">有效时间：</label>
        <div class="controls">
            <input name="validDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                   value="<fmt:formatDate value="${umsSystem.validDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <%--<div class="control-group">
        <label class="control-label">队列名称：</label>
        <div class="controls">
            <form:input path="queueName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">话题名称：</label>
        <div class="controls">
            <form:input path="topicName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>--%>
    <div class="control-group">
        <label class="control-label">备注信息：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="mq:umsSystem:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                             value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>