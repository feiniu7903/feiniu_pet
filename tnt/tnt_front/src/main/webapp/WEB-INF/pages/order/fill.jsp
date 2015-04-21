<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages/tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<title>订单信息填写</title>
<link rel="shortcut icon" type="image/x-icon"
	href="http://www.lvmama.com/favicon.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="keywords" content="页面关键字">
<meta name="description" content="页面描述">
<link rel="stylesheet"
	href="http://pic.lvmama.com/min/index.php?f=/styles/v4/pa-base.css,/styles/v4/modules/arrow.css,/styles/v4/modules/button.css,/styles/v4/modules/forms.css,/styles/v4/modules/selectbox.css,/styles/v4/modules/step.css,/styles/v4/modules/tags.css,/styles/v4/modules/tip.css,/styles/v4/modules/dialog.css,/styles/v4/modules/calendar.css,/styles/v4/modules/tables.css,/styles/v4/modules/bank.css" />
<link rel="stylesheet"
	href="http://pic.lvmama.com/styles/fx/b2b_order.css">
</head>
<body class="order">
	<div class="top">
		<div class="top_wrap">
			<img src="http://pic.lvmama.com/img/fx/fx_phone.png" class="fx_phone"
				alt="400-6040-616"> <a href="#" class="fx_logo"><img
				src="http://pic.lvmama.com/img/fx/fx_logo.png"></a>
		</div>
	</div>
	<div class="orderstep step01"></div>
	<sf:form id="buyUpdateForm" name="orderForm" method="post"
		action="/order/buy.do" modelAttribute="buyInfo">
		<sf:hidden path="productId" />
		<sf:hidden path="visitDate" />
		<sf:hidden path="productType" />
		<sf:hidden path="isAperiodic" />
		<sf:hidden path="validBeginTime" />
		<sf:hidden path="validEndTime" />
		<sf:hidden path="branchId" value="${buyInfo.branchId}" />
		<sf:hidden path="visitTime" value="${buyInfo.visitTime}"
			id="allVisitDate" />
		<sf:hidden path="leaveTime" id="_leaveTime" />
		<sf:hidden path="isNeedResourceConfirm"
			value="${isNeedResourceConfirm}" />
		<sf:hidden path="isEContract" value="${isEContract}" />
		<input type="hidden" name="submitOrder" value="true" />
		<input type="hidden" value="${buyInfo.mainProdBranch.productId}"
			id="_mainProductId" name="product_mainProductId" />
		<sf:hidden path="days" value="${buyInfo.days}" />
		<sf:hidden path="isPayToLvmama" />
		<input type="hidden" name="buyPeopleNum" value="0" />
		<%-- <sf:hidden path="adultQuantity" value="${buyInfo.adultQuantity}" />
		<sf:hidden path="childQuantity" value="${buyInfo.childQuantity}" /> --%>
		<input name="orderCouponPrice" type="hidden" id="orderCouponPrice"
			value="0" />
		<input name="buyTime" type="hidden" value="${buyInfo.visitTime}"
			id="buyTime" />
		<input type="hidden" name="first"
			value="${buyInfo.firstTravellerInfoOptions};" />
		<input type="hidden" name="other"
			value="${buyInfo.travellerInfoOptions};" />

		<div class="wrap">
			<div class="order-main border equalheight-box">
				<!-- 侧边栏 sidebar -->
				<div class="sidebar equalheight-item">
					<div class="sidebox side-setbox">
						<div class="side-title">
							<h3>订单费用结算</h3>
						</div>
						<div class="content">
							<dl class="pro-setbox">
								<dt>产品费用</dt>
								<div id="mainHtml"></div>
							</dl>
							<dl class="pro-setbox" id="additionalProductCost">
								<dt>附加产品费用</dt>
								<div id="minHtml"></div>
							</dl>
							<div class="solid_line"></div>
							<div class="total-price">
								<span class="price-num"><dfn id="dingdanjiesujiage">0</dfn>
									元</span> <strong>订单金额：</strong>
							</div>
						</div>
					</div>
				</div>
				<!-- //.sidebar -->
				<div class="main equalheight-item">
					<!-- 预订信息 -->
					<div class="order-title">
						<h3>预订信息</h3>
					</div>
					<div class="order-content">
						<div class="xdl-hor">
							<dl class="xdl">
								<dt class="B">您预订：</dt>
								<dd>
									<a class="B" target="_blank" href="#">${buyInfo.mainProdBranch.productName}</a>
								</dd>
							</dl>
							<dl class="xdl">
								<dt class="B">
									<i class="req">*</i>
									<c:if test="${buyInfo.mainProdBranch.isAperiodic}">
									产品有效期：
									</c:if>
									<c:if test="${!buyInfo.mainProdBranch.isAperiodic}">游玩日期：</c:if>
								</dt>
								<dd>
									<c:if test="${buyInfo.mainProdBranch.aperiodic}">
											${buyInfo.mainProdBranch.stringValidBeginTime}至${buyInfo.mainProdBranch.stringValidEndTime}
												<c:if
											test="${buyInfo.mainProdBranch.invalidDateMemo != null && buyInfo.mainProdBranch.invalidDateMemo != ''}">
											(${buyInfo.mainProdBranch.invalidDateMemo})								
											</c:if>
									</c:if>
									<c:if test="${!buyInfo.mainProdBranch.aperiodic}">
										<div class="dinput dinput-date">
											<sf:input id="input_visitTime" path="visitTime"
												class="input-date calendar" maxlength="10"
												placeholder="${buyInfo.visitTime }" />
											<span class="date-info"><i class="xicon icon-date"></i><span
												class="text-info"></span></span>
										</div>
									</c:if>
								</dd>
							</dl>
							<dl class="xdl JS_check">
								<dt class="B">
									<i class="req">*</i>预订数量：
								</dt>
								<dd class="check-radio-box">
									<div class="check-text">
										<span class="check-radio-item"> <c:if
												test="${buyInfo.mainProdBranch.description!=null }">
												<i class="ui-arrow-bottom blue-ui-arrow-bottom"></i>
											</c:if> <lv:subStringShow
												value="${buyInfo.mainProdBranch.branchName}" />
										</span>
										<!-- minAmt="最小起订数" maxAmt="最大订购数" textNum="用户订购数" buyPeople="订购总人数" people="成人数+儿童数" -->
										<input type="hidden"
											value='<lv:mapValueShow key="product_${buyInfo.mainProdBranch.branchId}" map="${buyInfo.buyNum}" />'
											productId="${buyInfo.mainProdBranch.branchId}"
											id="param${buyInfo.mainProdBranch.branchId}" ordNum="ordNum"
											name="paramName" minAmt="${buyInfo.mainProdBranch.minimum}"
											maxAmt="<c:if test="${buyInfo.mainProdBranch.maximum>0 }">${buyInfo.mainProdBranch.maximum}</c:if>
											<c:if test="${buyInfo.mainProdBranch.maximum<=0 }">${buyInfo.mainProdBranch.stock}</c:if>"
											textNum="textNum${buyInfo.mainProdBranch.branchId}"
											people="${buyInfo.mainProdBranch.adultQuantity+buyInfo.mainProdBranch.childQuantity}"
											buyPeopleNum='<lv:mapValueShow key="product_${buyInfo.mainProdBranch.branchId}" map="${buyInfo.buyNum}" />*${buyInfo.mainProdBranch.adultQuantity+buyInfo.mainProdBranch.childQuantity}'
											sellPriceYuan="${buyInfo.mainProdBranch.sellPriceInt}" /><span
											class="oper-numbox"> <a
											onClick="updateOperator('param${buyInfo.mainProdBranch.branchId}','miuns');"
											class="op-reduce">-</a> <input type="text" class="op-number"
											id="textNum${buyInfo.mainProdBranch.branchId}"
											name="buyNum[product_${buyInfo.mainProdBranch.branchId}]"
											value='<lv:mapValueShow key="product_${buyInfo.mainProdBranch.branchId}" map="${buyInfo.buyNum}" />'
											onblur="updateOperator('param${buyInfo.mainProdBranch.branchId}','input');"
											couponPrice="0" type="text" sellName="sellName"
											sellPrice="${buyInfo.mainProdBranch.sellPriceInt}" size="2"
											autocomplete="off"
											mainBranchName="${buyInfo.mainProdBranch.branchName}"
											maxlength="8" /> <a class="op-increase"
											onClick="updateOperator('param${buyInfo.mainProdBranch.branchId}','add');">+</a></span>
										（单价
										<dfn>
											&yen;<i>${buyInfo.mainProdBranch.sellPriceInt}</i>
										</dfn>
										）

									</div>
									<c:if test="${buyInfo.mainProdBranch.description!=null}">
										<div class="tiptext tip-info check-content">
											<span class="tip-close">&times;</span>
											<div class="pre-wrap">${buyInfo.mainProdBranch.descriptionHtml}</div>
										</div>
									</c:if>
								</dd>
								<c:forEach items="${buyInfo.relatedProductList }" var="product">
									<dd class="check-radio-box">
										<div class="check-text">
											<span class="check-radio-item"><c:if
													test="${product.description!=null }">
													<i class="ui-arrow-bottom blue-ui-arrow-bottom"></i>
												</c:if> <lv:subStringShow value="${product.branchName}" /> </span> <input
												type="hidden"
												value='<lv:mapValueShow key="product_${product.branchId}" map="${buyInfo.buyNum}" />'
												productId="${product.branchId}"
												id="param${product.branchId}" ordNum="ordNum"
												name="paramName" minAmt="${product.minimum}"
												maxAmt="<c:if test="${product.maximum>0 }">${product.maximum}</c:if>
											<c:if test="${product.maximum<=0 }">${product.stock}</c:if>"
												textNum="textNum${product.branchId}"
												people="${product.adultQuantity+product.childQuantity}"
												buyPeopleNum='<lv:mapValueShow key="product_${product.branchId}" map="${buyInfo.buyNum}" />*${product.adultQuantity+product.childQuantity}'
												sellPriceYuan="${product.sellPriceInt}" /><span
												class="oper-numbox"> <a
												onClick="updateOperator('param${product.branchId}','miuns');"
												class="op-reduce">-</a> <input type="text" class="op-number"
												id="textNum${product.branchId}"
												name="buyNum[product_${product.branchId}]"
												value='<lv:mapValueShow
													key="product_${product.branchId}" map="${buyInfo.buyNum}" />'
												onblur="updateOperator('param${product.branchId}','input');"
												couponPrice="0" type="text" sellName="sellName"
												sellPrice="${product.sellPriceInt}" size="2"
												autocomplete="off" mainBranchName="${product.branchName}"
												maxlength="8" /> <a class="op-increase"
												onClick="updateOperator('param${product.branchId}','add');">+</a></span>
											（单价
											<dfn>
												&yen;<i>${product.sellPriceInt}</i>
											</dfn>
											）
										</div>
										<c:if test="${product.description!=null}">
											<div class="tiptext tip-info check-content">
												<span class="tip-close">&times;</span>
												<div class="pre-wrap">${product.descriptionHtml}</div>
											</div>
										</c:if>
									</dd>
								</c:forEach>
							</dl>
							<dl class="xdl JS_check">
								<c:forEach items="${buyInfo.additionalProduct['税金']}" var="tax">
									<dd class="dot_line">间隔线</dd>
									<dt class="B">税金：</dt>
									<dd class="check-radio-box">
										<div class="check-text">
											<label class="radio inline"> <input type="hidden"
												ordNum='ordNum' value="0"
												name="buyNum[product_${tax.prodBranchId}]"
												id="addition${tax.prodBranchId}" sellName="sellName"
												cashRefund="" couponPrice="0"
												marketPrice="${tax.marketPriceYuan}"
												sellPrice="${tax.sellPriceInt}"
												minBranchName="${tax.productName}(${tax.branchName})" /> <input
												class="input-radio" name="taxes" type="radio"
												btype="${tax.branchType}" pstype="${tax.subProductType}"
												id="${tax.prodBranchId}" saleNumType="${tax.saleNumType}"
												tt="fromRadio" onClick="validateRadioIsTrue();"></label><span
												class="check-radio-item"><i
												class="ui-arrow-bottom blue-ui-arrow-bottom"></i>${tax.productName}(${tax.branchName})</span>
											<dfn>&yen;${tax.sellPriceInt}</dfn>
											/人
										</div>
										<c:if test="${tax.description!=null}">
											<div class="tiptext tip-info check-content">
												<span class="tip-close">&times;</span>
												<div class="pre-wrap">${tax.descriptionHtml}</div>
											</div>
										</c:if>
									</dd>
								</c:forEach>
							</dl>
						</div>
					</div>
					<c:if
						test="${buyInfo.additionalProduct!=null&&!empty buyInfo.additionalProduct}">
						<div class="hr_a"></div>
						<!-- 附加产品 -->
						<div class="order-title">
							<h3>附加产品</h3>
						</div>
						<div class="order-content xdl-hor">
							<div class="form-small">
								<c:if test="${buyInfo.additionalProduct['保险']!=null }">
									<dl class="xdl JS_check">
										<dt class="B">保险：</dt>
										<c:forEach items="${buyInfo.additionalProduct['保险'] }"
											var="insurance">
											<dd class="check-radio-box">
												<div class="check-text">
													<label class="radio inline"> <input type="hidden"
														ordNum='ordNum' value="0"
														name="buyNum[product_${insurance.prodBranchId}]"
														id="addition${insurance.prodBranchId}" sellName="sellName"
														cashRefund="" couponPrice="0"
														marketPrice="${insurance.marketPriceYuan}"
														sellPrice="${insurance.sellPriceInt}"
														minBranchName="${insurance.productName}(${insurance.branchName})" />
														<input class="input-radio" name="insurance" type="radio"
														id="${insurance.prodBranchId}"
														btype="${insurance.branchType}"
														pstype="${insurance.subProductType}"
														saleNumType="${insurance.saleNumType}" tt="fromRadio"
														onClick="updateAdditionRadio('insurance');"></label><span
														class="check-radio-item"> <c:if
															test="${insurance.description!=null}">
															<i class="ui-arrow-bottom blue-ui-arrow-bottom"></i>
														</c:if> ${insurance.productName}(${insurance.branchName})
													</span>
													<dfn>&yen;${insurance.sellPriceInt}</dfn>
													/人
												</div>
												<c:if test="${insurance.description!=null }">
													<div class="tiptext tip-info check-content">
														<span class="tip-close">&times;</span>
														<div class="pre-wrap">${insurance.descriptionHtml}</div>
													</div>
												</c:if>
											</dd>
										</c:forEach>
										<dd class="check-radio-box">
											<div class="check-text">
												<label class="radio inline"><input
													class="input-radio no-check" name="insurance" type="radio"
													onClick="updateAdditionRadio('insurance');" value="0"><span
													class="no-check">不需要保险</span></label>

											</div>
											<div class="tiptext tip-warning no-check-content hide">
												<div class="pre-wrap">旅游保险能够给您的出行安全带来更多保障，所以驴妈妈建议您务必购买旅游保险。如您放弃购买，则行程中的风险和损失将由您自行承担。</div>
											</div>
										</dd>
									</dl>
								</c:if>
								<c:if test="${buyInfo.additionalProduct['自费产品']!=null }">
									<dl class="xdl JS_check form-inline">
										<dd class="dot_line">间隔线</dd>
										<dt class="B">自费产品：</dt>
										<c:forEach items="${buyInfo.additionalProduct['自费产品'] }"
											var="ownexpense">
											<dd class="check-radio-box">
												<div class="check-text">
													<label class="checkbox inline"> <input
														type="hidden" ordNum='ordNum' value="0"
														name="buyNum[product_${ownexpense.prodBranchId}]"
														id="addition${ownexpense.prodBranchId}"
														sellName="sellName" cashRefund="" couponPrice="0"
														marketPrice="${ownexpense.marketPriceYuan}"
														sellPrice="${ownexpense.sellPriceInt}"
														minBranchName="${ownexpense.productName}(${ownexpense.branchName})" />
														<input class="input-checkbox" name="ownpro"
														type="checkbox" value="${ownexpense.prodBranchId}"
														productAdditional="true"></label><span
														class="check-radio-item"> <c:if
															test="${ ownexpense.description!=null}">
															<i class="ui-arrow-bottom blue-ui-arrow-bottom"></i>
														</c:if> ${ownexpense.productName}(${ownexpense.branchName})
													</span>
													<dfn>&yen;${ownexpense.sellPriceInt}</dfn>
													<div id="input${ownexpense.prodBranchId}"
														btype="${ownexpense.branchType}"
														pstype="${ownexpense.subProductType}"
														saleNumType="${ownexpense.saleNumType}"
														minAmt="${ownexpense.minimum}"
														maxAmt="<c:if test="${ownexpense.maximum>0 }" >${ownexpense.maximum}</c:if><c:if test="${ownexpense.maximum<=0 }" >${ownexpense.stock}</c:if>"
														class="selectbox selectbox-mini hide">
														<p class="select-info like-input">
															<span class="select-arrow"><i
																class="ui-arrow-bottom dark-ui-arrow-bottom"></i></span> <span
																class="select-value"></span>
														</p>
														<div class="selectbox-drop">
															<ul class="select-results">
															</ul>
														</div>
													</div>
												</div>
												<c:if test="${ownexpense.description!=null }">
													<div class="tiptext tip-info check-content">
														<span class="tip-close">&times;</span>
														<div class="pre-wrap">${ownexpense.descriptionHtml}</div>
													</div>
												</c:if>
											</dd>
										</c:forEach>
									</dl>
								</c:if>
								<c:if test="${buyInfo.additionalProduct['其它']!=null }">
									<dl class="xdl JS_check">
										<dd class="dot_line">间隔线</dd>
										<dt class="B">其他：</dt>
										<c:forEach items="${buyInfo.additionalProduct['其它'] }"
											var="other">
											<dd class="check-radio-box">
												<div class="check-text">
													<label class="checkbox inline"> <input
														type="hidden" ordNum='ordNum' value="0"
														name="buyNum[product_${other.prodBranchId}]"
														id="addition${other.prodBranchId}" sellName="sellName"
														cashRefund="" couponPrice="0"
														marketPrice="${other.marketPriceYuan}"
														sellPrice="${other.sellPriceInt}"
														minBranchName="${other.productName}(${other.branchName})" />
														<input class="input-checkbox" name="ownpro" name="ownpro"
														type="checkbox" value="${other.prodBranchId}"
														productAdditional="true"></label><span
														class="check-radio-item"><c:if
															test="${other.description!=null }">
															<i class="ui-arrow-bottom blue-ui-arrow-bottom"></i>
														</c:if> ${other.productName}(${other.branchName})</span>
													<dfn>&yen;${other.sellPriceInt}</dfn>
													<div id="input${other.prodBranchId}"
														btype="${other.branchType}"
														pstype="${other.subProductType}"
														saleNumType="${other.saleNumType}"
														minAmt="${other.minimum}"
														maxAmt="<c:if test="${other.maximum>0 }" >${other.maximum}</c:if><c:if test="${other.maximum<=0 }" >${other.stock}</c:if>"
														class="selectbox selectbox-mini hide">
														<p class="select-info like-input">
															<span class="select-arrow"><i
																class="ui-arrow-bottom dark-ui-arrow-bottom"></i></span> <span
																class="select-value"></span>
														</p>
														<div class="selectbox-drop">
															<ul class="select-results">
															</ul>
														</div>
													</div>
												</div>
												<c:if test="${other.description!=null }">
													<div class="tiptext tip-info check-content">
														<span class="tip-close">&times;</span>
														<div class="pre-wrap">${other.descriptionHtml}</div>
													</div>
												</c:if>
											</dd>
										</c:forEach>
									</dl>
								</c:if>
								<c:if test="${buyInfo.additionalProduct['快递']!=null }">
									<dl class="xdl JS_check">
										<dd class="dot_line">间隔线</dd>
										<dt class="B">快递：</dt>
										<c:forEach items="${buyInfo.additionalProduct['快递'] }"
											var="express">
											<dd class="check-radio-box">
												<div class="check-text">
													<label class="radio inline"> <input type="hidden"
														ordNum='ordNum' value="0"
														name="buyNum[product_${express.prodBranchId}]"
														id="addition${express.prodBranchId}" sellName="sellName"
														cashRefund="" couponPrice="0"
														marketPrice="${express.marketPriceYuan}"
														sellPrice="${express.sellPriceInt}"
														minBranchName="${express.productName}(${express.branchName})" />
														<input class="input-radio" name="express" type="radio"
														name="express" btype="${express.branchType}"
														pstype="${express.subProductType}"
														id="${express.prodBranchId}"
														saleNumType="${express.saleNumType}"
														onClick="updateAdditionRadio();" value="1" tt="fromRadio"></label><span
														class="check-radio-item"><c:if
															test="${express.description!=null }">
															<i class="ui-arrow-bottom blue-ui-arrow-bottom"></i>
														</c:if> ${express.productName}(${express.branchName})</span>
													<dfn>&yen;${express.sellPriceInt}</dfn>
												</div>
												<c:if test="${express.description!=null }">
													<div class="tiptext tip-info check-content">
														<span class="tip-close">&times;</span>
														<div class="pre-wrap">${express.descriptionHtml}</div>
													</div>
												</c:if>
											</dd>
										</c:forEach>
									</dl>
									<div id="showAddress">
										<input type="hidden" name="physical" value="true" /> <input
											type="hidden" valid="true" value="1">
										<!--如果存在地址就显示修改状态下的样式 start -->
										<div id="frast-edit" class="form-edit">
											<dl class="xdl">
												<dt>
													<i class="req">*</i>收件人姓名：
												</dt>
												<dd>
													<sf:input id="address-user" class="input-text"
														path="usrReceivers.fullName" maxlength="20" />
												</dd>
											</dl>
											<dl class="xdl">
												<dt>
													<i class="req">*</i>手机号码：
												</dt>
												<dd>
													<sf:input id="address-mobile" class="input-text"
														path="usrReceivers.mobile" value="15900909053" />
												</dd>
											</dl>
											<dl class="xdl">
												<dt>
													<i class="req">*</i>省份：
												</dt>
												<dd>
													<sf:select path="usrReceivers.province" id="captialId"
														class="areaselectBox province">
														<option value="">--请选择省份--</option>
													</sf:select>
													<sf:select path="usrReceivers.city" id="cityId"
														class="areaselectBox city">
														<option value="">--请选择城市--</option>
													</sf:select>
												</dd>
											</dl>
											<dl class="xdl">
												<dt>
													<i class="req">*</i>收件地址：
												</dt>
												<dd>
													<sf:input id="address" class="input-text input-big"
														path="usrReceivers.address" maxlength="100" value="青浦" />
												</dd>
											</dl>
											<dl class="xdl">
												<dt>邮编：</dt>
												<dd>
													<sf:input id="address-postcode"
														class="input-text input-mini" path="usrReceivers.postCode"
														maxlength="6" value="201706" />
												</dd>
											</dl>
											<dl class="xdl">
												<dt></dt>
												<dd>
													<button style="visibility: hidden" id="frast-submit"
														class="pbtn pbtn-mini pbtn-blue">保存</button>
												</dd>
											</dl>
										</div>
									</div>
								</c:if>

							</div>
						</div>
						<div class="hr_a"></div>
					</c:if>
					<!-- 订单联系人信息 -->
					<div class="order-title">
						<h3>取票人信息</h3>
					</div>
					<div class="order-content xdl-hor">
						<div class="form-small">
							<!-- 默认可编辑状态 -->
							<div id="order-edit" class="person form-edit">
								<input type="hidden" valid="true" value="2">
								<dl class="xdl">
									<dt>
										<i class="req">*</i>取票人：
									</dt>
									<dd class="form-inline">
										<label class="inline"><sf:input
												path="contact.fullName" id="take-user"
												name="text-order-person" placeholder="姓名" class="input-text" /></label>
									</dd>
								</dl>
								<dl class="xdl">
									<dt>
										<i class="req">*</i>手机号码：
									</dt>
									<dd class="form-inline">
										<label class="inline"> <sf:input path="contact.mobile"
												id="take-mobile" class="input-text" /> <span
											class="help-inline">免费接受订单确认短信，请务必填写正确</span></label>
									</dd>
								</dl>
								<c:if test="${buyInfo.contactContainsCardNumber}">
									<dl class="xdl">
										<dt>
											<i class="req">*</i>证件类型：
										</dt>
										<dd class="form-inline">
											<div class="selectbox selectbox-small">
												<p class="select-info like-input">
													<span value="ID_CARD" class="select-value">身份证</span>
													<sf:hidden path="contact.idType" value="ID_CARD" />
												</p>
											</div>
											<sf:input id="take-cardNum" path="contact.idNo"
												autocomplete="off" placeholder="证件号码"
												class="input-text input-middle" data-field="cardNum" />
										</dd>
									</dl>
								</c:if>
								<dl class="xdl">
									<dt></dt>
									<dd>
										<button style="visibility: hidden" id="take-submit"
											class="pbtn pbtn-mini pbtn-blue">保存</button>
									</dd>
								</dl>
							</div>
							<!-- 紧急联系人信息 -->
							<%-- <div class="order-title">
								<h3>紧急联系人信息</h3>
							</div>
							<div class="order-content xdl-hor">
								<div class="form-small">
									<!-- 默认可编辑状态 -->
									<div class="person form-edit">
										<input type="hidden" valid="true" value="5">
										<dl class="xdl">
											<dt>姓名：</dt>
											<dd class="form-inline">
												<label class="inline"><sf:input id="em-user"
														path="emergencyContact.fullName" placeholder="姓名"
														type="text" class="input-text" /></label>
											</dd>
										</dl>
										<dl class="xdl">
											<dt>手机：</dt>
											<dd class="form-inline">
												<label class="inline"> <sf:input id="em-mobile"
														path="emergencyContact.mobile" class="input-text"
														 /> <span class="help-inline">免费接受订单确认短信，请务必填写正确</span></label>
											</dd>
										</dl>
										<dl class="xdl">
											<dt></dt>
											<dd>
												<button style="visibility: hidden" id="em-submit"
													class="pbtn pbtn-mini pbtn-blue">保存</button>
											</dd>
										</dl>
									</div>
								</div>
							</div> --%>
							<!-- //紧急联系人信息 -->
							<div class="hr_a"></div>
							<!-- 游玩人信息 -->
							<div class="order-title" data-hide="play">
								<h3>游玩人信息</h3>
								<input type="hidden" id="firstTravellerInfoOptions"
									value="${buyInfo.firstTravellerInfoOptions};" /> <input
									type="hidden" id="travellerInfoOptions"
									value="${buyInfo.travellerInfoOptions};" />
							</div>
							<div class="order-content xdl-hor" data-hide="play">
								<div class="hr_a"></div>
								<div id="play-edit" class="form-edit">
									<input type="hidden" valid="true" value="4">
									<dl class="xdl">
										<dt></dt>
										<dd>
											<button id="play-submit" style="visibility: hidden"
												class="pbtn pbtn-mini pbtn-blue"></button>
										</dd>
									</dl>
								</div>
							</div>
							<!-- 同意协议/提交订单 -->
							<div class="order-content xdl-hor">
								<div class="hr_d"></div>
								<dl class="xdl">
									<dt class="tl">
										<a href="javascript:history.back();" class="vmiddle">&lt;
											返回上一步</a>
									</dt>
									<dd>
										<button class="pbtn pbtn-big pbtn-orange" id="form-submit"
											type="button" onclick="btnFormSubmit();">同意以下预订协议并提交订单</button>
									</dd>
								</dl>
								<div class="lv-agree">
									<h5>驴妈妈旅游网预订条款</h5>
									<br /> <strong>1.驴妈妈预订条款的确认</strong><br />
									驴妈妈旅游网（以下简称“驴妈妈”）各项服务的所有权与运作权归景域旅游运营集团所有。本服务条款具有法律约束力。一旦您点选“确认下单"成功提交订单后，即表示您自愿接受本协议之所有条款，并同意通过驴妈妈订购旅游产品。<br />

									<strong>2．服务内容</strong><br /> 2.1
									驴妈妈服务的具体内容由景域旅游运营集团根据实际情况提供，驴妈妈对其所提供之服务拥有最终解释权。<br /> 2.2
									景域旅游运营集团在驴妈妈上向其会员提供相关网络服务。其它与相关网络服务有关的设备（如个人电脑、手机、及其他与接入互联网或移动网有关的装置）及所需的费用（如为接入互联网而支付的电话费及上网费、为使用移动网而支付的手机费等）均由会员自行负担。<br />

									<strong>3. 特别提示</strong><br /> 3.1 预订提前期与付款期：<br />
									预订提前期与付款期。景点门票预订：所有订单请提前2天预订，部分支持手机电子票（二维码）的景点，支持当天预订当天游玩。如需在线支付请在订单生成后1个小时内付清已订产品所有款项。酒店预订：驴妈妈网站提供的门市价仅供参考，具体价格以酒店当日挂牌价为准。如需在线支付请提前1天预订并在预订后的半小时内付清已订产品所有款项。如未在规定时间内未付清所有款项，驴妈妈将取消产品订单
									。<br /> <strong>4. 订单生效</strong><br />
									订单生效后，您应按订单中约定的时间按时入园或入住酒店。如您未准时出发或未按时入园，视为您主动解约。<br /> <strong>5.
										解除已生效订单</strong><br /> 5.1 景点订单<br /> 5.1.1 如您通过取票时付款，解除预订无须支付任何费用。<br />
									5.1.2
									如您通过预付费预订，订单生效后，若要主动解除已生效订单，您须在行程前一天中午12点前通知驴妈妈解除所做预订，包括放弃整张订单的全部内容和减少出行人数，如未按规定时间内通知解除的情况，视违约处理，您需要按照如下标准支付违约金。<br />
									5.1.3 在至景点游玩前一天中午12点以前通知取消的，支付全部费用总额的5%的违约金，逾期则不予退费。<br />
									5.1.4 如景点有最新优惠活动，而本网站“会员价”高过于景点活动“优惠价”，本网站承诺会将游客已支付的高出“优惠价”
									的差额退还到游客指定的账户内。<br /> 5.2 酒店订单<br /> 5.2.1
									如您通过预付费预订并已完成支付，订单生效后，若要主动解除已生效订单，您须在行程前一天12点前通知驴妈妈解除所
									做预订，包括放弃整张订单的全部内容和减少出行人数，如未按规定时间内通知解除的情况，视违约处理，您需要按照如下标准支付违约金。<br />
									5.2.2 在入住酒店前24小时内通知取消的，支付全部费用总额的5%违约金，逾期则不予退费。<br /> 5.3 线路订单<br />
									旅游出行前，一方当事人因违约不能成行的，按照下列标准承担违约责任。<br /> 5.3.1
									违约方在出发前72小时通知对方的，应当支付旅游合同总价5%的违约金。<br /> 5.3.2
									违约方在出发前72小时内通知对方的，应当支付旅游合同总价10%的违约金。<br /> 5.3.3
									以上违约责任如涉及航空、陆运、水运票务等损失，可参照相关部门有关条款另行赔偿，违约金或赔偿金总额不超过旅游费用总额。<br />

									5.4 特殊产品订单<br /> 部分产品由于资源限制等特殊性，一旦预订不予退费！（此信息会在产品相关页面作提示） <br />

									<strong>6. 更改已生效订单</strong><br />
									订单生效后，您主动要求更改该订单内的任何项目，请务必在行程前一天中午12点前通知驴妈妈您的更改需求。驴妈妈会尽量满足
									您的需求，但您必须全额承担因变更带来的损失及可能增加的费用。<br /> <strong>7.
										因驴妈妈原因取消您的已生效订单</strong><br />
									在您按要求付清所有产品费用后，如因驴妈妈原因，致使您的产品取消，驴妈妈应当立即通知您。<br /> <strong>8.
										不可抗力</strong><br />
									您和驴妈妈双方因不可抗力(包括但不限于地震、台风、雷击、水灾、火灾等自然原因,以及战争、政府行为、黑客攻击、电信部门技术管制等原因)不能履行或不能继续履行已生效订单约定内容的，双方均不承担违约责任，但法律另有规定的除外。因驴妈妈延迟履行已生效订单约定内容后发生不可抗力的，不能免除责任。<br />

									<strong>9. 解决争议适用法律法规约定</strong><br />
									在您的预订生效后，如果在本须知或订单约定内容履行过程中，您对相关事宜的履行发生争议，您只同意按照中华人民共和国国家
									旅游局颁布的相关法律法规来解决争议。<br /> <strong>10. 其它</strong><br />
									本须知未尽的其他事项，在驴妈妈确认给您的订单中另行约定。同时合同双方需承担对等的义务。<br /> 祝您旅途愉快! <br />
									如有疑问，请拨打驴妈妈客服电话:1010-6060<br />
								</div>
							</div>
							<!-- 同意协议/提交订单 -->
						</div>
						<!-- //.main -->
					</div>
				</div>
			</div>
		</div>
	</sf:form>
	<div id="ticketHiddenDiv" style="display: none;">
		<sf:form id="chanageSubmitForm" method="post" modelAttribute="buyInfo">
			<sf:hidden path="productId" value="${buyInfo.productId}" />
			<sf:hidden path="branchId" value="${buyInfo.branchId}" />
			<%-- 	<sf:hidden path="channel" value="${buyInfo.channel}" /> --%>
			<sf:hidden path="visitTime" id="changevisitTime"
				value="${buyInfo.visitTime}" />
		</sf:form>
	</div>
	<script
		src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js"></script>
	<script
		src="http://pic.lvmama.com/js/ui/lvmamaUI/plugin/city/json-array-of-city-new.js"></script>
	<script
		src="http://pic.lvmama.com/js/ui/lvmamaUI/plugin/city/json-array-of-province.js"></script>
	<script src="/js/jqueryCity.js"></script>
	<script
		src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js"></script>
	<script src="http://pic.lvmama.com/js/fx/b2c_front/pandora-calendar.js"></script>

	<script src="/js/order/orderBuyBase.js" type="text/javascript"></script>
	<script src="/js/order/order-page.js"></script>
	<script src="/js/order/emergencyContact.js" type="text/javascript"></script>
	<script src="/js/order/orderFillNew.js" type="text/javascript"></script>
	<script src="/js/timePrice.js" type="text/javascript"></script>
