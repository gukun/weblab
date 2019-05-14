<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta name="description" content="User login page"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="${ctxStatic}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery-validation/1.14.0/jquery.validate.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery-validation/1.14.0/localization/messages_zh.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap/3.3.4/css_default/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/bootstrap/3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/awesome/4.4/css/font-awesome.min.css" rel="stylesheet"/>

    <link href="${ctxStatic}/common/labplus.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/common/labplus.js" type="text/javascript"></script>
    <link rel="shortcut icon" href="images/favicon.png" type="image/png">
    <!-- text fonts -->
    <link rel="stylesheet" href="${ctxStatic }/common/login/ace-fonts.css"/>

    <!-- ace styles -->
    <link rel="stylesheet" href="${ctxStatic }/common/login/ace.css"/>

    <!-- 引入layer插件 -->
    <script src="${ctxStatic}/layer-v2.0/layer/layer.js"></script>
    <script src="${ctxStatic}/layer-v2.0/layer/laydate/laydate.js"></script>


    <!--[if lte IE 9]>
    <link rel="stylesheet" href="../assets/css/ace-part2.css"/>
    <![endif]-->
    <link rel="stylesheet" href="${ctxStatic }/common/login/ace-rtl.css"/>
    <title>${fns:getConfig('productName')} 登录</title>
    <script>
        if (window.top !== window.self) {
            window.top.location = window.location;
        }
    </script>
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
                errorLabelContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    error.appendTo($("#loginError").parent());
                }
            });
        });
        // 如果在框架或在对话框中，则弹出提示并跳转到首页
        if (self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0) {
            alert('未登录或登录超时。请重新登录，谢谢！');
            top.location = "${ctx}";
        }
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate({
                rules: {
                    loginName: {remote: "${ctx}/sys/user/validateLoginName"},
                    mobile: {remote: "${ctx}/sys/user/validateMobile"},
                    randomCode: {

                        remote: {

                            url: "${ctx}/sys/register/validateMobileCode",

                            data: {
                                mobile: function () {
                                    return $("#tel").val();
                                }
                            }

                        }
                    }
                },
                messages: {
                    confirmNewPassword: {equalTo: "输入与上面相同的密码"},
                    ck1: {required: "必须接受用户协议."},
                    loginName: {remote: "此用户名已经被注册!", required: "用户名不能为空."},
                    mobile: {remote: "此手机号已经被注册!", required: "手机号不能为空."},
                    randomCode: {remote: "验证码不正确!", required: "验证码不能为空."}
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


<body class="login-layout blur-login">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1">
                <div class="login-container">

                    <div class="center">
                        <h1>
                            <br/>
                            <img src="${ctxStatic}/common/login/images/logo.png">
                            <br>
                        </h1>
                        <sys:message content="${message}"/>
                    </div>

                    <div class="space-6"></div>

                    <div class="position-relative">
                        <div id="login-box" class="login-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger">
                                        <i class="ace-icon fa fa-coffee green"></i>
                                        用户登录
                                    </h4>

                                    <div class="space-6"></div>

                                    <form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
                                        <fieldset>
                                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" id="username" name="username"
                                                                   class="form-control required" value="${username}"
                                                                   placeholder="用户名"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
                                            </label>

                                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" id="password" name="password"
                                                                   class="form-control required" placeholder="密码"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
                                            </label>
                                            <c:if test="${isValidateCodeLogin}">
                                                <div class="input-group m-b text-muted validateCode">
                                                    <label class="input-label mid" for="validateCode">验证码:</label>
                                                    <sys:validateCode name="validateCode"
                                                                      inputCssStyle="margin-bottom:5px;"/>
                                                </div>
                                            </c:if>
                                            <div class="space"></div>

                                            <div class="clearfix">
                                                <label class="inline">
                                                    <input type="checkbox" id="rememberMe"
                                                           name="rememberMe" ${rememberMe ? 'checked' : ''}
                                                           class="ace"/>
                                                    <span class="lbl"> 记住我</span>
                                                </label>

                                                <button type="submit"
                                                        class="width-35 pull-right btn btn-sm btn-primary">
                                                    <i class="ace-icon fa fa-key"></i>
                                                    <span class="bigger-110">登录</span>
                                                </button>
                                            </div>

                                            <div class="space-4"></div>
                                            <div id="themeSwitch" class="dropdown">
                                                <a class="dropdown-toggle" data-toggle="dropdown"
                                                   href="#">${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}<b
                                                        class="caret"></b></a>
                                                <ul class="dropdown-menu">
                                                    <c:forEach items="${fns:getDictList('theme')}" var="dict">
                                                        <li><a href="#"
                                                               onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href"><font
                                                                color="black">${dict.label}</font></a></li>
                                                    </c:forEach>
                                                </ul>
                                                <!--[if lte IE 6]>
                                                <script type="text/javascript">$('#themeSwitch').hide();</script>
                                                <![endif]-->
                                            </div>
                                        </fieldset>
                                    </form>

                                </div><!-- /.widget-main -->

                                <div class="toolbar clearfix">
                                    <div>

                                        <font color="#832">使用IE8+浏览器</font>
                                    </div>

                                    <!-- <div>
                                        <a href="#" data-target="#signup-box" class="user-signup-link">
                                            没有账号？注册
                                            <i class="ace-icon fa fa-arrow-right"></i>
                                        </a>
                                    </div> -->
                                </div>
                            </div><!-- /.widget-body -->
                        </div><!-- /.login-box -->
                    </div><!-- /.position-relative -->
                    <div class="center"><h4 class="light-blue" id="id-company-text">&copy; XXX</h4></div>
                    <div class="navbar-fixed-top align-right">
                        <br/>
                        &nbsp;
                        <a id="btn-login-dark" href="#">Dark</a>
                        &nbsp;
                        <span class="blue">/</span>
                        &nbsp;
                        <a id="btn-login-blur" href="#">Blur</a>
                        &nbsp;
                        <span class="blue">/</span>
                        &nbsp;
                        <a id="btn-login-light" href="#">Light</a>
                        &nbsp; &nbsp; &nbsp;
                    </div>
                </div>
            </div><!-- /.col -->
        </div><!-- /.row -->
    </div><!-- /.main-content -->
</div><!-- /.main-container -->

<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='../assets/js/jquery.js'>" + "<" + "/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='../assets/js/jquery1x.js'>" + "<" + "/script>");
</script>
<![endif]-->

<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='../assets/js/jquery.mobile.custom.js'>" + "<" + "/script>");
</script>
<style>
    /* Validation */

    label.error {
        color: #cc5965;
        display: inline-block;
        margin-left: 5px;
    }

    .form-control.error {
        border: 1px dotted #cc5965;
    }
</style>
<!-- inline scripts related to this page -->
<script type="text/javascript">
    $(document).ready(function () {
        $(document).on('click', '.toolbar a[data-target]', function (e) {
            e.preventDefault();
            var target = $(this).data('target');
            $('.widget-box.visible').removeClass('visible');//hide others
            $(target).addClass('visible');//show target
        });
    });


    //you don't need this, just used for changing background
    $(document).ready(function () {
        $('#btn-login-dark').on('click', function (e) {
            $('body').attr('class', 'login-layout');
            $('#id-text2').attr('class', 'white');
            $('#id-company-text').attr('class', 'blue');

            e.preventDefault();
        });
        $('#btn-login-light').on('click', function (e) {
            $('body').attr('class', 'login-layout light-login');
            $('#id-text2').attr('class', 'grey');
            $('#id-company-text').attr('class', 'blue');

            e.preventDefault();
        });
        $('#btn-login-blur').on('click', function (e) {
            $('body').attr('class', 'login-layout blur-login');
            $('#id-text2').attr('class', 'white');
            $('#id-company-text').attr('class', 'light-blue');

            e.preventDefault();
        });

    });
</script>
</body>
</html>
