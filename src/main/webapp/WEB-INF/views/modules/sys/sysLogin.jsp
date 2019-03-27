<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%

//    String ssoId = (String)request.getAttribute("ssoid");
//    System.out.println("ssoidfff="+ PbUserInfoAdapter.ssoid);
//    request.setAttribute("__sid", PbUserInfoAdapter.ssoid);
//    PbUserInfoAdapter sss=new PbUserInfoAdapter();
    //sss.acceptUserInfo();
%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>拉萨市教育云服务平台登陆</title>
    <link rel="stylesheet" href="${ctxStatic}/ums/css/style.css"/>
    <link rel="stylesheet" href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.css"/>
    <style>
        label.error {
            background: transparent;
            padding-left: 0;
        }
    </style>
    <script src="${ctxStatic}/jquery/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery-validation/1.11.1/dist/jquery.validate.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#loginForm").validate({
                rules: {
                    validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
                },
                messages: {
                    username: {required: "请填写用户名."}, password: {required: "请填写密码."},
                    validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
                },
                //errorLabelContainer: "#messageBox",
                //errorPlacement: function (error, element) {
                    //error.appendTo($("#loginError").parent());
                //}
            });
        });
        // 如果在框架或在对话框中，则弹出提示并跳转到首页
        if (self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0) {
            alert('未登录或登录超时。请重新登录，谢谢！');
            top.location = "${ctx}";
        }
    </script>
</head>
<body style="background: #F5F7FA;">
<!--logo、导航-->
<div class="header">
    <div class="wrap">
        <div class="logo fl"><a href="#"><img src="${pageContext.request.contextPath}/static/ums/images/logo.png"></a>
        </div>
    </div>
</div>
<!--头部结束-->
<!--中间内容-->
<div class="login_wrap">
    <div class="bgimg">
        <img src="${pageContext.request.contextPath}/static/ums/images/bg.jpg"></div>
    <div class="login_content">
        <h3>用户登录</h3>
        <!--普通教师登陆-->
        <div class="login teacher_login">
            <form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
                <p class="usrname"><b>
                    <img src="${pageContext.request.contextPath}/static/ums/images/login_04-02.png"></b>
                    <input type="text" id="username" name="username" placeholder="请输入用户名"></p>
                <p class="password"><b>
                    <img src="${pageContext.request.contextPath}/static/ums/images/login_08.png"></b>
                    <input type="password" id="password" name="password" placeholder="请输入密码"></p>
                <%--<p class="code">--%>
                    <%--<input type="text" placeholder="请输入验证码">--%>
                    <%--<b>--%>
                        <%--<img src="${pageContext.request.contextPath}/static/ums/images/login_15.png">--%>
                    <%--</b>--%>
                    <%--<i>换一换</i>--%>
                <%--</p>--%>
                <%--<p class="code">--%>
                    <%--<sys:validateCode name="validateCode" inputCssStyle="height:30px" imageCssStyle="height:28px;border:1px solid gray;margin-left:10px;" buttonCssStyle="color:#1f78d3;float:right;"/>--%>
                <%--</p>--%>

                <div class="clear"></div>
                <p>
                    <input type="submit" class="loginbtn" value="登录"/>
                </p>
                <span class="findpwd" style="float: right;">
                    <a href="#">找回密码</a>
                </span>
                <span class="v-error">
                    <span id="messageBox">
                        <label id="loginError" class="error">${message}</label>
                    </span>
                </span>
            </form>
        </div>

    </div>
</div>
<!--中间内容-->
<div class="clear"></div>
<!--底部-->
<div class="footer">
    <p>拉萨市电化教育馆</p>
    <p>技术支持：点创科技 </p>
</div>
<!--底部-->
</body>
</html>
      


      