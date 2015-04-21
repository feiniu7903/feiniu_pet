<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商 凭证对象</title>
</head>
<body>
<a href="javascript:void(0)" data="${supplierId}" class="addBCertificateBtn">新增对象</a>
<table class="zhanshi_table" cellspacing="0" cellpadding="0">
	<tr>
		<th>编号</th>
		<th>名称</th>
		<th>凭证方法</th>
		<th>传真号码</th>
		<th>传真策略</th>
		<th>使用模板</th>
		<th>备注</th>
		<th>操作</th>
	</tr>
	<s:iterator value="bcertificateList">
	<tr>
		<td><s:property value="targetId"/></td>
		<td><s:property value="name"/></td>
		<td><s:property value="viewBcertificate"/></td>
		<td><s:property value="faxNo"/></td>
		<td><s:property value="zhFaxStrategy"/></td>
		<td><s:property value="zhfaxTemplate"/></td>
		<td><s:property value="memo"/></td>
		<td><a href="javascript:void(0);" data="<s:property value="targetId"/>" class="editBCertificateBtn">修改</a></td>
	</tr>
	</s:iterator>
</table>
<div id="addBCertificateDiv" url="${basePath}/sup/target/toAddBCertificate.do"></div>
</body>
</html>