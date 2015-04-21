<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>手机注册+填写短信检验码</title>

<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_login/l_login.css"/>
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/ob_login/l_register.js"></script>
<#include "/common/coremetricsHead.ftl">
</head>
<body>


<div id="login_main" class="login_main">
		<div class="login_top zhfs_logo_top">
			<span class="login_logo">
				<a href="http://www.lvmama.com"><img src="http://pic.lvmama.com/img/new_v/ob_login/login_logo.jpg"></a> <label class="text">|</label> <a class="text">免费注册</a>
			</span>
			<span class="login_hotline">1010-6060</span>
		</div>
		<div class="register_center">
			<h2 class="register_h2_title">会员卡注册</h2>
			<ul class="register_step">
				<li><i>1</i><label class="reg_step_text">填写注册信息</label><em class="zhfs_jiao"></em></li>
				<li class="curr"><i>2</i><label class="reg_step_text">填写短信校验码</label><em class="zhfs_jiao"></em></li>
				<li><i>3</i><label class="reg_step_text">注册成功</label></li>
			</ul>
			<div class="register_allStep">
				<div class="register_stepWidth" id="register_stepWidth">
                                       <form action="/nsso/register/verifyCode.do" id="yzmPostForm" method="post">
                                       <@s.token></@s.token>
	                               <input name="mobile" type="hidden" value="<@s.property value='mobile' />"/>
	                               <input name="mobileNumber" type="hidden" id="mobileNumber"  value="<@s.property value='mobile' />"/>
	                               <input name="membershipCard" type="hidden" value="<@s.property value='membershipCard' />"/>
	                               <input type="hidden" name="channel" value="PAGEREG"/>
					<ul class="register_form" id="register_form_step2">
						<li><span class="zhfs_state zhfs_v_info zhfs_v_widthauto"><i></i>您好，我们已向您的手机发送了免费的短信校验码，请查看你的手机。</span></li>
						<li><label class="csmm_form_col w90">您的手机号</label><label class="register_sjtext"><@s.property value='mobile' /></label></li>
						<li>
							<label class="csmm_form_col w90"> 短信校验码</label><input type="text" id="sso_verifycode" name="authenticationCode" class="zhfs_form_input w_xym">
							<div class="register_yzm">
								<label class="csmm_form_col w83"></label>
								<span id="sendCodeBtn" class="zhfs_info_s marginclear" style="display:none;">重新发送校验码</span>
								<span id="yzm_send_ok">
								    <#assign local_errorMessages><@s.property value="#request.errorMessages"/></#assign>
			                         <span class="zhfs_success_s marginclear" style="display:none"><i></i>校验码已经发送成功，请查看手机</span>
									<span class="zhfs_info_send">60秒内没有收到短信？<a id="zhfs_send_again" class="zhfs_send_again" href="javascript:void(0)">(<label>60</label>)秒后再次发送</a></span>
								</span>
							</div>
						</li>
						<li><label class="csmm_form_col w90"></label><a id="register_sj_submit" class="zhfs_sj_submit mtopclear" href="javascript:void(0)"></a></li>
					</ul>
                                       </form>
				</div>
			</div>
			<div class="register_right register_right_hyk">
				<p class="register_r_b">返回 <a href="javascript:void(0)" class="link_blue">邮箱注册</a></p>
				<p class="register_r_b rrb_padding rrb_fsize"><i class="reg_sb register_lw"></i>现在注册即可获得<i class="organge">100积分</i></p>
				<p class="register_r_b rrb_padding">已经有驴妈妈账号？<a href="http://login.lvmama.com/nsso/login" class="reg_sb register_login"></a></p>
				<p class="register_r_b rrb_padding register_lh">
					<label class="gray">使用合作网站账号登录</label><br/>
					    <span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/tencentQQUnionLogin.do')"><i class="login_sp login_qq"></i>QQ</a></span>
						<!--<span class="login_other"><i class="login_sp login_tx"></i><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/tencentUnionLogin.do')">腾讯微博</a></span>-->
						<span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/sinaUnionLogin.do')"><i class="login_sp login_wb"></i>新浪微博</a></span>
						<span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/alipayUnionLogin.do')"><i class="login_sp login_zfb"></i>支付宝</a></span>
						<span class="login_other"><a class="link_blue" href="http://www.kaixin001.com/login/connect.php?appkey=85704812783077bafc036569af59c655&amp;re=/nsso/cooperation/kaixinUnionLogin.do&amp;t=25" target="_blank"><i class="login_sp login_kx"></i>开心网</a></span>
						<span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/sndaUnionLogin.do')"><i class="login_sp login_sd"></i>盛大</a></span>
				</p>
				<img src="http://pic.lvmama.com/img/new_v/ob_login/demo_right_gg.jpg"/>
			</div>
			<div class="reg_clear"></div>
		</div>
	</div>
<!-- 尾部 -->
<#include "/WEB-INF/ftl/comm/footer.ftl"/>
<!-- 尾部 -->
<script>
      cmCreatePageviewTag("会员卡注册短信校验", "F0001", null, null);
</script>
</body>	
	<script src="/nsso/js/common/closeF5MouseRight.js" type="text/javascript"></script>
	<script type="text/javascript">
        document.domain='lvmama.com';
        function union_login(url){ 
            window.open(url); 
        }
</script>
	<script>

		$(function(){
			hyk_step2();
			yzm_send_ok(true); //点击重新发送效验码后的效果
			<#if local_errorMessages!='[]'>
				error_tip("#sso_verifycode","校验码填写错误，请重新输入");
			</#if>
		});
		//完成页面验证后，会执行此方法
		function validate_pass(){
		    $("#yzmPostForm").submit();
		}

         <!--“发送校验码”按钮事件-->
          $("#sendCodeBtn").click(function () {  
                sendAuthenticationCode();
         });

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
						yzm_send_ok();
					} else {
						alert('验证码发送失败，请重新尝试');
					}
				}
			});		
		}

	</script>
<#include "/WEB-INF/ftl/common/mvHost.ftl"/>
</html>