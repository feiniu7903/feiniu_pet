<script src="http://pic.lvmama.com/js/common/copyright.js"></script>

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
var orderFromChannel = QueryString('losc'); 
var ov = QueryString('_ov'); //是否覆盖，默认为覆盖，只要当_ov = 0时才不覆盖 
if (orderFromChannel != null && ov != "0") { 
//获取当前时间 
var cookie_date=new Date(); 
//将date设置为30天以后的时间 
cookie_date.setDate(cookie_date.getDate()+30); 
document.cookie = "orderFromChannel=" + escape (orderFromChannel) + ";path=/;domain=.lvmama.com;expires="+cookie_date.toGMTString(); 
}
</script>

