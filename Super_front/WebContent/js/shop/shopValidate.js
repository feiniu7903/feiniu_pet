$(document).ready(function () {
	 $("#send-verifycode").click(function(){
		    $.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail="+$(this).attr("mobile")+"&jsoncallback=?",function(json){
		    	if (json.success) {
			    	$('#send-verifycode').hide();
			    	$('.xhinfo2').hide();
			    	$('.xhinfo3').hide();
			    	$('.xhinfo4').hide();
			    	$("#JS_countdown").show();
			        JS_countdown(".J_num");
		    	}else{
		    		if(json.errorText == 'phoneWarning'){
		    			$('.xhinfo2').show();
				    	$('.xhinfo3').hide();
				    	$('.xhinfo4').hide();
		    		}else if(json.errorText == 'ipLimit'){
		    			$('.xhinfo2').hide();
				    	$('.xhinfo3').show();
				    	$('.xhinfo4').hide();
					}else if(json.errorText == 'waiting'){
						$('.xhinfo2').hide();
				    	$('.xhinfo3').hide();
				    	$('.xhinfo4').show();
					}else{
						$("#xhinfo2").html(json.errorText);
						$("#xhinfo2").show();
					}
		    	}
		    });
	    });
	    function JS_countdown(_cdbox){
	    /*-------------------------------------------*/
	    var _InterValObj; //timer变量，控制时间
	    var _count = 60; //间隔函数，1秒执行
	    var _curCount;//当前剩余秒数
	    sendMessage(_count);
	    function sendMessage(_count){
	        _curCount = _count;
	            $(_cdbox).html(_curCount);
	            _InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
	        }
	    //timer处理函数
	    function SetRemainTime() {
	        if (_curCount == 0) {
	            window.clearInterval(_InterValObj);//停止计时器
	            var expr = _cdbox.indexOf("-old")>0?"-old":"";
	            $("#JS_countdown"+expr).find(".xhtipinfo").html("校验码已发送成功，以最近发送的校验码为准").end().hide();
	            $("#send-verifycode"+expr).html("重新发送验证码").show();
	        }else {
	            _curCount--;
	            $(_cdbox).html(_curCount);
	        }
	    }
	};
});