<!-- 新版-底部通栏 -->
<div style="align:center;">
<!-- 目的地_底部通栏 -->
<script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxMnxkZXN0X2NpdHlfMjAxMnxkZXN0X2NpdHlfMjAxMl9iYW5uZXIwMSZkYj1sdm1hbWltJmJvcmRlcj0wJmxvY2FsPXllcyZqcz1pZQ==" charset="gbk"></script>
<!-- 目的地_底部通栏 -->
</div>
<!-- 新版-底部通栏/End -->
<@s.if test="seoPersonDestList.size()>0">
<div class="hh_cooperate">
    <p><@s.property value="currentPlace.name"/>周边地区：
	 <@s.iterator value="seoPersonDestList" status="seo" >
	    <a href='http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>' target="_blank"><@s.property value="name"/>旅游</a></li>
	 </@s.iterator>
	 <@s.iterator value="fromPlaceList" >
	    <@s.if test='currentTab!="freeness"'>
		<a href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/dest2dest_tab_frm${placeId?if_exists}"  target="_blank">${name?if_exists}到<@s.property value="currentPlace.name"/></a>
	    </@s.if>
	</@s.iterator>
   </p> 	    
</div>
</@s.if>
<@s.if test='currentTab==null || currentTab!="" || "true"==maps || weeklyWeatherList!=null'>
<div class="hh_cooperate">
<p>热门景点导航：
<@s.iterator value="seoPlaceList">
<@s.if test="seoName!=null">
<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>"><@s.property value="seoName"/></a>
</@s.if>
</@s.iterator>
<a target="_blank" href="http://www.lvmama.com/guide/place/<@s.property value="currentPlace.pinYinUrl"/>"><@s.property value="currentPlace.name"/>旅游攻略</a>
<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="currentPlace.pinYinUrl"/>/scenery" ><@s.property value="currentPlace.name"/>景点</a>
<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/ticket_tab"><@s.property value="currentPlace.name"/>景点大全 </a>
<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/hotel_tab"><@s.property value="currentPlace.name"/>酒店预订</a> 
<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/freeness_tab"><@s.property value="currentPlace.name"/>自由行</a> 
<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/surrounding_tab"><@s.property value="currentPlace.name"/>周边游</a> 
<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/dest2dest_tab_frm"><@s.property value="currentPlace.name"/>旅游线路</a> 
<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="currentPlace.pinYinUrl"/>/dish"><@s.property value="currentPlace.name"/>美食</a>
<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="currentPlace.pinYinUrl"/>/hotel"><@s.property value="currentPlace.name"/>住宿</a>
<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="currentPlace.pinYinUrl"/>/traffic"><@s.property value="currentPlace.name"/>交通</a>
<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="currentPlace.pinYinUrl"/>/entertainment"><@s.property value="currentPlace.name"/>娱乐</a>
<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="currentPlace.pinYinUrl"/>/shop"><@s.property value="currentPlace.name"/>购物</a>
<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="currentPlace.pinYinUrl"/>/weekendtravel"><@s.property value="currentPlace.name"/>行程</a>
</p> 
</div>
</@s.if>