<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>上海出发到四川旅游_上海去四川旅游线路价格,多少钱-驴妈妈旅游</title>
<meta name="keywords" content="四川，上海，四川旅游" />
<meta name="description" content="驴妈妈推出四川大爱旅游,上海出发到四川旅游用我们的行动去支持四川,有非常合适您的上海去四川旅游线路和价格,去四川旅游人人奉贤一片爱心." />
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery-1.7.js"></script>
<link href="http://www.lvmama.com/zt/promo/ailvyou/css/index.css" rel="stylesheet" type="text/css">
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->

<div class="wrap">
	<img src="http://www.lvmama.com/zt/promo/ailvyou/images/banner.jpg" class="banner" width="1002" height="287">
    <div class="main">   
    <@s.iterator value="map.get('${station}_rm')" status="st"> 
    <div class="topPro">
    	<div class="leftpro">
        	<img src="${imgUrl?if_exists}" width="452" height="289" />
        </div>
        <div class="rightweibo">
        	<div class="rightPro">
            <a href="${url?if_exists}" class="proName">${title?if_exists}</a>
            <p class="proText">${bakWord1?if_exists}</p>
            <strong class="proBtn"><b>&yen;</b><span>${memberPrice?if_exists?replace(".0","")}</span>起<a href="${url?if_exists}" target="_blank"><img src="http://www.lvmama.com/zt/promo/ailvyou/images/btn.jpg" width="130" height="36" /></a></strong>
             <ul class="travel_grade clearfix">
						<li>
							心动指数：
							<span class="grade_cont">
								<em class="s5"></em>
							</span>
						</li>
						<li>
							艳遇指数：
							<span class="grade_cont">
								<em class="s4"></em>
							</span>
						</li>
						<li>
							摄影指数：
							<span class="grade_cont">
								<em class="s5"></em>
							</span>
						</li>
						<li>
							新颖指数：
							<span class="grade_cont">
								<em class="s4"></em>
							</span>
						</li>
					</ul>
                    <ul class="t_list">
						<li>苦才是人生，行走才是青春；</li>
                        <li>幽幽丽江行，幸福转经路；</li>
                        <li>游荡在梅里雪山，爱在卡瓦格博；</li>
                        <li>爱摄影，爱自然，香格里拉致青春</li>
					</ul>
            </div>
            
        </div>
    </div>
    </@s.iterator>
    
    <img src="http://www.lvmama.com/zt/promo/ailvyou/images/titBg.gif" class="siftboxtit" />
     <@s.iterator value="map.get('${station}_cp')" status="st">
    <div class="arrowtit">${title?if_exists}<span>${bakWord1?if_exists}</span></div>   
    <div class="proboxwrap">
      <@s.iterator value="map.get('${station}_cp_${st.index + 1}')" status="sts">
   	  <div class="probox1">
       	<img src="${imgUrl?if_exists}" width="231" height="193" />
        <img src="http://www.lvmama.com/zt/promo/kxlx/images/kxlx_13.gif" height="6" width="231" class="arrow" />
      	<a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
      	<p class="prop"><em>&yen;</em><span>${memberPrice?if_exists?replace(".0","")}</span>起</p>
      	<div class="opacity2"></div>
      	<p class="opacitytxt2">${bakWord1?if_exists}</p>         
      </div> 
      </@s.iterator>

    </div><!--proboxwrap end-->
    </@s.iterator>
    <!--weibo-->
    <div class="weiboBox">
    	
        <div class="weiboL">
        <iframe width="670" height="296"  frameborder="0" scrolling="no" src="http://widget.weibo.com/livestream/listlive.php?language=zh_cn&width=670&height=296&uid=2270881102&skin=1&refer=1&appkey=&pic=0&titlebar=1&border=1&publish=1&atalk=1&recomm=0&at=0&dpc=1"></iframe>
        </div>
        <div class="weiboR">
        <a href="#" target="_blank"><img src="http://s1.lvjs.com.cn/uploads/pc/place2/16018/1369966266384.jpg" width="236" height="296" alt=""></a>
        </div>
</div>
<!--weibo end-->
    </div><!--main end-->
</div><!--wrap end-->


<!--活动进行中-->
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

<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
