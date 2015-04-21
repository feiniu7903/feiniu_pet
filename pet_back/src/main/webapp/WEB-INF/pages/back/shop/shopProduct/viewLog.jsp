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
<title>操作日志：</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>

<jsp:include page="/WEB-INF/pages/pub/jquery.jsp" />
<jsp:include page="/WEB-INF/pages/pub/suggest.jsp" />
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />

<script type="text/javascript">
	$(function(){
	});
</script>
</head>
<body>
	<div class="p_box">
	<font>操作日志：</font>
	<br/>
		<table class="p_table table_center">
			<tr>
				<th width="100px">操作人</th>
				<th width="100px">操作内容</th>
				<th width="100px">操作日期</th>
			</tr>
			<s:iterator value="logList" var="shopLog">
				<tr>
					<td>${shopLog.operatorId}</td>
					<td>${shopLog.content}</td>
					<td>${shopLog.showDate}</td>
				</tr>
			</s:iterator>
	    </table>
	</div>
</body>
</html>