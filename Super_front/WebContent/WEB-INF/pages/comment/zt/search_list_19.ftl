function ${Request["functionName"]?default("getRecommendProduct")}(targetName) {
	<#if cmtTopPlaceVOList?exists && (cmtTopPlaceVOList.size() > 0)>
		var html = "";
		<@s.iterator value="cmtTopPlaceVOList" status='st'>
		<#if (st.index<5)>
			html = html + "<li <#if st.index=0>class=\"act_bor\"<#elseif st.index=4>class=\"lastli\"</#if>>";
			html = html + "<a target=\"_blank\" href=\"http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>\"  class=\"open_title<#if st.index=0>  open_titlebg</#if>\"><span<#if (st.index>2)> class=\"bg_hz\"</#if>>${st.index+1}</span><@s.property value="titleName"/>（<@s.property value="commentCount"/>条点评）</a>";
			html = html + "<div class=\"view_open_list<#if st.index=0>  dis</#if>\">";
			html = html + "<a target=\"_blank\" href=\"<#if st.index=0><@s.property value='bakWord1'/><#else>http://www.lvmama.com/dest/<@s.property value='pinYinUrl'/></#if>\">";
			html = html + "<img  src=\"http://pic.lvmama.com<@s.property value='placeSmallImage'/>\" height=\"128\" width=\"258\">";
			html = html + "</a>";
			html = html + "</div>";
			html = html + "</li>";
		</#if>
		</@s.iterator>
		html = html + "";
		document.getElementById(targetName).innerHTML = html;
	<#else>
		//nothing to do
	</#if>
}

<#if (Request["targetName"])??>
	${Request["functionName"]?default("getRecommendProduct")}("${Request["targetName"]}");     
 <#else>
 </#if>

        
