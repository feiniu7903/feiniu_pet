<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="page" id="testAA" content="regWaiting" />
<title>客服审核-驴妈妈分销平台</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/tip.css"
	rel="stylesheet" type="text/css">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/button.css"
	rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<jsp:include page="/WEB-INF/pages/common/head.jsp"></jsp:include>
	<div class="flowstep_wrap">
		<div style="display: none" id="filterPage"></div>
		<div class="flowstep step02"></div>
		<div class="tipbox tip-success tip-nowrap">
			<span class="tip-icon-big tip-icon-big-success"></span>
			<div class="tip-content">
				<h3 class="tip-title">恭喜您，您已成功提交基本信息，请等待客服审核。</h3>
				<p class="tip-explain">客服会在7个工作日内完成审核，请耐心等待。</p>
			</div>
		</div>
	</div>
	<!--login_form end-->
	<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
</body>
</html>