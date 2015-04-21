<#list pageConfig.items as item_hotel>
    <div class="items clearfix">
        <#if item_hotel.groupProductNum gt 0 ><i class="tuan-ico"></i></#if>
        <a class="tuan-see" href="http://www.lvmama.com/hotel/v${item_hotel.id}" target="_blank" rel="nofollow" onclick="coremetricsClick('${searchvo.fromDest}_${searchvo.keyword}_${type}_${pageConfig.currentPage}-${item_hotel_index+1}_${item_hotel.id}');"></a>
        <div class="tuan-head">
            <h2><a  href="http://www.lvmama.com/hotel/v${item_hotel.id}"  target="_blank" onclick="coremetricsClick('${searchvo.fromDest}_${searchvo.keyword}_${type}_${pageConfig.currentPage}-${item_hotel_index+1}_${item_hotel.id}');">
                 <#if item_hotel.name?length gt 30 >${item_hotel.name?substring(0,30)}<#else>${item_hotel.name}</#if>
            </a></h2>
            <div class="tuan-head-star">
                <span>${item_hotel.enName?if_exists}</span>
                <!--星钻-->
                <#if item_hotel.hotelStar == '1'><span class="tuan-diamond <#if !item_hotel.enName?? || item_hotel.enName=="">vm-tuan</#if>" title="经济型（驴妈妈用户评定为1.5钻）"><i style="width:30%" ></i></span></#if> 
                <#if item_hotel.hotelStar == '2'><span class="tuan-star <#if !item_hotel.enName?? || item_hotel.enName=="">vm-tuan</#if>" title="国家旅游局评定为二星级"><i style="width:40%" ></i></span></#if> 
                <#if item_hotel.hotelStar == '3'><span class="tuan-diamond <#if !item_hotel.enName?? || item_hotel.enName=="">vm-tuan</#if>" title="舒适型（驴妈妈用户评定为2.5钻）"><i style="width:50%" ></i></span></#if> 
                <#if item_hotel.hotelStar == '4'><span class="tuan-star <#if !item_hotel.enName?? || item_hotel.enName=="">vm-tuan</#if>" title="国家旅游局评定为三星级"><i style="width:60%" ></i></span></#if> 
                <#if item_hotel.hotelStar == '5'><span class="tuan-diamond <#if !item_hotel.enName?? || item_hotel.enName=="">vm-tuan</#if>" title="高档型（驴妈妈用户评定为3.5钻）"><i style="width:70%" ></i></span></#if> 
                <#if item_hotel.hotelStar == '6'><span class="tuan-star <#if !item_hotel.enName?? || item_hotel.enName=="">vm-tuan</#if>" title="国家旅游局评定为四星级"><i style="width:80%" ></i></span></#if> 
                <#if item_hotel.hotelStar == '7'><span class="tuan-diamond <#if !item_hotel.enName??  || item_hotel.enName=="">vm-tuan</#if>" title="豪华型（驴妈妈用户评定为4.5钻）"><i style="width:90%" ></i></span></#if> 
                <#if item_hotel.hotelStar == '8'><span class="tuan-star <#if !item_hotel.enName??  || item_hotel.enName=="">vm-tuan</#if>" title="国家旅游局评定为五星级"><i style="width:100%" ></i></span></#if>
            </div>
        </div>
        <!--主题-->
        <div class="tuan-tags">
            <span class="addr">[${item_hotel.province}<em>&bull;</em>${item_hotel.city}]</span>
            <span class="tuan-tagslist">
            <#if item_hotel.prodTopicList?? && item_hotel.prodTopicList?size gt 0>
                  <#list item_hotel.prodTopicList as pt>
                      <#if pt_index lt 8>
                            <#if searchvo.prodTopics?? && searchvo.prodTopics?contains(pt)>
                                <a href="javascript:void(0);" rel="nofollow" >${pt}</a>
                            <#else>
                                <a href="${base_url}<@fp filter="${filterStr}" type="J" val="${pt}" single=false />.html#list" rel="nofollow" >${pt}</a>
                            </#if>
                      <#else>
                            ...
                      </#if>
                  </#list>
             </#if>
            <#if item_hotel.hotelTopicList?? && item_hotel.hotelTopicList?size gt 0>
                  <#list item_hotel.hotelTopicList as ht>
                     <#if ht_index lt 8>
                        <#if searchvo.hotelTopics?? && searchvo.hotelTopics?contains(ht)>
                            <a href="javascript:void(0);"  rel="nofollow" >${ht}</a>
                        <#else>
                            <a href="${base_url}<@fp filter="${filterStr}" type="F" val="${ht}"  single=false/>.html#list"  rel="nofollow" >${ht}</a>
                        </#if>
                      <#else>
                            ...
                      </#if>
                  </#list>
            </#if>
            </span>
        </div>
        <p class="turn-intro-word">
            ${item_hotel.recommendContent}
        </p>
        <!--图片-->
        <#if item_hotel.picDisplay?contains("SMALL")>
            <div class="isotope tuan-small">
        <#else>
            <div class="isotope tuan-big">
        </#if>
        <!--#if display=="simple" -->
            <dl>
                <#list item_hotel.hotelImageList as hImage>
                    <#if item_hotel.picDisplay?contains("BIG") && display!="simple" && hImage_index==0>
                        <dd img = "large">
                            <div data-show-presence="true" class="mediaSquare" style="width: 486px; height: 324px;">
                                <a  href="http://www.lvmama.com/hotel/v${item_hotel.id}"  target="_blank" rel="nofollow">
                                    <img class="lazy" src="http://pic.lvmama.com/img/v3/holiday/loadingbig.gif" data-original="http://pic.lvmama.com${hImage.get("url")}"  width="486" height="324" />
                                <div class="tuan-imginfo">
                                    <h4>${hImage.get("name")}</h4>
                                    <p>${hImage.get("context")}</p>
                                </div>
                                </a>
                            </div>
                        </dd>
                     <#else>
                        <dd img = "small">
                            <div data-show-presence="true" class="mediaSquare small" style="width: 240px; height: 159px;">
                                <a  href="http://www.lvmama.com/hotel/v${item_hotel.id}"  target="_blank" rel="nofollow">
                                    <img class="lazy" src="http://pic.lvmama.com/img/v3/holiday/loading.gif" data-original="http://pic.lvmama.com${hImage.get("url")}" width="240" height="159" />
                                <div class="tuan-imginfo">
                                    <h4>${hImage.get("name")}</h4>
                                    <p>
                                        <#if hImage.get("context")?length gt 30 >
                                            ${hImage.get("context")?substring(0,27)}...
                                        <#else>
                                            ${hImage.get("context")}
                                        </#if>
                                    </p>
                                </div>
                                </a>
                            </div>
                        </dd>
                    </#if>
                    
                    <#if hImage_index==2>
                        <#break>
                    </#if>
                </#list>
            </dl>
            <div class="info-tags">
                <ul>
                    <li><dfn>&yen;</dfn><i>${item_hotel.productsPrice}</i>起</li>
                    <#if item_hotel.weekSales gt 0>
                        <li>近一周<span class="color-peach">${item_hotel.weekSales}</span>人预订</li>
                    </#if>
                </ul>
            </div>
            <#if item_hotel.distance??>
                <span class="distance-remind"><i></i>距离${searchvo.localName!searchvo.keyword} ${item_hotel.distance} KM</span>
             </#if>
        </div>
    </div>
</#list>

<#if pageConfig.totalPageNum gt 1 > 
    <div class="pages orangestyle"> 
        <@s.property escape="false" value="@com.lvmama.search.util.Pagination@pagination(pageConfig)"/>
    </div>
</#if>
<script>
	function coremetricsClick(val){
		cmCreateElementTag(val,"搜索结果点击");
	}
</script>