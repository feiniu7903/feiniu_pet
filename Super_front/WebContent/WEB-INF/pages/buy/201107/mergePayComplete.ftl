<#include "/WEB-INF/pages/buy/201107/navigation.ftl">
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="" name="description">
<meta content="" name="keywords">
<title>下单完成_驴妈妈旅游网</title>
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/s2_globalV1_0.css">
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/orderV2.css">
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<#include "/common/coremetricsHead.ftl">
</head>

<body>
<!-- 弹出层 -->
<div class="t-bg-tm"></div>
<div class="t-div-zf">
	<div class="t-div-cont">
		<p class="t-titleandclose"><span class="t-fuanfatome">邮箱信息</span><a class="t-btn-close"><img src="http://pic.lvmama.com/img/icons/close.gif" alt="关闭" /></a></p>
		<p><span class="inputNameSend"><em>*</em>签约邮箱：</span><label><input type="text" class="txtSendMail" /></label><em class="wrongInfo">请输入有效的邮箱地址</em></p>
		<p><span class="inputNameSend">&nbsp;</span><input type="submit" class="sendEmailInfo" value="确认发送" /></p>
	</div>
	<span class="t-img-zf-down-span"></span>
</div>
<!-- /弹出层 -->

	<!--===== 头部文件区域 S ======-->
		<div style="text-align:center;">
			<#include "/common/orderHeaderNew.ftl">
		</div>
	<!--===== 头部文件区域 E ======-->

<div class="tips-info">
  <p class="tips-title align-center">
      <strong><s></s>
     	     您的订单已合并支付成功！
      </strong>
  </p>
</div>

<!--其他信息 S-->
<div class="other-info complete-left">
	<div style="width:12px;border:1px solid #C8C8C8;position:absolute;top:0px;right:-16px;line-height:16px;"><a href="http://www.sojump.com/jq/902911.aspx" style="color:#404040;font-size:12px;">调查问卷</a></div>

    <p>您可以在 <a href="http://www.lvmama.com/myspace/order.do">我的订单</a>中管理您的订单<p>
	<p style="margin-bottom:0px;"><img src="http://pic.lvmama.com/img/icons/mobile.gif" style="vertical-align: middle; margin-right: 3px">
	  下载驴妈妈手机客户端，随时查看订单。</p>
	<p><a href="http://shouji.lvmama.com"><img src="http://pic.lvmama.com/img/icons/iPhone.jpg" style="vertical-align: top; margin-right: 3px"></a> <a href="http://shouji.lvmama.com"><img src="http://pic.lvmama.com/img/icons/Android.jpg"></a></p>

	<p>
		别忘了旅游归来后回驴妈妈发表您的点评哦！<br />
    	发表点评，赠100积分！&nbsp;&nbsp;<a target="_blank" href="http://www.lvmama.com/public/help_301">什么是积分？</a><br />
   </p>
    <div class="m-code m-code-bgDiff">
    	<strong>短信凭证样例：</strong>
        <p>图片仅供参考，请以实际收到的短信内容为准。</p>
		<p class="img-txt img-txt2">
			游玩时间：2011-5-20将此短信区验证。 <br />
			本凭证验证后即失效，切勿转发。 <br />
			如需帮助请致电驴妈妈旅游网客服中心<@s.if test="showDifferntHotLine != null && showDifferntHotLine != ''">4006-040-${showDifferntHotLine?if_exists}</@s.if><@s.else>1010-6060</@s.else>。 <br />
			驴妈妈旅游网
		</p>
    </div>
    
    <div style="position:absolute;top:30px;right:60px;">
          <!-- 支付成功页面广告 -->
          <@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('te1addac7eb4aa960001','js',null)"/>      
						
		  <!-- 支付成功页面广告/End -->
    </div>
</div>
<!--其他信息 E-->

<!--===== 底部文件区域 S ======-->
<#include "/common/orderFooter.ftl">

<script type='text/javascript'>
    if (window.GridsumWebDissector) {
        var _gsTracker = GridsumWebDissector.getTracker("GWD-000268");
        _gsTracker.track("/targetpage/order_completed");
    }
</script>
<!--===== 底部文件区域 E ======-->
<script language="javascript" src="http://pic.lvmama.com/js/super_v2/order_func.js"></script>
<#include "/common/ga.ftl"/>
<script>
        	cmCreatePageviewTag("支付成功页-"+"<@s.property value="order.mainProduct.productType"  escape="false"/>-"+"<@s.property value="order.mainProduct.subProductType"  escape="false"/>", "Q0001", null, null);
    </script>
</body>
</html>
