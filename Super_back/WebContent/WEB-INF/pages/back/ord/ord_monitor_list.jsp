<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>订单处理后台_订单监控</title>
		<script type="text/javascript">
var path = '<%=basePath%>'; 
</script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/jquery-1.4.4.min.js">
</script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/remoteUrlLoad.js">
</script>
		<script type="text/javascript" src="<%=basePath%>js/base/form.js">
</script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js"
			type="text/javascript">
</script>
		<script language="javascript" src="<%=basePath%>js/ord/ord.js"
			type="text/javascript">
</script>
		<script language="javascript" src="<%=basePath%>js/ord/in_add.js"
			type="text/javascript">
</script>
		<script language="javascript" src="<%=basePath%>js/ord/ord_div.js"
			type="text/javascript">
</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/cc.css" />
		<link rel="stylesheet"
			href="<%=basePath%>themes/base/jquery.ui.all.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/ui/jquery-ui-1.8.5.js">
</script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/lvmama_common.js">
</script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/lvmama_dialog.js">
</script>
<script src="${basePath}js/phoneorder/important_tips.js"
			type="text/javascript">
</script>
		<script type="text/javascript">
<!--
		var down_order_id,down_product_id;
		$(document).ready(function(){
			$("#resourceLackReson").change( function() {
			 	var resonOptionValue = $("#resourceLackReson").find("option:selected").val();
			 	if(resonOptionValue != 'ALL'){
			 		//选中资源审核不通过
			 		$("#approveStatus").val('RESOURCEFAIL');
			 	}
			}); 
			$("#approveStatus").change( function() {
				var resonOptionValue = $("#approveStatus").find("option:selected").val();
			 	if(resonOptionValue != 'RESOURCEFAIL'){
			 		//选中资源审核不通过
			 		$("#resourceLackReson").val('ALL');
			 	}
			}); 
			$("#checkall").click( 
				function(){ 
					if(this.checked){ 
						$("input[name='checkBoxName']").each(function(){this.checked=true;}); 
					}else{ 
						$("input[name='checkBoxName']").each(function(){this.checked=false;}); 
					} 
				} 
				);
			$('#closeContractDiv').click(function(){
				$("#contractDiv,#bg").hide();	
			});
			$('#downPdfContractButton').click(function(){
				if(down_order_id!=null&&down_product_id!=null){
					//window.open("<%=basePath%>ord/downPdfContractDetail.do?orderId="+down_order_id+"&productId="+29150);
					window.open("/back/ord/downPdfContractDetail.zul?orderId="+down_order_id+"&productId="+29150);
				}else{
					alert("没有订单号或产品号");
				}
			});
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
			var productType9 = '${productTypeList[9]}';
			
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
			if(productType9 =='TRAIN'){
				document.getElementById('productTypeList[9]').checked = true;
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
		function showContractDiv(orderId,productId){
			if(orderId!=null&&productId!=null){
					document.location.href="<%=basePath%>ord/downPdfContractDetail.do?orderId="+orderId+"&productId="+productId;
				}else{
					alert("没有订单号或产品号");
			}
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
			src="<%=basePath%>js/base/jquery.datepick-zh-CN.js">
</script>
<style>
    .order_justify {
        text-align:justify;
        text-justify:distribute-all-lines;/*ie6-8*/
        text-align-last:justify;/* ie9*/
        -moz-text-align-last:justify;/*ff*/
        -webkit-text-align-last:justify;/*chrome 20+*/
    }
    @media screen and (-webkit-min-device-pixel-ratio:0){/* chrome*/
        .order_justify:after{
            content:".";
            display: inline-block;
            width:100%;
            overflow:hidden;
            height:0;
        }
    }
</style>
	</head>
	<body>
		<form name='form1' method='post'
			action='<%=basePath%>ord/order_monitor_list!doOrderQuery.do?pageType=monitor'
			onsubmit="return beforeSubmit();">
			<div>
				<div class="main2">
					<!--=========================主体内容==============================-->
					<div class="table_box" id=tags_content_1>
						<div class="mrtit3">

							<table width="99%" border="0" class="newfont06"
								style="font-size: 12; text-align: left;">
								<tr>
									<td width="8%">
										下单人姓名：
									</td>
									<td width="12%">
										<mis:checkElement permCode="1974" name="userName"
											value="${userName}" />
									</td>
									<td width="8%">
										下单人手机：
									</td>
									<td width="12%">
										<mis:checkElement permCode="1975" name="userMobile"
											value="${userMobile}" />
									</td>
									<td width="8%">
										电子邮件：
									</td>
									<td width="12%">
										<mis:checkElement permCode="1976" name="userEmail"
											value="${userEmail}" />
									</td>
									<td width="8%">
										下单时间：
									</td>
									<td width="32%">
										<mis:checkElement permCode="1977" name="createTimeStart"
											id="createTimeStart" value="${createTimeStart}" />
										~
										<mis:checkElement permCode="1977" name="createTimeEnd"
											id="createTimeEnd" value="${createTimeEnd}" />
									</td>
								</tr>
								<tr>
									<td width="8%">
										联系人姓名：
									</td>
									<td>
										<mis:checkElement permCode="1978" name="contactName"
											value="${contactName}" />
									</td>
									<td width="8%">
										联系人手机：
									</td>
									<td>
										<mis:checkElement permCode="1979" name="contactMobile"
											value="${contactMobile}" />
									</td>
									<td width="8%">
										订单编号：
									</td>
									<td>
										<mis:checkElement permCode="1980" name="orderId"
											value="${orderId}" />
									</td>
									<td width="8%">
										游玩时间：
									</td>
									<td>
										<mis:checkElement permCode="1981" name="visitTimeStart"
											id="visitTimeStart" value="${visitTimeStart}" />
										~
										<mis:checkElement permCode="1981" name="visitTimeEnd"
											value="${visitTimeEnd}" />
									</td>
								</tr>
								<tr>
									<td width="8%">
										产 品 名：
									</td>
									<td>
										<mis:checkElement permCode="1982" name="productName"
											id="productName" value="${productName}" />
									</td>
									<td width="8%">
										产 品 ID：
									</td>
									<td>
										<mis:checkElement permCode="1983" name="productID"
											id="productID" value="${productID}" />
									</td>
									<td width="8%">
										会员卡号：
									</td>
									<td>
										<mis:checkElement permCode="1984" name="userMembershipCard"
											value="${userMembershipCard}" />
									</td>
									<td width="8%">
										支付时间：
									</td>
									<td>
										<mis:checkElement permCode="1985" name="paymentTimeStart"
											id="paymentTimeStart" value="${paymentTimeStart}" />
										~
										<mis:checkElement permCode="1985" name="paymentTimeEnd"
											value="${paymentTimeEnd}" />
									</td>
								</tr>
								<tr>
									<td width="8%">
										支付状态：
									</td>
									<td>
										<mis:checkElement permCode="1986" id="paymentStatus"
											type="select" name="paymentStatus" style="width: 125px;">
											<option value="">
												全部
											</option>
											<option value="PAYED">
												已经支付
											</option>
											<option value="PARTPAY">
												部分支付
											</option>
											<option value="UNPAY">
												未支付
											</option>
											<option value="TRANSFERRED">
												已转移
											</option>
										</mis:checkElement>
									</td>
									<td width="8%">
										资源状态：
									</td>
									<td>
										<mis:checkElement permCode="1987" list="orderApproveList"
											type="select" id="approveStatus" listKey="code"
											listValue="name" headerValue="请选择" name="approveStatus"
											style="width:125px;" />
									</td>
									<td width="8%">
										订单状态：
									</td>
									<td>
										<mis:checkElement permCode="1988" id="orderStatus"
											type="select" name="orderStatus" style="width: 125px;">
											<option value="">
												全部
											</option>
											<option value="NORMAL">
												正常
											</option>
											<option value="CANCEL">
												取消
											</option>
											<option value="FINISHED">
												完成
											</option>
										</mis:checkElement>
									</td>
									<td width="8%">
										首处理时间：
									</td>
									<td>
										<mis:checkElement permCode="1989" name="dealTimeStart"
											id="dealTimeStart" value="${dealTimeStart}" />
										~
										<mis:checkElement permCode="1989" name="dealTimeEnd"
											id="dealTimeEnd" value="${dealTimeEnd}" />
									</td>
								</tr>
								<tr>
									<td width="8%">
										处 理 人：
									</td>
									<td>
										<mis:checkElement permCode="1990" name="takenOperator"
											id="takenOperator" value="${takenOperator}" />
									</td>
									<td width="8%">
										信息状态：
									</td>
									<td>
										<s:select list="infoApproveList"
											type="select" id="infoApproveStatus" listKey="code"
											listValue="name" headerKey="" headerValue="请选择" name="infoApproveStatus"
											style="width:125px;"/>
									</td>
									<td width="8%">
										产品类型：
									</td>
									<td colspan="3">
										<mis:checkElement permCode="1991" name="productTypeList[0]"
											type="checkbox" id="productTypeList[0]" value="TICKET" />
										门票
										<mis:checkElement permCode="1991" name="productTypeList[1]"
											type="checkbox" id="productTypeList[1]" value="GROUP" />
										短途跟团游
										<mis:checkElement permCode="1991" name="productTypeList[2]"
											type="checkbox" id="productTypeList[2]" value="GROUP_LONG" />
										长途跟团游
										<mis:checkElement permCode="1991" name="productTypeList[3]"
											type="checkbox" id="productTypeList[3]" value="GROUP_FOREIGN" />
										出境跟团游
										<mis:checkElement permCode="1991" name="productTypeList[4]"
											type="checkbox" id="productTypeList[4]" value="FREENESS" />
										目的地自由行
										<br />
										<mis:checkElement permCode="1991" name="productTypeList[8]"
											type="checkbox" id="productTypeList[8]" value="HOTEL" />
										酒店
										<mis:checkElement permCode="1991" name="productTypeList[5]"
											type="checkbox" id="productTypeList[5]"
											value="FREENESS_FOREIGN" />
										出境自由行
										<mis:checkElement permCode="1991" name="productTypeList[6]"
											type="checkbox" id="productTypeList[6]" value="FREENESS_LONG" />
										长途自由行
										<mis:checkElement permCode="1991" name="productTypeList[7]"
											type="checkbox" id="productTypeList[7]" value="SELFHELP_BUS" />
										自助巴士班
										<mis:checkElement permCode="1991" name="productTypeList[9]"
											type="checkbox" id="productTypeList[9]" value="TRAIN" />
										火车票
									</td>
								</tr>
								<tr>
									<td width="8%">
										订单来源：
									</td>
									<td>
										<mis:checkElement permCode="179" list="channelList"
											type="select" id="channel" listKey="code"
											listValue="name" headerValue="请选择" name="channel"
											style="width:125px;" />
									</td>
									<td>所属分公司:</td>
										<td><s:select list="filialeNameList" name="filialeName" listKey="code" listValue="name"/></td>
									<td>资源审核不通过原因:</td>
										<td><select name="resourceLackReson" id="resourceLackReson">
											<option value="ALL">全部</option>
											<option value="NO_RESOURCE" <s:if test="resourceLackReson=='NO_RESOURCE'">selected="selected"</s:if>>没有资源</option>
											<option value="PRICE_CHANGE" <s:if test="resourceLackReson=='PRICE_CHANGE'">selected="selected"</s:if>>价格更改</option>
											<option value="UNABLE_MEET_REQUIREMENTS" <s:if test="resourceLackReson=='UNABLE_MEET_REQUIREMENTS'">selected="selected"</s:if>>无法满足游客要求</option>
											<option value="OTHER" <s:if test="resourceLackReson=='OTHER'">selected="selected"</s:if>>其他</option>
										</select></td>
									 <td>出团通知书状态：</td>
										   <td class="order_justify">	
	 						                  <s:select  list="#{'':'全部','NEEDSEND':'待发送','UPLOADED_NOT_SENT':'已上传待发送','SENT_NO_NOTICE':'已发送未通知','SENT_NOTICE':'已发送已通知','MODIFY_NO_NOTICE':'修改未通知','MODIFY_NOTICE':'修改已通知'}" name="groupWordStatus"></s:select>
										<input type="submit" value="查 询" class="right-button08" name="btnOrdMonitorQuery" />
										<%--
										<mis:checkPerm permCode="1630">
											<input type="button" name="btnOrdBatchSendSms" value="批量发送短信" class="right-button08" onclick="javascript:showDetaiSmsDiv('order_sms', '','','ManySms','ord_list');" />
										</mis:checkPerm>
										 --%>
							               </td>
								</tr>

							</table>
						</div>
						<br />
										<font color='red' size="5">${hasParamMessage}</font>
						<br />
						<table cellspacing="1" cellpadding="4" border="0"
							bgcolor="#666666" width="99%" class="newfont06"
							style="font-size: 12; text-align: center;">
							<tbody>
								<tr bgcolor="#eeeeee">
									<td width="4%">
										<input type="checkbox" name="checkall" value="1" id="checkall" />
									</td>
									<td height="35" width="6%">
										订单号
									</td>
									<td height="35" width="6%">
										处理人
									</td>
									<td width="6%">
										联系人姓名
									</td>
									<td width="6%">
										联系人电话
									</td>
									<td width="8%">
										产品名
									</td>
									<td width="6%">
										订购数量
									</td>
									<td width="6%">
										订单状态
									</td>
									<td width="3%">
										资源状态
									</td>
									<td width="3%">
										信息状态
									</td>
									<td width="6%">
										支付状态
									</td>
									<td width="6%">
										待付金额
									</td>
									<td width="8%">
										出团通知书状态
									</td>
									<td width="6%">
										履行状态
									</td>
									<td width="6%">
										售后服务
									</td>
									<td width="6%">
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
											<input type="checkbox" name="checkBoxName" value="${orderId}"></input>
										</td>
										<td height="30">
											${orderId }<font color="red">${zhIsAperiodic }</font>
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
									    <!--   <s:if test="#order.groupWordStatus=='NEEDSEND'">待发送 </s:if>
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
												<a
													href="javascript:showDetailDiv('historyDiv', '${orderId}');">查看</a>
											<s:if test="payToLvmama">
											|<a
														href="javascript:showDetaiPaylDiv('historyPayDiv','${orderId}','<s:property value="@com.lvmama.common.utils.PriceUtil@convertToYuan(oughtPay)"/>','${paymentStatus}');">支付信息</a>
											</s:if>
											<mis:checkPerm permCode="1635">
											|<a href="#"
													onClick="javascript:window.open('<%=basePath%>/ord/sale/OrdSaleAddJump.zul?orderId=${orderId}','','height=700,width=1000,top=200, left=200,scrollbars=yes')">售后服务</a>
											</mis:checkPerm>
											<mis:checkPerm permCode="1636">
											|<a
													href="javascript:showDetailDiv('in_addDiv', '${orderId }');">发票与物流</a>
											</mis:checkPerm>
											<s:if test="isShouldSendCert()">
												<mis:checkPerm permCode="1743">
								    		 	|<a
														href="javascript:showDetaiSmsDiv('order_sms', '${orderId}','${contact.mobile }','OneSms','ord_list');">发送短信凭证</a>
												</mis:checkPerm>
											</s:if>
											<!-- 已支付，需要签约 -->
											<s:if test='"NEED_ECONTRACT"==needContract'>
												<mis:checkPerm permCode="3169">|<a href='<%=basePath%>ord/downPdfContractDetail.do?orderId=${orderId}&productId=${mainProduct.productId}'>下载合同</a></mis:checkPerm>
												<mis:checkPerm permCode="3170">|<a href='<%=basePath%>ord/downAdditionDetail.do?orderId=${orderId}&productId=${mainProduct.productId}'>下载补充条款</a></mis:checkPerm>
												<s:if test='"ROUTE"==mainProduct.productType'>
													<mis:checkPerm permCode="3171">|<a href='<%=basePath%>/ord/downOrderTravel.do?orderId=${orderId}&productId=${mainProduct.productId}'>下载行程</a></mis:checkPerm>
												</s:if>
											</s:if>
											
											<!-- 已支付，需要签约，联系人有邮箱 -->
											<s:if test='"PAYED"==paymentStatus &&  "NEED_ECONTRACT"==needContract && null!=contact && null!=contact.email && "CANCEL"!=orderStatus && "CONFIRM"==eContractStatus'>
												<mis:checkPerm permCode="3172">|<a href='###' onclick='sendContractEmail("${orderId}","${mainProduct.productId}","${paymentStatus}")'>补发合同</a></mis:checkPerm>
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
										    |<a href="/pet_back/work/order/add.do?orderId=${orderId}&mobileNumber=${mobileNumber}&productId=${mainProduct.productId}" target="_blank">新增工单</a>
										</td>
										<td>
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
		</form>
		<table width="90%" border="0" align="center">
			<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
		</table>
		<div class="orderpop" id="historyDiv" style="display: none;"
			href="<%=basePath%>ord/showHistoryOrderDetail.do">
		</div>
		<div class="orderpop" id="historyPayDiv" style="display: none;width: 90% !important;margin-left: -600px ! important;"
			href="<%=basePath%>ord/showOrderPay.do">
		</div>
		<div class="orderpop" id="settlementStatusDiv" style="display: none;"
			href="<%=basePath%>ord/showSettlementStatusDetail.do"></div>
		
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
				style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity =                                           0); opacity =0; border-style: none; z-index: -1">
			</iframe>
		</div>
		<div id="bgPay" class="bg" style="display: none;">
		</div>
		<%--
		<div class="orderpop" id="contractDiv" style="display: none; top:250px; width:300px;height:100px;">
			<span style="width:260px;"><font size="+1">合同管理</font></span>
			<span style="background-color:red;right:0px;cursor:pointer;" id="closeContractDiv"><font style="color:white;font-size:14px;font-weight:bold;">×</font></span>
			<br/>
			<span>
			<table width="100%">
			<tr>
			<td><input name="btnViewContract" type="button" id="" value="查看合同"/></td><td><input type="button" name="btnDownContract" id="downPdfContractButton" value="下载合同"/></td> 
			</tr>
			</table>
			</span>
		</div>
		 --%>
		<!--main2 end-->

	</body>
</html>

