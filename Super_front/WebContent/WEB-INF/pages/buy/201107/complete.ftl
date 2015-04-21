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

	<!--===== 导航条 S ======-->
	    <@navigation productType="${order.mainProduct.productType?if_exists}" 
			subProductType="${order.mainProduct.subProductType?if_exists}" 
			resourceConfirm="${order.resourceConfirm}"
			eContract = "${order.isNeedEContract()?string('true','false')}" 
			stepView="completeView"  
			payToSupplier="${(order.paymentTarget=='TOSUPPLIER')?string('true','false')}" 
			fillTravellerInfo="${(travellerInfoOptions!=null)?string('true','false')}"/>
	<!--===== 导航条 E ======-->

	<!--===== 页面信用贴士S ======-->
		    <#include "/WEB-INF/pages/buy/201107/creditTip.ftl">
		    <#assign local_productNames><@s.iterator value="mainOrderList"><@s.property value="productName"/> </@s.iterator></#assign>
		    <@creditTip productName="${local_productNames}" productType="${order.mainProduct.productType?if_exists}"/>
	<!--===== 页面信用贴士E ======-->

<div class="tips-info">
  <p class="tips-title align-center">
  <@s.if test='order.isPaymentSucc() && order.isPayToLvmama()'>    
      <strong><s></s>
          您的订单已支付成功！
      </strong>
      <span class="f-14">系统已向手机
      <em>
       <#if (order.travellerList?size == 0)>
      	${order.contact.mobile?if_exists}
      <#else>      	
      	${order.travellerList.get(0).mobile?if_exists}
      </#if>
      </em>发送了短信凭证，请妥善保存。</span>  
      
      <@s.if test='order.isGroupOrderType()'>    
      <span style="color:#000000;">若产品在最后报名截止时,人数不足以成行，我们将提前至少3天通知，并根据您的意愿改期、选择其他产品或者退还款项</span>      
      </@s.if>
          
  </@s.if>
  
  
  <@s.elseif test='order.paymentTarget=="TOSUPPLIER"' >
   <strong><s></s>
          您已订购成功！
   </strong>
   <span class="f-14">系统已向手机<em><#if (order.travellerList?size == 0)>${order.contact.mobile?if_exists}<#else>${order.travellerList.get(0).mobile?if_exists}</#if></em>发送了短信凭证，请妥善保存。可凭此短信至景点以驴妈妈价付款取票！</span>
  </@s.elseif>
  <@s.elseif test="order.isPrePaymentSucc()">

    <div class="ord_tipbox">
            <div class="atr_pay_hint">
               <em>您已预授权成功</em>
               <p>我们将尽快确认资源，以手机短信的形式通知到您，请耐心等待。</p>
               <ul class="atr_pay_listtxt">
                  <li>• 在您进行预授权后，订单应付金额在银行卡中冻结，一旦产品资源得到确认后，您的订单款项将自动扣除，即订单支付成功</li>
                  <li>• 若您预订的产品资源无法确认，则您授权的预授权款项将自动解冻，返回您的银行账户，无需您任何操作及手续费。</li>
               </ul>
            </div>
         </div>
  </@s.elseif>


  <@s.if test='order.isNeedEContract() && order.isPaymentSucc()'>
   <span class="f-14">系统已向邮箱<em>${order.contact.email?if_exists}</em>成功发送了“旅游合同”，请注意查收！<a href=<@s.property value="catantMailHost"/> target="_blank">进入邮箱查收</a></span>
  </@s.if>
  
  <@s.if test = 'order.mainProduct.productId == 42851 || order.mainProduct.productId == 65693 || order.mainProduct.productId == 77589 || order.mainProduct.productId == 77721 || order.mainProduct.productId == 36516 || order.mainProduct.productId == 77588 || order.mainProduct.productId == 77681 || order.mainProduct.productId == 77708 || order.mainProduct.productId == 77568 || order.mainProduct.productId == 77559 || order.mainProduct.productId == 76316 || order.mainProduct.productId == 77615 || order.mainProduct.productId == 77616 || order.mainProduct.productId == 67623 || order.mainProduct.productId == 73091 || order.mainProduct.productId == 74954 || order.mainProduct.productId == 77430 || order.mainProduct.productId == 76996 || order.mainProduct.productId == 74211 || order.mainProduct.productId == 79086'>
   <span class="f-14">驴妈妈用户专享:<a href="http://www.biotherm.com.cn/biotherm/_zh/_cn/women/landing/2013/lvmama/bilv.aspx?utm_source=lvmama&utm_medium=bd&utm_campaign=E_bio_lvmama_201305#bilv" target="_blank">免费申请碧欧泉350RMB奢华旅行装</a></span>
  </@s.if>
  </p>
