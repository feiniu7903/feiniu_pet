
function closePop() {
	$("#floatbox-background").remove();
	$("#floatbox-box").remove();
}
function canel(str, itemCode, otherReason) {
	var data = "orderId=" + str;
	if (itemCode!=null) {
		data = data + "&itemCode=" + itemCode;
	}
	if (otherReason!=null) {
		data = data + "&reason=" + otherReason;
	}
	$.ajax({
		type:"POST", 
		url:encodeURI("/myspace/ordercancel.do?" + data,'UTF-8'),
		async:false, 
		dataType:"json", 
		success:function (json) {
			if (json.status == "true") {
				if(json.supplierChannel=="true"){
					$(".cancel_closed").html("取消申请中.");
				}else{
					$(".cancel_closed").html("您的订单已取消");
				}
				window.setTimeout("window.location.reload()", 5000);
			} else {
				alert("\u53d6\u6d88\u8ba2\u5355\u9519\u8bef\uff01");
			}
		}
	});
}
function pops(str) {
	$.pop({content:"<div class='order_cancel'><p>\u4f60\u786e\u8ba4\u8981\u53d6\u6d88\u8ba2\u5355" + str + "\uff1f</p><input name='' type='button' value='\u662f' onclick=canel('" + str + "') /><input name='' type='button' onclick='javascript:closePop();' value='\u5426'/></div>", boxConfig:{width:"280px", marginLeft:"-140px"}});
}

function sendSms(obj,id,mobile){
	$("#resendmsg").attr("disabled","true");
	$("#resendmsg").removeClass("sm-gray-btn");
	$("#reorderheadId").attr("value",id);
	$("#sendmobile").attr("value",mobile);
	subSendSms();
}
function subSendSms() {
		var float_layer = $("#float_layer");
		var float_bg = $("#float_bg");
		var close_btn = $("#close_btn");
		layer_xy(float_layer);
		float_layer.fadeIn("fast");
		var close_btn = $("#close_btn");
		close_btn.click(function(){
			float_bg.css({"display":""});
			float_layer.fadeOut("fast");
		 })
		runtime();
	}
function layer_xy(obj){
	 var windows = $(window);
	 var ctop = (windows.height() - obj.height())/2;
	 var cleft = (windows.width() - obj.width())/2;
	 if(ctop<=0){ctop = 0 + windows.scrollTop()}else{ctop=parseInt(ctop + windows.scrollTop())};
	 if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
	 obj.css({"top":ctop + "px","left":cleft + "px"});
}
var maxtime = -1;
var timer=null;
function resendmsg(){
	maxtime=60;
	var orderheadid=$("#reorderheadId").val();
	var mobile = $("#sendmobile").val();
	var dataMember = {'orderHeadId':orderheadid,'mobileNumber':mobile};
	timer = setInterval("runtime()",1000);
	$.ajax({
		type:"POST", 
		url:"/myspace/resendMsg.do", 
		async:false, 
		data:dataMember, 
		dataType:"json", 
		success:function (data) {}
	});
}

function runtime(){
	if(maxtime>0){
		$("#resendmsg").attr("disabled","true");
		$("#resendmsg").addClass("sm-gray-btn");  
	}else{
		$("#resendmsg").removeAttr("disabled");
		$("#resendmsg").removeClass("sm-gray-btn");
	}
	if(maxtime>=0){   
		seconds = Math.floor(maxtime%60);   
		msg ="重发倒计时："+seconds;   
		document.getElementById("timer").innerHTML=msg;  
		--maxtime;
	}else{   
		clearInterval(timer);  
	}   
}
