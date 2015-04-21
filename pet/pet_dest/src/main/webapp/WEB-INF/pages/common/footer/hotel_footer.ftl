<#include "/WEB-INF/pages/common/footer.ftl"/>
<!-- footer start-->
<div class="hh_cooperate">
<#include "/WEB-INF/pages/staticHtml/friend_link/hotel_detail_footer.ftl">
<@s.if test="place.seoContent!=null">
    <p><b>友情链接: </b><span>
    <@s.property value='place.seoContent' escape="false"/>
    </span></p>
</@s.if>    
</div>
<!-- footer end-->
<#include "/WEB-INF/pages/common/mvHost.ftl"/>
