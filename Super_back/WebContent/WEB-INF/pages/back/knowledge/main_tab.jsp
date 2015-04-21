<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/default/easyui.css">
	<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/base/jquery.easyui.min.js"></script>
	
  </head>
  
  <body style="padding: 0 0 0 0;margin: 0 0 0 0;" class="easyui-layout">
  <div region="center">
  <div id="tt" class="easyui-tabs"  fit="true" border="false">
		<div title="帮助中心" style="padding:10px;overflow:hidden;">
		<iframe  scrolling="yes" frameborder="0" id="iframe_帮助中心"  src="/pet_back/help/goToHelpCenter.do" style="width:100%;height:100%;"></iframe>
		</div>
	</div>
	</div>
  </body>
</html>
