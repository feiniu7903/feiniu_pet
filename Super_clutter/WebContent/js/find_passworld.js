/**
 * 找回密码倒计时
 * @param type
 */
function countDown_callBack(type){
	changeButStyle(2);
};
var lv_count = 0;
var countDownObj = null; //间隔函数，1秒执行 
//将时间减去1秒，计算天、时、分、秒
function countDownBySecond() {
	if (lv_count > 1) {
		lv_count = lv_count - 1;
		var second = Math.floor(lv_count % 60); // 计算秒
        if(second < 10) {
        	second = "0" + second;
        }
		$("#second_count_down").html(second);
	} else {// 剩余时间小于或等于0的时候，就停止间隔函数
		if(null != countDownObj) {
			window.clearInterval(countDownObj);
			// 这里可以添加倒计时时间为0后需要执行的事件
			countDown_callBack();
		}
	}
}

/*程序自定义函数*/
function popupModal_calback(){
	  $('body').delegate('a[_disappear]','click',function(){
		 popupModal(false, '', null, 0,true);
	  });
};

// 找回密码 - 发送验证码
function getAuthCodeByFindPW() {
	var c = $("#mobile").val();
	if(!v_mobile(c)) {
		return false;
	}
	// 灰掉按钮 
	changeButStyle(1);
	var url = contextPath+"/client/sendMessage.do?mobile="+c;
	$.ajax({
		url : url,
		data : {},
		type : "POST",
		success : function(data) {
			findPassWorldAuthCode_callback(data);
		},
		error:function() {
			popupModal(true, "网络异常,请稍后再试", popupModal_calback, 0,true); 
		},
		complete:function() {
			// 还原按钮 
			changeButStyle(2);
		}
	});
}

// 还原按钮
function changeButStyle(type) {
	if(type == "1") {
		// 设置只读 
		$("#mobile").attr('readonly','readonly');  
		$("#step1_show").hide();
		$("#step1_hide").html("您可以在<span id='second_count_down' >59</span>秒重新获取");
		$("#step1_hide").show();
		// 倒计时
		lv_count = 59;
		if(null != countDownObj) {
			window.clearInterval(countDownObj);
		}
		countDownObj = window.setInterval(countDownBySecond, 1000);
		
	} else {
		// 取消只读 
		$("#mobile").removeAttr("readonly"); 
		$("#step1_show").show();
		$("#step1_hide").html("获取手机验证码");
		$("#step1_hide").hide();
	}
}

// 找回密码-获取验证码回调
function findPassWorldAuthCode_callback(data) {
	if(data.success) {
		var mobile = $("#mobile").val();
		if(isMobile(mobile)) {
			mobile = mobile.substr(0,4) + "****" + mobile.substr(8,11);
			$("#telId").html(mobile);
		}
		findPassworldBack(1); // 
		nextStep(1,2);
	} else {
		popupModal(true, data.errorText, popupModal_calback, 0,true); 
		return;
	}
}

/**
 * 找回密码 - 重置密码
 */
function resetPassworld() {
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
	var oldP = $("#oldPassword").val();
	if(!v_resetPassword(p,oldP)) {
		return false;
	}
	
	// 
	var url = _nssoUrl+"mobileAjax/saveNewPass.do?mobile="+mobile+"&authenticationCode="+acode+"&password="+p+"&jsoncallback=?";
	$.getJSON(url,function(data) {
		resPassworld_callback(data);
	});
}


//找回密码-重置密码回调
function resPassworld_callback(data) {
	var serviceUrl = $("#redirect_url").val();;
	if(data.success) {
		if(serviceUrl!=""){
			window.location.href=contextPath+serviceUrl;
		}else{
			window.location.href=contextPath+"/login.htm";
		}
	} else {
		popupModal(true, data.errorText, popupModal_calback, 0,true); 
		return;
	}
}

// 同一个页面 后退 
function findPassworldBack(type) {
	if(type == 1) {
		$("#back2_id").show();
		$("#back1_id").hide();
	} else {
		$("#back1_id").show();
		$("#back2_id").hide();
		$("#step1").show();
		$("#step2").hide();
	}
	
}

// 手机号
function v_mobile(c) {
	if($.trim(c) == ""){
		popupModal(true, "请输入手机号", popupModal_calback, 0,true); 
		$("#mobile").focus();
		return false;
	} else if(!isMobile(c)) {
		popupModal(true, "请输入正确的手机号", popupModal_calback, 0,true); 
		$("#mobile").focus();
		return false;
	}
	
	return true;
}
// 校验码 
function v_authenticationCode(acode) {
	if($.trim(acode) == ""){
		popupModal(true, "请输入验证码", popupModal_calback, 0,true); 
		$("#authenticationCode").focus();
		return false;
	} else if($.trim(acode).length != 6) {
		popupModal(true, "请输入正确的验证码", popupModal_calback, 0,true); 
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
		popupModal(true, "请输入密码", popupModal_calback, 0,true); 
		$("#password").focus();
		return false;
	} else if(p.length < 6) {
		popupModal(true, "密码长度不能小于6", popupModal_calback, 0,true); 
		$("#password").focus();
		return false ;
	}
	
	return true;
}

/**
 * 判断新旧密码是否一致
 * @param p
 * @returns {Boolean}
 */
function v_resetPassword(newP,oldP) {
	if($.trim(oldP) == ""){
		popupModal(true, "请输入确认密码", popupModal_calback, 0,true); 
		$("#oldPassword").focus();
		return false;
	} else if(oldP.length < 6) {
		popupModal(true, "确认密码长度不能小于6", popupModal_calback, 0,true); 
		$("#oldPassword").focus();
		return false ;
	}
	if(newP != oldP) {
		popupModal(true, "密码输入不一致", popupModal_calback, 0,true); 
		return false;
	}
	return true;
}

/**
 * 验证码下一步 
 */
function nextStep(c,next){
	$("#step"+c).hide();
	$("#step"+next).show();
}

function step1change() {
	var mobile = $("#mobile").val();
	// 如果是合法的手机号
	if(isMobile(mobile)) {
		$("#step1_show").show();
		$("#step1_hide").hide();
	} else {
		$("#step1_show").hide();
		$("#step1_hide").show();
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
