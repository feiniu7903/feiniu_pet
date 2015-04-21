<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE7" /> 
<title>手机验证-驴妈妈迎新年-驴妈妈旅游网</title>
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/looknews/style.css" rel="stylesheet" type="text/css" />
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
</head>

<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<div id="login">
        <div class="log_main">            
            <div id="telcheck" class="tel_check">
                <h3>注册驴妈妈新会员</h3>
                <h4 class="step_01">01填写注册信息     02填写短信校验码     03注册完成</h4>
                <p>您好，我们已经向您的手机<strong><@s.property value='mobile' /></strong>发送了免费的短信验证码，请查看您的手机</p>
                <p>您的手机号：<em><@s.property value='mobile' /></em></p>
                <form action="/nsso/tuiguang/verifyCode.do" id="yzmPostForm" method="post">
                <@s.token></@s.token>
                <input name="channel" type="hidden" value="newyearGifts"/>
                <input name="pagePath" type="hidden" value="newyearGifts"/>
                <input name="mobile" type="hidden" value="${mobile}"/>
                <input name="mobileNumber" type="hidden" id="mobileNumber"  value="${mobile}"/>
                <table width="300" border="0">
                  <tr>
                    <td width="72">短信验证码：</td>
                    <td>
                        <input id="yzm" class="check" type="text" name="authenticationCode" size="10" value="">
                    </td>
                  </tr>
                  <tr>
                  	<td>&nbsp;</td>
                    <td><@s.actionerror/></td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td><input type="button" value="完成验证" onClick="$('#yzmPostForm').submit()"></td>
                  </tr>
                </table>
                </form>
                <p>如果你上时间没有收到短信，点击<a href="#" onClick="sendAuthenticationCode()">重新发送</a>确认短信</p>
            </div><!--telcheck end-->            
        </div><!--log_main end-->
</div><!--login end-->
<script src="/nsso/js/common/jquery.js" type="text/javascript"></script>
<script src="/nsso/js/common/closeF5MouseRight.js" type="text/javascript"></script>
<script>
        function sendAuthenticationCode() {             
            $.ajax({
                type: "POST",
                url: "/nsso/ajax/reSendAuthenticationCode.do",
                async: false,
                data: {
                    mobile: <@s.property value='mobile' />
                },
                dataType: "json",
                success: function(response) {
                    if (response.success == true) {
                        alert('验证码已经重新发送成功');
                    } else {
                        alert('验证码发送失败，请重新尝试');
                    }
                }
            }); 
        }
</script>

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
