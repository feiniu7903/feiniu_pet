<!--省份级、自治区和大洲级存在当季推荐-->
<@s.if test='currentPlace.placeType=="PROVINCE"||currentPlace.placeType=="ZZQ"||currentPlace.stage==0'>
<@s.if test="seoPersonDestList.size()>0||seoPlaceList.size()>0">
<div class="dstnt_tj">
	<h1 class="dstnt_tj_title"><@s.property value="currentPlace.seoName" escape="false"/>旅游</h1>
	<div class="dstnt_tj_jq">
	<@s.if test="seoPersonDestList.size()>0">
		<ul class="dstnt_tj_ul dstnt_tj_ul_active">
			<li class="dstnt_tj_title">热门目的地</li>
			<@s.iterator value="seoPersonDestList" status="seo" >
			 <@s.if test="#seo.index<10">
			  <li><a href='http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>' title="<@s.property value="name"/>旅游"><@s.property value="name"/></a></li>
			 </@s.if>
			</@s.iterator>
		</ul>
		</@s.if>
		<@s.if test='seoPlaceList.size()>0 && !"0".equals(currentPlace.stage)'>
			<ul class="dstnt_tj_ul">
				<li class="dstnt_tj_title">热门景区</li>
				<@s.iterator value="seoPlaceList" status="seo">
				 <@s.if test="#seo.index<10">
				 <li><a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>" title="<@s.property value="name"/>旅游"><@s.property value="name"/></a></li>
				 </@s.if>
				</@s.iterator>
			</ul>
		 </@s.if>		 
		<div class="dstnt_clear"></div>
	</div>
</div>
</@s.if>
</@s.if>