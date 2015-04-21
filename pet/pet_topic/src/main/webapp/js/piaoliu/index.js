//JavaScript Documentspzhe

$('.hdgzBtn1').click(function(){
	$('.tcc_mod1').show();
	$('.tcc_mod2').hide();
	tanchu(50);
})
$('.hdgzBtn2').click(function(){
	$('.tcc_mod2').show();
	$('.tcc_mod1').hide();
	tanchu(100);
})
$('.close_Btn').click(function(){
	guanbi();
})
function tanchu(_height){ 
	var _scrolltop = $(document).scrollTop()+_height;
	var height_w =$(document).height();
	var width = $('.PopBox').width();
    var height = $('.PopBox').height();
	$('.PopBox').css({'margin-left':-width/2});
	$('.pop_body_bg').css({'height':height_w,'width':$(document.body).width()}).show();
	$('.PopBox').show().css({'top':_scrolltop});
};
function guanbi(){ 
	$('.pop_body_bg,.PopBox').hide();
};

var time_now, time_end, timerID, serverDate;

$(function () {
    init();
});


function init() {
	//获取服务器时间戳
	$.ajax({
		url : "http://www.lvmama.com/piaoliu/ajaxgetDate.do",
		type : 'get',
		async : false,
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsoncallback",
		success : function(json) {
			  serverDate = { "year": json.year, "month":json.month, "day":json.day, "hour": json.hour, "minute": json.minute, "second": json.second };
			    //当前服务器时间
			    if (typeof serverDate === "object") {
			        //计算当前时间
			        time_now = new Date(serverDate.year + "/" + serverDate.month + "/" + serverDate.day + " " + serverDate.hour + ":" + serverDate.minute + ":" + serverDate.second);
			        time_now = time_now.getTime();
			        //计算结束时间
			        time_end = new Date(serverDate.year + "/" + serverDate.month + "/" + serverDate.day + " 10:00:00");
			        //结束时间为最近的10点
			        if (serverDate.hour >= 10)
			            time_end = time_end.getTime() + (1 * 24 * 60 * 60 * 1000);
			        else
			            time_end = time_end.getTime();
			        show_time();
			    }
		}
	});
}

function show_time() {
    var timer = document.getElementById("timer");
    if (!timer) {
        return;
    }

    var time_distance, str_time;
    var int_day, int_hour, int_minute, int_second;
    
    
    time_distance = time_end - time_now;
    if (time_distance > 0) {
        int_day = Math.floor(time_distance / 86400000);
        time_distance -= int_day * 86400000;
        int_hour = Math.floor(time_distance / 3600000);
        time_distance -= int_hour * 3600000;
        int_minute = Math.floor(time_distance / 60000);
        time_distance -= int_minute * 60000;
        int_second = Math.floor(time_distance / 1000);

        int_hour = int_hour < 10 ? "0" + int_hour : int_hour;
        int_minute = int_minute < 10 ? "0" + int_minute : int_minute;
        int_second = int_second < 10 ? "0" + int_second : int_second;

        var $timer = $("#timer");
        $timer.find("i:eq(0)").text(int_day);
        $timer.find("i:eq(1)").text(int_hour);
        $timer.find("i:eq(2)").text(int_minute);
        $timer.find("i:eq(3)").text(int_second);

        //倒计时结束 重置计时器
        if (parseInt(int_hour) == 0 && parseInt(int_minute) == 0 && parseInt(int_second) == 0) {
            clearTimeout(timerID);
            init();
            return;
        }
        time_now += 1000;
        timerID = setTimeout("show_time()", 1000);
    } else {
        clearTimeout(timerID);
        init();
    }
}
