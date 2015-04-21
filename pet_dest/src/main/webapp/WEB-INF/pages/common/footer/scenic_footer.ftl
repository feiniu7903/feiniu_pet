<#include "/WEB-INF/pages/common/footer.ftl"/>
<!-- footer start-->
<div class="hh_cooperate">
<@s.if test="currentTab!=null && currentTab == 'ticket'||currentTab == 'products'"><#include "/WEB-INF/pages/staticHtml/friend_link/dest_detail_footer.ftl"></@s.if>
	<@s.if test="currentTab!=null && currentTab == 'package'||currentTab == 'line'"><#include "/WEB-INF/pages/staticHtml/friend_link/route_detail_footer.ftl"></@s.if>
	<@s.if test="currentTab!=null && currentTab == 'hotels'"><#include "/WEB-INF/pages/staticHtml/friend_link/hotel_detail_footer.ftl"></@s.if>
<@s.if test="currentTab!=null && currentTab == 'ticket'||currentTab == 'products'&& place.seoContent!=null">
    <p><!--友情链接-->
	<@s.property value='place.seoContent' escape="false"/>
	</p>
</@s.if>
</div>
<!-- footer end-->
<#include "/WEB-INF/pages/common/mvHost.ftl"/>
