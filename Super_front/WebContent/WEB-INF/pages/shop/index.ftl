<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>积分商城—驴妈妈旅游网</title>

<meta name="keywords" content="积分商城—驴妈妈旅游网"/>
<meta name="description" content="积分商城—驴妈妈旅游网">

<!-- <?php include("common/meta.html"); ?> -->
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/v3/module.css" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_common/ui-components.css,/styles/v3/points.css">
<#include "/common/coremetricsHead.ftl">
</head>
<body>

<!-- <?php include("common/header.html"); ?> -->
<#include "/common/header.ftl">

<div class="hr_a"></div>

<!-- wrap\\ 1 -->
<div class="wrap">
	
	<!-- 左边 -->
	<div class="aside fl">
	    <!-- 我的积分 -->
		<#if shopUser??>
			<div class="side-box border sidebox1">
				<div class="stitle">
					<h4>我的积分</h4>
				</div>
				<div class="content">
					 <p>您好！<@s.property value="shopUser.userName"/><br>
                		<span class="gray">可用积分余额：<dfn><i><@s.property value="shopUser.point"/></i>分</dfn></span>
                	  </p>
					  <p class="line">
					  	<a href="http://www.lvmama.com/myspace/account/points.do" target="_blank">查看积分明细</a>
              			<a href="http://www.lvmama.com/myspace/account/points_order.do" target="_blank">兑换记录</a>
		             </p>
				</div>
			</div>
		<#else>
			<div class="side-box border sidebox1">
				<div class="stitle">
					<h4>积分商城</h4>
				</div>
				<div class="content">
				    <p class="tc">
				    	<button class="btn btn-pink" onclick="userLogin();" >&nbsp;&nbsp登录&nbsp;&nbsp;</button> 
			    		<button class="btn" onclick="window.open('http://login.lvmama.com/nsso/register/registering.do','_blank');">免费注册</button>
				    </p>
					<p class="line gray">登录后，可查看您的积分余额，用积分兑换商品、参与抽奖</p>
				</div>
			</div> 
		</#if>
		
		<!-- 我的积分  end-->
		 <div class="side-box">
            <div class="winners"><span class="notice"></span>
                <div id="marquee0" class="mqrquee">
                    <ul>
                    	<@s.iterator value="shopIndexPageMap.get('${station}_user')" status="sts">
                    		<li><a target="_blank" href="${url?if_exists}">${title?if_exists}</a></li>
		          		</@s.iterator> 
                    </ul>
                </div>
            </div>
        </div>
							        
		<div class="side-box border sidebox2">
			<div class="side-title">
				<h4><a target="_blank" href="http://www.lvmama.com/public/help_301">帮助中心</a></h4>
			</div>
			<div class="content">
				<ul class="ul_list">
					<li>·<a target="_blank" href="http://www.lvmama.com/public/help_center_209">积分能做什么？</a></li>
					<li>·<a target="_blank" href="http://www.lvmama.com/public/help_center_210">如何赚取积分？</a></li> 
					<li>·<a target="_blank" href="http://www.lvmama.com/public/help_center_211">积分有效期是多长？</a></li>
					<li>·<a target="_blank" href="http://www.lvmama.com/public/help_center_213">积分消费说明？</a></li>
				</ul>
			 </div>
		 </div>
							        
		<!-- 热门兑换排行 -->
		<div class="side-box border sidebox3">
            <div class="side-title">
                <h4>热门兑换排行</h4>
            </div>
            <div class="content JS_hover_select">
            	<@s.iterator value="topProductList" var="topProduct" status="sts">
            		<@s.if test="#sts.index==0"><dl class="selected"></@s.if>
		          	<@s.else><dl class=""></@s.else>
					<dt><@s.property value="#sts.index+1"/></dt>
					<dd>
                        <h6>
                        	<a target="_blank" href="/points/prod/${topProduct.productId}" title="${topProduct.productName}" >
                        		<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(productName,13)"/>
                        	</a>
                        </h6>
                        <p>所需积分：<dfn><i>${topProduct.pointChange}</i>分</dfn></p>
                        <a target="_blank" href="/points/prod/${topProduct.productId}"  class="img" title="${topProduct.productName}" >
                        	<img src="${topProduct.getFirstAbsolutePictureUrl()}" title="${topProduct.productName}" />
                        </a>
                    </dd>
					</dl>
            	</@s.iterator>
            </div>
        </div>
		<!-- 热门兑换排行 end -->	
	</div>	
			
      <div class="col-w fr">
      			<!-- 焦点图 
      			<div class="imgFocus"></div>
      			--> 
      			<div id="slides" class="slide-box">
							<ul class="slide-content">
								<li><script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxM3xwb2ludF8yMDEzfHBvaW50XzIwMTNfZm9jdXMwMSZkYj1sdm1hbWltJmJvcmRlcj0wJmxvY2FsPXllcyZqcz1pZQ==" charset="gbk"></script></li>
								<li><script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxM3xwb2ludF8yMDEzfHBvaW50XzIwMTNfZm9jdXMwMiZkYj1sdm1hbWltJmJvcmRlcj0wJmxvY2FsPXllcyZqcz1pZQ==" charset="gbk"></script></li>
								<li><script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxM3xwb2ludF8yMDEzfHBvaW50XzIwMTNfZm9jdXMwMyZkYj1sdm1hbWltJmJvcmRlcj0wJmxvY2FsPXllcyZqcz1pZQ==" charset="gbk"></script></li>
								<li><script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxM3xwb2ludF8yMDEzfHBvaW50XzIwMTNfZm9jdXMwNCZkYj1sdm1hbWltJmJvcmRlcj0wJmxvY2FsPXllcyZqcz1pZQ==" charset="gbk"></script></li>
								<li><script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxM3xwb2ludF8yMDEzfHBvaW50XzIwMTNfZm9jdXMwNSZkYj1sdm1hbWltJmJvcmRlcj0wJmxvY2FsPXllcyZqcz1pZQ==" charset="gbk"></script></li>
								<li><script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxM3xwb2ludF8yMDEzfHBvaW50XzIwMTNfZm9jdXMwNiZkYj1sdm1hbWltJmJvcmRlcj0wJmxvY2FsPXllcyZqcz1pZQ==" charset="gbk"></script></li>
								<li><script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxM3xwb2ludF8yMDEzfHBvaW50XzIwMTNfZm9jdXMwNyZkYj1sdm1hbWltJmJvcmRlcj0wJmxvY2FsPXllcyZqcz1pZQ==" charset="gbk"></script></li>
								<li><script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxM3xwb2ludF8yMDEzfHBvaW50XzIwMTNfZm9jdXMwOCZkYj1sdm1hbWltJmJvcmRlcj0wJmxvY2FsPXllcyZqcz1pZQ==" charset="gbk"></script></li>
							</ul> 
							<ul class="slide-nav"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(88)"/></ul> 
						</div> 
						
		        <!-- 条件 -->
		        <div class="hr_a"></div>
		        <div class="point-type border">
		            <dl class="dl-hor">
		            	<dt>积分范围：</dt>
		            	<dd>
		                    <a target="_blank" href="../shop/moreProductByType.do?productType=${productType}&changeType=${changeType}&purchasingPower=N"  <#if startPoints == null && purchasingPower=='N'>class="lightHref"</#if>>不限</a>
			                	<a target="_blank" href="../shop/moreProductByType.do?startPoints=0&endPoints=100&productType=${productType}&changeType=${changeType}&purchasingPower=N"  <#if startPoints=='0' && purchasingPower=='N'>class="lightHref"</#if> >0-100</a>
			                	<a target="_blank" href="../shop/moreProductByType.do?startPoints=101&endPoints=500&productType=${productType}&changeType=${changeType}&purchasingPower=N"  <#if startPoints=='101'>class="lightHref"</#if>>101-500</a>
			                	<a target="_blank" href="../shop/moreProductByType.do?startPoints=501&endPoints=1000&productType=${productType}&changeType=${changeType}&purchasingPower=N"  <#if startPoints=='501'>class="lightHref"</#if>>501-1000</a>
			                	<a target="_blank" href="../shop/moreProductByType.do?startPoints=1001&endPoints=5000&productType=${productType}&changeType=${changeType}&purchasingPower=N"  <#if startPoints=='1001'>class="lightHref"</#if>>1001-5000</a>
			                	<a target="_blank" href="../shop/moreProductByType.do?startPoints=5001&productType=${productType}&changeType=${changeType}&purchasingPower=N"  <#if startPoints=='5001'>class="lightHref"</#if>>5000以上</a>
			                	<#if shopUser??>
			                		<a target="_blank" href="../shop/moreProductByType.do?startPoints=0&productType=${productType}&changeType=${changeType}&purchasingPower=Y"  <#if purchasingPower=='Y'>class="lightHref"</#if>>我能兑换的商品</a>
			                	</#if>
		                </dd>
		            </dl>
		            <dl class="dl-hor">
		            	<dt>商品类型：</dt>
		            	<dd> 
		                    <a target="_blank" href="../shop/moreProductByType.do?startPoints=${startPoints}&endPoints=${endPoints}&purchasingPower=${purchasingPower}"  <#if changeType==null && productType==null>class="lightHref"</#if>>不限</a>
			                	<a target="_blank" href="../shop/moreProductByType.do?startPoints=${startPoints}&endPoints=${endPoints}&productType=COUPON&changeType=POINT_CHANGE&purchasingPower=${purchasingPower}"  <#if productType=='COUPON'>class="lightHref"</#if>>驴妈妈优惠券</a>
			                	<a target="_blank" href="../shop/moreProductByType.do?startPoints=${startPoints}&endPoints=${endPoints}&productType=COOPERATION_COUPON&changeType=POINT_CHANGE&purchasingPower=${purchasingPower}"  <#if productType=='COOPERATION_COUPON'>class="lightHref"</#if>>其它网站优惠券</a>
			                	<a target="_blank" href="../shop/moreProductByType.do?startPoints=${startPoints}&endPoints=${endPoints}&productType=PRODUCT&changeType=POINT_CHANGE&purchasingPower=${purchasingPower}"  <#if productType=='PRODUCT'>class="lightHref"</#if>>实物商品</a>
			                	<a target="_blank" href="../shop/moreProductByType.do?startPoints=${startPoints}&endPoints=${endPoints}&changeType=RAFFLE&purchasingPower=${purchasingPower}"  <#if changeType=='RAFFLE'>class="lightHref"</#if>>抽奖商品</a>
		                </dd>
		            </dl>
		        </div>
		        <!-- 抽奖 -->
		        <div class="points-box point_1">
		            <div class="ptitle">
		                <h4>抽奖兑换</h4>
		                <a class="link-more" target="_blank" href="http://www.lvmama.com/shop/moreProductByType.do?changeType=RAFFLE&purchasingPower=N" hidefocus="false">更多 »</a>
		            </div>
		            <div class="content JS_hover_show">
		                <ul class="clearfix">
			                	<@s.iterator value="raffleList" var="product">
				                    <li class="point-item">
				                        <#if product.beginTime??><span class="countdown">${product.lifeTime}</span></#if>
				                        <a class="img" target="_blank" href="/points/prod/${product.productId}"><i class="middle-full"></i><img src="${product.getFirstAbsolutePictureUrl()}" alt="${product.productName}" />
				                        </a>
				                        <p><a target="_blank" href="/points/prod/${product.productId}"><@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(productName,18)"/></a></p>
				                        <p><a target="_blank" href="/points/prod/${product.productId}" class="btn btn-mini btn-orange">&nbsp;兑换&nbsp;</a>所需积分：<dfn><i>${product.pointChange}</i>分</dfn></p>
				                    </li>
			                    </@s.iterator>
		                </ul>
		            </div>
		        </div>
    </div> <!-- //.col-w -->
