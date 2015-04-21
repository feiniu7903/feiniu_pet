<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>网关管理新增</title>
</head>
<body>
	<form method="post" action="" id="addForm">
	<div class="main2"><div class="table_box" id=tags_content_1><div class="mrtit3">
		<table class="cg_xx" width="600" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>网关CODE：<input type="text" name="paymentGatewayModel.gateway" id="gateway"/></td>
			</tr>
			<tr>
				<td>网关名称：<input type="text" name="paymentGatewayModel.gatewayName" id="gatewayName"/></td>
			</tr>
			<tr>
				<td>是否允许退款：
				<select name="paymentGatewayModel.isAllowRefund">
					<s:iterator var="paymentGatewayIsAllowRefund" value="paymentGatewayModel.paymentGatewayIsAllowRefund">
						<option value="${paymentGatewayIsAllowRefund.code}" selected="selected">${paymentGatewayIsAllowRefund.cnName}</option>
					</s:iterator>
				</select>
				</td>
			</tr>
			
		 	<tr>	
				<td>退款网关CODE：<input type="text" name="paymentGatewayModel.refundGateway" readonly="readonly" value="CASH_ACCOUNT"/></td>
			</tr>
			<tr>
				<td>网关状态：
				<select name="paymentGatewayModel.gatewayStatus">
					<s:iterator var="paymentGatewayStatus" value="paymentGatewayModel.paymentGatewayStatus">
						<option value="${paymentGatewayStatus.code}">${paymentGatewayStatus.cnName}</option>
					</s:iterator>
				</select>
				</td>
			</tr>
			<tr>
				<td>网关类型：
				<select name="paymentGatewayModel.gatewayType" id="gatewayType">
					<!--
					<s:iterator var="paymentGatewayType" value="paymentGatewayModel.paymentGatewayType">
						<option value="${paymentGatewayType.code}">${paymentGatewayType.cnName}</option>
					</s:iterator>
					-->
					<option value="OTHER">其它支付</option>
					<option value="DIST">分销或团购支付</option>
				</select>
				</td>
			</tr>
			<tr>	
				<td>退款顺序：<input type="text" name="paymentGatewayModel.refundOrder"/></td>
			</tr>
			
			<tr id="payPaymentGatewayElementStatus">	
				<td>启用自定义线下支付输入项：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.status" id="isPayPaymentGatewayElement" value="ENABLE"/></td>
			</tr>
		
			<table id="payPaymentGatewayElementDiv" style="display: none" class="cg_xx" width="600" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><hr></td>
				</tr>
				<tr>
					<td>对账流水号：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isPaymentTradeNo" value="TRUE"/></td>
				</tr>
				<tr>
					<td>网关交易号：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isGatewayTradeNo" value="TRUE"/></td>
				</tr>
				<tr>
					<td>退款流水号：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isRefundSerial" value="TRUE"/></td>
				</tr>
				<tr>
					<td>交易时间：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isCallbackTime" value="TRUE"/></td>
				</tr>
				<tr>
					<td>备注：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isCallbackInfo" value="TRUE"/></td>
				</tr>
				<tr>
					<td>收款公司：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isReceivingCompany" value="TRUE"/></td>
				</tr>
				<tr>
					<td>收款人：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isReceivingPerson" value="TRUE"/></td>
				</tr>
				
				<tr>
					<td>对方银行户名：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isPaymentAccount" value="TRUE"/></td>
				</tr>
				<tr>
					<td>对方银行名称：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isPaymentBankName" value="TRUE"/></td>
				</tr>
				<tr>
					<td>对方银行账号：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isPaymentBankCardNo" value="TRUE"/></td>
				</tr>
				<tr>
					<td>摘要：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isSummary" value="TRUE"/></td>
				</tr>
				<tr>
					<td>名称：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isDetailName" value="TRUE"/></td>
				</tr>
				<tr>
					<td>所属中心：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isBelongCenter" value="TRUE"/></td>
				</tr>
				<tr>
					<td>所属部门：<input type="checkbox" name="paymentGatewayModel.payPaymentGatewayElement.isBelongDepartment" value="TRUE"/></td>
				</tr>
			</table>
			
			
			<tr>
				<td>
					<input name="right_button08Submit" type="button" value="确定" class="right-button08" id="addSubmit"/>
					<input name="right_button08Submit" type="button" value="取消" class="right-button08" onclick="$('#gatewayDiv').dialog('close')" />
				</td>
			</tr>
		</table>
	</div></div></div>
	</form>
</body>
<script type="text/javascript">
	$("#addSubmit").click(function(){
		if(isNull("gateway")){
			alert('网关CODE不能为空!');
			return ;
		}
		if(isNull("gatewayName")){
			alert('网关名称不能为空!');
			return ;
		}
		$.ajax({
				type:"POST", 
				url:'${basePath}/gateway/payment_gateway!save.do' + '?random=' + Math.random(), 
				data:$("#addForm").serialize(), 
				async: false, 
				success:function (result) {
					var message=eval(result);
					if(message=='true'){
						alert('新增成功!');
						$('#gatewayDiv').dialog('close');
						$('#gatewayForm').submit();	
					}
					else{
						alert(message);
					}
				}
		});
	})
	function isNull(varName){
		if($("#"+varName).val()==null || $("#"+varName).val()==''){
			return true;
		}
		else{
			return false;
		}
	}
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
