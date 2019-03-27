<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>系统消息申请管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                rules: {
                    name: {remote: "${ctx}/mq/umsSystem/checkName?id=${umsSystem.id}&" + encodeURIComponent('${umsSystem.name}')}
                },
                messages: {
                    name: {remote: "系统名称已存在"}
                },
                submitHandler: function (form) {
                    /*if (customValidate()) {
                        loading('正在提交，请稍等...');
                        form.submit();
                    }*/
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

        /*function customValidate() {
            var name = $("input[name='name']").val();
            alert(name)
            return false;
        };*/

        $(function () {
            $("input[name='auditStatus']").click(function () {
                if ($(this).val() == "2" || $(this).val() == "3") {
                    $("#auditReason").show();
                } else {
                    $("#auditReason").hide();
                }

                if($(this).val() == "1") {
                    $("#validDate").show();
                } else {
                    $("#validDate").hide();
                }
            });
        });

        window.onload = function () {
            var auditStatus = $("input[name='auditStatus']:checked").val();
            if (auditStatus == "2" || auditStatus == "3") {
                $("#auditReason").show();
            } else {
                $("#auditReason").hide();
            }

            if(auditStatus == "1") {
                $("#validDate").show();
            } else {
                $("#validDate").hide();
            }
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/mq/umsSystem/">系统消息申请列表</a></li>
    <li class="active"><a href="${ctx}/mq/umsSystem/form?id=${umsSystem.id}">系统消息申请<shiro:hasPermission
            name="mq:umsSystem:edit">${not empty umsSystem.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="mq:umsSystem:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="umsSystem" action="${ctx}/mq/umsSystem/save" method="post"
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
            <form:input path="email" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请人手机：</label>
        <div class="controls">
            <form:input path="mobile" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请人电话：</label>
        <div class="controls">
            <form:input path="phone" htmlEscape="false" maxlength="20" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">审核状态：</label>
        <div class="controls">
                <%--<form:radiobuttons path="auditStatus" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>--%>
            <form:radiobutton path="auditStatus" value="0"/>申请
            <form:radiobutton path="auditStatus" value="1" checked="true"/>通过
            <form:radiobutton path="auditStatus" value="2"/>不通过
            <form:radiobutton path="auditStatus" value="3"/>停用
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
    <div class="control-group" id="auditReason" style="display:none">
        <label class="control-label">审核原因：</label>
        <div class="controls">
            <form:input path="auditReason" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group" id="validDate" style="display:none">
        <label class="control-label">有效时间：</label>
        <div class="controls">
            <input name="validDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                   value="<fmt:formatDate value="${umsSystem.validDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">系统编码：</label>
        <div class="controls">
            <form:input path="sysCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">密钥：</label>
        <div class="controls">
            <form:input path="sysKey" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
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
    </div>
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