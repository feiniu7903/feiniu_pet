	<!--上海自由行 S-->
	<@s.if test="recFreenessSelfPackPrdList.size>0">
	<div class="dstnt_recomend menu_orange">
		<@s.if test="recFreenessSelfPackPrdList.size>=2"><a target="_blank" class="fr gray" href="http://www.lvmama.com/search/freetour/-<@s.property value="currentPlace.name"/>.html">更多&gt;&gt;</a></@s.if>
		<span class="dstnt_jdtj">驴妈妈推荐</span>
	</div>
	<@s.set name="prdList" value="recFreenessSelfPackPrdList"/>
	<#include "/WEB-INF/pages/place/freenessPrdList.ftl" />
	</@s.if>
	
	<@s.if test="recFreenessPrdSearchList.size>0">
	<div class="dstnt_recomend ">
	    <@s.if test='recFreenessPrdSearchList.size>=10'><a target="_blank" rel="nofollow" href="http://www.lvmama.com/search/freetour/-<@s.property value="currentPlace.name"/>.html" class="fr gray"  title="更多<@s.property value="currentPlace.name"/>自由行">更多&gt;&gt;</a></@s.if>
		<h3 class="dstnt_jdtj"><@s.property value="currentPlace.name"/>自由行</h3>
	</div>
	<div class="dstnt_list dstnt_bt_clear">
		<@s.set name="prdList" value="recFreenessPrdSearchList"/>
	    <#include "/WEB-INF/pages/place/freenessPrdList.ftl" />
	</div>
	 </@s.if>
  <!--上海自由行 E-->   

