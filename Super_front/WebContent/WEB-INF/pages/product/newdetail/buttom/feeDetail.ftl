<@s.iterator value="costContainList" status="st" var="c">
<dl id="currentCCDl_${c.multiJourneyId}" <@s.if test="#st.index != 0">style="display:none;"</@s.if>>
	<@s.if test="#c.content != null">
    <dt>费用包含</dt>    
    <#list c.content?split("\n") as item>
	<dd>${item}</dd>
	</#list>
	</@s.if>
</dl>
</@s.iterator>
<@s.iterator value="nocostContainList" status="st" var="n">
<dl id="currentNCCDl_${multiJourneyId}" <@s.if test="#st.index != 0">style="display:none;"</@s.if>>
<@s.if test="#n.content != null">
     <dt>费用不包含</dt>
    <#list n.content?split("\n") as item>
	<dd>${item}</dd>
	</#list>
	</@s.if>
</dl>
</@s.iterator>