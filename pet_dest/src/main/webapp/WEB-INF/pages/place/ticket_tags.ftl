<#if tagGroupMap?? && tagGroupMap.get("抵扣")?? >
	<#assign dikou_description = '' />
	<#assign dikou_css= '' />
	<#list tagGroupMap.get("抵扣") as t>
			<#assign dikou_description = dikou_description + '<span>' +t.tagName +'</span><br/>' +t.description  />
			<#if (t_has_next)><#assign dikou_description = dikou_description  + '<br/><br/>' /></#if> 
			<#assign dikou_css= t.cssId />
	</#list>
	<span tip-content="${dikou_description}" class="${dikou_css}">抵扣</span>
</#if>
<#assign youhui_description = '<b>可以享受以下优惠</b><br/>' />
<#assign youhui_css= '' />
<#if tagGroupMap?? && tagGroupMap.get("优惠")?? >
	<#list tagGroupMap.get("优惠") as t>
		<#assign youhui_description = youhui_description  +'<span>'+t.tagName  +'</span>'/>
		<#if (t_has_next)><#assign youhui_description = youhui_description  +' | '  /></#if> 
		<#assign youhui_css= t.cssId />
	</#list>
	<span tip-content="${youhui_description}" class="${youhui_css}">优惠</span>
</#if>
<#if tagList??>
<#list tagList as t>
	<#if t.tagGroupName!='优惠' && t.tagGroupName!='抵扣'><span <#if t.description!="" >tip-content="${t.description}"</#if>class="${t.cssId}">${t.tagName}</span></#if>
</#list>
</#if>