<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>千岛湖森林氧吧送门票-认证手机和邮箱</title>
<link href="http://pic.lvmama.com/styles/v7style/globalV1_0.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/super_v2/newReg.css" />
<link href="/nsso/style/card.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<script type="text/javascript" src="/nsso/js/member/mySpace.js"></script>
<script src='/nsso/js/member/new_login_web.js' type='text/javascript'></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/top/top_common_noLazy.js"></script>
</head>
<#assign imagesPath="/nsso/images/member_card/card_images">
<body>
<!-- 头部 -->
<#include "/WEB-INF/ftl/comm/header.ftl">
<!-- 头部 -->
<div class="card_tan1"> 
<!--绑定手机　s-->
  <div class="bound_cont">
    <div><img src=${imagesPath + "/qiandaohu.gif"} width="669" height="25" /></div>
    <div class="bound_info">
      <p>亲爱的${users.userName}，您需要绑定手机、邮箱后领取千岛湖森林氧吧门票。</p>
      <p>若您之前已绑定其中一个，请完善另一个。</p>
      <div>
        <div class="bound_photo">
        <@s.if test="users != null">
        	<input type="hidden" id="userId" value="<@s.property value="users.id"/>"/>
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
          <img src=${imagesPath + "/bound_pic1.gif"} width="189" height="35" onclick="prompt();" style="cursor:pointer"/></div>
      </div>
    </div>
  </div>
  <div><img src=${imagesPath + "/card_bottom.gif"} width="980" height="13" /></div>

  <!--绑定手机　e-->
</div><div id="msgDiv">
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
</div>




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
        alert("手机、邮箱必须同时绑定，" + msg + "。");
    }
</script>
</body>
</html>