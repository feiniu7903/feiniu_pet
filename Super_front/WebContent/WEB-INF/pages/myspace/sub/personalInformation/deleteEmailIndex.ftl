<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>解绑邮箱-驴妈妈旅游网</title>
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
				<a class="current">解绑邮箱</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit email-edit">
					<div class="ui-box-title"><h3>解绑邮箱</h3></div>
					<div class="ui-box-container">
    					<div class="edit-box clearfix email-edit-box">
							<p class="tips-info"><span class="tips-ico03"></span>解绑邮箱后，邮箱地址将不能用来登录。</p>

							<div class="set-step set-step1 clearfix">
        						<ul class="hor">
							    <li class="s-step1"><span class="s-num">1</span>验证邮箱</li>
							    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>邮箱解绑成功</li>
								</ul>
							</div>

							<div class="edit-inbox">
  									<form id="myForm" action="/myspace/userinfo/email_delete.do" method="post">
									<p><label>邮箱地址：</label><span class="u-info-big"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(user.email)" /></span>
									&nbsp;&nbsp;&nbsp;<a href="/myspace/userinfo/contactCustomService.do" target="_blank">邮箱地址已不再使用？</a></p>
									<p><label>请输入验证码：</label><input type="text"  id="sso_verifycode1" name="authenticationCode" value="" class="input-text i-checkcode" />
									<a href="" class="email-checkcode" onClick="refreshCheckCode('image');return false;">
										<img src="/account/checkcode.htm" width="65" height="26" id="image">
									</a>
									<a href="" class="email-checkcode" onClick="refreshCheckCode('image');return false;">换一张</a></p>
									<p><a href="javascript:void(0)" class="ui-btn ui-btn1" id="submitBtn"><i>发送验证邮件</i></a></p>
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
	var oldEmail = "${Request.oldEmail}";

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
		$("#myForm").submit();
	}
</script>
	<script>
		cmCreatePageviewTag("解绑邮箱", "D1003", null, null);
	</script>
</body>
</html>