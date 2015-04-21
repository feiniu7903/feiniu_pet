<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>实际团成本</title>
	<jsp:include page="head.jsp"></jsp:include>
	<script type="text/javascript" src="../js/op/groupBudget/final_budget.js"></script>	
</head>
<body>
	<!-- 团号 -->
	<input id="groupCodeHd" type="hidden"
		value="<s:property value="travelGroupCode" />"/>
	<input id="groupSettlementStatusHd" type="hidden"
		value="<s:property value="#request.group.settlementStatus" />"/>
	<div class="wapper_accounts">
		<div class="rad5 wapper_list wapper_list_cash">
			<h3 class="order_check">实际团成本[团号：<s:property value="travelGroupCode"/>]</h3>
			<div class="tb_model_cont">
				<table class="mt10 tabw100 rad5 blue_skin_tb" border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<th>实际人数</th>
							<th>成人</th>
							<th>儿童</th>
							<th>实际收入</th>
							<th>活动折让</th>
							<th>实际成本</th>
							<th>实际毛利润</th>
							<th>实际毛利率</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<span id="actPersonsSp"><s:if test="#request.finalBudget != null"><s:property value="#request.finalBudget.actPersons"/></s:if></span>
								人
							</td>
							<td>
								<span id="actAdultSp"><s:if test="#request.finalBudget != null"><s:property value="#request.finalBudget.actAdult"/></s:if></span>
								人
							</td>
							<td>
								<span id="actChildSp"><s:if test="#request.finalBudget != null"><s:property value="#request.finalBudget.actChild"/></s:if></span>
								人
							</td>
							<td>
								<span id="actIncomingSp">
									<span id="salePriceSp">
										<fmt:formatNumber pattern="#,##0.00" value="${finalBudget.actIncoming}" />
									</span>
								</span>
							</td>
							<td>
								<span id="actAllowanceSp">
									<s:if test="#request.finalBudget != null">
										<fmt:formatNumber pattern="#,##0.00" value="${finalBudget.actAllowance}" />
								    </s:if>
								</span>
							</td>
							<td>
								<span id="actTotalCostsSp">
									<fmt:formatNumber pattern="#,##0.00" value="${finalBudget.actTotalCosts}" />
								</span>
							</td>
							<td>
								<span id="actProfitSp">
									<fmt:formatNumber pattern="#,##0.00" value="${finalBudget.actProfit}" />
								</span>
							</td>
							<td>
								<span id="actProfitRateSp"> 
									<s:if test="#request.finalBudget != null">
										<s:i18n name="format">
											<s:text name="format.number.money">
												<s:param value="#request.finalBudget.actProfitRate * 100"/>
											</s:text>
										</s:i18n>
								    </s:if>
								</span>
								%
							</td>
							<td>
								<a href="javascript:void(0)" onclick="editActPersonsHandler()" class="hidableClass">修改人数</a>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="p_rel w400 addition_income">
					<h3>附加收入：</h3>
					<input id="addIncomingBtn" type="submit" name="" class="p_abs left_bt Add_btn hidableClass" value="添加">
					<table class="mt10 tabw100 rad5 blue_skin_tb" border="0" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th>金额</th>
								<th>内容</th>
								<th>添加时间</th>
								<th>添加人</th>
								<th>备注</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="#request.incomingList" var="incoming">
								<tr>
									<td width="100"><fmt:formatNumber pattern="#,##0.00" value="${incoming.amount}" /></td>
									<td width="200">${incoming.costsItemName}</td>
									<td width="150">
										<s:date name="#incoming.createtime" format="yyyy-MM-dd HH:mm:ss"/>
									</td>
									<td width="150">
										${incoming.creator }
									</td>
									<td width="200">
										${incoming.remark}
									</td>
									<td width="200">
										<a href="#" class="hidableClass" onclick="editIncomingHandler(
											${incoming.id},
											${incoming.costsItemId },
											${incoming.amount },
											'${incoming.remark}'
											)">修改</a>
										<a href="#" class="hidableClass" onclick="deleteIncomingHandler(
											${incoming.id}
											)">删除</a>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				<h3>团实际成本项：</h3>
				<div class="tb_sort_cont">
					<h4>产品成本</h4>
					<table class="rad5 tabw100 blue_skin_tb" border="0" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th>实际成本项</th>
								<th>类别</th>
								<th>实际成本</th>
								<th>数量</th>
								<th>币种</th>
								<th>汇率</th>
								<th>单项总成本（元）</th>
								<th>单项总成本（币种）</th>
								<th>供应商/ID</th>
								<th>结算对象/结算ID</th>
								<th>付款方式</th>
								<th>打款金额</th>
								<th>付款状态</th>
								<th>是否计入总成本</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="#request.productList" var="product">
								<tr>
									<td width="150">${product.productName}</td>
									<td>${product.prdBranchName}</td>
									<td ><fmt:formatNumber pattern="#,##0.00" value="${product.bgCosts}" /></td>
									<td>${product.quantity }</td>
									<td>${product.currencyName }</td>
									<td>${product.exchangeRate }</td>
									<td><fmt:formatNumber pattern="#,##0.00" value="${product.subtotalCosts}" /></td>
									<td><fmt:formatNumber pattern="#,##0.00" value="${product.subtotalCostsFc}" /></td>
									<td>${product.supplierStr }</td>
									<td>${product.targetStr }</td>
									<td>${product.paymentTypeName }</td>
									<td><fmt:formatNumber pattern="#,##0.00" value="${product.payAmount}" /></td>
									<td>${product.payStatusName }</td>
									<td>
										${product.isInCost == 'Y'?'是':'否'}
									</td>
									<td>
										<s:if test="(#product.payStatus == 'NOPAY' || #product.payStatus == 'PARTREQPAY' || #product.payStatus == 'PARTPAY')
													&& #product.subtotalCostsFc > 0">
											<a href="#" onclick="requirePayHandler(1,${product.itemId},${product.supplierId},${product.subtotalCostsFc},'<fmt:formatNumber pattern="#,##0.00" value="${product.subtotalCostsFc}" />' )" class="hidableClass">付款申请</a>
										</s:if>
										<s:if test="#product.payStatus == 'REQPAY' && #product.subtotalCostsFc > 0">
											<a href="#" onclick="delayPayHandler(1,${product.itemId})" class="hidableClass">延迟</a>
										</s:if>
										<a href="#" onclick="showProductDetails(${product.prdBranchId},'${travelGroupCode}')">查看明细</a>
										
										<s:if test="#request.group.settlementStatus != 'CHECKED'">
											<s:if test="#product.payStatus == 'NOPAY'">
												<a href="isInCost.do?isInCost=${product.isInCost == 'Y'?'N':'Y'}&prdBranchId=${product.prdBranchId}">
													${product.isInCost == 'Y'?'不计入总成本':'加入总成本'}
												</a>
											</s:if>
										</s:if>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<h4>固定成本</h4>
					<table class="rad5 tabw100 blue_skin_tb" border="0" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th>实际成本项</th>
								<th>实际成本</th>
								<th>数量</th>
								<th>币种</th>
								<th>汇率</th>
								<th>单项总成本（元）</th>
								<th>单项总成本（币种）</th>
								<th>供应商/ID</th>
								<th>结算对象/结算ID</th>
								<th>付款方式</th>
								<th>打款金额</th>
								<th>付款状态</th>
								<th>备注</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="#request.fixedList" var="fixed">
								<tr>
									<td width="100">${fixed.costsItemName}</td>
									<td width="100"><fmt:formatNumber pattern="#,##0.00" value="${fixed.bgCosts}" /></td>
									<td width="80">${fixed.quantity}</td>
									<td width="80">${fixed.currencyName }</td>
									<td width="80">${fixed.exchangeRate }</td>
									<td width="100"><fmt:formatNumber pattern="#,##0.00" value="${fixed.subtotalCosts}" /></td>
									<td width="100"><fmt:formatNumber pattern="#,##0.00" value="${fixed.subtotalCostsFc}" /></td>
									<td width="150">${fixed.supplierStr }</td>
									<td width="150">${fixed.targetStr }</td>
									<td width="80">${fixed.paymentTypeName }</td>
									<td width="80"><fmt:formatNumber pattern="#,##0.00" value="${fixed.payAmount}" /></td>
									<td width="80">${fixed.payStatusName }</td>
									<td width="150">${fixed.remark }</td>
									<td width="150">
										<s:if test="#fixed.payStatus == 'NOPAY' || #fixed.payStatus == 'PARTREQPAY' || #fixed.payStatus == 'PARTPAY'">
											<a href="#" onclick="requirePayHandler(2,${fixed.itemId},${fixed.supplierId},${fixed.subtotalCostsFc },'<fmt:formatNumber pattern="#,##0.00" value="${fixed.subtotalCostsFc}" />')" class="hidableClass">付款申请</a>
										</s:if>
										<s:if test="#fixed.payStatus == 'REQPAY'">
											<a href="#" onclick="delayPayHandler(2,${fixed.itemId})" class="hidableClass">延迟</a>
										</s:if>
										<a href="#" class="hidableClass editFixedHandlerClass" onclick="editFixedHandler(this)" item_id="${fixed.itemId}" costs_item="${fixed.costsItem}"
																			bg_costs="${fixed.bgCosts}" quantity="${fixed.quantity}" currency="${fixed.currency}" 
																			exchangeRate="${fixed.exchangeRate}" supplier_id="${fixed.supplierId}" supplier_name="${fixed.supplierName}"
																			target_id="${fixed.targetId}" target_name="${fixed.targetName}" payment_type="${fixed.paymentType}"
																			remark="${fixed.remark}" pay_status="${fixed.payStatus}">
															修改</a>
										<s:if test="#fixed.payStatus == 'NOPAY'">
											<a href="#" class="hidableClass" onclick="deleteFixedHandler(${fixed.itemId})">删除</a>
										</s:if>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<div class="t_c mt10">
						<input id="addFixedBtn" type="button" class="ml10 btn_sub btn_w100 hidableClass" value="添加成本项"/>
						<input type="button" class="ml10 btn_sub" value="操作日志" onclick="showLog()"/>
						<input type="button" class="ml10 btn_w100 btn_sub " value="打印收入成本表" onclick="printGroupCostIncome('${basePath}','<s:property value="travelGroupCode"/>')"/>
						<input type="button" class="ml10 btn_w100 btn_sub" value="打印结算单" onclick="printGroupSettle('${basePath}','<s:property value="travelGroupCode"/>')"/>
						<input type="button" class="ml10 btn_sub" value="返回" onclick="window.history.back();"/>
					</div>					
				</div>
			</div>
		</div>
	</div>
	
	<!-- 添加附加收入 -->
	<div id="addIncomingWin" class="p_rel rad5 pub_pop_wrap addition" style="display: none;">
		<ul class="data_list">
			<input id="incomingItemIdHd" type="hidden" />
			<li>
				<span class="tit">内容：</span>
				<select id="incomingFixedSlct">
				</select>
			</li>
			<li>
				<span class="tit">金额：</span>
				<input id="incomingAmountInpupt" type="text" name="">
			</li>
			<li>
				<span class="tit">备注：</span>
				<textarea id="incomingRemarkInpupt" cols="30" rows="2" name=""></textarea>
			</li>
			<li class="btn_area">
				<input id="saveIncomingBtn" type="button" value="确定" class="btn_but" name="">
			</li>
			<li></li>
		</ul>
	</div>
	
	<!-- 添加固定成本项窗口 -->
	<div id="addFixedWin" style="display: none;">
		<table cellspacing="0" cellpadding="0" border="0" 
			class="rad5 tabw100 blue_skin_tb" style="border:0px">
			<tr>
				<td>成本项：</td>
				<td>
					<select id="fixedSlct">
					</select>
				</td>
			</tr>
			<tr>
				<td>币种：</td>
				<td>
					<select id="currencySlct">
					</select>
				</td>
			</tr>
			<tr>
				<td>成本：</td>
				<td><input id="fixedPriceInput" type="text" name="" class="inp_w60"></td>
			</tr>
			<tr>
				<td>数量：</td>
				<td><input id="fixedNumInput" type="text" name="" class="inp_w60"></td>
			</tr>
			<tr>
				<td>供应商：</td>
				<td>
					<input id="supplierInput_val" type="hidden" 
						label="" value="" 
						name="supplierInput">
					<input id="supplierInput" type="text" name="" class="input_text02 table_input_style">
				</td>
			</tr>
			<tr>
				<td>结算对象:</td>
				<td>
					<select id="targetSlct"></select>
