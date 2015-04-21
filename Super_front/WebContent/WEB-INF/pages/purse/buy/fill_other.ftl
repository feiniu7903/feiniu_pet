<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml
1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/templete.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="version" content="4.0" />
    <!-- InstanceBeginEditable name="head" -->
    <title>财付通-我的钱包</title>
	<!-- InstanceEndEditable -->
	<link href="https://img.tenpay.com/wallet/v4.0/css/wallet_v4.css" rel="stylesheet" type="text/css" />
    <link href="http://pic.lvmama.com/styles/super_v2/mybank/lvmama_v1.css?r=2916" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js?r=8420"></script>
    <script type="text/javascript" src="http://pic.lvmama.com/js/super_v2/mybank/mybank_func.js?r=2913"></script>
</head>
<body>
 <form id="productFrom" action="/purse/receiver.do" method="post">
 	<#include "/WEB-INF/pages/purse/buy/buyInfo.ftl"/>
<input type="hidden" id="param${mainProduct.productId}" name="paramName" minAmt="${mainProduct.minimum}" maxAmt="<#if (mainProduct.maxinum>0) >${mainProduct.maxinum}<#else>${mainProduct.stock}</#if>" textNum="textNum${mainProduct.productId}" people="${mainProduct.adultQuantity+mainProduct.childQuantity}" buyPeopleNum="<@s.property value='buyInfo.buyNum.product_${mainProduct.productId}*${mainProduct.adultQuantity+mainProduct.childQuantity}'/>"/>
 <#list relatedProductList as product>
                 <#if !product.additional>
		             <input type="hidden" id="param${product.productId}" name="paramName" minAmt="${product.minimum}" maxAmt="<#if (product.maxinum>0) >${product.maxinum}<#else>${product.stock}</#if>" textNum="textNum${product.productId}" people="${product.adultQuantity+product.childQuantity}" buyPeopleNum="<@s.property value='buyInfo.buyNum.product_${product.productId}*${product.adultQuantity+product.childQuantity}'/>"/>
            	</#if>
 </#list>
<#include "/WEB-INF/pages/purse/top.ftl"/>
        <div class="lvmama-body mg-t0 relative">

        	<span class="back-1" onclick="javascript:history.go(-1);">[返回上一页]</span>
        	<h3 class="bd-b">预订：<a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" />/product/<@s.property value="mainProduct.productId" />" ><@s.property value="mainProduct.productName"/></a></h3>
            	<div class="w-500">
                  
                        <h4 class="mg-t15">附加产品选择：</h4>
                        <@s.iterator value="additionalProduct" var="add">
                        <h5>${add.key}</h5>
                        <table class="ticket-cart">

                        	<thead>
                              <tr>
                                  <th>名称</th>
                                  <th>市场价</th>
                                  <th>驴妈妈价</th>
                                  <th>计价单位</th>
                                  <th class="w-110 txt-center">份数</th>

                              </tr>
                            </thead>
                            <tbody>
                            	<@s.iterator value="#add.value" var="product">
                            		<#if product.additional>
				                              <tr>
				                                  <td>${product.productName?if_exists}</td>
				                                  <td>￥${product.marketPriceYuan?if_exists}</td>
				                                  <td class="c-f50">￥${product.sellPriceYuan?if_exists}</td>
				                                  <td><@s.property value="product.priceUnit"/></td>
				                                  <td class="txt-center">
				                                  <input type="hidden" name="buyInfo.buyNum.product_${product.productId}"  id="addition${product.productId}" sellName="sellName" cashRefund=""  marketPrice="${product.marketPriceYuan?if_exists}" sellPrice="${product.sellPriceYuan?if_exists}" value="0"    />
									              <select name="select${product.productId}" id="select${product.productId}" onchange="updateAddition(this,'addition${product.productId}','sumAddition${product.productId}')" onmouseover="createOption('paramName','select${product.productId}','')">
									                <option selected='selected' value="0">0</option>
									              </select> 
 				                                  </td>
				                              </tr>
		                              </#if>
                  			</@s.iterator>
                            </tbody>
                        </table>
 					</@s.iterator>
                      <a class="step-back-2" href="javascript:history.go(-1);">上一步</a>   <button type="submit" class="button btn-p1">下一步</button>
               </div>
            
        </div>
 
  </form>
  <script src="/js/buy/form.js" type="text/javascript"></script>
<script src="/js/buyBase.js" type="text/javascript"></script>
<script src="/js/fillNew.js" type="text/javascript"></script>
</body>
<!-- InstanceEnd --></html>

