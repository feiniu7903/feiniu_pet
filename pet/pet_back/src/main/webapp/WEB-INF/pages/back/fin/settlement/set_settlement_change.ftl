<#setting number_format="#.##"> 
<!--流水记录-->
<div id='dariy_win' class="jiesuan" style="width: 700px;">
	<table width="100%" border="0">
		<tr class="title_table cash_titie">
			<td>用户名</td>
			<td>操作</td>
			<td>订单号</td>
			<td>采购产品ID</td>
			<td>操作前金额(元)</td>
			<td>操作后金额(元)</td>
			<td>时间</td>
			<td>备注</td>
		</tr>
		<#if settlementChangePage.items?size =0 >
		<tr class="list_contant list_contant01">
			<td colspan="6">
				没有流水记录
			</td>
		</tr>
		<#else>
		<#list settlementChangePage.items as var>
			<tr class="list_contant list_contant0<#if var_index%2 ==0>1<#else>2</#if>">
				<td class="list_contant_border">${var.creator!""}</td>
				<td class="list_contant_border">
					${var.changetypeName}
				</td>
				<td class="list_contant_border">${var.orderId!""}</td>
				<td class="list_contant_border">${var.metaProductId!""}</td>
				<td class="list_contant_border">${var.amountBeforeChangeYuan?string(",##0.00")}</td>
				<td class="list_contant_border">${var.amountAfterChangeYuan?string(",##0.00")}</td>
				<td class="list_contant_border">${var.createtime?string('yyyy-MM-dd HH:mm')}</td>
				<td class="list_contant_border">${var.remark!""}</td>
			</tr>
		</#list>
		<tr>
			<td>总条数：${settlementChangePage.totalResultSize}</td>
			<td id = "paginationTD" colspan="7" align="right">
				<div id="paginationDiv">${paginationHtml}</div>
			</td>
		  </tr>
		</#if>
	</table>
</div>