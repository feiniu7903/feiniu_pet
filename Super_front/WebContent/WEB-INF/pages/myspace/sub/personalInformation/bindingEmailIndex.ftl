<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>绑定邮箱-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-userinfo">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/userinfo.do">我的信息</a>
				&gt;
				<a class="current">绑定邮箱</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit email-edit">
					<div class="ui-box-title"><h3>绑定邮箱</h3></div>
					<div class="ui-box-container">
						<div class="edit-box clearfix email-edit-box">
    						<p class="tips-info"><span class="tips-ico03"></span>绑定邮箱后，可以用邮箱地址来登录。<@s.if test='!"F".equals(user.isEmailChecked)'>绑定邮箱并验证成功可获得<span class="lv-c1">300</span>积分。</@s.if></p>
							<div class="set-step set-step1 clearfix">
        						<ul class="hor">
							    <li class="s-step1"><span class="s-num">1</span>输入邮箱地址</li>
							    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>验证邮箱</li>
							    <li class="s-step3"><i class="s-arrow"></i><span class="s-num">3</span>邮箱绑定成功</li>
								</ul>
							</div>
        					<div class="edit-inbox">
							    <form id="myForm" action="/myspace/userinfo/email_send.do" method="post">
								    <p><label>请输入您的邮箱地址：</label>
								    <input type="text" name="email" id="sso_email" value="<@s.property value="user.email" />" class="input-text input-email" />
								    </p>
								    <p><label>请输入验证码：</label>
								    <input type="text" id="sso_verifycode1" name="authenticationCode" value="" class="input-text i-checkcode" />
								    <img src="/account/checkcode.htm" width="65" height="26" id="image">
								    <a href="" class="email-checkcode" onClick="refreshCheckCode('image');return false;">换一张</a></p>
								    <p><a id="submitBtn" class="ui-btn ui-btn4"><i>&nbsp;下一步&nbsp;</i></a></p>
							    </form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
<script type="text/javascript">
	function sso_email_callback(call){
		if($.trim($("#sso_email").val()) == ""){
			error_tip('#sso_email','Email地址格式不正确，请重新输入');
		}else{
			$.getJSON("http://login.lvmama.com/nsso/ajax/checkUniqueField.do?email=" + $.trim($("#sso_email").val()) + "&jsoncallback=?",function(json){
				if (json.success == true) {
					call();
				}else{
					error_tip("#sso_email","该Email地址已被使用，请更换其他邮箱");
				}
			});
		}
	}
	
	/* 更新验证码 */
	function refreshCheckCode(id){
		document.getElementById(id).src = "/account/checkcode.htm?now=" + new Date();
	}
	
	function sso_verifycode1_callback(call){
		$.getJSON("/check/yanzhengma.do?authenticationCode=" + $('#sso_verifycode1').val(),function(json){
			if (json) {
				call();	
			} else {
				error_tip('#sso_verifycode1','验证码出错!',":last");
				refreshCheckCode('image');
			}	
		});
	}
	function validate_pass(){
		$.getJSON("http://login.lvmama.com/nsso/ajax/checkUniqueField.do?email=" + $.trim($("#sso_email").val()) + "&jsoncallback=?",function(json){
			if (json.success == true) {
				$('#myForm').submit();	
			}else{
				error_tip("#sso_email","该Email地址已被使用，请更换其他邮箱");
			}
		});
	}
</script>
	<script>
		cmCreatePageviewTag("绑定邮箱", "D1003", null, null);
	</script>
</body>
</html>