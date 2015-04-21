<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/place/place.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/place/backstage_table.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/place/houtai.js"></script>
<title>PC内容，目的地图片</title>
</head>
<body>
	图片过大  <a href='javascript:history.go(-1);'>返回</a>
</body>
</html>