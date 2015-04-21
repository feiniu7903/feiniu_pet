<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驴妈妈旅游网</title>
<link href="http://pic.lvmama.com/styles/order_20100524.css?r=2916" rel="stylesheet" type="text/css" />
<script src="http://pic.lvmama.com/js/jquery.js?r=2913" type="text/javascript"></script>
<link href="http://pic.lvmama.com/min/index.php?g=commonIncluedTop" type="text/css" rel="stylesheet"/>
</head>

<body>
<!--===头部包含文件 S =====================-->
	<#include "/common/orderHeader.ftl">
	<!--===头部包含文件 E =====================-->

<div class="maincontainer">

    
    <div class="o_order_box">
    	<div class="o_box_tl"></div>
    	<div class="o_box_tr"></div>
    	<h4></h4>
        <div class="ordercontainer2">
        	<dl class="tipsinfo">
                <dt><br /><strong>您的订单已提交过了，请不要重复提交！</strong><br /><br /></dt>
            </dl>
            
<p class="p_desc">
            您可以在 <a href="/myspace/order.do" target="_blank">我的订单</a><br/>
	    <img src="http://pic.lvmama.com/img/icons/mobile.gif" style="vertical-align: middle; margin-right: 3px">
	    <a href="http://shouji.lvmama.com" target="_blank">下载驴妈妈手机客户端，随时查看订单。</a><br/>
            <em>别忘了旅游归来后回驴妈妈发表您的点评哦！</em><br />
            如果您对刚才的订购过程有任何的意见和建议，<a href="http://www.lvmama.com/userCenter/user/transItfeedBack.do" target="_blank">请告诉我们</a> >><br />
            如需帮助请致电 <em><@s.if test="showDifferntHotLine != null && showDifferntHotLine != ''">4006-040-${showDifferntHotLine?if_exists}</@s.if><@s.else>1010-6060</@s.else></em>。<br />
            </p>
		</div>
    	<div class="o_box_bl"></div>
    	<div class="o_box_br"></div>
    </div>
</div>

	<div class="o_foot">客服热线：<@s.if test="showDifferntHotLine != null && showDifferntHotLine != ''">4006-040-${showDifferntHotLine?if_exists}</@s.if><@s.else>1010-6060</@s.else>　服务时间：周一至周日早8:00到晚24:00　客服邮箱：service@lvmama.com<br />copyright&copy;2011&nbsp;www.lvmama.com&nbsp;景域旅游运营集团版权所有&bnsp;沪ICP备07509677</div>
    
  
</body>
</html>
