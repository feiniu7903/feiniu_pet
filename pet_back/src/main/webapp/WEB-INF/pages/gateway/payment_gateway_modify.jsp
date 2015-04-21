<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>网关管理修改</title>
</head>
<body>
	<form method="post" action="" id="modifyForm">
		<table class="cg_xx" width="600" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>网关CODE：${paymentGatewayModel.gateway}</td>
			</tr>
			<tr>
				<td>网关名称：<input type="text" name="paymentGatewayModel.gatewayName" value="${paymentGatewayModel.gatewayName}" /></td>
			</tr>
			<tr>
				<td>是否允许退款：
				<select name="paymentGatewayModel.isAllowRefund">
					<s:iterator var="paymentGatewayIsAllowRefund" value="paymentGatewayModel.paymentGatewayIsAllowRefund">
						<option value="${paymentGatewayIsAllowRefund.code}" <s:if test="#paymentGatewayIsAllowRefund.code==paymentGatewayModel.isAllowRefund">selected="selected"</s:if>>${paymentGatewayIsAllowRefund.cnName}</option>
					</s:iterator>
				</select>
				</td>
			</tr>
			
		 	<tr>	
				<td>退款网关CODE：<input type="text" name="paymentGatewayModel.refundGateway" value="${paymentGatewayModel.refundGateway}" readonly="readonly" /></td>
			</tr>
			<tr>
				<td>网关状态：
				<select name="paymentGatewayModel.gatewayStatus">
					<s:iterator var="paymentGatewayStatus" value="paymentGatewayModel.paymentGatewayStatus">
						<option value="${paymentGatewayStatus.code}" <s:if test="#paymentGatewayStatus.code==paymentGatewayModel.gatewayStatus">selected="selected"</s:if>>${paymentGatewayStatus.cnName}</option>
					</s:iterator>
				</select>
				</td>
			</tr>
			<tr>
				<td>网关类型：
				<select name="paymentGatewayModel.gatewayType" id="gatewayType">
					<!-- 
					<s:iterator var="paymentGatewayType" value="paymentGatewayModel.paymentGatewayType">
						<option value="${paymentGatewayType.code}" <s:if test="#paymentGatewayType.code==paymentGatewayModel.gatewayType">selected="selected"</s:if>>${paymentGatewayType.cnName}</option>
					</s:iterator>
					 -->
					<option value="OTHER" <s:if test="'OTHER'==paymentGatewayModel.gatewayType">selected="selected"</s:if>>其它支付</option>
					<option value="DIST" <s:if test="'DIST'==paymentGatewayModel.gatewayType">selected="selected"</s:if>>分销或团购支付</option>
				</select>
				</td>
			</tr>
			<tr>	
				<td>退款顺序：<input type="text" name="paymentGatewayModel.refundOrder" value="${paymentGatewayModel.refundOrder}" /></td>
			</tr>
			<tr>
				<td>
					<input type="hidden" value="${paymentGatewayModel.paymentGatewayId}" name="paymentGatewayModel.paymentGatewayId"/>
				</td>
			</tr>
			
			
			<tr>
				<td>
					<input type="hidden" value="${paymentGatewayModel.payPaymentGatewayElement.paymentGatewayElementId}" name="paymentGatewayModel.payPaymentGatewayElement.paymentGatewayElementId"/>
					<input type="hidden" value="${paymentGatewayModel.gateway}" name="paymentGatewayModel.payPaymentGatewayElement.gateway"/>
				</td>
			</tr>
			<tr id="payPaymentGatewayElementStatus" <s:if test="'OTHER'!=paymentGatewayModel.gatewayType">style="display: none"</s:if>>	
				<td>启用自定义线下支付输入项：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.status" id="isPayPaymentGatewayElement" value="ENABLE" <s:if test="'ENABLE'==paymentGatewayModel.payPaymentGatewayElement.status">checked="checked"</s:if>/></td>
			</tr>
			<table id="payPaymentGatewayElementDiv" class="cg_xx" width="600" border="0" cellspacing="0" cellpadding="0" <s:if test="'OTHER'!=paymentGatewayModel.gatewayType||paymentGatewayModel.payPaymentGatewayElement==null||'FORBIDDEN'==paymentGatewayModel.payPaymentGatewayElement.status">style="display: none"</s:if>>
				<tr>
					<td><hr></td>
				</tr>
				<tr>
					<td>对账流水号：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isPaymentTradeNo" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isPaymentTradeNo">checked="checked"</s:if>/></td>
				</tr>
				<tr>
					<td>网关交易号：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isGatewayTradeNo" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isGatewayTradeNo">checked="checked"</s:if>/></td>
				</tr>
				<tr>
					<td>退款流水号：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isRefundSerial" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isRefundSerial">checked="checked"</s:if>/></td>
				</tr>
				<tr>
					<td>交易时间：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isCallbackTime" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isCallbackTime">checked="checked"</s:if>/></td>
				</tr>
				<tr>
					<td>备注：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isCallbackInfo" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isCallbackInfo">checked="checked"</s:if>/></td>
				</tr>
				<tr>
					<td>收款公司：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isReceivingCompany" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isReceivingCompany">checked="checked"</s:if>/></td>
				</tr>
				<tr>
					<td>收款人：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isReceivingPerson" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isReceivingPerson">checked="checked"</s:if>/></td>
				</tr>
				
				<tr>
					<td>对方银行户名：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isPaymentAccount" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isPaymentAccount">checked="checked"</s:if>/></td>
				</tr>
				<tr>
					<td>对方银行名称：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isPaymentBankName" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isPaymentBankName">checked="checked"</s:if>/></td>
				</tr>
				<tr>
					<td>对方银行账号：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isPaymentBankCardNo" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isPaymentBankCardNo">checked="checked"</s:if>/></td>
				</tr>
				<tr>
					<td>摘要：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isSummary" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isSummary">checked="checked"</s:if>/></td>
				</tr>
				<tr>
					<td>名称：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isDetailName" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isDetailName">checked="checked"</s:if>/></td>
				</tr>
				<tr>
					<td>所属中心：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isBelongCenter" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isBelongCenter">checked="checked"</s:if>/></td>
				</tr>
				<tr>
					<td>所属部门：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isBelongDepartment" value="TRUE" <s:if test="'TRUE'==paymentGatewayModel.payPaymentGatewayElement.isBelongDepartment">checked="checked"</s:if>/></td>
				</tr>
			</table>
			
			<tr>
				<td>
					<input name="right_button08Submit" type="button" value="确定" class="right-button08" id="modifySubmit"/>
					<input name="right_button08Submit" type="button" value="取消" class="right-button08" onclick="$('#gatewayDiv').dialog('close')" />
				</td>
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
	$("#modifySubmit").click(function(){
		$.ajax({
				type:"POST", 
				url:'${basePath}/gateway/payment_gateway!modify.do' + '?random=' + Math.random(), 
				data:$("#modifyForm").serialize(), 
				async: false, 
				success:function (result) {
					alert(eval(result));
					$('#gatewayDiv').dialog('close');
					$('#gatewayForm').submit();
				}
		});
	})
	$("#isPayPaymentGatewayElement").click(function (){
        if ($("#isPayPaymentGatewayElement").attr("checked")) {
        	$("#payPaymentGatewayElementDiv").show();
        }
        else{
        	$("#payPaymentGatewayElementDiv").hide();	
        }
	})
	$("#gatewayType").change(function (){
		if("OTHER"==$("#gatewayType").val()){
			$("#payPaymentGatewayElementStatus").show();
	        if ($("#isPayPaymentGatewayElement").attr("checked")) {
	        	$("#payPaymentGatewayElementDiv").show();
	        }
		}
		else{
			$("#payPaymentGatewayElementStatus").hide();
			$("#payPaymentGatewayElementDiv").hide();
		}
	})
</script>
</html>
