<#include "/WEB-INF/pages/common/header.ftl">
<@s.action name="navigation" namespace="/place" executeResult="true">
		<@s.param name="fromDestId" value="fromDestId"></@s.param>
		<@s.param name="id"><@s.if test="place==null"><@s.property value="currentPlace.placeId"/></@s.if><@s.else><@s.property value="place.placeId"/></@s.else></@s.param>
</@s.action>
<input type="hidden" value="placePage" id="pageName">
<script type="text/javascript">
//setKeyword('<@s.property value="keyword" />');
</script>
