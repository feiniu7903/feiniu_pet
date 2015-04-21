<@s.iterator value="cmtReplyVOList" id="cmtReplyVO">
	<li class="comment-item">
		<@s.if test='replyType == "LVMAMA"'><p class="lv-recomment"><span>${chReplyType?if_exists}：</span>${content?if_exists}</p></@s.if>
		<@s.if test='replyType == "MERCHANT"'><p class="lv-recomment"><span>${chReplyType?if_exists}：</span>${content?if_exists}</p></@s.if>
		<@s.if test='replyType == "CUSTOMER"'><p class="comment-item"><span><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(16,userName)" />：</span>${content?if_exists}</p></@s.if>
		<p class="feed_time">${formattedCreateTime?if_exists}</p>
	</li>
</@s.iterator>
