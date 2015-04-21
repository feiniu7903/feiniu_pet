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
		<td width="130">行前须知：</td>
		<td>
			<textarea rows="10" style="width: 500px" readonly="readonly">${ebkProdContentMap.ACITONTOKNOW}</textarea>
			 <s:if test="compareEbkProductOther.containsKey('ACITONTOKNOW')">
			 	<span class="tip_text" tip-title="老数据:" tip-content="${compareEbkProductOther.ACITONTOKNOW}">审</span>
			 </s:if>
		</td>
	</tr>
	<s:if test="ebkProdProduct.productType=='SURROUNDING_GROUP' or ebkProdProduct.productType=='ABROAD_PROXY'">
		<tr>
			<td width="130">推荐项目：</td>
			<td>
				<textarea rows="10" style="width: 500px" readonly="readonly">${ebkProdContentMap.RECOMMENDPROJECT}</textarea>
				<s:if test="compareEbkProductOther.containsKey('RECOMMENDPROJECT')">
				 	<span class="tip_text" tip-title="老数据:" tip-content="${compareEbkProductOther.RECOMMENDPROJECT}">审</span>
				</s:if>
			</td>
		</tr>
	</s:if>
	<s:if test="ebkProdProduct.productType=='ABROAD_PROXY'">
		<tr>
			<td width="130">预订须知：</td>
			<td>
				<textarea rows="10" style="width: 500px" readonly="readonly">${ebkProdContentMap.ORDERTOKNOWN}</textarea>
				<s:if test="compareEbkProductOther.containsKey('ORDERTOKNOWN')">
				 	<span class="tip_text" tip-title="老数据:" tip-content="${compareEbkProductOther.ORDERTOKNOWN}">审</span>
				</s:if>
			</td>
		</tr>
	</s:if>
	<s:if test="ebkProdProduct.productType=='ABROAD_PROXY'">
		<tr>
			<td width="130">购物说明：</td>
			<td>
				<textarea rows="10" style="width: 500px" readonly="readonly">${ebkProdContentMap.SHOPPINGEXPLAIN}</textarea>
				<s:if test="compareEbkProductOther.containsKey('SHOPPINGEXPLAIN')">
				 	<span class="tip_text" tip-title="老数据:" tip-content="${compareEbkProductOther.SHOPPINGEXPLAIN}">审</span>
				</s:if>
			</td>
		</tr>
	</s:if>
</table>