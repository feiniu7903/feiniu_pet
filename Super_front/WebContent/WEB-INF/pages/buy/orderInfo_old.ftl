  	<div class="order-info">
  	 <h3>订单信息</h3>   
  	 <dl class="part1">    	 
        <dt>订单号：</dt>
        	<dd class="order-id"><@s.property value="order.orderId"/></dd>
        <dt>结算金额：</dt>
        	<dd><strong>&yen;${order.oughtPayYuan?if_exists}</strong></dd>
    	<dt>支付方式：</dt>
        	<dd><@s.if test='order.paymentTarget=="TOLVMAMA"'>在线支付</@s.if>
        	    <@s.else>
                  		<#if order.orderType == 'HOTEL'>
                  			酒店前台支付
                  		<#else>
							景区支付                  		
                  		</#if>
                 </@s.else>
             </dd>
        <dt>共返奖金：</dt>
        <dd>
            <strong>&yen;<#if order.cashRefund??>${order.cashRefund/100}<#else>0</#if></strong></em>
            &nbsp;<span>(游玩归来后发表点评返还，<a href="http://www.lvmama.com/public/comment_system#m_6" target="_blank" class="underline">奖金说明&raquo;</a>)</span>
        </dd>  
      </dl>

      <dl class="part2">
    	  <dt>您订购的是：</dt>    	    
          <dd class="title">
        	   <@s.iterator value="mainOrderList">
        	   <input type="hidden" sellName="sellName" cashRefund=""  marketPrice="${marketPriceYuan}" sellPrice="${sellPriceYuan}" value="1"    />
        	      <@s.property value="productName"/>
        	                 <em>&nbsp;&nbsp;&nbsp;&nbsp;×
        	                            <#if subProductType=='SINGLE_ROOM'>
				                           <@s.property value="quantity/days"/>
				                        <#else>
				                           <@s.property value="quantity"/>
				                        </#if>
				              </em><br/>    
				   </@s.iterator> 
				   <@s.iterator value="relativeOrderList"> 
				   		<input type="hidden" id="param${productId}" name="paramName" minAmt="${minimum}" maxAmt="<#if (maxinum>0) >${maxinum}<#else>${stock}</#if>" textNum="textNum${productId}" people="${adultQuantity+childQuantity}" buyPeopleNum=""/>  	  
        	       		<@s.property value="productName"/><em>&nbsp;&nbsp;&nbsp;&nbsp;×<@s.property value="quantity"/></em>
        	       </@s.iterator>
          </dd>
        	
          <!--附加产品S-->
          <@s.if test="additionalOrderList.size>0">
    	    <dt>附加产品：</dt>    	    
        	<dd class="title">
        			<@s.iterator value="additionalOrderList">
        			<input type="hidden" name="buyInfo.buyNum.product_${productId}"  id="addition${productId}" sellName="sellName" cashRefund=""  marketPrice="${marketPriceYuan?if_exists}" sellPrice="${sellPriceYuan?if_exists}" value="0"    />
        			<span><@s.property value="productName"/></span>
        			<em>&nbsp;&nbsp;&nbsp;&nbsp;×<@s.property value="quantity"/></em><br />
        			</@s.iterator>
        	 </dd>
          </@s.if>
          <!--附加产品E-->
          <@s.if test="!order.IsAperiodic()">
	          <!--订单履行日期S-->
	          <#if order.orderType == 'HOTEL'>
	              <dt>入住日期：</dt>
	              <dd class="date"><@s.iterator value="order.ordOrderItemProds">              						
	              							<@s.property value="dateRange"/>              					
	              			       </@s.iterator>
	              </dd>
	          <#else>
	             <dt> 游玩日期：</dt>
	             <dd class="date"><@s.date   name= "order.visitTime"   format= "yyyy年MM月dd日 " /></dd> 
	          </#if>     
	          <!--订单履行日期E-->
          </@s.if>
     </dl>
          
    <!--订单用户信息S-->
    <dl class="part1">
    <@s.if test='order.orderType == "HOTEL"'>
      <@s.iterator value="order.personList">
        <@s.if test='personType=="CONTACT"'>	          		
                  <dt>入住人姓名：</dt>
                  <dd><@s.property value="name"/> </dd>
                  <dt>入住人手机号：</dt>
                  <dd><@s.property value="mobile"/></dd>		   
        </@s.if>
      </@s.iterator>
     </@s.if>
     <@s.elseif test='order.orderType =="TICKET"'>           
      <@s.iterator value="order.personList">
        <@s.if test='personType=="CONTACT"'>          	       	       
                  <dt>取票人姓名：</dt>
                  <dd><@s.property value="order.contact.name"/> </dd>
                  <dt>取票人手机号：</dt>
                  <dd><@s.property value="order.contact.mobile"/></dd>	             
        </@s.if>
     </@s.iterator>  
     </@s.elseif>
     <@s.else>  
              
     <@s.iterator value="order.travellerList">
        <@s.if test='personType=="TRAVELLER" && certNo!=null'>          	        			
                  <dt>游玩人姓名：</dt>
                  <dd><@s.property value="name"/> </dd>
                  <dt>游玩人证件：</dt>
                  <dd><@s.property value="certNo"/></dd>				   		 
        </@s.if>
     </@s.iterator>
     </@s.else>
     <!--
     <@s.iterator value="order.personList">
             <@s.if test='personType=="BOOKER"'>		    
                  <dt>订票人姓名：</dt>
                  <dd><@s.property value="name"/> </dd>
                  <dt>订票人手机号：</dt>
                  <dd><@s.property value="mobile"/></dd>
              </@s.if>
         </@s.iterator>
     -->  
     </dl>
    <!--订单用户信息E-->   	
    </div>
 </div>


