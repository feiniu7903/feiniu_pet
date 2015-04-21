<div class="p_comment_list">
<@s.iterator value="cmtCommentVOList" id="cmtCommentVO">
<div class="p_comment_item" >
	<div class="feed_avatar">
		<span class="feed_face">
			<#if cmtCommentVO.userImg??><img src="http://pic.lvmama.com${cmtCommentVO.userImg?if_exists}" width="40" height="40" alt="" /><#else><img src="http://pic.lvmama.com/cmt/img/72x72.gif" width="40" height="40" alt="" /></#if>
		</span>
		<span class="feed_name"><#if cmtCommentVO.userName??><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(16,userName)" /><#else>匿名</#if></span>
		<span class="feed_time">${cmtCommentVO.createdTime?string("yyyy/MM/dd")?if_exists}</span>
	</div>
	<div class="feedbox">
		<div class="feed_info">
			<@s.if test='cmtType == "EXPERIENCE"'><span class="ctags tags104">体验</span></@s.if>
			<@s.if test='isBest == "Y"'><span class="ctags tags105">精华</span></@s.if>
			<b>总体评价：</b>
			<span class="xcm_star"><i style="width:<@s.if test="null!=sumaryLatitude&&null!=sumaryLatitude.score">${sumaryLatitude.score * 20}</@s.if>%"></i></span>
			<@s.iterator value="cmtLatitudes" id="latitudeScore">
				<span class="pm_name"><em>${latitudeScore.latitudeName?if_exists}：</em><i>${latitudeScore.score?if_exists}</i></span>
			</@s.iterator>
		</div> <!-- // div.feed_info -->
        <div class="backinfo"> 
            <#if cmtCommentVO.cashRefund?? && cmtCommentVO.cashRefund &gt; 0 ><span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt;${cmtCommentVO.cashRefundYuan?if_exists}&lt;/span&gt;元点评奖金返现。"><em>返</em><i>奖金${cmtCommentVO.cashRefundYuan?if_exists}元</i></span></#if> 
            <span class="tagsback tagblue" tip-title="写体验点评送积分" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt; 
            <#if cmtCommentVO.isBest == 'Y' && cmtCommentVO.cmtType == 'EXPERIENCE'>250</#if> 
            <#if cmtCommentVO.isBest == 'Y' && cmtCommentVO.cmtType == 'COMMON'>200</#if> 
            <#if cmtCommentVO.isBest != 'Y' && cmtCommentVO.cmtType == 'EXPERIENCE'>100</#if> 
            <#if cmtCommentVO.isBest != 'Y' && cmtCommentVO.cmtType == 'COMMON'>50</#if> 
            &lt;/span&gt;积分。"> 
            <#if cmtCommentVO.isBest == "Y" && cmtCommentVO.cmtType == "EXPERIENCE"><em>送</em><i>积分250分</i></#if> 
            <#if cmtCommentVO.isBest == "Y" && cmtCommentVO.cmtType == "COMMON"><em>送</em><i>积分200分</i></#if> 
            <#if cmtCommentVO.isBest != "Y" && cmtCommentVO.cmtType == "EXPERIENCE"><em>送</em><i>积分100分</i></#if> 
            <#if cmtCommentVO.isBest != "Y" && cmtCommentVO.cmtType == "COMMON"><em>送</em><i>积分50分</i></#if> 
            </span> 
        </div>
		<div class="comment_info">
			<@s.if test="contentSize > 100">
				<p class="comment_text" id="cmtContent_${cmtCommentVO.commentId!?c}" beief-data="<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(content, 100)"/>" complete-data="${contentFixBR}">
					<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(content, 100)" />&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:showCompleteData(${cmtCommentVO.commentId!?c})" class="view-details">&nbsp;&nbsp;&nbsp;&nbsp;查看全部 &gt;&gt;</a>
				</p>
			</@s.if><@s.else>
				<p class="comment_text">${contentFixBR}</p>
			</@s.else>		
		</div> <!-- // div.comment_info -->                                  
		<p class="feed-info">
			<a id="userfulCount_${cmtCommentVO.commentId!?c}" href="javascript:addUsefulCount(${cmtCommentVO.shamUsefulCount},${cmtCommentVO.commentId!?c},this);" class="j-comment-link">有用 <span>(${cmtCommentVO.usefulCount})</span></a>
			<span class="s-feed">|</span>
			<a   class="j-recomment">回复 <span>
			(${cmtCommentVO.replyCount?default("0")?number+cmtCommentVO.lvmamaReplyCount?default("0")?number})
			</span></a>
		</p> <!-- // p.feed-info -->
		<div class="feed-discuss <@s.if test="null==cmtReplyVOList">hide</@s.if>">
			<div class="discuss-inner j-discuss-inner">
				<span class="arrow-up"></span>
				<span class="arrow-up arrow-up-in"></span>
				<div class="feeds-reply-box hide">
					<form action="" class="form-hor form-small form-comment">
						<p>回复 <@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(16,userName)" /></p>
						<p><textarea class="textarea" name="" id="newReplyContent_${cmtCommentVO.commentId!?c}" ></textarea>
						<button type="button" class="btn btn-small btn-white" onClick="reply(${cmtCommentVO.commentId!?c})">回复</button></p>
					</form>
				</div>
				<ul class="feed-comments j-feed-comments" id="reply_${cmtCommentVO.commentId!?c}">
 				   <@s.iterator value="cmtReplyVOList" id="cmtReplyVO">
                        <li class="comment-item">
                            <@s.if test='replyType == "LVMAMA"'><p class="lv-recomment"><span>${chReplyType?if_exists}：</span>${content?if_exists}</p></@s.if>
                            <@s.if test='replyType == "MERCHANT"'><p class="lv-recomment"><span>${chReplyType?if_exists}：</span>${content?if_exists}</p></@s.if>
                            <@s.if test='replyType == "CUSTOMER"'><p class="comment-item"><span><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(16,userName)" />：</span>${content?if_exists}</p></@s.if>
                            <p class="feed_time">${formattedCreateTime?if_exists}</p>
                        </li>
                   </@s.iterator>
				</ul>  <!-- // ul.feed-comments -->
			</div>
	       </div> <!-- // div.feed-discuss -->
	 </div> <!-- // div.feedbox -->
</div>
</@s.iterator>
<div class="pages rosestyle">
	<@s.if test='type == "EXPERIENCE"'>
		<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(defaultPageSize,totalPage,'javascript:loadExperienceComment(argPage);',currentPage,'js')"/>
	</@s.if>
	<@s.if test='type == "COMMON"'>
		<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(defaultPageSize,totalPage,'javascript:loadCommonComment(argPage);',currentPage,'js')"/>
	</@s.if>
	<@s.if test='type == "Best"'>
		<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(defaultPageSize,totalPage,'javascript:loadBestComment(argPage);',currentPage,'js')"/>
	</@s.if>
</div>
</div>