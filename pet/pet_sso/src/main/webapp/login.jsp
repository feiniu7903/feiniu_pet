<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>驴妈妈登录</title>
	<link href="http://pic.lvmama.com/styles/new_v/header.css" rel="stylesheet" type="text/css" />
	<link href="http://pic.lvmama.com/styles/new_v/global.css" rel="stylesheet" type="text/css" />
	<link href="http://pic.lvmama.com/min/index.php?g=commonCss" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_login/l_login.css"/>
	
	<%@ include file="/common/coremetricsHead.jsp"%> 
</head>
<body>
	<div class="login_main" id="login_main">
		<div class="login_top">
			<span class="login_logo">
				<a href="http://www.lvmama.com"><img src="http://pic.lvmama.com/img/new_v/ob_login/login_logo.jpg"/></a> <label class="text">|</label> <a class="text">用户登录</a>
			</span>
			<span class="login_hotline">1010-6060</span>
		</div>
			<div class="login_input_main">
				<div class="login_input_border">
					<ul class="login_input_ul">
					<form method="post" id="loginform" name="login_form">
						<li class="login_input_title">
							<h2>登录驴妈妈</h2>
							<span class="login_error_tip" id="login_error_tip"><i class="login_sp login_v_error"></i>请输入邮箱/手机号/用户名/会员卡</span>
						</li>
						<li>
							<input type="text" class="login_text" name="username" value="${userName}" id="loginName" autocomplete="off"/><i class="login_sp login_v_pass"></i>
							<p class="login_input_info">邮箱/手机号/用户名/会员卡</p>
						</li>
						<li>
							<input type="password" class="login_text" name="password" id="password" type="password" autocomplete="off"/><i class="login_sp login_v_pass"></i>
							<p class="login_input_info">请输入密码</p>
						</li>
						<%
						if((Boolean)request.getAttribute("needCheckVerifyCode")){
						%>
						<li class="login_two_mm"><input type="text" class="zhfs_form_input" style="width:110px;margin-right:10px;" id="sso_verifycode1" name="verifycode"><i class="login_sp login_v_pass"></i>
                            <img id="image" src="http://login.lvmama.com/nsso/account/checkcode.htm" /><a href="#" class="link_blue" onClick="refreshCheckCode('image');return false;">换一张</a>
                            <p class="login_input_info">请输入验证码</p>
						</li>
						<%}%>
						<li class="login_input_submit">
						<a href="javascript:void(0)" class="login_sp login_submit" id="loginBtn"></a>
						<a href="/nsso/findpass/index.do" style="position: relative; left: 13px; top: -10px;" class="link_blue">忘记密码？</a>
						</li>
						<input type="hidden" name="lt" value="<%= request.getAttribute("edu.yale.its.tp.cas.lt") %>" />
                        <input type="hidden" id="serviceUrl" value="<%= request.getParameter("service") %>"/>
					 </form>
					</ul>
					<div class="login_input_hr"></div>
					<p class="login_input_bottom login_input_bottom_1">还不是驴妈妈会员？<a href="/nsso/register/registering.do" class="link_blue login_mfzc">免费注册</a></p>
					<p class="login_input_bottom login_input_bottom_2">使用合作网站账号登录
						<span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/tencentQQUnionLogin.do')"><i class="login_sp login_qq"></i>QQ</a></span>
						<!-- <span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/tencentUnionLogin.do')"><i class="login_sp login_tx"></i>腾讯微博</a></span> -->
						<span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/baiduTuanUnionLogin.do')"><i class="login_sp login_bd"></i>百度</a></span>
					</p>
					<p class="login_input_bottom login_input_bottom_3">
						<span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/sinaUnionLogin.do')"><i class="login_sp login_wb"></i>新浪微博</a></span>
						<span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/alipayUnionLogin.do')"><i class="login_sp login_zfb"></i>支付宝</a></span>
						<span class="login_other"><a class="link_blue" href="http://www.kaixin001.com/login/connect.php?appkey=85704812783077bafc036569af59c655&amp;re=/nsso/cooperation/kaixinUnionLogin.do&amp;t=25" target="_blank"><i class="login_sp login_kx"></i>开心网</a></span>
						<span class="login_other"><a href="javascript:void(0)" class="link_blue" onClick="union_login('/nsso/cooperation/sndaUnionLogin.do')"><i class="login_sp login_sd"></i>盛大</a></span>
					</p>
					<div class="login_autoComplete" id="login_autoComplete">
						<ul class="login_auto_ul">
							<li class="login_auto_title">请选择邮箱后缀</li>
							<li class="hover" hz="@qq.com"></li>
							<li hz="@163.com"></li>
							<li hz="@126.com"></li>
							<li hz="@yahoo.com"></li>
							<li hz="@sina.com"></li>
							<li hz="@21cn.com"></li>
						</ul>
					</div>
				</div>
			</div>
		<div class="reg_clear"></div>
		<div class="login_bg_change">
			<a href="javascript:void(0)" class="login_sp login_bg_prev">上一个</a> <label class="login_bg_tip"><i>1</i>/3</label> <a href="javascript:void(0)" class="login_sp login_bg_next">下一个</a>
		</div>
		<div id="allIframe">
