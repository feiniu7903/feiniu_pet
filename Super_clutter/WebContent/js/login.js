var LT_ACTIVITY_12580 = "mobile_activity_12580"; // 12580活动
var LT_ACTIVITY_10000 = "mobile_activity_10000"; // 10000活动
var loginUrl=decodeURIComponent(getCookie('loginUrl'));
// 登陆
function login (activityChannel) {
	var u = $("#username").val();
	if($.trim(u) == ""){
		lvToast(false,"用户名不能为空！",LT_LOADING_CLOSE);
		$("#username").focus();
		return ;
	}
	var p = $("#password").val();
	if($.trim(p) == ""){
		lvToast(false,"密码不能为空！",LT_LOADING_CLOSE);
		$("#password").focus();
		return ;
	}
	if(u.indexOf(" ")>=0 || p.indexOf(" ")>=0){
		lvToast(false,"用户名/密码错误！",LT_LOADING_CLOSE);
		$("#username").focus();
		return ;
	}
	var url = $("#redirect_url").val();
	$.post(contextPath+"/t_login.htm",{ username : u, password : p,activityChannel:activityChannel},function(data){
		if(data.success){
			if(navigator.geolocation) {
				setLocalStorage("LT_user_p",p);
			}
			//登录成功活动处理
			if(loginUrl.indexOf("callUrl")>0 && activityChannel!=null && activityChannel!=''){
				var callBackUrl=loginParms(loginUrl,data.userId,activityChannel,"_login");
				window.location.href=callBackUrl;
			}else if(url!=""){
				if(url.indexOf("http://")>=0){
					window.location.href=url+"?lvsessionid="+data.lvsessionId;
				}else{
					window.location.href=contextPath+url;
				}
			}else{
				window.location.href="http://"+hostName+"/user/";
			}
				
//			//活动处理 12580活动
//			if(null != data.activityChannel && data.activityChannel == LT_ACTIVITY_12580) {
//				window.location.href=contextPath+"/user/myconpon.htm?activityChannel="+LT_ACTIVITY_12580+"_login";
//			} else if(null != data.activityChannel && data.activityChannel == LT_ACTIVITY_10000 ) {
//				window.location.href=contextPath+"/user/myconpon.htm?activityChannel="+LT_ACTIVITY_10000+"_login";
//			} else if(url!=""){
//				window.location.href=contextPath+url;
//			}else{
//				window.location.href=contextPath+"/my";
//			}
//			
			
		}else{
			var msg = data.errorText;
			if(null != msg && ""!=msg) {
				lvToast(false,data.errorText,LT_LOADING_CLOSE);
			} else {
				lvToast(false,"用户名/密码错误",LT_LOADING_CLOSE);
			}
		}
	});
}
//跳转请求URL
function loginParms(loginUrl,userId,activityChannel,flag){
	var parms=loginUrl.split("callUrl=")[1];
	var callBack="&callBack="+encodeURIComponent("http://"+hostName+"/clutter/promotionAction/promotionAction.htm?activityChannel="+activityChannel+flag);
	var backUrl=parms+"&userId="+userId+callBack;
	$.cookie('loginUrl', null);
	return backUrl;
}
//跳转请求URL微信调用
function loginParmsWeixin(loginUrl,userId){
	var parms=loginUrl.split("callUrl=")[1];
	var backUrl=parms+"&userId="+userId;
	$.cookie('loginUrl', null);
	return backUrl;
}
//读取Cookie
function getCookie(objName) {
	var arrStr = document.cookie.split("; ");
	for (var i = 0; i < arrStr.length; i++) {
		var temp = arrStr[i].split("=");
		if (temp[0] == objName) {
			return temp[1];
		}
	}
	return '';
}
//删除Cookie
function delCookie(name){ 
	var date=new Date(); 
	date.setTime(date.getTime()-10000); 
	document.cookie=name+"=n;expire="+date.toGMTString(); 
}
/**
 * 第三方账号登陆. 
 * @param url
 */
function union_login(url){
	window.open(url);
}

// 注册 - 获取验证码 .
function getAuthCode() {
	var c = $("#mobile").val();
	if(!v_mobile(c)) {
		return false;
	}
	var check = $("#checkbox").attr('checked');
	if(undefined == check || 'undefined' == check || !(check =='checked')) {
		lvToast(false,"请同意 《驴妈妈旅游网会员服务条款》！",LT_LOADING_CLOSE);
		return;
	}
	var url = _nssoUrl+"mobileAjax/register.do?mobileOrEMail="+c+"&jsoncallback=?";
	$.getJSON(url,function(data) {
		regAuthCode_callback(data);
	});
}

//注册-获取验证码回调
function regAuthCode_callback(data) {
	if(data.success) {
		lvToast(true,"验证码已发送",LT_LOADING_CLOSE);
		nextStep(1,2);
	} else {
		lvToast(false,data.errorText,LT_LOADING_CLOSE);
		return;
	}
}

// 忘记密码首页
function submitFindPW() {
	 $("#findPassworldForm").submit();
}

