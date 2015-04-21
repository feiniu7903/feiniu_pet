<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><h1>银行解款</h1></title>
</head>
<body>
	<form method="post" action="" id="liberateForm">
		<table class="cg_xx" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>订单号: ${cashPaymentComboVO.objectId}</td>
				<td>户名: 
				<select name="receivingAccount" id="receivingAccountSelect">
					<s:iterator var="receivingAccount" value="receivingAccountList">
						<option value="${receivingAccount}" <s:if test="cashPaymentComboVO.receivingAccount==#receivingAccount">selected="selected"</s:if>>${receivingAccount}</option>
					</s:iterator>
				</select>
				</td>
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
				<td>银行: 
				<select name="bankName" id="bankNameSelect">
					<s:iterator var="bankName" value="bankNameList">
						<option value="${bankName}" <s:if test="cashPaymentComboVO.bankName==#bankName">selected="selected"</s:if>>${bankName}</option>
					</s:iterator>
				</select>
				</td>
			</tr>
			<tr>
				<td>解款日期: 
				<input name="cashPaymentComboVO.cashLiberateMoneyDate" type="text" id="cashLiberateMoneyDate" style="height: 22px;width: 85px;" value="<s:date format="yyyy-MM-dd" name="cashPaymentComboVO.cashLiberateMoneyDate"></s:date>"/>
				</td>
				<td>账户性质: 
				<select name="payReceivingBank.bankCardNo" id="receivingAccountTypeSelect">
					<s:iterator var="receivingAccountTypes" value="receivingAccountTypeList">
						<option value="${receivingAccountTypes.bankCardNo}" <s:if test="cashPaymentComboVO.receivingAccountType==#receivingAccountTypes.receivingAccountType">selected="selected"</s:if>>${receivingAccountTypes.receivingAccountType}</option>
					</s:iterator>
				</select>
				</td>
			</tr>
			<tr>
				<td>银行交易流水号: 
				<input name="cashPaymentComboVO.gatewayTradeNo" type="text" id="gatewayTradeNo" value="${cashPaymentComboVO.gatewayTradeNo}"/>
				</td>
				<td>银行卡号:
					<span id="bankCardNo">${bankCardNo}</span>
				</td>
			</tr>
			<tr>
				<input type="hidden" value="${cashPaymentComboVO.paymentId}" name="cashPaymentComboVO.paymentId"/>
				<input type="hidden" value="${cashPaymentComboVO.cashAuditStatus}" name="cashPaymentComboVO.cashAuditStatus"/>
				<td colspan="2" align="center"">
					<input name="right_button08Submit" type="button" value="确定" class="right-button08" id="liberateSubmit"/>
					<input name="right_button08Submit" type="button" value="取消" class="right-button08" onclick="$('#cashPaymentMonitorDiv').dialog('close')" />
				</td>
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
$(function() {
	$("input[id='cashLiberateMoneyDate']" ).datepicker({dateFormat:'yy-mm-dd'});
});
$("#liberateSubmit").click(function(){
	if($('#cashLiberateMoneyDate').val()==''){
		alert('解款日期不可为空!');
		return ;
	}
	if($('#gatewayTradeNo').val()==''){
		alert('银行交易流水号不可为空!');
		return ;
	}
	$.ajax({
			type:"POST", 
			url:'${basePath}/offlinepaymentmonitor/cash_payment_monitor!liberate.do' + '?random=' + Math.random(), 
			data:$("#liberateForm").serialize(), 
			async: false, 
			success:function (result) {
				alert(eval(result));
				$('#cashPaymentMonitorDiv').dialog('close');
				$('#cashPaymentMonitorForm').submit();
			}
	});
});
$("#receivingAccountSelect").change(function (){
	$("#bankNameSelect").empty();
	$.post("${basePath}/offlinepaymentmonitor/cash_payment_monitor!loadBankNames.do",{"payReceivingBank.receivingAccount":$(this).val()},function(data){
		var len=data.length;
		for(var i=0;i<len;i++){
			var $opt=$("<option/>");
			$opt.val(data[i].value);
			$opt.text(data[i].value);
			$("#bankNameSelect").append($opt);
		}
		$("#bankNameSelect").trigger("change");
	},"json");
});
$("#bankNameSelect").change(function (){
	$("#receivingAccountTypeSelect").empty();
	$.post("${basePath}/offlinepaymentmonitor/cash_payment_monitor!receivingAccountTypes.do",{"payReceivingBank.receivingAccount":$('#receivingAccountSelect').val(),"payReceivingBank.bankName":$(this).val()},function(data){
		var len=data.length;
		for(var i=0;i<len;i++){
			var $opt=$("<option/>");
			$opt.val(data[i].id);
			$opt.text(data[i].value);
			$("#receivingAccountTypeSelect").append($opt);
		}
		$("#receivingAccountTypeSelect").trigger("change");
	},"json");
});
$("#receivingAccountTypeSelect").change(function (){
	$("#bankCardNo").text($(this).val());
});
</script>
</html>
