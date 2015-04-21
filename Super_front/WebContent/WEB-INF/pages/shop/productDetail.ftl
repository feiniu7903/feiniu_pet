<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title><#if product??>${product.productName}</#if>_积分商城—驴妈妈旅游网</title>
<#include "/common/commonJsIncluedTopNew.ftl"/>
<script type="text/javascript" src="/js/shop/shop.js"></script>
<link href="http://pic.lvmama.com/styles/points/pointsMall.css" rel="stylesheet" />
<link href="http://pic.lvmama.com/min/index.php?g=commonCss" rel="stylesheet"/>
</head>

<body>
	<!-----------头部文件区域 S-------------->
	<#include "/common/header.ftl">
	<div class="main clearfix">
		<div class="mainTop">
			<p>
				<strong>您当前所处的位置：</strong>
				<span><a href="http://www.lvmama.com/">首页</a></span>
				<span><a href="/points">积分商城</a></span>
				<span><#if product??>${product.productName}</#if></span>
			</p>
		</div>

		<!----------------------------mainLeft-------------------------->
		<#include "/WEB-INF/pages/shop/mainLeft.ftl">
		<!----------------------------mainRight-------------------------->

		<#if product??>
			<form id="myForm" method="POST" action="/shop/initOrder.do">
	    		<div class="mainRight">
	        		<dl class="mainRightPro">
	           			<dt>
							<a href="/points/prod/${product.productId}"><img src="${product.getFirstAbsolutePictureUrl()}" title="${product.productName}" width="170px" height="100px"/></a>
	           			</dt>
	           			<dd class="mainRightProTop">
							<strong>产品名称：</strong><a href="/shop/showProductDetail.do?productId=${product.productId}">${product.productName}</a>
	           			</dd>                    
	           			<dd><abbr>市场价：</abbr><del>${product.marketPrice}元</del></dd>
			            <dd><strong>所需积分：</strong><span>${product.pointChange}</span><strong>分</strong></dd>
			            <dd><abbr>库存：</abbr><label>${product.stocks}</label>件</dd>
			            <#if product.isValid == "Y" && product.stocks != 0>
	           				<dd><img class="button07" height="24" width="78" src="http://pic.lvmama.com/img/points/button07-01.gif" /></dd>
	           			<#else>
	           				<dd><img height="24" width="78" src="http://pic.lvmama.com/img/points/button07-02.gif" /></dd>
	           			</#if>
	           		</dl>  
		           <div class="mainRightProText">
		           		<dd class="mainRightProTop"><strong>产品详情:  ${product.content}</strong></dd>  
		           </div>
		           <input type="hidden" name="productId" id="productId" value="${product.productId}" />
	   			</div>
   			</form>
    	</#if>
    	
    	<#if shopUser??>
    		<input type="hidden" name="productId" id="productId" value="${product.productId}" />
		 </#if>
        
		<!-----------底部文件区域 S -------------->
		<#include "/common/orderFooter.ftl">
<script src="http://pic.lvmama.com/min/index.php?g=common"></script>
<script src="http://pic.lvmama.com/js/new_v/top/top_common.js"></script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script type="text/javascript">
	$(function(){
		$('.button07').click(function(){
			<#if shopUser??>
				$('#myForm').submit();
			<#else>
				//$('.login01').show();
				$(UI).ui("login");
			</#if>	
		})
	});
</script>
</body>
</html>
