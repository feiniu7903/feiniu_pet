<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人中心-驴妈妈分销平台</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/tip.css,/styles/v5/modules/button.css,/styles/fx/b2c_front/b2b_fx2.css"
	rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>
	<jsp:include page="/WEB-INF/pages/common/head.jsp"></jsp:include>
	<div class="fx_front_nav">
		<ul class="fx_front_navList">
			<li><a href="/index" title="景点门票">景点门票</a></li>
			<li><a href="/help/index" title="帮助中心">帮助中心</a></li>
		</ul>
	</div>
	<div class="fx_in">
		<jsp:include page="/WEB-INF/pages/userspace/userinfo/left_aside.jsp"></jsp:include>
		<div class="main_r"></div>
	</div>
	<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
</body>

<script
	src="http://pic.lvmama.com/min/index.php?f=/js/v5/modules/pandora-poptip.js,/js/v4/modules/pandora-dialog.js"></script>
<!-- <script src="http://pic.lvmama.com/min/index.php?f=/js/v5/modules/pandora-calendar.js"></script> -->
<script src="/js/pandora-calendar.js"></script>
<script
	src="http://pic.lvmama.com/js/ui/lvmamaUI/plugin/city/json-array-of-city-new.js"></script>
<script
	src="http://pic.lvmama.com/js/ui/lvmamaUI/plugin/city/json-array-of-province.js"></script>
<script src="http://s2.lvjs.com.cn/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="${basePath}/js/jqueryCity.js"></script>
<script src="/js/jquery.form.js"></script>
<script src="/js/ajaxupload.js"></script>
</html>