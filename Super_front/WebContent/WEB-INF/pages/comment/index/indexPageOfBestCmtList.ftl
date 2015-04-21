 <#import "/WEB-INF/pages/comment/generalComment/showProdLatitudeCssTemplate.ftl" as commentMacroTemplate>
 
      <h3 class="c_title cbd_e"><small class="fr"><a rel="nofollow" href="http://www.lvmama.com/public/help_center_261" target="_blank">什么是精华点评？</a></small>
      		<strong>精华点评</strong><small>(点评被加为精华可获得不同额度的积分)</small>
      </h3>
       <div class="u_comment c_item_con lazyload">
       	   <a href="#xhtop-comment" id="xh_gotop"></a>
       	   
           <@s.if test="bestCmtsPageConfig.items.size>0">
			<@s.iterator value="bestCmtsPageConfig.items" id="cmtCommentVO">
		          <dl class="c_shadow clearfix">
		            <dt><a rel="nofollow" href="http://www.lvmama.com/product/${cmtCommentVO.productId}/comment" class="xh_imgauto" target="_blank">
		              <#if cmtCommentVO.productLargeImage != null || cmtCommentVO.productLargeImage != "">
				          <img src='${cmtCommentVO.productLargeImgUrl}'/>
				      <#else>
				          <img src='http://www.lvmama.com/dest/img/myspace/img_120_90.jpg'/>
				      </#if>
				      </a>
		            <dd class="c_item_tit">
		            
		                <#if cmtCommentVO.productOfCommentSellable==true>
		                	<h3><a class="text-ell" target="_blank" href="http://www.lvmama.com/product/${cmtCommentVO.productId}"><strong>${cmtCommentVO.productName}</strong></a></h3>
                        <#else>
                        	<h3><a class="text-ell" href="http://www.lvmama.com/comment/${commentId}" target="_blank"><strong>${cmtCommentVO.productName}</strong></a></h3>
                        </#if>
		            
		               	<a title="点击加心" class="h_xing fr" OnClick="javascript:return addUsefulCount(${cmtCommentVO.shamUsefulCount},${cmtCommentVO.commentId},this);"><i>（<big>${cmtCommentVO.shamUsefulCount}</big>）</i></a>
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
		            <dd class="c_comctext"><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(128,contentDelEnter)" /></dd>
		            <dd class="time"><a class="view_detail fr" rel="nofollow" href="http://www.lvmama.com/comment/${commentId}" target="_blank">查看全文</a>${cmtCommentVO.formatterCreatedTime}</dd>
		            
		            
		            <dd class="c_w_line">
		                <#if cmtCommentVO.productOfCommentSellable==true>
		                <b>该点评出自于：</b><a target="_blank" rel="nofollow" href="http://www.lvmama.com/product/${cmtCommentVO.productId}">${cmtCommentVO.productName}</a>
                        <#else>
                        <b>该点评出自于：</b>${cmtCommentVO.productName}<i>该产品已售完</i>
                        </#if>
                    </dd>
		            
		             <!--  遍历维度     -->
		            <dd class="c_score"> 
       					 <!-- commentMacroTemplate.showLatitudeCss cmtLatitudes = cmtCommentVO.cmtLatitudes  -->
	                 	 <@s.iterator value="cmtLatitudes" id="latitudeScore" status="st">
			                 	<span style="background:none;padding:0">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i></span>
	                 	</@s.iterator>
		            </dd>
		          </dl>
		       </@s.iterator>
			</@s.if>
      </div>
    </div> 
	
