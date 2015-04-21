/**
 *查询url中指定参数名的值
 *@parm item 参数的名字
 */
function QueryString(item){
     var sValue=location.search.match(new RegExp("[\?\&]"+item+"=([^\&]*)(\&?)","i"));
     return sValue?sValue[1]:sValue;
}

/**
 *获取Cookie的值
 *@parm objName Cookie的名字
 */
function getLOSCCookie(objName){   
	var arrStr = document.cookie.split("; "); 
	var temp;
	for(var i = 0,l=arrStr.length;i < l;i++){    
		 temp=arrStr[i].split("=");    
		if(temp[0] == objName) {
			return unescape(temp[1]);
		}
	}
}

/**
 *写Cookie的值
 *@parm objName Cookie的名字
 *@parm objValue Cookie的值
 *@parm expire Cookie的有效天数
 */
function writeCookie(objName, objValue, expire) {
	//获取当前时间
	var now = new Date();
	//将date设置为30天以后的时间
	now.setDate(now.getDate() + expire);
	document.cookie = objName + "=" + escape (objValue) + ";path=/;domain=.lvmama.com;expires="+now.toGMTString();		
}
//载入一个JS脚本
function addScript(url,callback){
	var elt = document.createElement("script");
	elt.src = url;
	elt.anysc = true;
	if(elt.onload==null){
		elt.onload = function(){
			typeof callback=='function'&&callback();
		}
	}else{
		elt.onreadystatechange = function(){
			if(elt.readyState=="loaded" || elt.readyState=="complete"){
				typeof callback=='function'&&callback();
			}
		}
	}
	document.getElementsByTagName("head")[0].appendChild(elt);
}

/**
 * 获取LOSC所需要的日期格式(MMdd)
 */
function getLOSCDate(){
	var myDate = new Date();
	var month = myDate.getMonth() + "";
	if (month.length  == 1){
		month = "0" + month;
	}
	var day = myDate.getDate() + "";
	if (day.length == 1) {
		day = "0" + day;
	}
	return month + day;
}

/**
 * 判断传入的MMdd是否在有效的3个月之内
 */
function isValidLOSCDate(data) {
	var p1 = /^[0-9]{4}$/;
	if (!p1.test(data)) {
		return false;
	}
	var _month = parseInt(data.substr(0,2), 10);
	var _day = parseInt(data.substr(2,2), 10);
    
	var month = new Date().getMonth();
	var day = new Date().getDate();
    
	if (month - _month < 0) {
		month = month + 12;
	}
	//alert("当前月份是" + month);
	//alert("获取月份是" + _month);

	if (month - _month >= 0 && month - _month < 3) {
		return true;
	}
	if (month - _month >= 4) {
		return false;
	}
	if (month - _month == 3) {
		return day - _day <= 0;
	}
	return false;
}

/**
 * 更新站内订单渠道
 * @param value 需要新增的站内渠道
 */
function updateOrderInnerChannel(value) {
	var p1 = /^[0-9]{6}$/;
	if (!p1.test(value)) {
		return;
	}
    
	//默认为站位渠道
	var channelType = "oUC";
	var timeType = "oUT";
	//当存在ict的参数时，则为内部渠道
	if (QueryString('ict') != undefined) {
		channelType = "oIC";
		timeType = "oIT";
	}

    var currentOrderInnerChannel = getLOSCCookie(channelType);
	var currentOrderChannelTime = getLOSCCookie(timeType);
   
	if (currentOrderInnerChannel == undefined) {
		currentOrderInnerChannel = value;
	} else {
		currentOrderInnerChannel = currentOrderInnerChannel + value;
	}

	if (currentOrderChannelTime == undefined) {
		currentOrderChannelTime = getLOSCDate();
	} else {
		currentOrderChannelTime = currentOrderChannelTime + getLOSCDate();
	}

	//当currentOrderInnerChannel的记录数与currentOrderChannelTime记录数不匹配时，进行牵制匹配
	for (var i = 0; i < currentOrderInnerChannel.length / 6 - currentOrderChannelTime / 4 ; i++ ) {
		currentOrderChannelTime = currentOrderChannelTime + getLOSCDate();	
	}

	if (currentOrderInnerChannel.length > 24) {
		if (isValidLOSCDate(currentOrderChannelTime.substr(0,4))) {
			//从头开始的6个字符代表首次渠道，不会被删除
			//FIFO原则从第二渠道，即第7位开始删除
			currentOrderInnerChannel = currentOrderInnerChannel.substr(0,6) + currentOrderInnerChannel.substr(currentOrderInnerChannel.length - 18, 18);
			currentOrderChannelTime = currentOrderChannelTime.substr(0,4) + currentOrderChannelTime.substr(currentOrderChannelTime.length - 12, 12);
		} else {
			currentOrderInnerChannel = currentOrderInnerChannel.substr(6, currentOrderInnerChannel.length - 6);
			currentOrderChannelTime = currentOrderChannelTime.substr(4, currentOrderChannelTime.length - 4);
		}	
	}

	writeCookie(channelType, currentOrderInnerChannel, 30);	
	writeCookie(timeType, currentOrderChannelTime, 30);
}

