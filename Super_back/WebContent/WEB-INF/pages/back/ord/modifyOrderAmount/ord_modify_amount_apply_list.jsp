<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() 
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		  
	</head>

	<body>
	<input type="hidden" id="orderId" value="<s:property value="orderId"/>">
		<table border="1">
			<tr>
					<td>订单号</td>
					<td>申请人</td>
					<td>审核人</td>
					<td>金额</td>
					<td>类型</td>
					<td>备注</td>
					<td>状态</td>
					<td>创建时间</td>
					<td>操作</td>
				</tr>
			<s:iterator value="ordOrderAmountApplayList">
				<tr>
					<td><s:property value="orderId"/></td>
					<td><s:property value="applayUser"/> </td>
					<td><s:property value="approveUser"/></td>
					<td><s:property value="amount"/></td>
					<td>类型</td>
					<td><s:property value="memo"/></td>
					<td><s:property value="applayStatus"/></td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><a href="#">确定</a>|<a href="#">取消</a></td>
				</tr>
			</s:iterator>
		</table>
	</body>

</html>