</div>
<!--其他信息 S-->
<div class="other-info complete-left">
	<div style="width:12px;border:1px solid #C8C8C8;position:absolute;top:0px;right:-16px;line-height:16px;"><a href="http://www.sojump.com/jq/902911.aspx" style="color:#404040;font-size:12px;">调查问卷</a></div>
	<@s.if test="order.lastCancelTime!=null"><p>
			<p style="color:#cc0066">注：${order.lastCancelStr}</p>
 </p>
 </@s.if>
    <p>您可以在 <a href="http://www.lvmama.com/myspace/order.do">我的订单</a>中管理您的订单<p>
<p style="margin-bottom:0px;"><img src="http://pic.lvmama.com/img/icons/mobile.gif" style="vertical-align: middle; margin-right: 3px">
	  下载驴妈妈手机客户端，随时查看订单。</p>
        <p><a href="http://shouji.lvmama.com"><img src="http://pic.lvmama.com/img/icons/iPhone.jpg" style="vertical-align: top; margin-right: 3px"></a> <a href="http://shouji.lvmama.com"><img src="http://pic.lvmama.com/img/icons/Android.jpg"></a></p>
    <!--若订购的产品需签约，则显示以下部分-->
    <@s.if test='order.isNeedEContract() && order.isPaymentSucc()'>
    <p>您可登录进入“我的驴妈妈” &gt; “我的订单” 下载旅游合同； <br />
	或者直接点击 <a href="/ord/downPdfContractDetail.do?orderId=${order.orderId}" class="downHeto">下载合同</a>，完成下载旅游合同。
	</p>

	<p>如果长时间没有收到邮件，请尝试：<br />
	1. 若未收到邮件，请到垃圾邮件箱查看，邮件可能被误放入；<br />
	2. 点此 <a href="#" onClick="reSendEContract()">重新发送</a> 确认邮件。
    </@s.if>
    

	<p>别忘了旅游归来后回驴妈妈发表您的点评哦！<br />
    发表点评，<#if order.cashRefund?default(0) gt 0>可获 <strong>￥${order.cashRefund/100}</strong>奖金返还！</#if>赠100积分！&nbsp;&nbsp;<a target="_blank" href="http://www.lvmama.com/public/help_301">什么是积分？</a><br />
    <!--您入园游玩后，登录即可获赠 <strong>2</strong> 驴币。--></p>
    
	<iframe style="margin-top:20px;margin-left:80px;" marginheight="0" marginwidth="0" frameborder="0" width="750" height="145" scrolling="no" src="http://lvmamim.allyes.com/main/s?user=lvmama_2014|zhifu_2014|zhifu_2014_banner2&db=lvmamim&border=0&local=yes"></iframe>
	
    <div class="codeapp_box bc" style="margin-top:20px;margin-left:80px;height:150px;padding:1px;background:url(http://pic.lvmama.com/img/v4/codeapp_bg.png) no-repeat;">
        <span class="codeapp" style="display:none;overflow:hidden;width:130px;height:130px;margin:6px;background:#fafafa;">
              <img class="erweima"  src='/callback/generateCodeImage.do?userid=<@s.property value="user.userNo" />' />
        </span>
    </div>
    
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
	<#include "/WEB-INF/pages/buy/googleAnaly.ftl"/>
<!--===== 底部文件区域 E ======-->
<script language="javascript" src="http://pic.lvmama.com/js/super_v2/order_func.js"></script>
<#include "/common/ga.ftl"/>
<script language="javascript">
function reSendEContract(){	
	$.ajax({
		type: "POST",
		url: "/ajax/sendOrderEContract.do",
		async: false,
		data: {orderId: <@s.property value="order.orderId"/> },
		dataType: "json",
		success: function(response) {
		   	if (response.success == true) {				    				
				alert("合同已重新发送！");						
			}else{			
			    alert("合同重新发送失败！");				
			}				
		}
	});
}
</script>

<script>
	<#if order.mainProduct.productType == "HOTEL">
		cmCreatePageviewTag("支付成功页-"+"<@s.property value="order.mainProduct.productType"  escape="false"/>-DJJD-"+"<@s.property value="order.mainProduct.subProductType"  escape="false"/>", "Q0001", null, null);
		cmCreateConversionEventTag("支付成功页-${order.mainProduct.productType?if_exists}-DJJD-${order.mainProduct.subProductType?if_exists}","2","订单流程");
	<#else>
		cmCreatePageviewTag("支付成功页-"+"<@s.property value="order.mainProduct.productType"  escape="false"/>-"+"<@s.property value="order.mainProduct.subProductType"  escape="false"/>", "Q0001", null, null);
		cmCreateConversionEventTag("支付成功页-${order.mainProduct.productType?if_exists}-${order.mainProduct.subProductType?if_exists}","2","订单流程");
	</#if>
   
</script>
</body>
</html>
