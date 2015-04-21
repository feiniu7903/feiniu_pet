<#macro showViewContent view_content_type tag="dd" prefix=''>
<#if viewPage.contents?? && viewPage.hasContent(view_content_type)>
<#list viewPage.contents.get(view_content_type).content?split("\n") as item>
<${tag}>${prefix}${item}</${tag}>
</#list>
</#if>
</#macro>

<#--
	显示一个产品描述信息，
	@param view_content_type 对应的描述信息类型
	@param title			  对应的描述信息的标题
-->
<#macro showViewContentTemplate view_content_type title showTitle=true>
<#if viewPage.contents?? && viewPage.hasContent(view_content_type)>
<dl>
<#if showTitle>
    <#if title!0 gt 0><dt>${title}</dt></#if>
</#if>
    <@showViewContent view_content_type/>
    <#nested/>
</dl>
</#if>
</#macro>
