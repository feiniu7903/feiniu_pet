<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script type="text/javascript">
$(function() {
	$(document).ready(function() {
		$('#receiverId').val($('#usrReceiverSelect option').first().val());
	});
});

function selectReceiver(obj) {
	$('#receiverId').val($(obj).val());
}
</script>
	</head>
	<body>
		<s:select id="usrReceiverSelect" value="receiverId"
			list="usrReceiversList" listKey="receiverId" listValue="receiverName"
			onchange="selectReceiver(this);"></s:select>
		<s:hidden id="receiverId" />
	</body>
</html>