<div class="slideimg">
<!--AdForward03 Begin:-->
<script type="text/javascript" src="http://lvmamim.allyes.com/main/s?user=lvmama_2014|otherpage_login|otherpage_login_2014_bg03&db=lvmamim&border=0&local=yes&js=ie" charset="gbk"></script>
<!--AdForward End-->
</div>

<div class="slideimg">
<!--AdForward02 Begin:-->
 <script type="text/javascript"src="http://lvmamim.allyes.com/main/s?user=lvmama_2014|otherpage_login|otherpage_login_2014_bg02&db=lvmamim&border=0&local=yes&js=ie" charset="gbk"></script>
 <!--AdForward End-->
</div>

<div class="slideimg">
<!--AdForward01 Begin:-->
<script type="text/javascript" src="http://lvmamim.allyes.com/main/s?user=lvmama_2014|otherpage_login|otherpage_login_2014_bg01&db=lvmamim&border=0&local=yes&js=ie" charset="gbk"></script>
<!--AdForward End-->
</div>
		</div>
	<%@ include file="/common/footer.jsp" %>
	</div>
	
	<script>
      cmCreatePageviewTag("网站登录", "F0001", null, null);
</script>
</body>
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
<script src="http://pic.lvmama.com/js/new_v/ob_login/l_login.js"></script>
<script src='/nsso/js/member/common.js' type='text/javascript'></script>
<script src='http://pic.lvmama.com/js/news_login/jiaodian.js' type='text/javascript'></script>
<script type="text/javascript">
        document.domain='lvmama.com';
        function union_login(url){ 
            window.open(url); 
        }
</script>

<script type="text/javascript">
		function refreshCheckCode(s) {
		    var elt = document.getElementById(s);
		    elt.src = elt.src + "?_=" + (new Date).getTime();
		}
		<% if (request.getAttribute("edu.yale.its.tp.cas.loginCount2many")!= null) { %>
            error_tip("#password","登录失败次数过于频繁，请稍后重试。");
        <% } %>
        <% if (request.getAttribute("edu.yale.its.tp.cas.badUsernameOrPassword")!= null) { %>
            error_tip("#password","用户名或密码错误，或者您<a href='http://login.lvmama.com/nsso/findpass/index.do'>忘记了密码？</a>");
        <% } %>
        
        <% if (request.getAttribute("edu.yale.its.tp.cas.badVerifyCode")!= null) { %>
             error_tip("#password","验证码输入错误");
        <% } %>
	
		function loginSubmit(){
			document.getElementById("loginform").submit();
		}
</script>
<script src="http://pic.lvmama.com/js/common/losc.js" language="javascript"></script>
</html>