<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>填写新密码-驴妈妈分销平台</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link href="http://pic.lvmama.com/styles/login/delv_css.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/new_v/header.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/min/index.php?g=commonCss"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_login/l_login.css"/>
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet" type="text/css">
<style type="text/css">
.login_main{width:1000px;}
</style>
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/ob_login/l_mmzh.js"></script>
<script type="text/javascript" src="/js/form.js"></script>
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
</head>
<body>
	<div id="login_main" class="login_main">
		<div class="login_top zhfs_logo_top top_wrap">
			<img  class="fx_phone" src="http://pic.lvmama.com/img/fx/fx_phone.png" />
			<span class="login_logo fx_logo"> <a href="http://f.lvmama.com"><img
					src="http://pic.lvmama.com/img/fx/fx_logo.png"></a> <label
				class="text">|</label> <a class="text">重置密码</a>
			</span>
		</div>
		<div class="zhfs_center">
			<ul class="zhfs_step">
				<li class="curr"><i>3</i><label class="zhfs_step_text">设置新密码</label></li>
				<li><i>2</i><label class="zhfs_step_text">输入注册账号</label><em class="zhfs_jiao"></em></li>
				<li><i>1</i><label class="zhfs_step_text">选择找回密码方式</label><em class="zhfs_jiao"></em></li>
			</ul>
			<form action="/findpass/saveNewPass" method="post"
				id="resetPassForm">
				<c:if test="${mobile!=null }">
					<div class="zhfs_t_left">手机找回密码</div>
				</c:if>
				<c:if test="${email!=null }">
					<div class="zhfs_t_left">邮箱找回密码</div>
				</c:if>
				<ul class="zhfs_form">
					<c:if test="${mobile!=null }">
						<input type="hidden" name="mobile" value="${mobile}" />
						<li class="zhfs_first"><label class="csmm_form_col">您的手机</label><label
							class="csmm_form_value">${mobile}</label></li>
						<input type="hidden" name="authenticationCode"
							value="${authenticationCode}" />
					</c:if>
					<c:if test="${email!=null }">
						<input type="hidden" name="email" value="${email}" />
						<input type="hidden" name="recallId" value="${recallId}" />
						<li class="zhfs_first"><label class="csmm_form_col">您的邮箱</label><label
							class="csmm_form_value">${email}</label></li>
					</c:if>
					<li><label class="csmm_form_col">设置新密码</label><input
						type="password" class="zhfs_form_input" name="password"
						id="sso_password" /></li>
					<li><label class="csmm_form_col">确认新密码</label><input
						type="password" class="zhfs_form_input" name="password2"
						id="sso_againPassword" /></li>
					<li><label class="csmm_form_col"></label><a
						href="javascript:void(0)" class="csmm_sj_submit" id="resetPassBtn"></a></li>
				</ul>
			</form>
		</div>
	</div>
	<!-- 尾部 -->
	<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
</body>
</html>