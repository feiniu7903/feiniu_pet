<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE7" /> 
<title>手机和邮箱绑定-驴妈妈迎新年-驴妈妈旅游网</title>
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/looknews/style.css" rel="stylesheet" type="text/css" />
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<script type="text/javascript" src="/nsso/js/member/mySpace.js"></script>
<script src='/nsso/js/member/new_login_web.js' type='text/javascript'></script>
</head>

<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<div id="login">
    <div class="log_main">
         <div id="bound" class="binding">
                <h3>获取礼品</h3>
                <h4 class="step02">01绑定手机&#38;邮箱     02成功获取礼品</h4>
                <p>为了保证您能顺利获得礼品和更多的会员专享，需要将您的账号绑定手机和邮箱，确保参与活动的每个用户的真实身份。若您之前已绑定其中一个，请完善另一个。</p>
                <div class="infor">
                      <@s.if test="users != null">
                      <input type="hidden" id="userId" value="${users.userId}"/>
                      <input name="bindSuccURL" type="hidden" value="${bindSuccURL}"/>
                      </@s.if>
                      <p>手机：    
                        <@s.if test="users==null || users.mobileNumber == null">
                            <a href="javascript:void(0);" class="tanchu">绑定手机</a>   
                        </@s.if>
                        <@s.else>
                            <@s.if test='users.isMobileChecked!="Y"'>
                                <@s.property value="users.mobileNumber"/>&nbsp;&nbsp;<a href="javascript:void(0);" class="tanchu">手机未验证,重新验证手机</a>
                            </@s.if>
                            <@s.else>
                                <@s.property value="users.mobileNumber"/>&nbsp;&nbsp;<a href="javascript:void(0);" class="tanchu">更改已验证的手机</a>
                            </@s.else>      
                        </@s.else>                    
                      </p>
                      <p>邮箱：
                        <@s.if test="users==null || users.email == null">
                            <a href="javascript:void(0);" class="e-mall_activation">更新邮箱</a>    
                        </@s.if>
                        <@s.else>
                            <@s.if test='users.isEmailChecked!="Y"'>
                                <@s.property value="users.email"/>&nbsp;&nbsp;<a href="javascript:void(0);" class="e-mall_activation">邮箱未验证,重新验证邮箱</a>
                            </@s.if>
                            <@s.else>
                                <@s.property value="users.email"/>&nbsp;&nbsp;<a href="javascript:void(0);" class="e-mall_activation">更改已验证的邮箱</a>
                            </@s.else>      
                        </@s.else>
                      </p>  
                      <p>
                        <input type="button" class="next_step" value="完成绑定，下一步" id="registBtn" name="registBtn" onClick="prompt();">
                      </p>
                </div>
                <p>如果您不想进行相应的绑定手机&#38;邮箱，请<a href="#">点击这里</a>直接查看可以预订的旅游产品</p>
            </div><!--binding end-->
      </div>
</div><!--login end-->
  <!--绑定手机　e-->
<div id="msgDiv">
  <div class="div_block">
    <div><a href="javascript:void(0)"><img class="closeBlock" src="http://pic.lvmama.com/img/icons/close.gif" id="msgShut" alt="关闭" /></a></div>
    <div class="msgNewBlock">
      <p class="msgTitle">绑定手机</p>
      <label> <span>手机号码：</span>
      <input type="text"  name="mobileNumber" id="mobileNumber"  maxlength="11" class="writein_field" size="22"/>
      </label>
      <label> <span>验证码：</span>
      <input type="text" style="width:88px;" id="valDate" size="6"/>
      <img style="vertical-align:middle;" id="image" src="/nsso/account/checkcode.htm" />
      <a href="javascript:void(0)" style="*position:relative;top:-5px;color:#0056AE" onClick="refreshCheckCode('image')">换一张</a> </label>
      <p class="msgError"><span id="mobileNumberTip"></span></p>
      <p>
        <input id="msgButs" name="msgButs" type="button" class="newbutton" value="发送手机绑定码" /><span id="msgButs_Msg"></span>
      </p>
      <p class="msgRecord"><span>注：</span>手机绑定码将以短信方式发送到您的手机，60秒后可重发。</p>
      <p style="margin-bottom:18px;">请在下面填写您收到的手机绑定码</p>
      <label> <span>手机绑定码：</span>
      <input style="width:88px;" type="text" name="validateNumber" id="validateNumber" class="writein_field" size="6" maxlength="6" />
      </label>
      <p class="msgError"><span id="validateNumberTip"></span></p>
      <p>
        <input id="checkRealNumber" type="button" class="newbutton" value="确认" />
      </p>
    </div>
  </div>
</div><!--msgDiv end-->

<div id="mailDiv">          
<form method="post" action="/userCenter/user.do" onsubmit="return true;" name="mailForm" id="mailForm">
<!-- 验证开始 -->
<div class="ticketer_detail_new">
<div class="close"><a href="javascript:void(0)"><img alt="关闭" id="msgShut3" src="http://pic.lvmama.com/img/icons/close.gif" class="closeBlock"></a></div>
<span id="successInfo">
    <table width="100%" cellspacing="0" cellpadding="0" border="0" class="contact">
      <tbody><tr>
        <td width="80" align="right">邮件地址：</td>
        <td><input type="text" class="writein_field" value="<@s.property value="users.email"/>" id="mail" name="mail" onfocus="info();"></td>
      </tr>
       <tr>
        <td width="50" align="right"></td>
        <td><font color="red"><div id="mailTip">请输入邮件地址</div></font></td>
      </tr>
      <tr>
        <td width="50" align="right"></td>
        <td><input type="button" value="发送验证邮件" class="button" onclick="checkMail();" id="mailButton"></td>
      </tr>
    </tbody></table>
</span>
</div>
<!-- 验证结束 -->
</form>
</div>
<div id="bgDiv"></div>

<script type="text/javascript"> 
    function prompt() {
        var msg;
        <@s.if test='users.mobileNumber == null || users.isMobileChecked!="Y"'>
            msg = "请绑定手机";
        </@s.if>
        <@s.if test='users.email == null || users.isEmailChecked!="Y"'>
            if (msg==null) {
                msg = "请绑定邮箱";
            } else {
                msg = "和邮箱";
            }
        </@s.if>
        alert("手机、邮箱必须同时绑定，" + msg);
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
