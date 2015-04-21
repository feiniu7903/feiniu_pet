<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商 履行对象</title>
</head>
<body>
<form method="post" action="${basePath}/sup/target/savePerform.do" onsubmit="return false">
<s:hidden name="performTarget.supplierId"/><s:hidden name="performTarget.targetId"/>
	<table style="width:100%" class="cg_xx" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>对象名称:</td><td><s:if test="performTarget.targetId==null">自动生成</s:if><s:else>${performTarget.name}</s:else></td>
		</tr>
		<tr>
			<td><font color="red">*</font>履行方式:</td><td><s:radio list="certificateTypeList" listKey="code" listValue="cnName" name="performTarget.certificateType"/></td>
		</tr>
		<tr>
			<td>履行时间：</td><td><s:textfield name="performTarget.openTime"/>~<s:textfield name="performTarget.closeTime"/></td>
		</tr>
		<tr>
			<td>支付信息:</td><td><s:textarea cols="30" rows="3" name="performTarget.paymentInfo"/></td>
		</tr>
		<tr>
			<td>履行信息:</td><td><s:textarea cols="30" rows="3" name="performTarget.performInfo"/></td>
		</tr>
		<tr>
			<td>备注:</td><td><s:textarea cols="30" rows="3" name="performTarget.memo"/></td>
		</tr>
		<tr>
			<td>传真联系人：<s:hidden name="contactListId"/></td>
			<td>
			<div id="contact_show_pos">
			<s:iterator value="contactList">
				<div contactId="<s:property value="contactId"/>"><span><s:property value="toHtml()" escape="false"/></span><a href='javascript:void(0)' class='deleteRelation'>删除</a></div>
			</s:iterator></div><input type="button" value="绑定联系人" class="bindContactBtn button"/>
			</td>
		</tr>
	</table><br/>
	<div><input type="submit" value="保存" class="performSubmit button"/></div>
</form>
</body>
</html>