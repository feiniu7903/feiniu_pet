   var _comentContent="";
<#if cmtTitleStatisticsVO != null>
	_comentContent = _comentContent + '<i>综合：</i><span class="zh"><@s.property value="cmtTitleStatisticsVO.avgScore"/></span>';
</#if>
<#if cmtLatitudeStatis != null>
 <@s.iterator value="cmtLatitudeStatis"  status="st">
	<#if st.index=0>
		_comentContent = _comentContent + '<i><@s.property value="latitudeName"/>：</i><span class="pz"><@s.property value="avgScore"/></span>';
	</#if>
	<#if st.index=1>
		_comentContent = _comentContent + '<i><@s.property value="latitudeName"/>：</i><span class="hj"><@s.property value="avgScore"/></span>';
	</#if>
	<#if st.index=2>
		_comentContent = _comentContent + '<i><@s.property value="latitudeName"/>：</i><span class="gm"><@s.property value="avgScore"/></span>';
	</#if>
	<#if st.index=3>
		_comentContent = _comentContent + '<i><@s.property value="latitudeName"/>：</i><span class="fw"><@s.property value="avgScore"/></span>';
	</#if>
 </@s.iterator>
</#if>
<#if cmtTitleStatisticsVO != null>
	_comentContent = _comentContent + '<span>点评:<a href="http://www.lvmama.com/comment/${cmtTitleStatisticsVO.placeId!?c}-1" target="_blank"><@s.property value="cmtTitleStatisticsVO.commentCount"/></a>条</span>';
</#if>
document.getElementById("<@s.property value="name"/>").innerHTML = _comentContent;