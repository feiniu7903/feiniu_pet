            <#if !prodCProduct.prodProduct.hasSelfPack()>              
             <div class="scroll_pro scroll_mar">
             	 <#if prodCProduct.prodProduct.productType == "TICKET">
        			<#assign formSubmitUrl = "/orderFill/ticket-${prodCProduct.prodProduct.productId}"> 
        		 </#if>	
                 <form method="post" action="${formSubmitUrl!"/buy/fill.do"}" onsubmit="return beforeSubmit(this);">
                  <input type="hidden" id="productIdHidden" name="buyInfo.productId" value="${prodCProduct.prodProduct.productId}"></input>
                  <input type="hidden" id="productBranchIdHidden" name="buyInfo.prodBranchId" value="<#if prodBranch!=null>${prodBranch.prodBranchId}</#if>"></input>
                  <input type="hidden" name="buyInfo.productType" value="${prodCProduct.prodProduct.productType?if_exists}"></input>
                  <input type="hidden" name="buyInfo.subProductType" value="${prodCProduct.prodProduct.subProductType?if_exists}"></input> 
					<ul class="quick-book-ul" >
						<li class="quick-book-ul_li1">
                        <#if prodCProduct.prodProduct.productType == "HOTEL">            
                        	入住日期：
                        <#else>
                        	游玩日期：
                        </#if>
                    	</li>    
	                    <li class="quick-book-ul_li2">
	                       <select name="buyInfo.visitTime" onChange="" id="quickBooker_select_2" class="quickBooker_select">
	                        </select>
	                        <div class="zxerror quick-error" style="top: 13px; left: 293px;">
                                <span class="zxerror-text">
                                    <div class="error-arrow">
                                        <em>◆</em>
                                        <i>◆</i>
                                    </div>
                                    <p>请选择游玩日期</p>
                                </span>
                            </div>
	                    </li>
	                    <li id="quickBooker2_tab2" class="quick-book-ul_li3">
	                    </li>
	                    <li class="quick-book-ul_li4">
	                        <span class="current_balance">当前价格：</span>
	                        <strong><span>¥</span><b id="SUM">0</b></strong> 
	                   </li>
	                   <li class="quick-book-ul_li5">
		                    <@s.set name="nowDate" value="new java.util.Date()"></@s.set>
	                        <@s.if test='!prodCProduct.prodProduct.isSellable()'>
	                        	<input type="button" class="immediateB_gray" value=""/>
	                        </@s.if>
	                        <@s.else>
	                        	<input class="immediateB" type="submit" style="cursor:pointer" value="">
	                        </@s.else>
	                   </li>
                   </ul>
               </form>
             </div>
             </#if>
