                <!--登陆用户前3条最新点评 -->
                <#if lastestUserPlaceCmtList??>
                	 <@s.iterator value="lastestUserPlaceCmtList" id="cmtCommentVO">
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
		                     	<@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(75,contentDelEnter)" /><a href="/comment/${cmtCommentVO.commentId}" target="_blank" class="c_w_more">查看全文&gt;&gt;</a>
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
			        </@s.iterator>
			    </#if>
			        
			        
			    <!--景点所有点评 -->
			     <#if allCommentVOList??>
                   <@s.iterator value="allCommentVOList" id="cmtCommentVO">
			             <#include "/WEB-INF/pages/comment/dest/listCommonCmtsOfDest.ftl" />
			        </@s.iterator>
                  </#if> 
                  <#if allCommentVOList!=null && allCommentVOList.size()!=0 && pageFlag=='Y'>
	                    <div class="paging" >
				       		 <@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pageConfig.pageSize,pageConfig.totalPageNum,pageConfig.url,pageConfig.currentPage)"/>
				        </div>
                  </#if>
                  
                <!--精华点评 -->
			     <#if pageOfBestCommentList??>
                   <@s.iterator value="pageOfBestCommentList" id="cmtCommentVO">
			              <#include "/WEB-INF/pages/comment/dest/listCommonCmtsOfDest.ftl" />
			        </@s.iterator>
                  </#if> 
                  <#if pageOfBestCommentList!=null && pageOfBestCommentList.size()!=0 && bestPageFlag=='Y'>
	                    <div class="paging" >
				       		 <@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(bestPageConfig.pageSize,bestPageConfig.totalPageNum,bestPageConfig.url,bestPageConfig.currentPage)"/>
				        </div>
                  </#if>
                  
                <!--体验点评 -->
			     <#if experienceCommentList??>
                   <@s.iterator value="experienceCommentList" id="cmtCommentVO">
			              <#include "/WEB-INF/pages/comment/dest/listCommonCmtsOfDest.ftl" />
			        </@s.iterator>
                  </#if> 
                  <#if experienceCommentList!=null && experienceCommentList.size()!=0 && experiencePageFlag=='Y'>
	                    <div class="paging" >
				       		 <@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(experiencePageConfig.pageSize,experiencePageConfig.totalPageNum,experiencePageConfig.url,experiencePageConfig.currentPage)"/>
				        </div>
                  </#if>
<script>
/**
 * 点评“有用”
 * @param {Object} varCommentId
 * @param {Object} obj
 * @param {Object} count
 */
 function xh_addClass(target,classValue) {
			var pattern = new RegExp("(^| )" + classValue + "( |$)"); 
			if (!pattern.test(target.className)) { 
				if(target.className ==" ") { 
					target.className = classValue; 
				}else { 
					target.className = " " + classValue; 
				} 
			}  
			$(target).addClass(classValue);
			return true; 
}; 
		
function addUsefulCount(varUsefulCount,varCommentId,obj) {
	$.ajax({
		type: "post",
		url: "http://www.lvmama.com/comment/ajax/addUsefulCountOfCmt.do",
		data:{
			commentId: varCommentId
		},
		dataType:"json",
		success: function(jsonList, textStatus){
			 if(!jsonList.result){
			   alert("已经点过一次");
			 }else{
			 	xh_addClass(obj,"d_xing");
				var newUsefulCount = varUsefulCount + 1;
				obj.innerHTML= "<i>（<big>" + newUsefulCount + "</big>）</i>" ;
			 }
		}
	});
	}
</script>
