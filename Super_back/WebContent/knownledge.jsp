<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta>
<title></title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/default/easyui.css">
	<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/base/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	<div region="west" split="true" title="导航" style="width:150px;padding:10px;"></div>
	<div region="center" title="内容" href="<%=basePath%>kn/main.do"  style="overflow:hidden;">
	
	</div>
</body>
</html>
