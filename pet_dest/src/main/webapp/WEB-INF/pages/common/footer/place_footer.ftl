<#include "/WEB-INF/pages/common/footer.ftl"/>
<@s.if test='"true"!=maps && weeklyWeatherList==null'>
<div class="hh_cooperate">
	<@s.if test='currentTab!=null && (currentTab=="products" || (currentPlace.template=="template_abroad" && currentTab=="dest2dest"))'>
 		<@s.if test="null!=seoLinkslist && seoLinkslist.size()>0">
        <p><b>友情链接：</b>
        <span>
            <@s.iterator value="seoLinkslist" var ="v" status="st">
               <a target="_blank" href="${linkUrl?if_exists }">${linkName?if_exists }</a>
            </@s.iterator>
        </span>
        </p>
      </@s.if>
	</@s.if>
</div>
</@s.if>
<#include "/WEB-INF/pages/common/mvHost.ftl"/>

