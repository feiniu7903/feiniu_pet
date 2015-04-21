<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title>发班取消管理</title>
		<script type="text/javascript">
	   		var path='<%=basePath%>';
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>js/ord/ord.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>js/ord/in_add.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>js/ord/ord_div.js"
			type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/cc.css" />
		<link rel="stylesheet"
			href="<%=basePath%>themes/base/jquery.ui.all.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/lvmama_common.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/lvmama_dialog.js"></script>
			<script src="${basePath}js/phoneorder/important_tips.js"
			type="text/javascript">
</script>
		<script type="text/javascript">
		$(document).ready(function(){
			$("#checkall").click( 
				function(){ 
					if(this.checked){ 
						$("input[name='checkBoxName']").each(function(){this.checked=true;}); 
					}else{ 
						$("input[name='checkBoxName']").each(function(){this.checked=false;}); 
					} 
				} 
				);
		});
		$(function() {
			$("input[name='createTimeStart']" ).datepicker({dateFormat:'yy-mm-dd'});
			$("input[name='createTimeEnd']").datepicker({dateFormat:'yy-mm-dd'});
			$("input[name='visitTimeStart']").datepicker({dateFormat:'yy-mm-dd'});
			$("input[name='visitTimeEnd']").datepicker({dateFormat:'yy-mm-dd'});
			$("input[name='paymentTimeStart']").datepicker({dateFormat:'yy-mm-dd'});
			$("input[name='paymentTimeEnd']").datepicker({dateFormat:'yy-mm-dd'});
		
		});
		$(document).ready(function () {
			var paymentStatus = '${paymentStatus}';
			var approveStatus = '${approveStatus}';
			var orderStatus = '${orderStatus}';
			var productType0 = '${productTypeList[0]}';
			var productType1 = '${productTypeList[1]}';
			var productType2 = '${productTypeList[2]}';
			var productType3 = '${productTypeList[3]}';
			
			var paymentStatusObj = document.getElementById('paymentStatus');
			if(paymentStatusObj!=null){
				for(var i = 0; i < paymentStatusObj.options.length; i++)
				{
					if(paymentStatusObj.options[i].value == paymentStatus)
					{
						paymentStatusObj.options[i].selected = true;
						break;
					}
				}
			}
			
			var approveStatusObj = document.getElementById('approveStatus');
			if(approveStatusObj!=null){
				for(var i = 0; i < approveStatusObj.options.length; i++)
				{
					if(approveStatusObj.options[i].value == approveStatus)
					{
						approveStatusObj.options[i].selected = true;
						break;
					}
				}
			}
			var orderStatusObj = document.getElementById('orderStatus');
			if(orderStatusObj!=null){
				for(var i = 0; i < orderStatusObj.options.length; i++)
				{
					if(orderStatusObj.options[i].value == orderStatus)
					{
						orderStatusObj.options[i].selected = true;
						break;
					}
				}
			}
			if(productType0 == 'TICKET')
			{
				document.getElementById('productTypeList[0]').checked = true;
			}
			if(productType1 == 'HOTEL')
			{
				document.getElementById('productTypeList[1]').checked = true;
			}
			if(productType2 == 'ROUTE')
			{
				document.getElementById('productTypeList[2]').checked = true;
			}
			if(productType3 == 'OTHER')
			{
				document.getElementById('productTypeList[3]').checked = true;
			}
		});
		
		function beforeSubmit() {
			if($("#productName").val()!="") {
				if($("#createTimeStart").val() == "" && $("#paymentTimeStart").val() == "" && $("#visitTimeStart").val() == "")	{
					alert("要用产品名查询，必须选择'下单时间'、'游玩时间'、'支付时间'之一");
					return false;
				}		
			}			
			return true;
		}
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>

	</head>
	<body>

			<div>
				<div class="main2">
					<!--=========================主体内容==============================-->
					<div class="table_box" id=tags_content_1>
						<table cellspacing="1" cellpadding="4" border="0"
							bgcolor="#666666" width="100%" class="newfont06"
							style="font-size: 12; text-align: center;">
							<tbody>
								<tr bgcolor="#eeeeee">
									<td width="4%">
										<input type="checkbox" name="checkall" value="1"
											id="checkall" />
									</td>
									<td height="35" width="6%">
										订单号
									</td>
									<td height="35" width="6%">
										处理人
									</td>
									<td width="10%">
										联系人姓名
									</td>
									<td width="6%">
										联系人电话
									</td>
									<td width="14%">
										产品名
									</td>
									<td width="6%">
										订购数量
									</td>
									<td width="8%">
										订单状态
									</td>
									<td width="8%">
										审核状态
									</td>
									<td width="8%">
										支付状态
									</td>
									<td width="9%">
										履行状态
									</td>
									<td width="6%">
										售后服务
									</td>
									<td>
										操作
									</td>
								</tr>
								<s:iterator value="ordersList">
									<tr bgcolor="#ffffff">
										<td>
											<input type="checkbox" name="checkBoxName"
												value="${orderId}"></input>
										</td>
										<td height="30">
											${orderId }
										</td>
										<td height="30">
											${takenOperator }
										</td>
										<td>
											${contact.name }
										</td>
										<td>
											${contact.mobile }
										</td>
										<td>
											<s:iterator id="orderItem" value="ordOrderItemProds">
											<a class="showImportantTips" href="javascript:void(0)"
						productId="${orderItem.productId}" prodBranchId="${orderItem.prodBranchId}">${orderItem.productName}</a>
												<br />
											</s:iterator>
										</td>
										<td>
											<s:iterator id="orderItem" value="ordOrderItemProds">
											<s:if test="#orderItem.productType=='HOTEL'">
										${orderItem.hotelQuantity }<br />
									</s:if>
									<s:else>
										${orderItem.quantity }<br />
									</s:else>
											</s:iterator>
										</td>
										<td>
											${zhOrderStatus }
										</td>
										<td>
											${zhApproveStatus }
										</td>
										<td>
											${zhPaymentStatus }
										</td>
										<td>
											${zhPerformStatus }
										</td>
										<td>
											${zhNeedSaleService }
										</td>
										<td>
										<mis:checkPerm permCode="1746">
											<a href="javascript:showDetailDiv('historyDiv', '${orderId}');">查看</a>
											</mis:checkPerm>
											<mis:checkPerm permCode="1747">	
											<s:if test="payToLvmama && approvePass">|<a href="javascript:showDetaiPaylDiv('historyPayDiv','${orderId}','<s:property value="@com.lvmama.common.utils.PriceUtil@convertToYuan(oughtPay)"/>','${paymentStatus}');">支付信息</a></s:if>
											</mis:checkPerm>
											<mis:checkPerm permCode="1748">
											|<a href="javascript:showDetailDiv('historyFaxMemoDiv', '${orderId}');">修改传真备注</a>
											</mis:checkPerm>
											<mis:checkPerm permCode="1749">
											|<a href="#" onClick="javascript:window.open('<%=basePath%>/ord/sale/OrdSaleAddJump.zul?orderId=${orderId}','','height=700,width=1000,top=200, left=200,scrollbars=yes')">售后服务</a>
											</mis:checkPerm>
											<mis:checkPerm permCode="1750">
											|<a href="javascript:showDetailDiv('in_addDiv', '${orderId }');">发票与物流</a>
											</mis:checkPerm>
											<s:if test="isShouldSendCert()" >
											<mis:checkPerm permCode="1752">
								    		 	|<a href="javascript:showDetaiSmsDiv('order_sms', '${orderId}','${contact.mobile }','OneSms','ord_list');">发送短信凭证</a>
								    		 	</mis:checkPerm>
											</s:if>
											
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>

					</div>
					<!--=========================主体内容 end==============================-->
				</div>
				<!--main2 end-->
			</div>

		<table width="90%" border="0" align="center">
			<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
		</table>
		<div class="orderpop" id="historyDiv" style="display: none;"
			href="<%=basePath%>ord/showHistoryOrderDetail.do">
		</div>
		<div class="orderpop" id="historyPayDiv" style="display: none;"
			href="<%=basePath%>ord/showOrderPay.do">
		</div>
		
		<div class="orderpop" id="historySaleDiv" style="display: none;"
			href="<%=basePath%>ordSale/transitOrderSaleAdd.do">
		</div>
		<div class="orderpop" id="historyInventoryDiv" style="display: none;"
			href="<%=basePath%>ordInventory/tansitShowOrder.do">
		</div>
		<div class="orderpop" id="order_sms" style="display: none;"
			href="<%=basePath%>ordSms/jumpSendOrdOrderSms.do">
		</div>
		<div class="orderpop" id="in_addDiv" style="display: none;"
			href="<%=basePath%>ord/showInvoiceAndAddress.do"></div>
		<div id="bg" class="bg" style="display: none;">
			<iframe
				style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity =         0); opacity =0; border-style: none; z-index: -1">
			</iframe>
		</div>
		<div id="bgPay" class="bg" style="display: none;">
		</div>

		<!--main2 end-->

	</body>
</html>
