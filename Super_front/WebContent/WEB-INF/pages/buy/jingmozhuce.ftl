<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录/注册_驴妈妈旅游网</title>
<link href="http://pic.lvmama.com/styles/super_v2/s2_globalV1_0.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/super_v2/s2_orderV1_0.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery.js"></script>
<script type="text/javascript" src="http://login.lvmama.com/nsso/js/sso.js"></script>
<!-- 开心网 -->
<script src="http://rest.kaixin001.com/api/FeatureLoader_js.php" type="text/javascript"></script>
<style type="text/css">
.topmsg{width:760px;height:100px;padding:20px 0px 30px;background:#fcf0f6; text-align:center;font-size:14px;color:#333; display:block; margin:0 auto;}
.topmsg b{font-weight:700;font-size:20px;margin-bottom:15px;display:block;}
.topmsg p{width:650px;margin:0 auto; text-align:left; line-height:30px;}
.topmsg p strong{color:#cc0066; font-weight:700;}
#screenshot{position:absolute;display:none;}
div.order-pay2{border:none;}
div.order-pay2 p{font-size:14px; background:url(http://pic.lvmama.com/img/jmpbg.gif) no-repeat left 4px; padding-left:20px; line-height:28px;}
div.order-pay2 p strong{ font-weight:700;color:#333;}
div.order-pay2 p span{color:#cc0066}
div.order-pay2 p img{ vertical-align:middle;}
.layer-block li{ list-style:none; display:inline;}
.layer-block ul{margin-bottom:30px;}
.layer-block ul li{margin-bottom:0; line-height:18px;margin-left:30px;width:370px;}
.layer-block ul li span{ display:block;}
.layer-block ul li input{ vertical-align:middle;margin-top:5px; width:150px;height:25px;line-height:25px;}
.layer-block ul li input.btn-yanzheng{width:auto;height:auto;}
.layer-block h4{ font-weight:700;margin-bottom:10px;font-size:14px;color:#333;}
.layer-block{margin:0 auto 20px;}
.bottips{ border-top:1px solid #ccc;padding-top:5px;color:#666; width:760px;margin:0 auto;}
#mobileLoinTextErrorTip font{}
#AjaxMobileSlientRegisterFormErrorMessage{margin-left:95px;}
#mobileLoinTextErrorTip{margin-left:95px}
.weibo {padding-top:5px;}
.weibo li {height:28px;}
.sina {float:left;margin-right:20px;}
.qq {margin-right:20px;float:left;display:block;}
#kx001_btn_login {margin-top:5px;}
#kx001_btn_login span {padding:0 8px 0 6px;}
</style>
<script type="text/javascript"> 
document.domain='lvmama.com'; 
</script>
</head>

<body style="background:none;">
<!--===== 头部文件区域 S ======-->
<div style="text-align:center;">
	<#include "/common/buyHeader.ftl">
</div>
<div class="s2-order-container">
<center class="topmsg"><b>您的订单已生成，请登录/注册并完成支付！</b><p>请牢记您的订单号：<strong><@s.property value="order.orderId"/></strong>&nbsp;&nbsp;以及此订单的<#if order.orderType == 'HOTEL'>入住人<#else>取票人</#if>手机号：<strong><@s.property value="order.booker.mobile" /></strong><br />您可以在网站首页顶部的 “<a href="#" class="screenshot" rel="http://pic.lvmama.com/img/jingmo.png">快速查询订单</a>” 页面查看您的订单状态或进行支付。</p>
</center>
<!--===== 头部文件区域 E ======-->
<!--<@s.if test="isLogin()">
			<input name="userId" type="hidden" id="userId" value="<@s.property value='getUserId()'/>"/>
			<@s.if test="order.contact.mobile==order.booker.mobile">
			</@s.if>
			<@s.else>
	
					  <div class="order-pay">
					  	<p>您的账号已经关联手机账号，如果[<@s.property value="order.booker.mobile" />]是您本人，您可以现在<a href="javascript:void(0)" onclick="$('#hasIdLayer').slideDown(800);" id="mobile_yanzheng">验证该手机号</a></p>
					    <div class="layer-block" id="hasIdLayer">
					    </div>
					  </div>
		
		</@s.else>
</@s.if>
<@s.else>-->
	  <!--===== 没有登录 S ======-->
	  <div class="order-pay order-pay2">
	    <p><strong>请注意：</strong>该笔订单目前还未和任何账号关联，因此您不能获得<span>消费积分</span>和可能的<span>点评奖金</span>，如果您已有驴妈妈账号，请务必在本页面登录。<a href="javascript:void(0)" onclick="regSlideDown('login');" id="login_a"><img src="http://pic.lvmama.com/img/jmbtn01.gif" /></a></p>
	  	<p>如果您尚未注册，请在此处注册，订单会自动关联到您注册的账号。<a href="javascript:void(0)" onclick="regSlideDown('reg');" id="reg_a"><img src="http://pic.lvmama.com/img/jmbtn02.gif" /></a></p>		
	    <div class="layer-block" id="loginLayer">
	    </div>
	    <div class="layer-block" id="regLayer">
	    </div> 	 	    
	  </div>
        <div class="bottips">温馨提示：登录后，您可以在“我的驴妈妈”中查看订单和享受其它会员服务。</div>		  
	  <!--===== 未登录 E ======-->
</@s.else>
</div>
<script type="text/javascript">
this.screenshotPreview = function(){	
	/* CONFIG */
		
		xOffset = 10;
		yOffset = 30;
		
		// these 2 variable determine popup's distance from the cursor
		// you might want to adjust to get the right result
		
	/* END CONFIG */
	$("a.screenshot").hover(function(e){
		this.t = this.title;
		this.title = "";	
		var c = (this.t != "") ? "<br/>" + this.t : "";
		$("body").append("<p id='screenshot'><img src='"+ this.rel +"' alt='url preview' />"+ c +"</p>");								 
		$("#screenshot")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px")
			.fadeIn("fast");						
    },
	function(){
		this.title = this.t;	
		$("#screenshot").remove();
    });	
	$("a.screenshot").mousemove(function(e){
		$("#screenshot")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px");
	});			
};


// starting the script on page load
$(document).ready(function(){
	screenshotPreview();
});
</script>
<script type="text/javascript">
function regSlideDown(divId){
	if("login"==divId){
		$("#loginLayer").slideDown(800);  
		$("#regLayer").slideUp(1);  
	}else if("reg"==divId){
		$("#regLayer").slideDown(800);  
		$("#loginLayer").slideUp(1);  
	}
}
	function updateOrder(){
		var url = '/udpate/order.do?orderId=${order.orderId}&id=${id}';
		window.location.href=url;
	}
	function orderView(){
		var url = '/view/view.do?orderId=${order.orderId}&id=${id}';
		window.location.href=url;
	}
	var ajaxMobileSlientRegisterForm = new AjaxMobileSlientRegisterForm();
	ajaxMobileSlientRegisterForm.onSuccess("updateOrder");
	ajaxMobileSlientRegisterForm.render("regLayer");
	
	var loginForm = new AjaxSSOLoginForm();
	loginForm.onSuccess("updateOrder");
	loginForm.render("loginLayer");
	
	var ajaxMobileOrderValidateAuthenticationcodeForm = new AjaxMobileOrderValidateAuthenticationcodeForm('13044102235');
	ajaxMobileOrderValidateAuthenticationcodeForm.onSuccess("orderView");
	ajaxMobileOrderValidateAuthenticationcodeForm.render("hasIdLayer");
	
</script>
<!--===== 底部文件区域 S ======-->
<#include "/common/orderFooter.ftl">
<!--===== 底部文件区域 E ======-->
<!--==意见反馈入口S==-->
<style>
#u_container{width:11px; height:69px; position:fixed;}#usercenter_float{background:url(http://pic.lvmama.com/img/bg_floating.gif) no-repeat 0 0; display:block; width:11px; height:53px; line-height:15px; padding:5px 4px 11px;}a#usercenter_float{color:#404040;text-decoration:none;}a#usercenter_float:hover{color:#c06;text-decoration:none;}
</style>
<div id="u_container">
	<a id="usercenter_float" target="_blank" href="http://www.lvmama.com/userCenter/user/transItfeedBack.do">意见反馈</a>
</div>
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
</body>
</html>

