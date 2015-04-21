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
		src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/base/lvmama_dialog.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/base/jquery-from.js"></script>
	<body>
		<div class="orderpopmain">
			<form name='saleDealFrm' id="saleDealFrm" method='post'
				action='/ordRefund/addRefundMent.do'>
				<s:hidden name="orderId" vlaue="${orderId}"></s:hidden>
				<strong>申请售后服务</strong>
				<table style="font-size: 12px" width="100%" border="0"
					class="contactlist">
					<tr>
						<td width="25%">
							订单号：${historyOrderDetail.orderId }
						</td>
						<td width="25%">
							下单时间：${historyOrderDetail.zhCreateTime }
						</td>
						<td width="25%">
							下单人：${historyOrderDetail.userName }
						</td>
						<td width="25%">
							支付等待时间： 
							<s:if test="historyOrderDetail.hasNeedPrePay()">
						<s:date name="historyOrderDetail.aheadTime" format="yyyy-MM-dd HH:mm"/>
						</s:if><s:else>${historyOrderDetail.zhWaitPayment}</s:else>
						</td>
					</tr>
					<tr>
						<td>
							应付金额：${historyOrderDetail.oughtPayYuan }
						</td>
						<td>
							实付金额：${historyOrderDetail.actualPayYuan }
						</td>
						<td>
							支付状态：${historyOrderDetail.zhPaymentStatus }
						</td>
						<td>
							订单状态：${historyOrderDetail.zhOrderStatus }
						</td>
					</tr>
					<tr>
						<td colspan="4">
							订单来源渠道：${historyOrderDetail.zhProductChannel }
						</td>
					</tr>
					<tr>
						<td colspan="3">
							用户备注：${historyOrderDetail.userMemo }
						</td>
					</tr>
				</table>

				<!--=============商品清单=============-->
				<div class="popbox">
					<strong>商品清单</strong>
					<p class="paytime">
						游玩时间：${historyOrderDetail.zhVisitTime }
					</p>
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
						border="0" bgcolor="#666666" width="100%" class="newfont05">
						<tbody>
							<tr class="CTitle">
								<td height="22" align="center" style="font-size: 16px;"
									colspan="13">
									商品清单
								</td>
							</tr>
							<tr bgcolor="#eeeeee">
								<td height="35" width="4%">
									序号
								</td>
								<td width="18%">
									产品名称
								</td>
								<td width="8%">
									会员价
								</td>
								<td width="5%">
									数量
								</td>
								<td width="8%">
									总金额
								</td>
								<td width="10%">
									游玩时间
								</td>
							</tr>
							<s:iterator id="prod"
								value="historyOrderDetail.ordOrderItemProds">
								<s:if test="#prod.productType!='OTHER'">
									<s:iterator id="prod" value="ordOrderItemMetas">
										<tr bgcolor="#ffffff">
											<td height="30">
												<input type="checkbox" name="checkItemId"
													value="${orderItemId}"></input>
												${orderItemId}
											</td>
											<td>
												${productName}
											</td>
											<td>
												${sellPrice}
											</td>
											<td>
												<s:if test="productType=='HOTEL'">
										${hotelQuantity }
									</s:if>
									<s:else>
										${quantity }
									</s:else>
											</td>
											<td>
												${sellPrice*quantity }
											</td>
											<td>
												${zhVisitTime }
											</td>
										</tr>
									</s:iterator>
								</s:if>
							</s:iterator>
						</tbody>
					</table>
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
						border="0" bgcolor="#666666" width="100%" class="newfont06">
						<tbody>
							<tr class="CTitle">
								<td height="20" align="center" style="font-size: 14px;"
									colspan="13">
									附加产品
								</td>
							</tr>
							<tr bgcolor="#eeeeee">
								<td height="35" width="5%">
									序号
								</td>
								<td width="20%">
									产品名称
								</td>
								<td width="8%">
									会员价
								</td>
								<td width="8%">
									总金额
								</td>
								<td width="8%">
									产品类型
								</td>
								<td width="7%">
									数量
								</td>
								<td width="12%">
									游玩时间
								</td>
							</tr>
							<s:iterator id="prod"
								value="historyOrderDetail.ordOrderItemProds">
								<s:if test="#prod.productType=='OTHER'">
									<s:iterator id="prod" value="ordOrderItemMetas">
										<tr bgcolor="#ffffff">
											<td height="30">
												<input type="checkbox" name="checkItemId"
													value="${orderItemId}"></input>
												${orderItemId}
											</td>
											<td>
												${productName}
											</td>
											<td>
												${sellPrice}
											</td>
											<td>
												${quantity }
											</td>
											<td>
												${sellPrice*quantity }
											</td>
											<td>
												${zhVisitTime }
											</td>
										</tr>
									</s:iterator>
								</s:if>
							</s:iterator>
						</tbody>
					</table>
				</div>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#666666" width="100%" class="newfont05">
					<tbody>
						<tr class="CTitle">
							<td height="22" align="left" style="font-size: 16px;"
								colspan="13">
								添加退款售后内容
							</td>
						</tr>
						<tr bgcolor="#ffffff">
							<td width="18%">
								退款类型
							</td>
							<td width="8%">
								<s:select name="ordRefundment.refundType" id="refundType"
									list="#{'订单退款':'REFUND_ORDER','补偿':'REFUND_REPAY'}" value="''"
									listKey="value" listValue="key"></s:select>
							</td>
							<td width="18%">
								退款金额
							</td>
							<td width="8%">
								<input type="text" name="ordRefundment.amount" id="amount"
									value="" />
							</td>
							<td width="18%">
								退款渠道
							</td>
							<td width="8%">
								<s:select name="ordRefundment.refundChannel" id="refundChannel"
									list="#{'现金账户':'CHANNEL_NOWMONEY','原路退回':'CHANNEL_WAYBACK'}"
									value="''" listKey="value" listValue="key"></s:select>
							</td>
						</tr>
						<tr bgcolor="#ffffff">
							<td width="18%">
								帐号类型
							</td>
							<td width="8%">
								<s:select list="channelList" id="channelId" listKey="code"
									listValue="name" name="ordRefundment.accountType" headerKey=""></s:select>
							</td>
							<td width="18%">
								退款帐号
							</td>
							<td width="8%" colspan="3">
								<input type="text" name="ordRefundment.account" id="account"
									value="" />
							</td>
						</tr>
						<tr bgcolor="#ffffff">
							<td width="18%">
								退款要求
							</td>
							<td width="8%" colspan="5">
								<textarea id="memo" name="ordRefundment.memo" rows="5" cols="80"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
				<br />
				<input type="button" name="btnSaleAddRefund" class="right-button08" onClick="btn_sale_add()"
					value="申请退款" />
				<input type="button" name="btnCloseHisRefment" class="right-button08" value="关闭"
					onclick="javascript:closeDetailDiv('historyRefundMentDiv');">
			</form>
		</div>
	</body>
	<script type="text/javascript"> 
function btn_sale_add(){
	var amount=document.getElementById("amount").value;
	if(amount==""){
		alert("请输入用户的退款金额!");
		return false;
	}
	if(isNaN(amount)){
		alert("请输入正确的退款金额!");
		return false;
	}
	var uform = $("#saleDealFrm");
	var dataMember = uform.getForm({   
            prefix:''
        }); 
	$.ajax({type:"POST", async:false,url:"<%=basePath%>ordRefund/addRefundMent.do", data:dataMember, dataType:"json", success:function (json) {
		alert("添加成功!");
	}});
}
</script>
</html>
