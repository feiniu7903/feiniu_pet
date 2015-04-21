<#import "/WEB-INF/pages/product/newdetail/buttom/view_content_func.ftl" as vcf>
        <#--<script type="text/templete">-->
               <@s.if test="prodCProduct.prodProduct.hasSelfPack()">
                    <div class="dtl_box_right clearfix">   
               </@s.if>
               <@s.else>
                    <div class="dtl_box_right clearfix">
               </@s.else>
                       <div class="dtl_box_r_topbox">
                          <div class="dtl_boxr_txt1">
                             <p>
                                 <@s.if test="prodCProduct.prodProduct.marketPrice!=0 || !prodCProduct.prodProduct.hasSelfPack()"><label>市 场 价：</label><span class="dtl_linetxt">&yen;<@s.property value="prodCProduct.prodProduct.marketPrice/100" escape="false"/></span> <br> </@s.if>
                                 <@s.if test="prodCProduct.prodProduct.sellPrice!=0"><label>驴妈妈价：</label><strong>&yen;<em> ${prodCProduct.prodProduct.sellPriceYuan}</em></strong>起 &nbsp;<em class="qijiashuoming" tip-content='<#if prodCProduct.prodProduct.hasSelfPack()>本起价为2人出行住同一间房，并包含最优惠门票2张后核算出来的最低单人价格。<br>您所选择的产品价格会根据出发日期、出行人数、入住酒店房型、航班或交通以及所选附加服务的不同而有所差别。<#else>本起价是指未包含附加服务（如单人房差、保险费等）的基本价格。您最终确认的价格将会随所选出行日期、人数及服务项目而相应变化。 </#if>'
                                 >起价说明</em></@s.if> 
                             </p>
                          <#if couponEnabled == "Y">
                              <#if tagGroupMap??>
                                  <#if tagGroupMap.get("优惠")??  || tagGroupMap.get("抵扣")?? >
                                      <div class="xh-youhui">
                                          <label>优惠活动：</label>

                                          <div style="overflow:hidden;">
                                              <#list tagGroupMap.get("优惠") as t>
                                                  <span class="${t.cssId}" <#if t.description!="">tip-content="${t.description}"</#if>>${t.tagName}</span>
                                              </#list>
                                              <#list tagGroupMap.get("抵扣") as t>
                                                  <span class="${t.cssId}" <#if t.description!="">tip-content="${t.description}"</#if>>${t.tagName}</span>
                                              </#list>
                                              <ul class="youhui_tab" style="color:#f60;padding-left:4px;">
                                                  <@s.iterator value="couponActivityList" var="ca" status="sts">
                                                          <li style=<@s.if test="#sts.index==0">"display: list-item;"</@s.if><@s.else>"display: none;"</@s.else>>
                                                      <@s.property value="#ca.couponName" escape="false"/>
                                                      </li>
                                                  </@s.iterator>
                                              </ul>
                                          </div>
                                      </div>
                                  </#if>
                              </#if>
                          </#if>

                             <p class="c_m">
                                <@s.if test="productCommentStatistics != null && productCommentStatistics.commentCount!=0">
                                <label>总体评价：</label>
                                     <@s.if test="!prodCProduct.prodProduct.hasSelfPack() && prodCProduct.prodProduct.isRoute()">
                                        <#if prodCProduct.prodRoute.routeCategory=="PROD_ROUTE_CATEGORY_1"><span class="icon-type1">经济</span></#if>
                                        <#if prodCProduct.prodRoute.routeCategory=="PROD_ROUTE_CATEGORY_2"><span class="icon-type2">舒适</span></#if>
                                        <#if prodCProduct.prodRoute.routeCategory=="PROD_ROUTE_CATEGORY_3"><span class="icon-type3">豪华</span></#if>
                                     </@s.if>
                                     <small class="b_star"><span class="c_star star${productCommentStatistics.roundHalfUpOfAvgScore}"><i></i></span></small>
                                     <a rel="nofollow" href="#row_5"  hidefocus="false" >点评数：<@s.property value='productCommentStatistics.commentCount'/></a>
                                 </@s.if>
                             </p>
                             
                             <p>
                             <#if couponEnabled == "Y">
                                 <@s.if test="prodCProduct.prodProduct.cashRefundY!=0">
                                     <label>互动有奖： </label>
                                     <span class="gray" style="float: left; max-width: 66%;">购买成功并点评，<i class="orange xtiptext" tip-content='1.<b>写体验点评返奖金</b><br>预订此产品，游玩归来发表体验点评，即可获得此点评奖金。<br>2.<b>如何发表体验点评</b><br>登录我的驴妈妈—我的点评—待点评，即可发表点评。'>返<@s.property value="prodCProduct.prodProduct.cashRefundY" escape="false"/>元奖金<#if (prodCProduct.prodProduct.ticket  || prodCProduct.prodProduct.route) && prodCProduct.prodProduct.cashRefundY?? && prodCProduct.prodProduct.cashRefundY gt 0>（手机预订返${mobileMoney}元哦！）</#if></i></span><br>
                                 </@s.if>
                             </#if>

                             <div style="clear:both;"></div>
                             <@s.if test="prodCProduct.prodProduct.isRoute()">  
                                   <label>出发/目的地：</label><#if (prodCProduct)??&&(prodCProduct.from)??><@s.property value="prodCProduct.from.name" escape="false"/></#if>                                        
                                      <#if (prodCProduct)??&&(prodCProduct.to)??>
                                      <img width="32" height="5" align="absmiddle" class="dtl_goto_icon" alt=" " src="http://pic.lvmama.com/img/new_v/ob_detail/arrow.gif"><span id="toId" style="display:none;">${prodCProduct.to.placeId}</span>
                                      ${prodCProduct.to.name?if_exists}  
                                      </#if> <br> 
                             </@s.if>                                                                    
                             <label>支付方式：</label> <@s.if test="prodCProduct.prodProduct.payToLvmama=='true' || prodCProduct.prodProduct.hasSelfPack()">在线支付、电话支付、<b title="银行转账金额至少不低于￥5000">银行转账</b>、<b title="分期支付金额至少不低于￥1000">分期支付</b></@s.if><@s.else>景区支付</@s.else>       
                              </p>
                              
                              </div><!--txt1-->   
                              <@s.if test="prodCProduct.prodProduct.hasSelfPack()">               
                                  <#include "/WEB-INF/pages/product/newdetail/top/selfpackRight.ftl">
                              </@s.if>
                              <@s.else>
                                  <#include "/WEB-INF/pages/product/newdetail/top/quickBooker.ftl">
                              </@s.else>
                                                                                                        
                       </div><!--topbox-->
                           <@s.if test="prodCProduct.prodProduct.hasSelfPack() && viewPage.hasContent('MANAGERRECOMMEND')">
                           <div class="dtl_cfd_gonggao">
                               <h3><b>产品经理推荐</b></h3> 
                               <ol class="dtl_gonggao_list">
                                  <@vcf.showViewContent 'MANAGERRECOMMEND' 'li'/>
                               </ol>
                           </div><!--gonggao-->
                           </@s.if>
                           <@s.if test="viewPage.hasContent('ANNOUNCEMENT')">
                           <div class="dtl_cfd_gonggao">
                               <h3><b>公告</b></h3> 
                            <@s.if test="prodCProduct.prodProduct.productType =='TICKET'">
                            <div class="tiptext tip-info">
                            <span class="tip-icon tip-icon-info"></span>
                                                                            本产品限网上及手机客户端预订，不接受电话预订。</div>
                            </@s.if>
                               <ol class="dtl_gonggao_list">
                                  <@vcf.showViewContent 'ANNOUNCEMENT' 'li'/>
                               </ol>
                           </div><!--gonggao-->
                           </@s.if>
                           
                     </div><!--boxright-->
    <#--</script>-->