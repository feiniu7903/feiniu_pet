<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>2014成都五一旅游_成都及周边五一线路,价格,推荐-驴妈妈旅游网</title> 
<meta name="keywords" content="成都五一,成都五一旅游,成都周边,线路"> 
<meta name="description" content="2014五一劳动节：驴妈妈旅游网推荐成都五一旅游线路,成都五一劳动节旅游去哪里玩，五一劳动节成都周边旅游线路;以优质的服务提供您一个愉快五一旅游度假！"> 
<link rel="stylesheet" href="http://pic.lvmama.com/styles/zt/wuyi/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="wrap">
	<div class="main">
        <div class="banner"></div>
        
        <div class="nav" id="nav">
            <a class="nav1" href="http://www.lvmama.com/zt/lvyou/wuyi_1/" target="_self"><span></span></a>
            <a class="nav2 navLi" href="http://www.lvmama.com/zt/lvyou/wuyi_1/cd.html" target="_self"><span></span></a>
            <a class="nav3" href="http://www.lvmama.com/zt/lvyou/wuyi_1/bj.html" target="_self"><span></span></a>
            <a class="nav4" href="http://www.lvmama.com/zt/lvyou/wuyi_1/gz.html" target="_self"><span></span></a>
        </div>
        
        <div class="areaList">
        	<div class="section" id="md1">
        		<h3><a href="http://www.lvmama.com/ticket" target="_blank"></a></h3>
                <div class="pro">
            		<ul class="proList">
            		<@s.iterator value="map.get('${station}_cd_place')" status="st">
                        <li>
                            <img src="${imgUrl?if_exists}" width="224" height="150" alt="">
                            <a class="chanpin" href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a>
                            <div class="pricebox">
                                <p>
                                    <span>￥<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                    <del>原价：￥${marketPrice?if_exists?replace(".0","")}</del>
                                </p>
                                <a href="${url?if_exists}" target="_blank"></a>
                            </div>
                        </li>
                       </@s.iterator> 
                    </ul>
                </div>
            </div>
            
            <div class="section" id="md2">
        		<h3 class="tit2"><a href="http://www.lvmama.com/freetour" target="_blank"></a></h3>
                <div class="pro2">
                	<ul class="proNav">
                    	<li class="proNavLi"><span class="proNav1"></span></li>
                    	<li><span class="proNav2"></span></li>
                    </ul>
            		<ul class="proList" style="display:block;">
                      <@s.iterator value="map.get('${station}_cd_ambitus_free')" status="st">
                        <li>
                            <img src="${imgUrl?if_exists}" width="224" height="150" alt="${title?if_exists}">
                            <a class="chanpin" href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a>
                            <div class="pricebox">
                                <p>
                                    <span>￥<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                    <del>原价：￥${marketPrice?if_exists?replace(".0","")}</del>
                                </p>
                                <a href="${url?if_exists}" target="_blank"></a>
                            </div>
                        </li>
				 	</@s.iterator>
                    </ul>
                    <ul class="proList">
                        <@s.iterator value="map.get('${station}_cd_ambitus_group')" status="st">
                        <li>
                            <img src="${imgUrl?if_exists}" width="224" height="150" alt="${title?if_exists}">
                            <a class="chanpin" href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a>
                            <div class="pricebox">
                                <p>
                                    <span>￥<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                    <del>原价：￥${marketPrice?if_exists?replace(".0","")}</del>
                                </p>
                                <a href="${url?if_exists}" target="_blank"></a>
                            </div>
                        </li>
                       </@s.iterator>
                    </ul>
                </div>
            </div>
            
            <div class="section" id="md3">
        		<h3 class="tit3"><a href="http://www.lvmama.com/destroute" target="_blank"></a></h3>
                <div class="pro2">
                	<ul class="proNav">
                    	<li class="proNavLi"><span class="proNav1"></span></li>
                    	<li><span class="proNav2"></span></li>
                    </ul>
            		<ul class="proList" style="display:block;">
                        <@s.iterator value="map.get('${station}_cd_home_free')" status="st">
                        <li>
                            <img src="${imgUrl?if_exists}" width="224" height="150" alt="${title?if_exists}">
                            <a class="chanpin" href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a>
                            <div class="pricebox">
                                <p>
                                    <span>￥<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                    <del>原价：￥${marketPrice?if_exists?replace(".0","")}</del>
                                </p>
                                <a href="${url?if_exists}" target="_blank"></a>
                            </div>
                        </li>
                        </@s.iterator>
                    </ul>
                    <ul class="proList">
                       <@s.iterator value="map.get('${station}_cd_home_group')" status="st">
                        <li>
                            <img src="${imgUrl?if_exists}" width="224" height="150" alt="${title?if_exists}">
                            <a class="chanpin" href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a>
                            <div class="pricebox">
                                <p>
                                    <span>￥<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                    <del>原价：￥${marketPrice?if_exists?replace(".0","")}</del>
                                </p>
                                <a href="${url?if_exists}" target="_blank"></a>
                            </div>
                        </li>
                        </@s.iterator>
                    </ul>
                </div>
            </div>
            
            <div class="section" id="md4">
        		<h3 class="tit4"><a href="http://www.lvmama.com/abroad" target="_blank"></a></h3>
                <div class="pro2">
                	<ul class="pro-nav">
                    	<li class="pro-nav1 pro-nav-li"><span></span></li>
                    	<li class="pro-nav2"><span></span></li>
                        <li class="pro-nav3"><span></span></li>
                        <li class="pro-nav4"><span></span></li>
                        <li class="pro-nav5"><span></span></li>
                    </ul>
            		  <!--东南亚-->
            		<ul class="proList" style="display:block;">
            			<@s.iterator value="map.get('${station}_cd_southeast_asia')" status="st">
                        <li>
                            <img src="${imgUrl?if_exists}" width="224" height="150" alt="${title?if_exists}">
                            <a class="chanpin" href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a>
                            <div class="pricebox">
                                <p>
                                    <span>￥<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                    <del>原价：￥${marketPrice?if_exists?replace(".0","")}</del>
                                </p>
                                <a href="${url?if_exists}" target="_blank"></a>
                            </div>
                        </li>
                        </@s.iterator>
                    </ul>
                    <!--海岛 sh_island -->
                    <ul class="proList">
                   	 <@s.iterator value="map.get('${station}_cd_island')" status="st">
                        <li>
                            <img src="${imgUrl?if_exists}" width="224" height="150" alt="${title?if_exists}">
                            <a class="chanpin" href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a>
                            <div class="pricebox">
                                <p>
                                    <span>￥<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                    <del>原价：￥${marketPrice?if_exists?replace(".0","")}</del>
                                </p>
                                <a href="${url?if_exists}" target="_blank"></a>
                            </div>
                        </li>
                    </@s.iterator>
                    </ul>
                    <!--港澳日韩-->
                    <ul class="proList">
                    <@s.iterator value="map.get('${station}_cd_garh')" status="st">
                        <li>
                            <img src="${imgUrl?if_exists}" width="224" height="150" alt="${title?if_exists}">
                            <a class="chanpin" href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a>
                            <div class="pricebox">
                                <p>
                                    <span>￥<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                    <del>原价：￥${marketPrice?if_exists?replace(".0","")}</del>
                                </p>
                                <a href="${url?if_exists}" target="_blank"></a>
                            </div>
                        </li>
                     </@s.iterator>   
                    </ul>
                    
                    <!--欧美澳非-->
                    <ul class="proList">
                    <@s.iterator value="map.get('${station}_cd_omaf')" status="st">
                        <li>
                            <img src="${imgUrl?if_exists}" width="224" height="150" alt="${title?if_exists}">
                            <a class="chanpin" href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a>
                            <div class="pricebox">
                                <p>
                                    <span>￥<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                    <del>原价：￥${marketPrice?if_exists?replace(".0","")}</del>
                                </p>
                                <a href="${url?if_exists}" target="_blank"></a>
                            </div>
                        </li>
                     </@s.iterator>     
                    </ul>
                    
                    <!--油轮-->
                    <ul class="proList">
                   	 <@s.iterator value="map.get('${station}_cd_youlun')" status="st">
                        <li>
                            <img src="${imgUrl?if_exists}" width="224" height="150" alt="${title?if_exists}">
                            <a class="chanpin" href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a>
                            <div class="pricebox">
                                <p>
                                    <span>￥<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                    <del>原价：￥${marketPrice?if_exists?replace(".0","")}</del>
                                </p>
                                <a href="${url?if_exists}" target="_blank"></a>
                            </div>
                        </li>
                       </@s.iterator>   
                    </ul>
                </div>
            </div>
            
        <div class="section" id="md5">
        	<h3 class="tit5"></h3>
        	<div class="pro2">
            	<div class="num">
                    <ul class="numList">
                        <@s.iterator value="map.get('${station}_sh_activity_notice')"  status="st">
                    	 <@s.if test="#st.first">
                    		 <li class="numLi">${st.index+1}</li>
                    	 </@s.if>
                    	 <@s.else>
                       		 <li>${st.index+1}</li>
                    	</@s.else>
                    </@s.iterator>
                    </ul>
                </div>
                <div class="hd">
                <@s.iterator value="map.get('${station}_sh_activity_notice')"  status="st">
                	<@s.if test="#st.first">
                	<div class="hdList" style="display:block;">
                    	<a class="hdL" href="${url?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="500" height="260" alt=""></a>
                    	<div class="hdR">
                        	<h4><a href="${url?if_exists}" target="_blank">${title?if_exists}</a></h4>
                        	<p>${remark?if_exists}</p>
                            <div class="hdIntro">
                            	<span>活动时间：${bakWord1?if_exists}</span>
                                <a class="show" href="${url?if_exists}" target="_blank"></a>
                                <a class="hidden" href="javascript:void(0)"></a>
                            </div>
                        </div>
                    </div>
                	</@s.if>
                	
				 	<@s.else>
                    <div class="hdList">
                    	<a class="hdL" href="${url?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="500" height="260" alt=""></a>
                    	<div class="hdR">
                        	<h4><a href="${url?if_exists}" target="_blank">${title?if_exists}</a></h4>
                        	<p>${remark?if_exists}</p>
                            <div class="hdIntro">
                            	<span>活动时间：${bakWord1?if_exists}</span>
                                <a class="show" href="${url?if_exists}" target="_blank"></a>
                                <a class="hidden" href="javascript:void(0)"></a>
                            </div>
                        </div>
                    </div>
                  </@s.else>  
                 </@s.iterator>  
                </div>
            </div>
        </div>
            <div class="bottom">
            	<div class="bottombg"></div>
            </div>
        </div>

    </div>
</div>
<div class="sideBg">
	<div class="sideNav">
        <a href="#md1" target="_self"><span class="side1"></span></a>
        <a href="#md2" target="_self"><span class="side2"></span></a>
        <a href="#md3" target="_self"><span class="side3"></span></a>
        <a href="#md4" target="_self"><span class="side4"></span></a>
        <a href="#md5" target="_self"><span class="side5"></span></a>
        <a href="#" target="_self"><span class="side6"></span></a>
    </div>
</div>








<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
<script src="http://pic.lvmama.com/js/jquery-1.7.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/wuyi/index.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
<script src="http://www.lvmama.com/zt/000global/js/eventCM.js" type="text/javascript"></script>
</body>
</html>
