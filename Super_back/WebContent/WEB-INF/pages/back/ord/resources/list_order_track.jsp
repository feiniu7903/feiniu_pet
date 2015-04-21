<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>
	<body>
		<s:form name='frmOrdSms' action='saveOrdTarck.do' theme="simple"
			namespace="/ord">
			<input type="hidden" name="orderId" value="${orderId}">
			<div class="orderpoptit">
				<strong>领取处理日志</strong>
				<p class="inputbtn">
					<input type="button" name="btnCloseDetailDiv" class="button"
						value="关闭" onclick="javascript: closeDetailDiv('followLogDiv');" />
				</p>
			</div>
			<div class="orderpopmain">
				<div class="popbox">
				<br/>
					<%@ include
						file="/WEB-INF/pages/back/ord/resources/tarct_log_list.jsp"%>
				</div>
				<br />
			</div>
		</s:form>
	</body>
</html>
