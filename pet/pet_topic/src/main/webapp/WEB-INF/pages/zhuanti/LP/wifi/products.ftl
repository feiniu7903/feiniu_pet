<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>wifi相关产品 - 驴妈妈旅游网</title> 
<meta name="keywords" content="出国，出境，wifi" /> 
<meta name="description" content="出国旅游快乐无处不在，wifi无处不在,驴妈妈和全球电信合作推出境外移送上网便宜不限流量、稳定、高速,出境游上网大优惠尽在驴妈妈." /> 
<link rel="stylesheet" href="http://www.lvmama.com/zt/promo/wifi/css/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<base target="_blank">
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->

<div class="mainAll">
    <div class="banner">
    	<h1></h1>	
    </div>
    <div class="navTop">	
        <ul class="nav" id="nav">
            <li><a href="index.html" target="_self">出境 WiFi</a></li>
            <li class="navLi"><a href="http://www.lvmama.com/zt/lvyou/wifi/product.html" target="_self">WiFi 相关产品</a></li>
            <li><a href="http://www.lvmama.com/zt/lvyou/wifi/help.html" target="_self">WiFi Q&amp;A</a></li>
        </ul>
    </div>
    <div class="pro">
	<ul class="proList">
	<@s.iterator value="map.get('${station}_cp')" status="st">
    	<li>
        	<a class="photo" href="${url?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="230" height="154" alt=""></a>
            <div class="proDetail">
            	<span><a href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a></span>
                <em><i>&yen;</i>${memberPrice?if_exists?replace(".0","")}</em>
                <a href="${url?if_exists}" target="_blank"><img src="http://www.lvmama.com/zt/promo/wifi/images/order.png" width="86" height="28" alt=""></a>
            </div>
        </li>
	</@s.iterator>
        
        
    </ul>
    </div>
</div>
<div class="arrow"><a href="#" target="_self"></a></div>
<div class="footer">
	<div class="ztfooter">
	<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
    </div>
</div>








<script src="http://pic.lvmama.com/js/jquery-1.7.js"></script>
<script type="text/javascript" src="http://www.lvmama.com/zt/promo/wifi/js/index.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
