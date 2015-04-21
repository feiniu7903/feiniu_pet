<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>清迈旅游团_清迈旅游线路报价,大约要多少钱_团购-驴妈妈旅游网</title>
<meta name="keywords" content="清迈，旅游，线路" />
<meta name="description" content="驴妈妈包机去清迈旅游团,为您提供专属的清迈旅游线路,优惠的团购清迈旅游价格,非一般的清迈旅游多少钱都值得.<清迈旅游,就找驴妈妈>" />
<link rel="stylesheet" href="http://www.lvmama.com/zt/promo/qingmai/css/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<base target="_blank">
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="wrap">
	<div class="page">
    	<div class="main">
        	<div class="section">
            	<div class="sectionL"><img src="http://www.lvmama.com/zt/promo/qingmai/images/hbxx.jpg" width="248" height="155" alt=""></div>
                <div class="sectionR">
					<ul>
                    	<@s.iterator value="map.get('${station}_hb')" status="st">
                    	<li>
                        	<span> ${bakWord1?if_exists}</span>
                            <b>${title?if_exists}</b>
                            <em> ${bakWord2?if_exists}</em>
                        </li>
                        </@s.iterator>	
                    </ul>
                </div>
            </div>
            <div class="section">
            	<div class="sectionL"><img src="http://www.lvmama.com/zt/promo/qingmai/images/price.jpg" width="248" height="155" alt=""></div>
                <div class="sectionR">
                <@s.iterator value="map.get('${station}_jg')" status="st">
					<span class="price"> ${bakWord1?if_exists}<em></em></span>
                </@s.iterator>	
                </div>
            </div>
            <div class="section">
            	<div class="sectionL sectionL3"><img src="http://www.lvmama.com/zt/promo/qingmai/images/date.jpg" width="248" height="310" alt=""></div>
                <div class="sectionR sectionR3">
                	<ul class="date">
                    <@s.iterator value="map.get('${station}_rq1')" status="st">
						<li><a href="${url?if_exists}">${bakWord1?if_exists}<i></i>${bakWord2?if_exists}</a></li>
               		</@s.iterator>
                    </ul>
                    <ul class="date">
                    <@s.iterator value="map.get('${station}_rq2')" status="st">
						<li><a href="${url?if_exists}">${bakWord1?if_exists}<i></i>${bakWord2?if_exists}</a></li>
               		</@s.iterator>
                    </ul>
                </div>
            </div>
            <div class="section">
            	<div class="sectionL"><img src="http://www.lvmama.com/zt/promo/qingmai/images/return.jpg" width="248" height="155" alt=""></div>
                <div class="sectionR">
                <@s.iterator value="map.get('${station}_zc')" status="st">
					<span class="explain">${title?if_exists}</span>
                </@s.iterator>
                </div>
            </div>
            <div class="section">
            	<div class="sectionL sectionL5"><img src="http://www.lvmama.com/zt/promo/qingmai/images/order.jpg" width="248" height="198" alt=""></div>
                <div class="sectionR sectionR5">
                    <div class="intro">
                    <@s.iterator value="map.get('${station}_yd')" status="st">
                        <span class="num1">${bakWord1?if_exists}<br>${bakWord2?if_exists}<br>${bakWord3?if_exists}</span>
                        <span class="num2">${bakWord4?if_exists}</span>
                        <span class="num3">${bakWord5?if_exists}</span>
                    </@s.iterator>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="bgBox">
    	<div class="bg1"></div>
        <div class="bg2"></div>
        <div class="bg3"></div>
        <div class="bg4"></div>
        <div class="bg5"></div>
        <div class="bg6"></div>
        <div class="bg7"></div>
        <div class="bg8"></div>
        <div class="bg9"></div>
        <div class="bg10"></div>
    </div>
</div>
<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>







<script src="http://pic.lvmama.com/js/jquery-1.7.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
