<ul class="cprobig-list ">
  <@s.iterator value="recommendInfoForScenicList" status="st">
    <li>
        <a title="${title?if_exists}" target="_blank" class="text-cover auto_img" href="${url?if_exists}">
            <img src="${imgUrl?if_exists}" width="252" height="168" alt="${title?if_exists}" />
            <span></span>
            <i>${title?if_exists}</i>
        </a>
         <a target="_blank" class="link-other" href="http://www.lvmama.com/search/ticket/${fromPlaceName}-${title?if_exists}.html">相关产品</a> 
        <p><b>推荐理由：</b><@s.if test="remark!=null && remark.length()>55">
			<@s.property value="remark.substring(0,55)" escape="false"/>...
			</@s.if><@s.else>${remark?if_exists}
			</@s.else></p>
    </li>
    </@s.iterator>
</ul>
<p class="link-more"><a target="_blank" href="http://www.lvmama.com/search/ticket/${fromPlaceName?if_exists}-${paramDataSearchName?if_exists}.html">— — 查看更多 — —</a></p>
