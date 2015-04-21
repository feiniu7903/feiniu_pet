<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		$('.tip_text').ui('lvtip',{
		    hovershow: 200
	    });
	});
</script>
<table class="newfont06" border="0"  cellpadding="0"  >
	<input type="hidden" name="ebkProdProductId" value="ebkProdProduct.ebkProdProductId"/>
	<tr>
		<td width="130">费用包含：</td>
		<td>
			<textarea rows="6" style="width: 450px" readonly="readonly">${ebkProdContentMap.COSTCONTAIN}</textarea>
			 <s:if test="compareEbkProductCost.containsKey('costcontain')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="${compareEbkProductCost.costcontain}">审</span>
			 </s:if>
		</td>
	</tr>
	<tr>
		<td width="130">费用不包含：</td>
		<td>
			<textarea rows="6" style="width: 450px" readonly="readonly">${ebkProdContentMap.NOCOSTCONTAIN}</textarea>
			<s:if test="compareEbkProductCost.containsKey('nocostcontain')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="${compareEbkProductCost.nocostcontain}">审</span>
			</s:if>
		</td>
	</tr>
</table>