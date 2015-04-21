<!DOCTYPE html>
<html lang="cn">
<head>
	<meta charset="utf-8" />
	<title></title>
	<link href="http://pic.lvmama.com/styles/zt/phb/phb_write_cmt.css?r=3332" rel="stylesheet" type="text/css" />
</head>
<body>
	
  <!--用户登录弹出层-->
  <div class="ban_bg"></div>  
  <div id="log_layer">
  			<dl class="c_Steps">
				<dt id="Steps_Title_4">用户登录</dt>
				<input type="hidden" id="targetUrl" value="../comment/writeComment/fillComment.do?placeId=${placeId}" />
				<dd id="Steps_Content_4">
					<p>您可以使用用户名/手机号/Email/会员卡登录</p>
					<ul>
						<form method="post" id="loginform" name="login_form">
							<li><label>用户名/手机号/Email/会员卡：</label></li>
							<li><input name="username"  class="inputtext"  value="" id="loginName"  type="text"></li>
							<li class="red" id="errorTipName">*请输入用户名、手机、Email、会员卡四者之一登录</li>
							<li><label>密码：</label></li>
							<li><input name="password" class="inputtext" id="password" type="password"></li>
							<li id="errorTipPwd" class="red">*请输入密码</li>
							<li><img src="http://pic.lvmama.com/img/zt/phb/c_login_submit.gif" id="loginBtn"><strong>还没注册？
							<a href="http://login.lvmama.com/nsso/register/registering.do" target="_brank" >点此注册</a></strong></li>
						</form>
				  </ul>
				</dd>
			</dl>
  			<a class="close" id="close"><img src="http://pic.lvmama.com/img/zt/phb/close.gif" height="15" width="15" /></a>
  </div>
		
<script src="http://pic.lvmama.com/js/jquery142.js?r=8420" type="text/javascript"></script> 
<#include "/WEB-INF/comment/jsResource/phb/login_js.ftl" />

</body>
</html>
