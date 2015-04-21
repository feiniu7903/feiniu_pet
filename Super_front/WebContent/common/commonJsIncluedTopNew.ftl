<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js?r=8420"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery_cookie.js?r=2913"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/UniformResourceLocator.js?r=2913"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/top/jquery.remote.jsonSuggest.js?r=2913"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/super_v2/s2_common.js?r=4675"></script>
<script>
$(document).ready(function () {
	changCity();
	//myLvmamaSelect();  
	headerSearch();
	//showMeunTool();
	ticketSearch();
	checkCookie();
});
$(document).ready(function(){
$("#placeKeyword").suggest("http://www.lvmama.com/dest/place/place!getPlaceInfoByName.do?jsoncallback=?",
{});
});
</script>

