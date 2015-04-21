<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈携手中国银联，银联在线high爆驴行-驴妈妈旅游网</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" href="http://www.lvmama.com/zt/promo/yinlian/css/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery-1.7.js"></script>
<base target="_blank">
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="main_all">
	<div class="banner">
    </div>
    <div class="navTop">
    	<a  href="#jingwai" target="_self">出境游</a> 
        <a class="guonei" href="#guonei" target="_self">国内游</a> 
    </div>
    <img src="http://www.lvmama.com/zt/promo/yinlian/images/text.gif" width="1002" height="252" alt="">
    </div>
    <div class="abroad" id="jingwai">
    	<h4><img src="http://www.lvmama.com/zt/promo/yinlian/images/abroad.gif" width="911" height="47" alt=""></h4>
	<@s.iterator value="map.get('${station}_cjcp')" status="st">
            <div class="probox1">
                <img src="${imgUrl?if_exists}" width="276" height="200" />
                <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                <div class="jiage">
                    <span>${bakWord1?if_exists}</span><p class="jiageLv">驴妈妈价：<em>&yen;</em><samp>${memberPrice?if_exists?replace(".0","")}</samp>起</p>
                </div>        
            </div> 
	</@s.iterator>
            
    </div>
    <div class="home" id="guonei">
    	<h4><img src="http://www.lvmama.com/zt/promo/yinlian/images/home.gif" width="910" height="53" alt=""></h4>
        	<@s.iterator value="map.get('${station}_gncp')" status="st">
            <div class="probox1">
                <img src="${imgUrl?if_exists}" width="276" height="200" />
                <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                <div class="jiage">
                    <span>${bakWord1?if_exists}</span><p class="jiageLv">驴妈妈价：<em>&yen;</em><samp>${memberPrice?if_exists?replace(".0","")}</samp>起</p>
                </div>        
            </div> 
	</@s.iterator>
    </div>  
     
     
     
     <div class="main_b ">
	<div class="hd_title">
    	<p></p>
        <h3>以下活动正在进行</h3>
    </div>
    <ul class="hd_list"> 
       <@s.iterator value="map.get('${station}_tp')" status="st">
    	<li><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="224" height="160" alt=""></a></li>
		</@s.iterator>
	</ul>
    <script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
</div>
     
     
     
     
</div>










<script>
$(function(){ 
/*智能加title，给a、h、span等标签加title*/
	var hoverA = '..probox1 a' ;       //鼠标指上对应的class，智能给当前元素加title。
////////////////////////////////////////////////////////////
/*智能加title*/
////////////////////////////////////////////////////////////
	$(hoverA).hover(function(){
		var _title = $(this).text();
		$(this).attr("title",_title);
	}); 
});

</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
