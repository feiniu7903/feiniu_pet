
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
       <div class="from-city switch-city"><p class="city"><span class="css-arrow"><i></i></span>
        <i ></i><span id="cityId"><@s.if test="stationName!=fromPlaceName">请选择出发点</@s.if><@s.else>${fromPlaceName}</@s.else></span></p>
        <div class="citylist LISTSECOND">
            <h5>热门出发城市</h5>
                <dl>
                    <dd>
                        <a data-name="上海" data-id="79" data-code="SH" href="#">上海</a>
                        <a data-name="广州" data-id="229" data-code="GZ" href="#">广州</a>
                        <a data-name="深圳" data-id="231" data-code="SZ" href="#">深圳</a>
                        <a data-name="北京" data-id="1" data-code="BJ" href="#">北京</a>
                        <a data-name="成都" data-id="279" data-code="CD" href="#">成都</a>
                        <a data-name="南京" data-id="82" data-code="NJ" href="#">南京</a>
                        <a data-name="杭州" data-id="100" data-code="HZ" href="#">杭州</a>
                        <a data-name="宁波" data-id="104" data-code="NB" href="#">宁波</a>
                        <a href="#" data-id="107" data-name="温州" hidefocus="false">温州</a>
                    </dd>
                </dl>
        </div> 
    </div>
            <form class="form-search">
                <input type="text" x-webkit-speech placeholder="邮轮特惠"  class="input-search"><button type="button" class="btn-search xicon">搜索</button>
            </form>
            <span class="hot-travel">
                <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_TOPRECOMMEND')" status="st">
                 <a target="_blank"   href="${url?if_exists}" hidefocus="false">${title?if_exists}</a>
                </@s.iterator>
            </span>
</div> <!-- //搜索框区域 -->