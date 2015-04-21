<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>团预算表</title>
		<jsp:include page="head.jsp"></jsp:include>
		<script type="text/javascript" src="../js/op/accounting.min.js"></script>
		<script type="text/javascript" src="../js/op/groupBudget/budget.js"></script>
	</head>
	<body>
		<!-- 团号 -->
		<input id="groupCodeHd" type="hidden"
			value="<s:property value="#request.group.travelGroupCode" />"/>
		<!-- 团销售单价 -->
		<input id="groupSalePriceHd" type="hidden"
			value="<s:property value="#request.group.sellPrice / 100"  />"/>
		<!-- 团结算状态:UNBUDGET-未做预算；BUDGETED-已做预；COSTED-已做实际成本；CHECKED – 已核算 -->
		<input id="groupSettlementStatusHd" type="hidden"
			value="<s:property value="#request.group.settlementStatus" />"/>
		<input id="visitTimeHd" type="hidden"
			value="<s:property value="#request.group.visitTimeNum"/>"/>
			
		<div class="wapper_accounts">
		<div class="rad5 wapper_list wapper_list_cash">
			<h3 class="order_check">团预算[团号：<s:property value="#request.group.travelGroupCode"/>]</h3>
			<div class="tb_model_cont">
				<ul class="order_top_list ">
					<li class="other_list hidableClass">
						<label class="data_list">请输入预计人数：</label>
						<input id="bgPersonsInput" type="text" name="bgPersons" class="input_text02" maxlength="10"   
							<s:if test="#request.groupBudget != null">
								value="<s:property value="#request.groupBudget.bgPersons"/>"
							</s:if>
						/>
					</li>
					<li style="width:450px;">
						<input id="countBtn" type="button" value="计算" class="btn_sub hidableClass">
						<input id="saveBudgetBtn" type="button" value="保存" class="ml10 btn_sub hidableClass">
						<input id="addFixedBtn" type="button" value="添加成本项" class="ml10 btn_sub btn_w100 hidableClass">
						<input id="showLogBtn" type="button" value="操作日志" class="ml10 btn_sub" onclick="showLog()">
						<input type="button" class="ml10 btn_sub" value="返回" onclick="goBack();"/>
						<!-- 是否已点击保存按钮的标识,已点击为'true',未点击为'false' -->
						<input type="hidden" id="saveFlag" />
					</li>
				</ul>
				<table cellspacing="0" cellpadding="0" border="0" class="rad5 tabw100 blue_skin_tb">
					<thead>
						<tr>
							<th>预计人数</th>
							<th>销售价</th>
							<th>预计总成本</th>
							<th>人均成本</th>
							<th>预计收入</th>
							<th>预计毛利润</th>
							<th>预计毛利率</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<span id="bgPersonsNumSp"><s:if test="#request.groupBudget != null"><s:property value="#request.groupBudget.bgPersons"/></s:if></span>
								人
							</td>
							<td>
								<span id="salePriceSp">
									<fmt:formatNumber pattern="#,##0.00" value="${group.sellPrice / 100}" />
								</span>
							</td>
							<td>
								<span id="bgTotalCostsSp">
									<fmt:formatNumber pattern="#,##0.00" value="${groupBudget.bgTotalCosts}" />
								</span>
							</td>
							<td>
								<span id="bgPerCostsSp">
									<fmt:formatNumber pattern="#,##0.00" value="${groupBudget.bgPerCosts}" />
								</span>
							</td>
							<td>
								<span id="bgIncomingSp">
									<fmt:formatNumber pattern="#,##0.00" value="${groupBudget.bgIncoming}" />
								</span>
							</td>
							<td>
								<span id="bgProfitSp">
									<fmt:formatNumber pattern="#,##0.00" value="${groupBudget.bgProfit}" />
								</span>
							</td>
							<td>
								<span id="bgProfitRateSp">
									<s:if test="#request.groupBudget != null">
		 								<s:i18n name="format">
											<s:text name="format.number.money">
												<s:param value="#request.groupBudget.bgProfitRate * 100"/>
											</s:text>
										</s:i18n>
									</s:if>
								</span>
								%
							</td>
						</tr>
					</tbody>
				</table>
				<h3>团预算项目：</h3>
				<div class="tb_sort_cont">
					<h4>产品成本</h4>
					<table id="productTbl" >
						
					</table>
					<h4>固定成本</h4>
					<table id="fixedTbl" >
						
					</table>
					
				</div>
			</div>
		</div>
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
				<td>结算对象：</td>
				<td>
					<select id="targetSlct"></select>
				</td>
			</tr>
			<tr>
				<td>付款方式：</td>
				<td>
					<select id="paymentTypeSlct">
						<option value="CASH">现金</option>
						<option value="TRANSFER">银行转帐</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>备注：</td>
				<td>
					<textarea id="fixedMemoInput" cols="30" rows="2" name=""></textarea>
				</td>
			</tr>		
			<tr>
				<td colspan="9">
					<div class="t_c mt10">
						<input id="saveFixedBtn" type="button" value="保存" class="btn_sub">
						<input id="saveFixedAndContinueBtn" type="button" value="保存并继续" class="ml10 btn_sub btn_w100">
					</div>
				</td>
			</tr>
		</table>
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
						class="input_text01">
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
							onclick="saveReqPayHandler()">
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
						class="input_text01 Wdate">
				</td>
			</tr>
			<tr>
				<td>备注：</td>
				<td>
					<textarea id="delayMemoInput" cols="30" rows="2" name=""></textarea>
				</td>
			</tr>		
			<tr>
				<td colspan="9">
					<div class="t_c mt10">
						<input id="saveDelayBtn" type="button" value="确定" class="btn_sub">
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<!-- 日志窗口 -->
	<div id="logWin" style="display: none;height: 400px;">
		<table id="logTbl" />
	</div>
	</body>
</html>
