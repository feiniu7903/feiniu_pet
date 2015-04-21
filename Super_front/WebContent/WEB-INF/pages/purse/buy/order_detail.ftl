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
<#include "/WEB-INF/pages/purse/top.ftl"/>
        <div class="lvmama-body mg-t0 relative">
        	<span class="back-1">
	        	 
        	</span>
   	  <h3 class="bd-b">预订：
   	  <@s.iterator value="order.ordOrderItemProds" status="st">
   	  		<@s.if test="wrapPage&&!additional">
   	  			<a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" />/product/<@s.property value="productId" />" ><@s.property value="productName"/></a>
   	  		</@s.if>
   	  </@s.iterator>
   	  </h3>
            	<div class="bgc-div4"><span class="icon large-d"></span>订单提交成功！<br />
                    <span class="c-f00">请等待客服与您联系!</span>
                    <ul>
                    	<li><strong>客服将协助办理以下事项：</strong></li>

                    	<li>·&nbsp;确认订单内容核实订单金额；</li>
                    	<li>·&nbsp;付款、签约；</li>
                    	<li>·&nbsp;预订成功后出行前为您发送出团通知。</li>
                    </ul>
                </div>
                <div class="order-id">

                	<div class="o-id">订单编号：<strong>${order.orderId?if_exists}</strong></div>
                    <div class="o-money">订单金额：￥${order.oughtPayFloat?if_exists}</div>
                </div>
                <#if (viewPage.contents.get('IMPORTMENTCLEW'))??>
          			<h3>产品特别提示：</h3>
	                <div class="o-content" name="pro_list">
	                  ${viewPage.contents.get('IMPORTMENTCLEW').content?if_exists}
	          	</div>
             </#if>
        </div>
</body>
</html>