//<!-- 配置GA的参数开始 -->
var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-5493172-1']);
_gaq.push(['_setDomainName', 'lvmama.com']);
_gaq.push(['_setAllowHash', 'false']);
_gaq.push(["_addOrganic", "baidu", "wd"]);
_gaq.push(["_addOrganic", "baidu", "word"]);
_gaq.push(["_addOrganic", "baidu", "bs"]);
_gaq.push(["_addOrganic", "baidu", "oq"]);
_gaq.push(["_addOrganic", "baidu", "query"]);
_gaq.push(["_addOrganic", "google", "q"]);
_gaq.push(["_addOrganic", "bing", "q"]);
_gaq.push(["_addOrganic", "soso", "w"]);
_gaq.push(["_addOrganic", "soso", "key"]);
_gaq.push(["_addOrganic", "sogou", "query"]);
_gaq.push(["_addOrganic", "sogou", "keyword"]);
_gaq.push(['_addOrganic', 'youdao', 'q']);
_gaq.push(["_addOrganic", "360", "q"]);
_gaq.push(["_addOrganic", "so.360.cn", "q"]);
_gaq.push(["_addOrganic", "so.com", "q"]);
_gaq.push(["_addOrganic", "baidu.com", "wd"]);
_gaq.push(["_addOrganic", "m.baidu.com", "word"]);
_gaq.push(["_addOrganic", "sogou.com", "query"]);
_gaq.push(["_addOrganic", "google.com", "q"]);
_gaq.push(['_trackPageview']);
//<!-- 配置GA的参数结束 -->

(function () {
        var length = document.getElementsByTagName('script').length,leng=0;
        for(var i=0;i<length;i++){
            if(/\/losc\.js$/.test(document.getElementsByTagName('script')[i].src)){
                leng=i;
            }
        }
	   
        //<!-- 加载GA代码开始 --> 
        var ga = document.createElement('script'); 
        ga.type = 'text/javascript'; 
        ga.async = true;
        //ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
		ga.src = 'http://pic.lvmama.com/stacac/ga.js';

        var s = document.getElementsByTagName('script')[leng]; 
        s.parentNode.insertBefore(ga,s);
        //<!-- 加载GA代码结束 --> 
		
		//<!-- 判断losc代码开始 -->
		var orderChannel = getLOSCCookie("oUC");
		var orderChannelTime = getLOSCCookie("oUT");
		while (orderChannelTime != undefined && orderChannelTime.substr(0,4) != "" && !isValidLOSCDate(orderChannelTime.substr(0,4))) {
			orderChannel = orderChannel.substr(6, orderChannel.length - 6);
			orderChannelTime = orderChannelTime.substr(4, orderChannelTime.length - 4);
		}
		orderChannel = getLOSCCookie("oIC");
		orderChannelTime = getLOSCCookie("oIT");
		while (orderChannelTime != undefined && orderChannelTime.substr(0,4) != "" && !isValidLOSCDate(orderChannelTime.substr(0,4))) {
			orderChannel = orderChannel.substr(6, orderChannel.length - 6);
			orderChannelTime = orderChannelTime.substr(4, orderChannelTime.length - 4);
		}

		//SEO的来源
		var seoKeyWords = ["baidu", "google","sogou","soso","youdao","bing","hao123","etao","360","yahoo"];
		//SEO的代码
		var seoChannelCode = ["018115", "018116", "018117", "018118","018119", "018128", "018122", "018123","018120","018121"];

		//url中的losc值
		var orderFromChannel = QueryString('losc');
        
		//没有指定渠道的情况下，统计seo的来源
		if (orderFromChannel == undefined || orderFromChannel == null) {
			var ref = document.referrer;
			if (ref != "" && ref.indexOf("losc=") == -1) {
				var re = new RegExp('^(?:f|ht)tp(?:s)?\://([^/?#]+)', 'im');
				ref = ref.match(re)[1];
				for (var i = 0,l=seoKeyWords.length; i < l ; i++) {
					if (ref.indexOf(seoKeyWords[i]) != -1) {
						updateOrderInnerChannel(seoChannelCode[i]);	
					}
				}
			}
		}

		//执行覆盖操作
		if (orderFromChannel != undefined && orderFromChannel != null) {
			updateOrderInnerChannel(orderFromChannel);
		}
	
		//睿广的产品推荐
		var pr = QueryString('_pr');
		if (pr != undefined && pr != null) {
			updateOrderInnerChannel(pr);
		}	
		//<!-- 判断losc代码结束 -->

		//<!-- 加载百分点代码开始 --> 
		window["_BFD"] = window["_BFD"] || {};
		_BFD.client_id = "Ctest_lvmama";
		_BFD.script = document.createElement("script");
		_BFD.script.type = "text/javascript";
		_BFD.script.async = true;
		_BFD.script.charset = "utf-8";
		_BFD.script.src = (('https:' == document.location.protocol?'https://ssl-static1':'http://static1')+'.baifendian.com/service/lvmama/lvmama.js');
		document.getElementsByTagName('script')[length].parentNode.insertBefore(_BFD.script, document.getElementsByTagName('script')[length]);
		//<!-- 加载百分点代码结束 --> 
    })();
	


