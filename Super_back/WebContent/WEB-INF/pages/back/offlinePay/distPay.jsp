<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script language="javascript" src="<%=basePath%>js/ord/ord.js" type="text/javascript"> </script>

<script type="text/javascript">
$(document).ready(function(){
	
$("input.dateYMD").datetimepicker({
	showSecond: true,
	timeFormat: 'hh:mm:ss',
	stepHour: 1,
	stepMinute: 1,
	stepSecond: 1,
	showButtonPanel:false
});

})
</script>

<script>
function commercialAdd() {
	
	var r = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	var paytotal=document.getElementById("actualPayFloat").value;
	if(!r.test(paytotal)){
		alert("支付金额格式不正确!");
        return false;
    }
	if(paytotal<=0){
		alert("支付金额必须大于零!");
		return false;
	}
	if(paytotal>${actualPayFloat}){
		alert("支付金额不能超过${actualPayFloat}元");
		return false;
	}
	
	var channel = "${channel}";
	var zhChannel = "${zhChannel}";
	var way = $('#paymentGateway').val();
	var paymentGatewayName=$("#paymentGateway").find("option:selected").text();
	if(!confirm("你所选择的支付渠道是:"+paymentGatewayName+",订单的下单渠道是:"+zhChannel+",确定要继续支付吗?")){
		return false;
	}
	$('#AddBatch').attr("disabled",true);
	var dataPost = {
		orderId : $('#orderId').val(),
		paymentGateway : way,
		paymentTradeNo : $('#paymentTradeNo').val(),
		actualPayFloat : $('#actualPayFloat').val(),
		gatewayTradeNo : $('#gatewayTradeNo').val(),
		refundSerial : $('#refundSerial').val(),
		callbackTime : $('#callbackTime').val(),
		memo : $('#memo').val()
	};
	
	$.ajax( {
		type : "POST",
		url : "<%=basePath%>offlinePay/distPaySave.do",
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
		<strong>线下支付管理-分销支付</strong>
		<p class="inputbtn">
			<input type="button" class="button" value="关闭"
				name="btnOrdHistoryPayDiv"
				onclick="javascript:closeDetailDiv('distPayDiv');">
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
							<td style="width:70px">下单渠道: </td>
							<td colspan="3" >
							<input type="hidden" name="channel" id="channel" value="${channel}"/>
							<input type="text" name="zhChannel"   id="zhChannel" value="${zhChannel}" readonly="readonly" />
							</td>
						</tr>
					
						<tr bgcolor="#ffffff" style="height:30px">
							<td style="width:70px">支付网关: </td>
							<td>
							<s:select list="payPaymentGatewayList"
									id="paymentGateway" name="paymentGateway" listKey="gateway"
									listValue="gatewayName" label="CLASS"></s:select>
							</td>
							<td style="width:90px">对账流水号:</td>
							<td><input type="text" name="paymentTradeNo"
								id="paymentTradeNo" />
							</td>
						</tr>
						<tr bgcolor="#ffffff" style="height:30px">
							<td>支付金额:</td>
							<td><input type="text" name="actualPayFloat" id="actualPayFloat"
								value="${actualPayFloat}"/>
							</td>
							<td>网关交易号:</td>
							<td><input type="text" name="gatewayTradeNo"
								id="gatewayTradeNo" />
							</td>
						</tr>
						<tr bgcolor="#ffffff" style="height:30px">
							<td>退款流水号:</td>
							<td><input type="text" name="refundSerial"
								id="refundSerial" />
							</td>
							<td>交易时间:</td>
							<td> 
							<input type="text" name="callbackTime" id="callbackTime" class="dateYMD"/>
							</td>
						</tr>
						
						<tr bgcolor="#ffffff" style="height:30px">
						<input type="hidden" name="orderId" id="orderId" value="${orderId}"/>
							<td>备注:</td>
							<td colspan="3"> <input type="text" name="memo"  id="memo" width="100" /> </td>
						</tr>
                        <tr bgcolor="#ffffff" style="height:30px">
							<td colspan="4" align="center">
							 <input type='button' name="AddBatch" value="保存" id="AddBatch"
								class="right-button08" onclick="commercialAdd()" />	
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
				value="关闭" onclick="javascript:closeDetailDiv('distPayDiv');">
		</p>
		
	</div>
</body>

</html>




