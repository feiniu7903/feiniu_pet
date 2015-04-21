<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>香港迪士尼特价门票/酒店/自由行_香港迪斯尼门票多少钱-驴妈妈旅游网</title>
<meta name="keywords" content="香港迪士尼门票,香港迪士尼酒店,香港迪士尼自由行" />
<meta  name="description" content="驴妈妈旅游网暑期特惠香港迪斯尼提价门票，有便宜的香港迪斯尼附近特价酒店，一起来香港迪士尼来度欢乐时光吧！"/>
<link href="http://www.lvmama.com/zt/promo/dishini/include/css/iframe.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://s1.lvjs.com.cn/js/jquery-1.7.js"></script>
<base target="_blank">
</head>

<body>

<div class="chanpinAll">
    <h2>香港迪士尼乐园<br/>双倍乐享季</h2>
    <div class="chanpinBox">
    	<h3 class="menpiaoT">门票产品</h3>
        <ul class="chanpinList">
        	<@s.iterator value="map.get('${station}_mp')">
        	<li>
            	<span>￥<big>${memberPrice?if_exists?replace(".0","")}</big>起</span>
                <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
            </li>
            </@s.iterator>
        </ul>
        <span class="gengduo"></span>
    </div>
    
    <div class="chanpinBox">
    	<h3 class="jiudianT">酒店产品</h3>
	<ul class="chanpinList">
        	<@s.iterator value="map.get('${station}_jd')">
        	<li>
            	<span>￥<big>${memberPrice?if_exists?replace(".0","")}</big>起</span>
                <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
            </li>
            </@s.iterator>
          
        </ul>
        <span class="gengduo"></span>
    </div>
    
    <div class="chanpinBox">
    	<h3 class="ziyouxingT">自由行产品*可自选</h3>
        <p class="zhushi"></p>
        <ul class="chanpinList">
        	<@s.iterator value="map.get('${station}_zyx')">
        	<li>
            	<span>￥<big>${memberPrice?if_exists?replace(".0","")}</big>起</span>
                <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
            </li>
            </@s.iterator>
          
        </ul>
        <span class="gengduo"></span>
    </div>
    



	<p class="zhushi zhushi1">更多香港迪士尼乐园线路请登陆<a href="http://www.lvmama.com/" target="_blank">www.lvmama.com</a>或致电10106060</p>

	<a href="http://www.lvmama.com/" style="width:220px;height;50px;overflow:hidden;display:block"><img src="http://pic.lvmama.com/img/v3/logo.png" /></a>












</div>
<script>
	
$(function(){ 
	$('.gengduo').each(function(){
		if($(this).siblings('.chanpinList').find('li').length<=6){
			$(this).hide();
			
		}else{
			$(this).siblings('.chanpinList').find('li:gt(5)').hide();
		}
	});
	
	$('.gengduo').toggle(function(){ 
		$(this).siblings('.chanpinList').find('li').show();
	},function(){ 
		$(this).siblings('.chanpinList').find('li:gt(5)').hide();
	});
});
</script>

</body>
</html>