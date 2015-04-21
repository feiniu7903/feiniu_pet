var PASSWORD_REGX = /^[0-9]{6}$/;
var secs = 60;//计时
$(document).ready(function() {
	$('.passwordModify .settingPassword dd > p:first').css('color','#F00');	   
	$('.showSetting ul > :last').css('border','none');	   
	$('.clickShow').click(function(){
		var  clickShow = $(this).attr('data-biaoshi');
		$('.'+clickShow).show();
		$('.bgDiv').show();
	})
	
	$('#initPasswordButton').click(function(){
		var closeThis = $(this).attr('data-biaoshi');
		var settingButtonA = $(this).attr('data-biaoshi2');
		var postData = {paymentPassword:$("#paymentPassword").val()};
		var paymentPasswordFlag = passwrodFieldOnBlur('#paymentPassword',"<s class='icon3'></s>密码必须为6位数字");
		var confirmPasswordFlag = confirmPasswordFieldOnBlur('#paymentPassword','#confirmPassword',"<s class='icon3'></s>两次密码不一致");
		if(paymentPasswordFlag && confirmPasswordFlag){
			$.ajax({
				url: "/myspace/account/initPassword.do",
				type:"post",
				dataType:"json",
				data:postData,
				success:function(data){
					if(data.flag){
						$('.'+closeThis).hide();
						$('.'+settingButtonA).show();		
						$('.bgDiv').show();	
					}
				}
			});
		}
	})
	
	$('#findInitPasswordButton').click(function(){
		var closeThis = $(this).attr('data-biaoshi');
		var settingButtonA = $(this).attr('data-biaoshi2');
		var postData = {paymentPassword:$("#paymentPassword_").val()};
		var paymentPasswordFlag_ = passwrodFieldOnBlur('#paymentPassword_',"<s class='icon3'></s>密码必须为6位数字");
		var confirmPasswordFlag_ = confirmPasswordFieldOnBlur('#paymentPassword_','#confirmPassword_',"<s class='icon3'></s>两次密码不一致");
		if(paymentPasswordFlag_ && confirmPasswordFlag_){
			$.ajax({
				url: "/myspace/account/initPassword.do",
				type:"post",
				dataType:"json",
				data:postData,
				success:function(data){
					if(data.flag){
						$('.'+closeThis).hide();
						$('.'+settingButtonA).show();		
						$('.bgDiv').show();	
					}
				}
			});
		}
	})
	
	$('#passwordModify').click(function(){
		var  clickShow = $(this).attr('data-biaoshi');
		$('.'+clickShow).show();
		$('.bgDiv').show();
	})
	
	$('#modifyPasswordButton').click(function(){
		var closeThis = $(this).attr('data-biaoshi');
		var settingButtonA = $(this).attr('data-biaoshi2');
		var postData = {paymentPassword:$("#newPaymentPassword").val(),oldPaymentPassword:$("#oldPaymentPassword").val()};
		var newPaymentPasswordFlag = passwrodFieldOnBlur('#newPaymentPassword',"<s class='icon3'></s>密码必须为6位数字");
		var newConfirmPasswordFlag = confirmPasswordFieldOnBlur('#newPaymentPassword','#newConfirmPassword',"<s class='icon3'></s>两次密码不一致");
		if(newPaymentPasswordFlag && newConfirmPasswordFlag){
			$.ajax({
				url: "/myspace/account/modifyPassword.do",
				type:"post",
				dataType:"json",
				data:postData,
				success:function(data){
					if(data.flag){
						$('.'+closeThis).hide();
						$('.'+settingButtonA).show();		
						$('.bgDiv').show();	
					}
				}
			});
		}
	})
	
	$('#passwordForget').click(function(){
		$.ajax({
			url:"/myspace/account/forgetPassword.do",
			type:"post",
			success:function(data){
				var data=eval("("+data+")");
				if(data.success){
					$('.mobileNumber').html(data.mobileFormat);
				}
			}
		});
		var  clickShow = $(this).attr('data-biaoshi');
		$('.'+clickShow).show();
		$('.bgDiv').show();
	})
	
	$('#sendFindPasswordMobileCode').click(function(){
		$.ajax({
			url:"/myspace/account/sendFindPasswordMobileCode.do",
			type:"post",
			success:function(data){
                var data=eval("("+data+")");
				if(data.sendSmsSuccess){
					mobileCodeNoteTime();
					$('#mobileCodeTip').html("<font color='red'>发送校验码成功！</font>");
				}
			}
		})
	})
	
	$('#passwordForgetButtonA').click(function(){
		var validatePasswodCode = $('#validatePasswodCode').val();
		var closeThis = $(this).attr('data-biaoshi');
		var settingButtonA = $(this).attr('data-biaoshi2');
		$.ajax({
			url:"/myspace/account/validateFindPasswordMobileCode.do",
		    type:"post",
		    dataType:"json",
		    data:{validatePasswodCode:validatePasswodCode},
			success:function(data){
		    	if(data.validateSuccess){
					$('.'+closeThis).hide();
					$('.'+settingButtonA).show();		
					$('.bgDiv').show();	
		    	}
			}
		})
	})
	
	
	$('#payOKButton').click(function(){
		var address = "/view/view.do?orderId="+$('#orderId').val();
		window.location.href = address;
	});
	//现金账户页面的修改和设置支付密码的确定按钮事件
	$('.settingButtonA').click(function(){
		var closeThis = $(this).attr('data-biaoshi');
		$('.'+closeThis).hide();
		$('.bgDiv').hide();
		var IsParentPage = $('#IsParentPage').val();
		var address = window.location.href;
		if("Y" == IsParentPage){
			window.parent.location.href = address;
		}else{
			window.location.href = address;
		}
	})
	//支付页面的修改和设置支付密码的确定按钮事件
	$('.settingButton_A').click(function(){ 
		var closeThis = $(this).attr('data-biaoshi'); 
		$('.'+closeThis).hide(); 
		$('.bgDiv').hide(); 
		var IsParentPage = $('#IsParentPage').val(); 
		var address = "/view/view.do?orderId="+$('#orderId').val(); 
		if("Y" == IsParentPage){ 
		window.parent.location.href = address; 
		}else{ 
		window.location.href = address; 
		} 
	})

	$('.close,.settingButtonC').click(function(){
		var closeThis = $(this).attr('data-biaoshi');
		$('.'+closeThis).hide();
		$('.bgDiv').hide();
	})
	
	
	
	$('#paymentPassword').bind("blur", function(){
		clearPasswordTextErrorInfo('#paymentPassword');
		passwrodFieldOnBlur('#paymentPassword',"<s class='icon3'></s>密码必须为6位数字");
	}).bind("focus", function(){
		clearPasswordTextErrorInfo('#paymentPassword');
	});

	$('#confirmPassword').bind("blur", function(){
		clearPasswordTextErrorInfo('#confirmPassword');
		confirmPasswordFieldOnBlur('#paymentPassword','#confirmPassword',"<s class='icon3'></s>两次密码不一致");
	}).bind("focus", function(){
		clearPasswordTextErrorInfo('#confirmPassword');
	});
	
	$('#oldPaymentPassword').bind("blur", function(){
		clearPasswordTextErrorInfo('#oldPaymentPassword');
		$.ajax({
			url:"/myspace/account/validatePassword.do",
			type:"post",
			dataType:"json",
			data:{paymentPassword:$('#oldPaymentPassword').val()},
			success:function(data){
				if(!data.validateSuccess){
					showContent('#oldPaymentPasswordTip',"<s class='icon3'></s>您输入的密码不正确");
				}else{
					showContent('#oldPaymentPasswordRight',"<s class='icon2'></s>");
				}
			}
		});
		
	}).bind("focus", function(){
		clearPasswordTextErrorInfo('#oldPaymentPassword');
	});
	
	$('#newPaymentPassword').bind("blur", function(){
		clearPasswordTextErrorInfo('#newPaymentPassword');
		passwrodFieldOnBlur('#newPaymentPassword',"<s class='icon3'></s>密码必须为6位数字");
	}).bind("focus", function(){
		clearPasswordTextErrorInfo('#newPaymentPassword');
	});
	
	$('#newConfirmPassword').bind("blur", function(){
		clearPasswordTextErrorInfo('#newConfirmPassword');
		confirmPasswordFieldOnBlur('#newPaymentPassword','#newConfirmPassword',"<s class='icon3'></s>两次密码不一致");
	}).bind("focus", function(){
		clearPasswordTextErrorInfo('#newConfirmPassword');
	});
	
	$('#paymentPassword_').bind("blur", function(){
		clearPasswordTextErrorInfo('#paymentPassword_');
		passwrodFieldOnBlur('#paymentPassword_',"<s class='icon3'></s>密码必须为6位数字");
	}).bind("focus", function(){
		clearPasswordTextErrorInfo('#paymentPassword_');
	});
	
	$('#confirmPassword_').bind("blur", function(){
		clearPasswordTextErrorInfo('#confirmPassword_');
		confirmPasswordFieldOnBlur('#paymentPassword_','#confirmPassword_',"<s class='icon3'></s>两次密码不一致");
	}).bind("focus", function(){
		clearPasswordTextErrorInfo('#confirmPassword_');
	});
	
	$('#paymentPassword_input').bind("blur", function(){
		clearPasswordTextErrorInfo('#paymentPassword_input');
		$.ajax({
			url:"/myspace/account/validatePassword.do",
			type:"post",
			dataType:"json",
			data:{paymentPassword:$('#paymentPassword_input').val()},
			success:function(data){
				if(data.validateSuccess){
					$('#paymentPassword_inputRight').html("<s class='icon2'></s>");
					$('#paymentPassword_inputRight').show();
				} else {
					$('#paymentPassword_inputTip').html("<s class='icon3'></s>您输入的密码不正确！");
					$('#paymentPassword_inputTip').show();
				}
			}
		});
	}).bind("focus", function(){
		clearPasswordTextErrorInfo('#paymentPassword_input');
	});
/**
 * onBlur事件
 * @param value
 * @returns {Boolean}
 */
function passwrodFieldOnBlur(name,content) {
	var value = $(name).val();
	if (PASSWORD_REGX.test(value)) {
		validErrorInfo(name);
		return true;
	} else {
		showContent(name + "Tip",content);
		return false;
	}
}
/**
 * 确认密码框的onBlur事件
 * @param value
 * @returns {Boolean}
 */
function confirmPasswordFieldOnBlur(firstName,secondName,content) {
	var paymentPassword = $(firstName).val();
	var confirmPassword = $(secondName).val();
	if (paymentPassword == confirmPassword) {
		validErrorInfo(secondName);
		return true;
	} else{
		showContent(secondName + "Tip",content);
		return false;
	}
}
/**
 * 合法状态下的辅助信息域
 **/
function validErrorInfo(name) {
	showContent(name + "Right","<s class='icon2'></s>");
	clearContent(name + "Tip");
}
/**
 * Password表单域的辅助信息域清空
 **/
function clearPasswordTextErrorInfo(name) {
	clearContent(name + "Right");
	clearContent(name + "Tip");
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
	
});

function sendButtonUpdate(num) { 
		if(num == secs) { 	
			var msgButs = document.getElementById('sendFindPasswordMobileCode');
			msgButs.value ="再次发送"; 
			msgButs.disabled=false; 
		}else { 
			printnr = secs-num; 
			document.getElementById('sendFindPasswordMobileCode').value = "( " + printnr +"发送消息)"; 
		} 
	} 
	
function mobileCodeNoteTime(){
		var event=document.getElementById('sendFindPasswordMobileCode');
		event.disabled=true; //将按钮设为失效
		butt_info=1;
		for(i=1;i<=secs;i++) { 
			setTimeout("sendButtonUpdate('" + i + "')", i * 1000);	  	
		} 
}
