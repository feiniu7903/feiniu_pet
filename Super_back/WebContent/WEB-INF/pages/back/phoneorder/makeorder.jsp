﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<s:set scope="request" name="hideCss" value="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<title>super——后台下单</title>

		<link href="${basePath}style/houtai.css" type="text/css" rel="stylesheet" />
		<link href="${basePath}style/phoneorder/superCss.css" type="text/css" rel="stylesheet" />
		<s:include value="/WEB-INF/pages/back/base/jquery.jsp" />
		<script src="${basePath}js/phoneorder/superJs.js" type="text/javascript"> </script>
        <script src="${basePath}js/phoneorder/makeorder.js" type="text/javascript"> </script>
		<script src="${basePath}js/phoneorder/important_tips.js" type="text/javascript"> </script>
		<script src="${basePath}js/phoneorder/place_info.js" type="text/javascript"> </script>
		<script type="text/javascript" src="${basePath}/js/timeprice/time.js"> </script>
	</head>

	<body>
		<input type="hidden" id="isPhysical" value="${product.physical }" />
		<input type="hidden" id="productId" value="${product.productId}" />
		<input type="hidden" id="productType" value="${product.productType}" />
		<input type="hidden" id="subProductType" value="${product.subProductType}" />
		<input type="hidden" id="testOrder" value="${testOrder}" />
		<input type="hidden" name="isAperiodic" id="isAperiodic" value="${isAperiodic}" />
		<input type="hidden" value="${userId}" id="userId" />
		<s:if test="testOrder">
		</s:if>
		<s:else>
			<div class="main main01">
				<!--mainTop end-->
				<div class="mainmid">
					<div class="tit" style="height: auto;">
						<s:form id="userForm" theme="simple" onsubmit="return false;">
							<span style="float: left;">用户查询： <input name="userName"
									id="userName" value="${userName}" type="text" /> <span>（您可以输入手机号码，用户名，邮箱，会员卡等信息查找用户）</span>
							</span>
							<a href="javascript:void(0)" class="button" style="display: none;"
								onclick="getLVCC();">获取LVCC信息</a>
							<a href="javascript:void(0)" class="button"
								onclick="window_callcenter('/pet_back/call/callCenter.do?callerid=&agentname=<s:property value="operatorName"/>@lvmama.com&isCallBack=Y&openFrom=supbak');">来电注册</a>
							<a href="javascript:void(0)" class="button"
								onclick="doShowChannelDialog();">渠道注册</a>
							<a href="javascript:void(0)" class="button" onclick="queryUsr();">查&nbsp;询</a>
						</s:form>
					</div>
					<div style="margin-top: 10px; font-size: 12px;" id="userItem"
						href="${basePath}phoneOrder/queryUser.do">
					</div>
				</div>
				<!--mainmid end-->
			</div>
		</s:else>
		<div id="edituser" href="<%=basePath%>/phoneOrder/toEditUser.do">
		</div>
		<div id="channelRegisterDiv"
			href="${basePath}/phoneOrder/doShowChannelPage.do">
		</div>
		<div class="main03">
			<h3 class="tit tit03">
				${product.zhProductType
				}信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:if test="product.isHotel()">
					<s:if test="product.IsAperiodic()">
						入住时间：<font color="red" size="3" style="font-weight: bold;">（此产品为不定期产品，用户需凭短信凭证在产品有效期内提前${product.aheadBookingDays }天致电酒店预约确认入住时间）注：供应商电话在短信凭证或产品介绍中有显示</font>
					</s:if>
					<s:else>
						入住时间：<font color="red" size="3" style="font-weight: bold;"><s:date
						name="visitDate" format="yyyy-MM-dd" /> </font>
						<s:if test="product.subProductType == 'SINGLE_ROOM'">
						&nbsp;离店时间：<font color="red" size="3" style="font-weight: bold;"><s:date
									name="leaveDate" format="yyyy-MM-dd" /> </font>
						</s:if>
					</s:else>
				</s:if>
				<s:elseif test="product.isTicket()">
					游玩时间：
					<font color="red" size="3" style="font-weight: bold;">
						<s:if test="product.IsAperiodic()">
						（此产品为不定期产品，用户在产品有效期内将短信凭证交与景区即可入园游玩）注：供应商电话在短信凭证或产品介绍中有显示
						</s:if>
						<s:else>
							<s:date name="visitDate" format="yyyy-MM-dd" /> 
						</s:else>
					</font>
				</s:elseif>
				<s:elseif test="product.isRoute()">
					<s:if test="product.IsAperiodic()">
						出发时间：<font color="red" size="3" style="font-weight: bold;">（此产品为不定期产品，用户需凭短信凭证在产品有效期内提前${product.aheadBookingDays }天致电供应商预约确认出发时间）注：供应商电话在短信凭证或产品介绍中有显示</font>
					</s:if>
					<s:else>
						出发时间：<font color="red" size="3" style="font-weight: bold;"><s:date
							name="visitDate" format="yyyy-MM-dd" /> </font>
						&nbsp;&nbsp;&nbsp;
						团号：<font color="red" size="3" style="font-weight: bold;">${routeCode}</font>
					</s:else>
				</s:elseif>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="后退" class="button" onclick="goBack();" />
			</h3>
			<div class="row row1">
				<h3 class="rowTIt">
					<em><a class="showImportantTips" href="javascript:void(0)"
						productId="${product.productId}"><font color="red">产品信息</font>
					</a> </em><font style="font-weight: bold;">${product.productId}</font>&nbsp;&nbsp;&nbsp;
					<s:if test="product.productType == 'ROUTE'">
						<a href="javascript:void(0)"
							style="font-weight: bold; font-size: 13px;">${product.productName}</a>
					</s:if>
					<s:else>
						<a href="javascript:void(0)" class="showPlaceInfo"
							style="font-weight: bold; font-size: 13px;"
							productId="${product.productId}">${product.productName}</a>
					</s:else>
					（${product.zhFilialeName}）
				</h3>
				<!-- 产品类别列表 -->
				<s:if test="product.isHotel()">
					<table width="100%" border="1" cellspacing="0" cellpadding="0"
						class="tableIntroduction">
						<tr class="tableTit">
							<td>
								房型
							</td>
							<td>
								可住人数
							</td>
							<td>
								市场价
							</td>
							<td>
								驴妈妈价
							</td>
							<td>
								早晨
							</td>
							<td>
								宽带
							</td>

							<td>
								数量
							</td>
							<td>
								小计
							</td>
							<s:if test="product.IsAperiodic()">
								<td>产品有效期</td>
							</s:if>
							<td>&nbsp;</td>
						</tr>
						<s:iterator value="prodBranchItemList">
							<tr>
								<td>
									<a href="javascript:void(0)" class="description">${key.branchName}</a>
									<div
										style='display: none; border: 1px solid #000; position: absolute; bottom: -10px; right: 0; background: #fff; width: 700px; height: 50px; padding: 10px; margin: 0 2px; z-index: 99999;'
										class='description'>
										${key.description }
									</div>
								</td>
								<td>
									${key.adultQuantity+key.childQuantity}
								</td>
								<td>
									￥${key.marketPriceYuan}
								</td>

								<td>
									￥${key.sellPriceYuan}
								</td>
								<td>
									${key.zhBreakfast}
								</td>
								<td>
									${key.zhBroadband}
								</td>
								<td>
									<input type="hidden" name="branchId"
										value="${key.prodBranchId}" />
									<input type="hidden" name="itemType" value="branch" />
									<input type="hidden" name="minimum" value="${key.minimum}" />
									<input type="hidden" name="maximum" value="${key.maximum}" />
									<input type="hidden" name="stock" value="${key.stock}" />
									<input type="hidden" name="sellPrice" value="${key.sellPrice}" />
									<input type="button" class="reduce" />
									<input title="prodBranchCount" type="text" class="countInput" value="${value}"
										name="countInput" />
									<p name="sumQuantity" style="display: none;">${(key.adultQuantity+key.childQuantity)}</p>
									<input type="button" class="plus" />
								</td>
								<td>
									￥
									<span name="amount_1">${key.sellPriceYuan*value}</span>
								</td>
								<s:if test="product.IsAperiodic()">
									<td>
										<s:date name="key.validBeginTime" format="yyyy-MM-dd" />至
										<s:date name="key.validEndTime" format="yyyy-MM-dd" />
										<s:if test='key.invalidDateMemo != null && key.invalidDateMemo != ""'>
											(${key.invalidDateMemo })
										</s:if>
									</td>
								</s:if>
								<td>
									<a
										onclick="loadLog({id:${product.productId}, mainProdBranchId:${key.prodBranchId}, justShow: 'true'});"
										href="#">查看时间价格</a>
								</td>
							</tr>
						</s:iterator>
					</table>
				</s:if>
				<s:else>
					<table width="100%" border="1" cellspacing="0" cellpadding="0"
						class="tableIntroduction">
						<tr class="tableTit">
							<td>
								票种
							</td>
							<td>
								市场价
							</td>
							<td>
								驴妈妈价
							</td>
							<td>
								数量
							</td>
							<td>
								小计
							</td>
							<s:if test="product.IsAperiodic()">
								<td>产品有效期</td>
							</s:if>
							<td>&nbsp;</td>
						</tr>
						<s:iterator value="prodBranchItemList">
							<tr>
								<td>
									${key.branchName}
								</td>
								<td>
									￥${key.marketPriceYuan}
								</td>
								<td>
									￥${key.sellPriceYuan}
								</td>
								<td>
									<input type="hidden" name="branchId"
										value="${key.prodBranchId}" />
									<input type="hidden" name="itemType" value="branch" />
									<input type="hidden" name="minimum" value="${key.minimum}" />
									<input type="hidden" name="maximum" value="${key.maximum}" />
									<input type="hidden" name="stock" value="${key.stock}" />
									<input type="hidden" name="sellPrice" value="${key.sellPrice}" />
									<input type="button" class="reduce" />
									<input title="prodBranchCount" type="text" class="countInput" value="${value}"
										name="countInput" />
									<p name="sumQuantity" style="display: none;">${(key.adultQuantity+key.childQuantity)}</p>
									<input type="button" class="plus" />
								</td>
								<td>
									￥
									<span name="amount_1">${key.sellPriceYuan*value}</span>
								</td>
								<s:if test="product.IsAperiodic()">
									<td>
										<s:date name="key.validBeginTime" format="yyyy-MM-dd" />至
										<s:date name="key.validEndTime" format="yyyy-MM-dd" />
										<s:if test='key.invalidDateMemo != null && key.invalidDateMemo != ""'>
											(${key.invalidDateMemo })
										</s:if>
									</td>
								</s:if>
								<td>
									<a
										onclick="loadLog({id:${product.productId}, mainProdBranchId:${key.prodBranchId}, justShow: 'true'});"
										href="#">查看时间价格</a>
								</td>
							</tr>
						</s:iterator>
					</table>
				</s:else>
			</div>
			<!--附加产品和附加类别列表-->
			<s:if
				test="relationProductItemList != null && relationProductItemList.size > 0">
				<h3 class="tit tit03">
					附加产品
				</h3>
				<div class="row row2">
					<table id="tabmark" width="100%" border="1" cellspacing="0" cellpadding="0"
						class="tableIntroduction">
						<tr class="tableTit">
							<td>
								附加产品
							</td>
							<td>
								价格
							</td>
							<td>
								数量
							</td>
							<td>
								小计
							</td>
						</tr>
						<s:iterator value="relationProductItemList">
							<tr>
								<td>
									${key.relationProduct.productName}(${key.branch.branchName})
								</td>
								<td>
									￥${key.branch.sellPriceYuan}
								</td>
								<td>
									<input type="hidden" name="branchId"
										value="${key.branch.prodBranchId}" />
									<input type="hidden" name="itemType" value="relation" />
									<input type="hidden" name="minimum"
										value="${key.branch.minimum}" />
									<input type="hidden" name="maximum"
										value="${key.branch.maximum}" />
									<input type="hidden" name="stock" value="${key.branch.stock}" />
									<input type="hidden" name="sellPrice"
										value="${key.branch.sellPrice}" />
									<s:if test="!(key.saleNumType == 'ALL' && key.relationProduct.subProductType == 'INSURANCE' && product.productType != 'OTHER')">
										<input type="button" class="reduce" />
									</s:if>
									<!-- 附加类别 -->
									<s:if test="key.branch.productId == product.productId">
										<input type="text" class="countInput" value="${value}"
											name="countInput" />
									</s:if>
									<!-- 附加类别 -->
									<s:else>
										<!-- 保险 -->
										<s:if test="key.relationProduct.subProductType == 'INSURANCE'">
											<s:if test="key.saleNumType == 'ALL' && product.productType != 'OTHER'">
												<input title="saleNumTypeAll" type="text" class="countInput" value=""
												name="additional" readonly='true'/>
											</s:if>
											<s:else>
												<input type="text" class="countInput" value="${value}"
												name="additional" />
											</s:else>
										</s:if>
										<s:else>
											<input type="text" class="countInput" value="${value}" />
										</s:else>
									</s:else>
									<s:if test="!(key.saleNumType == 'ALL' && key.relationProduct.subProductType == 'INSURANCE' && product.productType != 'OTHER')">
										<input type="button" class="plus" />
									</s:if>
								</td>
								<td>
									￥
									<span name="amount_1">${key.branch.sellPriceYuan*value}</span>
								</td>
							</tr>
						</s:iterator>
					</table>
				</div>
			</s:if>
			<table width="98%" border="1" cellspacing="0" cellpadding="0"
				class="tableIntroduction">
				<tr>
					<td colspan="4">
						<strong>总计：<b id="amount_2"></b>元 </strong>
					</td>
				</tr>
			</table>
			<!--设置隐藏文件用于优惠块显示-->
	    			<s:iterator value="prodBranchItemList">
	    				<input type="hidden" value="${key.branchName}" id="businessCouponBranchId${key.prodBranchId}" name="businessCouponBranchId${key.prodBranchId}"/>
	    			</s:iterator>
			<!--优惠块-->
				<div id="businessCoupon" href="${basePath}/phoneOrder/businessCoupon.do" >
					<s:include value="/WEB-INF/pages/back/phoneorder/businessCoupon.jsp"></s:include>
				</div>
				
			<!--优惠券块-->
				<div id="orderCoupon" href="${basePath}/phoneOrder/allMarkCoupon.do" >
					<s:include value="/WEB-INF/pages/back/phoneorder/youhui.jsp"></s:include>
				</div>
			 
			
			<!--总金额块-->
			<div id="orderAmount"
				href="${basePath}/phoneOrder/countOrderAmount.do">
					<s:include value="/WEB-INF/pages/back/phoneorder/order_amount.jsp"></s:include>
				</div>
			<div class="paymentObject">
				<b>支付对象：</b>
				<span><s:radio
						list="#{'TOLVMAMA':'支付给驴妈妈','TOSUPPLIER':'支付给供应商'}"
						value="payTarget" disabled="true" name="paymentName" /> </span>
				<b>支付等待时间：</b>
				<s:select list="payWaitItemList" listKey="attr01" listValue="name"
					id="paymentWaitTime" name="paymentWaitTime"></s:select>
				&nbsp;&nbsp;&nbsp;
				<b>订单来源渠道：</b>
				<s:if test="orderChannel != null">
					${zhOrderChannel}
				</s:if>
				<s:else>
					<s:select list="orderChannelList" listKey="code" listValue="name"
						id="orderChannel" name="orderChannel"></s:select>
				</s:else>
				<br/>
				<span id="lastModifyTimeSpan">
					<s:if test="lastModifyTime != null">
						<b>最晚修改或取消时间：</b>
						${lastModifyTime }
					</s:if>
				</span>
				<s:if test="viewPage != null">
					<s:if test="viewPage.contents.REFUNDSEXPLANATION.content!=null">
						<b>退款说明：</b>
						<s:property value="viewPage.contents.REFUNDSEXPLANATION.content" />
					</s:if>
				</s:if>
				
			</div>
			<!--取票人和联系人块-->
			<!-- 销售产品的必填项含取票人的证件号 -->
		<s:if test="product.travellerInfoOptions!=null && product.travellerInfoOptions.size>0">
			<s:if test='product.travellerInfoOptions.contains("C_CARD_NUMBER")'>
				<input type="hidden" id="travellerCardNum" value="true" />
			</s:if>
		</s:if>
		<div class="userInformation">
				<h3 class="tit tit03">
					<span class="button"
						onclick="beforeDoOperator('emergencyContactList');">保存为紧急联系人</span><span
						class="button"
						onclick="beforeDoOperator('visitorList');">保存为游客</span>
						<span 
						class="button"
						onclick="beforeDoOperator('receiversList');">保存为取票人</span>
					<span class="button" onclick="doModifyReceiver();">修改</span>
					<span class="button"
						onclick="beforeDoShowDialog('usrReceiverDiv', {'to' : 'add_receiver', 'productId' : $('#productId').val()}, '新增联系人');">添加</span>
					<em> 已有联系人选择： <span id="selectReceiversList"
						href="${basePath}/phoneOrder/doOperateSelectUsrReceivers.do">
						<s:include value="/WEB-INF/pages/back/phoneorder/receivers.jsp"></s:include>
					</span> </em>
				</h3>
				<h4>
					取票人信息
				</h4>
				<div id="receiversList"
					href="${basePath}/phoneOrder/doOperateUsrReceiversList.do">
					<s:include value="/WEB-INF/pages/back/phoneorder/receivers_list.jsp"></s:include>
				</div>
				<h4>
					游客信息
				</h4>
				<div id="visitorList"
					href="${basePath}/phoneOrder/doOperateVisitorList.do">
					<s:include value="/WEB-INF/pages/back/phoneorder/visitor_list.jsp"></s:include>
				</div>
				<h4>
					紧急联系人信息
				</h4>
				<div id="emergencyContactList"
					href="${basePath}/phoneOrder/doOperateEmergencyContactList.do">
					<s:include value="/WEB-INF/pages/back/phoneorder/emergency_list.jsp"></s:include>
				</div>				
			</div>
			<!--发票信息-->
			<div class="invoiceInformation" style="display:none;">
				<h3 class="tit tit03">
					发票信息：
					<input id="needInvoice" type="checkbox" class="clickShow03"
						data-biaoshi="invoiceInformationText" />
					<span>需要发票</span>
				</h3>
				<div class="invoiceInformationText">
					<h4>
						<a href="javascript:void(0)" class="clickShow02"
							onclick="doShowDialog('addInvoiceDiv', {'to' : 'add_invoice'}, '新增发票');">添加发票</a>
					</h4>
					<div id="invoiceList"
						href="${basePath}/phoneOrder/doOperateInvoiceList.do">
						<s:include value="/WEB-INF/pages/back/phoneorder/invoice_list.jsp"></s:include>
					</div>
				</div>
			</div>
			<!-- 地址信息块 -->
			<div class="addressInformation" style="display: none;">
				<h3 class="tit tit03">
					地址信息：
				</h3>
				<div class="addressInformation">
					<h4>
						<a href="javascript:void(0)" class="clickShow02"
							onclick="beforeDoShowDialog('addAddressDiv', {'to': 'add_address'}, '新增地址');"><b>点击添加地址</b>
						</a>
					</h4>
					<div id="addressList"
						href="${basePath}/phoneOrder/doOperateAddressList.do">
						<s:include value="/WEB-INF/pages/back/phoneorder/address_list.jsp"></s:include>
					</div>
				</div>
			</div>
			<!--备注信息-->
			<div class="orderRemarks">
				<h3 class="tit tit03">
					订单备注
				</h3>
				<div class="remarks">
					<s:form id="orderMemoForm" theme="simple">
						<b>备注类别：</b>
						<s:select list="memoTypes" name="oomemo.type" listKey="code"
							listValue="name">
						</s:select>
						<br />
						<b>备注内容：</b>
						<textarea id="memocontent" cols="remarks" rows="remarks" name="oomemo.content"></textarea>
					
						<input type="checkbox"  id="SpecialUserCheck" name="oomemo.userMemo" value="true" />
						<span style="color:red;">用户特殊要求审核</span>
						<input type="button" value="提交备注" class="button" onclick="addOrderMemo();" />
					</s:form>
				</div>
				<!--remarks end-->
				<div id="memoList"
					href="${basePath}/phoneOrder/doOperateMemoList.do">
					<s:include value="/WEB-INF/pages/back/phoneorder/memo_list.jsp"></s:include>
				</div>
				<ul>
					<!-- 上车地点 -->
					<s:if test="prodAssemblyPointList != null">
						<li>
							上车地点
							<b>（请自己复制）：</b>
							<span> <s:iterator value="prodAssemblyPointList">
							${assemblyPoint}&nbsp;&nbsp;&nbsp;
								</s:iterator> </span>
						</li>
					</s:if>
					<li>
						<s:if test="product.isPaymentToSupplier()" >
							<s:checkbox id="resourceConfirm" disabled="true" fieldValue="ddddddd"
							name="resourceConfirm" />
						</s:if>
						<s:else>
							<s:if test="supplierProduct">
								<s:checkbox id="resourceConfirm" fieldValue="ddddddd" disabled="true"
							name="resourceConfirm" onclick="checkResource();" />
							</s:if>
							<s:else>
								<s:checkbox id="resourceConfirm" fieldValue="ddddddd"
							name="resourceConfirm" onclick="checkResource();" />
							</s:else>
						</s:else>
						资源已确认
					</li>
					<li>
						<s:checkbox id="needRedail" fieldValue="ddddddd" name="needRedail" />
						需重发
					</li>
				</ul>
			</div>
			<!--orderRemarks end-->
			<input type="button" value="确认下单" class="button" id="checkOrder"
				onclick="checkOrder();" />
			<div id="saveOrderSucc" href="<%=basePath%>phoneOrder/doSaveOrder.do">
			</div>
			<!-- 新增取票人 -->
			<div class="usrReceiverAdd" id="usrReceiverDiv">
			</div>
			<!-- 投保信息 -->
			<div id="toubaoDiv">
			</div>
			<!-- 新增地址 -->
			<div class="addAddress" id="addAddressDiv"></div>
			<!-- 新增发票 -->
			<div class="addInvoice" id="addInvoiceDiv">
			</div>
		</div>
		${paramsStr }
	</body>
</html>
