<div class="main">
    <div class="hr_a"></div>
    <div class="c-title">
        <h3>您预订：${order.mainProduct.productName?if_exists}</h3>
        <div class="total-price">
            <span class="price-num"><dfn>${order.oughtPayYuanFloat}</dfn> 元</span>
            <strong>订单金额：</strong>
        </div>
    </div>
    
    <!-- 订单列表 -->
    <div class="order-list hide">
        <div class="dot_line"></div>
        
        <!-- 订单信息 -->
        <div class="c-title"><h4>订单信息</h4></div>
        <table class="table-full order-buy">
            <thead>
                <tr>
                    <th>订单号</th>
                    <th>游玩日期</th>
                   <@s.if test='order.orderType == "HOTEL"'>    	
				    </@s.if>
				    <@s.elseif test='order.orderType =="TICKET"'>
				    </@s.elseif>
				    <@s.else>
					    <th>游玩人数</th>
				    </@s.else>
                    <th>产品信息</th>
                </tr>
            </thead>
            <tbody class="table-vtop">
                <tr>
                    <td><@s.property value="order.orderId"/></td>
                    <td>
						<@s.if test="!order.IsAperiodic()">
					    	<#if order.orderType == 'HOTEL'>
					    		<@s.iterator value="order.ordOrderItemProds">   
					    			<@s.if test="productType=='HOTEL'">           						
					              	<@s.property value="dateRange"/>
					              	</@s.if>              					
					            </@s.iterator>
					    	<#else>
					    		<@s.date   name= "order.visitTime" format= "yyyy-MM-dd" />
					    	</#if>
				    	</@s.if>
				    	<@s.else>
				    		未确定
				    	</@s.else>
					</td>
                    <#assign personCount = 0 >
				    <@s.if test='order.orderType == "HOTEL"'>
				    </@s.if>
				    <@s.elseif test='order.orderType =="TICKET"'>
				    </@s.elseif>
				    <@s.else>
				    	<@s.iterator value="order.travellerList">
				        	<@s.if test='personType=="TRAVELLER"'>
				        	   	<#assign personCount = personCount + 1>
				        	</@s.if>
				     	</@s.iterator>
						<td>${personCount}人</td>
				    </@s.else>
                    <td>
                    	<@s.iterator value="mainOrderList">
						<input type="hidden" sellName="sellName" cashRefund=""  marketPrice="${marketPriceYuan}" sellPrice="${sellPriceYuan}" value="1"    />
						<span <@s.if test="order.hasSelfPack()">class="selfPack"</@s.if>><@s.property value="productName"/></span>
						    <em>&nbsp;&nbsp;&nbsp;&nbsp;×
						        <#if subProductType=='SINGLE_ROOM'>
									<@s.property value="quantity/days"/>
								<#else>
									<@s.property value="quantity"/>
								</#if>
							</em><br/>    
						</@s.iterator>
						<@s.if test="order.hasSelfPack()">
						<div id="relat_product_list" style="display:none;position:absolute;background-color:#fff;border:1px solid #ccc;width:359px;padding:10px;text-align:left">
						<span style="font-weight:bold">包含产品:</span><br/>
						</@s.if>
						<@s.iterator value="relativeOrderList"> 
							<input type="hidden" id="param${productId}" name="paramName" minAmt="${minimum}" maxAmt="<#if (maxinum>0) >${maxinum}<#else>${stock}</#if>" textNum="textNum${productId}" people="${adultQuantity+childQuantity}" buyPeopleNum=""/>  	  
			        	    <p><@s.property value="productName"/>&nbsp;&nbsp;<span class="num">&times;<i class="price"><@s.property value="quantity"/></i></span>
			        	</@s.iterator>
			        	<@s.iterator value="additionalOrderList"> 
							<input type="hidden" id="param${productId}" name="paramName" minAmt="${minimum}" maxAmt="<#if (maxinum>0) >${maxinum}<#else>${stock}</#if>" textNum="textNum${productId}" people="${adultQuantity+childQuantity}" buyPeopleNum=""/>  	  
			        	    <p><@s.property value="productName"/>&nbsp;&nbsp;<span class="num">&times;<i class="price"><@s.property value="quantity"/></i></span>
			        	</@s.iterator>
			        	<@s.if test="order.hasSelfPack()">
						</div>
						</@s.if>
                        <!--<p>全场满500减20优惠券&nbsp;&nbsp;<span class="num save-price"><dfn>- &yen;<b>50</b></dfn></span></p>-->
                    </td>
                </tr>
            </tbody>
        </table>
    </div> <!-- //订单列表 -->
    <div class="hr_c"></div>
    <span class="view-details slidedown-orderlist active">
       	 订单详情<i class="ui-arrow-bottom white-ui-arrow-bottom"></i>
    </span>
</div>