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
				              		<#if cmtCommentVO.cmtType=="EXPERIENCE"><strong class="yan">验</strong></#if>
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
			                     	<@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(75,contentDelEnter)" /><a href="http://www.lvmama.com/comment/${cmtCommentVO.commentId}" target="_blank" class="c_w_more">查看全文&gt;&gt;</a>
			                     </dd>
	                   
	                       <!--   详情, 有用 , 回复(景点只有商家回复)-->
						   <dd class="c_reply">
						   	   <a title="点击加心" class="h_xing" OnClick="javascript:return addUsefulCount(${cmtCommentVO.shamUsefulCount},${cmtCommentVO.commentId},this);">
				                	<i> （<big>${cmtCommentVO.shamUsefulCount}</big>）</i>
				                </a>
				                <a href="http://www.lvmama.com/comment/${cmtCommentVO.commentId}" target="_blank" class="h_fu">回复<i>（${cmtCommentVO.replyCount}）</i></a>
				                <a href="http://www.lvmama.com/comment/${cmtCommentVO.commentId}" target="_blank" class="c_canyu">商家参与回复<i>（<#if cmtCommentVO.merchantReplyCount!=0>${cmtCommentVO.merchantReplyCount}<#else>0</#if>）</i></a>
				                <small>${cmtCommentVO.formatterCreatedTime}</small>
						   </dd>
			            </dl>
			             <!--   遍历回复(暂不显示)     -->                  
	                     <#if cmtCommentVO.replies != null && cmtCommentVO.replies.size()!= 0>
		                      <#list cmtCommentVO.replies as cmtReplyVO>
		                               <#if cmtReplyVO.replyType =="MERCHANT">
		                                  <!--<dd class="feedback"> <h4>商家${cmtReplyVO.userName}反馈：</h4> <p>${cmtReplyVO.content}</p> <small>${cmtReplyVO.formattedCreateTime}</small></dd>   -->  
		                                </#if>
	                         </#list>
                         </#if>  
