<#include "/WEB-INF/pages/buy/201107/navigation.ftl">
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="dns-prefetch" href="//s1.lvjs.com.cn">
<link rel="dns-prefetch" href="//s2.lvjs.com.cn">
<link rel="dns-prefetch" href="//s3.lvjs.com.cn">
<title>预订成功_驴妈妈旅游网</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="keywords" content="预订成功_驴妈妈旅游网">
<meta name="description" content="预订成功_驴妈妈旅游网">

<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/pa-base.css,/styles/v4/modules/arrow.css,/styles/v4/modules/button.css,/styles/v4/modules/forms.css,/styles/v4/modules/selectbox.css,/styles/v4/modules/step.css,/styles/v4/modules/tags.css,/styles/v4/modules/tip.css,/styles/v4/modules/dialog.css,/styles/v4/modules/tables.css,/styles/v4/modules/bank.css" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/order-common.css,/styles/v4/order.css" >

</head>

<body class="order">
<!-- 订单公共头部开始 -->
<div class="order-header wrap">
    <div class="header-inner">
        <a class="logo" href="http://www.lvmama.com/">自助天下游 就找驴妈妈</a>
        <p class="welcome">
        	<@s.if test='isLogin()'> 
			您好，<b><@s.property value="getUser().userName" /></b> 
			</@s.if>
		</p>
        <p class="info">24小时服务热线：<strong>1010-6060</strong></p>
        
    </div>
</div> <!-- //.lv-header -->

<!-- 公共头部结束  -->
<div class="wrap">
<!--===== 导航条 S ======-->
	<#include "/WEB-INF/pages/order/orderPayView/navigation.ftl">
    <@navigation productType="${order.mainProduct.productType?if_exists}" 
			subProductType="${order.mainProduct.subProductType?if_exists}" 
			resourceConfirm="${order.resourceConfirm}"
			eContract = "${order.isNeedEContract()?string('true','false')}" 
			stepView="success"  
			payToSupplier="${(order.paymentTarget=='TOSUPPLIER')?string('true','false')}" />
<!--===== 导航条 E ======-->    

<div class="order-main border">
        <div class="main">
            <div class="pay-feedback">
                <div class="tipbox tip-success tip-nowrap">
                    <span class="tip-icon-big tip-icon-big-success"></span>
                    <div class="tip-content">
                        <h3 class="tip-color-title">您的预订已经成功</h3>
                    </div>
                    
                    <div class="tip-other tip-align pay-backinfo">
                       <div class="backinfo-item">
		        <p>我们将相关信息，以短信方式通知 <em>
		         <#if (order.contact)??>
		       		 <@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(order.contact.mobile)"/> 
		         </#if></em> 。您可在 <a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a> &gt;  <a href="http://www.lvmama.com/myspace/order.do">我的订单</a>之中查看订单相关信息 </p>
		        
		        
		        <#if order.cashRefund?default(0) gt 0>
		        <p>游玩归来后发表点评可获得￥ <b class="red">${order.cashRefund/100}</b> 元点评返现，以及 <b class="red">100</b> 驴妈妈积分。        
		        <#else>
		        <p>发布点评并通过，可获得<b class="red">100</b> 驴妈妈积分。
		        </#if>
		          <a href="http://www.lvmama.com/public/help_center_208" target="_blank">什么是积分？</a><a href="http://www.lvmama.com/public/help_center_452" target="_blank">什么是奖金？</a></p>
		          <div class="dot_line"></div>
		        <!--<p>游玩归来后您可在  <a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a> &gt;  <a href="http://www.lvmama.com/myspace/order.do">我的订单</a> 中索取发票。</p>-->
		    </div>
                        
                        <div class="backinfo-item">
                            <!-- 手机客户端二维码 -->

<div class="codeapp-box" alt="扫描下载客户端">
    <span class="codeapp"><img src="http://www.lvmama.com/callback/generateCodeImage.do?userid=ff8080813726f6e9013736b517a532c6" alt="" /></span>
</div>                        </div>
                        
                </div>
                
            </div>
        </div> <!-- //.main -->
        
    </div>
    
</div>
    
 <!-- 订单底部 -->
<div id="order-footer" class="lv-footer">
	
</div>
 
    
    <div style="position:absolute;top:30px;right:60px;">
          <!-- 支付成功页面广告 -->
          <@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('te1addac7eb4aa960001','js',null)"/>
				
		  <!-- 支付成功页面广告/End -->
    </div>
    
</div>
	<!--jQuery以及通用js-->
	<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js"></script>
	<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js"></script>
	<script src="http://pic.lvmama.com/min/index.php?f=/js/common/footer.js,/js/v4/order-page.js"></script>
	<script src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
