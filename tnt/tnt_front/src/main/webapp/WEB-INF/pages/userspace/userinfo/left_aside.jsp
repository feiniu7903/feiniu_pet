<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="left_nav2">
	<h4>订单管理</h4>
	<ul>
		<li class="active"><i class="icon-point"></i> <a
			href="/mOrder/index.do">订单管理</a></li>
	</ul>
	<h4>资金管理</h4>
	<ul>
		<li><i class="icon-point"></i><a
			href="/userspace/cashAccount/index.do">预存款账户</a></li>
		<li><i class="icon-point"></i><a href="/user/recognizance.do">保证金账户</a></li>
	</ul>
	<h4>信息管理</h4>
	<ul>
		<li><i class="icon-point"></i><a href="/user/baseInfo.do">基本信息管理</a></li>
		<li><i class="icon-point"></i><a
			href="/user/updatePasswordPage.do?ispay=false">修改密码</a></li>
		<li><i class="icon-point"></i><a href="/meterial/index.do">我的资料</a></li>
		<li><i class="icon-point"></i><a href="/user/pact.do">我的合同</a></li>
	</ul>
</div>
<script type="text/javascript">
	$(function() {
		$(".left_nav2").find("ul>li").click(function() {
			$(".left_nav2").find("ul>li").removeClass("active");
			$(this).addClass("active");
			query($(this).find("a").attr("href"), ".main_r");
			return false;
		});

		query("/mOrder/index.do", ".main_r");
	});
	function query(url, div) {
		$.ajax({
			type : "get",
			url : url,
			success : function(response) {
				//var r = $("<div></div>");
				//r.html(response);
				//var page = r.find("#filterPage").attr("id");
				var page = response.indexOf("id=\"filterPage\"");				
				//if (page && "filterPage" == page) {
				if (page!=-1) {
					top.location.href = "/user/index.do";
				} else {
					$(div).html(response);
				}
			}
		});
	}
</script>