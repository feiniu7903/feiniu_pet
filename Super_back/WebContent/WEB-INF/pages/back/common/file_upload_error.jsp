<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>文件列表</title>
	</head>
	<body>
	<h2>您访问的参数不正确</h2>
	<div><s:if test="errorMessages">${errorMessages}</s:if></div>
	<a href="javascript:window.close();">关闭</a>
	</body>
</html>