</div> <!-- //.wrap 1 -->

<div class="wrap">
	<!-- 合作品牌 -->
    <div class="points-box point-brand">
        <div class="ptitle">
            <h4>合作品牌展示</h4>
        </div>
        <div class="content scoll_box">
            <span class="prev"></span>
            <span class="next"></span>
            <div class="scoll">
                <ul class="hor">
                	<@s.iterator value="shopIndexPageMap.get('${station}_coopBand')" status="sts">
                		 <li><a target="_blank" href="${url?if_exists}"><img src="${imgUrl}" width="110" height="70" alt="" /></a></li>
		          	</@s.iterator> 
                </ul>
            </div>
        </div>
    </div>

	<!-- 驴妈妈优惠券 -->
    <div class="wrap points-box point_2">
        <div class="ptitle">
            <h4><span class="icon-point"></span>驴妈妈优惠券</h4>
            <a class="link-more" target="_blank" href="http://www.lvmama.com/shop/moreProductByType.do?productType=COUPON&changeType=POINT_CHANGE&purchasingPower=N">更多  »</a>
        </div>
        
        <div class="inner-side"> 
            <@s.iterator value="shopIndexPageMap.get('${station}_shop')" status="sts">
           		 <@s.if test="#sts.index==2">
           				 <a target="_blank" href="${url?if_exists}">
           		 			<img src="${imgUrl}" alt="" target="_blank" href="/points/prod/${product.productId}"/>
           		 		</a>
				            <h4>${title}</h4>
				            <p>${bakWord2?if_exists}</p>
				            <div class="points-num">
				                <span></span>
				                <b><i>${bakWord3?if_exists}</i> 积分</b>
				            </div>
			       </@s.if>
          	</@s.iterator> 
        </div>
        
        <div class="content JS_hover_show">
            <ul class="clearfix">
            		<@s.iterator value="couponList" var="product">
			                <li class="point-item">
			                    <#if product.beginTime??><span class="countdown">${product.lifeTime}</span></#if>
			                    <a class="img" target="_blank" href="/points/prod/${product.productId}"><i class="middle-full"></i><img src="${product.getFirstAbsolutePictureUrl()}" alt="${product.productName}" />
			                    </a>
			                    <p>
				                    	<a target="_blank" href="/points/prod/${product.productId}">
				                    	 		<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(productName,18)"/>
				                      </a>
			                    </p>
			                    <p><a target="_blank" href="/points/prod/${product.productId}" class="btn btn-mini btn-orange">&nbsp;兑换&nbsp;</a>所需积分：<dfn><i>${product.pointChange}</i>分</dfn></p>
			                </li>
                </@s.iterator>
            </ul>
        </div>
    </div>

	<!-- 其他网站优惠 -->
    <div class="wrap points-box point_3">
        <div class="ptitle">
            <h4><span class="icon-point"></span>其他网站优惠</h4>
            <a class="link-more" target="_blank" href="http://www.lvmama.com/shop/moreProductByType.do?productType=COOPERATION_COUPON&amp;changeType=POINT_CHANGE&amp;purchasingPower=N">更多 »</a>
        </div>
        <div class="inner-side"> 
        		<@s.iterator value="shopIndexPageMap.get('${station}_shop')" status="sts">
           		 <@s.if test="#sts.index==1">
           		 		<a target="_blank" href="${url?if_exists}">
           		 			<img src="${imgUrl}" alt="" target="_blank" href="/points/prod/${product.productId}"/>
           		 		</a>
			            <h4>${title}</h4>
			            <p>${bakWord2?if_exists}</p>
			            <div class="points-num">
			                <span></span>
			                <b><i>${bakWord3?if_exists}</i> 积分</b>
			            </div>
			           </@s.if>
          	</@s.iterator>  
        </div>
        
        <div class="content JS_hover_show">
            <ul class="clearfix"> 
	            	<@s.iterator value="cooperationCouponList" var="product">
				                <li class="point-item">
				                     <#if product.beginTime??><span class="countdown">${product.lifeTime}</span></#if>
				                    <a class="img" target="_blank" href="/points/prod/${product.productId}"><i class="middle-full"></i><img src="${product.getFirstAbsolutePictureUrl()}" alt="${product.productName}" />
				                    </a>
				                    <p>
					                    	<a target="_blank" href="/points/prod/${product.productId}">
					                    	 		<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(productName,18)"/>
					                      </a>
				                    </p>
				                    <p><a target="_blank" href="/points/prod/${product.productId}" class="btn btn-mini btn-orange">&nbsp;兑换&nbsp;</a>所需积分：<dfn><i>${product.pointChange}</i>分</dfn></p>
				                </li>
	                </@s.iterator> 
            </ul>
        </div>
    </div>

