<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp" %>
<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" " http://www.w3.org/TR/html4/strict.dtd"> -->
<html>
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>虚拟网络实验室--登录</title>
	<script src="${ctxStatic}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.14.0/jquery.validate.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.14.0/localization/messages_zh.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/bootstrap/3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap/3.3.4/css_default/bootstrap.min.css" type="text/css" rel="stylesheet"/>

	<link rel="stylesheet" type="text/css" href="${ctxStatic}/common/login/login_new.css" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/common/login/PIE.htc" />

    <script>
        if (window.top !== window.self) {
            window.top.location = window.location;
        }

        // 如果在框架或在对话框中，则弹出提示并跳转到首页
        if (self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0) {
            alert('未登录或登录超时。请重新登录，谢谢！');
            top.location = "${ctx}";
        }
    </script>

    <script type="text/javascript">
        $(document).ready(function() {
            //新的登录页面的js add by gukun
            $(window).resize(function(){
                $(".loginMainBox").find(".container").css({
                    height: ($(window).height()-120) + "px"
                });
                $(".loginFormNew,.loginImgBox").css({
                    top: (($(window).height()-120)*0.2) + "px"
                });
            });
            $(window).resize();

            //输入框提示
            $(".tipInt").each(function(){
                var $input=$(this);
                $input.css("color","#bbb");
                var oldText=$.trim($input.val());
                $input.focus(function(){
                    var newText=$.trim($input.val());
                    if (newText==oldText){
                        $input.val("");
                    }
                    $input.css("color","#000");
                });
                $input.blur(function(){
                    var newText=$.trim($input.val());
                    if(newText==""){
                        $input.val(oldText);
                        $input.css("color","#bbb");
                    }
                });
            });

            var $showpwd_obj=$('#show_pwd');//获取id为show_pwd的jquery对象
            var $pswd_obj=$('#pwd_p');//获取id为pwd_p的jquery对象
            $showpwd_obj.focus(function(){
                $pswd_obj.show().focus();//使密码输入框获取焦点

                $showpwd_obj.hide();//隐藏文本输入框

            });
            $pswd_obj.blur(function(){
                if($pswd_obj.val()==''){//密码输入框失去焦点后，若输入框中没有输入字符时，则显现文本输入框
                    $showpwd_obj.show();
                    $pswd_obj.hide();
                }

            });

        });

        function changeVerifyCodeImage() {
            var verify=document.getElementById('vcode');
            verify.setAttribute('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());
        }

        function addFav(){
            var url=window.location.href;
            window.external.addFavorite(url,'虚拟网络实验室');//只支持ie浏览器
        }

        function loginCheck(){
            var frm = document.getElementById('loginForm');
            frm.submit();
        }

        function enter(e){
            var theEvent = window.event || e;
            var code = theEvent.keyCode || theEvent.which;
            if (code == 13){
                var name = $("#fld_lu").val();
                var pwd = $("#pwd_p").val();
                if(name=!null && pwd!=null && name!="" && pwd!=""){
                    loginCheck();
                }else{
//                    alert("不能为空！");
                    return false;
                }

            }
        }
    </script>
<style>
html { overflow-x: hidden; overflow-y: hidden; } 
</style>
</head>
<body onkeydown="enter(event);">
<form id="loginForm" action="${ctx}/login" method="post">

<div id="loginWrap" class="loginWrap"  style="background-color: #BBD6FF">
	<div class="loginTopBar"  style="background-color: #BBD6FF">
    <div class="container">
      <h1 class="left">
        <%--<span class="sysLogo"></span>--%>
        <span style="color: #0B61A4">虚拟网络实验室</span>

      </h1>
      <ul class="helpBar right">
        <li><a href="#" onclick="addFav();">加入收藏</a></li>
      </ul>
    </div>
  </div>
  <div class="loginMainBox">
    <div class="container">
      <div class="loginFormNew">
        <h2>&nbsp;用户登录</h2>
          <br>
        <ul>
        	<li>
	          	<!--告警提示-->
                <%--<sys:message content="${message}"/>--%>
	          	<c:if test="${message!=null}">
	          	<div id="messageBox" class="BubbleTip">
                   <p>${message}</p>
	            </div>
	            </c:if>
          		<input type="text" name="username" id="fld_lu" class="logInt tipInt" tabindex="1" value="用户名"/>
          </li>
          
        	<li>
          		<input type="text" id="show_pwd" autoComplete="off" class="logInt tipInt" tabindex="2" value="密码"/>
   				<input type="password" name="password" id="pwd_p" class="logInt" style="display: none;" autoComplete="off" />
          	</li>
          
          	<!--验证码-->
            <c:if test="${isValidateCodeLogin}">
        		<li id="vCodeBox" >
        			<input type="text" name="validateCode" id="verifyCode" class="shortInt tipInt" value="验证码" />
        			<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="vcode" title="验证码,不区分大小写" class="vCode"/>
                    <a href="javascript:changeVerifyCodeImage();">看不清</a>
          		</li>
          	</c:if>
          	<!--验证码-->         
        </ul>         
        <div class="logInfoBar">
            <div style="float: left;width: 50%;">
                <input type="checkbox" id="rememberMe"
                       name="rememberMe" ${rememberMe ? 'checked' : ''}
                       class="ace"/>
                <span class="lbl"> 记住我</span>
            </div>
            <%--<div id="themeSwitch" class="dropdown" style="width: 50%;float: right;margin-top: 5px;">
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
            </div>--%>

        </div>
        <input id="loginBtn" class="btnPrimary" type="button" onClick="loginCheck()" value="登录" />
      </div>   
      <div class="loginImgBox">
        <div class="imgCont"></div>
        <ul class="titleTxt">
          <li class="txtA">虚拟网络实验室</li>
          <li class="txtB">就/在/你/身/边</li>
        </ul>

      </div>
    </div>
    <div class="imgCirBg"><span class="cirSliceA"></span><span class="cirSliceB"></span><span class="cirSliceC"></span><span class="cirSliceD"></span></div> 
	</div>
	
	<div id="footerCopy" class="footerCopy">

	  <div class="container">
	      <div  style="width: 33%;float: left;"><span style="color: orangered">注意：请使用IE8及以上浏览器</span></div>
          <div  style="width: 33%;float: left;text-align: center"><span style="color: #1a1a1a">&copy; XXXXX公司</span></div>
	      <div class="right">
	        友情链接：<a href="http://www.baidu.com" target="_blank">百度</a>
	      </div>
	  </div>
	</div>
	
</div>

</form>

<div id="bulletin">
</div>

</body>

</html>
