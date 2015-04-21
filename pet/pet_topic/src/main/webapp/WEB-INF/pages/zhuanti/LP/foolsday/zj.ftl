<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>2014愚人节旅游_愚人节有什么好玩的,活动-驴妈妈旅游网</title>
<meta content="愚人节,愚人节旅游" name="keywords" />
<meta content="2014愚人节：愚人节有什么好玩的,去哪里玩，愚人节有什么活动，2014愚人节不开玩笑，不恶搞,和小伙伴一起去旅游。2014愚人节，就找驴妈妈。" name="description" />
<link rel="stylesheet" href="http://s2.lvjs.com.cn/styles/zt/foolsday/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<base target="_blank">
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="wrap">	
    <div class="banner">
    	<a class="md1" href="#tuangou" target="_self"></a>
    	<a class="md2" href="#group" target="_self"></a>
        <a class="md3" href="#ticket" target="_self"></a>
        <a class="md4" href="#freetour" target="_self"></a>
        <a class="md5" href="#abroad" target="_self"></a>
    </div>
    <div class="main" id="main">
    	<div class="ticket" id="ticket">
        	<h3>
        	<@s.iterator value="map.get('${station}_zj_ticket_more')"  var="var" status="st">
        	  <@s.if test="#st.first"><a href="${url?if_exists}">>>更多推荐</a></@s.if>
        	</@s.iterator> 
        	</h3>
            <ul class="proList">
             <@s.iterator value="map.get('${station}_zj_ticket')"  var="var" status="st">
            	<li>
                	<div class="intro">
                    	<img src="${imgUrl?if_exists}" width="210" height="140" alt="">
                        <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                        <div class="priceBox">
                        	<span>¥<b>${memberPrice?if_exists}</b></span>
                            <a href="${url?if_exists}"></a>
                        </div>
                    </div>
                </li>
                </@s.iterator>
            </ul>
        </div>
        <div class="bottom1"></div>
        <div class="ticket freetour" id="freetour">
            <h3>
        	<@s.iterator value="map.get('${station}_zj_freetour_more')"  var="var" status="st">
        	  <@s.if test="#st.first"><a href="${url?if_exists}">>>更多推荐</a></@s.if>
        	</@s.iterator> 
        	</h3>
            <ul class="proList">
              <@s.iterator value="map.get('${station}_zj_freetour')"  var="var" status="st">
            	<li>
                	<div class="intro">
                    	<img src="${imgUrl?if_exists}" width="210" height="140" alt="">
                        <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                        <div class="priceBox">
                        	<span>¥<b>${memberPrice?if_exists}</b></span>
                            <a href="${url?if_exists}"></a>
                        </div>
                    </div>
                </li>
                 </@s.iterator>
            </ul>
        </div>
        <div class="bottom1 bottom2"></div>
        <div class="ticket group" id="group">
        	 <h3>
        	<@s.iterator value="map.get('${station}_zj_group_more')"  var="var" status="st">
        	  <@s.if test="#st.first"><a href="${url?if_exists}">>>更多推荐</a></@s.if>
        	</@s.iterator> 
        	</h3>
            <ul class="proList">
            	 <@s.iterator value="map.get('${station}_zj_group')"  var="var" status="st">
            	<li>
                	<div class="intro">
                    	<img src="${imgUrl?if_exists}" width="210" height="140" alt="">
                        <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                        <div class="priceBox">
                        	<span>¥<b>${memberPrice?if_exists}</b></span>
                            <a href="${url?if_exists}"></a>
                        </div>
                    </div>
                </li>
                 </@s.iterator>
            </ul>
        </div>
        <div class="bottom1"></div>
        <div class="ticket freetour abroad" id="abroad">
        	 <h3>
        	<@s.iterator value="map.get('${station}_zj_abroad_more')"  var="var" status="st">
        	  <@s.if test="#st.first"><a href="${url?if_exists}">>>更多推荐</a></@s.if>
        	</@s.iterator> 
        	</h3>
            <ul class="proList">
            	 <@s.iterator value="map.get('${station}_zj_abroad')"  var="var" status="st">
            	<li>
                	<div class="intro">
                    	<img src="${imgUrl?if_exists}" width="210" height="140" alt="">
                        <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                        <div class="priceBox">
                        	<span>¥<b>${memberPrice?if_exists}</b></span>
                            <a href="${url?if_exists}"></a>
                        </div>
                    </div>
                </li>
                 </@s.iterator>
            </ul>
        </div>
        <div class="bottom1 bottom2"></div>
        <div class="ticket tuangou" id="tuangou">
        	 <h3>
        	<@s.iterator value="map.get('${station}_zj_tuangou_more')"  var="var" status="st">
        	  <@s.if test="#st.first"><a href="${url?if_exists}">>>更多推荐</a></@s.if>
        	</@s.iterator> 
        	</h3>
            <ul class="proList">
            	 <@s.iterator value="map.get('${station}_zj_tuangou')"  var="var" status="st">
            	<li>
                	<div class="intro">
                    	<img src="${imgUrl?if_exists}" width="210" height="140" alt="">
                        <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                        <div class="priceBox">
                        	<span>¥<b>${memberPrice?if_exists}</b></span>
                            <a href="${url?if_exists}"></a>
                        </div>
                    </div>
                </li>
                 </@s.iterator>
            </ul>
        </div>
    </div>
</div>
<div class="nav">
	<img src="http://s2.lvjs.com.cn/img/zt/foolsday/navbg.png" alt="" width="181" height="355" border="0" usemap="#Map">
    <map name="Map">
      <area shape="poly" coords="39,215,151,215,145,263,70,291,44,270" href="http://www.lvmama.com/zt/lvyou/foolsday/" target="_self">
      <area shape="poly" coords="40,269,15,287,15,309,28,329,62,327,78,299" href="#" target="_self">
    </map>
    <ul class="navList">
   	   	<li ><a href="http://www.lvmama.com/zt/lvyou/foolsday/" target="_self">上海</a></li>
        <li ><a href="http://www.lvmama.com/zt/lvyou/foolsday/js.html#main" target="_self">江苏</a></li>
        <li  class="navLi"><a href="http://www.lvmama.com/zt/lvyou/foolsday/zj.html#main" target="_self">浙江</a></li>
        <li ><a href="http://www.lvmama.com/zt/lvyou/foolsday/bj.html#main" target="_self">北京</a></li>
        <li><a href="http://www.lvmama.com/zt/lvyou/foolsday/gz.html#main" target="_self">广州</a></li>
        <li><a href="http://www.lvmama.com/zt/lvyou/foolsday/cd.html#main" target="_self">成都</a></li>
  </ul>
</div>

<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
<script src="http://pic.lvmama.com/js/jquery-1.7.js"></script>
<script type="text/javascript" src="http://s2.lvjs.com.cn/js/zt/foolsday/index.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
