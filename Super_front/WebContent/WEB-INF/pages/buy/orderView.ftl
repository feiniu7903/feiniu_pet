<#include "/WEB-INF/pages/buy/201107/navigation.ftl">
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="" name="description">
<meta content="" name="keywords">
<title>订单支付_驴妈妈旅游网</title>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/super_v2/s2_globalV1_0.css,/styles/super_v2/orderV2.css,/styles/new_v/header-air.css,/styles/cards01.css,/styles/v5/modules/tip.css"/>
<script src='/js/common/jquery.js' type='text/javascript'></script>
<script type="text/javascript" src="/js/bindMobile.js"></script>
<script type="text/javascript" src="/js/cards01.js"></script>
<script type="text/javascript" src="/js/mylvmama.js"></script>
<script>
function myLvmamaSelect(){
	var $mylv = $("#my-lvmama");
	var $mylvs = $("#my-lvmama a");
	var $sublist = $("#lvmama_sub");
		$mylv.mouseover(function(){
			var leftX =$(this).offset().left-($sublist.outerWidth()-$(this).outerWidth())/2;
			$sublist.css({"display":"block","left":leftX,"right":"auto"});
		});
		$mylv.siblings().mouseover(function(){
			$sublist.css({"display":"none"})
		});
		$sublist.mouseover(function(){
			$sublist.css({"display":"block"});
		}).mouseout(function(){$sublist.css({"display":"none"});})	
}
myLvmamaSelect();

					
                 function chackVerifycode(verifycode){
						$.post("/ajax/chackVerifycode.do",{"verifycode":verifycode},function(dt){
							var data=eval("("+dt+")");
							if(data.success){								
							}else{
								alert(data.msg);
								$("#verifycode").val("");
							}
						});
                  }

                  function chackOldCardNo(cardNo){
						$.post("/ajax/chackOldCardNo.do",{"cardNo":cardNo},function(dt){
							var data=eval("("+dt+")");
							if(data.success){		
							}else{
								alert(data.msg);
							}
						});
                    }

				function chackNewCardNo(cardNo){
					$.post("/ajax/chackNewCardNo.do",{"cardNo":cardNo},function(dt){
						var data=eval("("+dt+")");
						if(data.success){			
						}else{
							alert(data.msg);
						}
					});
	          }


</script>
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/prestore.css">
<#include "/common/coremetricsHead.ftl">
</head>

<body>
<div id="warp">
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
			stepView="payView"  
			payToSupplier="${(order.paymentTarget=='TOSUPPLIER')?string('true','false')}" 
			fillTravellerInfo="${(travellerInfoOptions!=null)?string('true','false')}"/>
	<!--===== 导航条 E ======-->
	
	<!--===== 页面信用贴士S ======-->
		    <#include "/WEB-INF/pages/buy/201107/creditTip.ftl">
		    <#assign local_productNames><@s.iterator value="mainOrderList"><@s.property value="productName"/> </@s.iterator></#assign>
		    <@creditTip productName="${local_productNames}" productType="${order.mainProduct.productType?if_exists}"/>
	<!--===== 页面信用贴士E ======-->

  <div class="tips-info">	  	
				<p class="tips-title"><s></s>				
			  	<strong>
			  	<@s.if test="order.oughtPayYuan==0">
			  		您已使用优惠券支付了所有金额，请核查订单信息，如无误请点击确认按钮。
			  	</@s.if>
			  	<@s.else>
				  		订单提交成功，
				  		 <@s.if test='order.paymentTarget=="TOLVMAMA"'>
					  		请选择以下支付方式支付订单！
					  	</@s.if>
					  	<@s.else>
					  		请等待审核！
					  	</@s.else>
					  	<span style="color:#f00;padding-left:5px">注：请于${order.lastWaitPaymentTimeOfString}之前完成支付，逾期订单将失效</span>	
				  </@s.else>
				</strong>
				</p>	
				<@s.if test="order.oughtPayYuan>0">
					<span class="price">应付总额：<strong>&yen;${order.oughtPayYuanFloat?if_exists}</strong></span>
				</@s.if>
	  	
</div>
<!--支付方式 S-->
<@s.if test="order.oughtPayYuan==0">
</@s.if>
<@s.elseif test='order.isCanToPay()'>
       <@s.if test='!order.isPaymentChannelLimit()'>
		   <!--储值卡 支付  S-->
			<#include "/WEB-INF/pages/buy/storedToPay.ftl"/>
			<!--储值卡 支付支付-->
			<#--<#include "/WEB-INF/pages/buy/bonusPay.ftl"/>-->
			
			<!-- 存款账户支付 START-->
			<@s.if test="!tempCloseCashAccountPay">
				<#include "/WEB-INF/pages/buy/moneyAccountPay.ftl"/>
			</@s.if>
			<!-- 存款账户支付 END-->
       </@s.if>
	   <#include "/WEB-INF/pages/buy/canTopay.ftl"/>
	
</@s.elseif>

<@s.elseif test='order.isCanToPrePay()'>
   <#include "/WEB-INF/pages/buy/canTopay.ftl"/>
</@s.elseif>




<!--支付方式 E-->

