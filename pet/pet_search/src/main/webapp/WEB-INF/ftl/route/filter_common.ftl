<span class="priceform">
    <form class="J_price" action="${base_url}<@fp filter="${filterStr}" type="K,O" val="1,1" repeat=true s=true/>.html#list">
        <input type="text" val ="${searchvo.startPrice}" value="${searchvo.startPrice!"最低价"}" class="input-text"> - <input type="text" val ="${searchvo.endPrice}" value="${searchvo.endPrice!"最高价"}" class="input-text">
        <input type="button" class="button" value="确定">
    </form>
</span>
<#if searchvo.promotion == "1" >
 	<span class="checkbox"><a rel="nofollow" class="selected" href="${base_url}<@fp filter="${filterStr}" type="V"  remove=true/>.html">促销活动</a></span>
 <#else>
 	<span class="checkbox"><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="V" val ="1" />.html">促销活动</a></span>
 </#if>
<div class="result-search">
    	<input  type="text" class="input-text input-result-search" val="${searchvo.keyword2}" value="${searchvo.keyword2!""}"><input url="${base_url}<@fp filter="${filterStr}" type="Q" val="1" s=true/>.html#list" type="button" class="button" value="搜索">
</div>