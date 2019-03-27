<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>应用系统申请管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
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
    <li><a href="${ctx}/mq/umsSystem/">应用系统申请列表</a></li>
    <li class="active"><a href="${ctx}/mq/umsSystem/form?id=${umsSystem.id}">应用系统申请<shiro:hasPermission
            name="mq:umsSystem:edit">${not empty umsSystem.id?'审核':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="mq:umsSystem:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="umsSystem" action="${ctx}/mq/umsSystem/save?op=3" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核状态：</label>
        <div class="controls">
            <form:radiobutton path="auditStatus" value="1" checked="true"/>通过
            <form:radiobutton path="auditStatus" value="2"/>不通过
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
        <label class="control-label">审核不通过原因：</label>
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
    <div class="form-actions">
        <shiro:hasPermission name="mq:umsSystem:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                             value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>