<div class="aside_box c_shadow">
       	 	<h4 class="c_iconbg reflect"><strong><@s.property value="place.name"/>相关产品</strong></h4>
     <div class="c_w_relpro">
            <h4>门票<s></s></h4>
            <ul>
            <@s.if test="productList.productTicketList.size>0">
            	<@s.iterator value="productList.productTicketList">
		            <li>
		            	<a href="http://www.lvmama.com<@s.property value="productUrl"/>" target="_blank" >
		            		<@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(18,productName)"/>
		            	</a>
		            	<span>¥<@s.property value="sellPriceInteger"/></span>
		            	
		            </li>
		         </@s.iterator>
            </@s.if>
            </ul>
            <h4>自由行<s></s></h4>
            <ul>
           <@s.if test="productList.productSinceList.size>0">
            	<@s.iterator value="productList.productSinceList">
		            <li>
		            	<a href="http://www.lvmama.com<@s.property value="productUrl"/>" target="_blank" >
		            		<@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(18,productName)"/>
		            	</a>
		            	<span>¥<@s.property value="sellPriceInteger"/></span>
		            </li>
		         </@s.iterator>   
            </@s.if>
            </ul>
            <h4>跟团游<s></s></h4>
            <ul>
             <@s.if test="productList.productRouteList.size>0">
            	<@s.iterator value="productList.productRouteList">
		            <li>
		            	<a href="http://www.lvmama.com<@s.property value="productUrl"/>" target="_blank" >
		            		<@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(18,productName)"/>
		            	</a>
		            	<span>¥<@s.property value="sellPriceInteger"/></span>
		            </li>
		         </@s.iterator>   
            </@s.if>
            </ul>
      </div>
 </div><!--aside_box end-->
        
