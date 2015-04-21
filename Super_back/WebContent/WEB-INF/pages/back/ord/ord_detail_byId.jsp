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
		<title>查看订单明细</title>
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
<script type="text/javascript"
	src="<%=basePath%>js/base/jquery.datepick-zh-CN.js">
</script>
	<script type="text/javascript">
			var orderId = '${orderDetail.orderId }', operatFrom = 'monitor', contactId = '${orderDetail.contact.personId }', userId = '${orderDetail.userId }', isPhysical = '${orderDetail.physical}';
			$(document).ready(function() {
				$("#youhuiContent").loadUrlHtml();
				$("#memoDiv").loadUrlHtml();
				$("#priceDiv").loadUrlHtml();
				$("#orderCoupon").loadUrlHtml();
				$("#choseCoupon").loadUrlHtml();
				$("#editReceiverDialg").lvmamaDialog( {
					modal : false,
					width : 600,
					height : 350,
					close : function() {
					}
				});
				
				//批量发送团通知
				$("#batchSendNotifyBtn").click(function(){
					var btn=$(this);
					if(confirm("确定要重发出团通知吗?")){
						$(btn).attr("disabled",true);
						$.ajax({
						   type: "POST",
						   dataType:"json",
						   url: "<%=basePath%>groupadvice/doReSendGroupAdviceNote.do",
						   data: "orderId="+orderId,
						   error:function(){
							   alert('网络请求错误，请稍后再试');
							   $(btn).attr("disabled",false);
						   },
						   success: function(data){
						     alert(data.message);
						     $(btn).attr("disabled",false);
						   }
						});
					}
					return false;
				});
			});

		/**
		 * 改变通知出团通知书状态
		 */
			 function changeGroupAdviceStatus(){
				      var groupstatus='${orderDetail.orderRoute.groupWordStatus}';
				       
				      if(groupstatus=="<%=com.lvmama.comm.vo.Constant.GROUP_ADVICE_STATE.SENT_NO_NOTICE.name()%>"||
						      groupstatus=="<%=com.lvmama.comm.vo.Constant.GROUP_ADVICE_STATE.MODIFY_NO_NOTICE.name()%>"){
				 	          //修改数据库表出团通知书状态状态
				 	          var basePh= '${basePath}';		
				 	          $.ajax({type:"POST", dataType:"json", url:basePh + "groupadvicenote/updateNoticeStatus.do", data:{orderId:orderId,groupWordStatus:groupstatus}, success:function (result) {
				 	    	 	if (result.jsonMsg == "ok") {
				 	    	 		 $("#btnGroupAdviceStatus").css("color","#ccc");
		 				 	         $("#btnGroupAdviceStatus").attr("disabled",true);
		 				 	         
		 				 	         alert("修改成功!");
				 	    			 
				 	    	 	} else {
				 	    	 		 alert("error");
				 	    	 	}
				 	    	}});
		 	          
				       }
		     }
		
		function updateOrAddPerson(type) {
			var personOrderId = $("#personOrderId").val();
			if (personOrderId == "" || personOrderId == null) {
				alert("订单id不能为空");
				return false;
			}
			$('#editReceiverDialg').attr("href", "<%=basePath%>/ord/person.do");
			$("#editReceiverDialg").reload( {
				orderId : personOrderId
			});
			$('#editReceiverDialg').openDialog();
		}
		//后台订单取消 在订单监控中 取消已经通过一城卡支付成功的订单时
		//系统不走退款到现金帐户的流程 出现相关提示 请发一城卡退款传真 由一城卡完成原路退款 提示确定后订单状态变更为取消
		function oneCityOneCard(oneCityOneCardFlag, historyDiv) {
			if (oneCityOneCardFlag == 'true') {
				if (confirm("请发一城卡退款传真, 由一城卡完成原路退款")) {
					doCancelOrder(historyDiv);
					return true;
				}
			} else {
				var cancelResson = $("#cancelReason").find("option:selected").text();
				cancelResson = encodeURI(cancelResson);
				window
						.open(
								'<%=basePath%>/ord/refundMent/ordOrderRefundAdd.zul?orderId=${orderDetail.orderId}&isCancelOrder=true&cancelResson=' + cancelResson,
								'',
								'height=500,width=1000,top=200, left=200,scrollbars=yes');
			}
		}
