<%@page import="com.lvmama.tnt.user.po.TntUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path;
%>
<script
	src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js"></script>
<div class="header">
	<div class="topNav">
		<div class="topNav_wrap">
			<p class="topNav_l">
				您好，
				<c:if test="${not empty SESSION_TNT_USER}">
					<a href="/user/index.do" title="${SESSION_TNT_USER.realName}">${SESSION_TNT_USER.realName}</a>
					|<a rel="nofollow" href="/logout" class="lv-logout"
						hidefocus="false">[退出]</a>
				</c:if>
				欢迎来到驴妈妈分销平台
				<c:if test="${empty SESSION_TNT_USER}">
					<a href="/login/index" title="登录">登录</a>
					<a href="/reg/index" title="注册">注册</a>
				</c:if>
			<p class="topNav_r">
				<a href="/user/index.do" title="我的驴妈妈">我的驴妈妈</a><a
					href="/help/index" title="帮助中心">帮助中心</a><a href="javascript:"
					id="favButton" rel="sidebar" title="驴妈妈旅游网">收藏</a>
			</p>
		</div>
	</div>
	<div class="top">
		<div class="top_wrap">
			<img src="http://pic.lvmama.com/img/fx/fx_phone.png" class="fx_phone"
				alt="400-6040-616"> <a href="http://f.lvmama.com/"
				class="fx_logo"><img
				src="http://pic.lvmama.com/img/fx/fx_logo.png"></a>
		</div>
	</div>
	<script type="text/javascript">
	$(function(){
		$("#favButton").click(function() {
			var page = "http://www.lvmama.com/";
			var desc = "驴妈妈旅游网";
			try {
				window.external.AddFavorite(page, desc);
			} catch (e) {
				if (window.sidebar) {
					$("#favButton").attr("href", page);
					window.sidebar.addPanel(desc, page, '');
				} else {
					alert('请使用按键 Ctrl+d，收藏' + desc);
				}
			}
		});
		
	});
	$.ajaxSetup({
		cache : false
	});

	</script>
</div>