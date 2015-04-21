<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>
	<script type="text/javascript"
		src="<%=basePath%>js/base/lvmama_dialog.js"></script>
	<script language="javascript"> 
//function openWindow(){
//window.open ("<%=basePath%>ord/payment/chinapnr.do?orderId=${orderId}", "支付信息","height=480, width=850,top=200, left=200")
//}

function loadOrdPaymentList(){
$("#ordPay").reload({"orderId":${orderId}});
}

function openMoneyAccountPayByPhoneWindow(){
	window.open ("<%=basePath%>ord/payment/orderMoneyAccountPay.do?orderId=${orderId}", "存款账户电话支付","height=480, width=850,top=200, left=200");
}

function openByPayByPhoneWindow(){
	window.open ("<%=basePath%>ord/payment/byPay.do?orderId=${orderId}", "百付电话支付","height=480, width=850,top=200, left=200");
}

function openPosWindow(posType){
	window.open("<%=basePath%>/ord/payment/initPosPaymentRecord.do?orderId=${orderId}&posType="+posType,"POS机支付","height=150,width=400,top=200,left=400");
}

function transferPayment(){
	var a = confirm("订单要将原订单的资金转移到此订单?");
	if (a == true) {
		$.get("<%=basePath%>ord/transfer_payment.do", 
				{orderId:"${orderId}"}, 
				function(data, textStatus){
					var t = eval("(" + data + ")"); 
					if (t.result) {
						alert("资金转移成功!");
						$("#refreshPayInfo").click();
					} else {
						alert("资金转移失败!");
					}
				});		
	}	
}
function reSendPaymentSuccessMessage(paymentId){
	if (confirm("您确定要手工发送支付成功消息?")) {
		$.get("<%=basePath%>ord/reSendPaymentSuccessCallMessage.do", 
				{paymentId:paymentId}, 
				function(data, textStatus){
					var t = eval("(" + data + ")"); 
					if (t.result) {
						alert("发送支付成功消息成功!");
						$("#refreshPayInfo").click();
					} else {
						alert("发送支付成功消息失败!");
					}
				});		
	}	
}

