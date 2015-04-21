
<#macro pagination currentPage  totalPage url>
<p>
<#if totalPage!=1>
	<#if currentPage==1>
		<a href="${url}page=${currentPage+1}">下一页</a>
	</#if>
	<#if (currentPage>1&&currentPage<totalPage) >
		<a href="${url}page=${currentPage+1}">下一页</a>
		<a href="${url}page=${currentPage-1}">上一页</a>
	</#if>
	<#if currentPage==totalPage>
		<a href="${url}page=${currentPage-1}">上一页</a>
	</#if>
	页码:${currentPage}/${totalPage}
	<#else>
	页码:${currentPage}/${totalPage}
</#if>
</p>
</#macro>