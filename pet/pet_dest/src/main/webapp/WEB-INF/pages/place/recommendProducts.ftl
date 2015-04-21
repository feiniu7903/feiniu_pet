	<!--上海景点推荐 S-->
	<@s.if test="recPlacePrdSearchList.size>0">
	<div class="dstnt_recomend ">
	    <a rel="nofollow" href="http://www.lvmama.com/dest/<@s.if test="hasGlobalProducts"><@s.property value="parentPlace.pinYinUrl"/></@s.if><@s.else><@s.property value="currentPlace.pinYinUrl"/></@s.else>/ticket_tab" class="fr gray"  title="更多<@s.if test="hasGlobalProducts"><@s.property value="parentPlace.name"/></@s.if><@s.else><@s.property value="currentPlace.name"/></@s.else>景点">更多&gt;&gt;</a>
		<h3 class="dstnt_jdtj"><@s.if test="hasGlobalProducts"><@s.property value="parentPlace.name"/></@s.if><@s.else><@s.property value="currentPlace.name"/></@s.else>景区门票</h3>
	</div>
	<#include "/WEB-INF/pages/place/placesTicket.ftl" />
	</@s.if>
	<!--上海景点推荐 E-->
	
	<!--上海自由行（景点加酒店） S-->
	<@s.if test="recFreenessPrdSearchList.size>0">
	<div class="dstnt_recomend ">
	     <a rel="nofollow" href="http://www.lvmama.com/dest/<@s.if test="hasGlobalProducts"><@s.property value="parentPlace.pinYinUrl"/></@s.if><@s.else><@s.property value="currentPlace.pinYinUrl"/></@s.else>/freeness_tab" class="fr gray"  title="更多<@s.if test="hasGlobalProducts"><@s.property value="parentPlace.name"/></@s.if><@s.else><@s.property value="currentPlace.name"/></@s.else>自由行">更多&gt;&gt;</a>
		<h3 class="dstnt_jdtj"><@s.if test="hasGlobalProducts"><@s.property value="parentPlace.name"/></@s.if><@s.else><@s.property value="currentPlace.name"/></@s.else>自由行(景点+酒店)</h3>
	</div>
	<div class="dstnt_list">
		<@s.set name="prdList" value="recFreenessPrdSearchList"/>
	    <#include "/WEB-INF/pages/place/freenessPrdList.ftl" />
	</div>
	 </@s.if>
	<!--上海自由行 E-->
	
	<!--上海酒店预订 S-->
	<@s.if test=" recHotelPrdSearchList.size>0 ">
	<div class="dstnt_recomend ">
	    <a rel="nofollow" href="http://www.lvmama.com/dest/<@s.if test="hasGlobalProducts"><@s.property value="parentPlace.pinYinUrl"/></@s.if><@s.else><@s.property value="currentPlace.pinYinUrl"/></@s.else>/hotel_tab" class="fr gray"  title="更多<@s.if test="hasGlobalProducts"><@s.property value="parentPlace.name"/></@s.if><@s.else><@s.property value="currentPlace.name"/></@s.else>酒店预订">更多&gt;&gt;</a>
		<h3 class="dstnt_jdtj"><@s.if test="hasGlobalProducts"><@s.property value="parentPlace.name"/></@s.if><@s.else><@s.property value="currentPlace.name"/></@s.else>酒店预订</h3>
	</div>
	<#include "/WEB-INF/pages/place/hotel.ftl" />
	 </@s.if>
	<!--上海酒店预订 E-->
	<!--上海跟团游 S-->
	<@s.if test="recSurroundingPrdSearchList.size>0" >
	<div class="dstnt_recomend">
		<a rel="nofollow" href="http://www.lvmama.com/dest/<@s.if test="hasGlobalProducts"><@s.property value="parentPlace.pinYinUrl"/></@s.if><@s.else><@s.property value="currentPlace.pinYinUrl"/></@s.else>/surrounding_tab" class="fr gray"  title="更多<@s.if test="hasGlobalProducts"><@s.property value="parentPlace.name"/></@s.if><@s.else><@s.property value="currentPlace.name"/></@s.else>周边跟团游">更多&gt;&gt;</a>
		<h3 class="dstnt_jdtj"><@s.if test="hasGlobalProducts"><@s.property value="parentPlace.name"/></@s.if><@s.else><@s.property value="currentPlace.name"/></@s.else>跟团游</h3>
	</div>
	 <@s.set name="prdList" value="recSurroundingPrdSearchList"/>
	 <#include "/WEB-INF/pages/place/destIndexPrdList.ftl" />
	</@s.if>
	<!--上海周边跟团游 E-->
	
	<!--自由行（含交通）-->
	<@s.if test="recDest2destPrdSearchList.size>0" >
		<div class="dstnt_recomend dstnt_margin_clear ">
		<a rel="nofollow" href="http://www.lvmama.com/dest/<@s.if test="hasGlobalProducts"><@s.property value="parentPlace.pinYinUrl"/></@s.if><@s.else><@s.property value="currentPlace.pinYinUrl"/></@s.else>/dest2dest_tab_frm" class="fr gray">更多&gt;&gt;</a>
		<h3 class="dstnt_jdtj"><@s.if test='currentPlace.placeType=="PROVINCE"||currentPlace.placeType=="ZZQ"||hasGlobalProducts'>各地到<@s.if test="hasGlobalProducts"><@s.property value="parentPlace.name"/></@s.if><@s.else><@s.property value="currentPlace.name"/></@s.else></@s.if><@s.else><@s.property value="currentPlace.name"/>自由行(含交通)</@s.else></h3>
		</div>
		<@s.set name="prdList" value="recDest2destPrdSearchList"/>
		<@s.set name="isDest2DestPaging" value='"Y"'/>
		<#include "/WEB-INF/pages/place/destIndexPrdList.ftl" />
	</@s.if>
	<!--各地到上海旅游 E-->

