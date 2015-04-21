	<div class="wapper_accounts" style="width:700px; padding: 0px;">
		<div class="wapper_list wapper_list_cash">
			<h3 class="order_check">供应商基本信息</h3>
			<div class="cash_seach" style="margin: 0px;">
				<table width="100%" border="0" class="table_list01">
					<tr class="odd">
						<td align="right" width="15%">供应商名称：</td>
						<td align="left" width="35%"> ${supplierInfo.supplierName!""} </td>
						<td align="right" width="15%">固定电话：</td>
						<td align="left"width="35%"> ${supplierInfo.mobile!""} </td>
					</tr>
					<tr class="even">
						<td align="right">我方结算主体：</td>
						<td align="left"> ${supplierInfo.zhTargetName!""} </td>
						<td align="right">网址：</td>
						<td align="left"> ${supplierInfo.website!""} </td>
					</tr>
					<tr class="odd">
						<td align="right">地址：</td>
						<td align="left"> ${supplierInfo.address!""} </td>
						<td align="right">传真：</td>
						<td align="left"> ${supplierInfo.fax!""} </td>
					</tr>
					<tr class="even">
						<td align="right">邮编：</td>
						<td align="left"> ${supplierInfo.postCode!""} </td>
						<td align="right">手机：</td>
						<td align="left"> ${supplierInfo.telephone!""} </td>
					</tr>
					<tr class="odd">
						<td align="right">预存款余额：</td>
						<td align="left">
							<#if supplierInfo.advanceDepositAmountYuan?? && supplierInfo.advanceDepositAmountYuan != 0>
								${supplierInfo.advanceDepositAmountYuan?string(",###.00")} 
							</#if>
						</td>
						<td align="right">押金余额：</td>
						<td align="left">
							<#if supplierInfo.depositAmountYuan?? && supplierInfo.depositAmountYuan != 0>
								 ${supplierInfo.depositAmountYuan?string(",###.00")} 
							</#if>
						</td>
					</tr>
					<tr class="even">
						<td align="right">担保函额度：</td>
						<td align="left"> 
							<#if supplierInfo.guaranteeLimitYuan?? && supplierInfo.guaranteeLimitYuan != 0>
								 ${supplierInfo.guaranteeLimitYuan?string(",###.00")} 
							</#if>
						</td>
						<td align="right">预存款预警金额：</td>
						<td align="left">
							<#if supplierInfo.advanceDepositAlertYuan?? && supplierInfo.advanceDepositAlertYuan != 0>
								 ${supplierInfo.advanceDepositAlertYuan?string(",###.00")} 
							</#if>
						</td>
					</tr>
					<tr class="odd">
						<td align="right">押金回收时间：</td>
						<td align="left"> ${supplierInfo.depositAlertStr!""} </td>
						<td align="right"></td>
						<td align="left">  </td>
					</tr>
				</table>
			</div>
		</div>
		
		<div class="wapper_list wapper_list_cash">
			<h3 class="order_check">结算对象</h3>
			<div class="cash_seach" style="margin:0px;">
				<table class="table_list01" width="100%">
					<tr class="tr_title">
						<td align="center"><b>对象名称</b></td>
						<td align="center"><b>结算周期</b></td>
						<td align="center"><b>录入时间</b></td>
					</tr>
					<#list targetList as var>
						<tr class="<#if var_index %2 == 0 >odd<#else>even</#if>">
							<td align="center">${var.name!'' }</td>
							<td align="center">${var.zhSettlementPeriod!'' }</td>
							<td align="center">${var.createTimeStr!'' }</td>
						</tr>
		    		</#list>
				</table>
			</div>
		</div>
	</div>
