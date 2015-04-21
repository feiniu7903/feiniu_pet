  <ul class="list">
  <@s.iterator value="productListRecommend" var="product">
    <li>
        <dfn>&yen;<i>${product.sellPriceInteger}</i>起</dfn>
        <a class="tv_link" href="http://www.lvmama.com${product.productUrl}" target="_blank" title="${product.productName}">
        <#if (productName?length>32)>
           ${product.productName?substring(0,32)}...
        <#else>
           ${product.productName}
        </#if>
        
        <@s.if test="tagsImage!=null"><@s.iterator value="tagsImage.split(',')" status="tag"><span class="icon<@s.property/>"></span></@s.iterator></@s.if>
        </a>
        <p>${product.recommendInfoSecond}</p>
    </li>
  </@s.iterator>
  </ul>
