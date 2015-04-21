function calculate(){
	var amount = document.getElementById("bonusDrawMoneyInfo.amount");
	var radio1 = document.getElementById("radio1");
	var radio2 = document.getElementById("radio2");
	if(amount.value!=""&&!isNaN(amount.value)){
		if(amount.value<2||amount.value>10){
			amount.focus();
			return false;
			}
		if(radio1.checked){
			$("#tax").html("0");
			$("#cash").html(parseInt(""+amount.value)*100);
			}else if(radio2.checked){
				$("#tax").html(parseInt(""+amount.value)*100*1/10);
				$("#cash").html(parseInt(""+amount.value)*100*9/10);
			}
		}
}
function saveApply(){
	var cashRefundYuan = document.getElementById("cashRefundYuan");
	var amount = document.getElementById("bonusDrawMoneyInfo.amount");
	var bank = document.getElementById("bonusDrawMoneyInfo.bank");
	var bankBranchName = document.getElementById("bonusDrawMoneyInfo.bankBranchName");
	var bankName = document.getElementById("bonusDrawMoneyInfo.bankName");
	var bankNumber = document.getElementById("bonusDrawMoneyInfo.bankNumber");
	var contactName = document.getElementById("bonusDrawMoneyInfo.contactName");
	var contactMobile = document.getElementById("bonusDrawMoneyInfo.contactMobile");
	var userRemark = document.getElementById("bonusDrawMoneyInfo.userRemark");
	var password = document.getElementById("password");
	var validatePasswodCode = document.getElementById("validatePasswodCode");
	if(parseInt(cashRefundYuan.value)<200){
		$("#cash1").html("<span class='tips-error'><span class='tips-ico02'></span>当前余额不足200</span>");
		$("#cash1").focus();
		return false;
	}else{
		$("#cash1").html("");
	}
	if(amount.value==""||isNaN(amount.value)||amount.value<2||amount.value>10){
		amount.focus();
		return false;
	}
	if(bank.value==""||bank.value.length>60){
		$("#bank").html("<span class='tips-error'><span class='tips-ico02'></span>请正确填写开户行</span>");
		bank.focus();
		return false;
	}else{
		$("#bank").html("");
	}
	if(bankBranchName.value==""||bankBranchName.value.length>40){
		$("#bankBranchName").html("<span class='tips-error'><span class='tips-ico02'></span>请正确填写所属分行</span>");
		bankBranchName.focus();
		return false;
	}else{
		$("#bankBranchName").html("");
	}	
	if(bankName.value==""||bankName.value.length>60){
		$("#bankName").html("<span class='tips-error'><span class='tips-ico02'></span>请正确填写账户名</span>");
		bankName.focus();
		return false;
	}else{
		$("#bankName").html("");
	}
	if(bankNumber.value==""||bankNumber.value.length>60){
		$("#bankNumber").html("<span class='tips-error'><span class='tips-ico02'></span>请正确填写账号</span>");
		bankNumber.focus();
		return false;
	}else{
		$("#bankNumber").html("");
	}
	if(contactName.value==""||contactName.value.length>20){
		$("#contactName").html("<span class='tips-error'><span class='tips-ico02'></span>请正确填写联系人</span>");
		contactName.focus();
		return false;
	}else{
		$("#contactName").html("");
	}
	var tmp = /^([\d]{11}||[\d]{3}[\s]{1}[\d]{4}[\s]{1}[\d]{4}){1}$/;
	if(contactMobile.value==""||!tmp.test(contactMobile.value)){
		$("#contactMobile").html("<span class='tips-error'><span class='tips-ico02'></span>请正确填写手机号</span>");
		contactMobile.focus();
		return false;
	}else{
		$("#contactMobile").html("");
	}
	if(userRemark.value.length>300){
		$("#userRemark").html("<span class='tips-error'><span class='tips-ico02'></span>请正确填写备注</span>");
		userRemark.focus();
		return false;
	}else{
		$("#userRemark").html("");
	}
	if(password.value==""||password.value.length>20){
		$("#password1").html("<span class='tips-error'><span class='tips-ico02'></span>请正确填写登录密码</span>");
		password.focus();
		return false;
	}else{
		$("#password1").html("");
	}
	if(validatePasswodCode.value==""||validatePasswodCode.value.length!=6){
		$("#validatePasswodCodeTip").html("<span class='tips-error'><span class='tips-ico02'></span>请正确填写验证码</span>");
		password.focus();
		return false;
	}else{
		$("#validatePasswodCodeTip").html("");
	}
	document.getElementById("frm").submit();
}

$('#send-verifycode').click(function(){
	var mobileNumber=$('#send-verifycode').attr('data-atr');
	$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail="+mobileNumber+"&jsoncallback=?",function(json){
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
 });
 

 function JS_countdown(_cdbox) {
	/*-------------------------------------------*/
	var _InterValObj; // timer变量，控制时间
	var _count = 60; // 间隔函数，1秒执行
	var _curCount;// 当前剩余秒数
	sendMessage(_count);
	function sendMessage(_count) {
		_curCount = _count;
		$(_cdbox).html(_curCount);
		_InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次
	}
	// timer处理函数
	function SetRemainTime() {
		if (_curCount == 0) {
			window.clearInterval(_InterValObj);// 停止计时器
			var expr = _cdbox.indexOf("-old") > 0 ? "-old" : "";
			$("#JS_countdown" + expr).children(".tips-success").html(
					"<span class=\"tips-ico01\"></span>校验码已发送成功，以最近发送的校验码为准")
					.end().hide();
			$("#send-verifycode" + expr).html("<i>重新发送验证码</i>").show();
		} else {
			_curCount--;
			$(_cdbox).html(_curCount);
		}
	}
}