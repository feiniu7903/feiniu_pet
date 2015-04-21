<h4>今日其他团购</h4>
<@s.iterator value="otherPrdList" status="groupPrd">
<ul class="bottom-border group-list">
                <li><a href="/product/<@s.property value="prodProduct.productId"/>"><@s.property value="prodProduct.productName" escape="false"/></a></li>
                <li><a href="/product/<@s.property value="prodProduct.productId"/>"><img src="<@s.property value="prodProduct.absoluteSmallImageUrl"/>" width="200px" height="100px"/></a></li>
                <li class="group-link">
                	市场价：<del>&yen;${prodProduct.marketPriceYuan?if_exists}</del><br />
                    团购：<strong><em>&yen;</em>${prodProduct.sellPriceYuan?if_exists}</strong>
                    <a class="group-bg" href="/product/<@s.property value="prodProduct.productId"/>" target="_self">去看看</a>
                </li>
</ul>
</@s.iterator>
