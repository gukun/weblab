<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		     WinMove();
		});
	</script>
</head>
<body class="gray-bg">
   <div class="row  border-bottom white-bg dashboard-header">
        <div class="col-sm-12">
            <blockquote class="text-info" style="font-size:14px">虚拟网络实验室
                        <br>在你身边的网络实验室，在这里你能随时随地第进行课程实验内容。
            </blockquote>

            <hr>
        </div>
    </div>
      
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-4">

                <div class="ibox float-e-margins">
                     <div class="ibox-title">
                        <h5>xxxxx</h5> 
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="index.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a href="index.html#">选项1</a>
                                </li>
                                <li><a href="index.html#">选项2</a>
                                </li>
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        这里可以放一些实验的说明、声明等信息。
                    </div>
                </div>
              
            </div>
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                     <div class="ibox-title">
                        <h5>网络实验室的介绍</h5> <span class="label label-primary">K+</span>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="index.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a href="index.html#">选项1</a>
                                </li>
                                <li><a href="index.html#">选项2</a>
                                </li>
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="panel-body">
                            <div class="panel-group" id="version">
                            		<div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h5 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#version" href="#v2.2">第一条</a><code class="pull-right">2016.04.13</code>
                                            </h5>
                                    </div>
                                    <div id="v2.2" class="panel-collapse collapse in">
                                        <div class="panel-body">
                                            内容1：主要内容
                                        </div>
                                    </div>
                                </div>
                                
                            	<div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h5 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#version" href="#v2.0">第二条</a><code class="pull-right">2016.04.25</code>
                                            </h5>
                                    </div>
                                    <div id="v2.0" class="panel-collapse collapse in">
                                        <div class="panel-body">
                                            内容2：主要内容
                                        </div>
                                    </div>
                                </div>
                </div>
            </div>
            </div>
            </div>
            </div>
            
        </div>
    </div>

</body>
</html>