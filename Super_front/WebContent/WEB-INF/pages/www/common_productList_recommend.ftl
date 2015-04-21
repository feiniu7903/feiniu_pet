<dl>
	<dt>
		<a href="http://www.lvmama.com<@s.property value="productSearchInfo.productUrl" />" title="<@s.property value="productSearchInfo.productName" />" target="_blank">
			<@s.if test="productSearchInfo.smallImage!=null">
				<img src="http://pic.lvmama.com/pics/<@s.property value="productSearchInfo.smallImage" />" height="60" width="120" />
			</@s.if>
			<@s.else>
				<img src="http://pic.lvmama.com/img/cmt/img_120_60.jpg" height="60" width="120" />
			</@s.else>
		</a>
	</dt>
	<dd style="height: 40px; _height: 36px;">
		<a href="http://www.lvmama.com<@s.property value="productSearchInfo.productUrl" />" title="<@s.property value="productSearchInfo.productName" />" target="_blank"><@s.property value="productSearchInfo.productName" /></a>
		<#include "/WEB-INF/pages/www/common_productTag.ftl">
	</dd>
	<!-- <dd class="cpl_dd2 gray">
		<@s.property value="productSearchInfo.recommendInfoFirst" />
	</dd> -->
	<dd>
		<strong>&yen;<b><@s.property value="productSearchInfo.sellPrice/100" /></b>起</strong>
	</dd>
</dl>
