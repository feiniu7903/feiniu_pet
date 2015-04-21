<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() 
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script language="javascript" src="<%=basePath%>js/ord/ord.js" type="text/javascript"></script>
		<script src="${basePath}js/phoneorder/important_tips.js"
			type="text/javascript">
</script>
		<script type="text/javascript">
			var orderId='${orderDetail.orderId }', operatFrom='monitor', contactId='${orderDetail.contact.personId }',userId='${orderDetail.userId }',isPhysical = '${orderDetail.physical}';
			$(document).ready(function(){
				$("#youhuiContent").loadUrlHtml();
				$("#orderCoupon").loadUrlHtml();
				$("#editReceiverDialg").lvmamaDialog({modal:false,width:600,height:350,close:function(){}});
			});
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
						支付等待时间：<s:if test="orderDetail.hasNeedPrePay()">
						<s:date name="orderDetail.aheadTime" format="yyyy-MM-dd HH:mm"/>
						</s:if><s:else>${orderDetail.zhWaitPayment}</s:else>
					</td>
				</tr>
				<tr>
					<td>
						订单产品金额：${orderAmountItem.amountYuan}
					</td>
					<td>
						优惠金额：${sumYouHuiAmount }
					</td>
					<td>
						应付金额：${orderDetail.oughtPayYuan }
					</td>
					<td>
						实付金额：<span style="color:#007500;font-weight:bold">${orderDetail.actualPayYuan } </span>
					</td>
				</tr>
				<tr>
					<td>
						退款金额：${sumRefundmentAmount}
					</td>
					<td>
						补偿金额：${sumCompensationAmount}
					</td>
					<td>
						金额调整：
					</td>
					<td>
						订单目前应有金额：
						<span style="color:#f00;font-weight:bold">
							<s:if test="orderDetail.paymentStatus=='UNPAY' && orderDetail.paymentTarget =='TOLVMAMA'">
								0
							</s:if>
							<s:elseif test="orderDetail.paymentTarget =='TOSUPPLIER'">  
        						0
						    </s:elseif>
							<s:else>
								${orderDetail.actualPayYuan - sumRefundmentAmount}
							</s:else>
						</span>
					</td>
				</tr>
				<tr>
					<td>
						优惠券优惠金额：${youHuiAmountList }
					</td>
					<td>
						优惠券名称：${youHuiNameList }
					</td>
					<td>
						优惠活动优惠金额：
					</td>
					<td>
						优惠活动名称：
					</td>
				</tr>				
				<tr>
					<td>
						奖金支付金额：${orderDetail.bonusPaidAmountYuan }
					</td>
					<td>
						支付状态：${orderDetail.zhPaymentStatus }
					</td>
					<td>
						订单状态：${orderDetail.zhOrderStatus}
					</td>
					<td>
						结算状态：${settlementStatus }
					</td>
				</tr>
				<tr>
					<td>
						返现金额：${orderDetail.cashRefundYuan}
					</td>				
					<td>
						订单来源渠道：${orderDetail.zhProductChannel }
					</td>
					<td>
					<s:if test="orderDetail.travelGroupCode!=null">
						合同： 
						<s:if test="orderDetail.contractStatus=='UNCONFIRMED'">未签 </s:if>
						<s:if test="orderDetail.contractStatus=='CONFIRM'">已签</s:if>
						</s:if>
					</td>
					<td>
						<s:if test="orderDetail.travelGroupCode!=null">
						出票：
						<s:if test="orderDetail.trafficTicketStatus=='false'">未开票 </s:if>
						<s:if test="orderDetail.trafficTicketStatus=='true'">已开票</s:if>
						</s:if>
					</td>
				</tr>
				<tr>
					<s:if test="orderDetail.travelGroupCode!=null">
						<td>
						签证状态：
						${orderDetail.zhVisaStatus}
						</td>
					</s:if>
					<td colspan="3">
						用户备注：${orderDetail.userMemo }
					</td>
				</tr>
			</table>
			
			<div style="border:1px solid #ccc;background-color:#efefef;margin:5px;padding:5px;text-align: left">
				文件列表<a href="javascript:openWin('<%=request.getContextPath()%>/common/upload.do?objectId=${orderDetail.orderId}&objectType=ORD_ORDER',500,400)"">上传文件</a>				
				<s:if test="groupComAffixList!=null&&groupComAffixList.size()>0">
				<div>团文件列表</div>
				<table width="60%">
				<s:iterator value="groupComAffixList" id="affix">
					<tr>
						<td><span title="${affix.memo}">${affix.name}</span></td><td width="80"><a href="http://pic.lvmama.com/pics/${affix.path}" target="_blank">文件下载</a></td>
					</tr>
				</s:iterator>
				</table>
				</s:if>
				<s:if test="orderComAffixList!=null&&orderComAffixList.size()>0">
				<div>订单文件列表</div>
				<table width="60%">
				<s:iterator value="orderComAffixList" id="affix">
				     <s:if test="#affix.fileType=='GROUP_ADVICE_NOTE'">
				         <tr>
							<td><span title="${affix.memo}">${affix.name}</span></td><td width="80">
							<a target="_blank" href="/super_back/groupadvice/download.do?fileId=<s:property value="#affix.fileId"/>&fileName=<s:property value="#affix.name"/>">文件下载</a>
 						 </tr>
					</s:if>
					<s:else>
					     <tr>
							<td><span title="${affix.memo}">${affix.name}</span></td><td width="80"><a href="http://pic.lvmama.com/pics/${affix.path}" target="_blank">文件下载</a></td>
						 </tr>				     
					</s:else>
						
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
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#666666" width="100%" class="newfont05">
					<tbody>
						<tr class="CTitle">
							<td height="22" align="center" style="font-size: 16px;"
								colspan="10">
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
							<td width="8%">
								产品类型
							</td>
							<td width="6%">
								优惠
							</td>
							<td width="10%">
								游玩时间
							</td>
							<td width="10%">最后废单时间</td>
						</tr>
						<s:iterator id="prod" value="orderDetail.ordOrderItemProds">
							<s:if test="#prod.productType!='OTHER'">
								<tr bgcolor="#ffffff">
									<td height="30">
										${prod.productId }
									</td>
									<td>
										<a class="showImportantTips" href="javascript:void(0)"
						productId="${prod.productId}"  prodBranchId="${prod.prodBranchId}">${prod.productName }</a>
									</td>
									<td>
										${prod.priceYuan }
									</td>
									<td>
									<s:if test="#prod.productType=='HOTEL'">
										${prod.hotelQuantity }
									</s:if>
									<s:else>
										${prod.quantity }
									</s:else>
									
									</td>
									<td>
										${prod.price*prod.quantity/100 }
									</td>
									<td>
										${prod.zhProductType }
									</td>
									<td>
										${prod.zhAdditional }
									</td>
									<td>
											<s:if test="subProductType=='SINGLE_ROOM'">
					${dateRange}
					</s:if>
					<s:else>
					${zhVisitTime}
					</s:else>
									</td>
									<td>
										<s:if test="#prod.lastCancelTime!=null">
										<s:date name="#prod.lastCancelTime" format="yyyy-MM-dd HH:mm"/>
										</s:if>
									</td>
								</tr>
							</s:if>
						</s:iterator>
					</tbody>
				</table>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#666666" width="100%" class="newfont06">
					<tbody>
						<tr class="CTitle">
							<td height="20" align="center" style="font-size: 14px;"
								colspan="9">
								附加产品
							</td>
						</tr>
						<tr bgcolor="#eeeeee">
							<td height="35" width="5%">
								序号
							</td>
							<td width="18%">
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
							<td width="10%">
								信息
							</td>							
						</tr>
						<s:iterator id="prod" value="orderDetail.ordOrderItemProds">
							<s:if test="#prod.productType=='OTHER'">
								<tr bgcolor="#ffffff">
									<td height="30">
										${prod.productId }
									</td>
									<td>
										<a class="showImportantTips" href="javascript:void(0)"
						productId="${prod.productId}"  prodBranchId="${prod.prodBranchId}">${prod.productName }</a>
									</td>
									<td>
										${prod.priceYuan }
									</td>
									<td>
										${prod.price*prod.quantity/100}
									</td>
									<td>
										${prod.zhProductType }
									</td>
									<td>
										<s:if test="#prod.productType=='HOTEL'">
										${prod.hotelQuantity }
									</s:if>
									<s:else>
										${prod.quantity }
									</s:else>
									</td>
									<td>
											<s:if test="subProductType=='SINGLE_ROOM'">
					${dateRange}
					</s:if>
					<s:else>
					${zhVisitTime}
					</s:else>
									</td>
									<td>
										<s:if test="#prod.subProductType=='INSURE'">
											<a href="javascript:openWin('<%=basePath%>insurance/viewPolicyStatus.zul?orderId=${orderDetail.orderId}&prodId=${prod.productId}',900,300)">查看</a>
										</s:if>
										<s:else>
											${prod.subProductType }
										</s:else> 
									</td>
								</tr>
							</s:if>
						</s:iterator>
					</tbody>
				</table>
			</div>
			<!--popbox end-->

	<div id="editReceiverDialg"
			style="position: absolute;left:500px; z-index: 10000; display:none;">
		</div>

			<!--=============用户信息=============-->
			<div class="popbox">
				<strong>用户信息</strong>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tbody>
						<tr bgcolor="#f4f4f4" align="center">
							<td width="16%" height="25">
								卡号
							</td>
							<td width="16%">
								用户名
							</td>
							<td width="16%">
								用户姓名
							</td>
							<td width="16%">
								手机号
							</td>
							<td width="16%">
								邮箱地址
							</td>
						</tr>
						<tr bgcolor="#ffffff" align="center">
							<td height="25">
								${orderDetail.user.cardId }
							</td>
							<td>
								${orderDetail.user.userName }
							</td>
							<td>
								${orderDetail.user.realName }
							</td>
							<td>
								${orderDetail.user.mobileNumber }
							</td>
							<td>
								${orderDetail.user.email }
							</td>
						</tr>
					</tbody>
				</table>

				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont04">
					<tbody>
						<tr bgcolor="#f4f4f4" align="center">
							<td height="30" width="8%">
								类别
							</td>
							<td width="8%">
								姓名
							</td>
							<td width="6%">
								联系电话
							</td>
							<td width="9%">
								Email
							</td>
							<td width="10%">
								证件类型
							</td>
							<td width="10%">
								证件号码
							</td>
							<td width="5%">
								邮编
							</td>
							<td width="17%">
								地址
							</td>
							<td width="5%">
								座机号
							</td>
							<td width="5%">
								传真
							</td>
							<td width="11%">
								传真接收人
							</td>
						</tr>
						<s:if test="orderDetail.contact!=null">
							<tr bgcolor="#ffffff" align="center" href="<%=basePath%>usrReceivers/loadList.do">
								<td height="25">
									取票人/联系人
								</td>
								<td>
									${orderDetail.contact.name }
								</td>
								<td>
									${orderDetail.contact.mobile }
								</td>
								<td>
									${orderDetail.contact.email }
								</td>
								<td>
									${orderDetail.contact.certType }
								</td>
								<td>
									${orderDetail.contact.certNo }
								</td>
								<td>
									${orderDetail.contact.postcode }
								</td>
								<td>
									${orderDetail.contact.address }
								</td>
								<td>
									${orderDetail.contact.tel }
								</td>
								<td>
									${orderDetail.contact.fax }
								</td>
								<td>
									${orderDetail.contact.faxTo }
								</td>
							</tr>
							<script>
								contactReceiverId = '${orderDetail.contact.receiverId }';
							</script>
						</s:if>
						<s:iterator id="person" value="orderDetail.personList">
							<s:if test="#person.personType=='ADDRESS'">
								<tr bgcolor="#ffffff" align="center">
									<td height="25">
										配送地址
									</td>
									<td>
										${person.name }
									</td>
									<td>
										${person.mobile }
									</td>
									<td>
										${person.email }
									</td>
									<td>
										${person.certType }
									</td>
									<td>
										${person.certNo }
									</td>
									<td>
										${person.postcode }
									</td>
									<td>
										${person.address }
									</td>
									<td>
										${person.tel }
									</td>
									<td>
										${person.fax }
									</td>
									<td>
										${person.faxTo }
									</td>
								</tr>
							</s:if>
						</s:iterator>
						<s:iterator id="person" value="orderDetail.personList">
							<s:if test="#person.personType=='TRAVELLER'">
								<tr bgcolor="#ffffff" align="center">
									<td height="25">
										游客
									</td>
									<td>
										${person.name }
									</td>
									<td>
										${person.mobile }
									</td>
									<td>
										${person.email }
									</td>
									<td>
										${person.certType }
									</td>
									<td>
										${person.certNo }
									</td>
									<td>
										${person.postcode }
									</td>
									<td>
										${person.address }
									</td>
									<td>
										${person.tel }
									</td>
									<td>
										${person.fax }
									</td>
									<td>
										${person.faxTo }
									</td>
								</tr>
							</s:if>
						</s:iterator>
						<s:if test="orderDetail.emergencyContact!=null">
							<tr bgcolor="#ffffff" align="center">
								<td height="25">
									紧急联系人
									<input type="radio" name="updateOrdPerson"
										onclick='$("#ordPersonId").val(${orderDetail.emergencyContact.personId })'>
								</td>
								<td>
									${orderDetail.emergencyContact.name }
								</td>
								<td>
									${orderDetail.emergencyContact.mobile }
								</td>
								<td>
									${orderDetail.emergencyContact.email }
								</td>
								<td>
									${orderDetail.emergencyContact.zhCertType }
								</td>
								<td>
									${orderDetail.emergencyContact.certNo }
								</td>
								<td>
									${orderDetail.emergencyContact.postcode }
								</td>
								<td>
									${orderDetail.emergencyContact.address }
								</td>
								<td>
									${orderDetail.emergencyContact.tel }
								</td>
								<td>
									${orderDetail.emergencyContact.fax }
								</td>
								<td>
									${orderDetail.emergencyContact.faxTo }
								</td>
							</tr>
						</s:if>						
						<tr>
							<td>
							<input name="ordPersonId" type="hidden" id="ordPersonId" value=""/>
							<input name="personOrderId" type="hidden" id="personOrderId" value="${orderDetail.orderId }"/>
							
 						</td>
						<td>
 							</td>
						</tr>
					</tbody>
				</table><br/>
				<!--=============发票信息=============-->
				<s:include value="/WEB-INF/pages/back/ord/ord_invoice_module.jsp"/>
				<br />
				<!--popbox end-->
				<!-- 价格修改 -->
					<div>
						<table style="font-size: 12px" cellspacing="1" cellpadding="4"
			id="changPrice" border="0" bgcolor="#B8C9D6" width="100%"
			class="newfont03">
			<tbody>
				<tr bgcolor="#f4f4f4" align="center">
				    <td height="30" width="10%">
						修改价格原因
					</td>
					<td height="30" width="10%">
						类型
					</td>
					<td width="8%">
						修改金额
					</td>
					<td width="10%">
						申请者
					</td>
					<td width="10%">
						审核者
					</td>
					<td width="10%">
						审核状态
					</td>
					<td width="21%">
						申请备注
					</td>
					<td width="21%">
						审核备注
					</td>
					
				</tr>
				<s:iterator value="ordOrderAmountApplyList" id="orderAmount">
					<tr bgcolor="#ffffff" align="center">
					    <td height="25">
							${orderAmount.applyType}
						</td>
						<td>
							<s:if test="#orderAmount.amount>0">增加费用</s:if>
							<s:else>减少费用</s:else>
						</td>
						<td>
							${orderAmount.amountYuan}
						</td>
						<td>
							${orderAmount.applyUser}
						</td>
						<td>
							${orderAmount.approveUser}
						</td>
						<td>
						    ${orderAmount.orderAmountApplyStatusStr}
						</td>
						<td>
							${orderAmount.applyMemo}
						</td>
						<td>
							${orderAmount.approveMemo}
						</td>
						
					</tr>
				</s:iterator>
			</tbody>
		</table>
					</div>
				
				<!--=============订单备注=============-->
				<div>
						<strong>订单备注</strong>
		<table style="font-size: 12px" cellspacing="1" cellpadding="4"
			border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
			<tbody>
				<tr bgcolor="#f4f4f4" align="center">
					<td height="30" width="20%">
						备注类别
					</td>
					<td width="50%">
						内容
					</td>
					<td width="10%">
						维护人
					</td>
					<td width="10%">
						创建时间
					</td>
					<!-- td width="10%">
						操作
					</td-->
				</tr>
				<s:iterator id="memo" value="orderMemos">
					<tr bgcolor="#ffffff" align="center">
						<td height="25">
							${memo.zhType }
						</td>
						<td>
							${memo.content }
						</td>
						<td>
							${memo.operatorName }
						</td>
						<td>
							${memo.zhCreateTime }
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
				</div>
				<!-- 订单备注 -->${orderDetail.orderId}
				<p class="ordersuml2" id="youhuiContent"
					href="<%=basePath%>ajax/loadUsedYouhui.do"
					param="{orderId:'${orderDetail.orderId }'}">
				</p>
				
				
				<div class="ordersum" style="margin-left: 10px;" id="orderCoupon" href="<%=basePath%>orderCoupon/allMarkCoupon.do" 
				   param="{orderId:'${orderDetail.orderId}'}">
				</div>
				
				
				 <div class="ordersum" style="margin-left: 10px;" id="choseCoupon" href="<%=basePath%>shoppingCard/loadChoseCoupon.do">
					</div>
				
				<!--popbox end-->
				<p class="submitbtn2">
					<input type="button" name="btnCloseOrdHis" class="button" value="关闭" onclick="javascript:closeDetailDiv('historyDiv');" />
				</p>
				<!-- 操作日志 -->
				<strong>操作日志</strong>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tr bgcolor="#f4f4f4" align="center">
						<td height="30">
							日志名称
						</td>
						<td style="width:340px" nowrap="nowrap">
							内容
						</td>
						<td>
							操作人
						</td>
						<td>
							创建时间
						</td>
						<td>
							备注
						</td>
					</tr>
					<s:iterator value="comLogs" id="log">
						<tr bgcolor="#ffffff" align="center">
							<td height="25">
								${log.logName }
							</td>
							<td>
								${log.content }
							<s:if test="#log.logType=='cancelToCreateNew_new'">
							老订单ID${log.parentId}
							</s:if>
							<s:if test="#log.logType=='cancelToCreateNew_original'">
							新订单ID${log.parentId}
							</s:if>
							</td>
							<td>
								${log.operatorName }
							</td>
							<td>
								<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								${log.memo }
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</div>
		<!--=========================我的历史审核弹出层 end==============================-->
		<input name="usrReceiver" type="hidden" id="usrReceiver" value="${orderDetail.contact.receiverId }" >

	</body>

</html>


