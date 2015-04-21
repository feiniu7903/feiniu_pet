<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驴妈妈登录 绑定账号</title>
<link href="http://pic.lvmama.com/styles/login/Login_20100420.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
</head>
<body onload="$('#mobileOrEMail').focus()">
<div class="c_MainContainer">
	<div class="c_eHeader"><a href="http://www.lvmama.com/" class="aNone_sty"><img src="http://pic.lvmama.com/img/lvmamalogo.gif" alt="驴妈妈网旅游网" /></a><img src="http://login.lvmama.com/nsso/images/login_tel.gif" alt="订购热线 8:00-20:00　1010-6060" class="c_tel" /><span>如果您还没注册请 <a id="reg" href="http://login.lvmama.com/nsso/register/registering.do">注册</a></span></div>

    <!--Step start-->
    <dl class="c_Steps">
    	<dt id="Steps_Title_4"><strong>网站登录</strong></dt>
        <dd id="Steps_Content_4">
			<p class="tit-p tit-span1">没有驴妈妈账号？请点击下面按钮直接登录</p>
			<p class="tit-p p-margin"><input type="checkbox" name="txt-notice" class="txt-notice" checked="checked" />同意《<a class="txt-notice-a">驴妈妈旅游网会员服务条款</a>》</p>
			<p class="tit-p"><a class="a-login" href="javascript:checkNotice();"><img src="http://pic.lvmama.com/img/icons/btn-login.gif" alt="登录驴妈妈" /></a></p>
			<p class="noimg"></p><br /><br />
			<p class="tit-p tit-span1">已有驴妈妈账号，请登录账号进行绑定</p>
			<ul class="user">
			<form action="/nsso/union/binding.do" method="post" id="myForm" name="myForm">
				<input type="hidden" name="cooperationName" value="<@s.property value="cooperationName"/>">
				<input type="hidden" name="cooperationUserAccount" value="<@s.property value="cooperationUserAccount"/>">
				<li><label>用户名/手机号/Email：</label></li>
				<li><input name="mobileOrEMail" value="" id="sso_mobileAndEmail" type="text" /></li>
				<li><span style="display:none;color:red;" id="sso_mobileAndEmail_errorText"  class="new_error"></span></li>
				<li class="pwd-info"><label>密码：</label></li>
				<li><input name="password" id="sso_password" type="password" /></li>
				<li><span style="display:none;color:red;" id="sso_password_errorText"  class="new_error"></span></li>
				<li><font color='red'><@s.actionerror/></font></li>
			</from>
			</ul>
			<p class="tit-p"><a class="a-login"><img src="http://pic.lvmama.com/img/icons/btn-login2.gif" alt="登录并绑定账号" class="a-login-img" onClick="binding()"/></a></p>
		</dd>
    </dl>
	<div class="c_eFoot">Copyright &copy; 2010 www.lvmama.com. 景域旅游运营集团版权所有<br />沪ICP备07509677 </div>

</div>
</body>
<script type="text/javascript">
	function binding() {
		var mobileOrEMail=$('#sso_mobileAndEmail').val();		
		if(mobileOrEMail==''){
			showContent("sso_mobileAndEmail_errorText","<font color='red'>&nbsp;&nbsp;&nbsp;*用户名不能为空</font>");	
			return;
		};

		var password=$('#sso_password').val();
		if(password==''){
			showContent("sso_password_errorText","<font color='red'>&nbsp;&nbsp;&nbsp;*密码不能为空</font>");	
			return;
		};
		document.myForm.submit();
		
	}

	function showContent(name, content) {
		if ($('#' + name).length > 0) {
			$('#' + name).html(content);
			$('#' + name).show();
		} 
	}
	function checkNotice(){
		var notice=$("input:checkbox[name=txt-notice]").attr("checked");
		if(null!=notice&&notice){
			window.location.href="/nsso/union/bindingRegister.do?cooperationName=<@s.property value="cooperationName"/>&cooperationUserAccount=<@s.property value="cooperationUserAccount"/>";
		}else{
			alert("请同意《驴妈妈旅游网会员服务条款》");
		}
	}
</script>
</html>
