  <div class="play_date">
  		 <#if prodCProduct.prodProduct.productType == "TICKET">
			<#assign formSubmitUrl = "/orderFill/ticket-${prodCProduct.prodProduct.productId}"> 
		 </#if>	
         <form method="post" action="${formSubmitUrl!"/buy/fill.do"}" onsubmit="return beforeSubmit(this);">
            <input type="hidden" id="productIdHidden" name="buyInfo.productId" value="${prodCProduct.prodProduct.productId}"></input>
            <input type="hidden" id="productBranchIdHidden" name="buyInfo.prodBranchId" value="<#if prodBranch!=null>${prodBranch.prodBranchId}</#if>"></input>
            <input type="hidden" name="buyInfo.productType" value="${prodCProduct.prodProduct.productType?if_exists}"></input>
            <input type="hidden" id="subProductType" name="buyInfo.subProductType" value="${prodCProduct.prodProduct.subProductType?if_exists}"></input>      
            <input type="hidden" id="selfPack" value="${prodCProduct.prodProduct.hasSelfPack()}"></input>
            <dl class="clearfix">
                <!--非不定期产品才显示游玩日期-->
                <@s.if test='!prodCProduct.prodProduct.IsAperiodic()'>                        
                    <dt id="date_type"><em>*</em><#if prodCProduct.prodProduct.productType == "HOTEL">入住日期<#else>游玩日期</#if>：</dt>
                </@s.if>
                <dd class="quick-wrap">
                    <@s.if test='!prodCProduct.prodProduct.IsAperiodic()'>
                        <p>
                            <select name="buyInfo.visitTime" id="quickBooker_select_1" class="quickBooker_select">
                            </select>
                        </p>
                        <div class="zxerror quick-error">
                            <span class="zxerror-text">
                                <div class="error-arrow">
                                    <em>◆</em>
                                    <i>◆</i>
                                </div>
                                <p>请选择游玩日期</p>
                            </span>
                        </div>
                    </@s.if>
               <@s.if test='!prodCProduct.prodProduct.IsAperiodic()'>
                   </dd>
               </dl>
               <dl class="clearfix">
                   <dt><em>*</em>预订数量：</dt>
                   <dd style="position: relative;">
               </@s.if>  
                    <@s.if test='prodCProduct.prodProduct.isSellable()'>
                    <div id="quickBooker1_tab2">
                        <table class="free_dtl_pro_tab"> 
                           <!-- 默认类别-->
                            <@s.if test="prodBranch!=null">
                                <!-- 不定期-->
                                <@s.if test='prodCProduct.prodProduct.IsAperiodic()'>
                                    <tr class="p_${prodBranch.prodBranchId}" >              
                                         <td><@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(prodBranch.branchName,8)" />
                                            <@s.if test="prodBranch.description!=null && prodBranch.description!=''"><img width="13" height="13" title="${prodBranch.withOutHtmlDescription?if_exists}" src="http://pic.lvmama.com/img/new_v/ob_detail/wen.gif" class="wen"></@s.if>
                                         </td>
                                         <td <@s.if test='prodCProduct.prodProduct.hasSelfPack()'>style="display:none;"</@s.if>>
                                          <strong>(¥<font class="product_${prodBranch.prodBranchId}_price" id="product_${prodBranch.prodBranchId}_price"><@s.if test="${prodBranch.sellPrice}!=0">${prodBranch.sellPrice/100}</@s.if></font>)</strong>
                                         </td>
                                         <td>
                                            <span class="price-wrap">
                                                <em class="minus" onClick="updateOperator('${prodBranch.prodBranchId}','miuns', this)">-</em>
                                                <input seq="1" name="buyInfo.buyNum.product_${prodBranch.prodBranchId}" 
                                                    id="param${prodBranch.prodBranchId}" type="text" size="2" class="number prod-num" 
                                                    value="${prodBranch.minimum}" 
                                                    ordNum="ordNum"
                                                    onchange="updateOperator('${prodBranch.prodBranchId}','input', this)" 
                                                    minAmt="${prodBranch.minimum}" 
                                                    maxAmt="${prodBranch.maximum}" 
                                                    textNum="textNum${prodBranch.prodBranchId}" 
                                                    people="${prodBranch.adultQuantity+prodBranch.childQuantity}"
                                                    branchId="${prodBranch.prodBranchId}"/>
                                                <em class="plus" onClick="updateOperator('${prodBranch.prodBranchId}','add', this)">+</em>
                                           </span>
                                         </td>
                                     </tr>
                                 </@s.if>
                                 <!-- 非不定期-->
                                 <@s.else>
                                   <tr class="p_${prodBranch.prodBranchId}" >                
                                         <td><span title="${prodBranch.branchName}"><@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(prodBranch.branchName,8)" /></span>
                                            <@s.if test="prodBranch.description!=null && prodBranch.description!=''"><img width="13" height="13" title="${prodBranch.withOutHtmlDescription?if_exists}" src="http://pic.lvmama.com/img/new_v/ob_detail/wen.gif" class="wen"></@s.if>
                                         </td>
                                         <td>
                                            <span class="price-wrap">
                                                <em class="minus price-disable" onClick="updateOperator('${prodBranch.prodBranchId}','miuns',this)">-</em>
                                                <input seq="1" name="buyInfo.buyNum.product_${prodBranch.prodBranchId}" 
                                                    id="param${prodBranch.prodBranchId}" type="text" size="2" class="number prod-num" 
                                                    value="${prodBranch.minimum}" 
                                                    ordNum="ordNum"
                                                    onchange="updateOperator('${prodBranch.prodBranchId}','input',this)" 
                                                    minAmt="${prodBranch.minimum}" 
                                                    maxAmt="${prodBranch.maximum}" 
                                                    textNum="textNum${prodBranch.prodBranchId}" 
                                                    people="${prodBranch.adultQuantity+prodBranch.childQuantity}"
                                                    branchId="${prodBranch.prodBranchId}"/>
                                                <em class="plus" onClick="updateOperator('${prodBranch.prodBranchId}','add',this)">+</em>
                                            </span>
                                         </td>
                                         <td <@s.if test='prodCProduct.prodProduct.hasSelfPack()'>style="display:none;"</@s.if>>
                                          <span>(单价<dfn>&yen;<font class="product_${prodBranch.prodBranchId}_price" id="product_${prodBranch.prodBranchId}_price"><@s.if test="${prodBranch.sellPrice}!=0">${prodBranch.sellPrice/100}</@s.if></font></dfn>)</span>
                                         </td>
                                     </tr>
                                 </@s.else>
                                <@s.if test='prodCProduct.prodProduct.IsAperiodic()'>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td>有效期：</td>
                                        <td>${prodBranch.validBeginTime?string("yyyy-MM-dd")}至${prodBranch.validEndTime?string("yyyy-MM-dd")}</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td colspan="2">
                                            <@s.if test='prodBranch.invalidDateMemo != null && prodBranch.invalidDateMemo != ""'>
                                                (${prodBranch.invalidDateMemo})
                                            </@s.if>
                                        </td>
                                    </tr>
                                </@s.if>
                            </@s.if>
                        
                            <@s.iterator value="prodProductBranchList" var="ppb"> 
                            <!-- 期票产品，如果主类别不可售，取第一个可售类别为主类别，在此过滤 -->
                            <#if ppb.online = "true" && ppb.defaultBranch != "true" && (prodBranch != null && ppb.prodBranchId != prodBranch.prodBranchId)>
                            <!-- 不定期页面-->
                            <@s.if test='prodCProduct.prodProduct.IsAperiodic()'>
                                <tr class="p_${ppb.prodBranchId}" > 
                                     <td>
                                        <@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(#ppb.branchName,8)" />
                                        <@s.if test="#ppb.description!=null && #ppb.description!=''"><img width="13" height="13" title="${ppb.withOutHtmlDescription?if_exists}" src="http://pic.lvmama.com/img/new_v/ob_detail/wen.gif" class="wen"></@s.if>
                                     </td>
                                     <td  <@s.if test='prodCProduct.prodProduct.hasSelfPack()'>style="display:none;"</@s.if>>
                                        <strong>(¥<font class="product_${ppb.prodBranchId}_price" id="product_${ppb.prodBranchId}_price"><@s.if test="${ppb.sellPrice}!=0">${ppb.sellPrice/100}</@s.if></font>)</strong>
                                     </td>
                                     <td>
                                        <span class="minus" onClick="updateOperator('${ppb.prodBranchId}','miuns')"></span>
                                        <input seq="1" name="buyInfo.buyNum.product_${ppb.prodBranchId}" 
                                            id="param${ppb.prodBranchId}" type="text" size="2" class="number prod-num" 
                                            value="${ppb.minimum}" 
                                            ordNum="ordNum"
                                            onchange="updateOperator('${ppb.prodBranchId}','input')" 
                                            minAmt="${ppb.minimum}" 
                                            maxAmt="${ppb.maximum}" 
                                            textNum="textNum${ppb.prodBranchId}" 
                                            people="${ppb.adultQuantity+ppb.childQuantity}"
                                            branchId="${ppb.prodBranchId}"/>
                                        <span class="plus" onClick="updateOperator('${ppb.prodBranchId}','add')"></span>
                                     </td>
                                </tr>
                             </@s.if>
                             <!-- 非不定期页面-->
                             <@s.else>
                                 <tr class="p_${ppb.prodBranchId}" > 
                                     <td>
                                        <span title="${ppb.branchName}"> <@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(#ppb.branchName,8)" /></span>
                                        <@s.if test="#ppb.description!=null && #ppb.description!=''"><img width="13" height="13" title="${ppb.withOutHtmlDescription?if_exists}" src="http://pic.lvmama.com/img/new_v/ob_detail/wen.gif" class="wen"></@s.if>
                                     </td>
                                     <td>
                                      <span class="price-wrap">
                                        <em class="minus price-disable" onClick="updateOperator('${ppb.prodBranchId}','miuns',this)">-</em>
                                        <input seq="1" name="buyInfo.buyNum.product_${ppb.prodBranchId}" 
                                            id="param${ppb.prodBranchId}" type="text" size="2" class="number prod-num" 
                                            value="${ppb.minimum}" 
                                            ordNum="ordNum"
                                            onchange="updateOperator('${ppb.prodBranchId}','input',this)" 
                                            minAmt="${ppb.minimum}" 
                                            maxAmt="${ppb.maximum}" 
                                            textNum="textNum${ppb.prodBranchId}" 
                                            people="${ppb.adultQuantity+ppb.childQuantity}"
                                            branchId="${ppb.prodBranchId}"/>
                                        <em class="plus" onClick="updateOperator('${ppb.prodBranchId}','add',this)">+</em>
                                        </span>
                                     </td>
                                     <td  <@s.if test='prodCProduct.prodProduct.hasSelfPack()'>style="display:none;"</@s.if>>
                                        <span>(单价<dfn>&yen;<font class="product_${ppb.prodBranchId}_price" id="product_${ppb.prodBranchId}_price"><@s.if test="${ppb.sellPrice}!=0">${ppb.sellPrice/100}</@s.if></font></dfn>)</span>
                                     </td>
                                </tr>
                             </@s.else>
                            <@s.if test='prodCProduct.prodProduct.IsAperiodic()'>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>有效期：</td>
                                    <td>${ppb.validBeginTime?string("yyyy-MM-dd")}至${ppb.validEndTime?string("yyyy-MM-dd")}</td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td colspan="2">
                                        <@s.if test='#ppb.invalidDateMemo != null && #ppb.invalidDateMemo != ""'>
                                            (${ppb.invalidDateMemo})
                                        </@s.if>
                                    </td>
                                </tr>
                            </@s.if>          
                            </#if>             
                            </@s.iterator>
                        </table>
                    </div>
                    </@s.if>
                    <!--  end-->
                    <!--按钮-->                    
                    <@s.if test='!prodCProduct.prodProduct.hasSelfPack()'><!-- 非超级自由行-->
                        <@s.set name="nowDate" value="new java.util.Date()"></@s.set>
                        <@s.if test='!prodCProduct.prodProduct.isSellable()'>
                        <input type="button" class="immediateB_gray" value=""/>
                        </@s.if>
                        <@s.else><!-- 可售-->
                        <span class="bookerBtn">
                                <input type="submit" class="immediateB" value="" style="cursor:pointer">
                         </span>
                        </@s.else>
                    </@s.if>                   
                    <@s.else>
                        <input type="button" class="dtl_morebtn" value="" style="cursor:pointer" onClick="$('.row_2').trigger('click');"/>
                    </@s.else>                                  
                    <a class="dtl_savebtn" <@s.if test='!prodCProduct.prodProduct.IsAperiodic()'>style="display:none" </@s.if> href="javascript:addBookmark();">收藏该商品</a><br> 
                    <a class="dtl_savebtn detail-recomment" <@s.if test='!prodCProduct.prodProduct.IsAperiodic()'>style="display:none" </@s.if> href="javascript:void(0)">推荐给好友</a>           
                </dd>
              
            </dl>
            </form> 
            <@s.if test="qrFlag!=null&&qrFlag=='ticketTrue'" >         
               <div class="ewm_box ewm_box_xl"> 
                        <img src="http://www.lvmama.com/qrTicket/${prodCProduct.to.placeId!?c}.png" width="75" height="75" alt="手机订购二维码"> 
                        <p> 用驴妈妈app扫描此二维码 
                        <span>手机订购更优惠</span> 
                        </p> 
                        <span class="zhiyin"></span> 
                </div>
            </@s.if>
            <@s.if test="qrFlag!=null&&qrFlag=='routeTrue'" >         
               <div class="ewm_box ewm_box_xl"> 
                        <img src="http://www.lvmama.com/qrRoute/${prodCProduct.prodProduct.productId!?c}.png" width="75" height="75" alt="手机订购二维码"> 
                        <p> 用驴妈妈app扫描此二维码 
                        <span>手机订购更优惠</span> 
                        </p> 
                        <span class="zhiyin"></span> 
                </div>
            </@s.if>
          <p class="dtl_r_hint"></p>
          <#include "/WEB-INF/pages/product/newdetail/top/recommendToFriend.ftl">      
      </div>    
      