<!-- 实物商品 -->
    <div class="wrap points-box point_4">
        <div class="ptitle">
            <h4><span class="icon-point"></span>实物商品</h4>
            <a class="link-more" target="_blank" href="http://www.lvmama.com/shop/moreProductByType.do?productType=PRODUCT&changeType=POINT_CHANGE&purchasingPower=N">更多 »</a>
        </div>
        <div class="inner-side">
        		 <@s.iterator value="shopIndexPageMap.get('${station}_shop')" status="sts">
           		 <@s.if test="#sts.index==0">
           		 		<a target="_blank" href="${url?if_exists}">
           		 			<img src="${imgUrl}" alt="" target="_blank" href="/points/prod/${product.productId}"/>
           		 		</a>
			            <h4>${title}</h4>
			            <p>${bakWord2?if_exists}</p>
			            <div class="points-num">
			                <span></span>
			                <b><i>${bakWord3?if_exists}</i> 积分</b>
			            </div>
			     </@s.if>
          	</@s.iterator>
        </div>
        
        <div class="content JS_hover_show">
            <ul class="clearfix"> 
                <@s.iterator value="productList" var="product">
				                <li class="point-item">
				                    <#if product.beginTime??><span class="countdown">${product.lifeTime}</span></#if>
				                    <a class="img" target="_blank" href="/points/prod/${product.productId}"><i class="middle-full"></i><img src="${product.getFirstAbsolutePictureUrl()}" alt="${product.productName}" />
				                    </a>
				                    <p>
					                    	<a target="_blank" href="/points/prod/${product.productId}">
					                    	 		<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(productName,18)"/>
					                      </a>
				                    </p>
				                    <p><a target="_blank" href="/points/prod/${product.productId}" class="btn btn-mini btn-orange">&nbsp;兑换&nbsp;</a>所需积分：<dfn><i>${product.pointChange}</i>分</dfn></p>
				                </li>
	                </@s.iterator>
            </ul>
        </div>
    </div>

