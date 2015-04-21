<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>绑定联系人</title>
</head>
<body>
<table style="width:100%" class="zhanshi_table">
	<s:iterator value="contactList" var="c">
		<tr>
			<td style="width:20px;"><input type="checkbox" name="contactId"
				<s:if test="selectContact(#c.contactId)">checked</s:if>
				value="<s:property value="contactId"/>" /></td>
			<td style="text-align:left;" nowrap="nowrap" id="td_contactId_<s:property value="contactId"/>"><s:property
					value="toHtml()" escape="false" /></td>
		</tr>
	</s:iterator>
</table>
</body>
</html>