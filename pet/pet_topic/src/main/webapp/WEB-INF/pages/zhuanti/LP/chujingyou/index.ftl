<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>出境旅游去哪里好_出境游有哪些好地方_出国游有哪些国家好玩-驴妈妈旅游网</title>
<meta name="keywords" content="出境，出国，旅游" />
<meta name="description" content="驴妈妈推出最新特价大狂欢出境旅游去哪里好，出境抄底游，不用去寻找出国游有哪些国家好玩和出境游有哪些好地方，小编已经为您筛选好了，赶快来吧！" />
<link rel="stylesheet" href="http://www.lvmama.com/zt/promo/chaodi/css/index.css">
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
    	<h1></h1>
    </div>
    <div id="pageTop"></div>
    <ul class="main_box" style="display:block;">  
    	<@s.iterator value="map.get('${station}_cp1')" status="st">  
        <li>
            <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
            <a class="imgBox" href="${url?if_exists}">
            	<img src="${imgUrl?if_exists}" width="288" height="111">
            </a>
            <div class="jiage">
                	<span>原价<small>￥</small>${bakWord4?if_exists}</span> <big>现价:</big><small> ￥ </small><big>${memberPrice?if_exists?replace(".0","")}</big> 起
            </div>    
            <p>★ ${bakWord1?if_exists}<br>★ ${bakWord2?if_exists}<br>★ ${bakWord3?if_exists}</p>
            <a class="goumai" href="${url?if_exists}"></a>
        </li>
        </@s.iterator>
        
    </ul>
    <ul class="main_box">    
       <@s.iterator value="map.get('${station}_cp2')" status="st">  
        <li>
            <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
            <a class="imgBox" href="${url?if_exists}">
            	<img src="${imgUrl?if_exists}" width="288" height="111">
            </a>
            <div class="jiage">
                	<span>原价<small>￥</small>${bakWord4?if_exists}</span> <big>现价:</big><small> ￥ </small><big>${memberPrice?if_exists?replace(".0","")}</big> 起
            </div>    
            <p>★ ${bakWord1?if_exists}<br>★ ${bakWord2?if_exists}<br>★ ${bakWord3?if_exists}</p>
            <a class="goumai" href="${url?if_exists}"></a>
        </li>
        </@s.iterator>
    </ul>
    <ul class="main_box">    
       <@s.iterator value="map.get('${station}_cp3')" status="st">  
        <li>
            <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
            <a class="imgBox" href="${url?if_exists}">
            	<img src="${imgUrl?if_exists}" width="288" height="111">
            </a>
            <div class="jiage">
                	<span>原价<small>￥</small>${bakWord4?if_exists}</span> <big>现价:</big><small> ￥ </small><big>${memberPrice?if_exists?replace(".0","")}</big> 起
            </div>    
            <p>★ ${bakWord1?if_exists}<br>★ ${bakWord2?if_exists}<br>★ ${bakWord3?if_exists}</p>
            <a class="goumai" href="${url?if_exists}"></a>
        </li>
        </@s.iterator>
    </ul>
    
	<div class="pages">
    	<a class="pages_a" target="_self" href="#pageTop">1</a>
        <a target="_self" href="#pageTop">2</a>
        <a target="_self" href="#pageTop">3</a>
    </div>
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

<script>
$(function(){
	$('.pages a').click(function(){
		_num = $(this).index();
		$(this).addClass('pages_a').siblings().removeClass('pages_a');
		$('.main_box').eq(_num).show().siblings('.main_box').hide();	
	});
	
	
	
});
</script>

<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