<!-- 特别推荐 -->
    <div class="featured clearfix">
        <div class="title">
            <a class="link-more" target="_blank" href="http://www.lvmama.com/shop/brandList.do">
                <h4>会员活动</h4>
                <p>ACTIVITY</p>
            </a>
        </div>
        <ul class="hor">
        	<@s.iterator value="shopIndexPageMap.get('${station}_recommend')" status="sts">
			<li><a target="_blank" href="${url?if_exists}"><img src="${imgUrl}" width="160" height="80" alt="" /></a></li>
		</@s.iterator> 
        </ul>
    </div> <!-- //.bigbox -->

<!-- 更多赚取积分 -->
    <div class="points-box point_5">
        <div class="ptitle">
            <h4>赚取积分<small>完成以下任务，即可获得积分</small></h4>
            <a class="link-more" target="_blank" href="http://www.lvmama.com/public/help_center_210">更多赚取积分的方法 ?</a>
        </div>
        
        <div class="content">
            <ul class="earn-points clearfix">
                <li class="contact">
                    <h5>范小姐</h5>
                    <p>Tel：021-60561616-3515</p>
                    <p>email：fanlu@lvmama.com</p>
                    <br>
                    <h5>李先生</h5>
                    <p>Tel：021-60561616-3507</p>
                    <p>email：lizengping@lvmama.com</p>
                </li>
                <li><a class="link-details" target="_blank" href="http://login.lvmama.com/nsso/register/registering.do">
                    <h5>会员注册</h5>
                    <b>+100积分</b>
                    <p>去完成 »</p>
                    </a>
                </li>
                <li><a class="link-details" target="_blank" href="http://www.lvmama.com/myspace/userinfo.do">
                    <h5>邮箱验证</h5>
                    <b>+300积分</b>
                    <p>去完成 »</p>
                    </a>
                </li>
                <li><a class="link-details" target="_blank" href="http://www.lvmama.com/myspace/userinfo.do">
                    <h5>手机验证</h5>
                    <b>+300积分</b>
                    <p>去完成 »</p>
                    </a>
                </li>
                <li><a class="link-details" target="_blank" href="http://www.lvmama.com/">
                    <h5>购买产品</h5>
                    <b> </b>
                    <p>去完成 »</p>
                    </a>
                </li>
                <li><a class="link-details" target="_blank" href="http://bbs.lvmama.com/">
                    <h5>每日签到</h5>
                    <b>+50积分</b>
                    <p>去看看 »</p>
                    </a>
                </li>
                <li><a class="link-details" target="_blank" href="http://www.lvmama.com/comment/">
                    <h5>发表点评</h5>
                    <b>+50-550积分</b>
                    <p>去看看 »</p>
                    </a>
                </li>
                <li><a class="link-details" target="_blank" href="http://www.lvmama.com/guide/">
                    <h5>撰写攻略</h5>
                    <b>+500-1500积分</b>
                    <p>去看看 »</p>
                    </a>
                </li>
                <li><a class="link-details" target="_blank" href="http://www.lvmama.com/info/public/ued/">
                    <h5>优秀建议</h5>
                    <b> </b>
                    <p>去看看 »</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div> <!-- //.wrap 2 -->

<script type="text/javascript" >
	function userLogin(){
		$(UI).ui("login");
	}
</script>

<#include "/common/orderFooter.ftl">
<!-- <?php include("common/footer.html"); ?> -->

<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/v3/plugins.js,/js/v3/app.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v3/slides.jquery.js,/js/v3/jcarousellite.min.js,/js/v3/points.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js"></script>
<!--
<script src="http://pic.lvmama.com/js/new_v/ui_plugin/jquery.wb_focusImg.js?r=2913" type="text/javascript" charset="utf-8"></script>
<script language="JavaScript1.1" src="http://afp8core.kejet.com/afp/door/;ap=df60032c8a44e32f0001;ct=js;pu=4dcb4947990dd69f0001;/?" charset="utf-8"></script>
-->
<script type="text/javascript" src="http://pic.lvmama.com/js/points/shop.js?r=4440"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/points/points_mall.js?r=2943"></script> 
<script>
      cmCreatePageviewTag("积分商城首页", "D1001", null, null);
 </script>
</body>
</html>
