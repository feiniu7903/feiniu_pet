/****
 * 存款账户支付密码操作js 
 */
var PASSWORD_REGX = /^[0-9]{6}$/;
var MOBILE_REGX=/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
var MONEY_REGX=/^\d+(.(\d){0,2}){0,1}$/;
var VALIDATEPASSWORDCODE_REGX=/^\d{6}$/;
var secs = 60;//计时

function sendFindPasswordMobileCode(){
	if(!checkMobile($('#mobileNumber').val().replace(/\s+/g,''))){
		showContent('#mobileNumberTip',"<span class='tips-ico02'></span>");
	}else{
		$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=" + $('#mobileNumber').val().replace(/\s+/g,'') + "&jsoncallback=?",function(json){
			if (json.success) {
				mobileCodeNoteTime();
				$('#sendFindPasswordMobileCodeId').attr("style","display:none");
				$('#sendFindPasswordMobileCodeTip').html("<span class='tips-success'><span class='tips-ico01'></span>校验码已发送成功，请查看手机</span>");
			} else {
				alert('验证码发送失败，请重新尝试');
			}
			
		});	
	}
}
function validateAuthenticationCode(){
	if(!checkMobile($('#mobileNumber').val().replace(/\s+/g,''))){
		showContent('#mobileNumberTip',"<span class='tips-ico02'></span>");
	}else if(null==$("input[name=validatePasswodCode]").val() || ""==$("input[name=validatePasswodCode]").val()){
		return;
	}else{
		$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobileOrEMail=" + $('#mobileNumber').val().replace(/\s+/g,'') + "&authenticationCode="+$("input[name=validatePasswodCode]").val()+"&jsoncallback=?",function(json){
			if (!json.success) {
				$('#validatePasswodCodeTip').attr("style","display: inline;");
			}
		});	
	}
}
function sendButtonUpdate(num) { 
	if(num == secs) { 	
		$('#sendFindPasswordMobileCode').bind("click",sendFindPasswordMobileCode)
		$('#sendFindPasswordMobileCode').empty();
		$('#sendFindPasswordMobileCodeRight').empty();
		$('#sendFindPasswordMobileCodeTip').empty();
		$('#sendFindPasswordMobileCode').html('重新发送校验码');
		$('#sendFindPasswordMobileCodeId').attr("style","display:block");
	}else { 
		var printnr = secs-num; 
		var msg="<span class='tips-winfo'><span class='ui-btn ui-disbtn' href=''><i>(<span class='num-second'>"+printnr+"</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span>";
		$('#sendFindPasswordMobileCodeRight').empty();
		$('#sendFindPasswordMobileCodeRight').html(msg);
	} 
} 
function mobileCodeNoteTime(){
	$('#sendFindPasswordMobileCode').unbind("click");
	for(i=1;i<=secs;i++) { 
		setTimeout("sendButtonUpdate('" + i + "')", i * 1000);	  	
	} 
}
/**
 * Password表单域的辅助信息域清空
 **/
function clearPasswordTextErrorInfo(name) {
	$(name).removeClass("input-error");
	clearContent(name + "Tip");
	clearContent(name + "Right");
}
/**
 * 清空指定元素内容
 * @param {Object} name
 */
function clearContent(name) {
	if ($(name).length > 0) {
		$(name).empty();
		$(name).hide();
	} 
}
/**
 * 指定元素展示指定内容
 * @param name
 * @param content
 */
function showContent(name, content) {
	if ($(name).length > 0) {
		$(name).html(content);
		$(name).show();
	} 
}
/**
 * 合法状态下的辅助信息域
 **/
function validErrorInfo(name) {
	$(name).removeClass("input-error");
	showContent(name + "Tip","<span class='tips-ico01'></span>");
}
/**
 * 校验金额
 * **/
function checkAmount(v){
	if(!MONEY_REGX.test(v)){
		return false;
	}else{
		return true;
	}
}
/**
 * 校验手机
 * **/
function checkMobile(v){
	if(!MOBILE_REGX.test(v)){
		return false;
	}else{
		return true;
	}
}

function error_ouput(tobj,v1,opt,strHtml){
	var obj = $(tobj).nextAll().filter("[validate='"+v1+"']");
	if(opt.position && /^[^\:]+/.test(opt.position)){
		obj = $(opt.position+" [validate='"+v1+"']");
	}
	if(obj.length==0){
		if(opt.position){
			if(/^\:/.test(opt.position)){
				$(tobj).parent().children().filter(opt.position).after(strHtml);
			}else{
				$(opt.position).html(strHtml);
			}
		}else{
			$(tobj).after(strHtml);
		}
	}else{
		if(opt.position && /^[^\:]+/.test(opt.position)){
			$(opt.position).html(strHtml);
		}else{
			obj.replaceWith(strHtml);
		}
	}
}

function outOk(tobj){
	var strHtml = '<span class="zhfs_state zhfs_v_success" validate="pass"><i></i><label class="label_tip"></label></span>';
	var opt={};
	opt.position = ":last";
	error_ouput(tobj,"pass",opt,strHtml);
	$(tobj).removeClass("input_border_red");
}
