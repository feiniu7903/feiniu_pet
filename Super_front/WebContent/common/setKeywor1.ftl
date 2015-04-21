<#include "/common/header.ftl">
<@s.if test='product.isTicket=="2"'>
	<input type="hidden" value="hotel" id="pageName">
</@s.if>
<@s.else>
	<input type="hidden" value="ticketPage" id="pageName">
</@s.else>
<script type="text/javascript">
setKeyword('<@s.property value="keyword" />');
</script>


