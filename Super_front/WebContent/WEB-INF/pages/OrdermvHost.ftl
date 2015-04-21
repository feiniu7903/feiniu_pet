


<script type='text/javascript'>
    (function () {
        var s = document.createElement('script');
        s.type = 'text/javascript';
        s.async = true;
        s.src = (location.protocol == 'https:' ? 'https://ssl.' : 'http://static.')
         + 'gridsumdissector.com/js/Clients/GWD-000268-6F8036/gs.js';
        var firstScript = document.getElementsByTagName('script')[0];
        firstScript.parentNode.insertBefore(s, firstScript);
    })();
</script>
<script type="text/javascript">
try{
var _mvsite=new $mvt.$site("m-246-0");
_mvsite.$logConversion();
}catch(err){}
</script>

<script type="text/javascript"> 
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-5493172-1']);
  _gaq.push(['_setDomainName', 'lvmama.com']);
  _gaq.push(['_setAllowHash', 'false']);
  _gaq.push(['_addOrganic', 'soso', 'w']);
  _gaq.push(['_addOrganic', 'sogou', 'query']);
  _gaq.push(['_addOrganic', 'youdao', 'q']);
  <@s.if test = 'order != null'>
  _gaq.push(['_addTrans','${order.orderId?if_exists}','0','${order.oughtPayFloat?if_exists}','0','0','0','0','0']);
 <#list order.ordOrderItemProds as item>
	_gaq.push(['_addItem', '${order.orderId?if_exists}','${item.productId?if_exists}','${item.productName?if_exists}','在线支付','${item.priceYuan?if_exists}','${item.quantity?if_exists}']);
</#list>
 </@s.if> 
  _gaq.push(['_trackTrans']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>

<script  type="text/javascript">
function QueryString(item){
     var sValue=location.search.match(new RegExp("[\?\&]"+item+"=([^\&]*)(\&?)","i"))
     return sValue?sValue[1]:sValue
}


var orderFromChannel = QueryString('losc');
if (orderFromChannel != null) {
	//获取当前时间
	var cookie_date=new Date();
	//将date设置为30天以后的时间
	cookie_date.setDate(cookie_date.getDate()+30);
	document.cookie = "orderFromChannel=" + escape (orderFromChannel) + ";path=/;domain=.lvmama.com;expires="+cookie_date.toGMTString();	
}
</script>