</body>
<!-- <script src="http://pic.lvmama.com/js/common/losc.js"></script> -->
<script type="text/javascript">
	var productId = "${buyInfo.productId}";

	pandora.calendar({
		sourceFn : fillData,
		selectDateCallback : callbackClick,
		frequent : true
	});

	function callbackClick() {
		var time_1 = $("#input_visitTime").val();
		$("#allVisitDate").val(time_1);
		$("#changevisitTime").val(time_1);
		var _form = $("#chanageSubmitForm");
		_form.submit();
	}

	var lock = false;
	function btnFormSubmit() {
		if (!lock) {
			lock = true;
			var flag = subOrders();
			if (flag) {
				loginFormSubmit();
			} else {
				lock = false;
			}
		}
	}

	function loginFormSubmit() {
		$("#buyUpdateForm").submit();
	}
</script>
<c:if test="${buyInfo.additionalProduct['快递']!=null }">
	<script type="text/javascript">
		UI.extend.city({
			province : $("#captialId"),
			city : $("#cityId"),
			provinceRequest : true,
			cityRequest : true
		});
		function provinceLoaded() {
			for ( var i = 0; i < document.getElementById("captialId").options.length; i++) {
				if (document.getElementById("captialId").options[i].value == $(
						"#province_city").attr("tag")) {
					document.getElementById("captialId").options[i].selected = "true";
					break;
				}
			}
		}
		function cityLoaded() {
			for ( var i = 0; i < document.getElementById("cityId").options.length; i++) {
				if (document.getElementById("cityId").options[i].value == $(
						"#province_city").val()) {
					document.getElementById("cityId").options[i].selected = "true";
					break;
				}
			}
		}
		$("#captialId").change();
	</script>
</c:if>
</html>