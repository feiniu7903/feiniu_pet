<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>手机注册+填写短信检验码</title>

<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<link href="http://pic.lvmama.com/styles/mylvmama/ui-lvmama.css" rel="stylesheet" />
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
			<ul class="regc_zc_title">
				<li class="menu menu_yx" onclick="location.href='http://login.lvmama.com/nsso/register/registering.do'">邮箱注册</li>
				<li class="menu menu_sj curr" onclick="location.href='http://login.lvmama.com/nsso/register/mobileregist.do'">手机注册</li>
				<li class="register_topfont"><a href="http://login.lvmama.com/nsso/register/membercardregist.do" class="link_blue">会员卡注册</a> 成为驴妈妈会员</li>
			</ul>
			<div class="reg_zc_line"></div>
			<div class="w_overflow" id="w_overflow">
				<div class="w_max" id="w_max">
				
					<div class="fl" id="sj_div">
						<ul class="reg_diandian" id="sj_reg_diandian">
							<li class="login_sp"></li>
							<li class="login_sp curr"></li>
							<li class="login_sp"></li>
						</ul>
						<div class="reg_sjyx_allStep">
							<div class="register_stepWidth" id="register_stepWidth">
								<ul class="register_form register_sj_form">
                                    <form action="/nsso/register/verifyCode.do" id="yzmPostForm" method="post">
                                        <@s.token></@s.token>
                                        <input name="mobile" type="hidden" value="<@s.property value='mobile' />"/>
                                        <input name="mobileNumber" type="hidden" id="mobileNumber"  value="<@s.property value='mobile' />"/>
                                        <input name="membershipCard" type="hidden" value="<@s.property value='membershipCard' />"/>
                                        <input type="hidden" name="channel" value="PAGEREG"/>
										<li><label class="csmm_form_col w90">您的手机号</label><label class="register_sjtext"><@s.property value='mobile' /></label></li>
										<li>
											<label class="csmm_form_col w90">短信校验码</label>
											<input type="text" id="sso_verifycode" name="authenticationCode" class="zhfs_form_input w_xym">
											<div class="register_yzm">
												<span id="sendCodeBtn" class="zhfs_info_s ml100">重新发送校验码</span>
												<span class="tips-warn" style="display:none" id="span_tips"><span class="tips-ico03"></span>已超过每日发送上限，请于次日再试</span></p>
												<div id="yzm_send_ok" style="display:none">
													<#assign local_errorMessages><@s.property value="#request.errorMessages"/></#assign>
					                                <span class="zhfs_success_s marginclear" ><i></i>校验码已经发送成功，请查看手机</span>
												    <span class="zhfs_info_send ml100">60秒内没有收到短信？<a id="zhfs_send_again" class="zhfs_send_again" href="javascript:void(0)">(<label>44</label>)秒后再次发送</a></span>
												</div>
											</div>
									</li>
									<li><label class="csmm_form_col w90"></label><a id="register_sj_submit" class="zhfs_sj_submit mtopclear" href="javascript:void(0)"></a></li>
								</ul>
                        </form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="register_right">
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
      cmCreatePageviewTag("手机注册短信校验", "F0001", null, null);
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
			sj_step2();
			//yzm_send_ok(true); //点击重新发送效验码后的效果
			$("#sendCodeBtn").html('发送校验码');
			<#if local_errorMessages!='[]'>
				error_tip("#sso_verifycode","校验码填写错误，请重新输入");
			</#if>
		});
		//完成页面验证后，会执行此方法
		function validate_pass2(){
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
						$("#ipLimit").hide();
						yzm_send_ok();
					} else {
						if(response.errorText == 'phoneWarning'){
							$("#ipLimit").hide();
							$("#phoneWarning").show();
							$("#sendCodeBtn").unbind();  
						}else if(response.errorText == 'ipLimit'){
							$("#ipLimit").show();
						}else{
							error_tip("#sendCodeBtn",response.errorText);
						} 
					}
				}
			});		
		}

	</script>
<#include "/WEB-INF/ftl/common/mvHost.ftl"/>
</html>