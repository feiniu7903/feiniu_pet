<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>工单监控</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.validate.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.jsonSuggest-2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script src="<%=basePath%>js/ord/ord.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/jquery-ui-timepicker-addon.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/base/jquery.jsonSuggest.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/workorder/workorder.css"></link>
	</head>
	<body>
		找不到用户。 <s:property value="error"/>
	</body>
</html>
