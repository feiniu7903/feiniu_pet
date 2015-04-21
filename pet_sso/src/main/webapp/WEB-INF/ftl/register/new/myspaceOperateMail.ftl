<!DOCTYPE html>
<!--[if lt IE 7 ]><html class="ie ie6"><![endif]-->
<!--[if IE 7 ]><html class="ie ie7"><![endif]-->
<!--[if IE 8 ]><html class="ie ie8"><![endif]-->
<!--[if IE 9 ]><html class="ie ie9"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--><html><!--<![endif]--><head>
<meta charset="utf-8">
<title>验证成功-我的驴妈妈</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="icon" href="/favicon/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="/favicon/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<link href="http://pic.lvmama.com/styles/mylvmama/ui-lvmama.css" rel="stylesheet" />
</head>
<body>
<div id="wrap" class="ui-container ver-success">
<div class="hh_header clearfix">
	<span class="hh_hotline">1010-6060</span>
	<a class="hh_logo" href="http://www.lvmama.com/">驴妈妈旅游网:中国自助游领军品牌</a>
</div>


<#if activeMailType == 'EMAIL_AUTHENTICATE'>
<div class="wrap ui-content">
    <!-- 绑定成功>> -->
    <div class="set-step set-step3 no-bd clearfix fr clr">
        <ul class="hor">
        <li class="s-step1"><span class="s-num">1</span>输入邮箱地址</li>
        <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>验证邮箱</li>
        <li class="s-step3"><i class="s-arrow"></i><span class="s-num">3</span>邮箱验证成功</li></ul>
    </div>
    <div class="msg-success"><span class="msg-ico01"></span>
        <h3>恭喜！邮箱验证成功！<@s.if test='firstCheck'>您获得了<span class="lv-c1">300</span>积分。</@s.if></h3>
        <p>今后，你可以使用邮箱地址<span class="lv-c1"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(email)" /></span>登录驴妈妈旅游网。</p>
        <p class="mt10">您现在可以：<a href="http://www.lvmama.com/myspace/userinfo.do">返回个人资料</a>　　<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a></p>
    </div>
    <div class="hr_a"></div>
    <!-- <<绑定成功 -->
</div>
<#elseif activeMailType == 'EMAIL_BIND'>
<div class="wrap ui-content">
    <!-- 验证成功>> -->
    <div class="set-step set-step3 no-bd clearfix fr clr">
        <ul class="hor">
        <li class="s-step1"><span class="s-num">1</span>输入邮箱地址</li>
        <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>验证邮箱</li>
        <li class="s-step3"><i class="s-arrow"></i><span class="s-num">3</span>邮箱绑定成功</li></ul>
    </div>
    <div class="msg-success"><span class="msg-ico01"></span>
        <h3>恭喜！邮箱绑定成功！<@s.if test='firstCheck'>您获得了<span class="lv-c1">300</span>积分。</@s.if></h3>
        <p>今后，你可以使用邮箱地址<span class="lv-c1"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(email)" /></span>登录驴妈妈旅游网。</p>
        <p class="mt10">您现在可以：<a href="http://www.lvmama.com/myspace/userinfo.do">返回个人资料</a>　　<a href="http://www.lvmama.com/myspace/account/store.do">返回存款账户</a></p>
    </div>
    <div class="hr_a"></div>
    <!-- <<绑定成功 -->
</div>
<#elseif activeMailType == 'EMAIL_MODIFY_AUTHENTICATE'>
<div class="wrap ui-content">
    <!-- 修改成功>> -->
    <div class="set-step set-step3 no-bd clearfix fr clr">
        <ul class="hor">
        <li class="s-step1"><span class="s-num">1</span>输入邮箱地址</li>
        <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>验证邮箱</li>
        <li class="s-step3"><i class="s-arrow"></i><span class="s-num">3</span>邮箱修改成功</li></ul>
    </div>
    <div class="msg-success"><span class="msg-ico01"></span>
        <h3>恭喜！邮箱修改成功。</h3>
        <p>今后，你可以使用邮箱地址<span class="lv-c1"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(email)" /></span>登录驴妈妈旅游网。</p>
        <p class="mt10">您现在可以：<a href="http://www.lvmama.com/myspace/userinfo.do">返回个人资料</a>　　<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a></p>
    </div>
    <div class="hr_a"></div>
    <!-- <<修改成功 -->
</div>
<#elseif activeMailType == 'EMAIL_DELETE_AUTHENTICATE'>
<div class="wrap ui-content">
    <!-- 解绑成功>> -->
    <div class="msg-success"><span class="msg-ico01"></span>
        <h3>恭喜！您的邮箱 <@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(email)" /> 解绑成功！</h3>
        <p class="mt10">您现在可以：<a href="http://www.lvmama.com/myspace/userinfo.do">返回个人资料</a>　　<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a></p>
    </div>
    <div class="hr_a"></div>
    <!-- <<修改成功 -->
</div>
</#if>

<script src="http://pic.lvmama.com/js/common/copyright.js"></script>
</div>
<script src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>