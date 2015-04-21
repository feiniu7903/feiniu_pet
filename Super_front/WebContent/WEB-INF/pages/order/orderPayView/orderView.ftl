<#include "/WEB-INF/pages/buy/201107/navigation.ftl">
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="" name="description">
<meta content="" name="keywords">
<title>订单支付_驴妈妈旅游网</title>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/super_v2/s2_globalV1_0.css,/styles/super_v2/orderV2.css,/styles/new_v/header-air.css,/styles/cards01.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/pa-base.css,/styles/v4/modules/arrow.css,/styles/v4/modules/button.css,/styles/v4/modules/forms.css,/styles/v4/modules/selectbox.css,/styles/v4/modules/step.css,/styles/v4/modules/tags.css,/styles/v4/modules/tip.css,/styles/v4/modules/dialog.css,/styles/v4/modules/tables.css,/styles/v4/modules/bank.css" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/order-common.css,/styles/v4/order.css" >
<link href="http://pic.lvmama.com/styles/mylvmama/ui-lvmama.css" rel="stylesheet" />
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/common/footer.js,/js/v4/order-page.js"></script>

<script type="text/javascript" src="/js/bindMobile.js"></script>
<script type="text/javascript" src="/js/cards01.js"></script>
<script type="text/javascript" src="/js/mylvmama.js"></script>
<script src="/js/myspace/account.js" type="text/javascript"></script>
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
					}
				});
         }
         function chackOldCardNo(cardNo){
				$.post("/ajax/chackOldCardNo.do",{"cardNo":cardNo},function(dt){
					var data=eval("("+dt+")");
					if(data.success){	
						document.getElementById('submit_pay').disabled=false;			
					}else{
						alert(data.msg);
						document.getElementById('submit_pay').disabled=true;
					}
				});
          }
         function chackNewCardNo(cardNo){
				$.post("/ajax/chackNewCardNo.do",{"cardNo":cardNo},function(dt){
					var data=eval("("+dt+")");
					if(data.success){	
						document.getElementById('submit_pay').disabled=false;			
					}else{
						alert(data.msg);
						document.getElementById('submit_pay').disabled=true;
					}
				});
          }
</script>
</head>
<body> 
	<!--===== 头部文件区域 S ======-->
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
		</div>
	<!--===== 头部文件区域 E ======-->
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
	
	<!--===== 页面信用贴士S ====== 
		    <#include "/WEB-INF/pages/buy/201107/creditTip.ftl">
		    <#assign local_productNames><@s.iterator value="mainOrderList"><@s.property value="productName"/> </@s.iterator></#assign>
		    <@creditTip productName="${local_productNames}" productType="${order.mainProduct.productType?if_exists}"/>
	===== 页面信用贴士E ======-->

	<div class="order-main border">
		<#include "/WEB-INF/pages/order/orderPayView/orderInfo.ftl"/>
	    <div class="lightbox paybox clearfix">	  
	  		<div class="paytips">	
				  	<@s.if test="order.oughtPayYuan==0">
				  		您已使用优惠券支付了所有金额，请核查订单信息，如无误请点击确认按钮。
				  	</@s.if>
				  	<@s.else>
					  		友情提示：您的预订信息已提交，
					  		 <@s.if test='order.paymentTarget=="TOLVMAMA"'>
						  		请选择以下支付方式支付订单！
						  	</@s.if>
						  	<@s.else>
						  		请等待审核！
						  	</@s.else>
						  	<span style="color:#f00;padding-left:5px">请在${order.lastWaitPaymentTimeOfString}前完成付款，</span>	
						  	<span class="timebox">剩余付款时间：<b class="countdown red">${order.lastWaitPayMentTimeOfMinus}</b></span>
					  </@s.else>
		  	 </div>
		  	 <div class="dot_line"></div>
		<!--支付方式 S-->
		<@s.if test="order.oughtPayYuan==0">
		</@s.if>
		<@s.elseif test='order.isCanToPay()'>
		       <@s.if test='!order.isPaymentChannelLimit()'>
				    <!--储值卡 支付  S-->
					<#include "/WEB-INF/pages/order/orderPayView/bonusPay.ftl"/>
				    <#include "/WEB-INF/pages/order/orderPayView/storedToPay.ftl"/>
				    <div class="check-text">
						<label class="checkbox inline">
							<input class="input-checkbox" type="checkbox" name="ownpro" id="storeToPay">使用驴妈妈储值卡进行支付 
						</label>
					</div>
					<!--储值卡 支付支付-->
					<!--奖金账户 START-->
					<!-- 奖金账户END-->
					<!-- 存款账户支付 START -->
					<#include "/WEB-INF/pages/order/orderPayView/moneyAccountPay.ftl"/>
					<div class="check-text">
						<label class="checkbox inline">
							<input class="input-checkbox" type="checkbox" name="ownpro" id="depositToPay">使用驴妈妈存款账户余额 ${isLogin}
						</label>
					</div>
					<!-- 存款账户支付 END -->
		       </@s.if>
			   <#include "/WEB-INF/pages/order/orderPayView/canTopay.ftl"/>
			
		</@s.elseif>
		<@s.elseif test='order.isCanToPrePay()'>
		   <#include "/WEB-INF/pages/order/orderPayView/canTopay.ftl"/>
		</@s.elseif>
		<!--支付方式 E-->
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
		</div>	
	</div>
</div>
<!--===== 底部文件区域 S ======-->
<#include "/common/orderFooter.ftl">
<#include "/common/ga.ftl"/>
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
					if(r >5){
						$("#waiting_a").hide();
					$("#qr_a").show();
					}else{
						window.location.href='/order/toSuccess.do?r='+r+'&orderId=<@s.property value="order.orderId"/>';
					}
				},30000); 
          	</#if>
				 $("#storeToPay").click(function(){
				 	 if(this.checked){
  					  // 使用驴妈妈存款账户余额						
    					pandora.dialog({						
						    title: "使用驴妈妈储值卡",												    content:$("#storedId"),												    wrapClass: "dialog-middle"						
						 })				 
					  }
				})
				$("#depositToPay").click(function(){
					if(this.checked){
  						var content1 = $("#moneyAccountPayBox").html();	
  					  // 使用驴妈妈存款账户余额						
    					pandora.dialog({						
						    title: "使用驴妈妈存款账户余额",						
						    content:$("#moneyAccountPayBox"),						
						    width: "760px"
						 })				 
					  }
				})
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
<!--==意见反馈入口E==-->
</div>
<script src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
