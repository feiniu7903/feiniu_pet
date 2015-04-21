<#include "/WEB-INF/pages/place/changeFromPlace.ftl" />
<@s.if test="recFreenessSelfPackPrdList.size>0">
<div class="dstnt_recomend menu_orange">
	<@s.if test="recFreenessSelfPackPrdList.size>=2"><a target="_blank" rel="nofollow" class="fr gray" href="http://www.lvmama.com/search/freetour/<@s.property value="fromPlaceName"/>-<@s.property value="currentPlace.name"/>.html">更多&gt;&gt;</a></@s.if>
	<span class="dstnt_jdtj">驴妈妈推荐</span>
</div>
<@s.set name="prdList" value="recFreenessSelfPackPrdList"/>
<#include "/WEB-INF/pages/place/destIndexPrdList.ftl" />
</@s.if>

<!--上海自由行 S-->
<@s.if test="recDest2destPrdSearchList.size>0">
<div class="dstnt_recomend ">
<@s.if test='recDest2destPrdSearchList.size>=10'><a target="_blank" rel="nofollow" href="http://www.lvmama.com/search/group/<@s.property value="fromPlaceName"/>-<@s.property value="currentPlace.name"/>.html" class="fr gray"  title="更多<@s.property value="currentPlace.name"/>跟团游">更多&gt;&gt;</a></@s.if>
<h3 class="dstnt_jdtj"><@s.property value="fromPlaceName"/>到<@s.property value="currentPlace.name"/>跟团游</h3>
</div>
<@s.set name="prdList" value="recDest2destPrdSearchList"/>
<#include "/WEB-INF/pages/place/destIndexPrdList.ftl" />
</@s.if>
<!--上海自由行 E-->
<@s.set name="isDest2DestPaging" value='"Y"'/>
<@s.if test='currentPlace.placeType=="PROVINCE"||currentPlace.placeType=="ZZQ"||currentPlace.stage==0'>
    <@s.if test="recSurroundingPrdSearchList.size>0">
	<!--上海跟团游 S-->
	<div class="dstnt_recomend">
	<@s.if test="recSurroundingPrdSearchList.size>=10"><a target="_blank" rel="nofollow" href="http://www.lvmama.com/search/freetour/<@s.property value="fromPlaceName"/>-<@s.property value="currentPlace.name"/>.html" class="fr gray"  title="更多<@s.property value="currentPlace.name"/>自由行">更多&gt;&gt;</a></@s.if>
	<h3 class="dstnt_jdtj"><@s.property value="fromPlaceName"/>到<@s.property value="currentPlace.name"/>自由行</h3>
	</div>
    <@s.set name="prdList" value="recSurroundingPrdSearchList"/>
    <#include "/WEB-INF/pages/place/destIndexPrdList.ftl" />
    <!--上海跟团游 E-->
    </@s.if>
</@s.if>

<@s.if test='currentPlace.placeType!="PROVINCE"&&currentPlace.placeType!="ZZQ"&&currentPlace.stage==1'>
    <@s.if test="recSurroundingPrdSearchList.size>0">
	<!--上海跟团游 S-->
	<div class="dstnt_recomend">
	<@s.if test="recSurroundingPrdSearchList.size>=10"><a target="_blank" rel="nofollow" href="http://www.lvmama.com/search/freetour/<@s.property value="fromPlaceName"/>-<@s.property value="currentPlace.name"/>.html" class="fr gray"  title="更多<@s.property value="currentPlace.name"/>自由行">更多&gt;&gt;</a></@s.if>
	<h3 class="dstnt_jdtj"><@s.property value="fromPlaceName"/>到<@s.property value="currentPlace.name"/>自由行</h3>
	</div>
    <@s.set name="prdList" value="recSurroundingPrdSearchList"/>
    <#include "/WEB-INF/pages/place/destIndexPrdList.ftl" />
    <!--上海跟团游 E-->
    </@s.if>
</@s.if>

