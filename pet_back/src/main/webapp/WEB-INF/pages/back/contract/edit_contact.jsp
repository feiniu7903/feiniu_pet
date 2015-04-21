<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新增联系人</title>
</head>
<body>
	<div>
		<form action="${basePath }/contract/doAddContact.do" method="post"
			id="addContactForm">
			<table class="cg_xx" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td><span class="required">*</span>姓名：</td>
					<td><s:textfield name="contact.name"
							data-options="required:true" cssClass="easyui-validatebox" maxlength="40" /></td>
					<td><span class="required">*</span>电话：</td>
					<td><s:textfield name="contact.telephone"
							data-options="required:true" cssClass="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td><span class="required">*</span>说明</td><td><s:textfield name="contact.memo" cssClass="required"/></td>
					<td></td>
					<td><input value="保存" type="button" class="button" id="saveContactBtn" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>