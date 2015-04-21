<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>E景通_驴妈妈旅游网</title>
		<link href="<%=basePath%>css/e.css" rel="stylesheet" type="text/css" />
<%
String eplace_userId="";
String eplace_realName="";
String eplace_manager="";
String eplace_phone="";
com.lvmama.comm.bee.po.pass.PassPortUser passPortUser = (com.lvmama.comm.bee.po.pass.PassPortUser) session.getAttribute("SESSION_USER");
	eplace_userId=passPortUser.getUserId() != null ? passPortUser.getUserId() : "";
	eplace_realName=passPortUser.getName() != null ? passPortUser.getName() : "";
	com.lvmama.comm.bee.po.pass.EplaceSupplier eplaceSupplier = (com.lvmama.comm.bee.po.pass.EplaceSupplier) session.getAttribute("SESSION_EPLACE_SUPPLIER");
	if (eplaceSupplier != null) {
			eplace_manager=eplaceSupplier.getProductManager() !=null? eplaceSupplier.getProductManager() : "";
			eplace_phone=eplaceSupplier.getMobile() !=null? eplaceSupplier.getMobile() : "";
	}
	%>
	</head>

	<body>
		<div class="user-group">
				<span class="col1">当前用户：<em><%=eplace_userId%></em> </span>
				<span class="col2">真实姓名：<em><%=eplace_realName%></em> </span>
				<span class="col3">客户经理：<em><%=eplace_manager%></em></em>
				</span>
				<span class="col3">电话：<em><%=eplace_phone%></em>
				</span>
				<span class="col4">系统支持：<em>13524899224(陈琳君)</em></span>
			</div>
		
</html>
