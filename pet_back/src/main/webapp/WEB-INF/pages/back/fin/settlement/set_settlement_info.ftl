<#setting number_format="#.##"> 
<!--结算单确认-->
<div id="confirm_div" >
	<#if settlement??>
	<form id="info_form" method="post" >
		<input  id = "settlementId" type="hidden" name="settlementId"  value="${settlement.settlementId}"/>
		<table width="100%" border="0" class="table_list01">
			<tr class="tr_title" >
				<td colspan="4">结算单信息</td>
			</tr>
			<tr class="odd">
				<td align="center">结算单号：</td>
				<td id="td_targetName">
					${settlement.settlementId?string('#')}
					<input id="target_name" type="hidden" value="${settlement.settlementId}">
				</td>
				<td align="center">结算周期：</td>
				<td id="td_settlementId">
					<#if settlement.settlementPeriodStr == initalSettlement.settlementPeriodStr >
						${settlement.settlementPeriodStr}
					<#else>
						<font color='red'>${settlement.settlementPeriodStr}(${initalSettlement.settlementPeriodStr})</font>
					</#if>
				</td>
			</tr>
			<tr class="even">
				<td align="center">结算对象：</td>
				<td id="td_targetName">${settlement.targetName}</td>
				<td align="center" >我方结算主体：</td>
				<td id="td_settlementId">${settlement.companyIdStr}</td>
			</tr>
			<tr class="odd">
				<td align="center">已打款金额：</td>
				<td id="td_payAmount">${settlement.payedAmountYuan?string(",##0.00")}</td>
				<td align="center">抵扣款使用金额：</td>
				<td id="td_payedAmount">${settlement.deductionAmountYuan?string(",##0.00")}</td>
			</tr>
			<tr class="even">
				<td align="center">应结金额：</td>
				<td id="td_payAmount" colspan="3">${settlement.settlementAmountYuan?string(",##0.00")}</td>
			</tr>
			<tr class="even">
				<td width="90px" align="center">备&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
				<td colspan="3">
				<#if type == 0>
					<div style="width: 500px;">
						${settlement.memo!""} 
					</div>
				<#else>
					<textarea name="memo" cols="" rows="" class="jiesuan_area">${settlement.memo!""}</textarea></td>
				</#if>
			</tr>
		</table>
		
		<div class="wapper_accounts" style="width: 100%;padding-left: 0px;padding-bottom: 5px;padding-top: 5px;padding-right: 0px;">
			<ul class="add_tap_list">
				<li id="fixInfoTab" class="current" style="cursor: pointer;">固化对象信息 </li>
				<li id="initalInfoTab" class="other_26" style="cursor: pointer;">原始对象信息</li>
			</ul>
			<div class="wapper_list wapper_listadd_26" style="padding-bottom: 0px;">
				<div class="order_top order_top_26_add current01" >
					<div id="fixInfo" style="display: block;">
						<table style="width: 100%;" class="table_list01">
							<tr>
								<td align="center" width="80px">联系人名：</td>
								<td width="200px" id="contactName">${settlement.contact.name!""}</td>
								<td align="center" width="90px">职务：</td>
								<td width="130px" id="contactTitle">${settlement.contact.title!""}</td>
							</tr>
							<tr>
								<td width="80px" align="center">开户名称：</td>
								<td width="200px" id="bankAccountName">${settlement.bankAccountName!""}</td>
								<td width="90px" align="center">电话：</td>
								<td width="130px" id="contactTelephone">${settlement.contact.telephone!""}</td>
							</tr>
							<tr>
								<td width="80px" align="center">开户银行：</td>
								<td width="200px" id="bankName">${settlement.bankName!""}</td>
								<td width="80px" align="center">开户帐号：</td>
								<td width="200px" id="bankAccount">${settlement.bankAccount!""}</td>
							</tr>
							<tr>
								<td width="90px" align="center">支付宝用户名：</td>
								<td width="130px" id="alipayName">${settlement.alipayName!""}</td>
								<td width="80px" align="center">支付宝帐号：</td>
								<td width="200px" id="alipayAccount" >${settlement.alipayAccount!""}</td>
							</tr>
							<tr>
								<td align="center" colspan="4">
									<#if !(settlement.payedAmount?? && settlement.payedAmount&gt;0) ><a id="refresh" href= "javascript:void(0);">[刷  新]</a></#if>
								</td>
							</tr>
						</table>
					</div>
					<div id="initalInfo" style="display: none;">
						<table style="width: 100%;" class="table_list01">
							<tr>
								<!-- <td align="center">结算帐号：</td>
								<td>1001746409006702760</td> -->
								<td width="80px" align="center">联系人名：</td>
								<td width="200px">${initalSettlement.contact.name!""}</td>
								<td width="90px" align="center">职务：</td>
								<td width="130px">
									${initalSettlement.contact.title!""}
									<input id="initalSettlementId" type="hidden" value="${initalSettlement.settlementId}" />
								</td>
							</tr>
							<tr>
								<td width="80px" align="center">开户名称：</td>
								<td width="200px">${initalSettlement.bankAccountName!""}</td>
								<td width="90px" align="center">电话：</td>
								<td width="130px">${initalSettlement.contact.telephone!""}</td>
							</tr>
							<tr>
								<td width="80px" align="center">开户银行：</td>
								<td width="200px">${initalSettlement.bankName!""}</td>
								<td width="80px" align="center">开户帐号：</td>
								<td width="200px">${initalSettlement.bankAccount!""}</td>
							</tr>
							<tr>
								<td width="90px" align="center">支付宝用户名：</td>
								<td width="130px">${initalSettlement.alipayName!""}</td>
								<td width="80px" align="center">支付宝帐号：</td>
								<td width="200px" colspan="3">${initalSettlement.alipayAccount!""}</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<#if type == 1>
			<#if settlement.status == "SETTLEMENTED">
				<div style="color: red;padding-top: 20px;">
					结算单已结算，请刷新列表。
				</div>
			<#elseif settlement.settlementAmountYuan &gt; (settlement.payedAmountYuan+settlement.deductionAmountYuan)>
				<div style="color: red;padding-top: 20px;">
					已结算金额小于应结金额，不能进行结算。
				</div>
			<#else>
				<div class="popups_button" style="margin-top: 10px;">
					<input type="submit" class="left_bt" value="确 定" />
				</div>
			</#if>
		</#if>
	</form>
	<#else>
	结算单获取失败！
	</#if>
</div>
