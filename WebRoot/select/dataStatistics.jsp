<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>教师评价系统后台操作页面</title>
<link rel="stylesheet" href="css/basic.css" />
<link rel="stylesheet" href="css/style.css" />
<!--<link rel="stylesheet" href="css/style-metro.css"/>-->
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/bootstrap-table.min.css" />
<link href="css/font-awesome.css" rel="stylesheet" />
</head>
<body>
	<%
		HttpSession s = request.getSession();
		String username = (String) s.getAttribute("username");
		String password = (String) s.getAttribute("password");
		if (username != null && password != null && ("admin".equals(username) || "jxgl".equals(username))) {
	%>
	<div id="wrapper">
		<nav class="navbar  navbar-cls-top " role="navigation"
			style="margin-bottom: 0">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".sidebar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">翡翠教育</a>
		</div>
		</nav>
		<!-- 导航 -->
		<nav class="navbar-default navbar-side" role="navigation">
		<div class="sidebar-collapse">
			<ul class="nav" id="main-menu">
				<!--查询主页面的侧边栏按钮-->
				<li><a href="queryExport.jsp"><i class="fa fa-dashboard"></i>查询导出</a>
				</li>
				<!--编辑菜单栏按钮-->
				<li><a href="abnormal.jsp"><i class="fa fa-anchor"></i>异常数据</a></li>
				<li><a href="teacherManage.jsp"><i class="fa fa-edit "></i>老师管理</a>
				</li>
				<li><a href=" dataAnalysis.jsp"><i
						class="fa fa-bar-chart-o"></i>数据分析</a></li>
				<li><a class="active-menu" href="dataStatistics.jsp"><i
						class="fa fa-key"></i>数据统计</a></li>
				<li><a href="exportTable.jsp"><i class="glyphicon glyphicon-file"></i>导出表格</a></li>
			</ul>
		</div>

		</nav>
		<!-- 主页面  -->
		<div id="page-wrapper">
			<div id="page-inner">
				<!--主页面头部-->
				<div>
					<div class="col-md-12">
						<!--<h1 class="page-subhead-line">请选择查询条件并编辑：</h1>-->
					</div>
				</div>

				<div class="portlet box grey">
					<div class="portlet-title">
						<div class="caption" style="width:100%;">
							<i class="icon-user"></i>数据统计
							<div class="div123">
							<iframe src="http://120.77.180.125:8000/research/queryExport.php" width="100%" height="890" scrolling="no" frameborder="no"></iframe>
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
	<!--尾部-->
	<div id="footer-sec">
		<div class="footer">
			<div class="innerContainer">
				<p style="text-align:center;">总部地址:北京市海淀区小南庄怡秀园甲一号亿德大厦10层
					电话：010-61943240</p>
				<p style="text-align:center;">Copyright © 2005-2020
					北京翡翠教育科技有限公司，All Rights Reserved 京ICP备12036804号-23</p>
			</div>
		</div>
	</div>
	<script src="js/jquery-1.9.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-table.min.js"></script>
	<script src="js/bootstrap-table-zh-CN.min.js"></script>
	<script src="js/WdatePicker.js"></script>
	<script src="js/tableExport.min.js"></script>
	<script src="js/bootstrap-table-export.min.js"></script>
	<script src="js/src/abnormal.js"></script>
	
	<%
		} else {
			response.sendRedirect("../Login.html");
		}
	%>
	
	
</body>
</html>