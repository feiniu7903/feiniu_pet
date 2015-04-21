<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE7" /> 
<title>注册完成-驴妈妈迎新年-驴妈妈旅游网</title>
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/looknews/style.css" rel="stylesheet" type="text/css" />
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
</head>

<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<div id="login">
        <div class="log_main">
            
            <div id="finsh" class="finsh main_top">
                <h3>注册驴妈妈新会员</h3>
                <h4 class="step_02">01填写注册信息     02填写短信校验码     03注册完成</h4>
                <p>恭喜，注册成功。</p>
                <p>您今后可使用手机号<strong><#if user!=null>${user.mobileNumber}</#if></strong>或用户名<strong><#if user!=null>${user.userName}</#if></strong>登录驴妈妈旅游网。 </p>
                <p>以后您也可以通过该手机号找回密码。 </p>
                <a class="get_gift" id="get_gift" href="/nsso/bind/application.do?pagePath=newyearGifts&bindSuccURL=http://login.lvmama.com/nsso/newyeargift/bindsucc.do">马上领取奖品</a>
            </div><!--finish end-->            
        </div><!--log_main end-->
</div><!--login end-->
 <!--SEM搜索引擎--> 
<script type="text/javascript"> 
function QueryString(item){ 
var sValue=location.search.match(new
RegExp("[\?\&]"+item+"=([^\&]*)(\&?)","i")) 
return sValue?sValue[1]:sValue 
} 
var orderFromChannel = QueryString('losc'); 
if (orderFromChannel != null) { 
var cookie_date=new Date();  
cookie_date.setDate(cookie_date.getDate()+30); 
document.cookie = "orderFromChannel=" + escape (orderFromChannel) +
";path=/;domain=.lvmama.com;expires="+cookie_date.toGMTString(); 
} 
</script> 
<!--SEM搜索引擎END-->
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
<script src="http://pic.lvmama.com/js/common/losc.js" language="javascript"></script>
</body>
</html>
