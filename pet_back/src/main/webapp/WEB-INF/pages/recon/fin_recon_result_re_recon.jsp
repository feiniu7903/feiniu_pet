<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><h1>重新对账</h1></title>
</head>
<body>
	<form method="post" action="" id="reReconForm">
		<table class="cg_xx" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="color: red;">提示:重新对账只会将对账状态为对账失败的数据进行重新对账</td>
			</tr>
			<tr>
				<td>对账网关: 
				<select name="gateway" style="height: 22px;width: 185px;">
					<option value="ALIPAY">支付宝</option>
					<option value="NING_BO_BANK">宁波银行</option>
					<option value="UNIONPAY">银联</option>
					<option value="CHINAPNR">汇付天下</option>
					<option value="CASH_ACCOUNT">存款账户</option>
					<option value="CASH_BONUS">奖金账户</option>
					<option value="SAND_POS">杉德POS机</option>
					<!-- <option value="TENPAY">财付通</option>-->
					<option value="NO_RECON">无需勾兑</option>
					<!-- 
					<s:iterator var="reconGateway" value="reconGateways">
						<option value="${reconGateway.code}" <s:if test="paramMap['gateway']==#reconGateway.code">selected="selected"</s:if>>${reconGateway.cnName}</option>
					</s:iterator>
					 -->
				</select>
				</td>
			</tr>
			<tr>	
				<td>对账日期: 
				<input name="bankReconTime" type="text" value="${paramMap['bankReconTime']}" id="bankReconTime" style="height: 22px;width: 85px;"/>
				</td>
			</tr>
			<tr>
				<input type="hidden" id="reconStatus" name="reconStatus" value="FAIL"/>
				<td><input id="reReconSumit" name="right_button08Submit" type="button" value="重新对账" class="right-button08" style="margin-left: 50px"/></td>
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
$(function() {
	$("input[id='bankReconTime']" ).datepicker({dateFormat:'yy-mm-dd'});
});
$("#reReconSumit").click(function(){
	if($('#bankReconTime').val()==''){
		alert('对账日期不可为空!');
		return ;
	}
	if(confirm('重新对账过程大约需要5分钟,请不要重复点击,您确定要重新对账?')){
		$('#reReconSumit').attr("disabled",true);
		$.ajax({
				type:"POST", 
				url:'http://super.lvmama.com/payment/pay/recon/accountRecon.do' + '?random=' + Math.random(), 
				data:$("#reReconForm").serialize(), 
				async: false
		});
		$('#finReconResultDiv').dialog('close');
		$('#finReconResultForm').submit();
	}
});
</script>
</html>
