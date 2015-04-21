<!--多行程 -->
<ul class="multithreading js_m clearfix">
	<@s.iterator value="viewMultiJourneyList" var="vmj" status="st">
		<li data="${multiJourneyId}" class="viewCurrentJourney <@s.if test='#st.index == 0'>m_active</@s.if>">
			${journeyName}<span <@s.if test="#st.index == 0">class="iy"</@s.if>></span>	
		</li>
	</@s.iterator>
</ul>
<@s.iterator value="viewMultiJourneyList" var="vmj" status="st">
<div class="js_day <@s.if test='#st.index == 0'>d_active</@s.if>" <@s.if test="#st.index != 0">style="display:none;"</@s.if> id="currentJourneyDiv_${multiJourneyId}">
	<div class="trial ">
	    <ul class="clearfix">
	        <li>★适用团期：${specDate}</li>
			<li>${content}</li>		       
	    </ul>
	</div>
	<#include "/WEB-INF/pages/product/newdetail/buttom/journeyDetail.ftl">
</div>
</@s.iterator>
<input type="hidden" name="multiIdStr" value="${multiIdStr}"/>