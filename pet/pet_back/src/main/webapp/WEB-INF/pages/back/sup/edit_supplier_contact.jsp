<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商 管理联系人</title>
</head>
<body>
<form action="${basePath}/sup/contact/saveContact.do" onsubmit="return false">
<s:hidden name="contact.supplierId"/><s:hidden name="contact.contactId"/>
<table style="width:90%" class="cg_xx" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><span class="required">*</span>联系人姓名</td><td><s:textfield name="contact.name" cssClass="required maxlength40" maxlength="40"/></td>
		<td><span class="required">*</span>电话</td><td><s:textfield name="contact.telephone" cssClass="required" maxlength="30" /></td>
	</tr>
	<tr>
		<td><span class="required">*</span>职务</td><td><s:textfield name="contact.title" cssClass="required"/></td>
		<td><span class="required">*</span>性别</td><td><s:select list="sexList" listKey="code" listValue="chName" name="contact.sex" cssClass="required"/></td>
	</tr>
	<tr>
		<td>手机</td><td><s:textfield name="contact.mobilephone"/></td>
		<td><span class="required">*</span>说明</td><td><s:textfield name="contact.memo" cssClass="required"/>(请填写联系人作用，比如结算请联系此人)</td>
	</tr>
	<tr>
		<td>地址</td><td><s:textfield name="contact.address"/></td>
		<td>Email</td><td><s:textfield name="contact.email" cssClass="email" /></td>
	</tr>
	<tr>
		<td>其他联系方式</td><td><s:textfield name="contact.otherContact"/></td>
		<td colspan="2"><input type="submit" value="保存" class="contactSubmit button"/></td>
	</tr>
</table>
</form>
</body>
</html>