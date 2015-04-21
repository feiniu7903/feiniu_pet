<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml
1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/templete.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <meta name="version" content="4.0" />
    <!-- InstanceBeginEditable name="head" -->
    <title>财付通-我的钱包</title>
	<!-- InstanceEndEditable -->
	<link href="https://img.tenpay.com/wallet/v4.0/css/wallet_v4.css" rel="stylesheet" type="text/css" />
    <link href="http://pic.lvmama.com/styles/super_v2/mybank/lvmama_v1.css?r=2916" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js?r=8420"></script>
    <script type="text/javascript" src="http://pic.lvmama.com/js/super_v2/mybank/mybank_func.js?r=2913"></script>
    <script type="text/javascript" src="/js/buy/form.js" ></script>
	<script type="text/javascript" src="/js/buyBase.js" ></script>
	<script type="text/javascript" src="/js/fillNew.js" ></script>
</head>
<body>
  <form id="productFrom" action="/purse/other.do" method="post" onsubmit="return purseSubmit();">
	<input type="hidden" id="productId" name="buyInfo.productId"  value="<@s.property value="buyInfo.productId"/>"/>
	<input type="hidden" name="buyInfo.visitTime" value="${buyInfo.visitTime?if_exists}" />
	<input type="hidden" name="buyInfo.paymentTarget" value="${buyInfo.paymentTarget?if_exists}" />
	<input type="hidden" name="buyInfo.channel" value="TENCENT" />
<#include "/WEB-INF/pages/purse/top.ftl"/>
        <div class="lvmama-body mg-t0 relative">

        	<span class="back-1" onclick="javascript:history.go(-1);">[返回上一页]</span>
        	<h3 class="bd-b">预订：<a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" />/product/<@s.property value="mainProduct.productId" />" ><@s.property value="mainProduct.productName"/></a></h3>
            	<div class="w-500">
                  <label for="inp_data" class="c-c06 mg-t20 block">填写游玩日期：</label>
                        <div class="h-40 pd-t5"><input type="text" id="inp_data" disabled="disabled" name="buyInfo.visitTime" value="${buyInfo.visitTime?if_exists}" /><span class="c-888">（不同日期价格可能有所差别）</span></div>
                        <h4>填写预订数量：</h4>

                        <table class="ticket-cart">
                        	<thead>
                              <tr>
                                  <th>票种</th>
                                  <th>门市价</th>
                                  <th>现售价</th>
                                  <th>节省</th>

                                  <th class="w-110 txt-center">预订数量</th>
                              </tr>
                            </thead>
                            <tbody>
                              <tr>
                                  <td><@s.property value="mainProduct.shortName" /></td>
                                  <td>￥<@s.property value="mainProduct.marketPriceYuan"/></td>

                                  <td class="c-f50">￥<@s.property value="mainProduct.sellPriceYuan"/></td>
                                  <td>￥<@s.property value="mainProduct.marketPriceYuan-mainProduct.sellPriceYuan"/></td>
                                  <td class="txt-center">
                                  <input type="hidden" id="param${mainProduct.productId}" name="paramName" minAmt="${mainProduct.minimum}" maxAmt="<#if (mainProduct.maxinum>0) >${mainProduct.maxinum}<#else>${mainProduct.stock}</#if>" textNum="textNum${mainProduct.productId}" people="${mainProduct.adultQuantity+mainProduct.childQuantity}" buyPeopleNum="<@s.property value='buyInfo.buyNum.product_${mainProduct.productId}*${mainProduct.adultQuantity+mainProduct.childQuantity}'/>"/>
                                      <button type="button"  onClick="updateOperator('param${mainProduct.productId}','miuns')" class="jian_btn">-</button>
                                      <input id="textNum${mainProduct.productId}" name="buyInfo.buyNum.product_${mainProduct.productId}" type="text" cashRefund="" sellName="sellName" marketPrice="${mainProduct.marketPriceYuan?if_exists}"  sellPrice="${mainProduct.sellPriceYuan}" value="${mainProduct.minimum?if_exists}"   onblur="updateOperator('param${mainProduct.productId}','input')" size="3" class="ticket-num" />&nbsp;
                                      <button type="button" onClick="updateOperator('param${mainProduct.productId}','add')" class="jia_btn">+</button>
                                  </td>
                              </tr>
                               <@s.iterator value="relatedProductList" var="product">
                               		<@s.if test="!additional">
		                              		<tr class="last-tr">
		                                  <td><@s.property value="shortName" /></td>
		                                  <td>￥${product.marketPriceYuan?if_exists}</td>
		                                  <td class="c-f50">￥${product.sellPriceYuan?if_exists}</td>
		                                  <td>￥<@s.property value="marketPriceYuan-sellPriceYuan"/></td>
		                                  <td class="txt-center">
		                                      <input type="hidden" id="param${product.productId}" name="paramName" minAmt="${product.minimum}" maxAmt="<#if (product.maxinum>0) >${product.maxinum}<#else>${product.stock}</#if>" textNum="textNum${product.productId}" people="${product.adultQuantity+product.childQuantity}" buyPeopleNum="${product.adultQuantity+product.childQuantity}'/>"/>
								              <button type="button" onClick="updateOperator('param${product.productId}','miuns')" class="jian_btn">-</button>
								              <input type="text" size="3" name="buyInfo.buyNum.product_${product.productId}" onblur="updateOperator('param${product.productId}','input')" sellName="sellName" cashRefund=""  marketPrice="${product.marketPriceYuan}" sellPrice="${product.sellPriceYuan}" value="${product.minimum?if_exists}"   id="textNum${product.productId}"   class="ticket-num" />
								              <button type="button" onClick="updateOperator('param${product.productId}','add')"  class="jia_btn">+</button></span>
		                                  </td>
		                              </tr>
                              	</@s.if>
           					</@s.iterator>
                            </tbody>
                        </table>
                       <button type="submit" class="button btn-p1">下一步</button>
               </div>
            
        </div>
  </form>

</body>
</html>
