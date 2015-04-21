<#include "/WEB-INF/pages/place/changeFromPlace.ftl" />
<@s.set name="isDest2DestPaging" value='"Y"'/>
<@s.if test="recDest2destGroupSearchList.size>0">
	<!--上海跟团游 S--> 
	<div class="dstnt_recomend">
	<@s.if test="recDest2destGroupSearchList.size>=10">
	  <@s.if test="currentTab=='dest2destGroup'">
	   <a target="_blank" rel="nofollow" class="fr gray" href="http://www.lvmama.com/search/abroad-from-<@s.property value="fromPlaceName"/>-to-<@s.property value="currentPlace.name"/>-group.html"  title="更多<@s.property value="currentPlace.name"/>跟团游">更多&gt;&gt;</a>
	  </@s.if>
	</@s.if>
	<h3 class="dstnt_jdtj"><@s.property value="fromPlaceName"/>到<@s.if test="hasGlobalProducts"><@s.property value="parentPlace.name"/></@s.if><@s.else><@s.property value="currentPlace.name"/></@s.else>跟团游</h3>
	</div>
        <@s.set name="prdList" value="recDest2destGroupSearchList"/>
       	<#include "/WEB-INF/pages/place/destIndexPrdList.ftl" />
      <!--上海跟团游 E-->
</@s.if>
<!--上海自由行 S-->
<@s.if test="recDest2destPrdSearchList.size>0">
	<div class="dstnt_recomend">
	<@s.if test="recDest2destPrdSearchList.size>=10">
	  <@s.if test="currentTab=='dest2destFreeness'">
	  <a target="_blank" rel="nofollow" class="fr gray" href="http://www.lvmama.com/search/abroad-from-<@s.property value="fromPlaceName"/>-to-<@s.property value="currentPlace.name"/>-freetour.html"  title="更多<@s.property value="currentPlace.name"/>自由行">更多&gt;&gt;</a>
	  </@s.if>
	</@s.if>
	<h3 class="dstnt_jdtj"><@s.property value="fromPlaceName"/>到<@s.if test="hasGlobalProducts"><@s.property value="parentPlace.name"/></@s.if><@s.else><@s.property value="currentPlace.name"/></@s.else>自由行</h3>
	</div>
    <@s.set name="prdList" value="recDest2destPrdSearchList"/>
   	<#include "/WEB-INF/pages/place/destIndexPrdList.ftl" />
</@s.if>
<!--上海自由行 E-->

