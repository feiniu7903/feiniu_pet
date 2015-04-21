<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><h1>查看解款单</h1></title>
</head>
<body>
	<form method="post" action="" id="viewForm">
		<table class="cg_xx" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>订单号: ${cashPaymentComboVO.objectId}</td>
				<td>户名: ${cashPaymentComboVO.receivingAccount}</td>
			</tr>
			<tr>	
				<td>解款金额: 
					<s:if test="cashPaymentComboVO.amount!=null">
					${cashPaymentComboVO.amount/100}
					</s:if>
					<s:else>
						0
					</s:else>
				</td>
				<td>银行: ${cashPaymentComboVO.bankName}</td>
			</tr>
			<tr>
				<td>解款日期: <s:date format="yyyy-MM-dd" name="cashPaymentComboVO.cashLiberateMoneyDate"></s:date></td>
				<td>账户性质:${cashPaymentComboVO.receivingAccountType}</td>
			</tr>
			<tr>
				<td>银行交易流水号: ${cashPaymentComboVO.gatewayTradeNo}</td>
				<td>银行卡号: <span id="bankCardNo">${cashPaymentComboVO.bankCardNo}</span></td>
			</tr>
			<tr>
				<input type="hidden" value="${cashPaymentComboVO.paymentId}" name="cashPaymentComboVO.paymentId"/>
				<input type="hidden" value="${cashPaymentComboVO.cashAuditStatus}" name="cashPaymentComboVO.cashAuditStatus"/>
				<td colspan="2" align="center"">
					<input name="right_button08Submit" type="button" value="关闭" class="right-button08" onclick="$('#cashPaymentMonitorDiv').dialog('close')" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
