<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>应用系统申请管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/mq/umsSystem/">应用系统申请列表</a></li>
    <li class="active"><a href="${ctx}/mq/umsSystem/form?id=${umsSystem.id}">应用系统申请查看</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="umsSystem" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">系统名称：</label>
        <div class="controls">
            <label>${umsSystem.name}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">系统编码：</label>
        <div class="controls">
            <label>${umsSystem.sysCode}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请单位：</label>
        <div class="controls">
            <label>${umsSystem.applicationUnit}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请人：</label>
        <div class="controls">
            <label>${umsSystem.applicant}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请人邮箱：</label>
        <div class="controls">
            <label>${umsSystem.email}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请人手机：</label>
        <div class="controls">
            <label>${umsSystem.mobile}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请人电话：</label>
        <div class="controls">
            <label>${umsSystem.phone}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">审核状态：</label>
        <div class="controls">
            <c:if test="${umsSystem.auditStatus eq '0'}">
                <label>申请</label>
            </c:if>
            <c:if test="${umsSystem.auditStatus eq '1'}">
                <label>通过</label>
            </c:if>
            <c:if test="${umsSystem.auditStatus eq '2'}">
                <label>不通过</label>
            </c:if>
            <c:if test="${umsSystem.auditStatus eq '3'}">
                <label>停用</label>
            </c:if>
            <c:if test="${umsSystem.auditStatus eq '9'}">
                <label>过期</label>
            </c:if>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">审核不通过原因：</label>
        <div class="controls">
            <label>${umsSystem.auditReason}</label>
        </div>
    </div>
    <div class="control-group" id="validDate">
        <label class="control-label">有效时间：</label>
        <div class="controls">
            <label><fmt:formatDate value="${umsSystem.validDate}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">审核人：</label>
        <div class="controls">
            <label>${umsSystem.auditBy}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">审核时间：</label>
        <div class="controls">
            <label><fmt:formatDate value="${umsSystem.auditDate}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">密钥：</label>
        <div class="controls">
            <label>${umsSystem.sysKey}</label>
        </div>
    </div>
    <%--<div class="control-group">
        <label class="control-label">队列名称：</label>
        <div class="controls">
            <label>${umsSystem.queueName}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">话题名称：</label>
        <div class="controls">
            <label>${umsSystem.topicName}</label>
        </div>
    </div>--%>
    <div class="control-group">
        <label class="control-label">创建者：</label>
        <div class="controls">
            <label>${umsSystem.createBy.name}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">创建时间：</label>
        <div class="controls">
            <label><fmt:formatDate value="${umsSystem.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">更新者：</label>
        <div class="controls">
            <label>${umsSystem.updateBy.name}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">更新时间：</label>
        <div class="controls">
            <label><fmt:formatDate value="${umsSystem.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注信息：</label>
        <div class="controls">
            <label>${umsSystem.remarks}</label>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>