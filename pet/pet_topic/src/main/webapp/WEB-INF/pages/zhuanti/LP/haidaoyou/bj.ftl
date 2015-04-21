<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>北京出发海岛游_海岛旅游好去处_海岛游线路推荐-驴妈妈旅游网</title>
<meta name="keywords" content="海岛游, 海岛旅游,旅游"/>
<meta name="description" content="驴妈妈推出海岛旅游游好去处，介绍北京出发海岛游给驴友们不一样的海岛游的新体验，同时挑选海岛游线路推荐，有美丽的济州岛，还有天堂般的马尔代夫群岛，一起海岛游吧！" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/zt/haidao/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery142.js"></script>

</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->

<div class="main_all">
	<ul class="nav_cfd">
    	<li class="width_80"><a href="index.html">江/浙/沪</a></li>
        <li class="width_125"><a href="gz.html">广州/深圳/香港</a></li>
        <li class="width_90"><a href="cd.html">成都/重庆</a></li>
        <li class="width_40"><a href="bj.html">北京</a></li>
    </ul>
    
<!--A.B.C.D-->
    <div class="list1 taiguo">
    	<span></span>
        <dl>
        	<dt><a target="_blank" href="http://www.lvmama.com/search/abroad-from-%E5%8C%97%E4%BA%AC-to-%E6%B3%B0%E5%9B%BD-route.html">泰国</a></dt>
            
        		<dd>
				<@s.iterator value="map.get('${station}_bj_1')" status="st">
				<a href="${url?if_exists}">${title?if_exists}</a>/
				 </@s.iterator>
        		</dd>
           
        </dl>
    </div>
    <div class="list1 yuenan">
    	<span></span>
        <dl>
        	<dt><a target="_blank" href="http://www.lvmama.com/search/abroad-from-%E5%8C%97%E4%BA%AC-to-%E8%B6%8A%E5%8D%97-route.html">越南</a></dt>
           
        		<dd>
				 <@s.iterator value="map.get('${station}_bj_2')" status="st">
				 <a href="${url?if_exists}">${title?if_exists}</a>/
				 </@s.iterator>
        		</dd>
            
        </dl>
    </div>
    <div class="list1 jianpuzhai">
    	<span></span>
        <dl>
        	<dt><a target="_blank" href="http://www.lvmama.com/search/abroad-from-%E5%8C%97%E4%BA%AC-to-%E6%9F%AC%E5%9F%94%E5%AF%A8-route.html">柬埔寨</a></dt>
             
        		<dd>
				<@s.iterator value="map.get('${station}_bj_3')" status="st">
				<a href="${url?if_exists}">${title?if_exists}</a>/
				 </@s.iterator>
        		</dd>
           
        </dl>
    </div>
    <div class="list1 malaixiya">
    	<span></span>
        <dl>
        	<dt><a target="_blank" href="http://www.lvmama.com/search/abroad-from-%E5%8C%97%E4%BA%AC-to-%E9%A9%AC%E6%9D%A5%E8%A5%BF%E4%BA%9A-route.html">马来西亚</a></dt>
             
        		<dd>
				<@s.iterator value="map.get('${station}_bj_4')" status="st">
				<a href="${url?if_exists}">${title?if_exists}</a>/
				</@s.iterator>
        		</dd>
            
        </dl>
    </div>
    
<!--墓碑-->

    <a class="list2 mlqs" target="_blank" href="http://www.lvmama.com/dest/feizhou_maoliqiusi/dest2dest_tab_frm1">毛里求斯</a>
    <a class="list2 medf" target="_blank" href="http://www.lvmama.com/dest/yazhou_maerdaifu/dest2dest_tab_frm1">马尔代夫</a>
    <a class="list2 sllk" target="_blank" href="http://www.lvmama.com/dest/yazhou_sililanka/dest2dest_tab_frm1">斯里兰卡</a>
    <a class="list2 bld" target="_blank" href="http://www.lvmama.com/dest/yindunixiya_balidao/dest2dest_tab_frm1">巴厘岛</a>
    <a class="list2 flb" target="_blank" href="http://www.lvmama.com/dest/yazhou_feilvbin/dest2dest_tab_frm1">菲律宾</a>
    <a class="list2 jzd" target="_blank" href="http://www.lvmama.com/dest/hanguo_jizhoudao/dest2dest_tab_frm1">济州岛</a>
    <a class="list2 gd" target="_blank" href="http://www.lvmama.com/dest/meiguo_guandao/dest2dest_tab_frm1">关岛</a>
    <a class="list2 sb" target="_blank" href="http://www.lvmama.com/dest/dayanghzhou_saibandao/dest2dest_tab_frm1">塞班</a>
    <a class="list2 xwy" target="_blank" href="http://www.lvmama.com/dest/meiguo_xiaweiyi/dest2dest_tab_frm1">夏威夷</a>
    
    <div class="bqrm">
    
    	<ul class="bqrm_list">
		<@s.iterator value="map.get('${station}_cp_bj')" status="st">
        	<li><h4><a target="_blank" href="${url?if_exists}">${title?if_exists}</a></h4><p>￥<span>${memberPrice?if_exists?replace(".0","")}</span>起</p></li>
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
