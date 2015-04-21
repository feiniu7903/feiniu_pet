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
	<script type="text/javascript">
		function btn_updateMemo(ordItemId){
		var faxMemo=document.getElementById("ordItemfaxMemo").value;
		var dataMember = {ordItemId:ordItemId,faxMemo:faxMemo};
		$.ajax({
		
		type : "POST",
		dataType : "html",
		url : "<%=basePath%>ordItemFax/saveOrUpdateMemo.do",
		async : false,
		data :dataMember,
		timeout : 3000,
		error : function(a, b, c) {
			if (b == "timeout") {
				alert("请求超时");
			} else if (b == "error") {
				alert("请求出错");
			}
		},
		success : function(data) {
			if(data=="true"){
				alert("修改成功");
			}else{
				alert("修改失败");
			}
			
		}
		
		});
		}
	</script>
	<body>
		<div class="orderpoptit">
			<strong>我的订单信息：</strong>
			<p class="inputbtn">
				<input type="button" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('historyFaxMemoDiv');">
			</p>
		</div>


		<div class="orderpopmain">
			<table width="100%" border="0" class="contactlist">
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
						支付等待时间：
						<s:if test="orderDetail.hasNeedPrePay()">
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

			<!--=============需审核的产品=============-->
			<div class="popbox">
				<strong>需审核产品</strong>
				<p class="paytime">
					游玩时间：${orderDetail.zhVisitTime }
				</p>
				<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666"
					width="100%" class="newfont05">
					<tbody>
						<tr bgcolor="#eeeeee">
							<td height="35" width="6%">
								子单号
							</td>
							<td width="15%">
								采购产品名称
							</td>
							<td width="5%">
								数量
							</td>
							<td width="8%">
								产品类型
							</td>
							<td width="10%">
								供应商
							</td>
							<td width="10%">
								游玩时间
							</td>
							<td width="20%">
								传真备注
							</td>
							<td width="10%">
								资源状态
							</td>
							<td width="10%">
								操作
							</td>
						</tr>
						<s:iterator value="orderDetail.allOrdOrderItemMetas"
							id="orderItemMeta">
							<tr bgcolor="#ffffff">
								<td height="30">
									${orderItemMeta.orderItemMetaId }
								</td>
								<td>
									<a
										href="javascript:openWin('/super_back/metas/view_index.zul?metaProductId=${orderItemMeta.metaProductId}&productType=${orderItemMeta.productType }',700,700)">${orderItemMeta.productName
										}</a>
								</td>
								<td>
									<s:if test="#orderItemMeta.productType=='HOTEL'">
										${orderItemMeta.hotelQuantity }
									</s:if>
									<s:else>
										${orderItemMeta.productQuantity }
								</s:else>
								</td>
								<td>
									${orderItemMeta.zhProductType }
								</td>
								<td>
									<a
										href="javascript:openWin('/pet_back/sup/detail.do?supplierId=${orderItemMeta.supplier.supplierId}',700,700)">${orderItemMeta.supplier.supplierName
										}</a>
								</td>
								<td>
									${orderItemMeta.strVisitTime }
								</td>
								<td>
									<input name="orderItemMeta.faxMemo" id="ordItemfaxMemo"
										value="${orderItemMeta.faxMemo}"></input>
								</td>
								<td>
									${orderItemMeta.zhResourceStatus }
								</td>
								<td>
									<input type="button" value="修改" class="right-button08"
										onclick="btn_updateMemo('${orderItemMeta.orderItemMetaId }')"
										name="editPassed">
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
			<p class="submitbtn2">
				<input type="button" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('historyFaxMemoDiv');">
			</p>
	</body>
</html>
