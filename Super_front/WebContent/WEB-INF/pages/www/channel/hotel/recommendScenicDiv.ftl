<ul class="hotel-list">
   <@s.iterator value="recommendInfoForScenicList" status="st">
    <li>
        <a target="_blank" class="text-cover" href="${url?if_exists}">
            <img src="${imgUrl?if_exists}" width="200" height="130" alt="${title?if_exists}" />
            <span></span>
            <i>${title?if_exists}</i>
        </a>
        <p class="rank">
            <dfn>&yen;<i>${memberPrice?if_exists}</i>起</dfn>
            <#include "/WEB-INF/pages/www/channel/hotel/star.ftl">
        </p>
        <p class="info"><@s.if test="remark!=null && remark.length()>31"><@s.property value="remark.substring(0,31)" escape="false"/>...
			</@s.if><@s.else>${remark?if_exists}</@s.else></p>
        <p class="local">
            <a target="_blank" class="link-more" href="http://www.lvmama.com/search/hotel/${fromPlaceName?if_exists}-${bakWord2?if_exists}.html">更多酒店 &raquo;</a>
            <span class="icon-local"></span>${bakWord2?if_exists}
        </p>
    </li>
    </@s.iterator> 
</ul>
<p class="link-more"><a target="_blank" href="http://www.lvmama.com/search/hotel/${fromPlaceName?if_exists}-${paramDataSearchName?if_exists}.html">— — 查看${paramDataSearchName?if_exists}更多 — —</a></p>
