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

$(function(){
	$("#paymentGatewaySelect").change(function(){
		var val=$(this).val();
		var $payment=$("#paymentTradeNoSelect");
		$payment.empty();
		if(val==''){
			var $opt=$("<option/>");
			$opt.val("");
			$opt.text("请选择");
			$city.append($opt);
		}else{
			$.post("/super_back/offlinePay/loadPayPayments.do",{"paymentGateway":val,"orderId":'${orderId}'},function(data){
				var len=data.payPaymentList.length;
				for(var i=0;i<len;i++){
					var payment=data.payPaymentList[i];
					var $opt=$("<option/>");
					$opt.val(payment.paymentTradeNo);
					$opt.text(payment.paymentTradeNo);
					$payment.append($opt);
					$("#paymentTradeNoSelect").trigger("change");
				}
			},"json");
			
		}
	});
	
	$("#paymentTradeNoSelect").change(function(){
		$.post("/super_back/offlinePay/loadPaymentAmount.do",{"paymentTradeNo":$(this).val(),"orderId":$('#orderId').val()},function(data1){
			$("#actualPayAmount").val(data1.actualPayFloat);
		},"json");
	});
});


})
</script>

<script>
function commercialAdd() {
	var dataPost = {
			paymentTradeNo : $('#paymentTradeNoSelect').val(),
			orderId : $('#orderId').val(),
			actualPayFloat : $('#actualPayFloat').val(),
			gatewayTradeNo : $('#gatewayTradeNo').val(),
			refundSerial : $('#refundSerial').val(),
			callbackTime : $('#callbackTime').val(),
			memo : $('#memo').val()
			
		};
		$.ajax( {
			type : "POST",
			url : "<%=basePath%>offlinePay/onlinePaySave.do",
			data : dataPost,
			success : function(dt) {
				data = eval("(" + dt + ")");
				if (data.success) {
					alert("保存成功!");
					window.location.reload();
				} else {
					alert(data.msg);
				}
			}
		});
}
</script>
</head>

<body>
	<div class="orderpoptit">
		<strong>线下支付管理-手工支付</strong>
		<p class="inputbtn">
			<input type="button" class="button" value="关闭"
				name="btnOrdHistoryPayDiv"
				onclick="javascript:closeDetailDiv('onlinePayDiv');">
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
							<td style="width:70px">支付网关: </td>
							<td>
							<s:select list="payPaymentGatewayList"
									id="paymentGatewaySelect" name="paymentGateway" listKey="gateway"
									listValue="gatewayName" label="CLASS"></s:select>
							</td>
							<td style="width:90px">对账流水号:</td>
							<td> 
							 <select  id="paymentTradeNoSelect"    name="paymentTradeNo"> </select>
						</tr>
						<tr bgcolor="#ffffff" style="height:30px">
							<td>支付金额:</td>
							<td><input type="text" name="actualPayFloat" id="actualPayAmount"
								value="${actualPayFloat}" readonly="readonly" />
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
							 <input type='button' name="AddBatch" value="保存"
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
				value="关闭" onclick="javascript:closeDetailDiv('onlinePayDiv');">
		</p>
		
	</div>
</body>

</html>