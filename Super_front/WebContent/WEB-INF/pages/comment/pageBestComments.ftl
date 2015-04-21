<@s.if test="bestCmtsPageConfig.items.size>0">
			<@s.iterator value="bestCmtsPageConfig.items" id="cmtCommentVO">
		          <dl class="c_shadow clearfix">
		            <dt><a rel="nofollow" href="/product/${cmtCommentVO.productId}/comment">
		              <#if cmtCommentVO.smallImage != null || cmtCommentVO.smallImage != "">
				          <img src='http://pic.lvmama.com${cmtCommentVO.smallImage}' width="240" height="220"/>
				      <#else>
				          <img src='http://www.lvmama.com/dest/img/myspace/img_120_90.jpg' width="240" height="220"/>
				      </#if>
		            <dd class="c_item_tit">
		              	<h3><a class="text-ell" href="/dest/${cmtCommentVO.productId}">${cmtCommentVO.productName}</a></h3>
		               	<a title="点击加心" class="h_xing fr"><i>（<big>${cmtCommentVO.shamUsefulCount}</big>）</i></a>
		            </dd>
		            <dd> 
		            	<span class="com_StarValueCon">
		            		<b><#if userName??><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(20,userNameExp)" /><#else>匿名</#if></b>
		            		<s class="star_bg cur_def"><i class="ct_Star${sumaryLatitude.score}"></i></s>
		            	</span> 
		            	<span class="re_jifen">
		            	<#if cmtCommentVO.cashRefund != null || cmtCommentVO.cashRefund != "">奖金<em>${cmtCommentVO.cashRefundYuan}元</em></#if>
		            	<#if cmtCommentVO.point != null || cmtCommentVO.point != "">积分<em>${cmtCommentVO.point}分</em></#if>
		            	</span> 
		            </dd>
		            <dd class="c_comctext"><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(160,contentDelEnter)" /></dd>
		            <dd class="time"><a class="view_detail fr" rel="nofollow" href="/comment/${commentId}" target="_blank">查看全文</a>${formatterCreatedTime}</dd>
		             <!--  遍历维度     -->
		            <dd class="c_score"> 
		            	<#list cmtCommentVO.cmtLatitudes as latitudeScore>
		                  <span class="c_hotel">${latitudeScore.latitudeName}:<i>${latitudeScore.score}分</i></span>
						</#list>
		            </dd>
		          </dl>
		       </@s.iterator>
			</@s.if>