<!-- 					<input id="targetInput_val" type="hidden"  -->
<!-- 						label="" value=""  -->
<!-- 						name="targetInput"> -->
<!-- 					<input id="targetInput" type="text" class="input_text02 table_input_style"> -->
				</td>
			</tr>
			<tr>
				<td>付款方式:</td>
				<td>
					<select id="paymentTypeSlct">
						<option value="CASH">现金</option>
						<option value="TRANSFER">银行转帐</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>备注:</td>
				<td>
					<textarea id="fixedMemoInput" cols="30" rows="2" name=""></textarea>
				</td>
			</tr>		
			<tr>
				<td colspan="9">
					<div class="t_c mt10">
						<input id="saveFixedBtn" type="button" value="保存" class="btn_sub" />
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<!-- 延迟打款弹出窗口 -->
	<div id="delayPayWin" style="display: none;">
		<input id="delayType" type="hidden"/>
		<input id="delayId" type="hidden"/>
		<table cellspacing="0" cellpadding="0" border="0" 
			class="rad5 tabw100 blue_skin_tb" style="border:0px">
			<tr>
				<td>延迟打款时间：</td>
				<td>
					<input id="delayTimeInput" type="text" 
						onclick="WdatePicker({isShowClear:true,readOnly:true})" 
						class="input_text01 Wdate" />
				</td>
			</tr>
			<tr>
				<td>备注:</td>
				<td>
					<textarea id="delayMemoInput" cols="30" rows="2" name=""></textarea>
				</td>
			</tr>		
			<tr>
				<td colspan="9">
					<div class="t_c mt10">
						<input id="saveDelayBtn" type="button" value="确定" class="btn_sub"
							onclick="saveDelayHandler()" />
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<!-- 产品销售明细窗口 -->
	<div id="prdDetailWin" style="display: none;">
		<table id="prdDetailTbl"></table>
		<div id="prdPageDiv"></div>
	</div>
	
	<!-- 催款申请弹出窗口 -->
	<div id="reqPayWin" style="display: none;">
		<input id="reqType" type="hidden"/>
		<input id="reqId" type="hidden"/>
		<input id="subtotalCostsFc" type="hidden"/>
		<input id="reqPayAmountHd" type="hidden"/>
		<table cellspacing="0" cellpadding="0" border="0" 
			class="rad5 tabw100 blue_skin_tb" style="border:0px">
			<tr>
				<td>单项总成本：</td>
				<td>
					<span id="subtotalCostsSpan"></span>
				</td>
			</tr>
			<tr>
				<td>申请打款金额：</td>
				<td>
					<input id="reqPayAmountInput" type="text" 
						class="input_text01" />
				</td>
			</tr>
			<tr id="isUseAdvanceTr">
				<td>是否使用预存款付款：</td>
				<td>
					<input id="isUseAdvanceCkb" type="checkbox"/>
				</td>
			</tr>	
			<tr id="supAdvanceAmountTr">
				<td>该供应商有预存款：</td>
				<td>
					<span id="supAdvanceAmountSpan"></span>
				</td>
			</tr>	
			<tr>
				<td colspan="9">
					<div class="t_c mt10">
						<input id="saveReqPayBtn" type="button" value="确定" class="btn_sub"
							onclick="saveReqPayHandler()" />
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<!-- 修改人数窗口 -->
	<div id="editActPersonsWin" style="display:none;">
		<form id="editActPersonsForm" action="updateActPersons.do">
		<table cellspacing="0" cellpadding="0" border="0" 
			class="rad5 tabw100 blue_skin_tb" style="border:0px">
			<tr>
				<td>成人：</td>
				<td>
					<input id="actAdultInput" type="text" name="actAdult" class="input_text01" maxlength="10" 
						onchange="personNumChangeHandler()">
				</td>
			</tr>
			<tr>
				<td>儿童：</td>
				<td>
					<input id="actChildInput" type="text" name="actChild" class="input_text01" maxlength="10"
						onchange="personNumChangeHandler()">
				</td>
			</tr>
			<tr id="isUseAdvanceTr">
				<td>实际人数：</td>
				<td>
					<span id="actPersonsSpan"></span>人
				</td>
			</tr>	
			<tr>
				<td colspan="9">
					<div class="t_c mt10">
						<input id="saveActPersonsBtn" type="button" value="修改" class="btn_sub"
							onclick="savePersonNumHandler()">
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
	
	<!-- 日志窗口 -->
	<div id="logWin" style="display: none;">
		<table id="logTbl" />
	</div>
	<div id="printGroupCostWin" style="display:none;">
		<table id="printGroupCostTb1"/>
	</div>
</body>
</html>

