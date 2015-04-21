<@s.if test="fromPlaceList.size>0" >
<div class="dstnt_c_list">
	<b>切换出发城市：</b>
	<span style="display:none;"><a rel="nofollow" href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/<@s.property value="currentTab"/>_tab_frm<@s.if test='pageFlag !=null || pageFlag=="Y"'  >_10_1</@s.if>"   <@s.if test="fromDestId ==null" > class="fromCurCity" <@s.set name="fromPlaceName" value='"各地"'/> </@s.if> >全国</a></span>
	<@s.if test="fromPlaceList.size<=10" >
				<@s.iterator value="fromPlaceList" >	
					<span <@s.if test="fromDestId==placeId" > class="cur" <@s.set name="fromPlaceName" value="name"/> </@s.if>><a class="city" href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/<@s.property value="currentTab"/>_tab_frm${placeId?c}<@s.if test='pageFlag !=null || pageFlag=="Y"'  >_10_1</@s.if>">${name}</a></span>
				</@s.iterator>
	</@s.if>
	<@s.else>
		<@s.iterator value="fromPlaceList" status="st" var="var" >
		<@s.if test="#st.count<=10" >
			<span <@s.if test="fromDestId==placeId" > class="cur" <@s.set name="fromPlaceName" value="name"/> </@s.if>><a href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/<@s.property value="currentTab"/>_tab_frm${placeId?c}<@s.if test='pageFlag !=null || pageFlag=="Y"'  >_10_1</@s.if>">${name}</a></span>
		</@s.if></@s.iterator>
		<span class="last">
		<a class="dstnt_more_a city_more_a link_blue" href="javascript:void(0)">更多<img class="dstnt_jmore dstnt_jtop" src="http://pic.lvmama.com/img/new_v/ob_destnt/dstnt_jtop.jpg"></a>
		<ul class="dstnt_more_fc">
			<@s.iterator value="fromPlaceList" status="st" var="var" >
			<@s.if test="#st.count>10" >
				<li><a <@s.if test="fromDestId==placeId" > class="city" <@s.set name="fromPlaceName" value="name"/> </@s.if>  href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/<@s.property value="currentTab"/>_tab_frm${placeId?c}<@s.if test='pageFlag !=null || pageFlag=="Y"'  >_10_1</@s.if>" >${name}</a></li>
			</@s.if></@s.iterator>
		</ul>
	</span>
	</@s.else>
</div>
</@s.if>