function updateContract(orderId){
	if(orderId==null){
		return false;
	}
	if(!confirm('确定要更新发送合同??')){
		return false;
	}
	$.ajax({type:"POST",
		     url: "<%=basePath%>ajax/updateContract.do", 
		    data:{orderId:orderId}, 
		 success: function(data){
	    			alert(data.result);
				  }
		  });
}
</script>
	</head>
	<body>
		<!--=========================我的历史订单弹出层==============================-->
	<div class="orderpoptit">
		<strong>订单查看：</strong>
		<p class="inputbtn">
				<input type="button" name="btnCloseOrder" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('historyDiv');">
			</p>
	</div>
	<div class="orderpopmain">
		<table style="font-size: 12px" width="100%" border="0" id="orderTable"
			class="contactlist">
			<tr>
				<td width="20%">订单号：${orderDetail.orderId }</td>
				<td width="30%">下单时间：${orderDetail.zhCreateTime }</td>
				<td width="20%">下单人：${orderDetail.userName }</td>
				<td width="30%">支付等待时间：<span id="waitPaymentSpan"> <s:if
							test="orderDetail.hasNeedPrePay()">
							<s:date name="orderDetail.aheadTime" format="yyyy-MM-dd HH:mm" />
						</s:if>
						<s:else>${orderDetail.zhWaitPayment}</s:else>
				</span>
				</td>
			</tr>
			<tr>
				<td>订单产品金额：${orderAmountItem.amountYuan}</td>
				<td>优惠金额：${sumYouHuiAmount }</td>
				<td>应付金额：${orderDetail.oughtPayYuan }</td>
				<td>实付金额： <span style="color: #007500; font-weight: bold">${orderDetail.actualPayYuan}
				</span>
				</td>
			</tr>
			<tr>
				<td>退款金额：${sumRefundmentAmount}</td>
				<td>补偿金额：${sumCompensationAmount}</td>
				<td>金额调整：</td>
				<td>订单目前应有金额： <span style="color: #f00; font-weight: bold">
						<s:if
							test="orderDetail.paymentStatus=='UNPAY' && orderDetail.paymentTarget =='TOLVMAMA'">
								0
							</s:if> <s:elseif test="orderDetail.paymentTarget =='TOSUPPLIER'">  
								0
							</s:elseif> <s:else>
							<fmt:formatNumber
								value="${orderDetail.actualPayYuan - sumRefundmentAmount}"
								pattern="#.##" />
						</s:else>
				</span>
				</td>
			</tr>
			<tr>
				<td>支付状态：${orderDetail.zhPaymentStatus }</td>
				<td>订单状态：${orderDetail.zhOrderStatus}</td>
				<td>结算状态：${settlementStatus }<input id="settlementStatus"
					type="hidden" value="${orderDetail.settlementStatus}" />
				</td>
				<td>订单来源渠道：${orderDetail.zhProductChannel }</td>
			</tr>
			<tr>
				<td>优惠券优惠金额：${youHuiAmountList }</td>
				<td>优惠券名称：${youHuiNameList }</td>
				<td>优惠活动优惠金额：</td>
				<td>优惠活动名称：</td>
			</tr>
            <tr>
				<td>早订早惠优惠金额：${earlyCouponAmount }</td>
				<td colspan="3">优惠名称：${earlyCouponName }</td>
			</tr>
			<tr>
	            <td>多订多惠优惠金额：${moreCouponAmount }</td>
    	        <td colspan="3" >优惠名称：${moreCouponName }</td>
            </tr>
			<tr>
				<td>返现金额：${orderDetail.cashRefundYuan}</td>
				<td>奖金支付金额：${orderDetail.bonusPaidAmountYuan }</td>
				<td><s:if test="orderDetail.needContract=='NEED_ECONTRACT'"> 
							合同：
							<s:if
							test="orderDetail.eContractStatus!='CONFIRM'&&orderDetail.orderStatus=='CANCEL'">
								已作废
							</s:if>
						<s:elseif test="orderDetail.eContractStatus!='CONFIRM'">
								未签约 
							</s:elseif>
						<s:elseif test="orderDetail.orderStatus=='CANCEL'">
								已作废
							</s:elseif>
						<s:elseif
							test="orderDetail.paymentStatus!='PAYED'||orderDetail.approveStatus!='VERIFIED'">
								已签约未生效
							</s:elseif>
						<s:elseif test="orderDetail.paymentStatus=='PAYED'">
								已生效
							</s:elseif>
					</s:if></td>
				<td><s:if test="orderDetail.travelGroupCode!=null">
						出票：
						<s:if test="orderDetail.trafficTicketStatus=='false'">未开票 </s:if>
						<s:if test="orderDetail.trafficTicketStatus=='true'">已开票</s:if>
					</s:if></td>
			</tr>
			<tr>
				<s:if test="orderDetail.travelGroupCode!=null">
					<td>签证状态： ${orderDetail.zhVisaStatus}</td>
				</s:if>
				<td colspan="2">用户备注：${orderDetail.userMemo } <s:if
						test='orderDetail.orderRoute.groupWordStatus=="SENT_NOTICE"||
 						      orderDetail.orderRoute.groupWordStatus=="MODIFY_NOTICE"'>
						<input type="button" id="btnGroupAdviceStatus" value="已通知用户出团通知书"
							onclick="changeGroupAdviceStatus()" style="color: #ccc;"
							disabled=disabled />
						<input type="button" id="batchSendNotifyBtn" value="重发用户出团通知书"
							onclick="batchSendNotify('${orderDetail.orderId}');" />
					</s:if> <s:elseif
						test='orderDetail.orderRoute.groupWordStatus=="SENT_NO_NOTICE"||
 						             orderDetail.orderRoute.groupWordStatus=="MODIFY_NO_NOTICE"'>
						<input type="button" id="btnGroupAdviceStatus" value="已通知用户出团通知书"
							onclick="changeGroupAdviceStatus()" />
						<input type="button" id="batchSendNotifyBtn" value="重发用户出团通知书"
							onclick="batchSendNotify('${orderDetail.orderId}');" />
					</s:elseif>
				</td>
				<td><s:if
						test="!isCardPay && orderDetail.orderStatus=='NORMAL' && !orderDetail.isHotel() && orderDetail.paymentTarget == 'TOSUPPLIER' && orderDetail.performStatus=='UNPERFORMED' && !orderDetail.hasSettlement">
						<s:set name="showBtFlag" value="true"></s:set>
						<s:iterator value="orderDetail.ordOrderItemProds">

							<s:if test="productType=='HOTEL'">
								<s:set name="showBtFlag" value="false"></s:set>
							</s:if>
						</s:iterator>

					</s:if> <s:if
						test="!isCardPay && orderDetail.orderStatus=='NORMAL' &&  !orderDetail.isHotel() && orderDetail.paymentTarget == 'TOLVMAMA' && orderDetail.approveStatus=='VERIFIED' && (orderDetail.paymentStatus == 'UNPAY' || (orderDetail.paymentStatus == 'PAYED' && !orderDetail.outLimitTime ))">
						<s:set name="showBtFlag" value="true"></s:set>
						<s:iterator value="orderDetail.ordOrderItemProds">
							<s:if test="productType=='HOTEL'">
								<s:set name="showBtFlag" value="false"></s:set>
							</s:if>
						</s:iterator>

					</s:if></td>
			</tr>
			<tr>
				<!-- 延长支付等待时间控件 -->
				<td colspan="4"><s:if test="showDelayWaitPaymentFlag">							
							延长支付等待时间：
							<input type="hidden" id="orderId" value="${orderDetail.orderId}" />
						<select id="delayWaitPaymentSelect">
							<s:iterator value="waitPaymentMap">
								<option value="<s:property value="value"/>">
									<s:property value="key" />
								</option>
							</s:iterator>
						</select>
						<input type="button" id="delayWaitPaymentBtn" value="保存" />
					</s:if></td>
			</tr>
		</table>

		<div
			style="border: 1px solid #ccc; background-color: #efefef; margin: 5px; padding: 5px; text-align: left">
			文件列表 <a
				href="javascript:openWin('<%=request.getContextPath()%>/common/upload.do?objectId=${orderDetail.orderId}&objectType=ORD_ORDER',500,400)"">上传文件</a>
			<s:if test="groupComAffixList!=null&&groupComAffixList.size()>0">
				<div>团文件列表</div>
				<table width="60%">
					<s:iterator value="groupComAffixList" id="affix">
						<tr>
							<td><span title="${affix.memo}">${affix.name}</span></td>
							<td width="80"><a
								href="http://pic.lvmama.com/pics/${affix.path}" target="_blank">文件下载</a>
							</td>
						</tr>

					</s:iterator>
				</table>
			</s:if>
			<s:if test="orderComAffixList!=null&&orderComAffixList.size()>0">
				<div>订单文件列表</div>
				<table width="60%">
					<s:iterator value="orderComAffixList" id="affix">
						<tr>
							<td><span title="${affix.memo}">${affix.name}</span></td>
							<td width="80"><s:if
									test="#affix.fileType=='GROUP_ADVICE_NOTE'">
									<a target="_blank"
										href="/super_back/groupadvice/download.do?fileId=<s:property value="#affix.fileId"/>&fileName=<s:property value="#affix.name"/>">文件下载</a>
								</s:if> <s:else>
									<a href="http://pic.lvmama.com/pics/${affix.path}"
										target="_blank">文件下载</a>
								</s:else></td>
						</tr>
					</s:iterator>
				</table>
			</s:if>
		</div>

		<!--=============商品清单=============-->
		<div class="popbox">
			<strong>商品清单</strong>
			<p class="paytime">
				游玩时间：
				<s:if test="orderDetail.orderType=='HOTEL'">
					<s:iterator value="orderDetail.ordOrderItemProds">
						<s:if test="subProductType=='SINGLE_ROOM'">
					${dateRange}
					</s:if>
						<s:else>
					${zhVisitTime}
					</s:else>
					</s:iterator>
				</s:if>
				<s:else>
					${orderDetail.zhVisitTime }
					</s:else>
			</p>
			<s:if test="orderDetail.isAble() && orderDetail.isHasLastCancelTime()">
				<p class="lastCanceltime">
					最晚修改或取消时间：
					<s:date name="orderDetail.lastCancelTime" format="yyyy-MM-dd HH:mm" />
				</p>
			</s:if>
			<s:if test="orderDetail.hasNeedPrePay()">
				<div>此订单已过最晚取消时间，只能通过预授权方式支付，一旦资源审核通过后此订单不退不改</div>
			</s:if>
			<br />
			<s:if test="viewPage != null">
				<s:if test="viewPage.contents.REFUNDSEXPLANATION.content!=null">
					<p class="paytime">
						退款说明：
						<s:property value="viewPage.contents.REFUNDSEXPLANATION.content" />
					</p>
				</s:if>
			</s:if>
			<br />
			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
				border="0" bgcolor="#666666" width="100%" class="newfont05">
				<tbody>
					<tr class="CTitle">
						<td height="22" align="center" style="font-size: 16px;"
							colspan="10">商品清单</td>
					</tr>
				</tbody>
			</table>
			<!-- 超级自由行，主产品 -->
			<s:if test="orderDetail.hasSelfPack()">
					主产品
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#666666" width="100%" class="newfont05">
					<tr bgcolor="#eeeeee">
						<td height="35" width="4%">序号</td>
						<td width="23%">产品名称</td>
						<!-- <td width="8%">
								会员价
							</td> -->
						<td width="13%">数量</td>
						<!-- <td width="8%">
								总金额
							</td> -->
						<td width="8%">产品类型</td>
						<td width="6%">优惠</td>
						<td width="10%">游玩时间</td>
						<!-- <td width="13%">
							最晚修修改或取消时间
							</td>
							 -->
					</tr>
					<tr bgcolor="#ffffff">
						<td height="30">${orderDetail.mainProduct.productId }</td>
						<td><a class="showImportantTips" href="javascript:void(0)"
							productId="${orderDetail.mainProduct.productId}"
							prodBranchId="${orderDetail.mainProduct.prodBranchId}">${orderDetail.mainProduct.productName}</a>
						</td>
						<!-- <td>
								${orderDetail.mainProduct.priceYuan }
							</td> -->
						<td>${orderDetail.mainProduct.quantity }</td>
						<!-- <td>
								${orderDetail.mainProduct.price*orderDetail.mainProduct.quantity/100
								}
							</td> -->
						<td>${orderDetail.mainProduct.zhProductType }</td>
						<td>${orderDetail.mainProduct.zhAdditional }</td>
						<td>${zhVisitTime}</td>
					</tr>
				</table>
			</s:if>
			<!-- 门票块 -->
			<s:if test="orderDetail.orderType == 'FREENESS'">
				门票信息
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#666666" width="100%" class="newfont05">
					<tr bgcolor="#eeeeee">
						<td height="35" width="4%">序号</td>
						<td width="15%">产品名称</td>
						<td width="8%">会员价</td>
						<td width="5%">数量</td>
						<td width="8%">总金额</td>
						<td width="8%">产品类型</td>
						<td width="6%">优惠</td>
						<td width="10%">游玩时间</td>
						<!-- <td width="13%">
							最晚修修改或取消时间
							</td>
							 -->
					</tr>
					<s:iterator id="prod" value="orderDetail.ordOrderItemProds">
						<s:if test="#prod.productType=='TICKET'">
							<tr bgcolor="#ffffff">
								<td height="30">${prod.productId }</td>
								<td><a class="showImportantTips" href="javascript:void(0)"
									productId="${prod.productId}"
									prodBranchId="${prod.prodBranchId}">${prod.productName}</a></td>
								<td>${prod.priceYuan }</td>
								<td>${prod.quantity }</td>
								<td>${prod.price*prod.quantity/100 }</td>
								<td>${prod.zhProductType }</td>
								<td>${prod.zhAdditional }</td>
								<td>${zhVisitTime}</td>
							</tr>
						</s:if>
					</s:iterator>
				</table>
				<!-- 酒店块 -->
					酒店信息
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#666666" width="100%" class="newfont05">
					<tr bgcolor="#eeeeee">
						<td height="35" width="4%">序号</td>
						<td width="15%">产品名称</td>
						<td width="8%">会员价</td>
						<td width="5%">数量</td>
						<td width="8%">总金额</td>
						<td width="8%">产品类型</td>
						<td width="6%">优惠</td>
						<td width="10%">游玩时间</td>
						<!-- <td width="13%">
							最晚修修改或取消时间
							</td>
							 -->
					</tr>
					<s:iterator id="prod" value="orderDetail.ordOrderItemProds">
						<s:if test="#prod.productType=='HOTEL'">
							<tr bgcolor="#ffffff">
								<td height="30">${prod.productId }</td>
								<td><a class="showImportantTips" href="javascript:void(0)"
									productId="${prod.productId}"
									prodBranchId="${prod.prodBranchId}">${prod.productName }</a></td>
								<td>${prod.priceYuan }</td>
								<td>${prod.hotelQuantity }</td>
								<td>${prod.price*prod.quantity/100 }</td>
								<td>${prod.zhProductType }</td>
								<td>${prod.zhAdditional }</td>
								<td><s:if test="subProductType=='SINGLE_ROOM'">
					${dateRange}
					</s:if> <s:else>
					${zhVisitTime}
					</s:else></td>
							</tr>
						</s:if>
					</s:iterator>
				</table>
				<!-- 线路块 -->
					线路信息
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#666666" width="100%" class="newfont05">
					<tr bgcolor="#eeeeee">
						<td height="35" width="4%">序号</td>
						<td width="15%">产品名称</td>
						<td width="8%">会员价</td>
						<td width="5%">数量</td>
						<td width="8%">总金额</td>
						<td width="8%">产品类型</td>
						<td width="6%">优惠</td>
						<td width="10%">游玩时间</td>
					</tr>
					<s:iterator id="prod" value="orderDetail.ordOrderItemProds">
						<s:if test="#prod.productType=='ROUTE'">
							<tr bgcolor="#ffffff">
								<td height="30">${prod.productId }</td>
								<td><a class="showImportantTips" href="javascript:void(0)"
									productId="${prod.productId}"
									prodBranchId="${prod.prodBranchId}">${prod.productName}</a></td>
								<td>${prod.priceYuan }</td>
								<td>${prod.hotelQuantity }</td>
								<td>${prod.price*prod.quantity/100 }</td>
								<td>${prod.zhProductType }</td>
								<td>${prod.zhAdditional }</td>
								<td>${zhVisitTime}</td>
							</tr>
						</s:if>
					</s:iterator>
				</table>
					
					交通信息
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#666666" width="100%" class="newfont05">
					<tr bgcolor="#eeeeee">
						<td height="35" width="4%">序号</td>
						<td width="15%">产品名称</td>
						<td width="8%">会员价</td>
						<td width="5%">数量</td>
						<td width="8%">总金额</td>
						<td width="8%">产品类型</td>
						<td width="6%">优惠</td>
						<td width="10%">游玩时间</td>
					</tr>
					<s:iterator id="prod" value="orderDetail.ordOrderItemProds">
						<s:if test="#prod.productType=='TRAFFIC'">
							<tr bgcolor="#ffffff">
								<td height="30">${prod.productId }</td>
								<td><a class="showImportantTips" href="javascript:void(0)"
									productId="${prod.productId}"
									prodBranchId="${prod.prodBranchId}">${prod.productName}</a></td>
								<td>${prod.priceYuan }</td>
								<td>${prod.hotelQuantity }</td>
								<td>${prod.price*prod.quantity/100 }</td>
								<td>${prod.zhProductType }</td>
								<td>${prod.zhAdditional }</td>
								<td>${zhVisitTime}</td>
							</tr>
						</s:if>
					</s:iterator>
				</table>
			</s:if>
			<s:else>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#666666" width="100%" class="newfont05">
					<tr bgcolor="#eeeeee">
						<td height="35" width="4%">序号</td>
						<td width="15%">产品名称</td>
						<td width="8%">会员价</td>
						<td width="5%">数量</td>
						<td width="8%">总金额</td>
						<td width="8%">产品类型</td>
						<td width="6%">优惠</td>
						<td width="10%">游玩时间</td>
						<!-- <td width="13%">
							最晚修修改或取消时间
							</td>
							 -->
					</tr>
					<s:iterator id="prod" value="orderDetail.ordOrderItemProds">
						<s:if test="#prod.productType!='OTHER'">
							<tr bgcolor="#ffffff">
								<td height="30">${prod.productId }</td>
								<td><a class="showImportantTips" href="javascript:void(0)"
									productId="${prod.productId}"
									prodBranchId="${prod.prodBranchId}">${prod.productName}</a></td>
								<td>${prod.priceYuan }</td>
								<td><s:if test="#prod.productType=='HOTEL'">
											${prod.hotelQuantity }
										</s:if> <s:else>
											${prod.quantity }
										</s:else></td>
								<td>${prod.price*prod.quantity/100 }</td>
								<td>${prod.zhProductType }</td>
								<td>${prod.zhAdditional }</td>
								<td><s:if test="subProductType=='SINGLE_ROOM'">
						${dateRange}
						</s:if> <s:else>
						${zhVisitTime}
						</s:else></td>
								<!--
										<td>
											<s:if test="#prod.lastCancelTime!=null">
												<s:date name="#prod.lastCancelTime" format="yyyy-MM-dd HH:mm"/>
											</s:if>
										</td>
										-->
							</tr>
						</s:if>
					</s:iterator>
				</table>
			</s:else>
			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
				border="0" bgcolor="#666666" width="100%" class="newfont06">
				<tbody>
					<tr class="CTitle">
						<td height="20" align="center" style="font-size: 14px;"
							colspan="9">附加产品</td>
					</tr>
					<tr bgcolor="#eeeeee">
						<td height="35" width="5%">序号</td>
						<td width="18%">产品名称</td>
						<td width="8%">会员价</td>
						<td width="8%">总金额</td>
						<td width="8%">产品类型</td>
						<td width="7%">数量</td>
						<td width="12%">游玩时间</td>
						<td width="10%">信息</td>
					</tr>
					<s:iterator id="prod" value="orderDetail.ordOrderItemProds">
						<s:if test="#prod.productType=='OTHER'">
							<tr bgcolor="#ffffff">
								<td height="30">${prod.productId }</td>
								<td><a class="showImportantTips" href="javascript:void(0)"
									productId="${prod.productId}"
									prodBranchId="${prod.prodBranchId}">${prod.productName}</a></td>
								<td>${prod.priceYuan }</td>
								<td>${prod.price*prod.quantity/100}</td>
								<td>${prod.zhProductType }</td>
								<td><s:if test="#prod.productType=='HOTEL'">
										${prod.hotelQuantity }
									</s:if> <s:else>
										${prod.quantity }
									</s:else></td>
								<td><s:if test="subProductType=='SINGLE_ROOM'">
					${dateRange}
					</s:if> <s:else>
					${zhVisitTime}
					</s:else></td>
								<td><s:if test="#prod.subProductType=='INSURE'">
										<a
											href="javascript:openWin('<%=basePath%>insurance/viewPolicyStatus.zul?orderId=${orderDetail.orderId}&prodId=${prod.productId}',900,300)">查看</a>
									</s:if> <s:else>
											${prod.subProductType }
										</s:else></td>
							</tr>
						</s:if>
					</s:iterator>
				</tbody>
			</table>
		</div>
		<div>
			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
				border="0" bgcolor="#666666" width="100%" class="newfont06">
				<tbody>
					<tr class="CTitle">
						<td height="20" align="center" style="font-size: 14px;"
							colspan="9">采购产品</td>
					</tr>
					<tr bgcolor="#eeeeee">
						<td>订单子号</td>
						<td>序号</td>
						<td>类别</td>
						<td>产品名称</td>
						<td>产品类型</td>
						<td>需要审核</td>
						<td>审核状态</td>
						<td>是否领单</td>
					</tr>
					<s:iterator value="orderDetail.allOrdOrderItemMetas" var="meta">
						<tr bgcolor="#ffffff">
							<td>${meta.orderItemMetaId}</td>
							<td>${meta.metaProductId}</td>
							<td>${meta.metaBranchId}</td>
							<td>${meta.productName}</td>
							<td>${meta.zhProductType}</td>
							<td><s:if test="resourceConfirm=='true'">是</s:if>
								<s:else>否</s:else></td>
							<td>${meta.zhResourceStatus}</td>
							<td><s:if test="taken='true'">是</s:if>
								<s:else>否</s:else></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		<!--popbox end-->


		<!--=============用户信息=============-->
		<div class="popbox">
			<strong>用户信息</strong>
			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
				border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
				<tbody>
					<tr bgcolor="#f4f4f4" align="center">
						<td width="16%" height="25">卡号</td>
						<td width="16%">用户名</td>
						<td width="16%">用户姓名</td>
						<td width="16%">手机号</td>
						<td width="16%">邮箱地址</td>
					</tr>
					<tr bgcolor="#ffffff" align="center">
						<td height="25">${orderDetail.user.cardId }</td>
						<td>${orderDetail.user.userName }</td>
						<td>${orderDetail.user.realName }</td>
						<td>${orderDetail.user.mobileNumber }</td>
						<td>${orderDetail.user.email }</td>
					</tr>
				</tbody>
			</table>

			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
				border="0" bgcolor="#B8C9D6" width="100%" class="newfont04">
				<tbody>
					<tr bgcolor="#f4f4f4" align="center">
						<td height="30" width="8%">类别</td>
						<td width="5%">姓名</td>
						<td width="6%">联系电话</td>
						<td width="9%">Email</td>
						<td width="8%">证件类型</td>
						<td width="10%">证件号码</td>
						<td width="5%">邮编</td>
						<td width="10%">地址</td>
						<td width="5%">座机号</td>
						<td width="5%">传真</td>
						<td width="8%">传真接收人</td>
						<td width="8%">出生日期</td>
						<td>性别</td>
					</tr>
					<s:if test="orderDetail.contact!=null">
						<tr bgcolor="#ffffff" align="center"
							href="<%=basePath%>usrReceivers/loadList.do">
							<td height="25">取票人/联系人
							</td>
							<td>${orderDetail.contact.name }</td>
							<td>${orderDetail.contact.mobile }</td>
							<td>${orderDetail.contact.email }</td>
							<td>${orderDetail.contact.zhCertType }</td>
							<td>${orderDetail.contact.certNo }</td>
							<td>${orderDetail.contact.postcode }</td>
							<td>${orderDetail.contact.address }</td>
							<td>${orderDetail.contact.tel }</td>
							<td>${orderDetail.contact.fax }</td>
							<td>${orderDetail.contact.faxTo }</td>
							<td>${orderDetail.contact.zhBrithday }</td>
							<td>${orderDetail.contact.zhGender }</td>
						</tr>
						<script>
