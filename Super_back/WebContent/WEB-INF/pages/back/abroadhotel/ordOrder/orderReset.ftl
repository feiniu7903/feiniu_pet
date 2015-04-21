<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<body>
		<!--=========================我的历史订单弹出层==============================-->
		<div class="orderpoptit">
			<strong>订单状态重置</strong>
			<p class="inputbtn">
				<input type="button" name="btnCloseOrder" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('statusDiv');">
			</p>
		</div>
		<div class="orderpopmain">
			<table style="font-size: 12px" width="100%" border="0"
				id="orderTable" class="contactlist">
				<tr>
					<td align="right">
						订单编号：
					</td>
					<td>
						${(reservationsOrderDetail.orderNo)!}
					</td>
					<td align="right">
						下单时间：
					</td>
					<td>
						${(reservationsOrderDetail.createdTime?string('yyyy-MM-dd HH:mm:ss'))!}
					</td>
					<td align="right" colspan="2">
						&nbsp;
					</td>
					
				</tr>
				<tr>
					<td align="lest" colspan="6">
						<strong>当前状态：</strong>
					</td>
				</tr>
				<tr>
					<td align="right">
						订单状态：
					</td>
					<td>
						${(reservationsOrderDetail.orderStatusZH)!}
					</td>
					<td align="right">
						审核状态：
					</td>
					<td>
						${(reservationsOrderDetail.approveStatusZH)!} 
					</td>
					<td align="right">
						支付状态：
					</td>
					<td>
						${(reservationsOrderDetail.paymentStatusZH)!}
					</td>
				</tr>
				<tr>
					<td align="lest" colspan="6">
						<strong>新状态：</strong>
					</td>
				</tr>
				<tr>
					<td align="right">
						订单状态：
					</td>
					<td>
						<select name="reservationsOrderReq.orderStatus" id="orderStatusId">
							<option value="">--请选择--</option>
							<option value="CANCEL">取消</option>
						</select>
					</td>
					<td align="right">
						审核状态：
					</td>
					<td>
						<select name="reservationsOrderReq.approveStatus" id="approveStatusId">
							<option value="">--请选择--</option>
							<option value="APPROVED" >已审核</option>
						</select>
					</td>
					<td align="right">
						支付状态：
					</td>
					<td>
						<select name="reservationsOrderReq.paymentStatus" id="paymentStatusId">
							<option value="">--请选择--</option>
							<option value="UNPAY">未支付</option>
							<option value="REFUNDED">已支付</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right" colspan="1">
						重置原因：
					</td>
					<td align="left" colspan="5">
						<input type="text" name="resetReason" size="80px">
					</td>
				</tr>
			</table>
		</div>
		<!--popbox end-->
		<p class="submitbtn2">
			<input type="button" name="btnResetOrder"
							onclick="doResetOrder('statusDiv','${(reservationsOrderDetail.orderNo)!}');" value="提交"
							class="right-button08" style="font-weight: bold;">
		</p>
	</body>
</html>
