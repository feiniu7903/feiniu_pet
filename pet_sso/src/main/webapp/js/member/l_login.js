(function($){
	function validate_submit(){
		var txtUser = $("#loginName");
		var txtPwd = $("#password");
		if(!validate_input(txtUser[0]) ){
			return false;
		}
		if(!validate_input(txtPwd[0]) ){
			return false;
		}
		if($("#sso_verifycode1").length > 0 ){
			if( !validate_input($("#sso_verifycode1")[0]) ){
				return false;
			}
		}
		return true;
	}
	function validate_input(obj){
		var tip = $("#login_error_tip");
		if($.trim(obj.value)==''){
			var err_info = $(obj).nextAll("p").html();
			if(err_info.indexOf("请输入") == -1 ){
				err_info = "请输入" + err_info;
			}
			$(obj).focus().css("border","1px solid red");
			tip.html('<i class="login_sp login_v_error"></i>'+err_info).show();
			if(obj.index==0){ //如果等于用户名时才验证
				$(obj).next().hide();
			}
			return false;
		}else{
			$(obj).css("border","1px solid #CECECE");
			tip.hide();
			return true;
		}
	}
	function error_tip(selector,msg){
		var obj = $(selector)[0];
		var tip = $("#login_error_tip");
		$(obj).focus().css("border","1px solid red");
		tip.html('<i class="login_sp login_v_error"></i>'+msg).show();
		$(obj).next().hide();
	}
	function resizeY(){
		$(window).resize(function(){
			var nBodyHeight = 620;
			var nClientHeight = document.documentElement.clientHeight;
			if(nClientHeight >= nBodyHeight + 2){
				var nDis = (nClientHeight - nBodyHeight)/2;
				document.body.style.paddingTop = nDis + 'px';
			}else{
				document.body.style.paddingTop = '0px';
			}

		}).resize();
	}
	function init(){
		//$("#allIframe").append("<div id='pixed1' style='display:none;width:970px;height:415px;position:absolute;left:0px;top:47px;z-index:1;background-color:#fff;opacity:0;filter:alpha(Opacity=0)'></div>");
		
		resizeY();
		$("#loginName").val($getCookie("r_username")).keyup();
		if($getCookie("r_username")){
			$("#password").val("").focus()
		}else{
			$("#loginName").focus();
		} 
		var slideitems = $("#allIframe").find(".slideimg");
		slideitems.first().show().siblings(".slideimg").hide();
		var oneI = slideitems.eq(0);
		var twoI = slideitems.eq(1);
		var threeI = slideitems.eq(2);
		$("a.login_bg_next").click(function(){
			var m = $(this).prev();
			var num = parseInt(m.find("i").html());
			var slideitem = $("#allIframe").find(".slideimg");
			slideitem.filter(":visible").hide();
			if(num>=3){
				num = 0;
			}
			num++;
			slideitem.eq(num-1).show();
			/*if(num==1 || num==2){
				$("#pixed1").hide();
			}else{
				$("#pixed1").show();
			}*/
			m.find("i").html(num);
			//$(this).addClass("login_bg_next_active").prev().prev().removeClass("login_bg_prev_active");
		});
		$("a.login_bg_prev").click(function(){
			var m = $(this).next();
			var num = parseInt(m.find("i").html());
			var slideitem = $("#allIframe").find(".slideimg");
			slideitem.filter(":visible").hide();
			if(num<=1){
				num = 4;
			}
			num--;
			slideitem.eq(num-1).show();
			if(num==3){
				$("#pixed1").hide();
			}else{
				$("#pixed1").show();
			}
			m.find("i").html(num);
			//$(this).addClass("login_bg_prev_active").next().next().removeClass("login_bg_next_active");
		});
		//帐号密码，框内灰色文字提示
		$("#loginName,#password,#sso_verifycode1").focus(function(){
			if(this.value==''){
				$(this).siblings().filter(".login_input_info").show().css("color","#dedede");
			}
			if(this.style.border!="1px solid red"){
				$(this).addClass("login_input_color");
			}
		}).blur(function(){
			if(this.value==''){
				$(this).siblings().filter(".login_input_info").show().css("color","");
				this.isFocus = false;
			}
			$(this).removeClass("login_input_color");
		}).keydown(function(){
			if(!this.isFocus){
				$(this).siblings().filter(".login_input_info").hide();
				this.isFocus = true;
			}
		}).change(function(){
			validate_input(this);
		}).each(function(i){
			this.index = i;
			if(this.value!=''){
				$(this).siblings().filter(".login_input_info").hide();
			}

		});
		if($("#login_error_tip").length==0){
			$("#loginName").focus();
		}
		$("#password").keyup(function(e){
			if(e.keyCode==13){
				$("#loginBtn").click();
			}
		});
		$("p.login_input_info").click(function(){
			$(this).css("color","").siblings().first().focus();
		});
		$("#loginBtn").replaceWith('<span id="loginBtn" class="login_sp login_submit"></span>');
		$("#loginBtn").click(function(){
			addCookie("r_username",$("#loginName").val());
			if(validate_submit()){
				window.loginSubmit && window.loginSubmit();
			}
		});
		var autoLi = $("#login_autoComplete li:not(.login_auto_title)");
		autoLi.mouseover(function(){
			$(this).siblings().filter(".hover").removeClass("hover");
			$(this).addClass("hover");
		}).mouseout(function(){
			$(this).removeClass("hover");
		}).mousedown(function(){
			$("#loginName").val($(this).text()).change();
			$(this).parent().parent().hide();
			
		});
		$("#password").keyup(function(e){
			if(/13|38|40/.test(e.keyCode) || this.value==''){
				return false;
			}
		});
		//用户名补全+翻动
		$("#loginName").keyup(function(e){
			if(/13|38|40|116|9/.test(e.keyCode) || this.value==''){
				return;
			}
			var username = this.value;
			if(username.indexOf("@")==-1){
				$("#login_autoComplete").hide();
				return false;
			}
			autoLi.each(function(){
				this.innerHTML = username.replace(/\@+.*/,"")+$(this).attr("hz");
				if(this.innerHTML.indexOf(username)>=0){
					$(this).show();
				}else{
					$(this).hide();	
				}
			}).filter(".hover").removeClass("hover");
			$("#login_autoComplete").show();
			if(autoLi.filter(":visible").length==0){
				$("#login_autoComplete").hide();
			}else{
				autoLi.filter(":visible").eq(0).addClass("hover");			
			}
		}).blur(function(){
			$("#login_autoComplete").hide();
		}).keydown(function(e){
			if(e.keyCode==38){ //上
				autoLi.filter(".hover").prev().not(".login_auto_title").addClass("hover").next().removeClass("hover");
			}else if(e.keyCode==40){ //下
				autoLi.filter(".hover").next().addClass("hover").prev().removeClass("hover");
			}else if(e.keyCode==13){ //Enter
				autoLi.filter(".hover").mousedown();
			}
		});
	}
	$(function(){
		init();
		setInterval(function() {
			if ($("#loginName").val() != "") {
				$("#loginName").siblings().filter(".login_input_info").hide();
			};
			if ($("#password").val() != "") {
				$("#password").siblings().filter(".login_input_info").hide();
			};
		},200);
	});
	window.error_tip = error_tip;
})(jQuery);
function $getCookie(objName) {
	var arrStr = document.cookie.split("; ");
	for (var i = 0; i < arrStr.length; i++) {
		var temp = arrStr[i].split("=");
		if (temp[0] == objName) {
			return decodeURI(temp[1]);
		}
	}
}
function delCookie(name) {
	var date = new Date;
	date.setTime(date.getTime() - 10000);
	document.cookie = name + "=a; expires=" + date.toGMTString();
}
function addCookie(objName, objValue, objHours) {
	var str = objName + "=" + encodeURI(objValue);
	if (objHours > 0) {
		var date = new Date;
		var ms = objHours * 3600 * 1000;
		date.setTime(date.getTime() + ms);
		str += "; expires=" + date.toGMTString();
	}
	document.cookie = str;
}