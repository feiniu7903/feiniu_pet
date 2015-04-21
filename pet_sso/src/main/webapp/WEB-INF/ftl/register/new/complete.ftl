<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>驴妈妈-注册成功</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_login/l_login.css"/>
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/top/top_common_noLazy.js"></script>
<script type="text/javascript">
	function resend() {
		$.ajax({
			type: "POST",
			url: "/nsso/ajax/resentRegisterEmail.do",
			async: false,
			data: {
			<#if userId!=null>
				userId: <@s.property value="userId"/>
			</#if>
			},
			dataType: "json",
			success: function(response) {
				if (response.success == false) {
					alert('邮件发送失败，请重新尝试');			
				} else {
				    alert('邮件发送成功');								
				}
			}
		});
	}
	
	function loginEmail(){
		window.open("<@s.property value='userMailHost'/>");
	}
</script>



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

<#include "/common/coremetricsHead.ftl">
</head>

<body>
	<div id="login_main" class="login_main">
		<div class="login_top">
			<span class="login_logo">
				<a href="http://www.lvmama.com"><img src="http://pic.lvmama.com/img/new_v/ob_login/login_logo.jpg"></a> <label class="text">|</label> <a class="text">免费注册</a>
			</span>
			<span class="login_hotline">1010-6060</span>
		</div>
		<div class="zhfs_center">
			<div class="zhfs_re">
				<i class="zhfs_sb zhfs_re_success"></i><strong class="zhfs_re_strong">恭喜，注册成功。您获得了<label class="organge">100积分</label>的奖励。</strong>

<#if sessionRegisterUser.email!=null &&  sessionRegisterUser.mobileNumber==null && sessionRegisterUser.memberShipCard==null>
				<ul class="regc_ul">
					<li><i class="regc_sb reg_dian"></i>您的注册邮箱为: <strong>${sessionRegisterUser.email}</strong></li>
					<li><i class="regc_sb reg_dian"></i>邮箱可用来登录、找回密码、订购产品。</li>
					<li class="regc_yx_text">
						<label>我们已向您的邮箱发送一封验证邮件，验证邮箱成功后将获得300积分奖励。</label><br/>
						<a href="javascript:void(0)" class="zhfs_sb zhfs_yx_lkyz" onclick="loginEmail()"></a>&nbsp;没有收到？<a href="#" class="link_blue" onclick="resend()">再次发送</a>
					</li>
					<li>您现在要去：
						<a href="http://www.lvmama.com" class="link_blue">返回首页</a>
					</li>
				</ul>
<#elseif sessionRegisterUser.mobileNumber!=null && sessionRegisterUser.memberShipCard==null>
				<ul class="regc_ul">
					<li><i class="regc_sb reg_dian"></i>您的注册手机号为: <strong>${sessionRegisterUser.mobileNumber}</strong></li>
					<li><i class="regc_sb reg_dian"></i>手机号可用来登录、找回密码、订购产品。</li>
					<li class="reg_li_middle">您现在想去：
						<a href="http://www.lvmama.com" class="regc_sb regc_sy"></a>
						<a href="http://www.lvmama.com/myspace/index.do" class="regc_sb regc_wdlmm"></a>
						<a href="http://www.lvmama.com/ticket" class="link_blue">打折门票</a>
						<a href="http://www.lvmama.com/freetour" class="link_blue">周边自由行</a>
						<a href="http://www.lvmama.com/around" class="link_blue">周边跟团游</a>
					</li>
					<li><i class="regc_sb reg_dian"></i><label id="lbl_djs">10</label>秒钟后将自动跳转到驴妈妈首页。</li>
					<li><i class="regc_sb reg_dian"></i>如果没有自动跳转，<a href="http://www.lvmama.com" class="link_blue">请点击这里>></a></li>
				</ul>
<#elseif sessionRegisterUser.memberShipCard!=null && sessionRegisterUser.mobileNumber!=null>
				<ul class="regc_ul">
					<li><i class="regc_sb reg_dian"></i>您的会员卡号为: <strong>${sessionRegisterUser.memberShipCard}</strong></li>
					<li><i class="regc_sb reg_dian"></i>您的注册手机号为: <strong>${sessionRegisterUser.mobileNumber}</strong></li>
					<li><i class="regc_sb reg_dian"></i>会员卡/手机号可用来登录、订购产品。</li>
					<li class="reg_li_middle">您现在想去：
						<a href="http://www.lvmama.com" class="regc_sb regc_sy"></a>
						<a href="http://www.lvmama.com/myspace/index.do" class="regc_sb regc_wdlmm"></a>
						<a href="http://www.lvmama.com/ticket" class="link_blue">打折门票</a>
						<a href="http://www.lvmama.com/freetour" class="link_blue">周边自由行</a>
						<a href="http://www.lvmama.com/around" class="link_blue">周边跟团游</a>
					</li>
					<li><i class="regc_sb reg_dian"></i><label id="lbl_djs">10</label>秒钟后将自动跳转到驴妈妈首页。</li>
					<li><i class="regc_sb reg_dian"></i>如果没有自动跳转，<a href="http://www.lvmama.com" class="link_blue">请点击这里>></a></li>
				</ul>
</#if>
			</div>
		</div>
	</div>
<#include "/WEB-INF/ftl/comm/footer.ftl"/>
<!-- 尾部 -->

<script>
      cmCreatePageviewTag("注册成功", "F0001", null, null);
</script>
</body>
<#if sessionRegisterUser.mobileNumber!=null>
<script type="text/javascript">
	$(function(){
		var loop = function(){
			var lbl = $("#lbl_djs");
			var num = parseInt(lbl.html());
			num--;
			if(num<0){
				location.href = "http://www.lvmama.com";
			}else{
				lbl.html(num);
			}
			setTimeout(loop,1000);
		}
		loop();
	})
</script>
</#if>
<script src="http://pic.lvmama.com/js/common/losc.js" language="javascript"></script>
<script>
	cmCreatePageviewTag("注册成功", "F0001", null, null);
	cmCreateRegistrationTag("${sessionRegisterUser.userNo}","null","null","null","null","null","null-_-null-_-null-_-null-_-null");
</script>
</html>