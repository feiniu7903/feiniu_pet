<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<script language="javascript" src="<%=basePath%>js/ord.js"
	type="text/javascript"></script>
<script language="javascript" src="http://super.lvmama.com/super_back/js/phoneorder/important_tips.js"
	type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript">
			var orderId = '${orderDetail.orderId }', operatFrom = 'monitor', contactId = '${orderDetail.contact.personId }', userId = '${orderDetail.userId }', isPhysical = '${orderDetail.physical}';
             			 
			$(document).ready(function() {
				//$("#memoDiv").loadUrlHtml();
				var canOperator = '${aperiodicCanOperate}';
				if(canOperator == "true") {
					//$("#priceDiv").loadUrlHtml();
				}
				
			});
		</script>
</head>
<link href="http://super.lvmama.com/super_back/themes/cc.css"
	rel="stylesheet" type="text/css" />
<link href="http://super.lvmama.com/super_back/themes/base/jquery.ui.all.css"
rel="stylesheet" />
<body>
<body>
	<!--=========================我的历史订单弹出层==============================-->
	<div class="orderpoptit">
		<strong>订单查看：</strong>
		<p class="inputbtn">
			<input type="button" name="btnCloseOrder" class="button" value="关闭"
				onclick="javascript:closeDetailDiv('historyDiv');"> <input
				type="hidden" name="isSHHolidayOrder" class="isSHHolidayOrder"
				value="${orderDetail.isShHolidayOrder }">
		</p>
	</div>
	<div class="orderpopmain">
		<table style="font-size: 12px" width="100%" border="0" id="orderTable"
			class="contactlist">
			<tr>
				<td width="25%">主站订单号：${tntOrder.orderId } <font color="red">${orderDetail.zhIsAperiodic
						}</font> <c:if test="${orderDetail.testOrderFlag == 'true'}">
						<font style="color: red;">(测试单)</font>
					</c:if>
				</td>
				<td width="20%">分销平台订单号：${tntOrder.tntOrderId } <font
					color="red">${orderDetail.zhIsAperiodic }</font> <c:if
						test="${orderDetail.testOrderFlag == 'true'}">
						<font style="color: red;">(测试单)</font>
					</c:if>
				</td>
				<td width="20%">分销商订单号：${tntOrder.partnerOrderId } <font
					color="red">${orderDetail.zhIsAperiodic }</font> <c:if
						test="${orderDetail.testOrderFlag == 'true'}">
						<font style="color: red;">(测试单)</font>
					</c:if>
				</td>
			</tr>
			<tr>
				<td width="20%">下单人：${orderDetail.userName }</td>
				<td width="30%">下单时间：${orderDetail.zhCreateTime }</td>
				<td width="30%">支付等待时间：<span id="waitPaymentSpan"> <c:if
							test="${isHasNeedPrePay}">
							<lv:dateOutput date="${orderDetail.aheadTime}"
								format="yyyy-MM-dd HH:mm" />
						</c:if> <c:if test="${!isHasNeedPrePay}">
						       ${orderDetail.zhWaitPayment}
						   </c:if>
				</span>
				</td>
			</tr>
			<tr>
				<td>主站订单金额：${orderDetail.orderPayFloat}</td>
				<td>分销订单金额：${tntOrder.orderAmount/100}</td>
				<td colspan="2">应付金额：${tntOrder.orderAmount/100}
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				实付金额：<span style="color: #007500; font-weight: bold">
				        <c:choose>
							<c:when
								test="${orderDetail.paymentStatus=='UNPAY' && orderDetail.paymentTarget =='TOLVMAMA'}">
							       0
							    </c:when>
							<c:when test="${orderDetail.paymentTarget=='TOSUPPLIER'}">
							       0
							    </c:when>
							<c:otherwise>
								<fmt:formatNumber
									value="${tntOrder.orderAmount/100 - sumRefundmentAmount}"
									pattern="#.##" />
							</c:otherwise>
						</c:choose>
				</span></td>
				</td>
			</tr>
			<tr>
				<td>退款金额：<c:if test="${sumRefundmentAmount==null}">0.0</c:if>
				             <c:if test="${sumRefundmentAmount!=null}">${sumRefundmentAmount}</c:if>  
				</td>
				<td>补偿金额：<c:if test="${sumCompensationAmount==null}">0.0</c:if>
				             <c:if test="${sumCompensationAmount!=null}">${sumCompensationAmount}</c:if>  
				</td>
				<td colspan="2">订单目前应有金额： <span style="color: #f00; font-weight: bold">
						<c:choose>
							<c:when
								test="${orderDetail.paymentStatus=='UNPAY' && orderDetail.paymentTarget =='TOLVMAMA'}">
							       0
							    </c:when>
							<c:when test="${orderDetail.paymentTarget=='TOSUPPLIER'}">
							       0
							    </c:when>
							<c:otherwise>
								<fmt:formatNumber
									value="${tntOrder.orderAmount/100 - sumRefundmentAmount}"
									pattern="#.##" />
							</c:otherwise>
						</c:choose>
				</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				返现金额：0<%-- ${orderDetail.cashRefundYuan} --%>
				</td>
				
			</tr>
			<tr>
				<td>支付状态：${orderDetail.zhPaymentStatus }</td>
				<td>订单状态：${orderDetail.zhOrderStatus}</td>
				<td colspan="2">供应商结算状态：${settlementStatus }<input id="settlementStatus"
					type="hidden" value="${orderDetail.settlementStatus}" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					分销商结算状态：${tntOrder.cnSettleStatus }<input
					id="settlementStatus" type="hidden"
					value="${tntOrder.settleStatus}" />
				</td>
			</tr>
			<tr>
				<td>订单渠道类型：${orderChannelType }</td>
				<td>分销商: ${tntOrder.distributorName}</td>
			</tr>
			<tr>
				<td>产品经理：${managerUser.realName }</td>
				<td>联系电话：${managerUser.mobile }</td>
				<td>分机：${managerUser.extensionNumber }</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<!-- 延长支付等待时间控件 -->
					<td colspan="4">						
						<c:if test="showDelayWaitPaymentFlag">							
							延长支付等待时间：
							<input type="hidden" id="orderId" value="${orderDetail.orderId}" />
							<select id="delayWaitPaymentSelect">
								<c:forEach var="waitPayment" items="waitPaymentMap">
								    <option value="${waitPayment.value}">${waitPayment.key}</option>
								</c:forEach>
							</select>	
							<input type="button" id="delayWaitPaymentBtn" value="保存" />							
						</c:if>	
					</td>
			</tr>
		</table>

		<!--=============商品清单=============-->
		<div class="popbox">
			<strong>商品清单</strong>
			<p class="paytime">
				游玩时间：
				<c:choose>
					<c:when test="${isAperiodic}">
						<c:choose>
							<c:when test="orderDetail.visitTime == null">
				                                    未确认
				          </c:when>
							<c:otherwise>
				              ${orderDetail.zhVisitTime }
				          </c:otherwise>
						</c:choose>
						<font color="red"> <c:if test="${isTicket}">
							  （此产品为门票不定期产品，游玩时间由用户在景区入园时输入短信密码后才会有）
						   </c:if> <c:if test="${isHotel}">
							 （此产品为酒店不定期产品，入住日期由用户提前${orderDetail.mainProduct.product.aheadBookingDays }天致电供应商预约确认）
						   </c:if> <c:if test="${isRoute}">
							（此产品为线路不定期产品，游玩时间由用户提前${orderDetail.mainProduct.product.aheadBookingDays }天致电供应商预约确认）
						   </c:if>
						</font>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="orderDetail.orderType=='HOTEL'">
								<c:forEach items="${orderDetail.ordOrderItemProds}"
									var="ordOrderItemProd">
									<c:choose>
										<c:when
											test="${ordOrderItemProd.subProductType=='SINGLE_ROOM'}">
				                          ${ordOrderItemProd.dateRange}
				                      </c:when>
										<c:otherwise>
				                          ${ordOrderItemProd.zhVisitTime}
				                      </c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
							<c:otherwise>
				              ${orderDetail.zhVisitTime }
				           </c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</p>
			<c:if test="${isAble && isHasLastCancelTime}">
				<p class="lastCanceltime">
					最晚修改或取消时间：
					<lv:dateOutput date="${orderDetail.lastCancelTime}"
						format="yyyy-MM-dd HH:mm" />
				</p>
			</c:if>
			<c:if test="${orderDetail.hasSupplierChannelOrder}">
				<div style="color: red">该订单废单后最终结果需等供应商系统反馈</div>
			</c:if>
			<c:if test="${isHasNeedPrePay}">
				<div>此订单已过最晚取消时间，只能通过预授权方式支付，一旦资源审核通过后此订单不退不改</div>
			</c:if>
			<br />
			<c:if test="${viewPage != null}">
				<c:if test="${viewPage.contents.REFUNDSEXPLANATION.content!=null}">
					<p class="paytime">
						退款说明:
						<c:out value="${viewPage.contents.REFUNDSEXPLANATION.content}" />
					</p>
				</c:if>
			</c:if>
			<c:if test="${orderDetail.orderType=='TRAIN'}">
				退款说明： <br />
				退票后，20个工作日，给用户退款。 <br />
				退票时间超过2个工作日时，用户反馈未收到款项的，向提交产品经理。产品经理向供应商核实退票情况，可以手动给用户退款。 <br />
				退票时，手续费按阶梯退票政策：发车前大于48小时退票，收取车票金额的5%用为手续费；24小时至48小时退票，收取车票金额的10%作为手续费；小于24小时，收取车票金额的20%作为手续费，其余退还给用户。退款金额以铁路局实际退款金额为准。
			</c:if>
			<br />
			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
				border="1" bgcolor="#666666" width="100%" class="newfont05">
				<tbody>
					<tr class="CTitle">
						<td height="22" align="center" style="font-size: 16px;"
							colspan="10">商品清单</td>
					</tr>
				</tbody>
			</table>
			<!-- 超级自由行，主产品 -->
			<c:if test="${isHasSelfPack}">
					主产品
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="1" bgcolor="#666666" width="100%" class="newfont05">
					<tr bgcolor="#eeeeee">
						<td height="35" width="4%">序号</td>
						<td width="23%">产品名称</td>
						<td width="13%">数量</td>
						<td width="8%">产品类型</td>
						<td width="6%">是否附加</td>
						<td width="10%">游玩时间</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td height="30">${orderDetail.mainProduct.productId }</td>
						<td><a class="showImportantTips" href="javascript:void(0)"
							productId="${orderDetail.mainProduct.productId}"
							prodBranchId="${orderDetail.mainProduct.prodBranchId}">${orderDetail.mainProduct.productName}</a>
						</td>
						<td>${orderDetail.mainProduct.quantity }</td>
						<td>${orderDetail.mainProduct.zhProductType }</td>
						<td>${orderDetail.mainProduct.zhAdditional }</td>
						<td>${zhVisitTime}</td>
					</tr>
				</table>
			</c:if>
			<!-- 门票块 -->
			<c:if test="${orderDetail.orderType == 'FREENESS'}">
				门票信息
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="1" bgcolor="#666666" width="100%" class="newfont05">
					<tr bgcolor="#eeeeee">
						<td height="35" width="4%">序号</td>
						<td width="15%">产品名称</td>
						<td width="8%">会员价</td>
						<td width="5%">数量</td>
						<td width="8%">总金额</td>
						<td width="8%">产品类型</td>
						<td width="6%">是否附加</td>
						<td width="10%">游玩时间</td>
					</tr>
					<c:forEach var="prod" items="${orderDetail.ordOrderItemProds}">
						<c:if test="${prod.productType=='TICKET'}">
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
						</c:if>
					</c:forEach>
				</table>
				<!-- 酒店块 -->
					酒店信息
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="1" bgcolor="#666666" width="100%" class="newfont05">
					<tr bgcolor="#eeeeee">
						<td height="35" width="4%">序号</td>
						<td width="15%">产品名称</td>
						<td width="8%">会员价</td>
						<td width="5%">数量</td>
						<td width="8%">总金额</td>
						<td width="8%">产品类型</td>
						<td width="6%">是否附加</td>
						<td width="10%">游玩时间</td>
					</tr>
					<c:forEach var="prod" items="${orderDetail.ordOrderItemProds}">
						<c:if test="${prod.productType=='HOTEL'}">
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
								<td><c:if test="${subProductType=='SINGLE_ROOM'}">
										    ${prod.dateRange}
										</c:if> <c:if test="${subProductType!='SINGLE_ROOM'}">
										    ${prod.zhVisitTime}
										</c:if></td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
				<!-- 线路块 -->
					线路信息
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="1" bgcolor="#666666" width="100%" class="newfont05">
					<tr bgcolor="#eeeeee">
						<td height="35" width="4%">序号</td>
						<td width="15%">产品名称</td>
						<td width="8%">会员价</td>
						<td width="5%">数量</td>
						<td width="8%">总金额</td>
						<td width="8%">产品类型</td>
						<td width="6%">是否附加</td>
						<td width="10%">游玩时间</td>
					</tr>
					<c:forEach var="prod" items="${orderDetail.ordOrderItemProds}">
						<c:if test="${prod.productType=='ROUTE'}">
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
								<td>${prod.zhVisitTime}</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
					
					交通信息
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="1" bgcolor="#666666" width="100%" class="newfont05">
					<tr bgcolor="#eeeeee">
						<td height="35" width="4%">序号</td>
						<td width="15%">产品名称</td>
						<td width="8%">会员价</td>
						<td width="5%">数量</td>
						<td width="8%">总金额</td>
						<td width="8%">产品类型</td>
						<td width="6%">是否附加</td>
						<td width="10%">游玩时间</td>
					</tr>
					<c:forEach var="prod" items="${orderDetail.ordOrderItemProds}">
						<c:if test="${prod.productType=='TRAFFIC'}">
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
								<td>${prod.zhVisitTime}</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${orderDetail.orderType != 'FREENESS'}">
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="1" bgcolor="#666666" width="100%" class="newfont05">
					<tr bgcolor="#eeeeee">
						<td height="35" width="4%">序号</td>
						<td width="15%">产品名称</td>
						<td width="8%">会员价</td>
						<td width="5%">数量</td>
						<td width="8%">总金额</td>
						<td width="8%">产品类型</td>
						<td width="6%">是否附加</td>
						<td width="10%">游玩时间</td>
					</tr>
					<c:forEach var="prod" items="${orderDetail.ordOrderItemProds}">
						<c:if test="${prod.productType!='OTHER'}">
							<tr bgcolor="#ffffff">
								<td height="30">${prod.productId }</td>
								<td><a class="showImportantTips" href="javascript:void(0)"
									productId="${prod.productId}"
									prodBranchId="${prod.prodBranchId}">${prod.productName}</a></td>
								<td>${prod.priceYuan }</td>
								<td><c:if test="${prod.productType=='HOTEL'}">
											   ${prod.hotelQuantity }
										    </c:if> <c:if test="${prod.productType!='HOTEL'}">
											   ${prod.quantity }
	                                        </c:if></td>
								<td>${prod.price*prod.quantity/100 }</td>
								<td>${prod.zhProductType }</td>
								<td>${prod.zhAdditional }</td>
								<td><c:if test="${subProductType=='SINGLE_ROOM'}">
												${prod.dateRange}
											</c:if> <c:if test="${subProductType!='SINGLE_ROOM'}">
												${prod.zhVisitTime}
											</c:if></td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</c:if>
			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
				border="1" bgcolor="#666666" width="100%" class="newfont06">
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
					<c:forEach var="prod" items="${orderDetail.ordOrderItemProds}">
						<c:if test="${prod.productType=='OTHER'}">
							<tr bgcolor="#ffffff">
								<td height="30">${prod.productId }</td>
								<td><a class="showImportantTips" href="javascript:void(0)"
									productId="${prod.productId}"
									prodBranchId="${prod.prodBranchId}">${prod.productName}</a></td>
								<td>${prod.priceYuan }</td>
								<td>${prod.price*prod.quantity/100}</td>
								<td>${prod.zhProductType }</td>
								<td><c:if test="${prod.productType=='HOTEL'}">
										    ${prod.hotelQuantity }
									    </c:if> <c:if test="${prod.productType!='HOTEL'}">
										    ${prod.quantity }
										</c:if></td>
								<td><c:if test="${subProductType=='SINGLE_ROOM'}">
										${prod.dateRange}
									</c:if> <c:if test="${subProductType!='SINGLE_ROOM'}">
										${prod.zhVisitTime}
								    </c:if></td>
								<td><c:if test="${prod.subProductType=='INSURE'}">
										<a
											href="javascript:openWin('/super_back/insurance/viewPolicyStatus.zul?orderId=${orderDetail.orderId}&prodId=${prod.productId}',900,300)">查看</a>
									</c:if> <c:if test="${prod.subProductType!='INSURE'}">
										${prod.subProductType }
									</c:if></td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div>
			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
				border="1" bgcolor="#666666" width="100%" class="newfont06">
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
						<c:if test="orderDetail.isAperiodic">
							<td>密码券状态</td>
						</c:if>
					</tr>
					<c:forEach items="${orderDetail.allOrdOrderItemMetas}" var="meta">
						<tr bgcolor="#ffffff">
							<td>${meta.orderItemMetaId}</td>
							<td>${meta.metaProductId}</td>
							<td>${meta.metaBranchId}</td>
							<td>${meta.productName}</td>
							<td>${meta.zhProductType}</td>
							<td><c:if test="${resourceConfirm=='true'}">是</c:if>
							    <c:if test="${resourceConfirm!='true'}">否</c:if>
							</td>
							<td>${meta.zhResourceStatus}</td>
							<td><c:if test="${taken=='true'}">是</c:if>
								<c:if test="${taken!='true'}">否</c:if>
							</td>
							<c:if test="${orderDetail.isAperiodic && aperiodicStatusMap != null}">
								 <td>${aperiodicStatusMap[orderItemMetaId]}</td> 
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!--=============用户信息=============-->
		<div class="popbox">
			<strong>用户信息</strong>
			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
				border="1" bgcolor="#B8C9D6" width="100%" class="newfont03">
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
				border="1" bgcolor="#B8C9D6" width="100%" class="newfont04">
				<tbody>
					<tr bgcolor="#f4f4f4" align="center">
						<td height="30" width="8%">类别</td>
						<td width="5%">姓名</td>
						<td width="5%">拼音</td>
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
					<c:if test="${orderDetail.contact!=null}">
						<tr bgcolor="#ffffff" align="center"
							href="http://super.lvmama.com/super_back/usrReceivers/loadList.do">
							<td height="25">取票人/联系人</td>
							<td>${orderDetail.contact.name }</td>
							<td>${orderDetail.contact.pinyin }</td>
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
					</c:if>
					<c:forEach var="person" items="${orderDetail.personList}">
						<c:if test="${person.personType=='ADDRESS'}">
							<tr bgcolor="#ffffff" align="center">
								<td height="25">配送地址</td>
								<td>${person.name }</td>
								<td>${person.pinyin }</td>
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
							<script>
								contactReceiverId = '${orderDetail.contact.receiverId }';
							</script>
						</c:if>
					</c:forEach>
					<c:forEach var="person" items="${orderDetail.personList}">
						<c:if test="${person.personType=='TRAVELLER'}">
							<tr bgcolor="#ffffff" align="center">
								<td height="25">游客</td>
								<td><a
									href="http://super.lvmama.com/pet_back/visa/approval/showVitMaterial.do?searchOrderId=${orderDetail.orderId}&searchPersonId=${person.personId }"
									target="_visa">${person.name }</a></td>
								<td>${person.pinyin }</td>
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
						</c:if>
					</c:forEach>
					<c:if test="${orderDetail.emergencyContact!=null}">
						<tr bgcolor="#ffffff" align="center">
							<td height="25">紧急联系人</td>
							<td>${orderDetail.emergencyContact.name }</td>
							<td>${orderDetail.emergencyContact.pinyin }</td>
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
					</c:if>
				</tbody>
			</table>
			<c:if test="${orderDetail.orderType=='TRAIN'}">
				<strong>火车票出票旅客信息</strong>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="1" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tbody>
						<tr bgcolor="#f4f4f4" align="center">
							<td width="5%">姓名</td>
							<td width="8%">证件类型</td>
							<td width="10%">日期</td>
							<td width="5%">出发地</td>
							<td width="5%">目的地</td>
							<td width="8%">票种</td>
							<td width="8%">车次</td>
							<td width="15%">坐席</td>
							<td width="8%">价格</td>
							<td width="25%">状态说明</td>
						</tr>
						<c:forEach var="traffic" items="${orderDetail.orderTrafficList}">
							<c:forEach var="ticket"
								items="${traffic.orderTrafficTicketInfoList}">
								<tr bgcolor="#ffffff" align="center">
									<td>${ticket.person.name}</td>
									<td>${ticket.person.zhCertType }</td>
									<td><lv:dateOutput date="${orderDetail.visitTime}"
											format="yyyy-MM-dd" /></td>
									<td>${traffic.departureStationName}</td>
									<td>${traffic.arrivalStationName}</td>
									<td>${ticket.zhTicketCategory}</td>
									<td>${traffic.trainName}</td>
									<td>${ticket.seatNo}</td>
									<td>${ticket.priceYuan}</td>
									<td>${ticket.zhTicketStatus}</td>
								</tr>
							</c:forEach>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!--popbox end-->
			<div id="editReceiverDialg"
				style="z-index: 300; margin-top: 10px; margin-left: -100px; display: none;">
			</div>
			<c:if
				test="${'NEED_ECONTRACT' == orderDetail.needContract &&  'CANCEL'!=orderDetail.orderStatus}">
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="1" width="100%" class="">
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
			</c:if>
			<br />
			<!--=============发票信息=============-->
			<jsp:include page="/WEB-INF/pages/order/invoice.jsp"></jsp:include>
			<br />

			<!-- 价格修改 -->
			<div href="http://super.lvmama.com/super_back/ord/loadModifyAmountApply.do" id="priceDiv"
				param="{'orderId':'${orderDetail.orderId }'}"></div>

			<div class="ordersum" style="margin-left: 10px;" id="orderCoupon"
				href="http://super.lvmama.com/super_back/orderCoupon/allMarkCoupon.do"
				param="{'orderId':'${orderDetail.orderId }'}"></div>

			<!--=============订单备注=============-->
			<div href="http://super.lvmama.com/super_back/ord/loadMemos.do" id="memoDiv"
				param="{'orderId':'${orderDetail.orderId }'}"></div>
			<!-- 订单备注 -->

			<!--popbox end-->
			<p class="submitbtn2">
				  <c:if test="${orderDetail.orderStatus!='CANCEL'}">
						<select name="cancelReason" id="cancelReason">
							<option value="">请选择</option>
							<c:forEach var="reason" items="${cancelReasons}">
								<option value="${reason.code }">${reason.name }</option>
							</c:forEach>
						</select>
				  </c:if>
				  
				  <c:if test="${orderDetail.orderStatus=='CANCEL'}">
			           	订单状态：取消
			      </c:if>
				  <c:if test="${orderDetail.orderStatus!='CANCEL'}">
						<c:if test="${aperiodicCanOperate}">
							<c:if test="${isCancelAble}">
								<c:if
									test="${orderDetail.paymentTarget == 'TOSUPPLIER' && orderDetail.performStatus=='UNPERFORMED' && !orderDetail.hasSettlement}">
									<input type="button" name="btnCancelOrder"
										onclick="chkCancelOrder();" value="废 单" class="right-button08">
								</c:if>
		
								<c:if
									test="${orderDetail.isJinjiangOrder && orderDetail.paymentStatus == 'PAYED'}">
									<input type="button" name="btnCancelOrder1"
										onclick="jinJiangCancelOrder();" value="废 单"
										class="right-button08">
								</c:if>
		
								<c:if
									test="${orderDetail.paymentTarget == 'TOLVMAMA' && orderDetail.approveStatus=='VERIFIED' && orderDetail.paymentStatus == 'UNPAY'}">
									<input type="button" name="btnCancelOrder1"
										onclick="chkCancelOrder();" value="废 单" class="right-button08">
								</c:if>
								<c:if test="${orderDetail.oughtPayYuan==0}">
									<input type="button" name="btnCancelOrder1"
										onclick="chkCancelOrder();" value="废 单" class="right-button08">
								</c:if>
								<c:if
									test="${orderDetail.paymentTarget == 'TOLVMAMA' && orderDetail.approveStatus=='VERIFIED' && (orderDetail.paymentStatus == 'PAYED' or orderDetail.paymentStatus == 'PARTPAY')}">
									<input type="button" name="btnCancelOrder2"
										onclick="doRefundOrder();" value="废 单"
										class="right-button08" />
								</c:if>
		
								<c:if test="${orderDetail.approveStatus == 'INFOPASS'}">
									<input type="button" name="btnCancelOrder1"
										onclick="chkCancelOrder();" value="废 单" class="right-button08">
								</c:if>
							</c:if>
							<c:if test="${!isCancelAble}">
								<span style="color: red"> 
								        <c:if test="${orderDetail.forbid}">
											该订单不退不改
										</c:if>
										<c:if test="${!orderDetail.forbid}">
											该订单过了最晚废单时间
										</c:if>
								</span>
							 </c:if>
						 </c:if>
						 <c:if test="${!aperiodicCanOperate}">
								该不定期订单已激活,不能操作
						 </c:if>
				</c:if>
