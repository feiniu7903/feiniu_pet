<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>

	<body>
		<div class="total">
			<strong>应付总计金额：<b>${info.shifu }</b>元 </strong><b>总计：</b>订单总金额
			<span style="color:red;">${info.allSellPrice }</span>元&nbsp;-&nbsp;订单优惠金额
			<span style="color:red;"><s:if test="info.allOrderYouhui==''">0</s:if> <s:else>
					<s:property value="info.allOrderYouhui" />
				</s:else> </span>元&nbsp;-&nbsp;现金账户扣款
			<span style="color:red;"><s:if test="info.cash==''">0</s:if> <s:else>
					${info.cash }
				</s:else> </span>元
		</div>
	</body>
</html>
