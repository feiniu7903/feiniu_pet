	<div id="s2-site-nav">
	<span>您当前所处的位置：</span>
    	<ul class="quick-menu">
        	<@s.iterator value="placesBlocks.navigation" status="sta" var="nav">
        	<li>
        	<@s.if test='defaultUrl!=null'>
            	<a class="menu-hd" href="<@s.property value="defaultUrl"/>" target="_top"><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(15,name)" escape="false" /><b></b></a>
			</@s.if>	
            <@s.else>
            	<a class="menu-hd" href="http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>" target="_top"><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(15,name)" escape="false" /><b></b></a>
            </@s.else>
            	<div class="menu-bd">
	                    <@s.if test='#sta.index==0&&defaultUrl!=null'>
	                    	<a href="http://www.lvmama.com/public/europe" target="_top">欧洲</a>
	                        <a href="http://www.lvmama.com/public/asia" target="_top">亚洲</a>
	                        <a href="http://www.lvmama.com/public/africa" target="_top">非洲</a>
	                        <a href="http://www.lvmama.com/public/northAmerica" target="_top">北美洲</a>
	                        <a href="http://www.lvmama.com/public/Oceania" target="_top">大洋洲</a>
	                    </@s.if>
	                     <@s.else>
				               <@s.iterator value="placesBlocks.brothers.get(placeId)">
				               		<@s.if test="defaultUrl!=null">
				               				<@s.if test='name==#nav.name'>
	                        				<a href="<@s.property value="defaultUrl"/>" target="_top">
			                            			 <span class="current-pla"><@s.property value="name" escape="false"/></span></a>
	                           		  		  </@s.if>
	                           		  		  <@s.else>
	                           		  		  		<a href="<@s.property value="defaultUrl"/>" target="_top"><@s.property value="name" escape="false"/></a>
	                           		  		  </@s.else>
	                        		</@s.if>
	                        		<@s.else>
	                        				
	                        				<@s.if test='name==#nav.name'>
			                            			 <a href="http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>" target="_top"><span class="current-pla"><@s.property value="name"  escape="false"/></span></a>
	                           		  		  </@s.if>
	                           		  		  <@s.else>
	                           		  		  		<a href="http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>" target="_top"><@s.property value="name" escape="false"/></a>
	                           		  		  </@s.else>
	                        				
	                        		</@s.else>
	                        </@s.iterator>     
	                    </@s.else>
	            </div>
            </li>
        	 </@s.iterator>
        	<li class="last"><@s.property escape="false" value="@com.lvmama.comm.utils.StringUtil@subStringStr(prodCProduct.prodProduct.productName,15)" /></li>
        </ul>
        <span class="pro_number">产品编号:<strong><@s.property value="prodCProduct.prodProduct.productId"/></strong></span>

    </div>          
