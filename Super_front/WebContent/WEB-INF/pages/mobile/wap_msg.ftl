<#if (Request.messages)??>
	${Request.messages?if_exists}
<#elseif (Request.errorMessages)??>
	${Request.errorMessages?if_exists}
</#if>
