<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>虚拟网络实验室</title>

    <%@ include file="/webpage/include/head.jsp" %>
    <script src="${ctxStatic}/common/inspinia.js?v=3.2.0"></script>
    <script src="${ctxStatic}/common/contabs.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            <%--if('${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}' == '天蓝主题'){--%>
                <%--// 蓝色主题--%>
                <%--$("body").removeClass("skin-2");--%>
                <%--$("body").removeClass("skin-3");--%>
                <%--$("body").addClass("skin-1");--%>
            <%--}else  if('${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}' == '橙色主题'){--%>
                <%--// 黄色主题--%>
                <%--$("body").removeClass("skin-1");--%>
                <%--$("body").removeClass("skin-2");--%>
                <%--$("body").addClass("skin-3");--%>
            <%--}else {--%>
                <%--// 默认主题--%>
                <%--$("body").removeClass("skin-2");--%>
                <%--$("body").removeClass("skin-3");--%>
                <%--$("body").removeClass("skin-1");--%>
            <%--};--%>
            //蓝色主题
//            $("body").removeClass("skin-2");
//            $("body").removeClass("skin-3");
            $("body").addClass("skin-1");
        });

    </script>

</head>

<body class="fixed-sidebar full-height-layout gray-bg">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span><img alt="image" class="img-circle" style="height:64px;width:64px;"
                                   src="${fns:getUser().photo}"/></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               <span class="block m-t-xs"><strong
                                       class="font-bold">${fns:getUser().name}</strong></span>
                               <span class="text-muted text-xs block">${fns:getUser().roleNames}<b
                                       class="caret"></b></span>
                                </span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a class="J_menuItem" href="${ctx}/sys/user/imageEdit">修改头像</a>
                            </li>
                            <li><a class="J_menuItem" href="${ctx }/sys/user/info">个人资料</a>
                            </li>
                            <li><a class="J_menuItem" href="${ctx }/iim/contact/index">我的通讯录</a>
                            </li>
                            <li><a class="J_menuItem" href="${ctx }/iim/mailBox/list">信箱</a>
                            </li>

                            <li class="divider"></li>
                            <li><a href="${ctx}/logout">安全退出</a>
                            </li>
                        </ul>
                    </div>
                    <div class="logo-element">LAB
                    </div>
                </li>

                <t:menu menu="${fns:getTopMenu()}"></t:menu>


            </ul>

            <%--<div class="navbar-header" style="text-align: center;">
                <a class="navbar-minimalize minimalize-styl-2 btn btn-primary" href="#"><i class="fa fa-bars"></i> </a>
                <a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>--%>

            <div class="nav-footer" style="position:absolute;bottom: 0px;">
                <nav class="navbar navbar-static-footer" role="navigation" style="margin-bottom: 0">
                    <ul class="nav navbar-top-links navbar-left" style="padding-left: 15px;min-width: 87px;"><!--样式修改 -->
                        <li class="dropup" style="padding-right: 10px;">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-envelope"></i> <span
                                    class="label label-warning">${noReadCount}</span>
                            </a>
                            <ul class="dropdown-menu dropdown-messages">
                                <c:forEach items="${mailPage.list}" var="mailBox">
                                    <li class="m-t-xs">
                                        <div class="dropdown-messages-box">

                                            <a href="#"
                                               onclick='top.openTab("${ctx}/iim/contact/index?name=${mailBox.sender.name }","通讯录", false)'
                                               class="pull-left">
                                                <img alt="image" class="img-circle" src="${mailBox.sender.photo }">
                                            </a>

                                            <div class="media-body">
                                                <small class="pull-right">${fns:getTime(mailBox.sendtime)}前</small>
                                                <strong>${mailBox.sender.name }</strong>
                                                <a class="J_menuItem"
                                                   href="${ctx}/iim/mailBox/detail?id=${mailBox.id}"> ${fns:abbr(mailBox.mail.title,50)}</a>
                                                <br>
                                                <a class="J_menuItem"
                                                   href="${ctx}/iim/mailBox/detail?id=${mailBox.id}">
                                                        ${mailBox.mail.overview}
                                                </a>
                                                <br>
                                                <small class="text-muted">
                                                    <fmt:formatDate value="${mailBox.sendtime}"
                                                                    pattern="yyyy-MM-dd HH:mm:ss"/></small>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="divider"></li>
                                </c:forEach>
                                <li>
                                    <div class="text-center link-block">
                                        <a class="J_menuItem" href="${ctx}/iim/mailBox/list?orderBy=sendtime desc">
                                            <i class="fa fa-envelope"></i> <strong> 查看所有邮件</strong>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li class="dropup" style="padding-right: 20px;">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-bell"></i> <span class="label label-primary">${count }</span>
                            </a>
                            <ul class="dropdown-menu dropdown-alerts">
                                <li>

                                    <c:forEach items="${page.list}" var="oaNotify">

                                        <div>
                                            <a class="J_menuItem" href="${ctx}/oa/oaNotify/view?id=${oaNotify.id}&">
                                                <i class="fa fa-envelope fa-fw"></i> ${fns:abbr(oaNotify.title,50)}
                                            </a>
                                            <span class="pull-right text-muted small">${fns:getTime(oaNotify.updateDate)}前</span>
                                        </div>

                                    </c:forEach>

                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="text-center link-block">
                                        您有${count }条未读消息 <a class="J_menuItem" href="${ctx }/oa/oaNotify/self ">
                                        <strong>查看所有 </strong>
                                        <i class="fa fa-angle-right"></i>
                                    </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li style="padding-bottom: 10px;">
                            <div class="navbar-footer" style="text-align: center;">
                                <a class="navbar-minimalize btn btn-primary" href="#"
                                   style="width: 40px; padding-top: 10px; min-height: 40px; padding-bottom: 0px;"><i class="fa fa-bars"></i></a>
                            </div>
                        </li>
                    </ul>
                    <%--<div class="navbar-footer" style="text-align: center;margin-left: -15px;">
                        <a class="navbar-minimalize btn btn-primary" href="#"><i class="fa fa-bars"></i></a>
                    </div>--%>

                </nav>
            </div>
        </div>

    </nav>

    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <%--
                    <div class="row border-bottom">
                        <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                            <div class="navbar-header" style="text-align: center;">
                                <a class="navbar-minimalize minimalize-styl-2 btn btn-primary" href="#"><i class="fa fa-bars"></i> </a>
                            </div>

                            <ul class="nav navbar-top-links navbar-right">
                                <li class="dropdown">
                                    <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                        <i class="fa fa-envelope"></i> <span class="label label-warning">${noReadCount}</span>
                                    </a>
                                    <ul class="dropdown-menu dropdown-messages">
                                         <c:forEach items="${mailPage.list}" var="mailBox">
                                            <li class="m-t-xs">
                                                <div class="dropdown-messages-box">

                                                    <a  href="#" onclick='top.openTab("${ctx}/iim/contact/index?name=${mailBox.sender.name }","通讯录", false)' class="pull-left">
                                                        <img alt="image" class="img-circle" src="${mailBox.sender.photo }">
                                                    </a>
                                                    <div class="media-body">
                                                        <small class="pull-right">${fns:getTime(mailBox.sendtime)}前</small>
                                                        <strong>${mailBox.sender.name }</strong>
                                                        <a class="J_menuItem" href="${ctx}/iim/mailBox/detail?id=${mailBox.id}"> ${fns:abbr(mailBox.mail.title,50)}</a>
                                                        <br>
                                                        <a class="J_menuItem" href="${ctx}/iim/mailBox/detail?id=${mailBox.id}">
                                                         ${mailBox.mail.overview}
                                                        </a>
                                                        <br>
                                                        <small class="text-muted">
                                                        <fmt:formatDate value="${mailBox.sendtime}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
                                                    </div>
                                                </div>
                                            </li>
                                            <li class="divider"></li>
                                        </c:forEach>
                                        <li>
                                            <div class="text-center link-block">
                                                <a class="J_menuItem" href="${ctx}/iim/mailBox/list?orderBy=sendtime desc">
                                                    <i class="fa fa-envelope"></i> <strong> 查看所有邮件</strong>
                                                </a>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li class="dropdown">
                                    <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                        <i class="fa fa-bell"></i> <span class="label label-primary">${count }</span>
                                    </a>
                                    <ul class="dropdown-menu dropdown-alerts">
                                        <li>

                                        <c:forEach items="${page.list}" var="oaNotify">

                                                <div>
                                                       <a class="J_menuItem" href="${ctx}/oa/oaNotify/view?id=${oaNotify.id}&">
                                                        <i class="fa fa-envelope fa-fw"></i> ${fns:abbr(oaNotify.title,50)}
                                                       </a>
                                                    <span class="pull-right text-muted small">${fns:getTime(oaNotify.updateDate)}前</span>
                                                </div>

                                        </c:forEach>

                                        </li>
                                        <li class="divider"></li>
                                        <li>
                                            <div class="text-center link-block">
                                               您有${count }条未读消息 <a class="J_menuItem" href="${ctx }/oa/oaNotify/self ">
                                                    <strong>查看所有 </strong>
                                                    <i class="fa fa-angle-right"></i>
                                                </a>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                    <div class="row content-tabs">
                        <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                        </button>
                        <nav class="page-tabs J_menuTabs">
                            <div class="page-tabs-content">
                                <a href="javascript:;" class="active J_menuTab" data-id="${ctx}/home">首页</a>
                            </div>
                        </nav>
                        <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                        </button>
                        <div class="btn-group roll-nav roll-right">
                            <button class="dropdown J_tabClose"  data-toggle="dropdown">关闭操作<span class="caret"></span>

                            </button>
                            <ul role="menu" class="dropdown-menu dropdown-menu-right">
                                <li class="J_tabShowActive"><a>定位当前选项卡</a>
                                </li>
                                <li class="divider"></li>
                                <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                                </li>
                                <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                                </li>
                            </ul>
                        </div>
                        <a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
                    </div>
                    --%>
        <div class="row J_mainContent" id="content-main">
            <iframe id="iframe0" class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/home"
                    frameborder="0" data-id="${ctx}/home" seamless></iframe>
        </div>
        <div class="footer">
            <div class="pull-left"></div>
        </div>
    </div>
    <!--右侧部分结束-->


</div>
</body>


<script type="text/javascript">

    $(document).ready(function () {

        $("a.lang-select").click(function () {
            $(".lang-selected").find(".lang-flag").attr("src", $(this).find(".lang-flag").attr("src"));
            $(".lang-selected").find(".lang-flag").attr("alt", $(this).find(".lang-flag").attr("alt"));
            $(".lang-selected").find(".lang-id").text($(this).find(".lang-id").text());
            $(".lang-selected").find(".lang-name").text($(this).find(".lang-name").text());

        });


    });

    function changeStyle() {
        $.get('${pageContext.request.contextPath}/theme/ace?url=' + window.top.location.href, function (result) {
            window.location.reload();
        });
    }

</script>


<!-- 即时聊天插件 -->
<link href="${ctxStatic}/layer-v2.0/layim/layim.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
    var currentId = '${fns:getUser().loginName}';
    var currentName = '${fns:getUser().name}';
    var currentFace = '${fns:getUser().photo}';
    var url = "${ctx}";
    var wsServer = 'ws://' + window.document.domain + ':8668';

</script>
<script src="${ctxStatic}/layer-v2.0/layim/layer.min.js"></script>
<script src="${ctxStatic}/layer-v2.0/layim/layim.js"></script>

</html>