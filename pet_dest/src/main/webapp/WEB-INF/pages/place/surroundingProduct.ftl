<#include "/WEB-INF/pages/place/changeFromPlace.ftl" />
 <!--上海周边跟团游 S-->
<@s.if test="recSurroundingPrdSearchList.size>0" >
<div class="dstnt_recomend">
	<@s.if test="recSurroundingPrdSearchList.size>=10">
		<@s.if test="isGroupMore">
		<a rel="nofollow" href="http://www.lvmama.com/search/group/<@s.property value="fromPlaceName"/>-<@s.property value="currentPlace.name"/>.html" class="fr gray"  title="更多<@s.property value="currentPlace.name"/>周边跟团游">更多&gt;&gt;</a>
		</@s.if>
		<@s.else>
		<a rel="nofollow" href="http://www.lvmama.com/search/around/<@s.property value="fromPlaceName"/>-<@s.property value="currentPlace.name"/>.html" class="fr gray"  title="更多<@s.property value="currentPlace.name"/>周边跟团游">更多&gt;&gt;</a>
		</@s.else>
	</@s.if>
	<h3 class="dstnt_jdtj"><@s.property value="fromPlaceName"/>到<@s.property value="currentPlace.name"/>跟团游</h3>
</div>
 <@s.set name="prdList" value="recSurroundingPrdSearchList"/>
 <#include "/WEB-INF/pages/place/destIndexPrdList.ftl" />
</@s.if>
<!--上海周边跟团游 E-->
