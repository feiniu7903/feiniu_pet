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
	<script type="text/javascript" src="<%=basePath%>js/base/lvmama_dialog.js"></script>
	<body>
		<div class="orderpoptit">
			<strong>其它订单合并支付信息</strong>
			<p class="inputbtn">
				<input type="button" class="button" value="关闭"  name="btnOrdHistoryPayDiv"
					onclick="javascript:closeDetailDiv('mergePayDiv');">
			</p>
		</div>

		<div class="orderpopmain">
			<div class="popbox">
				<strong></strong>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tbody>
						<tr bgcolor="#f4f4f4" align="center">
							<td width="8%">
								订单号
							</td>
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
						</tr>
						<s:iterator value="prePaymentList">
							<tr bgcolor="#ffffff">
								<td>
								   ${objectId}
								</td>
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
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
			
			
			
			<!--弹出层灰色背景-->
			<div id="bgPay" class="bg" style="display: none;"></div>
			<p class="submitbtn2">
				<input type="button" name="btnMergePayDiv" class="button" value="关闭" onclick="javascript:closeDetailDiv('mergePayDiv');">
			</p>
		</div>
	</body>
</html>
