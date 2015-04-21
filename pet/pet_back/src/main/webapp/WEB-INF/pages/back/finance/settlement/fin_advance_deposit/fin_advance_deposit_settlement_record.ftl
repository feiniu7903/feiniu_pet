<!--流水记录-->
<div id='dariy_win' class="jiesuan" style="width: 700px;">
	<table width="100%" border="0">
		<tr class="title_table cash_titie">
			<td>结算金额（元）</td>
			<td>结算对象</td>
			<td>结算单号/团号</td>
			<td>结算时间</td>
		</tr>
		<#if result?size =0 >
		<tr class="list_contant list_contant01">
			<td colspan="6">
				没有结算记录
			</td>
		</tr>
		<#else>
		<#list result as var>
			<tr class="list_contant list_contant0<#if var_index%2==0>1<#else>2</#if>">
				<td class="list_contant_border">${var.amountYuan?string(",##0.00")}</td>
				<td class="list_contant_border">${var.supplierName}</td>
				<td class="list_contant_border">${var.settlementIdStr!""}${var.travelGroupCode!""}</td>
				<td class="list_contant_border">${var.operatetime?string('yyyy-MM-dd HH:mm')}</td>
			</tr>
		</#list>
		</#if>
	</table>
</div>