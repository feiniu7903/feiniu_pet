if(!basePath) {
	var basePath = "/ebooking";
}
//写cookies
function setCookie(name, value) {
	var Days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}
// 读取cookies
function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}

function searchData(type) {
	if(!type){
		type="";
	}
	var sd = 0;
	$.ajax({
		type : 'POST',
		url : basePath + '/findCreateTaskCount.do',
		async : false,
		cache : false,
		data : {
			count : 2, productType:type
		},
		dataType : 'json',
		success : function(data) {
			sd = data;
		}
	});
	return sd;
}
var cookieName = "NONETRUNON";
var titleValue = "驴妈妈供应商管理系统";
function isTurnOn(name) {
	if (getCookie(name) == "true") {
		return true;
	} else {
		return false;
	}
}
function turnOff() {
	setCookie(cookieName, "false");
	changeHtmlView(false);
}

function turnOn() {
	setCookie(cookieName, "true");
	changeHtmlView(true);
}
function changeHtmlView(turnOn) {
	is_flash_title = turnOn;
	if (turnOn) {
		$("#flashIMG").attr("src",
				"http://pic.lvmama.com/img/ebooking/shakeOn.gif");
		$("#flashSPAN").text("已启动");
		$("#flashA").text("关闭");
		$("#flashA").attr("href", "javascript:turnOff()");
	} else {
		// 关闭
		$("#flashIMG").attr("src",
				"http://pic.lvmama.com/img/ebooking/shakeOff.gif");
		$("#flashSPAN").text("已关闭");
		$("#flashA").text("启动");
		$("#flashA").attr("href", "javascript:turnOn()");
		document.title = titleValue;
	}
}

function flash_title() {
	if (!isTurnOn(cookieName)) {
		return;
	}
	var searchCount = searchData();
	if (searchCount > 0) {
		is_flash_title = true;
	}
}
setInterval("flash_title()", 60000);
// 闪烁
setInterval("tile_Change()",1000);
var is_flash_title = false;
var i = 0;
function tile_Change() {
	if($("#menuConfirmOrderCountSpan").html()==0) {
		return;
	}
	if(!is_flash_title) {
		return;
	}
	if (i == 0) {
		document.title = titleValue;
		i = 1;
	} else {
		document.title = "您有待处理订单-" + titleValue;
		i = 0;
	}
}
/**
 * 菜单上的待处理订单数量
 */
function changeMenuConfirmOrderCount() {
	 var orderCount = $("#menuConfirmOrderCountSpan");
	 if(orderCount.size() <= 0) {
		 return;
	 }
	 var countH = searchData("HOTEL");
	 if(countH && countH > 0) {
		 $("#menuConfirmHotelOrderCountSpan").html(countH);
	 }
	 var countR = searchData("ROUTE");
	 if(countR && countR > 0) {
		 $("#menuConfirmRouteOrderCountSpan").html(countR);
	 }
	 var count = countR+countH;
	 if(count && count > 0) {
		 orderCount.html(count);
	 }
}
setInterval("changeMenuConfirmOrderCount()", 60000);
////////////////////////////
////弹出窗口提示
////////////////////////////
function tipMessageShow(msg,title) {
	$('div.window .panel-tool-close').click();
	$.messager.show({
		id: 'taskMessageDiv',
		name: 'taskMessageDiv',
		title: title,
		msg: msg,
		timeout: 60000,
		height:'110',
		width:'200',
		showType: 'slide'
	});
};
var is_tip_show = false;
var tip_show_cookie_name = "NONE_TIP_TRUNON";
function changeTipShowHtmlView(turnOn) {
	is_tip_show = turnOn;
	if (turnOn) {
		$("#flashIMGTipShow").attr("src",
				"http://pic.lvmama.com/img/ebooking/shakeOn.gif");
		$("#flashSPANTipShow").text("已启动");
		$("#flashATipShow").text("关闭");
		$("#flashATipShow").attr("href", "javascript:turnTipShowOff()");
	} else {
		// 关闭
		$("#flashIMGTipShow").attr("src",
				"http://pic.lvmama.com/img/ebooking/shakeOff.gif");
		$("#flashSPANTipShow").text("已关闭");
		$("#flashATipShow").text("启动");
		$("#flashATipShow").attr("href", "javascript:turnTipShowOn()");
		document.title = titleValue;
	}
}
function turnTipShowOff() {
	setCookie(tip_show_cookie_name, "false");
	changeTipShowHtmlView(false);
}

function turnTipShowOn() {
	setCookie(tip_show_cookie_name, "true");
	changeTipShowHtmlView(true);
}
function tipshowtime(){
	if(!is_tip_show) {
		return;
	}
	$.ajax( {
	url : basePath + "/findEbookingMessage.do",
	type: "POST",
	success : function(result) {
			if(result.length>0){
				tipMessageShow(result,"系统提醒消息");
			}
		}
	});
};
setInterval("tipshowtime()",120000);

/**
 * 初始化
 */
$(function() {
	if($("div #sessionUserName").size() > 0){
		titleValue = document.title;
		var sessionUserName = $("#sessionUserName").html();
		//标题提醒
		cookieName = sessionUserName+"TRUNON";
		changeHtmlView(isTurnOn(cookieName));
		//弹出提醒
		tip_show_cookie_name = sessionUserName = "_TIP_TRUNON";
		changeTipShowHtmlView(isTurnOn(tip_show_cookie_name));
		
		var orderCount = $("#menuConfirmOrderCountSpan");
		if(orderCount.size() > 0) {
			changeMenuConfirmOrderCount();
		}
	}
});