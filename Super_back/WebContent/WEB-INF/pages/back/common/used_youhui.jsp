<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<em>已使用的优惠券(活动):</em>
	<s:iterator value="listAmountItem">
		<label> ${itemName} 优惠金额：<font color="red">${itemAmount/100}</font>
		</label>
		<br />
	</s:iterator>
</body>
</html>
