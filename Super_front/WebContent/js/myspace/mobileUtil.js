/******
 * 我的驴妈妈手机验证码
 */
var MOBILE_REGX=/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
var MOBILE_AUTHENTUCATIONCODE_URL="http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do";
var MOBILE_UNIQUE_URL="http://login.lvmama.com/nsso/ajax/checkUniqueField.do";
/**
 * 手机号码校验
 * @param k 手机号控件id
 * @returns {Boolean}
 */
function checkMobileNumber(k){
	var flag=MOBILE_REGX.test($(k).val().replace(/\s+/g,''));
	if(!flag){
		error_tip(k,'请输入有效的手机号码');
	}
	return flag;
}
/**
 * 发送校验码
 * @param k 手机号控件id
 */
function sendAuthenticationCode(k){
	$.getJSON(MOBILE_AUTHENTUCATIONCODE_URL+"?mobileOrEMail=" + $(k).val().replace(/\s+/g,'') + "&jsoncallback=?",function(json){
		if (json.success) {
			$('#reSendAuthenticationCodeDiv').show();
			$('#sendAuthenticationCodeDiv').hide();	
			$('#send-verifycode').hide();
			$("#JS_countdown").show();
			JS_countdown("#JS_countdown span.num-second");
		} else {
			alert('验证码发送失败，请重新尝试');
		}
	});	
}
/**
 * 后台校验
 * @param codeId 校验码控件id 
 * @param div 步骤divId
 * @param url 校验地址
 * @param data 数据
 */
function validateAuthenticationCode(codeId,div,url,data){
	$.ajax({
		url: url,
		type:"post",
		dataType:"json",
		data:data,
		success:function(json){
			if(json.flag){
				$(div).attr("style","display:none");
				$(div+'Success').attr("style","display:block");
			}else{
				error_tip(codeId,'校验码错误，请重新输入');
			}
		}
	});
}
/**
 * 手机号唯一校验
 * @param k 手机号控件id
 * @param call 回调函数
 */
function checkUniqueField(k,call){
	$.getJSON(MOBILE_UNIQUE_URL+"?mobile=" + $(k).val().replace(/\s+/g,'') + "&jsoncallback=?",function(json){
		if (json.success == true) {
			call();
		} else {
			error_tip(k,'该手机号已被注册，请更换其它手机号');
		}	
	});	
}