<script type="text/javascript">
			function doCancelOrder(divName) {
				var reason = $("#cancelReason").find("option:selected").text();
				if(reason==''||reason=='请选择'){
					alert("请选择废单原因");
					return false;
				}
				httpRequest("http://super.lvmama.com/super_back/ord/doOrderCancel.do", {"orderId":${orderDetail.orderId }, "orderDetail.cancelReason":reason}, true, divName);
			}
			function showCancelOrderDiv(historyDiv) {
				var cancelResson = $("#cancelReason").find("option:selected").text();
				if(cancelResson==''||cancelResson=='请选择'){
					alert("请选择废单原因");
					return false;
				}
				cancelResson = encodeURI(cancelResson);
				window.open('http://super.lvmama.com/super_back/ord/refundMent/ordOrderRefundAdd.zul?orderId=${orderDetail.orderId }&isCancelOrder=true&cancelResson=' + cancelResson,'',	'height=500,width=1000,top=200, left=200,scrollbars=yes');
			}
			
			function jinJiangCancelOrder(){
				if(confirm("请确认锦江已废单,此次操作不会通知对方")){  
					doRefundOrder();
				}
			}
			<c:if test="${orderDetail.hasSettlement}">
				function chkCancelOrder(){
					if(confirm("该订单的结算状态为“结算中/已结算”,是否需要废单？")){
						doCancelOrder('historyDiv');
				}
				function doRefundOrder(){
					if(confirm("该订单的结算状态为“结算中/已结算”,是否需要废单？")){
						showCancelOrderDiv('historyDiv');
					}
				}
			}
			</c:if>
			
			<c:if test="${!orderDetail.hasSettlement}">
				function chkCancelOrder(){
					doCancelOrder('historyDiv');
				}
				function doRefundOrder(){
					showCancelOrderDiv('historyDiv');
				}
			</c:if>
