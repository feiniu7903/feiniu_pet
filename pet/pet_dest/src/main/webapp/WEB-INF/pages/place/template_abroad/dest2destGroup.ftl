<#include "/WEB-INF/pages/place/changeFromPlace.ftl" />
<@s.set name="isDest2DestPaging" value='"Y"'/>

<@s.if test="recDest2destGroupSearchList.size>0">
	<!--上海跟团游 S-->     
	<div class="dstnt_recomend">
	<@s.if test="recDest2destGroupSearchList.size>=10"><a target="_blank" rel="nofollow" class="fr gray" href="http://www.lvmama.com/search/abroad-from-<@s.property value="fromPlaceName"/>-to-<@s.property value="currentPlace.name"/>-group.html"  title="更多<@s.property value="currentPlace.name"/>跟团游">更多&gt;&gt;</a></@s.if>
	<h3 class="dstnt_jdtj"><@s.property value="fromPlaceName"/>到<@s.property value="currentPlace.name"/>跟团游</h3>
	</div>   
    <div class="r-box guide-list">
        <@s.set name="prdList" value="recDest2destGroupSearchList"/>
       	<#include "/WEB-INF/pages/place/destIndexPrdList.ftl" />
    </div>
      <!--上海跟团游 E-->
</@s.if>
