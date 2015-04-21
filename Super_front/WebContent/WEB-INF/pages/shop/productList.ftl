<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title>积分商城—驴妈妈旅游网</title>
<meta name="keywords" content="" />
<meta name="description" content="">



<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/v3/module.css" > 
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_common/ui-components.css,/styles/v3/points.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/v3/widget.css" />
<#include "/common/coremetricsHead.ftl">
</head>
<body>

	<!-----------头部文件区域 S-------------->
	<#include "/common/header.ftl">

	<!-- wrap\\ 1 -->
	<div class="wrap">
		<div class="lv-crumbs oldstyle">
			<p>
				<span>您当前所处的位置：</span>
				<span class="crumbs-arrow"></span>
				<span><a href="/points">积分商城</a></span>
				<span class="crumbs-arrow"></span>
				<span>全部商品</span>
			</p>
		</div>

		<!----------------------------mainLeft-------------------------->
		<#include "/WEB-INF/pages/shop/mainLeft.ftl">

		<!----------------------------mainRight-------------------------->
		<div class="col-w fr">
			<div class="point-type border">
				<dl class="dl-hor">
					<dt>积分范围：</dt>
					<dd>
						<a
							href="../shop/moreProductByType.do?productType=${productType}&changeType=${changeType}&purchasingPower=N"<#if
							startPoints == null &&
							purchasingPower=='N'>class="selected"</#if>>不限</a></span> 
						<a
							href="../shop/moreProductByType.do?startPoints=0&endPoints=100&productType=${productType}&changeType=${changeType}&purchasingPower=N"<#if
							startPoints=='0' && purchasingPower=='N'>class="selected"</#if>
							>0-100</a> 
						<a
							href="../shop/moreProductByType.do?startPoints=101&endPoints=500&productType=${productType}&changeType=${changeType}&purchasingPower=N"<#if
							startPoints=='101'>class="selected"</#if>>101-500</a> 
						<a
							href="../shop/moreProductByType.do?startPoints=501&endPoints=1000&productType=${productType}&changeType=${changeType}&purchasingPower=N"<#if
							startPoints=='501'>class="selected"</#if>>501-1000</a> 
						<a
							href="../shop/moreProductByType.do?startPoints=1001&endPoints=5000&productType=${productType}&changeType=${changeType}&purchasingPower=N"<#if
							startPoints=='1001'>class="selected"</#if>>1001-5000</a> 
						<a
							href="../shop/moreProductByType.do?startPoints=5001&productType=${productType}&changeType=${changeType}&purchasingPower=N"<#if
							startPoints=='5001'>class="selected"</#if>>5000以上</a> <#if shopUser??> 
						<a
							href="../shop/moreProductByType.do?startPoints=0&productType=${productType}&changeType=${changeType}&purchasingPower=Y"<#if
							purchasingPower=='Y'>class="selected"</#if>>我能兑换的商品</a> </#if>

					</dd>
				</dl>

				<dl class="dl-hor">
					<dt>商品类型：</dt>
					<dd>
						<a
							href="../shop/moreProductByType.do?startPoints=${startPoints}&endPoints=${endPoints}&purchasingPower=${purchasingPower}"<#if
							changeType==null && productType==null>class="selected"</#if>>不限</a></span>
						<a
							href="../shop/moreProductByType.do?startPoints=${startPoints}&endPoints=${endPoints}&productType=COUPON&changeType=POINT_CHANGE&purchasingPower=${purchasingPower}"<#if
							productType=='COUPON'>class="selected"</#if>>驴妈妈优惠券</a> 
						<a
							href="../shop/moreProductByType.do?startPoints=${startPoints}&endPoints=${endPoints}&productType=COOPERATION_COUPON&changeType=POINT_CHANGE&purchasingPower=${purchasingPower}"<#if
							productType=='COOPERATION_COUPON'>class="selected"</#if>>其它网站优惠券</a>
						<a
							href="../shop/moreProductByType.do?startPoints=${startPoints}&endPoints=${endPoints}&productType=PRODUCT&changeType=POINT_CHANGE&purchasingPower=${purchasingPower}"<#if
							productType=='PRODUCT'>class="selected"</#if>>实物商品</a> 
						<a
							href="../shop/moreProductByType.do?startPoints=${startPoints}&endPoints=${endPoints}&changeType=RAFFLE&purchasingPower=${purchasingPower}"<#if
							changeType=='RAFFLE'>class="selected"</#if>>抽奖商品</a>
					</dd>
				</dl>
			</div>
			<!--filter end-->

			<div class="points-box point_1">

				<div class="ptitle">
					<h4>筛选结果</h4> 
				</div>
				<div class="content JS_hover_show">
					<ul class="clearfix">
						<@s.iterator value="productList" var="product">
						<li class="point-item"><#if product.beginTime??><span
							class="countdown">${product.lifeTime}</span></#if> <a class="img" href="/points/prod/${product.productId}"><i
								class="middle-full"></i><img
								src="${product.getFirstAbsolutePictureUrl()}"
								alt="${product.productName}" /></a>
							<p>
								<a href="/points/prod/${product.productId}"><@s.property
									value="@com.lvmama.comm.utils.StringUtil@subStringStr(productName,18)"/></a>
							</p>
							<p>
								<a href="/points/prod/${product.productId}"
									class="btn btn-mini btn-orange">&nbsp;兑换&nbsp;</a>所需积分：
								<dfn>
									<i>${product.pointChange}</i>分
								</dfn>
							</p>

						</li> </@s.iterator>
					</ul>
				</div>
				<div class="page" style="margin: 10px; text-align: right;">
					<@s.property escape="false"
					value="@com.lvmama.comm.utils.Pagination@pagination(page.totalPageNum,page.totalPageNum,page.url,page.currentPage)"/>
				</div>
			</div>
			<!-- //.col-w -->
		</div>
		<!-- // col-w fr -->
	</div>
	<!-- //.wrap 1 -->

	<!-----------底部文件区域 S -------------->
	<#include "/common/orderFooter.ftl">
	
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/v3/plugins.js,/js/v3/app.js"></script> 
<script src="http://pic.lvmama.com/min/index.php?f=/js/v3/slides.jquery.js,/js/v3/jcarousellite.min.js,/js/v3/points.js"></script> 
<script src="http://pic.lvmama.com/js/common/losc.js"></script> 
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/v3/lvmm_pop.js"></script>
<script>
      cmCreatePageviewTag("奖品列表", "D1001", null, null);
 	</script>
</body>
</html>