</script>
				<c:if test="${orderDetail.redail=='true'}">
					<input name="redail" type="button" id="redail"
						onclick="doCancelNeedReplay();" value="取消需重拨"
						class="right-button08" />
				</c:if>
				<input type="button" name="btnCloseOrdHis" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('historyDiv');" />
			</p>

			<!-- 不通过原因 -->
			<c:if test="${orderDetail.resourceConfirmStatus=='LACK'}">
				<strong>资源审核不通过</strong>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="1" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tr bgcolor="#f4f4f4" align="center">
						<td height="30">原因</td>
						<td>内容</td>
						<td>操作人</td>
					</tr>
					<tr bgcolor="#ffffff" align="center">
						<td height="25">
						   <c:choose>
								<c:when test="${orderDetail.resourceLackReason=='NO_RESOURCE'}">没有资源</c:when>
								<c:when test="${orderDetail.resourceLackReason=='PRICE_CHANGE'}">价格更改</c:when>
								<c:when
									test="${orderDetail.resourceLackReason=='UNABLE_MEET_REQUIREMENTS'}">无法满足游客要求</c:when>
								<c:otherwise>其他</c:otherwise>
							</c:choose>
						</td>
						<td>
						    <c:choose>
								<c:when test="${orderDetail.resourceLackReason=='NO_RESOURCE'}">没有资源</c:when>
								<c:when test="${orderDetail.resourceLackReason=='PRICE_CHANGE'}">价格更改</c:when>
								<c:when
									test="${orderDetail.resourceLackReason=='UNABLE_MEET_REQUIREMENTS'}">无法满足游客要求</c:when>
								<c:otherwise>${orderDetail.resourceLackReason}</c:otherwise>
							</c:choose>
						</td>
						<td>${orderDetail.takenOperator }</td>
					</tr>
				</table>
			</c:if>

			<!-- 不通过原因end -->
			<!-- 操作日志 -->
			<strong>操作日志</strong>
			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
				border="1" bgcolor="#B8C9D6" width="100%" class="newfont03">
				<tr bgcolor="#f4f4f4" align="center">
					<td height="30">日志名称</td>
					<td style="width: 340px" nowrap="nowrap">内容</td>
					<td>操作人</td>
					<td>创建时间</td>
					<td>备注</td>
				</tr>
				<c:forEach var="log" items="${comLogs}">
					<tr bgcolor="#ffffff" align="center">
						<td height="25">${log.logName }</td>
						<td>${log.content } <c:if
								test="${log.logType=='cancelToCreateNew_new'}">
									老订单ID${log.parentId}
								</c:if> <c:if test="${log.logType=='cancelToCreateNew_original'}">
									新订单ID${log.parentId}
								</c:if>
						</td>
						<td>${log.operatorName }</td>
						<td><lv:dateOutput date="${log.createTime}"
								format="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${log.memo }</td>
					</tr>
				</c:forEach>
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
				url : "http://super.lvmama.com/super_back/order/delayWaitPayment.do",
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