contactReceiverId = '${orderDetail.contact.receiverId }';
</script>
					</s:if>
					<s:iterator id="person" value="orderDetail.personList">
						<s:if test="#person.personType=='ADDRESS'">
							<tr bgcolor="#ffffff" align="center">
								<td height="25">配送地址</td>
								<td>${person.name }</td>
								<td>${person.mobile }</td>
								<td>${person.email }</td>
								<td>${person.zhCertType }</td>
								<td>${person.certNo }</td>
								<td>${person.postcode }</td>
								<td>${person.address }</td>
								<td>${person.tel }</td>
								<td>${person.fax }</td>
								<td>${person.faxTo }</td>
								<td>${person.zhBrithday }</td>
								<td>${person.zhGender }</td>
							</tr>
						</s:if>
					</s:iterator>
					<s:iterator id="person" value="orderDetail.personList">
						<s:if test="#person.personType=='TRAVELLER'">
							<tr bgcolor="#ffffff" align="center">
								<td height="25">游客
								</td>
								<td><a
									href="/pet_back/visa/approval/showVitMaterial.do?searchOrderId=${orderDetail.orderId}&searchPersonId=${person.personId }"
									target="_visa">${person.name }</a></td>
								<td>${person.mobile }</td>
								<td>${person.email }</td>
								<td>${person.zhCertType }</td>
								<td>${person.certNo }</td>
								<td>${person.postcode }</td>
								<td>${person.address }</td>
								<td>${person.tel }</td>
								<td>${person.fax }</td>
								<td>${person.faxTo }</td>
								<td>${person.zhBrithday }</td>
								<td>${person.zhGender }</td>
							</tr>
						</s:if>
					</s:iterator>
					<s:if test="orderDetail.emergencyContact!=null">
						<tr bgcolor="#ffffff" align="center">
							<td height="25">紧急联系人
							</td>
							<td>${orderDetail.emergencyContact.name }</td>
							<td>${orderDetail.emergencyContact.mobile }</td>
							<td>${orderDetail.emergencyContact.email }</td>
							<td>${orderDetail.emergencyContact.zhCertType }</td>
							<td>${orderDetail.emergencyContact.certNo }</td>
							<td>${orderDetail.emergencyContact.postcode }</td>
							<td>${orderDetail.emergencyContact.address }</td>
							<td>${orderDetail.emergencyContact.tel }</td>
							<td>${orderDetail.emergencyContact.fax }</td>
							<td>${orderDetail.emergencyContact.faxTo }</td>
							<td>${orderDetail.emergencyContact.zhBrithday }</td>
							<td>${orderDetail.emergencyContact.zhGender }</td>
						</tr>
					</s:if>
					<tr bgcolor="#ffffff">
							<td colspan="14">
								<input name="ordPersonId" type="hidden" id="ordPersonId" value="" />
								<input name="personOrderId" type="hidden" id="personOrderId"
									value="${orderDetail.orderId }" />

								 <input type="button" value="修改/新增游客" class="right-button09"
									name="update" onclick="updateOrAddPerson('update');"> 
							</td>
							
						</tr>

				</tbody>
			</table>
			
		<div id="editReceiverDialg"
			style="z-index: 300;margin-top: 10px;margin-left: -100px;display: none;">
		</div>
			<s:if
				test='"NEED_ECONTRACT" == orderDetail.needContract &&  "CANCEL"!=orderDetail.orderStatus'>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" width="100%" class="">
					<tbody>
						<tr>
							<td><strong></strong></td>
						</tr>
						<tr>
							<td><input type="button" value="更新发送合同"
								title="若修改了订单信息，请点击此按钮更新合同后发送" class="right-button08"
								name="update" onclick="updateContract(${orderDetail.orderId });"><strong></strong>
							</td>
						</tr>
					</tbody>
				</table>
			</s:if>
			<br />
			<!--=============发票信息=============-->
			<s:include value="/WEB-INF/pages/back/ord/ord_invoice_module.jsp" />
			<br />
			<!--popbox end-->
			<!-- 价格修改 -->
			<div href="<%=basePath%>ord/loadModifyAmountApply.do" id="priceDiv"
				param="{'orderId':'${orderDetail.orderId }'}"></div>


			<div class="ordersum" style="margin-left: 10px;" id="orderCoupon"
				href="<%=basePath%>orderCoupon/allMarkCoupon.do"
				param="{'orderId':'${orderDetail.orderId }'}"></div>

			<div class="ordersum" style="margin-left: 10px;" id="choseCoupon"
				href="<%=basePath%>shoppingCard/loadChoseCoupon.do"></div>

			<p class="ordersuml2" id="youhuiContent"
				href="<%=basePath%>ajax/loadUsedYouhui.do"
				param="{orderId:'${orderDetail.orderId }'}"></p>
			<!--=============已使用的产品优惠活动 Begin=============-->
			<div class="ordersuml2">
				<em>已使用的产品优惠活动 ：</em>
				<table>
					<s:if test='!"".equals(earlyDesc)&&null!=earlyDesc'>
						<tr>
							<td valign="top">早订早惠：</td>
							<td><span>${earlyDesc}</span></td>
						</tr>
					</s:if>
					<s:if test='!"".equals(moreDesc)&&null!=moreDesc'>
						<tr>
							<td valign="top">多订多惠：</td>
							<td><span>${moreDesc}</span></td>
						</tr>
					</s:if>
				</table>

			</div>
			<!--=============已使用的产品优惠活动 End=============-->


			<!--=============订单备注=============-->
			<div href="<%=basePath%>ord/loadMemos.do" id="memoDiv"
				param="{'orderId':'${orderDetail.orderId }'}"></div>
			<!-- 订单备注 -->


			<!--popbox end-->
			<p class="submitbtn2">
				<s:if test="orderDetail.orderStatus!='CANCEL'">
					<select name="cancelReason" id="cancelReason">
						<s:iterator id="reason" value="cancelReasons">
							<option value="${reason.code }">${reason.name }</option>
						</s:iterator>
					</select>
				</s:if>
				<s:if test="orderDetail.orderStatus=='CANCEL'">
						订单状态：取消
					</s:if>
				<s:else>
					<s:if test="orderDetail.isCancelAble()">
						<s:if
							test="orderDetail.paymentTarget == 'TOSUPPLIER' && orderDetail.performStatus=='UNPERFORMED' && !orderDetail.hasSettlement">
							<input type="button" name="btnCancelOrder"
								onclick="chkCancelOrder();" value="废 单" class="right-button08">
						</s:if>

						<s:elseif
							test="orderDetail.paymentTarget == 'TOLVMAMA' && orderDetail.approveStatus=='VERIFIED' && orderDetail.paymentStatus == 'UNPAY'">
							<input type="button" name="btnCancelOrder1"
								onclick="chkCancelOrder();" value="废 单" class="right-button08">
						</s:elseif>

						<s:elseif test="orderDetail.oughtPayYuan==0">
							<input type="button" name="btnCancelOrder1"
								onclick="chkCancelOrder();" value="废 单" class="right-button08">
						</s:elseif>

						<s:elseif
							test="orderDetail.paymentTarget == 'TOLVMAMA' && orderDetail.approveStatus=='VERIFIED' && (orderDetail.paymentStatus == 'PAYED' or orderDetail.paymentStatus == 'PARTPAY')">
							<input type="button" name="btnCancelOrder2"
								onclick="chkOneCityOneCard();" value="废 单"
								class="right-button08" />
						</s:elseif>

						<s:elseif test="orderDetail.approveStatus == 'INFOPASS'">
							<input type="button" name="btnCancelOrder1"
								onclick="chkCancelOrder();" value="废 单" class="right-button08">
						</s:elseif>

					</s:if>
					<s:else>
						<span style="color: red">该订单过了最晚废单时间</span>
					</s:else>
				</s:else>
				<script>
						function chkCancelOrder(){
							if(settlementStatus.value != 'UNSETTLEMENTED'){
								if(confirm("该订单的结算状态为“结算中/已结算”,是否需要废单？")){  
									doCancelOrder('historyDiv');
								}
							}else{
								doCancelOrder('historyDiv');
							};
						}
						
						function chkOneCityOneCard(){
							if(settlementStatus.value != 'UNSETTLEMENTED'){
								if(confirm("该订单的结算状态为“结算中/已结算”,是否需要废单？")){  
									oneCityOneCard('${oneCityOneCardFlag}', 'historyDiv');
								}
							}else{
								oneCityOneCard('${oneCityOneCardFlag}', 'historyDiv');
							};
						}
						
						function cancelAndCreateNewOrder(){
							
						}
					</script>
				<s:if test="orderDetail.redail=='true'">
					<input name="redail" type="button" id="redail"
						onclick="doCancelNeedReplay();" value="取消需重拨"
						class="right-button08" />
				</s:if>
			</p>

			<!-- 不通过原因 -->
			<s:if test="orderDetail.resourceConfirmStatus=='LACK'">

				<strong>资源审核不通过</strong>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tr bgcolor="#f4f4f4" align="center">
						<td height="30">原因</td>
						<td>内容</td>
						<td>操作人</td>
					</tr>
					<tr bgcolor="#ffffff" align="center">
						<td height="25"><s:if
								test="orderDetail.resourceLackReason=='NO_RESOURCE'">
										没有资源
								</s:if> <s:elseif test="orderDetail.resourceLackReason=='PRICE_CHANGE'">
										价格更改
									</s:elseif> <s:elseif
								test="orderDetail.resourceLackReason=='UNABLE_MEET_REQUIREMENTS'">
										无法满足游客要求
									</s:elseif> <s:else>
										其他
									</s:else></td>
						<td><s:if
								test="orderDetail.resourceLackReason=='NO_RESOURCE'">
										没有资源
								</s:if> <s:elseif test="orderDetail.resourceLackReason=='PRICE_CHANGE'">
										价格更改
									</s:elseif> <s:elseif
								test="orderDetail.resourceLackReason=='UNABLE_MEET_REQUIREMENTS'">
										无法满足游客要求
									</s:elseif> <s:else>
										${orderDetail.resourceLackReason }
									</s:else></td>
						<td>${orderDetail.takenOperator }</td>
					</tr>
				</table>
			</s:if>
			<p class="submitbtn2">
				<!-- 废单重下按钮 -->
				<s:if
					test="orderDetail.isCancelAble() && orderDetail.orderStatus=='NORMAL' && orderDetail.settlementStatus == 'UNSETTLEMENTED' && !hasValidRefundment && orderDetail.performStatus =='UNPERFORMED' && !orderDetail.hasDistribution()">
					<form method="get"
						action="/super_back/ord/cancelAndCreateNewOrder.do">
						<input type="hidden" name="orderId" value="${orderId}" /> <input
							type="submit" value="废 单 * 重 下" class="right-button08"
							onclick="return confirm('你确定要废单重下吗？')" />
					</form>
				</s:if>
				<s:if test="orderDetail.orderStatus=='CANCEL'">
					<form action="<%=basePath%>/phoneOrder/index.do" method="post">
						<input type="hidden" name="orderId" value="${orderDetail.orderId}" />
						<input type="hidden" name="cancelType"
							value="toCreateOrderNew_new" /> <input type="submit"
							value="订 单 * 重 下" class="right-button08"
							onclick="return confirm('你确定要订单重下吗？')" />
					</form>
				</s:if>
			</p>
			<!-- 不通过原因end -->
			<!-- 操作日志 -->
			<strong>操作日志</strong>
			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
				border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
				<tr bgcolor="#f4f4f4" align="center">
					<td height="30">日志名称</td>
					<td style="width: 340px" nowrap="nowrap">内容</td>
					<td>操作人</td>
					<td>创建时间</td>
					<td>备注</td>
				</tr>
				<s:iterator value="comLogs" id="log">
					<tr bgcolor="#ffffff" align="center">
						<td height="25">${log.logName }</td>
						<td>${log.content } <s:if
								test="#log.logType=='cancelToCreateNew_new'">
							老订单ID${log.parentId}
							</s:if> <s:if test="#log.logType=='cancelToCreateNew_original'">
							新订单ID${log.parentId}
							</s:if>
						</td>
						<td>${log.operatorName }</td>
						<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>${log.memo }</td>
					</tr>
				</s:iterator>
			</table>
		</div>
	</div>
	<!--=========================我的历史审核弹出层 end==============================-->
	<input name="usrReceiver" type="hidden" id="usrReceiver"
		value="${orderDetail.contact.receiverId }">

	<script type="text/javascript">
$(function() {
	$(document).ready(function() {
		$("#delayWaitPaymentBtn").click(function() {
			var orderId = $("#orderId").val();
			var delayWaitPayment = $("#delayWaitPaymentSelect").val();
			$.ajax({
				type : 'POST',
				url : "<%=basePath%>/ord/delayWaitPayment.do",
				data : {"orderId": orderId, "delayWaitPayment": delayWaitPayment},
				success : function(dt) {
					var data = eval("(" + dt + ")");
					if (data.success) {
						alert("延迟成功！");
						$("#waitPaymentSpan").text(data.newWaitPaymentStr);
					} else {
						alert(data.msg);
					}
				}
			});
		});
	});
});
</script>
	</body>
</html>

