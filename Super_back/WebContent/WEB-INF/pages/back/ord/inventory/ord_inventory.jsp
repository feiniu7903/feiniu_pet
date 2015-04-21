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

	<body>
		<!--=========================我的历史订单弹出层==============================-->
		<div class="orderpoptit">
			<strong>我的订单查看：</strong>
			<p class="inputbtn">
				<input type="button" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('historyInventoryDiv');">
			</p>
		</div>
		<div class="orderpopmain">
			<table style="font-size: 12px" width="100%" border="0"
				class="contactlist">
				<tr>
					<td width="25%">
						订单号：${orderDetail.orderId }
					</td>
					<td width="25%">
						下单时间：${orderDetail.zhCreateTime }
					</td>
					<td width="25%">
						下单人：${orderDetail.userName }
					</td>
					<td width="25%">
						支付等待时间： <s:if test="orderDetail.hasNeedPrePay()">
						<s:date name="orderDetail.aheadTime" format="yyyy-MM-dd HH:mm"/>
						</s:if><s:else>${orderDetail.zhWaitPayment}</s:else>
					</td>
				</tr>
				<tr>
					<td>
						应付金额：${orderDetail.oughtPayYuan }
					</td>
					<td>
						实付金额：${orderDetail.actualPayYuan }
					</td>
					<td>
						支付状态：${orderDetail.zhPaymentStatus }
					</td>
					<td>
						订单状态：${orderDetail.zhOrderStatus }
					</td>
				</tr>
				<tr>
					<td colspan="4">
						订单来源渠道：${orderDetail.zhProductChannel }
					</td>
				</tr>
				<tr>
					<td colspan="3">
						用户备注：${orderDetail.userMemo }
					</td>
				</tr>
			</table>
				<p class="submitbtn2">
					<input type="button" onclick="doRestoreOrder('<%=basePath%>ordInventory/restoreOrder.do','${orderDetail.orderId}')"
						value="恢复正常" class="right-button08">
					<input type="button" class="button" value="关闭"
						onclick="javascript:closeDetailDiv('historyInventoryDiv');">
				</p>
			</div>
		<!--=========================我的历史审核弹出层 end==============================-->
	</body>
</html>
