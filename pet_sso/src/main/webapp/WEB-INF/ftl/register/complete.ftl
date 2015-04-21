<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<title>注册成功</title>
		<link href="http://pic.lvmama.com/styles/login/Login_20100420.css" rel="stylesheet" type="text/css" />
		<script src="/nsso/js/common/closeF5MouseRight.js" type="text/javascript"></script>

	</head>
	<body>
	<div class="c_MainContainer">
            <div class="c_eHeader">
		<a href="http://www.lvmama.com/" class="aNone_sty"><img src="http://pic.lvmama.com/img/lvmamalogo.gif" alt="驴妈妈网旅游网" /></a>
		<img src="/nsso/images/login_tel.gif" alt="订购热线 8:00-20:00　1010-6060" class="c_tel" />
		<span>已经有驴妈妈网帐号？ 请<a href="/nsso/login">登录</a></span>
	</div>
	<!--Step start-->
	<@s.property value='@((com.lvmama.comm.pet.po.user.UserUser)com.lvmama.comm.utils.ServletUtil.getSession(com.lvmama.comm.vo.Constant.SESSION_REGISTER_USER)).email' />
	<dl class="c_Steps">
    		<dt id="Steps_Title_3"><strong>3.注册完成</strong></dt>
		<dd id="Steps_Content_3">
			<p style="position:relative;background:none;">
				<strong style="position:absolute;display:block;height:35px;width:35px;left:110px;top:8px;background:url(http://pic.lvmama.com/img/member/c_login-icon_19.gif) no-repeat 0 -210px;"></strong>
				<span><span class="c_Red"><@s.property value='#sessionRegisterUser.userName' /></span>，恭喜您！</span>您已经成为驴妈妈会员！<br />
					以后您可以凭<@s.if test="#sessionRegisterUser.membershipCard!=null">会员卡<span class="c_Red"><@s.property	value='#sessionRegisterUser.membershipCard' /></span>,</@s.if> 
					<@s.if test="#sessionRegisterUser.email!=null">邮箱<span class="c_Red"><@s.property	value='#sessionRegisterUser.email' /></span></@s.if>
					<@s.if test="#sessionRegisterUser.mobileNumber!=null">手机号<span class="c_Red"><@s.property	value='#sessionRegisterUser.mobileNumber' /></span></@s.if>   
					或用户名 <span class="c_Red"><@s.property value='#sessionRegisterUser.userName' /></span> 登录驴妈妈，购买上万种低价旅游产品！
			</p>
			<div><a href="http://www.lvmama.com/?ticket=${token}" class="aNone_sty"><img src="/nsso/images/login-ok_16.gif" /></a></div>
			<br/>
			<div><span class="c_Red">注：</span>驴妈妈会员卡用户请在“<a href="http://www.lvmama.com/myspace/account/coupon.do">我的优惠券</a>”中查收优惠券代码，在填写订单时输入优惠券代码抵扣相应金融。</div>
			<ul>
				<li><em><a href="http://www.lvmama.com/product/ticket">购买</a></em><strong>低价门票:</strong>目前有2000家景点10000种旅游产品可以选择。</li>
				<li><em><a href="http://www.lvmama.com/comment/">发表</a></em><strong>旅游点评:</strong>目前已经有58万条点评，欢迎您来分享。</li>
				<li><em><a href="http://bbs.lvmama.com/">逛逛</a></em><strong>旅游论坛:</strong>人气超旺的旅游论坛，来这里交交爱好旅游的朋友吧。</li>
				<li><em><a href="http://www.lvmama.com/place">看看</a></em><strong>景点大全:</strong>目前已经收录国内10000家景点的资料，包括美食、美图、攻略等。</li>
			</ul>
		</dd>
	 </dl><!--Step end ///////-->
	 <div class="c_eFoot">
		Copyright &copy; 2011 www.lvmama.com. 景域旅游运营集团版权所有
		<br />
		沪ICP备07509677
	 </div>
	
	</body>
<#include "/WEB-INF/ftl/common/mvHost.ftl"/>
</html>



