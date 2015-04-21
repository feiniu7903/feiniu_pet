<#include "/WEB-INF/pages/place/template_common/seasonRecommend.ftl"/>
<@s.if test="hasGlobalProducts">
<!--no product S-->
<div class="dstnt_nocontent">很抱歉！该目的地暂时没有相关产品，我们为你找到了，你有可能喜欢的产品 </div>
<!--no product E-->
</@s.if>
<@s.if test="!hasGlobalProducts">
<div class="dstnt_menu">
<ul class="dstnt_oz_menu_tab">
		<@s.iterator  value="tabs" status="t">
        	<@s.if test='tab=="products" '  >
	        	<@s.if test="!hasGlobalProducts"  >
		        	<li  <@s.if test='currentTab=="products"'> class="first curr" </@s.if> > 
		        		<@s.if test='currentTab=="products"'>当季推荐</@s.if> <@s.else><a href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>">当季推荐</a></@s.else> 
		        	</li>
	        	</@s.if>
        	</@s.if>
        	<@s.if test='tab=="ticket"'  >
        	<li  <@s.if test='currentTab=="ticket"'> class="first curr" </@s.if> > 
        		<@s.if test='currentTab=="ticket"'>景区门票</@s.if> <@s.else><a href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/ticket_tab" title="<@s.property value="currentPlace.name"/>景区门票">景区门票 </a></@s.else> 
        	</li>
        	</@s.if>
        	<@s.if test='tab=="freeness"'  >
        	<li  <@s.if test='currentTab=="freeness"'> class="first curr" </@s.if> > 
        		<@s.if test='currentTab=="freeness"'>自由行(景点+酒店)</@s.if> <@s.else><a href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/freeness_tab" title="<@s.property value="currentPlace.name"/>自由行">自由行(景点+酒店)</a></@s.else> 
        	</li>
        	</@s.if>
        	<@s.if test='tab=="hotel"'  >
        	<li  class="<@s.if test='currentTab=="hotel"'>first curr </@s.if>" > 
        		<@s.if test='currentTab=="hotel"'>特色酒店</@s.if> <@s.else><a href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/hotel_tab" title="<@s.property value="currentPlace.name"/>特色酒店">特色酒店</a></@s.else> 
        	</li>
        	</@s.if>
        	<@s.if test='tab=="surrounding"'  >
        	<li  <@s.if test='currentTab=="surrounding"'> class="first curr" </@s.if> > 
        		<@s.if test='currentTab=="surrounding"'><@s.if test='currentPlace.placeType=="PROVINCE"||currentPlace.placeType=="ZZQ"'>当地</@s.if>跟团游</@s.if> <@s.else><a href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/surrounding_tab" title="<@s.property value="currentPlace.name"/>周边游"><@s.if test='currentPlace.placeType=="PROVINCE"||currentPlace.placeType=="ZZQ"'>当地</@s.if>跟团游</a></@s.else> 
        	</li>
        	</@s.if>
        	<@s.if test='tab=="dest2dest"'  >
        	<li  <@s.if test='currentTab=="dest2dest"'> class="first curr" </@s.if> > 
        		<@s.if test='currentTab=="dest2dest"'><@s.if test='currentPlace.placeType=="PROVINCE"||currentPlace.placeType=="ZZQ"'>各地到<@s.property value="currentPlace.name"/></@s.if><@s.else>自由行(含交通)</@s.else></@s.if> <@s.else><a href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/dest2dest_tab_frm" title="<@s.property value="currentPlace.name"/>出发线路"><@s.if test='currentPlace.placeType=="PROVINCE"||currentPlace.placeType=="ZZQ"'>各地到<@s.property value="currentPlace.name"/></@s.if><@s.else>自由行(含交通)</@s.else></a></@s.else> 
        	</li>
        	</@s.if>
        </@s.iterator>
</ul>
</div>
</@s.if>
