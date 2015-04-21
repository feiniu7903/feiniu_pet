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
		<title>订单处理后台_订单监控</title>
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
		<script type="text/javascript"><!--
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
			$("input[name='dealTimeStart']").datepicker({dateFormat:'yy-mm-dd'});
			$("input[name='dealTimeEnd']").datepicker({dateFormat:'yy-mm-dd'});
		
		});
		$(document).ready(function () {
			var paymentStatus = '${paymentStatus}';
			var approveStatus = '${approveStatus}';
			var orderStatus = '${orderStatus}';
			var productType0 = '${productTypeList[0]}';
			var productType1 = '${productTypeList[1]}';
			var productType2 = '${productTypeList[2]}';
			var productType3 = '${productTypeList[3]}';
			var productType4 = '${productTypeList[4]}';
			var productType5 = '${productTypeList[5]}';
			var productType6 = '${productTypeList[6]}';
			var productType7 = '${productTypeList[7]}';
			var productType8 = '${productTypeList[8]}';
			/*
			var paymentStatusObj = document.getElementById('paymentStatus');
			for(var i = 0; i < paymentStatusObj.options.length; i++)
			{
				if(paymentStatusObj.options[i].value == paymentStatus)
				{
					paymentStatusObj.options[i].selected = true;
					break;
				}
			}
			var approveStatusObj = document.getElementById('approveStatus');
			for(var i = 0; i < approveStatusObj.options.length; i++)
			{
				if(approveStatusObj.options[i].value == approveStatus)
				{
					approveStatusObj.options[i].selected = true;
					break;
				}
			}
			
			var orderStatusObj = document.getElementById('orderStatus');
			for(var i = 0; i < orderStatusObj.options.length; i++)
			{
				if(orderStatusObj.options[i].value == orderStatus)
				{
					orderStatusObj.options[i].selected = true;
					break;
				}
			}
			*/
			/**
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
			*/
			if(productType0 == 'TICKET')
			{
				document.getElementById('productTypeList[0]').checked = true;
			}
			if(productType1 == 'GROUP')
			{
				document.getElementById('productTypeList[1]').checked = true;
			}
			if(productType2 == 'GROUP_LONG')
			{
				document.getElementById('productTypeList[2]').checked = true;
			}
			if(productType3 == 'GROUP_FOREIGN')
			{
				document.getElementById('productTypeList[3]').checked = true;
			}
			if(productType4 == 'FREENESS')
			{
				document.getElementById('productTypeList[4]').checked = true;
			}
			if(productType5 == 'FREENESS_FOREIGN')
			{
				document.getElementById('productTypeList[5]').checked = true;
			}
			if(productType6 == 'FREENESS_LONG')
			{
				document.getElementById('productTypeList[6]').checked = true;
			}
			if(productType7 == 'SELFHELP_BUS')
			{
				document.getElementById('productTypeList[7]').checked = true;
			}
			if(productType8 == 'HOTEL')
			{
				document.getElementById('productTypeList[8]').checked = true;
			}
		});
		
		function beforeSubmit() {
			if($("input[name='userName']").val()=="" && $("input[name='userMobile']").val() == ""&& $("input[name='userEmail']").val() == "" && $("input[name='contactName']").val() == ""&& $("input[name='contactMobile']").val() == ""&& $("input[name='orderId']").val() == ""&& $("input[name='productName']").val() == ""&& $("input[name='productID']").val() == ""&& $("input[name='userMembershipCard']").val() == "" 
					&&($("input[name='createTimeStart']").val()==""||$("input[name='createTimeEnd']").val()=='') && ($("input[name='visitTimeStart']").val()==""||$("input[name='visitTimeEnd']").val()=='')){
					alert("请至少填写一项查询条件！");
					return false;
				}		
			return true;
		}
		
		function sendContractEmail(orderId,productId,paymentStatus){
			if(orderId==null){
				return false;
			}
			if(!confirm('确定要重发合同??')){
				return false;
			}
			$.ajax({type:"POST",
					url:"<%=basePath%>/ajax/sendContractEmail.do", 
					data:{orderId:orderId,productId:productId,paymentStatus:paymentStatus}, 
					success:function (data) {
						alert(data.result);
					}
			});
			
		}
		
		--></script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>

	</head>
	<body>
		<form name='form1' method='post'
			action='<%=basePath%>ord/order_monitor_list!doOrderQuery.do?pageType=single' onSubmit="return beforeSubmit();">
			<div>
				<div class="main2">
					<!--=========================主体内容==============================-->
					<div class="table_box" id=tags_content_1>
						<div class="mrtit3">

							<table width="100%" border="0" class="newfont06"
								style="font-size: 12; text-align: left;" >
								<tr>
									<td width="8%">下单人姓名：</td>
									<td width="12%">
										<input name="userName"  type="text"  size="15" value="${userName}" />
									</td>
									<td width="8%">下单人手机：</td>
									<td width="12%">
										<input name="userMobile" type="text" size="15" value="${userMobile}" />
									</td>
									<td width="8%">电子邮件：</td>
									<td width="12%">
										<input name="userEmail"  type="text" size="15" value="${userEmail}" />
									</td>
									<td width="8%">
										下单时间：
									</td>
									<td width="32%">
										<input name="createTimeStart"
											id="createTimeStart" value="${createTimeStart}" />
										~
										<input name="createTimeEnd"
											id="createTimeEnd" value="${createTimeEnd}" />
									</td>
									
								</tr>
								<tr>
									<td width="8%">联系人姓名：</td>
									<td>
										<input name="contactName"  type="text" size="15"  value="${contactName}" />
									</td>
									<td width="8%">联系人手机：</td>
									<td>
										<input name="contactMobile" type="text" size="15" value="${contactMobile}" />
									</td>
									<td width="8%">订单编号：</td>
									<td>
										<input name="orderId"       type="text" size="15" value="${orderId}" />
									</td>
									
									<td width="8%">
										游玩时间：
									</td>
									<td width="32%">
										<input name="visitTimeStart"
											id = "visitTimeStart" value="${visitTimeStart}" />
										~
										<input  name="visitTimeEnd"
											id = "visitTimeEnd" value="${visitTimeEnd}" />
									</td>
									
									
									
								</tr>
								<tr>
									<td width="8%">产 品 名：</td>
									<td>
										<input name="productName" type="text" size="15"		value="${productName}" id="productName" />
									</td>
									<td width="8%">产 品 ID：</td>
									<td>
										<input name="productID" type="text" size="15"	value="${productID}" id="productID" />
									</td>
									<td width="8%">会员卡号：</td>
									<td>
										<input name="userMembershipCard" type="text" size="15"	value="${userMembershipCard}" />
									</td>
									<td colspan="2">
                                    <input type="submit" value="查 询" name="btnOrdSingQuery" class="right-button08" />                                    
                                    </td>
								</tr>
								<tr>
								    <td width="9%">出团通知书状态：</td>
										   <td colspan="6">	
	 						                  <s:select  list="#{'':'全部','NEEDSEND':'待发送','UPLOADED_NOT_SENT':'已上传待发送','SENT_NO_NOTICE':'已发送未通知','SENT_NOTICE':'已发送已通知','MODIFY_NO_NOTICE':'修改未通知','MODIFY_NOTICE':'修改已通知'}" name="groupWordStatus"></s:select>
							          </td>
 									<td>&nbsp;</td>
									<td width="8%">&nbsp;</td>
									<td  colspan="4">&nbsp;</td>
									<td  colspan="2">&nbsp;</td>
								</tr>

							</table>
