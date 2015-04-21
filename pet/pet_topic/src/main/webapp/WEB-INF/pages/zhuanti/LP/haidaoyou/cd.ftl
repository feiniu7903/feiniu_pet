<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>海岛游好去处_成都出发海岛游_重庆出发海岛游-驴妈妈旅游网</title>
<meta name="keywords" content="海岛游, 海岛旅游,旅游"/>
<meta name="description" content="驴妈妈推出海岛旅游游好去处，介绍成都出发海岛游给驴友们不一样的海岛游的新体验，同时挑选重庆出发海岛游，有美丽的济州岛，还有天堂般的马尔代夫群岛，一起海岛游吧！" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/zt/haidao/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<base target="_blank">
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->

<div class="main_all">
	<ul class="nav_cfd">
    	<li class="width_80"><a target="_self" href="index.html">江/浙/沪</a></li>
        <li class="width_125"><a target="_self" href="gz.html">广州/深圳/香港</a></li>
        <li class="width_90"><a target="_self" href="cd.html">成都/重庆</a></li>
        <li class="width_40"><a target="_self" href="bj.html">北京</a></li>
    </ul>
    
<!--A.B.C.D-->
    <div class="list1 taiguo">
    	<span></span>
        <dl>
        	<dt><a target="_blank" href="http://www.lvmama.com/search/abroad-from-成都-to-泰国-route.html">泰国</a></dt>
            
        		<dd>
				<@s.iterator value="map.get('${station}_cd_1')" status="st">
				<a href="${url?if_exists}">${title?if_exists}</a>/
				</@s.iterator>
        		</dd>
            
        </dl>
    </div>
    <div class="list1 yuenan">
    	<span></span>
        <dl>
        	<dt><a target="_blank" href="http://www.lvmama.com/search/abroad-from-成都-to-越南-route.html">越南</a></dt>
            
        		<dd>
				<@s.iterator value="map.get('${station}_cd_2')" status="st">
				<a href="${url?if_exists}">${title?if_exists}</a>/
				</@s.iterator>
        		</dd>
            
        </dl>
    </div>
    <div class="list1 jianpuzhai">
    	<span></span>
        <dl>
        	<dt><a target="_blank" href="http://www.lvmama.com/search/abroad-from-成都-to-柬埔寨-route.html">柬埔寨</a></dt>
             
        		<dd>
				<@s.iterator value="map.get('${station}_cd_3')" status="st">
				<a href="${url?if_exists}">${title?if_exists}</a>/
				</@s.iterator>
        		</dd>
            
        </dl>
    </div>
    <div class="list1 malaixiya">
    	<span></span>
        <dl>
        	<dt><a target="_blank" href="http://www.lvmama.com/search/abroadSearch!abroadSearch.do?toDest=%E9%A9%AC%E6%9D%A5%E8%A5%BF%E4%BA%9A&fromDest=%E6%88%90%E9%83%BD&routeType=route	">马来西亚</a></dt>
             
        		<dd>
				<@s.iterator value="map.get('${station}_cd_4')" status="st">
				<a href="${url?if_exists}">${title?if_exists}</a>/
				 </@s.iterator>
        		</dd>
           
        </dl>
    </div>
    
<!--墓碑-->
    <a class="list2 ssr" href="http://www.lvmama.com/search/abroad-from-成都-to-塞舌尔-route.html">塞舌尔</a>
    <a class="list2 mlqs" href="http://www.lvmama.com/search/abroad-from-成都-to-毛里求斯-route.html">毛里求斯</a>
    <a class="list2 medf" href="http://www.lvmama.com/search/abroad-from-成都-to-马尔代夫-route.html">马尔代夫</a>
    <a class="list2 bld" href="http://www.lvmama.com/search/abroad-from-成都-to-巴厘岛-route.html">巴厘岛</a>
    <a class="list2 jzd" href="http://www.lvmama.com/search/abroadSearch!abroadSearch.do?toDest=%E6%B5%8E%E5%B7%9E%E5%B2%9B&fromDest=%E6%88%90%E9%83%BD&routeType=route
">济州岛</a>
    <a class="list2 sb" href="http://www.lvmama.com/search/abroad-from-成都-to-塞班岛-route.html">塞班</a>
    <a class="list2 fj" href="http://www.lvmama.com/search/abroad-from-成都-to-斐济-route.html">斐济</a>
    <a class="list2 xwy" href="http://www.lvmama.com/search/abroadSearch!abroadSearch.do?toDest=%E5%A4%8F%E5%A8%81%E5%A4%B7&fromDest=%E6%88%90%E9%83%BD&routeType=route">夏威夷</a>
    <a class="list2 dxd" href="http://www.lvmama.com/search/abroad-from-成都-to-大溪地-route.html">大溪地</a>
    
    <div class="bqrm">
    
    	<ul class="bqrm_list">
		<@s.iterator value="map.get('${station}_cp_cd')" status="st">
        	<li><h4><a href="${url?if_exists}">${title?if_exists}</a></h4><p>￥<span>${memberPrice?if_exists?replace(".0","")}</span>起</p></li>
		</@s.iterator>
        </ul>
   
    </div>
    
  <script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>  
    
</div>






<script>
$(function(){

			$('.bqrm_list li').hover(function(){ 
				var _title = $(this).find('a').text();
				$(this).find('a').attr("title",_title);	
									  });

		   
		  $('.list1 span').hover(function(){				 
			var _width = $(this).siblings('dl').find('dd').width();
			$(this).siblings('dl').find('dd').show();
					 },function(){				 
			$(this).siblings('dl').find('dd').hide();
					 });
		   $('.list1 dt').hover(function(){				 
			var _width = $(this).siblings('dd').width();
			$(this).siblings('dd').show();
					 },function(){				 
			$(this).siblings('dd').hide();
					 });
		   
		   $('.list1 dd').hover(function(){
				$(this).show();
										 },function(){
				$(this).hide();
										 });
		   
		   
		   });

</script>




<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
