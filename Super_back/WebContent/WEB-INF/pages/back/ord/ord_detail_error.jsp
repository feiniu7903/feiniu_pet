<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>查看订单明细</title>
		<script type="text/javascript"	src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script language="javascript" src="<%=basePath%>js/ord/ord.js"	type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>js/ord/in_add.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"	href="<%=basePath%>themes/cc.css" />
		<link rel="stylesheet"	href="<%=basePath%>themes/base/jquery.ui.all.css" /><script type="text/javascript" src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript"	src="<%=basePath%>js/base/lvmama_common.js"></script>
		<script type="text/javascript"	src="<%=basePath%>js/base/lvmama_dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
	</head>
	<body>
		<!--=========================我的历史订单弹出层==============================-->
	<div class="orderpoptit">
		<strong>订单异常</strong>
		<p class="inputbtn">
				<input type="button" name="btnCloseOrder" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('historyDiv');">
			</p>
	</div>
	<div class="orderpopmain" style="height:100px;">
		${jsonString }
	</div>
	</body>
</html>

