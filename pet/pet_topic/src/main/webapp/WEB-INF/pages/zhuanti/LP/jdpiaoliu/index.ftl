<!doctype html> 
<html> 
<head> 
<meta charset="utf-8" />
<title>漂流线路推荐_漂流景点门票_夏季漂流去哪好-驴妈妈旅游网</title>
<meta name="Keywords" content="漂流线路,漂流景点门票">
<meta name="Description" content="驴妈妈旅游网畅游江南溪游记,为你推荐最佳的漂流线路，为你提供最好的漂流景点门票,激情夏日，水与争锋!还犹豫什么,快来体验吧!">
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/piaoliu/piaoliu.css" type="text/css" rel="stylesheet" />
</head>
<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<div id="warp">
	<div class="header w1008">
    	<img src="http://pic.lvmama.com/img/zt/piaoliu/images/header01.jpg" height="189" width="1007" />
    	<img src="http://pic.lvmama.com/img/zt/piaoliu/images/header02.jpg" height="189" width="1007" />
    </div>
	<div class="main w1008">
    	<i class="main_icon1"></i>
        <div class="pros">
		<@s.iterator value="map.get('${station}_12251')" status="st">
        	<div class="pro">
            	<i class="pro_icon1"></i>
            	<i class="pro_icon2"></i>
            	<a href="${url?if_exists}" class="pic"><img src="${imgUrl?if_exists}" title="" alt="" height="202" width="284" /></a>
           	<p class="pro_tit"><a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a><i class="${bakWord2?if_exists}"></i></p>
                <p class="pro_text" title="${bakWord3?if_exists}">${bakWord3?if_exists}</p>
                <p class="product"><a href="${url?if_exists}">查看详情</a><strong>&yen;<b>${memberPrice?if_exists?replace(".0","")}</b>起</strong></p>          
            </div>
		</@s.iterator>
        </div>
        <i class="main_icon2"></i>
    </div>
	<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
</div>
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<script type="text/javascript">
$(document).ready(function(){ 
	//限制字符个数 
	$(".pro_text").each(function(){ 
		var maxwidth=40; 
		if($(this).text().length>maxwidth){ 
		$(this).text($(this).text().substring(0,maxwidth)); 
		$(this).html($(this).html()+'…'); 
		} 
	}); 
	//限制字符个数 
	$(".pro_tit a").each(function(){ 
		var maxwidth=11; 
		if($(this).text().length>maxwidth){ 
		$(this).text($(this).text().substring(0,maxwidth)); 
		$(this).html($(this).html()+'…'); 
		} 
	}); 
});
</script>
</body>
</html>
