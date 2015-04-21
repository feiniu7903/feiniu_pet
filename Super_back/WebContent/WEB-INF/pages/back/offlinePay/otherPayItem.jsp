<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>

<s:if test="payPaymentGatewayElement==null || 'ENABLE'!=payPaymentGatewayElement.status">
<tr bgcolor="#ffffff" style="height: 30px">
	<td style="width: 100px">对账流水号: <b style="color:red"> *</b></td>
	<td><input type="text" name="paymentTradeNo" id="paymentTradeNo" /></td>
</tr>
<tr bgcolor="#ffffff" style="height: 30px">
	<td style="width: 110px">网关交易号: <b style="color:red"> *</b></td>
	<td><input type="text" name="gatewayTradeNo" id="gatewayTradeNo" /></td>
</tr>
<tr bgcolor="#ffffff" style="height: 30px">
	<td style="width: 100px">退款流水号: <b style="color:red"> *</b></td>
	<td><input type="text" name="refundSerial" id="refundSerial" /></td>
</tr>
<tr bgcolor="#ffffff" style="height: 30px">
	<td style="width: 100px">交易时间: <b style="color:red"> *</b></td>
	<td><input type="text" name="callbackTime" id="callbackTime"class="dateYMD" /></td>
</tr>
<tr bgcolor="#ffffff" style="height: 30px">
	<td style="width: 100px">备注:</td>
	<td><input type="text" name="memo" id="memo"/></td>
</tr>
</s:if>

<s:else>
	<s:if test="'EXCHANGE'==payPaymentGatewayElement.gateway">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>我方银行户名:</td>
		<td><input type="text" name="receivingAccount" id="receivingAccount" value="上海驴妈妈兴旅国际旅行社有限公司" readonly="readonly"/></td>
	</tr>
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>我方银行名称:</td>
		<td><input type="text" name="receivingBankName" id="receivingBankName" value="建行长征支行" readonly="readonly"/></td>
	</tr>
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>我方银行账号:</td>
		<td><input type="text" name="receivingBankCardNo" id="receivingBankCardNo" value="31001633804052512924" readonly="readonly"/></td>
	</tr>
	</s:if>
	
	<s:if test="'TRUE'==payPaymentGatewayElement.isPaymentAccount">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>对方银行户名: <b style="color:red"> *</b></td>
		<td><input type="text" name="paymentAccount" id="paymentAccount" /></td>
	</tr>
	</s:if>
	<s:if test="'TRUE'==payPaymentGatewayElement.isPaymentBankName">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>对方银行名称: <b style="color:red"> *</b></td>
		<td><input type="text" name="paymentBankName" id="paymentBankName" /></td>
	</tr>
	</s:if>
	<s:if test="'TRUE'==payPaymentGatewayElement.isPaymentBankCardNo">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>对方银行账号: <b style="color:red"> *</b></td>
		<td><input type="text" name="paymentBankCardNo" id="paymentBankCardNo" /></td>
	</tr>
	</s:if>
	
	<s:if test="'TRUE'==payPaymentGatewayElement.isPaymentTradeNo">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>对账流水号: <b style="color:red"> *</b></td>
		<td><input type="text" name="paymentTradeNo" id="paymentTradeNo" /></td>
	</tr>
	</s:if>
	<s:if test="'TRUE'==payPaymentGatewayElement.isGatewayTradeNo">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>网关交易号: <b style="color:red"> *</b></td>
		<td><input type="text" name="gatewayTradeNo" id="gatewayTradeNo" /></td>
	</tr>
	</s:if>
	<s:if test="'TRUE'==payPaymentGatewayElement.isRefundSerial">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>退款流水号: <b style="color:red"> *</b></td>
		<td><input type="text" name="refundSerial" id="refundSerial" /></td>
	</tr>
	</s:if>
	<s:if test="'TRUE'==payPaymentGatewayElement.isReceivingCompany">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>收款公司: <b style="color:red"> *</b></td>
		<td colspan="3">
			<select name="receivingCompanyId" id="receivingCompanyId">
			<s:iterator var="payReceivingCompany" value="payReceivingCompanyList">
				<option value="${payReceivingCompany.receivingCompanyId}">${payReceivingCompany.receivingCompanyName}</option>
			</s:iterator>
			</select>
		</td>
	</tr>
	</s:if>
	<s:if test="'TRUE'==payPaymentGatewayElement.isReceivingPerson">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>收款人: <b style="color:red"> *</b></td>
		<td colspan="3">
		<input type="text" id="receivingPerson" name="receivingPerson" class="searchInput" autocomplete="off" style="height: 25px"/>
		</td>
	</tr>
	</s:if>
	<s:if test="'TRUE'==payPaymentGatewayElement.isCallbackTime">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>交易时间: <b style="color:red"> *</b></td>
		<td><input type="text" name="callbackTime" id="callbackTime"class="dateYMD" /></td>
	</tr>
	</s:if>
	
	
	<s:if test="'TRUE'==payPaymentGatewayElement.isBelongCenter">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>所属中心: <b style="color:red"> *</b></td>
		<td colspan="3"><input type="text" name="belongCenter" id="belongCenter"/></td>
	</tr>
	</s:if>
	<s:if test="'TRUE'==payPaymentGatewayElement.isBelongDepartment">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>所属部门: <b style="color:red"> *</b></td>
		<td colspan="3"><input type="text" name="belongDepartment" id="belongDepartment"/></td>
	</tr>
	</s:if>
	<s:if test="'TRUE'==payPaymentGatewayElement.isDetailName">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>名称: <b style="color:red"> *</b></td>
		<td colspan="3"><input type="text" name="detailName" id="detailName"/></td>
	</tr>
	</s:if>
	<s:if test="'TRUE'==payPaymentGatewayElement.isSummary">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>摘要: <b style="color:red"> *</b></td>
		<td colspan="3"><input type="text" name="summary" id="summary"/></td>
	</tr>
	</s:if>
	<s:if test="'TRUE'==payPaymentGatewayElement.isCallbackInfo">
	<tr bgcolor="#ffffff" style="height: 30px">
		<td>备注:</td>
		<td colspan="3"><input type="text" name="memo" id="memo"/></td>
	</tr>
	</s:if>
</s:else>
<script type="text/javascript">
$(document).ready(function(){
	$("input.dateYMD").datetimepicker({
		showSecond: true,
		timeFormat: 'hh:mm:ss',
		stepHour: 1,
		stepMinute: 1,
		stepSecond: 1,
		showButtonPanel:true
	});
})
$("#receivingPerson").jsonSuggest({
	url:"<%=basePath%>offlinePay/searchUserName.do",
	maxResults: 20,
	minCharacters:1,
	onSelect:function(item){
		$("#receivingPerson").val(item.id);	
	}
});
</script>