// 注册 - 验证校验 
function reg(activityChannel) {
	var mobile = $("#mobile").val();
	// 校验码
	var acode = $("#authenticationCode").val();
	if(!v_authenticationCode(acode)) {
		return false;
	}
	// 密码
	var p = $("#password").val();
	if(!v_passworld(p)) {
		return false;
	}
	
	lvToast(true,"校验中...",LT_LOADING_TIME);
	var secondChannel = "LVMM";
	
	if(null != activityChannel &&  activityChannel == LT_ACTIVITY_12580) {
		secondChannel = "12580";
	} else if(null != activityChannel &&  activityChannel == LT_ACTIVITY_10000) {
		secondChannel = "10000";
	}
	$.getJSON(_nssoUrl+"mobileAjax/verifyCode.do?mobile="+mobile+"&authenticationCode="+acode+"&password="+p+"&firstChannel=TOUCH&secondChannel="+secondChannel+"&jsoncallback=?",function(data) {
		if(data.success) {
			//注册成功活动处理
			if(loginUrl.indexOf("callUrl")>0 && activityChannel!=null && activityChannel!=''){
				var callBackUrl=loginParms(loginUrl,data.userId,activityChannel,"_reg");
				window.location.href=callBackUrl;
			}else{
				window.location.href="http://"+hostName+"/user/";
			}
			
//			if(null != activityChannel &&  activityChannel == LT_ACTIVITY_12580) {
//				window.location.href=contextPath+"/user/myconpon.htm?activityChannel="+LT_ACTIVITY_12580+"_reg";
//			} else if(null != activityChannel &&  activityChannel == LT_ACTIVITY_10000) {
//				window.location.href=contextPath+"/user/myconpon.htm?activityChannel="+LT_ACTIVITY_10000+"_reg";
//			} else {
//				window.location.href=contextPath+"/my";
//			}
		} else {
			lvToast(false,data.errorText);
		}
	});
	
}

// 手机号
function v_mobile(c) {
	if($.trim(c) == ""){
		lvToast(false,"请输入手机号",LT_LOADING_CLOSE);
		$("#mobile").focus();
		return false;
	} else if(!isMobile(c)) {
		lvToast(false,"请输入正确的手机号",LT_LOADING_CLOSE);
		$("#mobile").focus();
		return false;
	}
	
	return true;
}
// 校验码 
function v_authenticationCode(acode) {
	if($.trim(acode) == ""){
		lvToast(false,"请输入验证码",LT_LOADING_CLOSE);
		$("#authenticationCode").focus();
		return false;
	} else if($.trim(acode).length != 6) {
		lvToast(false,"请输入正确的验证码",LT_LOADING_CLOSE);
		$("#authenticationCode").focus();
		return false;
	} 
	
	return true;
}

/**
 * 校验密码
 * @param p
 * @returns {Boolean}
 */
function v_passworld(p) {
	if($.trim(p) == ""){
		lvToast(false,"请输入密码",LT_LOADING_CLOSE);
		$("#password").focus();
		return false;
	} else if(p.length < 6) {
		lvToast(false,"密码长度不能小于6",LT_LOADING_CLOSE);
		$("#password").focus();
		return false ;
	}
	
	return true;
}

/**
 * 验证码下一步 
 */
function nextStep(c,next){
	$("#step"+c).hide();
	$("#step"+next).show();
	$("#c_step"+next).removeClass("lv-progress-node").addClass("lv-progress-active");
	$("#c_step"+c).removeClass("lv-progress-active").addClass("lv-progress-node");
}


// 注册删除小图标默认不显示 
var mobile = $("#mobile").val();
$("#mobile, #authenticationCode, #password").keyup(function() {
	var _clearVal = $(this).val();
	if(_clearVal != ''){
		$(this).siblings('.clear_password').show();
	}else {
		$(this).siblings('.clear_password').hide();
	}
});
function step1change() {
	mobile = $("#mobile").val();
	// 如果是合法的手机号
	if(isMobile(mobile)) {
		$("#step1_show").show();
		$("#step1_hide").hide();
	} else {
		$("#step1_show").hide();
		$("#step1_hide").show();
	}
	//点击删除消失
	$('.clear_password').live('click',function(){
		if(mobile != ''){
			$('.clear_password').show();
		}else {
			$('.clear_password').hide();
		}
	});	
}

$('.clear_password').hide();

// 注册 - 验证码  按钮状态改变  
function step2change() {
	var authenticationCode = $("#authenticationCode").val();
	// 如果是合法的验证码 ，验证码长度为6
	if($.trim(authenticationCode) != "" && authenticationCode.length ==6 ) {
		$("#step2_show").show();
		$("#step2_hide").hide();
	} else {
		$("#step2_show").hide();
		$("#step2_hide").show();
	}
}

//注册 - 提交 按钮状态改变  
function step3change() {
	var password = $("#password").val();
	// 如果是合法的手机号
	if($.trim(password) != "" && password.length > 5 ) {
		$("#step3_show").show();
		$("#step3_hide").hide();
	} else {
		$("#step3_show").hide();
		$("#step3_hide").show();
	}
}

//手机号校验
function isMobile(m) {
	if ($.trim(m) == "" || !m.match(/^1[3|4|5|7|8][0-9]\d{4,8}$/)
			|| m.length != 11) {
		return false;
	} else {
		return true;
	}
}



// 统一登录 
function union_login(url,serviceUrl) {
	// 把url存到cookies中 setcookie(cookieName, cookieValue, seconds, path, domain, secure) 
	if(null == serviceUrl ) {
		serviceUrl = "";
	}
	try{
		setcookie('serviceUrl', serviceUrl, 72000000, '/', ".lvmama.com",false); //设置cookie 秒
	}catch(e){
		
	}
	window.location.href=url;
}

