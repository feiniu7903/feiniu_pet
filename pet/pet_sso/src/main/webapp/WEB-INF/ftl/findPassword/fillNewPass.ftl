<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>填写新密码-驴妈妈旅游网</title>
<link href="http://pic.lvmama.com/styles/login/delv_css.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/new_v/header.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/min/index.php?g=commonCss"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_login/l_login.css"/>
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<#include "/common/coremetricsHead.ftl">
</head>

<body>
	<div id="login_main" class="login_main">
		<div class="login_top zhfs_logo_top">
			<span class="login_logo">
				<a href="http://www.lvmama.com"><img src="http://pic.lvmama.com/img/new_v/ob_login/login_logo.jpg"></a> <label class="text">|</label> <a class="text">重置密码</a>
			</span>
			<span class="login_hotline">1010-6060</span>
		</div>
		<div class="zhfs_center">
			<ul class="zhfs_step">
				<li class="curr"><i>3</i><label class="zhfs_step_text">设置新密码</label></li>
				<li><i>2</i><label class="zhfs_step_text">输入注册账号</label><em class="zhfs_jiao"></em></li>
				<li><i>1</i><label class="zhfs_step_text">选择找回密码方式</label><em class="zhfs_jiao"></em></li>
			</ul>
                        <form action="/nsso/findpass/saveNewPass.do" method="post" id="resetPassForm">
                        <#if mobile!=null>
                        <div class="zhfs_t_left">手机找回密码</div>
                        <#elseif email!=null>
			<div class="zhfs_t_left">邮箱找回密码</div>
                        </#if>
			<ul class="zhfs_form">
                               <#if mobile!=null>
                               <input type="hidden" name="mobile" value="${mobile}"/>
                               <li class="zhfs_first"><label class="csmm_form_col">您的手机</label><label class="csmm_form_value">${mobile}</label></li>
                               <input type="hidden" name="authenticationCode" value="${authenticationCode}"/>
                               <#elseif email!=null>
                               <input type="hidden" name="email" value="${email}"/>
                               <input type="hidden" name="recallId" value="${recallId}"/>
			       <li class="zhfs_first"><label class="csmm_form_col">您的邮箱</label><label class="csmm_form_value">${email}</label></li>
                               </#if>
				<li><label class="csmm_form_col">设置新密码</label><input type="password" class="zhfs_form_input" name="password" id="sso_password"/></li>
				<li><label class="csmm_form_col">确认新密码</label><input type="password" class="zhfs_form_input" name="password2" id="sso_againPassword"/></li>
				<li><label class="csmm_form_col"></label><a href="javascript:void(0)" class="csmm_sj_submit" id="resetPassBtn" ></a></li>
			</ul>
                       </form>
		</div>
	</div>
<!-- 尾部 -->
<#include "/WEB-INF/ftl/comm/footer.ftl"/>
<!-- 尾部 -->
<script>
      cmCreatePageviewTag("设置新密码", "F0001", null, null);
</script>
</body>
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/ob_login/l_mmzh.js"></script>
<script type="text/javascript" src="/nsso/js/form.js"></script>
<script src='http://pic.lvmama.com/js/news_login/jiaodian.js' type='text/javascript'></script>
<script type="text/javascript">

    function validate_pass(){
     $("#resetPassForm").submit();	
    }

   //控件初始化
   $(function(){
	csmm_init();
   });
</script> 
</html>
