     
     <link  type="text/css" rel="stylesheet"href="/testStyle/css/index.css" />
     <script src="/testStyle/js/index.js"></script>
     <script src="/testStyle/js/self_time_price_table.js"></script>      
     <script src="/testStyle/js/jquery.popup.js"></script>        
<!--内容-->                      
            <div class="tcBox">
                <div class="tcNavBox">
                    <ul class="tcNav tcNavTab">
                        <#if havePack=="true">
	                        <li class="tcNavThis" >人气套餐</li>
	                        <li >自由组合</li>
                        </#if>
                        <#if havePack!="true">
                        	<li class="tcNavThis">自由组合</li>
                        </#if>
                    </ul>
                </div>
                
               
                <div class="tcListBox">
                 <div class="zuheTop">
                 <#if havePack=="true"><a class="zuheyuding" id="zuheyuding_pack" href="javascript:void(0)"  onclick="checkJourneySelecteAndSubmit()" style="display:none"></a> </#if>
                 <#if havePack!="true"><a class="zuheyuding" id="zuheyuding_pack" href="javascript:void(0)"  onclick="checkJourneySelecteAndSubmit()"></a> </#if>
                            选择出行日期：<select name="buyInfo.visitTime" onChange="" id="quickBooker_select_3" class="quickBooker_select" style="display:none"></select>
                            <b class="zuheTimeB"><i></i><span id="zuheTimeBDate"></span>
                            <div class="zuheTimeBox">
                            	<div class="self_time_price_one" style="float: left;width: 554px;height: 627px;overflow: hidden;" data-pid="<@s.property value="prodBranch.productId" escape="false"/>" data-bid="<@s.property value="prodBranch.prodBranchId" escape="false"/>"></div>
                            </div>
                            </b>
          <span id="quickBooker1_tab2"> 出游人数：                       
<@s.if test="prodBranch!=null"> 
<form id="packOrderForm" onsubmit="return checkJourneySelecte()" action="/buy/fill.do" method="POST" name="packOrderForm" style="display:none">
					<input type="hidden"  name="buyInfo.prodBranchId" value="${prodBranch.prodBranchId}">
					<input type="hidden" id="pack_visiTime" name="buyInfo.visitTime">
					<input id="pack_adult" type="hidden"  name="buyInfo.adult">
					<input id="pack_child" type="hidden"  name="buyInfo.child">
					<input type="hidden" value="true" name="buyInfo.selfPack">
					<input id="pack_content" type="hidden" value="" name="buyInfo.content">
					</form>       
（<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(prodBranch.branchName,8)" /><@s.if test="prodBranch.description!=null && prodBranch.description!=''"><img width="13" height="13" title="${prodBranch.description}" src="http://pic.lvmama.com/img/new_v/ob_detail/wen.gif" class="wen"></@s.if>）
                    <samp class="zuheNumber" >
                            <span class="numberJian" onclick="updateOperator('${prodBranch.prodBranchId}','miuns');this.blur()" ></span>
                            	<input seq="1" name="buyInfo.buyNum.product_${prodBranch.prodBranchId}" 
					                id="param${prodBranch.prodBranchId}" type="text" size="2" class="number prod-num free_input" 
					                value="${prodBranch.minimum}" 
					                ordNum="ordNum"
					                onchange="updateOperator('${prodBranch.prodBranchId}','input')" 
					                minAmt="${prodBranch.minimum}" 
					                maxAmt="${prodBranch.maximum}" 
					                textNum="textNum${prodBranch.prodBranchId}"
					                branchTypes="${prodBranch.branchType}" 
					                people="${prodBranch.adultQuantity+prodBranch.childQuantity}"
					                branchId="${prodBranch.prodBranchId}"/>
                        	<span class="numberJia" onclick="updateOperator('${prodBranch.prodBranchId}','add');this.blur()"></span>
                   </samp>
</@s.if>
<@s.iterator value="prodProductBranchList" var="ppb"> 
 <#if ppb.online = "true" && ppb.defaultBranch != "true">
<span>
（<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(#ppb.branchName,8)" /><@s.if test="#ppb.description!=null && #ppb.description!=''"><img width="13" height="13" title="${ppb.description}" src="http://pic.lvmama.com/img/new_v/ob_detail/wen.gif" class="wen"></@s.if>）
				<samp class="zuheNumber">
                            <span class="numberJian" onclick="updateOperator('${ppb.prodBranchId}','miuns');this.blur()" ></span>                            	
                            	<input seq="1" name="buyInfo.buyNum.product_${ppb.prodBranchId}" 
					                id="param${ppb.prodBranchId}" type="text" size="2" class="number prod-num free_input" 
					                value="${ppb.minimum}" 
					                ordNum="ordNum"
					                onchange="updateOperator('${ppb.prodBranchId}','input')" 
					                minAmt="${ppb.minimum}" 
					                maxAmt="${ppb.maximum}" 
					                textNum="textNum${ppb.prodBranchId}" 
					                branchTypes="${ppb.branchType}"
					                people="${ppb.adultQuantity+ppb.childQuantity}"
					                branchId="${ppb.prodBranchId}"/>
                        	<span class="numberJia" onclick="updateOperator('${ppb.prodBranchId}','add');this.blur()"></span>
                   </samp>
</#if>    		   
</@s.iterator>
</span>
 </div>
                    <!--人气套餐BOX-->
                    <div class="taocanBox tcNavList" id="tapcamBox" <#if havePack!="true"> style="display:none;"</#if>>
                        
                    </div>          
                    <!--自由组合BOX-->
                    <div class="zuheBox tcNavList" id="zuheBox" <#if havePack!="true"> style="display:block;"</#if>> 
                        <div class="zuheDay" id="superFreeDetail">                            
							<#include "/WEB-INF/pages/product/newdetail/buttom/selfPack/OJViewDetail.ftl">
                        </div>
                        <div class="zuhe_B">
                        	<a class="zuheyuding" href="javascript:void(0)" onclick="checkJourneySelecteAndSubmit()"></a>
                            <p class="zuhe_zj">总价<b><small>￥</small><span id="freeTotalPrices"></span></b></p>
                            <p class="zuhe_yh" style="display:none">已优惠<span id="freePreferential"></span>元</p>
                        </div>
                    </div>
                </div>
            </div>
 