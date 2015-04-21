 <@s.iterator value="cmtCommentVOList" id="cmtCommentVO" status="st">
 <div class="dcom-item">
                <div class="feedbox">
                    <div class="feed-info">
                        <span class="feed-date">${cmtCommentVO.createdTime?string("yyyy-MM-dd")?if_exists}</span>
                        <span class="feed-user"><#if cmtCommentVO.userName??><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(16,userName)" /><#else>匿名</#if></span>
                        <p class="feed-score">
                            <@s.if test="null!=sumaryLatitude&&null!=sumaryLatitude.score&&sumaryLatitude.score=5"><span class="icon dicon-good"></span></@s.if>
                            <@s.iterator value="cmtLatitudes" id="latitudeScore" var="var" status="st">
                                <span class="feed-item"> <em>${latitudeName?if_exists}&nbsp;</em><i>${score?if_exists}<@s.if test="null!=score&&score==1">(失望)</@s.if><@s.if test="null!=score&&score==2">(不满)</@s.if><@s.if test="null!=score&&score==3">(一般)</@s.if><@s.if test="null!=score&&score==4">(推荐)</@s.if> <@s.if test="null!=score&&score==5">(力荐)</@s.if></i></span>
                            </@s.iterator>
                       
                        </p>
                    </div>
                    <div class="dbackinfo">
                         <#if cmtCommentVO.cashRefund?? && cmtCommentVO.cashRefund &gt; 0 >
                         <span class="tagsback tagsback-blue" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt;${cmtCommentVO.cashRefundYuan?if_exists}&lt;/span&gt;元点评奖金返现。">
                         <em>返</em><i>¥${cmtCommentVO.cashRefundYuan?if_exists}元</i></span></#if> 
                        <span class="tagsback tagsback-green" tip-title="写体验点评送积分" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt; 
                        <#if cmtCommentVO.isBest == 'Y' && cmtCommentVO.cmtType == 'EXPERIENCE'>250</#if> 
                        <#if cmtCommentVO.isBest == 'Y' && cmtCommentVO.cmtType == 'COMMON'>200</#if> 
                        <#if cmtCommentVO.isBest != 'Y' && cmtCommentVO.cmtType == 'EXPERIENCE'>100</#if> 
                        <#if cmtCommentVO.isBest != 'Y' && cmtCommentVO.cmtType == 'COMMON'>50</#if> 
                        &lt;/span&gt;积分。"> 
                        <#if cmtCommentVO.isBest == "Y" && cmtCommentVO.cmtType == "EXPERIENCE"><em>送</em><i>250分</i></#if> 
                        <#if cmtCommentVO.isBest == "Y" && cmtCommentVO.cmtType == "COMMON"><em>送</em><i>200分</i></#if> 
                        <#if cmtCommentVO.isBest != "Y" && cmtCommentVO.cmtType == "EXPERIENCE"><em>送</em><i>100分</i></#if> 
                        <#if cmtCommentVO.isBest != "Y" && cmtCommentVO.cmtType == "COMMON"><em>送</em><i>50分</i></#if> 
                        </span> 
                    </div>
                    <div class="dcom-info">
                            <@s.if test="contentSize > 100">
                                <p class="dcom-text" id="cmtContent_${cmtCommentVO.commentId!?c}" beief-data="<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(content, 100)"/>" complete-data="${contentFixBR?if_exists}">
                                   <#if cmtCommentVO.isBest == 'Y' ><span class="tags-active">精华点评</span></#if>  <@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(content, 100)" />&nbsp;&nbsp;&nbsp;&nbsp;<a class="js_zhankai" href="javascript:;" class="view-details">&nbsp;&nbsp;&nbsp;&nbsp;查看全部 &gt;&gt;</a>
                                  </p>
                              </@s.if><@s.else>
                             <p class="dcom_text"> <#if cmtCommentVO.isBest == 'Y' ><span class="tags-active">精华点评</span></#if> ${contentFixBR?if_exists}</p>
                           </@s.else>  
                    </div>
                    <p class="dfeeduser">
                        <a id="userfulCount_${cmtCommentVO.commentId!?c}" href="javascript:addUsefulCount(${cmtCommentVO.shamUsefulCount},${cmtCommentVO.commentId!?c},this);" class="dcomplus"><i class="icon dicon-plus"></i> <em>(${cmtCommentVO.usefulCount})</em></a>
                        
                        <span class="s-feed">|</span>
                        <a href="javascript:;" class="dcomuser"><i class="icon dicon-dcom"></i><em>${cmtCommentVO.replyCount?default("0")?number+cmtCommentVO.lvmamaReplyCount?default("0")?number}</em></a>
                    </p>
                    
                    <div class="feed-discuss <@s.if test="null==cmtReplyVOList">hide</@s.if>">
                        <div class="tiptext tip-default">
                            <div class="tip-arrow tip-arrow-1">
                                <em>◆</em>
                                <i>◆</i>
                            </div>
                            <div class="tip-other">
                                <div class="feeds-reply-box hide">
                                    <p>回复 <@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(16,userName)" /></p>
                                    <p class="dform form-inline">
                                        <textarea maxlength="100" class="textarea"  id="newReplyContent_${cmtCommentVO.commentId!?c}" ></textarea>
                                        <button class="btn btn-small btn-w btn-orange" onClick="reply(${cmtCommentVO.commentId!?c})">回复</button>
                                    </p>
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
                        </div>
                    </div><!--//.feed-discuss-->
                </div><!---//.feedbox-->
                
            </div><!--//.dcom-item-->
             <@s.if test="(#st.last)">
                          <hr>
             </@s.if>
  </@s.iterator>
  
    <div class="paging orangestyle">
        <div class="pagebox">
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