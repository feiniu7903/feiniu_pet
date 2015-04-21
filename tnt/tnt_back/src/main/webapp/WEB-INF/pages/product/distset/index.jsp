<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分销设置</title>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
<link href='<c:url value="/css/ui-panel.css" />' rel="stylesheet" />
</head>
<body class="pg_body">
	<!-- 顶部导航\\ -->
	<div class="pg_topbar">
		<h1 class="pg_title">产品分销设置&#160;&#160;产品名称：
			${tntProdProduct.productName}&#160;&#160;类别名称：${tntProdProduct.branchName}</h1>
	</div>
	<!-- 边栏\\ -->
	<div class="pg_aside">
		<div class="aside_box">
			<h2 class="f16">分销设置</h2>
			<ul class="pg_list J_list">
				<li class="active"><a target="iframeMain"
					href="channel/${tntProdProduct.branchId }">渠道类型设置</a></li>
				<li><a target="iframeMain"
					href="user?branchId=${tntProdProduct.branchId }">渠道分销商设置</a></li>
			</ul>
		</div>
	</div>
	<!-- //工作区 -->
	<div class="pg_main">
		<iframe id="iframeMain" name="iframeMain"
			src="channel/${tntProdProduct.branchId }" frameborder="0"
			style="height: 100%; background: #fff"></iframe>
	</div>
	<script type="text/javascript">
		$(function() {
			var $LI = $(".J_list").find("li"), $IFRAME = $("#iframeMain");
			$LI.click(function() {
				var url = $(this).find("a").attr("href");
				$LI.removeClass("active");
				$(this).addClass("active");
				$IFRAME.attr("src", url);
			});
		});
	</script>
</body>
</html>
