<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<body>
		<div class="orderpoptit">
			<strong>订单支付流水信息</strong>
			<p class="inputbtn">
				<input type="button" class="button" value="关闭"  name="btnOrdHistoryPayDiv"
					onclick="javascript:closeDetailDiv('paymentInfoDiv');">
			</p>
		</div>

		<div class="orderpopmain">
			<div class="popbox">
				<strong>支付信息</strong>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tbody>
						<tr bgcolor="#f4f4f4" align="center">
							<td height="35" width="6%">
								订单号
							</td>
							<td width="10%">
								交易流水号
							</td>
							<td width="8%">
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
							<td>
								错误信息
							</td>
							<td width="18%">
								交易时间
							</td>
							<td width="18%">
								创建时间
							</td>
						</tr>
						<#if ahotelOrdPaymentList??>
							<#list ahotelOrdPaymentList as ahotelOrdPayment>
							<tr bgcolor="#ffffff">
								<td>
									${ahotelOrdPayment.orderId?c}
								</td>
								<td>
									${ahotelOrdPayment.serial! }
								</td>
								<td>
									${ahotelOrdPayment.gatewayTradeNo! }
								</td>
								<td>
									${ahotelOrdPayment.payWayZh!}
								</td>
								<td>
									${ahotelOrdPayment.amountYuan!}
								</td>
								<td>
									${ahotelOrdPayment.statusZh! }
								</td>
								<td>
									${ahotelOrdPayment.callbackInfo!}
								</td>
								<td>
									${(ahotelOrdPayment.callbackTime?string("yyyy-MM-dd HH:mm:ss"))!}
								</td>
								<td>
									${(ahotelOrdPayment.createTime?string("yyyy-MM-dd HH:mm:ss"))!}
								</td>
							</tr>
							</#list>
						</#if>
					</tbody>
				</table>
			</div>
			
			<!--弹出层灰色背景-->
			<div id="bgPay" class="bg" style="display: none;">
			</div>
			<div>
				<input type="button" class="right-button08" name="btnRefreshPayInfo"
					onClick="javascript:showPaymentInfoDiv('paymentInfoDiv', '${(reservationsOrderDetail.orderNo)!}');"
					id="refreshPayInfo" value="刷新订单支付信息" />
				<#if reservationsOrderDetail?exists && reservationsOrderDetail.paymentStatus?exists>
					<#if reservationsOrderDetail.paymentStatus=='PAYED'>
						<font color="red" style="font-weight: bold;">当前订单已经支付过了</font><br/>
					</#if>
				</#if>
			</div>
			
			<!--popbox end-->
			<p style="float:left;margin-left: 350px;">
				<input type="button" name="btnHistoryPayDiv" class="button" value="关闭"
				onclick="javascript:closeDetailDiv('paymentInfoDiv');">
			</p>
		</div>
	</body>
</html>
