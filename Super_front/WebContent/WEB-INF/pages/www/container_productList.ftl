  <ul class="list">
 <@s.iterator value="productListRecommend" var="product">
	  <li>
	    <dfn>&yen;<i>${product.sellPriceInteger}</i>起</dfn>
	    <em class="tv_show_calendar time-price" linkType="product" onlyMark="t0" onlyindex="${product.product_index}" data-bid="" data-pid="${product.productId}">团期</em>
	    <a class="tv_link" href="${product.productUrl}" target="_blank" title="${product.productName}">
	    <#if (product.productName?length>32)>
	       ${product.productName?substring(0,32)}...
	    <#else>
	       ${product.productName}
	    </#if>
	   <@s.if test="tagsImage!=null"><@s.iterator value="tagsImage.split(',')" status="tag"><span class="icon<@s.property/>"></span></@s.iterator></@s.if>
	    </a>
	    <p>${product.recommendInfoSecond}</p>
	    <div id="t0-timePrice${product.productId}-${product.product_index}" style="display:none;"></div>
	  </li>
	  </@s.iterator>
  </ul>
