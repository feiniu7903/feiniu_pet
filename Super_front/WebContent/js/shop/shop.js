$(function(){
			var allNum = 0;
			var i, j;
			var indexNum = $(".Slides li:last-child").index();
			$(".Slides li:first-child").fadeIn(1000); 
			$(".SlideTriggers").remove(); 
			$(".Slides").after("<ul class='SlideTriggers'><li class='Current'>1</li></ul>");
			for (i = 1; i <= indexNum; i++)
			{
				j = i+1;
				$(".SlideTriggers").append("<li>"+j+"</li>")
			}	
			$(".SlideTriggers li").mouseover(function(){
				var num = $(this).index();
				allNum = num;
				$(this).addClass("Current").siblings().removeClass("Current");
				$(".Slides li").eq(num).fadeIn(800).siblings().hide();
			});	
			function imgScroll(){
				$(".SlideTriggers li").eq(allNum).addClass("Current").siblings().removeClass("Current");
				$(".Slides li").eq(allNum).fadeIn(1000).siblings().hide();
				allNum += 1;
				if(allNum>indexNum) allNum=0;
			}			
			var anima = setInterval(imgScroll,3000);			
			$("#DestpicFlow").hover(
			  function () {
			  	clearInterval(anima);
			  },
			  function () {
			  	anima = setInterval(imgScroll,3000);
			  }
			); 


	$('.hotText dt').mouseover(function(){
		var biaoshi=$(this).parent();	
		$('.hotText').removeClass('hotTextShow');
		$(biaoshi).addClass('hotTextShow');
	});	
	
	$('#account').focus(function(){
		if ($('#account').val() == '用户名/手机号/Email/会员卡') {
			$('#account').val('');
		}
	});

	$('#account').blur(function(){
		if ($('#account').val() == "") {
			$('#account').val('用户名/手机号/Email/会员卡');
		}
	});

	$('#account_2').focus(function(){
		if ($('#account_2').val() == '用户名/手机号/Email/会员卡') {
			$('#account_2').val('');
		}
	});

	$('#account_2').blur(function(){
		if ($('#account_2').val() == "") {
			$('#account_2').val('用户名/手机号/Email/会员卡');
		}
	});

	$('#button01').click(function(){
		if ($('#account').val() == "" || $('#account').val() == "用户名/手机号/Email/会员卡") {
			alert('请输入用户名');
			return;
		}
		if ($('#password').val() == "") {
			alert('请输入密码');
			return;
		}
		$.getJSON("http://login.lvmama.com/nsso/ajax/login.do?mobileOrEMail=" + $('#account').val()  + "&password=" + $('#password').val() + "&jsoncallback=?" ,function (data){ 
			if (data.success) {
				window.location.reload();
			} else {
				alert("用户名密码出错，请重新登录");
			}
		}); 
	});


	$('#button01_2').click(function(){
		if ($('#account_2').val() == "" || $('#account_2').val() == "用户名/手机号/Email/会员卡") {
			alert('请输入用户名');
			return;
		}
		if ($('#password_2').val() == "") {
			alert('请输入密码');
			return;
		}
		$.getJSON("http://login.lvmama.com/nsso/ajax/login.do?mobileOrEMail=" + $('#account_2').val()  + "&password=" + $('#password_2').val() + "&jsoncallback=?" ,function (data){ 
			if (data.success) {
				window.location.href = "/shop/initOrder.do?productId=" + $('#productId').val();
			} else {
				alert("用户名密码出错，请重新登录");
			}
		}); 
	});
})
