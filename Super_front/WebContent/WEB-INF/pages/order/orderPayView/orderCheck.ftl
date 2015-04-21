<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="dns-prefetch" href="//s1.lvjs.com.cn">
<link rel="dns-prefetch" href="//s2.lvjs.com.cn">
<link rel="dns-prefetch" href="//s3.lvjs.com.cn">
<title>核对订单_驴妈妈旅游网</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="keywords" content="页面关键字">
<meta name="description" content="页面描述">



<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/pa-base.css,/styles/v4/modules/arrow.css,/styles/v4/modules/button.css,/styles/v4/modules/forms.css,/styles/v4/modules/selectbox.css,/styles/v4/modules/step.css,/styles/v4/modules/tags.css,/styles/v4/modules/tip.css,/styles/v4/modules/dialog.css,/styles/v4/modules/tables.css,/styles/v4/modules/bank.css" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/order-common.css,/styles/v4/order.css" >
<#include "/common/coremetricsHead.ftl">
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
			stepView="pay" 
			payToSupplier="${(order.paymentTarget=='TOSUPPLIER')?string('true','false')}" />
	<!--===== 导航条 E ======-->
        
    
    <div class="order-main border">
    		<!--订单信息 S-->
			<#include "/WEB-INF/pages/order/orderPayView/orderInfo.ftl"/>
			<!--订单信息 E-->
        <div class="main">
            
            <div style="height:30px;"></div>
            <div class="pay-feedback">
                <div class="tipbox tip-success tip-nowrap">
                    <span class="tip-icon-big tip-icon-big-success"></span>
                    <div class="tip-content">
                        <h3 class="tip-color-title">订单提交成功</h3>
                        <p>您可在<a href="/myspace/order.do">我的订单</a>中查看订单状态！</p>
                    </div>
                                     
                    <div class="tip-other tip-align pay-backinfo">                       
                        <div class="backinfo-item">
                            <!-- 手机客户端二维码 -->

<div class="codeapp-box" alt="扫描下载客户端">
    <span class="codeapp"><img src="http://www.lvmama.com/callback/generateCodeImage.do?userid=ff8080813726f6e9013736b517a532c6" alt="" /></span>
</div>                        </div>
                    </div>
                    
                    
                </div>
            </div>
        </div> <!-- //.main -->
        
    </div>
    
    
</div>


<!-- 订单底部 -->
<div id="order-footer" class="lv-footer"></div>


<!-- 上线JS 引用规划 -->
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js"></script>

<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js"></script>

<script src="http://pic.lvmama.com/min/index.php?f=/js/common/footer.js,/js/v4/order-page.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js"></script>
<script>
        	cmCreatePageviewTag("核对订单-"+"<@s.property value="order.mainProduct.productType,6)"  escape="false"/>-"+"<@s.property value="order.mainProduct.subProductType"  escape="false"/>", "Q0001", null, null);
    </script>
</body>
</html>
