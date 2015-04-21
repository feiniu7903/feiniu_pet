<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE7" /> 
<title>绑定成功-驴妈妈迎新年-驴妈妈旅游网</title>
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/looknews/style.css" rel="stylesheet" type="text/css" />
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
</head>

<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<div id="login">
        <div class="log_main">            
            <div id="succ_gift">
                <h3>获取礼品</h3>
                <h4 class="step01">01绑定手机&#38;邮箱     02成功获取礼品</h4>
                <#if errorFlag==1>
                <p>您之前已成功获取总价值800元的优惠券一份。</p>
                <P>请注意查收您的账户【我的驴妈妈】【我的优惠券】，关注优惠券发放情况。</p>
                <#elseif errorFlag==2>
                <p>礼品获取失败！</p>
                <#else>
                <p>感谢您的参与，800元优惠券已发放到您账户，<strong>【我的驴妈妈】&rarr;【我的优惠券】</strong>，请您注意登录查收，谢谢。</p>
                </#if> 
            </div><!--succ_gift end-->           
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
