<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>帮助中心-驴妈妈分销平台</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet"
	href="http://pic.lvmama.com/min/index.php?f=/styles/fx/b2c_front/header-air.css,/styles/fx/b2c_front/core.css,/styles/fx/b2c_front/module.css,/styles/fx/b2c_front/ui-search.css,/styles/fx/b2c_front/ui-components.css,/styles/fx/b2c_front/calendar.css,/styles/fx/b2c_front/tip.css,/styles/fx/b2c_front/b2b_fx2.css">

<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/pages/common/head.jsp"></jsp:include>
	<div class="fx_front_nav">
		<ul class="fx_front_navList">
			<li><a href="/index" title="景点门票">景点门票</a></li>
			<li class="nav_li"><a href="/help/index" title="帮助中心">帮助中心</a></li>
		</ul>
	</div>
	<div class="fx_in">
		<jsp:include page="/WEB-INF/pages/helpcenter/left_aside.jsp"></jsp:include>
		<div class="main_r"></div>
	</div>
	<!--fx_in end-->

	<!-- 尾部 -->
	<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
</body>
</html>