<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script language="javascript" src="<%=basePath%>js/ord/ord.js" type="text/javascript"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.jsonSuggest.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.jsonSuggest-2.min.js"></script>
<link href="<%=request.getContextPath()%>/themes/base/jquery.jsonSuggest.css" rel="stylesheet" type="text/css" />

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
	$("#otherPayConditionDIV").load($('#otherPayConditionDIV').attr('href'), {
		paymentGateway : $('#paymentGateway').val()
	});
	
	var notCustomAuth=$("#notCustomPayPaymentGatewayList").val();
	var customAuth=$("#customPayPaymentGatewayList").val();
	if(notCustomAuth!=undefined && customAuth!=undefined){
		$("#customPayPaymentGatewayList").remove();
	}
	if(notCustomAuth==undefined && customAuth==undefined){
		$("#payPaymentNoAuthMemo").show();
	}

})
$("#paymentGateway").change(function (){
	$("#otherPayConditionDIV").load($('#otherPayConditionDIV').attr('href'), {
		paymentGateway : $('#paymentGateway').val()
	});
})
function commercialAdd() {
	var gate = $('#paymentGateway').val();
	if (gate==null || gate=='undefined') {
		alert('支付网关不能为空，请添加权限！');
		return;
	}
	
	$('#AddBatch').attr("disabled",true);
	var dataPost = {
		orderId : $('#orderId').val(),
		paymentGateway : $('#paymentGateway').val(),
		paymentTradeNo : $('#paymentTradeNo').val(),
		actualPayFloat : $('#actualPayFloat').val(),
		gatewayTradeNo : $('#gatewayTradeNo').val(),
		refundSerial : $('#refundSerial').val(),
		callbackTime : $('#callbackTime').val(),
		memo : $('#memo').val(),
		paymentGatewayElementId:$('#paymentGatewayElementId').val(),
		receivingCompanyId:$('#receivingCompanyId').val(),
		receivingPerson:$('#receivingPerson').val(),
		receivingPersonUserName:$('#receivingPersonUserName').val(),
		receivingBankCardNo:$('#receivingBankCardNo').val(),
		paymentAccount:$('#paymentAccount').val(),
		paymentBankName:$('#paymentBankName').val(),
		paymentBankCardNo:$('#paymentBankCardNo').val(),
		belongCenter:$('#belongCenter').val(),
		belongDepartment:$('#belongDepartment').val(),
		detailName:$('#detailName').val(),
		summary:$('#summary').val()
	};
	$.ajax( {
		type : "POST",
		url : "<%=basePath%>offlinePay/otherPaySave.do",
		data : dataPost,
		success : function(dt) {
			data = eval("(" + dt + ")");
			if (data.success) {
				alert("保存成功!");
				window.location.reload();
			} else {
				$('#AddBatch').attr("disabled",false);
				alert(data.msg);
			}
		}
	});
}
</script>
</head>

<body>
	<div class="orderpoptit">
		<strong>线下支付管理-其他支付</strong>
		<p class="inputbtn">
			<input type="button" class="button" value="关闭"
				name="btnOrdHistoryPayDiv"
				onclick="javascript:closeDetailDiv('otherPayDiv');">
		</p>
	</div>


	<div id="bgPos" class="bg" style="display: none;">
		<iframe
			style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity =     0); opacity =0; border-style: none; z-index: -1">
		</iframe>
	</div>

	<div class="orderpopmain">
		<div class="popbox">
			<strong>填写信息</strong>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tbody>
						<tr bgcolor="#ffffff" style="height:30px">
							<td style="width: 100px">支付网关: <b style="color:red"> *</b></td>
							<td>
							<mis:checkPerm permCode="3364">
								<span id="notCustomPayPaymentGatewayList" value="notCustomPayPaymentGatewayList">
									<s:select list="payPaymentGatewayList"
											id="paymentGateway" name="paymentGateway" listKey="gateway"
											listValue="gatewayName" label="CLASS"></s:select>
								</span>
							</mis:checkPerm>
							<s:if test="isAdmin!=0">
							<mis:checkPerm permCode="3365">
								<span id="customPayPaymentGatewayList" value="customPayPaymentGatewayList">
									<select id="paymentGateway" name="paymentGateway">
										<option value="EXCHANGE">银行转账</option>
									</select>
								</span>
							</mis:checkPerm>
							</s:if>
							
							<div id="payPaymentNoAuthMemo" style="color: red;display: none;">没有支付网关权限,请联系相关人员添加权限!</div>
							</td>
						</tr>
						<tr bgcolor="#ffffff" style="height:30px">
							<td style="width: 100px">支付金额: <b style="color:red"> *</b></td>
							<td><input type="text" name="actualPayFloat" id="actualPayFloat"
								value="${actualPayFloat}" readonly="readonly" />
							</td>
						</tr>
					</tbody>
					<tbody id="otherPayConditionDIV" href="<%=basePath%>offlinePay/gatewayChange.do" style="width: 100px"></tbody>
					<tbody>
                        <tr bgcolor="#ffffff" style="height:30px">
							<td colspan="4" align="center">
								<input type="hidden" name="orderId" id="orderId" value="${orderId}" />
								<input type='button' name="AddBatch" id="AddBatch" value="保存" class="right-button08" onclick="commercialAdd()" />	
							</td>
						</tr>
					</tbody>
				</table>
		</div>

		<!--弹出层灰色背景-->
		<div id="bgPay" class="bg" style="display: none;"></div>
		<!--popbox end-->
		<p class="submitbtn2">
			<input type="button" name="btnHistoryPayDiv" class="button"
				value="关闭" onclick="javascript:closeDetailDiv('otherPayDiv');">
		</p>
		
	</div>
</body>

</html>


