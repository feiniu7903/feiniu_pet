                
                    <div class="c_w_score">
                         <p class="c_p_link"><span class="com_StarValueCon total_val_posi">
                        
                         <@s.if test="cmtCommentStatisticsVO != null">
	                          	<font><em>${cmtCommentStatisticsVO.avgScoreStr}</em>分</font><s class="star_bg cur_def"><i class="ct_Star${cmtCommentStatisticsVO.roundHalfUpOfAvgScore}"></i></s></span>
                            	<a href="http://www.lvmama.com/comment/${cmtCommentStatisticsVO.placeId}-1" target="_blank">${cmtCommentStatisticsVO.commentCount}封点评</a>
                         </@s.if>
                         <@s.else>
                         		<font><em>0</em>分</font><s class="star_bg cur_def"><i class="ct_Star0"></i></s></span>
                           		<span>0封点评</span>
                         </@s.else> 
                         </p>
                         <!--点评平均分维度统计开始-->
  	                     <#include "/WEB-INF/pages/comment/cmtAvgLatitudeStatisticsInfo.ftl">
  	                     
			            <div class="comment_tips">
			                <p><i class="bgorange">返</i> 有订单？发表体验点评，返点评奖金。<a rel="nofollow" href="http://www.lvmama.com/myspace/share/comment.do">写体验点评&gt;&gt;</a></p>
			                <p><i class="bgorange">送</i> 没订单？发表普通点评，赠50积分，精华追加150积分！</p>
			                <p>　　<a class="white_comment_btn" href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=${place.placeId!?c}" target="_blank" rel="nofollow">我要写点评</a></p>
			            </div>
		        </div>
  			 
   <!--景点所有点评 -->
			    <#if cmtCommentVOList??>
			       <div class="u_comment cd_comlist last_child clearfix">
                   <@s.iterator value="cmtCommentVOList" id="cmtCommentVO">
			            <dl>
			              <#if cmtCommentVO.userImg??>
	                            	<dt>
	                            		<img height="76" width="76" src="http://pic.lvmama.com${cmtCommentVO.userImg}" />
	                            		<span><#if cmtCommentVO.userName??><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(16,userNameExp)" /><#else>匿名</#if></span>
	                            	</dt>
	                      <#else>
	                            	<dt><img height="76" width="76" src="http://pic.lvmama.com/cmt/img/72x72.gif" />
	                            		<span><#if cmtCommentVO.userName??><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(16,userNameExp)" /><#else>匿名</#if></span>
	                            	</dt>
	                     </#if>

				              <dd> 
				              		<#if cmtCommentVO.isBest=="Y"><strong class="jing">精</strong></#if> 
				                	<#if cmtCommentVO.cmtType=="2"><strong class="yan">验</strong></#if>
				              		<span class="com_StarValueCon"><b>总体评价：</b><s class="star_bg cur_def"><i class="ct_Star<@s.property value='sumaryLatitude.score'/>"></i></s></span> 
				              		<span class="re_jifen">
					              		<#if cashRefund??>奖金<em>${cashRefundYuan}元</em></#if>
					              		<#if point??>积分<em>${point}分</em></#if>
				              		</span>
				              </dd>
			              
				         <!--   遍历维度(景点纬度,不需要标识)      -->
				                 <dd class="c_score"> 
				                	 <@s.iterator value="cmtLatitudes" id="latitudeScore">
						                 	<span style="background:none;padding:0">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i> </span>
				                 	 </@s.iterator>
				                 </dd>
	                  
		                     <dd class="c_comctext">
		                     
		                     	<@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(75,contentDelEnter)" />
		                     	<a href="http://www.lvmama.com/comment/${cmtCommentVO.commentId}" target="_blank" class="c_w_more">查看全文&gt;&gt;</a>
		                     </dd>
	                              
	                       <!--   详情, 有用 , 回复(景点只有商家回复)-->
						   <dd class="c_reply">
						   	   <a title="点击加心" class="h_xing" OnClick="javascript:return addUsefulCount(${cmtCommentVO.shamUsefulCount},${cmtCommentVO.commentId},this);">
				                	<i> （<big>${cmtCommentVO.shamUsefulCount}</big>）</i>
				                </a>
				                <a href="http://www.lvmama.com/comment/${cmtCommentVO.commentId}" target="_blank" rel="nofollow" class="h_fu">回复<i>（${cmtCommentVO.replyCount}）</i></a>
				                <a href="http://www.lvmama.com/comment/${cmtCommentVO.commentId}" target="_blank" rel="nofollow" class="c_canyu">商家参与回复<i>（<#if cmtCommentVO.merchantReplyCount!=0>${cmtCommentVO.merchantReplyCount}<#else>0</#if>）</i></a>
				                <small>${cmtCommentVO.formatterCreatedTime}</small>
						   </dd>
			            </dl>
			             
			        </@s.iterator>
                  </#if> 
			</div>
