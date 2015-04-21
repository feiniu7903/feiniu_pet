<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商 履行对象</title>
</head>
<body>
<div>
<a href="javascript:void(0)" class="addPerformBtn button" data="${supplierId}">新增对象</a>
</div>
<table class="zhanshi_table" cellspacing="0" cellpadding="0">
	<tr>
		<th>编号</th>
		<th>名称</th>
		<th>履行时间</th>
		<th>履行信息</th>
		<th>支付信息</th>
		<th>备注</th>
		<th>操作</th>
	</tr>
	<s:iterator value="performTargetList">
	<tr>
		<td><s:property value="targetId"/></td>
		<td><s:property value="name"/></td>
		<td><s:property value="openTime"/>~<s:property value="closeTime"/></td>
		<td><s:property value="performInfo"/></td>
		<td><s:property value="paymentInfo"/></td>
		<td><s:property value="memo"/></td>		
		<td><a href="javascript:void(0)" class="editPerformBtn" data="<s:property value="targetId"/>">修改</a></td>
	</tr>
	</s:iterator>
</table>

<div id="addPerformDiv" url="${basePath}/sup/target/toAddPerform.do"> </div>
</body>
</html>