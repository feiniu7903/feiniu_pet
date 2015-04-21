<!--LOG记录-->
<div  class="jiesuan" style="width: 700px;">
	<table width="100%" border="0">
		<tr class="title_table cash_titie">
			<td>操作类型</td>
			<td>操作内容</td>
			<td>操作时间</td>
			<td>操作人</td>
		</tr>
		<#if logs?size =0 >
		<tr class="list_contant list_contant01">
			<td colspan="6">
				没有操作日志
			</td>
		</tr>
		<#else>
		<#list logs as var>
			<tr class="list_contant list_contant0<#if var_index%2 ==0>1<#else>2</#if>">
				<td class="list_contant_border">${var.logName!""}</td>
				<td class="list_contant_border">
					${var.content!""}
				</td>
				<td class="list_contant_border">${var.createTime?string('yyyy-MM-dd HH:mm')}</td>
				<td class="list_contant_border">${var.operatorName!""}</td>
			</tr>
		</#list>
		</#if>
	</table>
</div>