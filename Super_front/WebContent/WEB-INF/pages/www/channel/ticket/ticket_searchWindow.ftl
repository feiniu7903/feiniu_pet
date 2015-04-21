
<!-- 搜索框区域\\ -->
<div data-city-name="${fromPlaceName}" data-city-id="${fromPlaceId}" data-source="home" class="search-box wrap">
        <div class="incity switch-city">
                <p class="city">
                    <a class="switch-info">切换城市</a>
                    <i class="icon-local"></i>
                    <span>您现在
                    <b>${stationName}</b>
                    </span>
                </p>
                <div class="citylist LISTFIRST">
                    <#include "/WEB-INF/pages/www/channel/stationDiv.ftl" />
                </div>
            </div>    
            <form class="form-search">
                <input type="text" x-webkit-speech placeholder="目的地/景点/酒店/主题"  class="input-search search-wbig"><button type="button" class="btn-search xicon">搜索</button>
            </form>
            <span class="hot-travel">
                <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_TOPRECOMMEND')" status="st">
                 <a target="_blank"   href="${url?if_exists}" hidefocus="false">${title?if_exists}</a>
                </@s.iterator>
            </span>
</div> <!-- //搜索框区域 -->