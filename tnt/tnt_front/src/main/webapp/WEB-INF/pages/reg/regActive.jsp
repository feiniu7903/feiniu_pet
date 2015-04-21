<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户激活-驴妈妈分销平台</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/tip.css"
	rel="stylesheet" type="text/css">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/button.css"
	rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/pages/common/head.jsp"></jsp:include>
	<div class="flowstep_wrap">
		<div style="display: none" id="filterPage"></div>
		<div class="flowstep step03"></div>
		<div class="tipbox tip-success tip-nowrap">
			<span class="tip-icon-big tip-icon-big-success"></span>
			<div class="tip-content">
				<h3 class="tip-title">恭喜您，注册资料审核成功。请您点击以下按钮激活账号。</h3>
				<p class="tip-explain">
					您的邮箱：${email } <a href="#" onclick="goAcative();return false;"
						class="btn cbtn-blue btn-small ">立即激活</a>
				</p>
			</div>
		</div>
	</div>
	<!--login_form end-->
	<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
	function goAcative() {
		$
				.ajax({
					type : "get",
					url : "/ajax/sendAuthenticationCode?validateType=EMAIL_AUTHENTICATE&userId=${userId }&email=${email}",
					dataType : "json",
					success : function(response) {
						if (response.success == true) {
							location.href = response.result;
						} else {
							alert(response.errorText);
						}
					}
				});
	}
</script>
</html>