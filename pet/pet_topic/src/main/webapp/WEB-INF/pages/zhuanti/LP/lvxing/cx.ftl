<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>【开心驴行】品质旅游线路,价格_品质团旅游怎么样-驴妈妈自发班</title>
<meta name="keywords" content="开心驴行，品质旅游" />
<meta name="description" content="【开心驴行】开班一人也成团，铁定发班，品质旅游保证全程信息公开接受游客全程监督，我们有精选景区、指定酒店、等。选择驴妈妈就是选择品牌的保证！" />
<link rel="stylesheet" href="http://www.lvmama.com/zt/promo/kxlx/css/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<!--[if IE 6]> 
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js"></script>
<script>DD_belatedPNG.fix('.nav_l li,.nav_r li,.tmh_title');</script> 
<![endif]--> 
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="top"><div class="banner"></div></div>
<div class="navbg"><div class="nav"><a href="index.html"><img src="http://www.lvmama.com/zt/promo/kxlx/images/kxlx_09.jpg"></a><img src="http://www.lvmama.com/zt/promo/kxlx/images/kxlx_10.jpg" alt="开心驴行" ></div></div>
<!--主体内容↓↓↓↓↓-->
<div class="wrap">
	<div class="main">
    <!--我们的理念--><a name="a01"></a><img src="http://www.lvmama.com/zt/promo/kxlx/images/item2.gif" width="1002" height="117" alt="开心驴行" />
    <div class="siftbox">
    <!--当季超值--><a name="a02"></a>
    <img src="http://www.lvmama.com/zt/promo/kxlx/images/proboxtit01.gif" class="siftboxtit1" />	
    <div class="proboxwrap">
   	  <@s.iterator value="map.get('${station}_rmcx')" status="st">
   	  <div class="probox">
            	<img src="${imgUrl?if_exists}" class="pro1"/>
            	<div class="protxt">
            		<a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                	<span class="prospan">${bakWord1?if_exists}</span>
                	<p class="prop"><em>&yen;</em><span>${memberPrice?if_exists?replace(".0","")}</span>起<a href="${url?if_exists}" target="_blank"><img src="http://www.lvmama.com/zt/promo/kxlx/images/btn.gif" /></a></p>
                	<div class="opacity"></div>
                	<p class="opacitytxt">满意度：<span>${bakWord3?if_exists}</span><br />回访数：<span>${bakWord4?if_exists}</span></p>
            	</div>
      </div>
      </@s.iterator>
   	                                  
    </div><!--proboxwrap end-->
    
    <!--开心驴行精品推荐--><a name="a03"></a>  
    <img src="http://www.lvmama.com/zt/promo/kxlx/images/kxlx_12.gif" class="siftboxtit" />
     <@s.iterator value="map.get('${station}_cpcx')" status="st">
    <div class="arrowtit">${title?if_exists}<span>${bakWord1?if_exists}</span></div>   
    <div class="proboxwrap">
      <@s.iterator value="map.get('${station}_cpcx_${st.index + 1}')" status="sts">
   	  <div class="probox1">
       	<img src="${imgUrl?if_exists}" width="231" height="193" />
        <img src="http://www.lvmama.com/zt/promo/kxlx/images/kxlx_13.gif" height="6" width="231" class="arrow" />
      	<a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
      	<p class="prop"><em>&yen;</em><span>${memberPrice?if_exists?replace(".0","")}</span>起</p>
      	<div class="opacity"></div>
      	<p class="opacitytxt">满意度：<span>${bakWord3?if_exists}</span><br />回访数：<span>${bakWord4?if_exists}</span></p> 
      	<div class="opacity2"></div>
      	<p class="opacitytxt2">${bakWord1?if_exists}</p>         
      </div> 
      </@s.iterator>
             
    </div><!--proboxwrap end-->
    </@s.iterator> 
    
	<@s.iterator value="map.get('${station}_qtcx')" status="st">
    <div style="height:107px;margin-top:20px;"><img src="http://www.lvmama.com/zt/promo/kxlx/images/kxlxBtn01.gif" width="1002" height="107" border="0" usemap="#Map"><map name="Map"><area shape="rect" coords="147,21,451,85" href="${bakWord1?if_exists}" target="_blank"><area shape="rect" coords="530,20,839,85" href="${bakWord2?if_exists}" target="_blank"></map></div>
    </@s.iterator>
   
    <!--QA--><a name="a06"></a>
    <div class="qa">       
    <img src="http://www.lvmama.com/zt/promo/kxlx/images/kxlx_14.gif" width="970" height="24" class="siftboxtit4">
    	<@s.iterator value="map.get('${station}_qacx')" status="st">
    	<dl>
        <dt><b>Q：</b><p>${bakWord1?if_exists}</p></dt>
        <dd><b>A：</b><p>${bakWord2?if_exists}</p></dd>
        </dl>
        </@s.iterator>
    </div><!--qa end-->
    
    </div><!--siftbox end-->
    </div>

    <!--照片分享--><a name="a07"></a>
    <div class="xiangce">
	<div class="bigImg_all">
    <ul class="bigImg_box">
        <@s.iterator value="map.get('${station}_lxtpcx')" status="st">
        <li>
        	<img width="911" height="415" src="${imgUrl?if_exists}" alt="品质旅游" />
        	<div class="imgText imgText_bg"></div>
            <div class="imgText">
            	<h4>${title?if_exists}</h4>
                <p>${bakWord1?if_exists}</p>
            </div>
        </li>
        </@s.iterator>
    </ul>
    <span class="left1"></span>
    <span class="right1"></span>
