<!--流水记录-->
<div id='dariy_win' class="jiesuan" style="width: 700px;">
	<table width="100%" border="0">
		<tr class="title_table cash_titie">
			<td style="width: 80px">用户名</td>
			<td style="width: 80px">操作</td>
			<td style="width: 120px">来源</td>
			<td style="width: 100px">金额(元)</td>
			<td style="width: 150px">时间</td>
			<td width="200px">备注</td>
		</tr>
		<#if finForegiftPage.items?size =0 >
		<tr class="list_contant list_contant01">
			<td colspan="6">
				没有流水记录
			</td>
		</tr>
		<#else>
		<#list finForegiftPage.items as var>
			<tr class="list_contant list_contant0<#if var_index%2 ==0>1<#else>2</#if>">
				<td class="list_contant_border">${var.creator!""}</td>
				<td class="list_contant_border">
					<#if var.type == 'DEPOSIT'>
						存入
					<#elseif var.type == 'SHIFTIN'>
						预存款转入
					<#elseif var.type == 'SHIFTOUT'>
						押金转出
					<#elseif var.type == 'RETURN'>
						退回
					<#elseif var.type == 'REVISION'>
						冲正
					</#if>
				</td>
				<td class="list_contant_border">${var.bank!""}</td>
				<td class="list_contant_border">${var.amountYuan?string(",##0.00")}</td>
				<td class="list_contant_border">${var.operatetimeStr!""}</td>
				<td class="list_contant_border">${var.remark!""}</td>
			</tr>
		</#list>
		  <tr>
			<td>总条数：${finForegiftPage.totalResultSize}</td>
			<td id = "paginationTD" colspan="7" align="right">
				<div id="paginationDiv">${paginationHtml}</div>
			</td>
		  </tr>
		</#if>
	</table>
</div>