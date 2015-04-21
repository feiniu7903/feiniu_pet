
function getCookie(objName) {
	var arrStr = document.cookie.split("; ");
	for (var i = 0; i < arrStr.length; i++) {
		var temp = arrStr[i].split("=");
		if (temp[0] == objName) {
			return unescape(temp[1]);
		}
	}
}
function sendAdvertising() {
	var orderId = document.getElementById("advOrderId");
	var signature = document.getElementById("signature").value;
	var channelId = "";
	var externalId = "";
	var oRequest;
	if (signature!="") {
		channelId = getCookie("advChannelId");
		externalId = getCookie("advExternalId");
		if (channelId) {
//			if (window.XMLHttpRequest){
//				oRequest = new XMLHttpRequest();
//			} else {
//				oRequest = new ActiveXObject("Microsoft.XMLHTTP");
//			}
//			var url = "http://union.lvmama.com/userCenter/channel!record.do?advOrderId=" + orderId + "&signature=" + signature + "&advChannelId=" + channelId + "&advExternalId=" + externalId;
//			oRequest.open("get", url, false);
//			oRequest.send(null);
//			$.getJSON("http://union.lvmama.com/userCenter/channel!record.do?advOrderId=" + orderId + "&signature=" + signature + "&advChannelId=" + channelId + "&advExternalId=" + externalId);
			$("#advertising").attr("src","http://union.lvmama.com/userCenter/channel!record.do?advOrderId=" + orderId + "&signature=" + signature + "&advChannelId=" + channelId + "&advExternalId=" + externalId);
		}
	}
	var vardate = document.getElementById("showDate").innerHTML;
	if(vardate!=""){
	selectDate(vardate);
	}
}

