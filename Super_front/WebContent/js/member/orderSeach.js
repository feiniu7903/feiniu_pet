
$(document).ready(function () {
	$("#dateSeach").click(function () {
		$("#one").show();
		$("#two").hide();
		$("#three").hide();
	});
	$("#placeSeach").click(function () {
		$("#two").show();
		$("#one").hide();
		$("#three").hide();
	});
	$("#orderSeach").click(function () {
		$("#three").show();
		$("#two").hide();
		$("#one").hide();
	});
});


function checkForm1() {
	var begin = $("#begin").val();
	var end = $("#end").val();
	if (begin != "" && end != "") {
		return true;
	}
	$("#dateTip").html("\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u65e5\u671f");
	return false;
}
function checkForm2() {
	var placeName = $.trim($("#placeName").val());
	if (placeName != "") {
		return true;
	}
	$("#placeNameTip").html("\u8bf7\u8f93\u5165\u6b63\u786e\u666f\u533a\u540d\u79f0");
	return false;
}
function checkForm3() {
	var orderId = $.trim($("#orderId").val());
	if (orderId != "") {
		return true;
	}
	$("#orderIdTip").html("\u8bf7\u8f93\u5165\u6b63\u786e\u8ba2\u5355\u53f7");
	return false;
}
