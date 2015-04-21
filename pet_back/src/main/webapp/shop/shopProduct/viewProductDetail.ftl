<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>产品预览</title>
		<link href="http://pic.lvmama.com/styles/points/pointsMall.css" type="text/css" rel="stylesheet" />
		<link href="http://pic.lvmama.com/styles/super_v2/s2_globalV1_0.css" rel="stylesheet" type="text/css" />
	</head>
	<body >
		<#if product??>
    		<div style= "width:750px;margin:0 auto;float:left;">
        		<dl class="mainRightPro">
           			<dt>
						<img src="<@s.property value="product.absolutePictureUrl"/>" title="<@s.property value="product.productName"/>" width="170px" height="100px"/>
           			</dt>
           			<dd class="mainRightProTop">
						<strong>产品名称：</strong><@s.property value="product.productName"/>
           			</dd>                    
           			<dd><abbr>市场价：<@s.property value="product.marketPrice"/>元</abbr></dd>
		            <dd><strong>所需积分：</strong><span><@s.property value="product.pointChange"/></span><strong>分</strong></dd>
		            <dd>库存：<@s.property value="product.stocks"/>件</dd>
           		</dl>  
	           <div class="mainRightProText">
	           		<dd class="mainRightProTop"><strong>产品详情: <@s.property escape="false"  value="product.content"/> </strong></dd>  
	           </div>
   			</div>
    	</#if>

	</body>
</html>
