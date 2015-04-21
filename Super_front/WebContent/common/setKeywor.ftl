<!--head start-->
<#include "/common/header.ftl">
<@s.if test="prodCProduct!=null&&prodCProduct.prodRoute!=null">
	<@s.if test='prodCProduct.prodProduct.isGroup()'>
		<input name="pageName" type="hidden" value="route" id="pageName">
	</@s.if>
	<@s.elseif test='prodCProduct.prodProduct.isForeign()'>
		<input name="pageName" type="hidden" value="internat" id="pageName">
	</@s.elseif>
	<@s.elseif test='prodCProduct.prodProduct.isFreeness()'>
		<input name="pageName" type="hidden" value="guide" id="pageName">
	</@s.elseif>
	<@s.else>
		<input name="pageName" type="hidden" value="ticketPage" id="pageName">
	</@s.else>
</@s.if>

<@s.elseif test="prodCProduct!=null &&prodCProduct.prodHotel != null">
<input name="pageName" type="hidden" value="hotel" id="pageName">
</@s.elseif>
<@s.elseif test="prodCProduct!=null">
<input name="pageName" type="hidden" value="ticketPage" id="pageName">
</@s.elseif>

