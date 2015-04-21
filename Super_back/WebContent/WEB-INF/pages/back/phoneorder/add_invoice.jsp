<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	</head>
	<body>
		<s:form id="addInvoiceForm" theme="simple">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						发票抬头：
					</td>
					<td>
						<input name="oinvoice.title" id="invoicedetail" type="text"
							class="text2" />
					</td>
					<td>
						发票内容：
					</td>
					<td>
						&nbsp;
						<s:select list="invoiceDetails" name="oinvoice.detail"
							listKey="code" listValue="name" />
					</td>
					<td>
						发票金额：
					</td>
					<td>
						<input name="oinvoice.amount" id="invoiceamount" type="text"
							class="text3" />
					</td>
				</tr>
				<tr>
					<td>
						<span>发票备注：</span>
					</td>
					<td colspan="6">
						<textarea name="oinvoice.memo" id="invoicememo" cols="remarks"
							rows="remarks"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="6" class="anniu">
						<input type="button" value="保存" class="button"
							onclick="addOrderInvoice();" />
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>
