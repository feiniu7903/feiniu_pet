<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>南京出发到海南旅游_南京去海南旅游线路价格,多少钱-驴妈妈旅游网</title>
<meta name="keywords" content="海南旅游，南京 " />
<meta name="description" content="独立发团南京出发到海南旅游，南京去海南旅游线路价格,不用问南京去海南旅游线路多少钱,驴妈妈给您一个非凡的海南国际旅游岛体验。" />
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<link rel="stylesheet" href="http://www.lvmama.com/zt/promo/hainan/css/hainan.css">
<base target="_blank">
</head>
<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->

<div class="wrap">
	<div class="banner">
    	<div class="nav">
        	<span class="navl"></span>
            <span class="navc"><a href="index.html" target="_self">上海</a><a href="hz.html" target="_self">杭州</a><a href="#" target="_self" class="cur">南京</a><a href="bj.html" target="_self">北京</a></span>
            <span class="navr"></span>
        </div>
    </div>
    <div class="main">
    <@s.iterator value="map.get('${station}_njdb')" status="st">
    	<div class="topPro">
        	<img src="${imgUrl?if_exists}" width="429" height="275" class="topProImg" />
            <div class="topProText">
            <a href="${url?if_exists}" class="topProa">${title?if_exists}</a>
            <ul>
            <li>心动指数：<i class="star${bakWord1?if_exists}"></i></li>
            <li>艳遇指数：<i class="star${bakWord2?if_exists}"></i></li>
            <li>摄影指数：<i class="star${bakWord3?if_exists}"></i></li>
            <li>新颖指数：<i class="star${bakWord4?if_exists}"></i></li>
            </ul>
            <p class="topProInfo">
            ${bakWord5?if_exists}<br />
			${bakWord6?if_exists}<br />
			${bakWord7?if_exists}<br />
			${bakWord8?if_exists}<br />
            </p>
            <p class="topProPrice">驴妈妈价：<i>&yen;</i><b>${memberPrice?if_exists?replace(".0","")}</b>起<a href="${url?if_exists}"><img src="http://www.lvmama.com/zt/promo/hainan/images/btn.jpg"/></a></p>
            </div>
        </@s.iterator>
        </div>
    	<div class="main2">
        	<div class="mainL">
            	<div class="tabs">
                	<ul class="tabList"><li class="tabList_this">品质跟团游</li><li>热门自由行</li></ul>
                    <!--------------品质跟团游内容↓↓↓-------------->
                    <div class="tabmain jsList tabList_this">
                    	<@s.iterator value="map.get('${station}_njlx')" status="st">
                    	<div class="main2TopPro">
                        	<img src="${imgUrl?if_exists}" class="topProImg" width="180" height="180" />
                            <div class="kxlxPro">
                            <img src="http://www.lvmama.com/zt/promo/hainan/images/kxlxLogo.gif" width="255" height="55">
                            <a href="${url?if_exists}" target="_blank" class="kxlxName">${title?if_exists}</a>
                            <div class="kxlxInfo">
                            	<p class="kxlxText">
                                ${bakWord1?if_exists}<br />
                                ${bakWord2?if_exists}<br />
                                ${bakWord3?if_exists}
                                </p>
                                <p class="kxlxPrice">
                                <span>&yen;<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                <a href="${url?if_exists}" target="_blank"><img src="http://www.lvmama.com/zt/promo/hainan/images/btn.jpg" /></a>
                                </p> 
                            </div>
                            </div>
                        </div>
                        </@s.iterator>
                     	
                    	<ul class="tabList2">
			<@s.iterator value="map.get('${station}_njgt')" status="st">  
                        <@s.if test="#st.isFirst()">
                        <li class="tabList_this">${title?if_exists}</li>
                        </@s.if>
            		<@s.else>
                        <li>${title?if_exists}</li>
			</@s.else>
                      	</@s.iterator>
                        </ul>
                        
                        <!--==============品质跟团游产品列表内容==============-->
                        <@s.iterator value="map.get('${station}_njgt')" status="st"> 
                        <div class="tabmain2 jsList tabList_this">
                        <@s.iterator value="map.get('${station}_njgt_${st.index + 1}')" status="sts">
                        	<dl>
                            <dt><b><i>&yen;</i><span>${memberPrice?if_exists?replace(".0","")}</span>起</b><a href="${url?if_exists}">${title?if_exists}</a></dt>
                            <dd>${bakWord1?if_exists}</dd>
                            </dl>
                        </@s.iterator>
                        </div>
                        </@s.iterator>
                    </div>
                    <!--------------品质跟团游内容↑↑↑-------------->
                    <!--------------热门自由行内容↓↓↓-------------->
                    <div class="tabmain jsList">
                    	<@s.iterator value="map.get('${station}_njlx')" status="st">
                    	<div class="main2TopPro">
                        	<img src="${imgUrl?if_exists}" class="topProImg" width="180" height="180" />
                            <div class="kxlxPro">
                            <img src="http://www.lvmama.com/zt/promo/hainan/images/kxlxLogo.gif" width="255" height="55">
                            <a href="${url?if_exists}" target="_blank" class="kxlxName">${title?if_exists}</a>
                            <div class="kxlxInfo">
                            	<p class="kxlxText">
                                ${bakWord1?if_exists}<br />
                                ${bakWord2?if_exists}<br />
                                ${bakWord3?if_exists}
                                </p>
                                <p class="kxlxPrice">
                                <span>&yen;<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                <a href="${url?if_exists}" target="_blank"><img src="http://www.lvmama.com/zt/promo/hainan/images/btn.jpg" /></a>
                                </p> 
                            </div>
                            </div>
                        </div>
                        </@s.iterator>
                     	
                    	<ul class="tabList2">
			<@s.iterator value="map.get('${station}_njzyx')" status="st">  
                        <@s.if test="#st.isFirst()">
                        <li class="tabList_this">${title?if_exists}</li>
                        </@s.if>
            			<@s.else>
                        <li>${title?if_exists}</li>
			</@s.else>
                      	</@s.iterator>
                        </ul>
                        
                        <!--==============品质跟团游产品列表内容==============-->
                        <@s.iterator value="map.get('${station}_njzyx')" status="st"> 
                        <div class="tabmain2 jsList tabList_this">
                        <@s.iterator value="map.get('${station}_njzyx_${st.index + 1}')" status="sts">
                        	<dl>
                            <dt><b><i>&yen;</i><span>${memberPrice?if_exists?replace(".0","")}</span>起</b><a href="${url?if_exists}">${title?if_exists}</a></dt>
                            <dd>${bakWord1?if_exists}</dd>
                            </dl>
                        </@s.iterator>
                        </div>
                        </@s.iterator>
                    </div>
                    <!--------------热门自由行内容↑↑↑-------------->
                </div><!--tabs选项卡 end-->
                <div class="local-tuan">
				<div class="zt_title"><img src="http://www.lvmama.com/zt/promo/hainan/images/zt_title2.png"></div>
				<ul>
                <@s.iterator value="map.get('${station}_dd')" status="st">
				<li><dfn>&yen;<i>${memberPrice?if_exists?replace(".0","")}</i><span>起</span></dfn><a href="${url?if_exists}">${title?if_exists}</a>
					<p>${bakWord1?if_exists}</p>
				</li>
				</@s.iterator>
				</ul>
                <@s.iterator value="map.get('${station}_njdd')" status="st">
				<@s.if test="#st.index == 0"> 
				<p><a class="link-more" href="${bakWord2?if_exists}">更多&gt;&gt;</a></p>
                </@s.if>
       	 		</@s.iterator> 
				</div>
    			<div class="local-ticket">
				<div class="zt_title"><img src="http://www.lvmama.com/zt/promo/hainan/images/zt_title3.png"></div>
				<ul>
                <@s.iterator value="map.get('${station}_njmp')" status="st">
				<li><img src="${imgUrl?if_exists}">
					<p><a href="${url?if_exists}">${title?if_exists}</a><span>${bakWord1?if_exists}</span></p>
					<p><del>&yen;${marketPrice?if_exists?replace(".0","")}</del><dfn>&yen;<i>${memberPrice?if_exists?replace(".0","")}</i>起</dfn></p>
				</li>
                </@s.iterator> 	
				</ul>
                <@s.iterator value="map.get('${station}_njmp')" status="st">
				<@s.if test="#st.index == 0"> 
				<p><a class="link-more" href="${bakWord2?if_exists}">更多&gt;&gt;</a></p>
                </@s.if>
       	 		</@s.iterator>
				</div>
    			<div class="last-comment">
				<div class="zt_title"><h4>最新点评</h4><a class="link-more" href="http://www.lvmama.com/comment/272-1">点击查看全部点评&gt;&gt;</a></div>
				<ul>
                <@s.iterator value="map.get('${station}_dp')" status="st">
				<li>
					<img src="${imgUrl?if_exists}">
					<h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
					<p>${bakWord1?if_exists}</p>
					<p class="time">${bakWord2?if_exists}</p>
				</li>
                </@s.iterator>
			</ul>
		</div>
      		</div><!--mainL end-->
            <@s.iterator value="map.get('${station}_dt')" status="st">
            <div class="mainR">
                    <div class="hn_box_r1">
               <h3 class="hn_tit3">【海南省地图】</h3>
                <div class="hn_mapBox">
                  <img src="${imgUrl?if_exists}"  alt="">
                  <a id="hn_showbig" href="${url?if_exists}" target="_blank">点击查看大图</a>
                  <!--<div id="hn_mapBigBox"><img src="http://www.lvmama.com/zt/promo/hainan/images/map_s.jpg"  alt=""></div>-->
                </div>
                <ul class="hn_mapInfo">
                   <li>${bakWord1?if_exists}</li>
                   <li>${bakWord2?if_exists}</li>
                   <li>${bakWord3?if_exists}</li>
                   <li>${bakWord4?if_exists}</li>
                </ul>
             </div>
             </@s.iterator>
             
             <@s.iterator value="map.get('${station}_gl')" status="st">
                 <div class="hn_box_gl">
                 <a href="${url?if_exists}" class="hn_box_glImg" target="_blank"><img src="${imgUrl?if_exists}" alt=""></a>
                 <div class="hn_box_glInfo">${bakWord1?if_exists}
                  <p class="hn_glLinkBox"><a href="${url?if_exists}" target="_blank" class="hn_glLink">官方攻略下载</a></p>
                 </div>
       			 </div>
        	</@s.iterator>
                 <ul class="hn_proList">
                 <@s.iterator value="map.get('${station}_yc')" status="st">
                 <li>
                    <a href="${url?if_exists}" class="hn_proListImg" target="_blank"><img src="${imgUrl?if_exists}"></a>
                    <p class="hn_proListInfo">${bakWord1?if_exists}
                       <span class="hn_fBlue">［<a href="${url?if_exists}" target="_blank">详情</a>］</span>
                    </p>
                 </li>
                 </@s.iterator>
       			 </ul>
                </div><!--mainR end-->
        </div><!--main2 end-->  		      
    </div><!--main end-->
    <div class="main_b ">
<div class="footer">
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
</div>
</div>



<script type="text/javascript">
$('.tabList li,.tabList2 li,.tabList3 li').click(function(){ 
		var _num = $(this).index();
		$(this).addClass('tabList_this').siblings().removeClass('tabList_this');
		$(this).parents('ul').siblings('.jsList').eq(_num).show().siblings('.jsList').hide();
	});
</script>

<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
