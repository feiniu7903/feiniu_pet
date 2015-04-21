<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业注册页面-驴妈妈分销平台</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/tip.css"
	rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
</head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>
<body>
	<jsp:include page="/WEB-INF/pages/common/head.jsp"></jsp:include>

	<form:form id="userForm" name="userForm" modelAttribute="userForm"
		action="/reg/regSave" method="post">
		<div class="login_form">
			<div class="flowstep step01"></div>

			<div class="nRBox">
				<h3>会员登录名和密码项</h3>
				<ul class="formBox">
					<li><label class="titleBox"><em>*</em>会员登录名：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="userName" name="userName"
								value="${userForm.userName }" type="text"> <span
								class="tiptext tip-line tipsxx">仅限4-16个英文或数字字符（A-Z,a-z，0-9）及（—_）的组合</span>
							<span class="tiptext tip-line errorTipsBox"> </span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox"><em>*</em>密码：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="loginPassword" name="loginPassword"
								type="password"> <span class="tiptext tip-line tipsxx"></span>
							<span class="tiptext tip-line errorTipsBox"> </span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox"><em>*</em>再输一次密码：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="loginPasswordTwo"
								name="loginPasswordTwo" type="password"> <span
								class="tiptext tip-line errorTipsBox"> </span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
				</ul>
			</div>

			<div class="nRBox">
				<h3>个人资料和联系方式</h3>
				<ul class="formBox">
					<li><label class="titleBox"><em>*</em>您的真实姓名：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="realName" name="realName"
								value="${userForm.realName }" type="text"> <span
								class="tiptext tip-line tipsxx">请输入不超过40个字符的真实姓名</span> <span
								class="tiptext tip-line errorTipsBox"></span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox"><em>*</em>性别：</label> <label
						class="sexBox"> <input type="radio" checked="checked"
							value="Y" name="gender">先生
					</label> <label class="sexBox"> <input type="radio" name="gender"
							value="M">女士
					</label></li>
					<li><label class="titleBox">您的职务：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="duties" name="duties"
								value="${userForm.duties }" type="text">
						</div></li>
					<li><label class="titleBox">所在部门：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="department" name="department"
								value="${userForm.department }" type="text">
						</div></li>
					<li><label class="titleBox">常用固定电话：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="phoneNumber" name="phoneNumber"
								value="${userForm.phoneNumber }" type="text">
						</div></li>
					<li><label class="titleBox">传真：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="faxNumber" name="faxNumber"
								value="${userForm.faxNumber }" type="text">
						</div></li>
					<li><label class="titleBox"><em>*</em>手机：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="mobileNumber" name="mobileNumber"
								value="${userForm.mobileNumber }" type="text"> <span
								class="tiptext tip-line tipsxx">手机是您找回用户名和密码的主要途径，请务必正确填写，否则将无法通过审核。</span>
							<span class="tiptext tip-line errorTipsBox"> </span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox"><em>*</em>常用通迅地址：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="address" name="address"
								value="${userForm.address }" type="text"> <span
								class="tiptext tip-line errorTipsBox"></span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox"><em>*</em>邮编：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="zipCode" name="zipCode"
								value="${userForm.zipCode }" type="text"> <span
								class="tiptext tip-line errorTipsBox"></span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox"><em>*</em>电子邮件：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="email" name="email"
								value="${userForm.email }" type="text"> <span
								class="tiptext tip-line errorTipsBox"></span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox">网址：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="netUrl" name="netUrl"
								value="${userForm.netUrl }" type="text">
						</div></li>
					<li><label class="titleBox">QQ号：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="qqAccount" name="qqAccount"
								value="${userForm.qqAccount }" type="text">
						</div></li>
				</ul>
			</div>
			<div class="nRBox">
				<h3>企业相关信息</h3>
				<ul class="formBox">
					<li><label class="titleBox"><em>*</em>公司所在地：</label> <select
						name="provinceId" id="captialId" class="areaselectBox province"><option
								value="">--请选择省份--</option></select> <select name="cityId" id="cityId"
						class="areaselectBox city"><option value="">--请选择城市--</option></select>
					</li>
					<li><label class="titleBox"><em>*</em>公司名称：</label>
						<div class="form-inline form-small">
							<input type="text" name="companyName" id="companyName"
								value="${userForm.companyName }" class="textClass"> <span
								class="tiptext tip-line errorTipsBox"></span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox"><em>*</em>类型选择：</label>
						<div class="form-inline form-small">
							<select id="companyTypeId" name="companyTypeId" class="tpyeselectBox">
							<c:forEach var="cType" items="${companyTypes}">
								<option value="${cType.companyTypeId }">${cType.companyTypeName }</option>
							</c:forEach>
							</select>
						</div></li>
					<li><label class="titleBox"><em>*</em>客服负责人姓名：</label>
						<div class="form-inline form-small">
							<input type="text" name="chargeName" id="chargeName"
								value="${userForm.chargeName }" class="textClass"> <span
								class="tiptext tip-line errorTipsBox"></span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox"><em>*</em>客服电话：</label>
						<div class="form-inline form-small">
							<input type="text" name="serviceTel" id="serviceTel"
								value="${userForm.serviceTel }" class="textClass"> <span
								class="tiptext tip-line tipsxx">例：021-8291201</span> <span
								class="tiptext tip-line errorTipsBox"></span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li class="teshuLi"><label class="titleBox">公司简介：</label>
						<div class="form-inline form-small">
							<textarea class="textareaBox" name="companyProfile">${userForm.companyProfile }</textarea>
						</div></li>
					<li><label class="titleBox">法人代表：</label>
						<div class="form-inline form-small">
							<input type="text" name="legalRepresentative"
								value="${userForm.legalRepresentative }"
								id="legalRepresentative" class="textClass">
						</div></li>
					<li><label class="titleBox">员工人数：</label>
						<div class="form-inline form-small">
							<select id="employeeNum" name="employeeNum" class="tpyeselectBox">
								<option value="10人以下">10人以下</option>
								<option value="10-100人">10-100人</option>
								<option value="100-500人">100-500人</option>
								<option value="500-1000人">500-1000人</option>
								<option value="1000人以上">1000人以上</option>
							</select>
						</div></li>
					<li><label class="titleBox"><em>*</em>验证码：</label>
						<div class="form-inline form-small">
							<input class="textCodeClass textClass" id="imageCode"
								name="imageCode" type="text"> <img id="image"
								src="/tnt/code.png"> <a class="link_blue"
								onclick="refreshCheckCode('image');return false;" href="#">换一张</a>
							<span class="tiptext tip-line errorTipsBox"></span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
				</ul>
			</div>
			<div class="tjbkBox">
				<c:if test='${ result.success=="false"}'>
				<span class="tiptext tip-line " style="color:red">${ result.errorText}</span>
			</c:if>
			</div>
			<div class="tjbkBox">
				<a href="#" onclick="return false;"><img src="http://pic.lvmama.com/img/fx/tj_btn.jpg" id="regSubmit"
					width="233" height="36" /></a>
				<div class="lv-agree">
					<h3>驴妈妈旅游网预订条款</h3>
					<h4>1.驴妈妈预订条款的确认</h4>
					<p>驴妈妈旅游网（以下简称"驴妈妈"）各项服务的所有权与运作权归景域旅游运营集团所有。本服务条款具有法律约束力。一旦您点选"确认下单"成功提交订单后，即表示您自愿接受本协议之所有条款，并同意通过驴妈妈订购旅游产品。</p>
					<h4>2.服务内容</h4>
					<p>2.1 驴妈妈服务的具体内容由景域旅游运营集团根据实际情况提供，驴妈妈对其所提供之服务拥有最终解释权。</p>
					<p>2.2
						景域旅游运营集团在驴妈妈上向其会员提供相关网络服务。其它与相关网络服务有关的设备（如个人电脑、手机、及其他与接入互联网或移动网有关的装置）及所需的费用（如为接入互联网而支付的电话费及上网费、为使用移动网而支付的手机费等）均由会员自行负担。</p>
				</div>
			</div>
		</div>
		<!--login_form end-->
		<input name="isCompany" value="true" type="hidden" />
		<input id="province_city" tag="${userForm.provinceId }"
			value="${userForm.cityId }" type="hidden" />
	</form:form>

	<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
	<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
	<script src="http://s2.lvjs.com.cn/js/ui/lvmamaUI/lvmamaUI.js"></script>
	<script src="${basePath}/js/jqueryCity.js"></script>
	<script src="${basePath}/js/jquery.validate.min.js"></script>
	<script src="${basePath}/js/user/user_validate.js"></script>
	<script
		src="http://pic.lvmama.com/js/ui/lvmamaUI/plugin/city/json-array-of-city-new.js"></script>
	<script
		src="http://pic.lvmama.com/js/ui/lvmamaUI/plugin/city/json-array-of-province.js"></script>
	<script language="javascript">
	$(function(){
		var userForm = $("#userForm");
		userForm.validate(companyReg);
		$("#regSubmit").bind("click", function() {
			if (!userForm.validate().form()) {
				return;
			}
			userForm.submit();
		});
	});
	$(".tureTipsBox").hide();
	UI.extend.city({
		province : $("#captialId"),
		city : $("#cityId"),
		provinceRequest : true,
		cityRequest : true
	});
	function provinceLoaded() {
		for ( var i = 0; i < document.getElementById("captialId").options.length; i++) {
			if (document.getElementById("captialId").options[i].value == $(
					"#province_city").attr("tag")) {
				document.getElementById("captialId").options[i].selected = "true";
				break;
			}
		}
	}
	function cityLoaded() {
		for ( var i = 0; i < document.getElementById("cityId").options.length; i++) {
			if (document.getElementById("cityId").options[i].value == $(
					"#province_city").val()) {
				document.getElementById("cityId").options[i].selected = "true";
				break;
			}
		}
	}
	$("#captialId").change();
	function refreshCheckCode(s) {
		var elt = document.getElementById(s);
		elt.src = elt.src + "?_=" + new Date().getTime();
	}
	</script>
</body>
</html>