<%@page import="java.util.Date"%>
<%@page import="com.lvmama.comm.utils.DateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link href="http://pic.lvmama.com/styles/ebooking/base.css" rel="stylesheet">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link href="http://pic.lvmama.com/js/ui/lvmamaUI/css/jquery.common.css" rel="stylesheet">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script>
function loginHandler(){
	if($.trim($("#userName").val()) == ""){
		$("#msgSpan").text("请输入用户名");
		return false;
	}
	if($.trim($("#password").val()) == ""){
		$("#msgSpan").text("请输入密码");
		return false;
	}
	if($.trim($("#validateCode").val()) == ""){
		$("#msgSpan").text("请输入验证码");
		return false;
	}
	$("#loginForm").submit();
}
function changeValidateCodeHandler(){
	$("#validateCodeImg").attr("src","login/validate_code.do?_=" + (new Date).getTime());
	return ;
}
function enterHandler(event){
	if(event.keyCode==13) {
        $("#loginBtn").click();
  }
}
function ancHandler(ancId){
	window.open("${contextPath}/announcement/show_announcement_detail.do?id="+ancId);
}
$(function(){
	$("#userName").focus();
});
</script>
</head>
<body>
	<div class="login_top">
		<div class="login_logo">
			<span class="login_logo_a"></span>
		</div>
	</div>
	<div class="time_all">
		<div class="time">今天是：<%=DateUtil.formatDate(new Date(), "yyyy年MM月dd日") + "&nbsp;&nbsp;&nbsp;&nbsp;" + DateUtil.getZHDay(new Date()) %></div>
	</div>

	<div class="login_main">
		<div class="login_left">
			<h3>重要公告</h3>
			<ul>
				 <s:iterator value="#request.announceList" var="item">
				 	<li>
				 		<span>[<s:date name="beginDate" format="yyyy-MM-dd"/>]</span> 
				 		<a href="javascript:void(0);" onclick="ancHandler(${item.announcementId})">${item.title}</a>
				 	</li>
				 </s:iterator>
			</ul>
		</div>
		<div class="login">
			<h2>用户登录</h2>
			<form id="loginForm" action="login.do" method="post">
			<ul class="login_list">
				<li><label class="login_list_label">用户名：</label><input
					id="userName" class="login_list_input width163" name="user.userName" 
					value="${user.userName }" type="text" onkeyup="enterHandler(event)"></li>
				<li><label class="login_list_label">密码：</label><input
					id="password" class="login_list_input width163" name="user.password" 
					value="${user.password }" type="password" onkeyup="enterHandler(event)"></li>
				<li><label class="login_list_label">验证码：</label><input
					id="validateCode" class="login_list_input width63" name="validateCode" 
					value="${validateCode }" type="text" onkeyup="enterHandler(event)">
					<img class="yanzhengma" id="validateCodeImg"
					src="login/validate_code.do" alt="验证码" onclick="changeValidateCodeHandler()"><a
					class="new_yzm" href="javascript:changeValidateCodeHandler()">换一个</a></li>
				<li>
					<label class="login_list_label"></label>
					<span style="color:red" id="msgSpan">
						<s:if test="#request.loginError != null">
							${loginError}
						</s:if>
					</span>
				</li>
				<li class="login_list_li_l"><a id="loginBtn" class="login_ing"
					href="#" target="_self" onclick="loginHandler()">登录</a>
                    <a class="wjmm" href="javascript:void(0)" target="_self">忘记密码？</a>
				</li>
			</ul>
			</form>
		</div>
        <!--[if lt IE 8]>
        <div class="newBrowser">为了您更好的使用驴妈妈E-Booking系统，请先下载安装并使用IE8及以上版本浏览器登录系统。<br>
        	<a href="http://download.microsoft.com/download/1/6/1/16174D37-73C1-4F76-A305-902E9D32BAC9/IE8-WindowsXP-x86-CHS.exe" target="_blank">点击进入官方下载</a>
			<span>◆<i>◆</i></span>
        </div>
        <![endif]-->
        
	</div>
<!--弹出层-->
    <div class="bg_opacity1 show_hide"></div>
    <iframe class="bg_opacity2 show_hide"></iframe>
    <div class="eject_rz show_hide" style="text-align:left">
        <h4>忘记密码</h4>
        <p class="tab_bot_p_1 tab_bot_p_4">
            请联系为您开通账号的管理员帮您重设密码；<br>若您是管理员请联系驴妈妈合作人或拨打系统服务热线为您重设密码。
        </p>
        <span class="close" onclick="window.location.href='to_add_user.do';return true;"></span>
    </div>


	<jsp:include page="common/footer.jsp"></jsp:include>

	<script type="text/javascript"
		src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js" charset="utf-8"></script>

</body>
</html>