</div>
<div class="smallImg_all">
    <div class="smallImg_box">
        <ul>
          	<@s.iterator value="map.get('${station}_lxtpcx')" status="st">
            <li><img width="96" height="96" src="${bakWord2?if_exists}" alt="品质旅游线路" /></li>
            </@s.iterator>
        </ul>
    </div>
    <span class="left2"></span>
    <span class="right2"></span>
</div>
</div>

<!--微博分享-->
	<div class="weiboBox">
    	<div class="weiboL">
        	<iframe width="700" height="320"  frameborder="0" scrolling="no" src="http://widget.weibo.com/livestream/listlive.php?language=zh_cn&width=700&height=320&uid=3273046782&skin=1&refer=1&appkey=&pic=0&titlebar=1&border=1&publish=1&atalk=1&recomm=0&at=0&atopic=%E5%BC%80%E5%BF%83%E9%A9%B4%E8%A1%8C&ptopic=%E5%BC%80%E5%BF%83%E9%A9%B4%E8%A1%8C&dpc=1"></iframe>
        </div>
        <@s.iterator value="map.get('${station}_wb')" status="st">
        <div class="weiboR"><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="236" height="296" alt="开心旅行"></a></div>
         </@s.iterator>
    </div>

</div><!--wrap end-->

<!--热门活动--><a name="a08"></a>
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

<script type="text/javascript" src="http://www.lvmama.com/zt/promo/kxlx/js/kaixin.js"></script>
<!--pop-->
<div class="sp_pop" ><img src="http://www.lvmama.com/zt/promo/kxlx/images/frimg.png" width="158" height="270" border="0" usemap="#Map2">
  <map name="Map2">
    <area shape="rect" coords="4,39,156,75" href="#a01">
    <area shape="rect" coords="4,81,157,112" href="#a02">
    <area shape="rect" coords="6,119,153,153" href="#a03">
    <area shape="rect" coords="8,159,154,191" href="#a06">
    <area shape="rect" coords="3,196,155,231" href="#a07">
    <area shape="rect" coords="6,236,158,269" href="#a08">
  </map>
</div>
<!--pop-->
<!--营销点代码:驴妈妈旅游网_到达--> 
<script type="text/javascript" src="http://aw.kejet.net/t?p=gq&c=im"></script>
<script src="http://s15.cnzz.com/stat.php?id=5199293&web_id=5199293&show=pic1" language="JavaScript"> </script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
