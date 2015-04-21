<@s.if test="(null!=scenicVo.ticketProductList.get('SINGLE')&&scenicVo.ticketProductList.get('SINGLE').size()>0)
                         ||(null!=scenicVo.ticketProductList.get('UNION')&&scenicVo.ticketProductList.get('UNION').size()>0)
                        ||(null!=scenicVo.ticketProductList.get('SUIT')&&scenicVo.ticketProductList.get('SUIT').size()>0) " > 
 <div class="jd_tabouter">
        <table class="jd_tab" >
            <tr class="jd_ticket_type">
                <th style="width:510px;">门票预订</th>
                <th style="width:50px;">市场价</th>
                <th style="width:65px;">驴妈妈价</th>
                <th style="width:65px;">支付方式</th>
                <th style="width:65px"></th>
            </tr>
        <!-- 门票或者通票-->
              <@s.if test="null!=scenicVo.ticketProductList.get('SINGLE')&&scenicVo.ticketProductList.get('SINGLE').size()>0" >
                <tr>
                    <td colspan="5"><b class="num">门票</b></td>
                </tr>
                    <@s.iterator value="scenicVo.ticketProductList.get('SINGLE')" var="var" status="st">
                    <tr>
                        <td class="destProName" colspan="5"><@s.property value="productSearchInfo.productName" />
                            <#include "/WEB-INF/pages/scenicDetail/ticket_tags.ftl" />
                            <@s.if test="productSearchInfo.cashRefund!=null&&productSearchInfo.cashRefund!=0"><span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt;${productSearchInfo.cashRefund?if_exists}&lt;/span&gt;元点评奖金返现。"><em>返</em><i>${productSearchInfo.cashRefund?if_exists}元</i></span></@s.if>
                        </td>
                    </tr>
                           <@s.iterator value="branchSearchInfo" var="v2" status="st">
                            <tr>
                                <td class="color_blue"><a class="jd_click_tag">${v2.branchName?if_exists} <span><samp>详情</samp><i class="tri_blue"></i></span></a></td>
                                <td>
                                    <del class="price">&yen;${marketPriceInteger?if_exists}</del>
                                </td>
                                <td><dfn>&yen;<i>${sellPriceYuan?if_exists}</i></dfn></td>
                                <td> <#if  payToLvmama=="true">在线支付<#elseif  payToSupplier=="true">景区支付<#else>在线支付</#if></td>
                                <td>
                                <@s.if  test="null!=validBeginTime">
                               		<a target="_blank" href="http://www.lvmama.com/buy/fill.do?buyInfo.prodBranchId=${prodBranchId?c}" class="btn btn-small btn-orange" onClick="cmCreateElements('<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>${v2.branchName?if_exists}_${productId?if_exists?c}','${productId?if_exists?c}','<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>','${sellPriceYuan?if_exists}','${subProductType}')">预&nbsp;订</a>                                
                               	</@s.if>
                                <@s.else>
                                    <button class="btn btn-small btn-orange jd_order_btn time-price" href="javascript:void(0);" data-pid="${productId?c}" data-bid="${prodBranchId?c}" onClick="cmCreateElements('<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>${v2.branchName?if_exists}_${productId?if_exists?c}','${productId?if_exists?c}','<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>','${sellPriceYuan?if_exists}','${subProductType}')">预&nbsp;订</button>
                                 </@s.else>
                                </td>
                            </tr>
                                        <tr style="border: 1px solid #ccc; display: none;" class="jd-item-detail">
                                            <td class="jd-item-detail" style="" colspan="5">
                                              <div style="position: relative">
                                              <span class="arrow-top">
                                                   <i></i>
                                              </span>
                                                <@s.if  test="null!=validBeginTime">产品有效期：${validBeginTime?string("yyyy-MM-dd")} 至 ${validEndTime?string("yyyy-MM-dd")}<@s.if test='null!=invalidDateMemo && invalidDateMemo != ""'>${invalidDateMemo}</@s.if><br/></@s.if>
                                                  ${descriptionWithTag?if_exists?replace('</br>','')} 
                                                <a class="item-detail-hide hide-more">收起<i class="arrow"></i></a>
                                              </div>
                                            </td>
                                         </tr>
                                         
                                        <tr style="border: 1px solid #ccc; display: none;" class="jd-item-calendar">
                                            <td class="jd-item-detail" style="" colspan="5">
                                                <div style="position: relative">
                                              <span class="arrow-top">
                                                   <i></i>
                                              </span>
                                                  <@s.if  test="null!=validBeginTime">产品有效期：${validBeginTime?string("yyyy-MM-dd")} 至 ${validEndTime?string("yyyy-MM-dd")}<@s.if test='null!=invalidDateMemo && invalidDateMemo != ""'>${invalidDateMemo}</@s.if><br/></@s.if>${descriptionWithTag?if_exists?replace('</br>','')}
                                                    <!--时间价格表-->
                                                    <div id="timePrice${productId?c}${prodBranchId?c}" class="clear_both"></div>
                                                    <!--时间价格表-->
                                                    <a class="item-detail-hide hide-more">收起<i class="arrow"></i></a>
                                                </div>
                                            </td>
                                        </tr>
                        </@s.iterator>
                   </@s.iterator>
              </@s.if>     
            
            <!-- 联票-->
              <@s.if test="null!=scenicVo.ticketProductList.get('UNION')&&scenicVo.ticketProductList.get('UNION').size()>0" >
                <tr>
                    <td colspan="5"><b class="num">联票</b></td>
                </tr>
                    <@s.iterator value="scenicVo.ticketProductList.get('UNION')" var="var" status="st">
                    <tr>
                        <td class="destProName" colspan="5"><@s.property value="productSearchInfo.productName" />
                        <#include "/WEB-INF/pages/scenicDetail/ticket_tags.ftl" />
                       <@s.if test="productSearchInfo.cashRefund!=null&&productSearchInfo.cashRefund!=0"><span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt;${productSearchInfo.cashRefund?if_exists}&lt;/span&gt;元点评奖金返现。"><em>返</em><i>${productSearchInfo.cashRefund?if_exists}元</i></span></@s.if>
                        </td>
                    </tr>
                           <@s.iterator value="branchSearchInfo" var="v2" status="st">
                            <tr>
                                <td class="color_blue"><a class="jd_click_tag">${v2.branchName?if_exists} <span><samp>详情</samp><i class="tri_blue"></i></span></a></td>
                                <td>
                                    <del class="price">&yen;${marketPriceInteger?if_exists}</del>
                                </td>
                                <td><dfn>&yen;<i>${sellPriceYuan?if_exists}</i></dfn></td>
                                <td> <#if  payToLvmama=="true">在线支付<#elseif  payToSupplier=="true">景区支付<#else>在线支付</#if></td>
                                <td>
                                    <@s.if  test="null!=validBeginTime">
                                    <a target="_blank" href="http://www.lvmama.com/buy/fill.do?buyInfo.prodBranchId=${prodBranchId?c}" class="btn btn-small btn-orange" onClick="cmCreateElements('<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>${v2.branchName?if_exists}_${productId?if_exists?c}','${productId?if_exists?c}','<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>','${sellPriceYuan?if_exists}','${subProductType}')">预&nbsp;订</a>
                                    </@s.if>
                                    <@s.else>
                                    <button class="btn btn-small btn-orange jd_order_btn time-price" href="javascript:void(0);" data-pid="${productId?c}" data-bid="${prodBranchId?c}" onClick="cmCreateElements('<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>${v2.branchName?if_exists}_${productId?if_exists?c}','${productId?if_exists?c}','<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>','${sellPriceYuan?if_exists}','${subProductType}')">预&nbsp;订</button>
                                     </@s.else>                              
                                 </td>
                            </tr>
                                        <tr style="border: 1px solid #ccc; display: none;" class="jd-item-detail">
                                            <td class="jd-item-detail" style="" colspan="5">
                                              <div style="position: relative">
                                              <span class="arrow-top">
                                                   <i></i>
                                              </span>
                                               <@s.if  test="null!=validBeginTime">产品有效期：${validBeginTime?string("yyyy-MM-dd")} 至 ${validEndTime?string("yyyy-MM-dd")}<@s.if test='null!=invalidDateMemo && invalidDateMemo != ""'>${invalidDateMemo}</@s.if><br/></@s.if>${descriptionWithTag?if_exists?replace('</br>','')}
                                                <a class="item-detail-hide hide-more">收起<i class="arrow"></i></a>
                                              </div>
                                            </td>
                                         </tr>
                                         
                                        <tr style="border: 1px solid #ccc; display: none;" class="jd-item-calendar">
                                            <td class="jd-item-detail" style="" colspan="5">
                                                <div style="position: relative">
                                                  <span class="arrow-top">
                                                       <i></i>
                                                  </span>
                                                    <@s.if  test="null!=validBeginTime">产品有效期：${validBeginTime?string("yyyy-MM-dd")} 至 ${validEndTime?string("yyyy-MM-dd")}<@s.if test='null!=invalidDateMemo && invalidDateMemo != ""'>${invalidDateMemo}</@s.if><br/></@s.if>${descriptionWithTag?if_exists?replace('</br>','')}
                                                       <!--时间格表-->
                                                        <div id="timePrice${productId?c}${prodBranchId?c}" class="clear_both"></div>
                                                        <!--时间价格表-->
                                                        <a class="item-detail-hide hide-more">收起<i class="arrow"></i></a>
                                                </div>
                                            </td>
                                        </tr>
                        </@s.iterator>
                </@s.iterator>
            </@s.if>        
               
    <!-- 套票-->
              <@s.if test="null!=scenicVo.ticketProductList.get('SUIT')&&scenicVo.ticketProductList.get('SUIT').size()>0" >
                <tr>
                    <td colspan="5"><b class="num">套票</b></td>
                </tr>
                    <@s.iterator value="scenicVo.ticketProductList.get('SUIT')" var="var" status="st">
                     <tr>
                        <td class="destProName" colspan="5"><@s.property value="productSearchInfo.productName" />
                        <#include "/WEB-INF/pages/scenicDetail/ticket_tags.ftl" />
                        <@s.if test="productSearchInfo.cashRefund!=null&&productSearchInfo.cashRefund!=0"><span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt;${productSearchInfo.cashRefund?if_exists}&lt;/span&gt;元点评奖金返现。"><em>返</em><i>${productSearchInfo.cashRefund?if_exists}元</i></span></@s.if>
                        </td>
                     </tr>
                           <@s.iterator value="branchSearchInfo" var="v2" status="st">
                            <tr>
                                <td class="color_blue"><a class="jd_click_tag">${v2.branchName?if_exists} <span><samp>详情</samp><i class="tri_blue"></i></span></a></td>
                                <td>
                                    <del class="price">&yen;${marketPriceInteger?if_exists}</del>
                                </td>
                                <td><dfn>&yen;<i>${sellPriceYuan?if_exists}</i></dfn></td>
                                <td> <#if  payToLvmama=="true">在线支付<#elseif  payToSupplier=="true">景区支付<#else>在线支付</#if></td>
                                <td>
                                    <@s.if  test="null!=validBeginTime">
                                    <a target="_blank" href="http://www.lvmama.com/buy/fill.do?buyInfo.prodBranchId=${prodBranchId?c}" class="btn btn-small btn-orange" onClick="cmCreateElements('<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>${v2.branchName?if_exists}_${productId?if_exists?c}','${productId?if_exists?c}','<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>','${sellPriceYuan?if_exists}','${subProductType}')">预&nbsp;订</a>
                                    </@s.if>
                                    <@s.else>
                                    <button class="btn btn-small btn-orange jd_order_btn time-price" href="javascript:void(0);" data-pid="${productId?c}" data-bid="${prodBranchId?c}" onClick="cmCreateElements('<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>${v2.branchName?if_exists}_${productId?if_exists?c}','${productId?if_exists?c}','<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>','${sellPriceYuan?if_exists}','${subProductType}')">预&nbsp;订</button>
                                    </@s.else>  
                                </td>
                            </tr>
                                       <tr style="border: 1px solid #ccc; display: none;" class="jd-item-detail">
                                            <td class="jd-item-detail" style="" colspan="5">
                                              <div style="position: relative">
                                              <span class="arrow-top">
                                                   <i></i>
                                              </span>
                                              <@s.if  test="null!=validBeginTime">产品有效期：${validBeginTime?string("yyyy-MM-dd")} 至 ${validEndTime?string("yyyy-MM-dd")}<@s.if test='null!=invalidDateMemo && invalidDateMemo != ""'>${invalidDateMemo}</@s.if><br/></@s.if>${descriptionWithTag?if_exists}
                                                <a class="item-detail-hide hide-more">收起<i class="arrow"></i></a>
                                              </div>
                                            </td>
                                         </tr>
                                         
                                        <tr style="border: 1px solid #ccc; display: none;" class="jd-item-calendar">
                                            <td class="jd-item-detail" style="" colspan="5">
                                                <div style="position: relative">
                                              <span class="arrow-top">
                                                   <i></i>
                                              </span>
                                               <@s.if  test="null!=validBeginTime">产品有效期：${validBeginTime?string("yyyy-MM-dd")} 至 ${validEndTime?string("yyyy-MM-dd")}<@s.if test='null!=invalidDateMemo && invalidDateMemo != ""'>${invalidDateMemo}</@s.if><br/></@s.if>${descriptionWithTag?if_exists}
                                                    <!--时间价格表-->
                                                    <div id="timePrice${productId?c}${prodBranchId?c}" class="clear_both"></div>
                                                    <!--时间价格表-->
                                                    <a class="item-detail-hide hide-more">收起<i class="arrow"></i></a>
                                                </div>
                                            </td>
                                        </tr>
                        </@s.iterator>
                </@s.iterator>
            </@s.if>        
        </table>
    </div>
  </@s.if>
  <script type="text/javascript">
  	    function cmCreateElements(one,productId,productName,price,subType){
  	    	cmCreateElementTag("门票预订_"+one,"门票预订点击");
  	    	cmCreateShopAction5Tag(productId, productName, "1", price, subType);
			cmDisplayShops();
  	    }
  </script>