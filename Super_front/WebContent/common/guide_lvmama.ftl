<div class="buttom clearfix">
	<div class="buttom_list">
		<b class="buttom_list_tit1">订购指南</b>
		<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_help_0')" status="status">
		   <a target="_blank" href="${url?if_exists}" rel="nofollow">${title?if_exists}</a>
		</@s.iterator>
	</div>
	<div class="buttom_list">
		<b class="buttom_list_tit2">注册与登录</b>
		<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_help_1')" status="status">
		   <a target="_blank" href="${url?if_exists}" rel="nofollow">${title?if_exists}</a>
		</@s.iterator>
	</div>
	<div class="buttom_list">
		<b class="buttom_list_tit3">支付与取票</b>
	   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_help_2')" status="status">
		   <a target="_blank" href="${url?if_exists}" rel="nofollow">${title?if_exists}</a>
		</@s.iterator>
	</div>
	<div class="buttom_list">
		<b class="buttom_list_tit4">沟通与订阅</b>
	   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_help_3')" status="status">
		   <a target="_blank" href="${url?if_exists}" rel="nofollow">${title?if_exists}</a>
		</@s.iterator>
	</div>
</div><!--bottom end-->

