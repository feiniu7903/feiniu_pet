<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购产品信息</title>
<link rel="stylesheet" type="text/css"
	href="../../themes/base/jquery.ui.all.css">
<link rel="stylesheet" type="text/css" href="../../themes/ebk/admin.css">
<script type="text/javascript" src="../../js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="../../js/base/jquery-ui-1.8.5.js"></script>
<script type="text/javascript"
	src="../../js/base/jquery.validate.min.js"></script>
<script type="text/javascript" src="../../js/base/jquery.form.js"></script>

</head>
<body>
	<input type="hidden" id="userIdHd" value="${userId}">
	<input type="hidden" id="supplierIdHd" value="${supplierId}">
	<input type="hidden" id="bizTypeHd" value="${bizType}">

	<table class="gl_table" cellspacing="0" cellpadding="0">
		<tr>
			<th width="40">采购ID</th>
			<th>采购名称</th>
			<th>类别名称</th>
			<!-- 
		<th width="60">类别ID</th>
		 -->
		</tr>

		<s:iterator value="metaProductPage.items" id="item">
			<tr>
				<td>${metaProductId}</td>
				<td>${productName}</td>
				<td>${branchName}</td>
			</tr>
		</s:iterator>
		<tr bgcolor="#ffffff">
			<td colspan="3" align="center">
				共有<s:property value="metaProductPage.totalResultSize" />个采购
				${metaProductPage.pagination }
			</td>
		</tr>
	</table>
</body>
</html>