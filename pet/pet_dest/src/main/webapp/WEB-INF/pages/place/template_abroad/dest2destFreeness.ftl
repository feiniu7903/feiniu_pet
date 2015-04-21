<#include "/WEB-INF/pages/place/changeFromPlace.ftl" />
<@s.if test="recFreenessSelfPackPrdList.size>0">
<div class="dstnt_recomend menu_orange">
	<a target="_blank" rel="nofollow" class="fr gray" href="http://www.lvmama.com/search/abroad-from-<@s.property value="fromPlaceName"/>-to-<@s.property value="currentPlace.name"/>-freetour.html">更多&gt;&gt;</a>
	<span class="dstnt_jdtj">驴妈妈推荐</span>
</div>
<@s.set name="prdList" value="recFreenessSelfPackPrdList"/>
<#include "/WEB-INF/pages/place/destIndexPrdList.ftl" />
</@s.if>

<@s.set name="isDest2DestPaging" value='"Y"'/>
<!--上海自由行 S-->
<@s.if test="recDest2destPrdSearchList.size>0">
    <div class="r-box guide-list">
        <div class="dstnt_recomend">
		 <@s.if test="recDest2destPrdSearchList.size>=10"><a target="_blank" rel="nofollow" class="fr gray" href="http://www.lvmama.com/search/abroad-from-<@s.property value="fromPlaceName"/>-to-<@s.property value="currentPlace.name"/>-freetour.html"  title="更多<@s.property value="currentPlace.name"/>自由行">更多&gt;&gt;</a></@s.if>
		<h3 class="dstnt_jdtj"><@s.property value="fromPlaceName"/>到<@s.property value="currentPlace.name"/>自由行</h3>
		</div>
        <@s.if test='pageConfig.items.size<=0'><a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/dest2dest_tab_frm${fromDestId}_10_1" class="more">查看更多>></a></@s.if>
        <@s.set name="prdList" value="recDest2destPrdSearchList"/>
       	<#include "/WEB-INF/pages/place/destIndexPrdList.ftl" />
    </div>
</@s.if>
<!--上海自由行 E-->

		
		

      
