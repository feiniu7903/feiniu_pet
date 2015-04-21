<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商联系人管理</title>
</head>
<body>
	<div>
		<a href="javascript:void(0)" data="${supplierId}" class="addContactBtn button">新增联系人</a>&nbsp;
	</div><s:set var="currentSupplierId">${supplierId}</s:set>
	<div><form onsubmit="return false" id="contactForm">
		<table style="width:150%" class="zhanshi_table" cellspacing="0" cellpadding="0">
			<tr>
				<th>联系人姓名</th>
				<th>电话</th>
				<th>性别</th>
				<th>手机</th>
				<th>说明</th>
				<th>职务</th>
				<th>Email</th>
				<th>地址</th>
				<th>其他联系方式</th>
				<th>操作</th>
			</tr>
			<s:iterator value="contactList">
			<tr>
				<td><s:property value="name"/></td>
				<td><s:property value="telephone"/></td>
				<td><s:property value="zhSex"/></td>
				<td><s:property value="mobilephone"/></td>
				<td><s:property value="memo"/></td>
				<td><s:property value="title"/></td>
				<td><s:property value="email"/></td>
				<td><s:property value="address"/></td>
				<td><s:property value="otherContact"/></td>
				<td><a href="javascript:void(0)" class="editContactBtn" supplierId="${currentSupplierId}" data="<s:property value="contactId"/>">修改</a></td>
			</tr>
			</s:iterator>
		</table></form>
	</div>
<div id="addContactDiv" url="${basePath}/sup/contact/toAddContact.do"></div>
</body>
</html>
