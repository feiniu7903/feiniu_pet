//yuzhibing
var pageValidatro;//保存的验证码
var sm = 60;//默认倒数时间
var timein = 0;//计时状态
var flagValidatro = 0;
var secs = 60;//计时
function showDetail() { 
	//重置
	$("#successInfo3").show();
	$("#successInfo2").hide();
	//背景
	var bgObj = document.getElementById("bgDiv");
	bgObj.style.width = document.body.offsetWidth + "px";
	bgObj.style.height = document.body.offsetHeight+ "px";
	//定义窗口
	var msgObj = document.getElementById("msgDiv");
	//msgObj.style.marginTop = -75 + document.documentElement.scrollTop + "px";
		
	//关闭
	if(document.getElementById("msgShut") != null){
		document.getElementById("msgShut").onclick = function () {
			bgObj.style.display = msgObj.style.display = "none";
		};
	}
	msgObj.style.display = bgObj.style.display = "block";
}

$(document).ready(function () {		
//手机号码验证
	$("#msgButs").click(function () {
	//$(this).val('再次发送');
	if (!checkFormat("^[0-9]{11}$", $("#mobileNumber").val())) {
		$("#mobileNumberTip").html("填写正确手机号");
	}else{
	var checkNumber = {mobileNumber:$("#mobileNumber").val(),ma:$("#ma").val()};
			$.ajax({url:"/main/ajax/ajax!checkMobileNumber.do", type:"post", dataType:"json", data:checkNumber, success:function (data) {
				pageValidatro = data.isMobileNum;
				if (data.isMobileNum) {
					$("#mobileNumberTip").html("");
					var phone = {mobileNumber:$("#mobileNumber").val(),valDate:$("#valDate").val()};
					send(phone);
					return true;					
				}else{
					$("#mobileNumberTip").html("手机号码已存在");
					return false;
				}
			}});
	
	}
			
		});
	
	function send(phone){
		$.ajax({url:"/main/ajax/ajax!sendSms.do", type:"post", dataType:"json", data:phone, success:function (data) {
				var pv = data.checkNumber;
				if (pv=="error") {
					$("#mobileNumberTip").html("验证码错误");
					return false;
				}else if(pv != "" && pv != null){
					noteTime();
					pageValidatro=pv;
					$("#mobileNumberTip").html("手机绑定码发送成功");
					document.getElementById("image2").src = "/account/checkcode.htm?now=" + new Date();
					return true;
				} else {
					$("#mobileNumberTip").html("\u9a8c\u8bc1\u7801\u53d1\u9001\u5931\u8d25,\u8bf7\u91cd\u53d1");
					return false;
				}
			}});
	}
	//验证码验证
	$("#checkRealNumber").click(function(){
		if ($("#validateNumber").val() == "") {
			$("#validateNumberTip").html("手机绑定码不能为空！");
			return false;
		}
		if ($("#validateNumber").val() == pageValidatro) {
			flagValidatro = 1;
			sm = 1;
			var checkNumber = {mobileNumber:$("#mobileNumber").val(), checkNumber:$("#validateNumber").val(), ma:$("#ma").val(), binding:$("#binding").val()};
				$.ajax({
					url:"/main/ajax/ajax!checkRealName.do", 
					type:"post", 
					dataType:"json", 
					data:checkNumber, 
					success:function (data) {
						if (data.bool == "true") {
							if(data.binding == "true"){
								window.parent.location.href="/member/moneyAccount";
							}else{
								var telNo = $("#mobileNumber").val();
								$("#msgTel").html(telNo);
								$("#successInfo3").hide();
								$("#successInfo2").show();
							}
							return true;
						} else {
							$("#info").html("\u5b9e\u540d\u8ba4\u8bc1\u5931\u8d25");
							document.getElementById("msgButs").disabled = false;
							document.getElementById("mobileNumber").disabled = false;
							pageValidatro = "";
						}
					}
				});
			
			return true;
		} else {
			$("#validateNumberTip").html("手机绑定码错误！");
			return false;
		}
	});
});

function update(num) { 
	if(num == secs) { 	
		var msgButs = document.getElementById('msgButs');
		msgButs.value ="再次发送"; 
		msgButs.disabled=false; 
	}else { 
		printnr = secs-num; 
		document.getElementById('msgButs').value = "( " + printnr +"发送消息)"; 
	} 
} 

function noteTime(){
		 var event=document.getElementById('msgButs');
		 event.disabled=true; //将按钮设为失效
		 butt_info=1;
		 for(i=1;i<=secs;i++) { 
		  	window.setTimeout("update(" + i + ")", i * 1000);	  	
		 } 
	}
//格式校验：如手机号	  
function checkFormat(format, input) {
	var myReg = new RegExp(format);
	return myReg.test(input);
}
//储值卡 换一张验证码
function refreshw(id) {
    document.getElementById(id).src = "/account/checkcode.htm?&now=" + new Date()
};
//存款账户换一张验证码 
function refreshCode(id) { 
    document.getElementById(id).src = "/account/checkcode.htm?&now=" + new Date() 
};
