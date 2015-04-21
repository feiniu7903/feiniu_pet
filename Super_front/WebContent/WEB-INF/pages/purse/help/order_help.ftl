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

</head>
<body>
<div class="lv-nav">
            <h2>帮助中心</h2>
            <span>
            <@s.if test='mainProduct!=null&&mainProduct.productType=="TICKET"'>
           		<a href="/purse/ticket/index.do">预订首页</a> 
            </@s.if>
            <@s.else>
            	 <a href="/purse/route/index.do">预订首页</a> 
            </@s.else>
            | <a href="/purse/order.do">我的订单</a> | <a href="/purse/help/order.do">预订帮助</a> | <a href="/purse/help/service.do">客服</a></span>
        </div>

        <div class="lvmama-body">
            <h3 class="vtop-align">预订流程：</h3>

            <div class="liucheng"><img src="http://pic.lvmama.com/img/super_v2/mybank/help_liucheng2.gif" alt="预订流程图" /></div>
            <h3>相关提示：</h3>
            <div class="tips-textarea">
               	 线路产品部分有库存限制，需要客服回电确认，请等待客服电话。<br />
				确认后，您通过财付通付款，支付成功后接收手机提示短信。<br />
				客服电话：<@s.if test="showDifferntHotLine != null && showDifferntHotLine != ''">4006-040-${showDifferntHotLine?if_exists}</@s.if><@s.else>1010-6060</@s.else>

            </div>
        </div>
        
</body>
</html>
