 $(function(){
	$("#y_input").focus(function(){
		$("#y_txt_info").hide();
		$(this).css("background","#ffffff");
		$("#y_error_msg").hide();
	}).blur(function(){
		if(this.value==""){
			$("#y_txt_info").show();
			$(this).css("background","transparent");
		}
	});
	$("#y_txt_info").click(function(){
		$("#y_input").focus();
	});
	$("#yjdy_btn").click(function(){
		var input = $("#y_input");
		var error_msg = $("#y_error_msg.y_error_msg");
		var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		var _input_left = input.offset().left;
		var _input_top = input.offset().top + input.height() + 8;
		if(!reg.test(input.val())){
			input.blur();
			error_msg.css({left : _input_left,top : _input_top,display:"inline-block"}).fadeIn(300).find("label").html("请输入正确的Email地址").css("display","inline-block");
		}else{
			$(this).parent().addClass("dn");
			var two = $("p.y_s_two").removeClass("dn");
			two.html(two.html());
			var subscribe ="({";
			subscribe+='\"isUpdate\":\"true\",';
	 		subscribe+='\"email\":\"'+ input.val()+'\",';
	 		subscribe+='\"subscribe.email\":\"'+  input.val()+'\",';
	 		subscribe+='\"subscribe.channel\":\"FAST\",';
	 		var type = new Array();
	 		type.push("MARKETING_EMAIL");
	 		type.push("PRODUCT_EMAIL");
		 	subscribe+="\"regEdmType\":\""+type+'\"';
	 		subscribe +="})";
	 		subscribe = eval(subscribe);
	 		$.ajax({
	 			type:"POST",
	 			async:true,
	 			url:"/edm/checkEmailIsSubscribe.do",
	 			data:{"subscribe.email":input.val(),"isUpdate":"true"},
	 			dataType:"json", 
				success:function (data) {
					if(data.success!=true && data.success!="true"){
						var error = data.errorText;
					 	if(error=="A"){
					 		error = "请填写邮箱后订阅";
					 	}else if(error=="B"){
					 		error = "请填写正确的邮箱";
					 	}else if(error=="C"){
					 		error = "您已经订阅过该邮件了";
					 	}else if(error=="D"){
					 		error = "请选择订阅邮件类型";
					 	}else if(error=="F"){
					 		error = "订阅失败，请稍后重试";
					 	}
					 	input.blur();
						error_msg.css({left : _input_left,top : _input_top,display:"inline-block"}).fadeIn(300).find("label").html(error).css("display","inline-block");
					 	$("p.y_s_one").removeClass("dn");
						$("p.y_s_two,p.y_s_three").addClass("dn");
					}else{
						$.ajax({
							type:"POST",
							async:true,
							url:"/edm/subscribeEmail.do",
							data:subscribe,
							dataType:"json", 
							success:function (data) {
								 if(data.success!=true && data.success!="true"){
								 	var error = data.errorText;
								 	if(error=="A"){
								 		error = "请填写邮箱后订阅";
								 	}else if(error=="B"){
								 		error = "请填写正确的邮箱";
								 	}else if(error=="C"){
								 		error = "您已经订阅过该邮件了";
								 	}else if(error=="D"){
								 		error = "请选择订阅邮件类型";
								 	}else if(error=="F"){
								 		error = "订阅失败，请稍后重试";
								 	}
								 	input.blur();
									error_msg.css({left : _input_left,top : _input_top,display:"inline-block"}).fadeIn(300).find("label").html(error).css("display","inline-block");
								 	$("p.y_s_one").removeClass("dn");
									$("p.y_s_two,p.y_s_three").addClass("dn");
								 }else{
										setTimeout(function(){
											$("p.y_s_two").addClass("dn");
											$("p.y_s_three").removeClass("dn");
										},1000);
								 }
							}});
					}
				}
	 		});
		}
	});
	$("#y_link_return").click(function(){
		$("p.y_s_one").removeClass("dn");
		$("p.y_s_two,p.y_s_three").addClass("dn");
	});
}); 