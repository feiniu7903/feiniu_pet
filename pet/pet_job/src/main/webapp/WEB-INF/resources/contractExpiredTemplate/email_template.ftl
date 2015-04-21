<#if days==5>
您好，您的合同距到期日还有5天，请做好产品下线准备，若已经与供应商续签，请至super系统-合同管理中进行补充合同-顺延合同操作。
<#else>
您好，您的合同距到期日还有${days}天，请做好续签准备。
</#if>
<table style='border-collapse: collapse;border-spacing: 0;'>
	<tr>
		<td style='border:#000 solid 1px;line-height: 18px;padding: 3px 5px;'>合同编号</td>
		<td style='border:#000 solid 1px;line-height: 18px;padding: 3px 5px;'>供应商名称</td>
		<td style='border:#000 solid 1px;line-height: 18px;padding: 3px 5px;'>到期日</td>
		<td style='border:#000 solid 1px;line-height: 18px;padding: 3px 5px;'>合同采购产品经理</td>
	</tr>
	<#list supContractList as item>
	  <tr>    
	     <td style='border:#000 solid 1px;line-height: 18px;padding: 3px 5px;color:#1F497D;'>${item.contractNo?if_exists}</td>  
	     <td style='border:#000 solid 1px;line-height: 18px;padding: 3px 5px;color:#1F497D;'>${item.supplierName?if_exists}</td>   
	     <td style='border:#000 solid 1px;line-height: 18px;padding: 3px 5px;color:#1F497D;'>${item.endDate?string('yyyy-MM-dd')}</td>     
	     <td style='border:#000 solid 1px;line-height: 18px;padding: 3px 5px;color:#1F497D;'>${managerName?if_exists}</td>        
	  </tr>
	</#list>
</table>