<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>驴妈妈会员卡申请</title>
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/new_v/header-air.css">
<link href="http://pic.lvmama.com/styles/v7style/globalV1_0.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/super_v2/newReg.css" />
<link href="/nsso/style/card.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/top/top_common_noLazy.js"></script>
</head>

<body>
<#assign imagesPath="/nsso/images/member_card/card_images">
<!-- 头部 -->
<#include "/WEB-INF/ftl/comm/header.ftl">
<!-- 头部 -->
<div class="card_wapper">
  <div class="card_pic"><img src=${imagesPath + "/card.gif"} width="980" height="340" /></div>
  <div class="card_txt">
    <p>"驴妈妈会员卡，让您选择多，奖励多多！申请驴妈妈会员卡，即可让您轻松订票，旅游更轻松……" </p>
  </div>
  <div class="card_cont">
    <div class="card_main">
      <div class="card_title"><img src=${imagesPath + "/card_title.gif"} width="318" height="40" /></div>
      <div class="card_ka"><img src=${imagesPath + "/card_ca.gif"} width="260" height="176" /></div>
      
      
    <#if users != null>
	      <div class="login_online" >
	        <p>您好，系统检测到您已登录驴妈妈账号</p>
	        <p class="card_names">${users.getUserName()}</p>
	        
	        <p>请点击申请按钮进入会员卡申请流程</p>
	        <div class="shengqing"><a href="javascript:void(0);" class="tanchu01"><img src=${imagesPath + "/shenqing.gif"} width="166" height="49" /></a></div>
	      </div>
    <#else>
	      <div class="login_online" >
	        <p>您还未登录驴妈妈账号， <a href="http://login.lvmama.com/nsso?service=http://login.lvmama.com/nsso/onlineApplyMemberCard/index.do">请登录>></a></p>
	        <p>如您没有驴妈妈账号， <a href="http://login.lvmama.com/nsso/register/registering.do">请注册>></a></p>
	      </div>
     </#if>
    
           
    <div class="info_main">
        <div><img src=${imagesPath + "/infor.gif"} width="199" height="26" /></div>
        <p>1.若您已经是驴妈妈会员，请登录后，再完成申请。</p>
        <p>2.若您没有驴妈妈账号，请在注册页面中完成注册。 </p>
        <p>3.驴妈妈会员卡号与每一位驴妈妈会员用户一一对应，如果您已拥有驴妈妈会员卡，无须重复申请。</p>
      </div>
      <div class="sq_pic"><img src=${imagesPath + "/qa.gif"} width="651" height="291" /></div>
    </div>
    <div class="card_side">
      <div><img src=${imagesPath + "/liucheng.gif"} width="250" height="375" /></div>
      <div class="hot_tuijian">
        <div class="tuijian_pic">
          <div class="pic_seat"><a href="http://www.lvmama.com/zt/abroad/lundun.html" target="_blank"><img src="http://pic.lvmama.com/opi/http://pic.lvmama.com/opi/abr_london_980x280.jpg" width="215" height="100" /></a></div>
          <div><a href="http://www.lvmama.com/zt/lvyou/chujing" target="_blank"><img src="http://pic.lvmama.com/opi/abr_spring_980x280.jpg"} width="215" height="100" /></a></div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- 已申请会员卡弹出层 -->
<div class="bg-tan"></div>
<div class="card_tan">
  <div class="choujiang">
    <div class="login_title">
      <h4>已申请驴妈妈会员卡</h4>
      <div class="closed"><a  class="closed"><img src=${imagesPath + "/closed.gif"} /></a></div>
    </div>
    <div class="cj_list">
      <p>您好，该账户之前已申请过驴妈妈会员卡，无须重复申请。</p>
      <p>若您没有激活驴妈妈会员卡，请在激活页面完成激活。 <a href="http://www.lvmama.com/login/loginBindCard.do">前往激活>></a></p>
      <input  type="button" class="bt_lv1" />
    </div>
  </div>
</div>
<!-- 尾部 -->
<#include "/WEB-INF/ftl/comm/footer.ftl"/>
<!-- 尾部 -->
<#if users != null>
<script type="text/javascript">
$(function(){
	var leftN = $(window).width()/2-198;
	var topN = $(window).height()/2-70;
	var jsonUrl = "http://www.lvmama.com/others/member_card/check.php?userid=${users.getUserId()}&callback=?";
	
	$(".tanchu01").click(function(){
		$.getJSON(jsonUrl,function(json){ 
			var result = json.result;//true：已申请,false:未
			if (!result) {
				window.location.href="/nsso/bind/application.do?pagePath=membercard&bindSuccURL=http://www.lvmama.com/others/member_card/login2.php";//会员卡的申请页面
			}else {
				 $('.bg-tan,.card_tan').show();  //已申请 弹出框
				$(".card_tan").fadeIn(10).css({"top":topN+$(window).scrollTop(),"left":leftN});
			}
		}); 
	});
		
	$('.closed,.bt_lv1').click(function(){    //已申请 弹出框 关闭
				$('.bg-tan,.card_tan').hide();
				//widow.location.href="www.lvmama.com";  已有会员卡需要跳转到主页么
		});
		
})
 </script>
 </#if>



<!-- Gridsum tracking code begin. -->
<script type='text/javascript'>
    (function () {
        var s = document.createElement('script');
        s.type = 'text/javascript';
        s.async = true;
        s.src = (location.protocol == 'https:' ? 'https://ssl.' : 'http://static.')
         + 'gridsumdissector.com/js/Clients/GWD-000268-6F8036/gs.js';
        var firstScript = document.getElementsByTagName('script')[0];
        firstScript.parentNode.insertBefore(s, firstScript);
    })();
</script>
<!--Gridsum tracking code end. -->


</body>
</html>
