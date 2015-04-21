var secs = 60;//计时

$(document).ready(function () {	
	$('.tanchu').click(function(){
				$('#msgDiv,#mailDiv,#bgDiv').hide();
				$('#msgDiv,#bgDiv').show();
	});
	$('.e-mall_activation').click(function(){
				$('#msgDiv,#mailDiv,#bgDiv').hide();			
				$('#mailDiv,#bgDiv').show();
	});
	$('#msgShut,#msgShut3').click(function(){
				$('#msgDiv,#mailDiv,#bgDiv').hide();
	});	
	//手机号码验证
	$("#msgButs").click(function () {
		if (!checkFormat("^[0-9]{11}$", $("#mobileNumber").val())) {
			$("#mobileNumberTip").html("填写正确手机号");		
		}else{
			$.ajax({
				url:"/nsso/ajax/checkUniqueField.do", 
				type:"post", 
				dataType:"json", 
				data:{
					mobile:$("#mobileNumber").val()
				}, 
				success:function (response){					
					if (response.success == false) {
						$("#mobileNumberTip").html("手机号码已存在");	
					} else {
						$.ajax({
							url:"/nsso/ajax/sendAuthenticationCode.do", 
							type:"post", 
							dataType:"json", 
							data:{
								mobileOrEMail:$("#mobileNumber").val(),
								userId : $("#userId").val(),
								validateCode:	$("#valDate").val()
							}, 
							success:function (response) {				
								if (response.success == true) {
									$("#mobileNumberTip").html("手机绑定码发送成功");
									refreshCheckCode("image");
									document.getElementById('msgButs').disabled=true; //将按钮设为失效
									for(i=1;i<=secs;i++) { 
		  								window.setTimeout("update(" + i + ")", i * 1000);	  	
									}
								} else {
									$("#mobileNumberTip").html(response.errorText);
								}
							}
						});				
					}
				}
			});	
		}			
	});
	
	//验证码验证
	$("#checkRealNumber").click(function(){
		if ($("#validateNumber").val() == "") {
			$("#validateNumberTip").html("手机绑定码不能为空！");
			return false;
		}
		$.ajax({
			url:"/nsso/ajax/validateMobileAuthenticationCode.do", 
			type:"post", 
			dataType:"json", 
			data:{
			    mobile : $("#mobileNumber").val(),
				authenticationCode : $("#validateNumber").val(),
				userId : $("#userId").val()
			},
			success:function (response) {				
				if (response.success == true) {
					alert('手机验证成功!');
					window.location.reload();
				} else {
					alert(response.errorText);
				}
			}
		});			
	});
});


function checkMail(){
	var mail = $("#mail").val();

	if (!checkNull(mail)) {
			$("#mailTip").html("邮件地址错误");
			return false;
		}
	if (checkLength(mail, 4)) {
			$("#mailTip").html("邮件地址错误");
			return false;
	}
	if (!checkFormat("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$", mail)) {
			$("#mailTip").html("邮件地址错误");
			return false;
	}
	$("#mailTip").html("邮件地址正确");
	$.ajax({
			url:"/nsso/ajax/checkUniqueField.do", 
			type:"post", dataType:"json", 
			data: {
				email : $("#mail").val()
			}, 
			success:function (response){					
				if (response.success == false) {
					$("#mailTip").html("邮箱已存在");	
				} else {
					$.ajax({
						url:"/nsso/ajax/sendAuthenticationCode.do", 
						type:"post", 
						dataType:"json", 
						data:{
							mobileOrEMail:$("#mail").val(),
							userId : $("#userId").val()
						}, 
						success:function (response) {
							if (response.success == true) {
								$("#successInfo").html("<div class='success'>邮件发送已成功！请按邮件内容认证邮箱并刷新此页面</div>");
							} else {					
								$("#mailTip").html("发送邮件失败");
							}
						}
					});
				}
	}});
}
	  
function update(num) { 
	if(num == secs) { 	
		var msgButs = document.getElementById('msgButs');
		msgButs.value ="发送手机绑定码"; 
		msgButs.disabled=false; 
	}else { 
		printnr = secs-num; 
		document.getElementById('msgButs').value = "( " + printnr +"发送消息)"; 
	} 
} 

function checkLength(input, lengthStr) {
	if (input.length > lengthStr) {
		return false;
	}
	return true;
}
function checkFormat(format, input) {
	var myReg = new RegExp(format);
	return myReg.test(input);
}
function checkNull(input) {
	if (input == "") {
		return false;
	}
	return true;
}

function info(){
	$("#mailTip").html("请输入邮件地址");
}

