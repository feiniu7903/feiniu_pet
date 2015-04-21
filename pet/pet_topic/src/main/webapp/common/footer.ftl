<script src="http://pic.lvmama.com/js/v6/public/footer.js"></script>

<script type="text/javascript">
function QueryString(item){
     var sValue=location.search.match(new RegExp("[\?\&]"+item+"=([^\&]*)(\&?)","i"))
     return sValue?sValue[1]:sValue
}

function getCookie(objName){   
	var arrStr = document.cookie.split("; "); 
	for(var i = 0;i < arrStr.length;i++){    
		var temp = arrStr[i].split("=");    
		if(temp[0] == objName) 
			return unescape(temp[1]);   
	}
}

if (document.referrer.indexOf("baidu")!= -1) {
	//获取当前时间
	var cookie_date=new Date();
	//将date设置为30天以后的时间
	cookie_date.setDate(cookie_date.getDate()+30);
	document.cookie = "orderFromChannel=baidu;path=/;domain=.lvmama.com;expires="+cookie_date.toGMTString();	
}
if (document.referrer.indexOf("google")!= -1) {
	//获取当前时间
	var cookie_date=new Date();
	//将date设置为30天以后的时间
	cookie_date.setDate(cookie_date.getDate()+30);
	document.cookie = "orderFromChannel=google;path=/;domain=.lvmama.com;expires="+cookie_date.toGMTString();	
}
if (document.referrer.indexOf("sogou")!= -1) {
	//获取当前时间
	var cookie_date=new Date();
	//将date设置为30天以后的时间
	cookie_date.setDate(cookie_date.getDate()+30);
	document.cookie = "orderFromChannel=sogou;path=/;domain=.lvmama.com;expires="+cookie_date.toGMTString();	
}
if (document.referrer.indexOf("soso") != null) {
	//获取当前时间
	var cookie_date=new Date();
	//将date设置为30天以后的时间
	cookie_date.setDate(cookie_date.getDate()+30);
	document.cookie = "orderFromChannel=soso;path=/;domain=.lvmama.com;expires="+cookie_date.toGMTString();	
}
if (document.referrer.indexOf("youdao") != null) {
	//获取当前时间
	var cookie_date=new Date();
	//将date设置为30天以后的时间
	cookie_date.setDate(cookie_date.getDate()+30);
	document.cookie = "orderFromChannel=youdao;path=/;domain=.lvmama.com;expires="+cookie_date.toGMTString();	
}
if (document.referrer.indexOf("bing") != null) {
	//获取当前时间
	var cookie_date=new Date();
	//将date设置为30天以后的时间
	cookie_date.setDate(cookie_date.getDate()+30);
	document.cookie = "orderFromChannel=bing;path=/;domain=.lvmama.com;expires="+cookie_date.toGMTString();	
}


var orderFromChannel = QueryString('losc');
var ov = QueryString('_ov');  //是否覆盖，默认为覆盖，只要当_ov = 0时才不覆盖
if (orderFromChannel != null && ov != "0") {
	//获取当前时间
	var cookie_date=new Date();
	//将date设置为30天以后的时间
	cookie_date.setDate(cookie_date.getDate()+30);
	document.cookie = "orderFromChannel=" + escape (orderFromChannel) + ";path=/;domain=.lvmama.com;expires="+cookie_date.toGMTString();	
}
if (orderFromChannel != null && ov == "0" && QueryString('orderFromChannel') == null) {
	//获取当前时间
	var cookie_date=new Date();
	//将date设置为30天以后的时间
	cookie_date.setDate(cookie_date.getDate()+30);
	document.cookie = "orderFromChannel=" + escape (orderFromChannel) + ";path=/;domain=.lvmama.com;expires="+cookie_date.toGMTString();	
}

//产品推荐
var pr = QueryString('_pr');
if (pr != null && getCookie("orderFromChannel") == undefined) {
	document.cookie = "orderFromChannel=" + escape (pr) + ";path=/;domain=.lvmama.com"
}
</script>

<#include "/common/mvHost.ftl"/>