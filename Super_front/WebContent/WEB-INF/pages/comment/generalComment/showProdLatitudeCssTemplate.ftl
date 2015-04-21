    
<#--
	显示一个产品所有点评纬度s
	@param cmtComment 点评 
-->
<#macro showProdLatitudeCss cmtComment>
<#if cmtComment != null && cmtComment.cmtLatitudes != null>
 <@s.iterator value="cmtComment.cmtLatitudes" id="latitudeScore">
 	<@s.if test='${latitudeScore.latitudeName=="酒店"}'>
		<span class="c_hotel">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="景点"}'>
		<span class="c_spot">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="服务"}'>
		<span class="c_serve">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="交通"}'>
		<span class="c_traffic">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="舒适"}'>
		<span class="c_shushi">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="性价"}'>
		<span class="c_xingjia">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="卫生"}'>
		<span class="c_weisheng">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="位置"}'>
		<span class="c_weizhi">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="通关"}'>
		<span class="c_tongguan">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="体验"}'>
		<span class="c_tiyan">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="人气"}'>
		<span class="c_renqi">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if> 
 </@s.iterator>
</#if> 
</#macro>

<#macro showLatitudeCss cmtLatitudes>
<#if cmtLatitudes != null>
 <@s.iterator value="cmtLatitudes" id="latitudeScore">
 	<@s.if test='${latitudeScore.latitudeName=="酒店"}'>
		<span class="c_hotel">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="景点"}'>
		<span class="c_spot">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="服务"}'>
		<span class="c_serve">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="交通"}'>
		<span class="c_traffic">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="舒适"}'>
		<span class="c_shushi">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="性价"}'>
		<span class="c_xingjia">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="卫生"}'>
		<span class="c_weisheng">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="位置"}'>
		<span class="c_weizhi">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="通关"}'>
		<span class="c_tongguan">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="体验"}'>
		<span class="c_tiyan">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if>
	<@s.if test='${latitudeScore.latitudeName=="人气"}'>
		<span class="c_renqi">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
	</@s.if> 
 </@s.iterator>
</#if> 
</#macro>
