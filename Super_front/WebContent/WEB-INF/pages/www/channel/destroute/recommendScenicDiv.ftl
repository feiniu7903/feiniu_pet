<ul class="cprobig-list ">
  <@s.iterator value="recommendInfoForScenicList" status="st">
    <li>
        <a title="${title?if_exists}" target="_blank" class="text-cover auto_img" href="${url?if_exists}">
            <img src="${imgUrl?if_exists}" width="252" height="168" alt="${title?if_exists}" />
            <span></span>
            <i>${title?if_exists}</i>
        </a>
        <p><b>推荐理由：</b><@s.if test="remark!=null && remark.length()>55">
			<@s.property value="remark.substring(0,55)" escape="false"/>...
			</@s.if><@s.else>${remark?if_exists}
			</@s.else></p>
    </li>
    </@s.iterator>
</ul>
<p class="link-more"><a rel="nofollow" target="_blank" href="http://www.lvmama.com/search/route/${fromPlaceName?if_exists}-${paramDataSearchName?if_exists}.html">— — 查看${paramDataSearchName?if_exists}更多 — —</a></p>
