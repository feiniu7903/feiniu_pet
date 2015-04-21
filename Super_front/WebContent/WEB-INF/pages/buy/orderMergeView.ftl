<#include "/WEB-INF/pages/buy/201107/navigation.ftl">
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="" name="description">
<meta content="" name="keywords">
<title>订单支付_驴妈妈旅游网</title>
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/s2_globalV1_0.css">
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/orderV2.css">
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<link type="text/css" rel="stylesheet" href="/style/cards01.css">
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
			}
		});
  }

  function chackCardNo(cardNo){
		$.post("/ajax/chackCodeNo.do",{"cardNo":cardNo},function(dt){
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
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/prestore.css">
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
					  	<span style="color: black;">
						  	订单号：
						  	<#list orderIdArray as orderId>
						  		<a target="_blank" class="status detail-link" href="${base}/myspace/order_detail.do?orderId=${orderId!}">${orderId!}</a>
							</#list>
						  	  进行合并支付
					  	</span>	
				  </@s.else>
				</strong>
				</p>	
				<@s.if test="order.oughtPayYuan>0">
					<span class="price">应付总额：<strong>&yen;${oughtPayTotalAmount!}</strong></span>
				</@s.if>
	  	
</div>
<!--支付方式 S-->
<@s.if test="order.oughtPayYuan==0">
</@s.if>
<@s.elseif test='order.isCanToPay()'>
	   <#include "/WEB-INF/pages/buy/canToMergePay.ftl"/>
</@s.elseif>
<!--支付方式 E-->


<@s.if test="order.oughtPayYuan==0 && order.isApproveResourceAmple()">
<center>
	<a href="/pay/pay0.do?orderId=<@s.property value="order.orderId"/>"><img src="http://pic.lvmama.com/img/qr.gif" alt="确认"/></a>
	</center>
</@s.if>
<!--===== 底部文件区域 S ======-->
<#include "/common/orderFooter.ftl">
	<#include "/WEB-INF/pages/buy/googleAnaly.ftl"/>
<script type='text/javascript'>
    if (window.GridsumWebDissector) {
        var _gsTracker = GridsumWebDissector.getTracker("GWD-000268");
        _gsTracker.track("/targetpage/order_pay");
    }
</script>
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
<!--==意见反馈入口E==-->
</div>
</body>
</html>