</script>
	<body>
		<div class="orderpoptit">
			<strong>订单支付流水信息</strong>
			<p class="inputbtn">
				<input type="button" class="button" value="关闭"  name="btnOrdHistoryPayDiv"
					onclick="javascript:closeDetailDiv('historyPayDiv');">
			</p>
		</div>

		<div class="orderpop" id="posDiv" style="display: none;"
			href="<%=basePath%>ord/showInvoiceAndAddress.do"></div>
		<div id="bgPos" class="bg" style="display: none;">
			<iframe
				style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity = 0); opacity =0; border-style: none; z-index: -1">
			</iframe>
		</div>

		<div class="orderpopmain">
			<div class="popbox">
				<strong>支付信息</strong>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tbody>
						<tr bgcolor="#f4f4f4" align="center">
							<td height="35" width="10%">
								交易流水号
							</td>
							<td width="10%">
								网关交易号
							</td>
							<td width="8%">
								支付网关
							</td>
							<td width="6%">
								交易金额
							</td>
							<td width="6%">
								支付状态
							</td>
							<td width="17%">
								支付信息
							</td>
							<td width="7%">
								交易时间
							</td>
							<td width="7%">
								创建时间
							</td>
							<td width="10%">
								对帐流水号
							</td>
							<td width="6%">
								原订单号
							</td>
							<td width="7%">
								预授权状态
							</td>
							<td width="8%">
								操作
							</td>
						</tr>
						<s:iterator value="prePaymentList">
							<tr bgcolor="#ffffff">
								<td>
									${serial }
								</td>
								<td>
									${gatewayTradeNo }
								</td>
								<td>
									${payWayZh}
								</td>
								<td>
									${amountYuan}
								</td>
								<td>
									${statusZh }
								</td>
								<td>
									${callbackInfo}
								</td>
								<td>
									<s:date name="callbackTime" format="yyyy-MM-dd HH:mm:ss" />	
								</td>
								<td>
									<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>
									${paymentTradeNo}
								</td>
								<td>
									${oriObjectId}
								</td>
								<td>
								   ${payPreStatusZh}
								</td>
								<td>
									<s:if test="mergePayPaymentList!=null&&mergePayPaymentList.size()>=1">
								   		<a href="javascript:showDetaiMergePaylDiv('mergePayDiv','${orderId}','${paymentTradeNo}','${gatewayTradeNo}','${paymentGateway}');">合并支付</a>
								   	</s:if>
									|
								   	<s:if test="isSuccess() && !isNotified()">
								   		<a href="javascript:reSendPaymentSuccessMessage(${paymentId});">发送支付成功消息</a>
								   	</s:if>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
			
			
			
			<!--弹出层灰色背景-->
			<div id="bgPay" class="bg" style="display: none;">
			</div>
			<input type="button" class="right-button08" name="btnRefreshPayInfo"
					onClick="javascript:showDetaiPaylDiv('historyPayDiv', '${orderId}','${payTotal}','${paymentStatus}','false');"
					id="refreshPayInfo" value="刷新支付信息" />
			<s:if test='canTransfer'>
				<input type="button" class="right-button08" id="BtnTransferPayment"
					onClick="javascript:transferPayment();" value="资金转移" />
			</s:if>

		<s:if test='ordOrder.isNormal()'>
			<s:if test='ordOrder.userId=="FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"'>
				<font color="red" style="font-weight: bold;">当前订单未关联普通用户，请让用户在前台关联，再进行电话支付。</font>
				<br />
			</s:if>
			<s:elseif test="ordOrder.isPaymentSucc()">
				<font color="red" style="font-weight: bold;">当前订单已经支付过了</font>
				<br />
			</s:elseif>
			<s:elseif test="ordOrder.isPrePaymentSucc()">
				<font color="red" style="font-weight: bold;">当前订单已经预授权完成</font>
				<br />
			</s:elseif>
			<s:elseif test="ordOrder.isPaymentChannelLimit()">
				<font color="red" style="font-weight: bold;">当前订单有支付渠道限制(<s:property value="ordOrder.zhPaymentChannel" />)
				</font>
				<br />
			</s:elseif>
			<s:else>
                <s:if test="ordOrder.isApprovePass()">
				<input type="button" name="btnByPayByPhone" class="right-button08" onClick="openByPayByPhoneWindow()" value="电话支付" />
                </s:if>
				<s:if test="ordOrder.isCanToPay()">
					<!-- <input type="button" name="btnPhonePay" class="right-button08" onClick="openWindow()" value="信用卡电话支付" /> --> 
					<input type="button" name="btnLakala" class="right-button08"
						onClick="window.open('${lakalaURL}');" value="线下拉卡拉支付" />
						
					<input type="button" name="btnPayPos" class="right-button08"
						onClick="openPosWindow('comm_pos');" value="Pos机支付" />
					<input type="button" name="btnSandPosURL" class="right-button08"
						onClick="openPosWindow('sand_pos');" value="杉德Pos机支付" />
					存款账户余额为<s:if test='moneyAccount!=null'>${moneyAccount.maxPayMoneyYuan }</s:if> <s:else>0</s:else>元 
					<s:if test="!haveMoblie">
						<font color="red" style="font-weight: bold;">该账户未设置存款账户提醒手机号，请先设置号码后再进行存款账户电话支付</font>
					</s:if>
					<s:elseif test="!havePaymentPassword">
						<font color="red" style="font-weight: bold;">该账户未设置存款账户支付密码，请先设置支付密码再进行存款账户电话支付</font>
					</s:elseif>
					<s:elseif test="tempCloseCashAccountPay">
						<font color="red" style="font-weight: bold;">对于广州长隆供应商的产品暂时关闭存款账户电话支付功能</font>
					</s:elseif>
					<s:elseif test="canAccountPay">
						<input type="button" name="btnMoneyAccountPayByPhone" class="right-button08" onClick="openMoneyAccountPayByPhoneWindow()" value="存款账户电话支付" />
					</s:elseif>
				</s:if>
			</s:else>
		</s:if>
		<!--popbox end-->
			<p class="submitbtn2">
				<input type="button" name="btnHistoryPayDiv" class="button" value="关闭"
				onclick="javascript:closeDetailDiv('historyPayDiv');">
			</p>
		</div>
		<div class="orderpop" id="mergePayDiv" style="display: none;width: 90% !important;margin-left: -540px ! important;margin-top: 50px ! important;"
			href="<%=basePath%>ord/showOtherMergePay.do">
		</div>
	</body>
</html>
