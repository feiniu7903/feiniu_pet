$(function(){
	$('#cardsH3CloseBtn').click(function(){
		if($(this).hasClass('h3CloseB')){
			$(this).removeClass('h3CloseB');
			$('#cardsPalmentTextDiv').hide();
		}else{
			$(this).addClass('h3CloseB');
			$('#cardsPalmentTextDiv').show();
		}								
	});
	$('.button01,.button02').click(function(){
		var checkType = $('input[name=cardTypeCheck]:checked').val();
		var userCardNo;
		var userCardpassword;
		var canNotToPay = false;
		if('cardNoOldRadio'==checkType){
			userCardNo=$("#cardNoOld").val();
			if(userCardNo==""){
				alert("储值卡卡号不能为空，请填写！"); 	
				canNotToPay = true;
				return;	
			}
		}
		if('cardNoNewRadio'==checkType){
			userCardNo = $("#cardNoNew").val();
			userCardpassword = $("#cardPassword").val();
			if(userCardNo==""||userCardpassword==""){
				alert("储值卡卡号，密码不能为空，请填写！"); 
				canNotToPay = true;
				return;
			}	
		}
		var userVerifycode=$("#verifycode").val(); 
		if(userVerifycode==""){
			 alert("验证码不能为空 请填写！"); 		
			 canNotToPay = true;
			 return;	 
		}
        
		
		if(canNotToPay){
			return;
		}else{
			if('cardNoNewRadio'==checkType){
				$.post("/ajax/chackNewCardNo.do",{"cardNo":userCardNo},function(dt){
					var data=eval("("+dt+")");
					if(data.success){	
						var isBoundLipinka = $("#isBoundLipinka").val();
						var boundLipinkaUsable = $("#boundLipinkaUsable").val();
						if(isBoundLipinka!="1" || boundLipinkaUsable!="1") {
							var userCardNo = $("#cardNoNew").val();
							var userCardpassword = $("#cardPassword").val();
							$.post("/ajax/chackCodeNoPassword.do",{"cardNo":userCardNo,"cardPassword":userCardpassword},function(dt){
								var data=eval("("+dt+")");
								if(data.success){	
									$('.msgDiv01,.bgDiv').show();	
								}else{
									alert(data.msg);
									return;
								}
							});	
						}else {//已绑定礼品卡
							//做手机验证码的判断
							$.post("/ajax/mobileCodeValidate.do",{"userNo":$("#stc_userNo").val(),"mobileVerifyCode":$("#cardMobileCode").val()},function(dt){
								var data=eval("("+dt+")");
								if(data.success){	
									$('.msgDiv01,.bgDiv').show();
								}else{
									alert(data.msg);
								}
							});
						}
					}else{
						alert(data.msg);
					}
				});
			}else{
				$.post("/ajax/chackOldCardNo.do",{"cardNo":userCardNo},function(dt){
					var data=eval("("+dt+")");
					if(data.success){	
						$('.msgDiv01,.bgDiv').show();
					}else{
						alert(data.msg);
					}
				});
			}  
		}
   
	
	});
	$('.newbutton02A').click(function(){
				$('.msgDiv01').hide();
				$('.msgDiv02,.bgDiv').show();
	});
	$('.close,.newbutton02B,.newbutton01A').click(function(){
				$('.msgDiv,.bgDiv').hide();
	});	
	/************************************************************/
	   
	$('.DepositTop  > li:last').css('border','none');
	$('.paymentOrderTit  > td:last').css('border-left','none');
	$('.deposith3Close').click(function(){
		if($(this).hasClass('h3CloseB')){
			$(this).removeClass('h3CloseB');
			$('#cashAccountDetail').hide();
		}else{
			$(this).addClass('h3CloseB');
			$('#cashAccountDetail').show();
		}			
	});
	$('.settingButtonB').click(function(){
		var closeThis = $(this).attr('data-biaoshi');
		var settingButtonA = $(this).attr('data-biaoshi2');
		$('.'+closeThis).hide();
		$('.'+settingButtonA).show();		
		$('.bgDiv').show();	
	});
	$('.close,.settingButtonA,.settingButtonC').click(function(){
		var closeThis = $(this).attr('data-biaoshi');
		$('.'+closeThis).hide();
		$('.bgDiv').hide();
	});
});
