<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>湖南大巴旅游_湖南大巴跟团游-湖南大巴自由行-驴妈妈旅游网</title>
<meta name="keywords" content="大巴旅游,湖南旅游,自由行" />
<meta name="description" content="驴妈妈旅游网重磅推出湖南大巴旅游产品,让您轻松省事,免去旅途的交通麻烦.湖南大巴跟团游,湖南大巴自由行,是您预订旅行产品,跟团游及自由行的最佳选择!" />
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/gzdb/style.css" rel="stylesheet" type="text/css" />
<base target="_blank" />
</head>

<body>
	<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
    <div class="banner">
    	<img src="http://pic.lvmama.com/img/zt/gzdb/banner1.jpg" width="902" height="197" alt="">
        <img src="http://pic.lvmama.com/img/zt/gzdb/banner2.jpg" width="902" height="197" alt="">
        <ul>
        	<li class="gx"><a href="guangxi.html" target="_self"><strong>广西游</strong><br><span>山清水秀、甲天下</span></a></li>
            <li class="hain"><a href="hainan.html" target="_self"><strong>海南游</strong><br><span>天涯海角、山盟海誓</span></a></li>
            <li class="fj"><a href="fujian.html" target="_self"><strong>福建游</strong><br><span>海岛风情、浪漫之旅</span></a></li>
            <li class="hun"><a><strong>神州游</strong><br><span>井冈红叶、湘西明珠</span></a></li>
       </ul>
    </div>
    <div class="content hunct">
    		<div class="hun_cttopbg"></div>
            <div class="mainCon">
            	<div class="main">
            	<@s.iterator value="map.get('${station}_bus_hunan')" status="st">
                    <dl class="prolist">
                        <dt>
                            <a href="${url?if_exists}">
                                <img src="${imgUrl?if_exists}" width="190" height="95" alt=""></a>
                        </dt>
                        <dd>
                            <a href="${url?if_exists}" class="order">立即订购</a>
                            <h3>
                                <a href="${url?if_exists}">${title?if_exists}</a>
                            </h3>
                            <p class="price"> <del>门市价：${marketPrice?if_exists?replace(".0","")}元</del>
                                驴妈妈价： <em>${memberPrice?if_exists?replace(".0","")}</em>
                                元
                            </p>
                            <p>${remark?if_exists}</p>
                        </dd>
                    </dl>
                </@s.iterator> 
                <div class="hbox"></div>
            </div>
            </div>
            <div class="hun_ctbmbg"></div>
            
    </div><!--content end-->
<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script> 
</body>
</html>