</div>
						<br />
						<br />
						<div>
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
										下单人工号
									</td>
									<td width="6%">
										联系人姓名
									</td>
									<td width="6%">
										联系人电话
									</td>
									<td width="12%">
										产品名
									</td>
									<td width="5%">
										订购数量
									</td>
									<td width="5%">
										订单状态
									</td>
									<td width="3%">
										资源状态
									</td>
									<td width="3%">
										信息状态
									</td>
									<td width="5%">
										支付状态
									</td>
									<td width="5%">
										待付金额
									</td>
									<td width="8%">
										出团通知书状态
									</td>
									<td width="5%">
										履行状态
									</td>
									<td width="5%">
										售后服务
									</td>
									<td width="5%">
										结算状态
									</td>
									<td>
										操作
									</td>
									<td width="1%">
										 
									</td>
								</tr>
								<s:iterator id="order" value="ordersList">
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
						productId="${orderItem.productId}" prodBranchId="${orderItem.prodBranchId}">${orderItem.productName
													}</a>
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
										<td id="orderStatus_${orderId}">
											${zhOrderStatus }
										</td>
										<td>
											${zhApproveStatus }
										</td>
										<td>
											${zhInfoApproveStatus }
										</td>
										<td id="paymentStatus_${orderId}">
											${zhPaymentStatus }
										</td>
										<td>
											${unpayAmountFloat}
										</td>
										<td align="center" id="groupWordStatus_${order.orderId}">
										   <s:property value="#order.zhGroupWordStatus"/>
										   <!--  <s:if test="#order.groupWordStatus=='NEEDSEND'">待发送 </s:if>
										    <s:if test="#order.groupWordStatus=='SENT_NO_NOTICE'">已发送未通知</s:if>
										    <s:if test="#order.groupWordStatus=='SENT_NOTICE'">已发送已通知 </s:if>
										    <s:if test="#order.groupWordStatus=='MODIFY_NO_NOTICE'">修改未通知</s:if>
										    <s:if test="#order.groupWordStatus=='MODIFY_NOTICE'">修改已通知 </s:if>
										      -->
									   </td>
										<td>
											${zhPerformStatus }
										</td>
										<td>
											${zhNeedSaleService }
										</td>
										<td>
											<s:if test="settlementStatus == 'UNSETTLEMENTED'">${zhSettlementStatus}</s:if>
											<s:else><a href="javascript:showDetailDiv('settlementStatusDiv', '${orderId}');">${zhSettlementStatus}</a></s:else>
										</td>
										<td>
										<mis:checkPerm permCode="1640">
											<a href="javascript:showDetailDiv('historyDiv', '${orderId}');">查看</a>
											</mis:checkPerm>
											<s:if test="payToLvmama">
											|<a href="javascript:showDetaiPaylDiv('historyPayDiv','${orderId}','<s:property value="@com.lvmama.common.utils.PriceUtil@convertToYuan(oughtPay)"/>','${paymentStatus}');">支付信息</a>
											</s:if>
											<mis:checkPerm permCode="1643">
											|<a href="#" onClick="javascript:window.open('<%=basePath%>/ord/sale/OrdSaleAddJump.zul?orderId=${orderId}','','height=700,width=1000,top=200, left=200,scrollbars=yes')">售后服务</a>
											</mis:checkPerm>
											<mis:checkPerm permCode="1644">
											|<a href="javascript:showDetailDiv('in_addDiv', '${orderId }');">发票与物流</a>
											</mis:checkPerm>
											<s:if test="isShouldSendCert()" >
											<mis:checkPerm permCode="1745">
								    		 	|<a href="javascript:showDetaiSmsDiv('order_sms', '${orderId}','${contact.mobile }','OneSms','ord_list_single');">发送短信凭证</a>
								    		 	</mis:checkPerm>
											</s:if>
											<!-- 已支付，需要签约 -->
											<s:if test='"NEED_ECONTRACT"==needContract'>
												<mis:checkPerm permCode="3173">|<a href='<%=basePath%>ord/downPdfContractDetail.do?orderId=${orderId}&productId=${mainProduct.productId}'>下载合同</a></mis:checkPerm>
												<mis:checkPerm permCode="3174">|<a href='<%=basePath%>ord/downAdditionDetail.do?orderId=${orderId}&productId=${mainProduct.productId}'>下载补充条款</a></mis:checkPerm>
											</s:if>
											<s:if test='"ROUTE"==mainProduct.productType'>
												<mis:checkPerm permCode="3175">|<a href='<%=basePath%>/ord/downOrderTravel.do?orderId=${orderId}&productId=${mainProduct.productId}'>下载行程</a></mis:checkPerm>
											</s:if>
											<!-- 已支付，需要签约，联系人有邮箱 -->
											<s:if test='"PAYED"==paymentStatus &&  "NEED_ECONTRACT"==needContract && null!=contact && null!=contact.email && "CANCEL"!=orderStatus && "CONFIRM"==eContractStatus'>
												<mis:checkPerm permCode="3176">|<a href='###' onclick='sendContractEmail("${orderId}","${mainProduct.productId}","${paymentStatus}")'>补发合同</a></mis:checkPerm>
											</s:if>	
											<!-- 已上传出团通知书，下载出团通知书 -->
											<s:if test="#order.mainProduct.productType=='ROUTE'&&#order.paymentStatus=='PAYED'">
												<s:if test="#order.groupWordStatus=='SENT_NO_NOTICE'||#order.groupWordStatus=='SENT_NOTICE'||#order.groupWordStatus=='MODIFY_NO_NOTICE'||#order.groupWordStatus=='MODIFY_NOTICE'">
											    	|<a href="javascript:openWin('<%=request.getContextPath()%>/groupadvice/dwload.do?objectId=${order.orderId}&objectType=ORD_ORDER',500,400)">下载出团通知书</a>
											    </s:if>
											    <s:else>
											    	|<a href="javascript:void(0)" style="color:#ccc;" disabled=disabled>下载出团通知书</a>
											    </s:else>
											    
										    </s:if>
										   | <a href="/pet_back/work/order/add.do?orderId=${orderId}&mobileNumber=${mobileNumber}&productId=${mainProduct.productId}" target="_blank">新增工单</a>
										</td>
										<td>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
						</div>
					</div>
					<!--=========================主体内容 end==============================-->
				</div>
				<!--main2 end-->
			</div>
		</form>
		<table width="90%" border="0" align="center">
			<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
		</table>
		<div class="orderpop" id="historyDiv" style="display: none;"
			href="<%=basePath%>ord/showHistoryOrderDetail.do">
		</div>
	<div class="orderpop" id="settlementStatusDiv" style="display: none;"
			href="<%=basePath%>ord/showSettlementStatusDetail.do">
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
