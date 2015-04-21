<@s.iterator value="city" status="st">
	            	<dt><a href="${url?if_exists}" title="${title?if_exists}" target="_blank">共${num?if_exists} 款自由行产品</a><strong><span>${st.index+1}</span>${title?if_exists}</strong></dt>
	                <dd <#if (st.index==0)>class="showit"</#if>>
	                	<ul>
	                	
	                			<@s.iterator value="cityProduct.get(#st.index)" status="sta">
	                				<#if sta.index==0>
				                    	 <li class="first_li" ><a href="${url?if_exists}" target="_blank" title="${title?if_exists}"><img src="<#if imgUrl?exists && imgUrl!="">http://pic.lvmama.com${imgUrl}</#if>" alt="${title?if_exists}" /></a> 
				                    	<br /><strong>¥${memberPrice?if_exists?replace(".0","")}</strong><a href="${url?if_exists}" target="_blank" title="${title?if_exists}"><@s.property value='@com.lvmama.eshop.util.StringUtil@cutString(13,title)'/></a></li>
			                    	</#if>
			                    	<#if (sta.index>0)>
			                    		<li><strong>¥${memberPrice?if_exists?replace(".0","")}</strong><a href="${url?if_exists}" target="_blank" title="${title?if_exists}"><@s.property value='@com.lvmama.eshop.util.StringUtil@cutString(13,title)'/></a></li>
			                    	</#if>
			                    </@s.iterator>    
	                    </ul>
	                </dd>
</@s.iterator> 