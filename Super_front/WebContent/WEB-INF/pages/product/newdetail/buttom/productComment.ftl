<i id="row_5" class="pkg-maodian">&nbsp;</i>
              <h3 class="h3_tit"><span>体验点评</span></h3>
              <div class="row important_prompt c_padd">
              <h4 class="c_line_tit">${prodCProduct.prodProduct.productName}</h4>
              <div class="c_line_pro" id="c_line_pro">
              <div class="big_box">
<!--线路点评开始>>-->               
              	 <div class="c_line_ct">
               	<h5 class="c_line_num"><strong>体验点评(<span>
               	<@s.if test="productCommentStatistics != null && productCommentStatistics.commentCount!=0">
               		${productCommentStatistics.commentCount}
               	</@s.if>
               	<@s.else>0</@s.else>
               	条</span>)</strong></h5>
               	<#include "/WEB-INF/pages/product/newdetail/buttom/productCmtLatitudeScoreInfo.ftl">
               <div class="u_comment">
                     <@s.iterator value="productComments" status="pcs">
                       <dl>
                          <dt><img src="http://pic.lvmama.com/76x76${userImg}" width="76" height="76" /><span><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(16,userNameExp)" /></span></dt>
                          <dd>
                            <@s.if test='isBest=="Y"'><strong class="jing">精</strong></@s.if>
                            <strong class="yan">验</strong>
                            <span class="com_StarValueCon"><b>总体评价：</b>
                            <s class="star_bg cur_def">
                             <@s.if test='sumaryLatitude.score > 0'>
                               <i class="ct_Star${sumaryLatitude.score}"></i>
                             </@s.if>
                             <@s.else>
                                <i class="ct_Star0"></i>
                             </@s.else>
                            </s>
                            </span>
                            <span class="re_jifen"><@s.if test='cashRefundYuan > 0'>返：奖金<em>${cashRefundYuan}元</em></@s.if><@s.else></@s.else>积分<em>${point}分</em></span>
                          </dd>
                          <dd class="c_score">
				   <@s.iterator value="cmtLatitudes" id="latitudeScore">
					<span style="background:none;padding:0">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i></span>
				   </@s.iterator>
                          </dd>
                          <dd class="c_comctext"><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(80,contentDelEnter)" /><a href="/comment/${commentId}" target="_blank" class="c_w_more">查看全文&gt;&gt;</a></dd>
                          <dd class="c_reply"><a title="点击加心" class="h_xing" OnClick="javascript:return addUsefulCount(${shamUsefulCount},${commentId},this);"><i>（<big>${shamUsefulCount}</big>）</i></a><a href="/comment/${commentId}" target="_blank" class="h_fu">网友回复<i>（<big>${replyCount}</big>）</i></a><a href="/comment/${commentId}" target="_blank" class="c_canyu">驴妈妈官方回复<i>（
                          <#if lvmamaReplyCount!=0>
                          <big>${lvmamaReplyCount}</big>
                          <#else>
                          <big>0</big>
                          </#if>                                          
                                                                     ）</i></a><small>${formatterCreatedTime}</small></dd>
                       </dl>
                     </@s.iterator>
                  </div>
				<p class="c_seemore"><a target="_blank" href="http://www.lvmama.com/product/${prodCProduct.prodProduct.productId}/comment" target="_blank">查看全部点评&gt;&gt;</a></p>

                 
                                          
                </div><!--c_line_ct end-->
<!--线路点评结束>>-->
                     
<!--相关线路点评开始>>-->               
              	<!--
              	<div class="c_line_ct c_reline_ct" id="c_reline_ct">

                                          
                </div>
                -->
<!--相关线路点评结束>>-->   
<!--遮罩层>>-->	
                <!--
				<div class="c_shade" id="c_shade">
                    <div class="c_shadebg" id="c_shadebg">
                        <s class="c_left"></s>
                        <s class="c_right"></s>
                        <a class="c_jtleft" id="c_jtleft"></a>
                        <a class="c_jtright" id="c_jtright"></a>
                    </div>
                </div>
                -->
                

            </div><!--big_box end-->
          </div> <!--c_line_pro end-->                 
                
        </div><!--important_prompt end-->
        
      
        
   
