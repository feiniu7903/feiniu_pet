<div class="buttom clearfix">
	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_newHelp')" status="st">
	<div class="buttom_list"<@s.if test="#st.isLast()"> style="border:none"</@s.if>>
		  <b>${title?if_exists}</b>
		  <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_newHelp_${st.index}')" status="status">
		  <a target="_blank" href="${url?if_exists}" rel="nofollow">${title?if_exists}</a>
		 </@s.iterator>
	</div>
	</@s.iterator>
</div><!--bottom end-->