<!--订单信息 S-->
<#include "/WEB-INF/pages/buy/orderInfo.ftl"/>
<!--订单信息 E-->
<@s.if test="order.oughtPayYuan==0 && order.isApproveResourceAmple()">
<center>
	<#if payState?? && payState =='UNPAY'>
		<a  id= "waiting_a" href="javascript:void(0);">订单处理中....</a>
		<a id= "qr_a" href="/pay/pay0.do?orderId=<@s.property value="order.orderId"/>" style="display:none;"><img src="http://pic.lvmama.com/img/qr.gif" alt="确认"/></a>
	<#else>
		<a href="/pay/pay0.do?orderId=<@s.property value="order.orderId"/>"><img src="http://pic.lvmama.com/img/qr.gif" alt="确认"/></a>
	</#if>
	</center>
</@s.if>
<!--===== 底部文件区域 S ======-->
<#include "/common/orderFooter.ftl">
	<#include "/WEB-INF/pages/buy/googleAnaly.ftl"/>
<!--===== 底部文件区域 E ======-->
<script language="javascript" src="http://pic.lvmama.com/js/super_v2/order_func.js?r=8391"></script>
<!--==意见反馈入口S==-->
<style>
#u_container{width:11px; height:69px; position:fixed;}#usercenter_float{background:url(http://pic.lvmama.com/img/bg_floating.gif) no-repeat 0 0; display:block; width:11px; height:53px; line-height:15px; padding:5px 4px 11px;}a#usercenter_float{color:#404040;text-decoration:none;}a#usercenter_float:hover{color:#c06;text-decoration:none;}
</style>
<!--<div id="u_container">
	<a id="usercenter_float" target="_blank" href="http://www.lvmama.com/userCenter/user/transItfeedBack.do">意见反馈</a>
</div>-->
<script type="text/javascript"> 
          $(function(){
          	<#if payState?? && payState=='UNPAY'>
				setTimeout(function(){
					var r = 1;
					<#if r??>
						r = ${r};
						r =r+ 1;
					</#if>
					if(r > 5){
						$("#waiting_a").hide();
					$("#qr_a").show();
					}else{
						window.location.href='/order/toSuccess.do?r='+r+'&orderId=<@s.property value="order.orderId"/>';
					}
				},30000); 
          	</#if>
              $(".tit-payway").click(function(){
                  $(this).siblings(".ico-payway").slideDown(400,function(){
                      $(this).siblings("p").css({"background":"url('http://pic.lvmama.com/img/icons/btn-down.gif') no-repeat scroll 99% 50% ","background-color":"#f0f0f0"})
                  })
                  .end().parent().siblings().children(".ico-payway").slideUp(400,function(){
                      $(this).siblings("p").css({"background":"url('http://pic.lvmama.com/img/icons/btn-right.gif') no-repeat scroll 99% 50% ","background-color":"#f0f0f0"})
       
                  });
              })
          });
</script>
<script>
jQuery(function($){
FollowDiv = {
	  follow : function(){
		  $("#u_container").css("position","absolute");
		  $(window).scroll(function(){
			  var f_top = $(window).scrollTop() + $(window).height() - $("#u_container").height()-69;
			  $("#u_container").css("top",f_top+"px");
		  });
	  }
}
		var leftPX = ($(window).width()-980)/2+982;
		var topPX = $(window).height()-$("#u_container").height()-69;
		$("#u_container").css({"left":leftPX+"px","top":topPX+"px"})
						   
			if($.browser.msie && $.browser.version == 6) {
				FollowDiv.follow();
			}
});
</script>
<#include "/common/ga.ftl"/>
<script>
	<#if order.mainProduct.productType == "HOTEL">
		cmCreatePageviewTag("选择支付方式-"+"<@s.property value="order.mainProduct.productType"  escape="false"/>-DJJD-"+"<@s.property value="order.mainProduct.subProductType"  escape="false"/>（订单提交成功）", "Q0001", null, null);
	<#else>
		cmCreatePageviewTag("选择支付方式-"+"<@s.property value="order.mainProduct.productType"  escape="false"/>-"+"<@s.property value="order.mainProduct.subProductType"  escape="false"/>（订单提交成功）", "Q0001", null, null);
	</#if>
	 //数量
	 try{
		<#assign num=0/>
		<#assign payYuan=0/>
		<#list mainOrderList as mainOrder>
			<#assign num="${num?number + mainOrder.quantity?number}"/>
			<#assign payYuan="${payYuan?number + mainOrder.getPriceYuan()?number}"/>
		</#list>
		<#list relativeOrderList as relativeOrder> 
			<#assign num="${num?number + relativeOrder.quantity?number}"/>
			<#assign payYuan="${payYuan?number + relativeOrder.getPriceYuan()?number}"/>
		</#list>
		//均价
		<#assign price="${payYuan?number / num?number}"/>
		$.getJSON("http://login.lvmama.com/nsso/ajax/getUserNo.do?jsoncallback=?",function(req){
			var booker = "null";
			if(req.success){
				booker = req.result;
			}
			cmCreateShopAction9Tag("${order.mainProduct.productId?if_exists}","<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(order.mainProduct.productName)"/>","${num}","${price}",booker,"${order.orderId?if_exists}","${order.oughtPayYuan?if_exists}", "${order.mainProduct.subProductType?if_exists}");
			cmDisplayShops();
			cmCreateOrderTag("${order.orderId?if_exists}","${order.oughtPayYuan?if_exists}","0",booker);
		});
	}catch(exception){}
</script>
<!--==意见反馈入口E==-->
</div>
</body>
</html>
