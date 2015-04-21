$(document).ready(function () {
//手机号码验证
	$("#sendSms").click(function () {
		var userId = $("#userid").val();
		var hasBinded = $("#hasBinded").val();
		var moneyAccountMobile = $("#moneyAccountMobile").val();
		$.ajax({
			url:"/super_back/moneyAccount/sendSms.do",
			type:"post", 
			dataType:"json",
			data:{userId:userId,moneyAccountMobile:moneyAccountMobile,hasBinded:hasBinded},
			success:function (data) {
				if(data.sendSmsSuccess){
					alert("成功发送动态支付密码！");
					return;
				} else {
					alert("发送动态支付密码失败！");
					return;
				}
		}});
	});
});