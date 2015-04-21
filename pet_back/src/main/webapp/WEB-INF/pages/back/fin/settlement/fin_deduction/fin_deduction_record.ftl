<!--流水记录-->
<div id='dariy_win' class="jiesuan" style="width: 700px;">
	<table width="100%" border="0">
		<tr class="title_table cash_titie">
			<td style="width: 100px">日期</td>
			<td>结算单号/团号</td>
			<td style="width: 100px">金额</td>
			<td style="width: 120px">类型</td>
			<td style="width: 120px">操作人</td>
		</tr>
		<#if finDeductionPage.items?size =0 >
			<tr class="list_contant list_contant01">
				<td colspan="6">
					没有流水记录
				</td>
			</tr>
		<#else>
		<#list finDeductionPage.items as var>
			<tr class="list_contant list_contant0<#if var_index%2 ==0>1<#else>2</#if>">
				<td class="list_contant_border">${var.createtime?string('yyyy-MM-dd HH:mm')}</td>
				<td class="list_contant_border">${var.objectId!""}</td>
				<td class="list_contant_border">${var.amountYuan?string(",##0.00")}</td>
				<td class="list_contant_border">${var.zhType!""}</td>
				<td class="list_contant_border">${var.creator!""}</td>
			</tr>
		</#list>
		  <tr>
			<td>总条数：${finDeductionPage.totalResultSize}</td>
			<td id = "paginationTD" colspan="7" align="right">
				<div id="paginationDiv">${paginationHtml}</div>
			</td>
		  </tr>
		</#if>
	</table>
</div>
