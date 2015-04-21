<div class="lv-nav wrap">
<p>
<#list navList as nav>
	<#if nav_index != 0 >&gt;</#if><a href="http://www.lvmama.com/myspace/${nav.url}.do">${nav.title}</a>
</#list>
</p>
</div>