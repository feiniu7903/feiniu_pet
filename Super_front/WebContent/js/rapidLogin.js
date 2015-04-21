			 var MOBILE_REGX = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
	 		 var SERVER_NAME = "http://login.lvmama.com";
	 		 
			/**
			 * 输入手机的注册文本框的onblur事件
			 */
			function mobileRegisterOnBlur(fieldId, successId, errorId){
				$("#" + successId).html('');
				$("#" + errorId).html('');
				$("#loinTextErrorTip").empty();
				$("#sso_mobileAndEmail").val('');
				$("#sso_password").val('');
				var mobile = $("#" + fieldId).val();
				$("#" + fieldId).data("valid","false");
				if (MOBILE_REGX.test($("#" + fieldId).val())) {
					$.ajaxSettings.async = false;
					$.getJSON(
						SERVER_NAME + "/nsso/ajax/checkUniqueField.do?jsoncallback=?",
						{
							mobile: $("#" + fieldId).val()	
						},
						function(response) {
							if (response.success == false) {
								$("#" + errorId).html("<font color='red'>该手机已是驴妈妈会员,请在右侧登录</font>");								
								$("#sso_mobileAndEmail").val(mobile);
								$("#" + fieldId).data("valid","false");
								return false;
							} else {
								$("#" + successId).html("<img src=http://login.lvmama.com/nsso/images/input_ok.gif>");
								$("#" + fieldId).data("valid","true");
								return true;
							}
						}
					);
					$.ajaxSettings.async = true;
				} else {
					$("#" + errorId).html('<font color=\'red\'>请输入正确的手机号</font>');
					return false;
				}
				return true;
			}
		
		    /**
			 * 手机的快速注册事件
			 */
			 function rapidRegister() {
				//mobileRegisterOnBlur("mobileLoginText","mobileLoinTextSuccessTip","mobileLoinTextErrorTip");
				//alert($("#mobileLoginText").data("valid"));
				if ($("#mobileLoginText").data("valid") == "true") {
					$.getJSON(
						SERVER_NAME + "/nsso/ajax/silentRegisterLoginByMobile.do?jsoncallback=?",
						{
							mobile: $("#mobileLoginText").val()
						},
						function(response) {
							if (response.success == false) {
							//	$("#mobileLoinTextErrorTip" ).html("<font color='red'>手机注册失败,请重新尝试!</font>");
							} else {
								$(window.parent.document).find(".bgLogin,.LoginAndReg, #loginDIV").hide();
								loginFormSubmit();
							}
						}
					);
				} else {
					if($("#mobileLoinTextErrorTip").html()==null || $("#mobileLoinTextErrorTip").html()=='')
						{
						$("#mobileLoinTextErrorTip").html('<font color=\'red\'>请输入正确的手机号</font>');
						}
					
				}		 
			 }
			 
			 /**
			  * 用户登录
			  **/
			  function login() {
				$("#mobileLoginText").val('');
				$("#mobileLoinTextErrorTip").empty();
				$("#mobileLoinTextSuccessTip").empty();
			  	var mobileOrEMail=$('#sso_mobileAndEmail').val();		
				if(mobileOrEMail==''){
					$("#loinTextErrorTip").html("<font color='red'>请输入邮箱/手机号/用户名/会员卡</font>");
					$('#sso_mobileAndEmail').focus();	
					return false;
				};
				
				var password = $('#sso_password').val(); 
				if(password==''){
					$("#loinTextErrorTip").html("<font color='red'>请输入密码</font>");
					$('#sso_password').focus();	
					return false;
				};
				
				var verifycode = $('#sso_verifycode1').val(); 
				if(verifycode==''){
					$("#loinTextErrorTip").html("<font color='red'>请输入验证码</font>");
					$('#sso_verifycode1').focus();	
					return false;
				};
				
				$.getJSON(
					"http://login.lvmama.com/nsso/ajax/login.do?jsoncallback=?",
					{
						mobileOrEMail : mobileOrEMail,
						password	  : password,
						verifycode    : verifycode
					},
					function(data) {	   
						if (data.success) {
							$(window.parent.document).find(".bgLogin,.LoginAndReg, #loginDIV").hide();
							loginFormSubmit();
						} else {
							$("#loinTextErrorTip").html("<font color='red'>"+data.errorText+"</font>");	
						}
					}
				);								
			  }
			  
			function tipsWindow(obj,screenBg){
				$(window.parent.document).find(".bgLogin,.LoginAndReg, #loginDIV").hide();
				var windowLayer = $("#windowLayer").html();
				if(windowLayer!="" || windowLayer!=null){
				  $("body").append(windowLayer);
				  $("#windowLayer").remove();
				}
				
				$("#"+obj).fadeIn("fast");
				$("#"+screenBg).css({"display":"block","opacity":"0.6"});
				screenAlign();
					
				function screenAlign(){
					var leftAlign=($(window).width()-$("#"+obj).outerWidth())/2;
					var topAlign=($(window).height()-$("#"+obj).outerHeight())/2+$(window).scrollTop();
					$("#"+obj).css({"left":leftAlign,"top":topAlign});
					}
			}
			
			function closeMsg(obj,screenBg){
				$("#"+obj).fadeOut("fast");
				$("#"+screenBg).fadeOut("fast");
			}		
			
			//登录状态检查
			function completeLogin()
			{
				$.post("/check/login.do",null,function(dt){
					var result=eval("("+dt+")");
					if(result)
					{
						loginFormSubmit();
					}else//未成功
					{
						alert("您还没有登录成功");
					}
				});
			}
			
			/*针对密码框回车直接登录操作*/
			$("#sso_password").keyup(function(e){
					if(e.keyCode==13)
					{
						//判断是否值都填写
						if($("#sso_mobileAndEmail").val()!="" && $("#sso_password").val()!="")
						{
							login();
						}
					}
				});
			function refreshCheckCode(s) {
			    var elt = document.getElementById(s);
			    elt.src = elt.src + "?_=" + (new Date).getTime();
			}
