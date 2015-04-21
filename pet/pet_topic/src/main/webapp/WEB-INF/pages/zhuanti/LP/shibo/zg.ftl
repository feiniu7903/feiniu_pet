 <!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>上海世博园_中国馆开放时间_世博园有什么好玩-驴妈妈旅游网</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" href="http://www.lvmama.com/zt/promo/shibohui/css/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery-1.7.js"></script>
<base target="_blank">
<style>
body{ background:url(http://www.lvmama.com/zt/promo/shibohui/images/china_bg.jpg) no-repeat center 51px #b00000;}
</style>
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="main_all">
	<div class="china_banner1">
    	<h1></h1>
    </div>
	<div class="china_navBox">
    	<ul class="navTop navTop2">
        	<li>
            	<h3><a href="http://www.lvmama.com/zt/lvyou/shibo/" target="_self">意大利馆<b>ltaly</b></a></h3>
                
                <a href="http://e.weibo.com/itssic">意大利馆微博</a><br>
                <a href="http://www.italiancenter.cn">意大利馆官网</a>
            </li>
            <li >
            	<h3><a href="http://www.lvmama.com/zt/lvyou/shibo/st.html" target="_self">沙特馆<b>Saudi</b></a></h3>
                
            </li>
            <li class="mar navTop_li">
            	<h3><a href="http://www.lvmama.com/zt/lvyou/shibo/zg.html" target="_self">中国馆<b>China</b></a></h3>
                
            </li>
        </ul>
    </div>
    <@s.iterator value="map.get('${station}_hd')" status="st">
    <div class="ydl_hd">
    	<a href="${url?if_exists}"><img class="ydl_hd_l" src="${imgUrl?if_exists}" width="657" height="271" alt=""></a>
        <div class="ydl_hd_r">
        	<p>${bakWord1?if_exists}</p>
        </div>
    </div>
    </@s.iterator>
    <div class="china_xiuchang">
    	<img src="http://www.lvmama.com/zt/promo/shibohui/images/china_xiu1.jpg" width="1002" height="713" alt="">
    </div>
    
    
    <div class="jp_box">
    	<h3 class="ydl_qy_title">意大利馆精推产品</h3>
       	<div class="qy_box">
        	<div class="qy_l">
            	<@s.iterator value="map.get('${station}_zgtt')" status="st">
            	<div class="qy_first">
                	<img class="qy_first_l" src="${imgUrl?if_exists}" width="190" height="119" alt="">
                    <div class="qy_first_r">
                    	<h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                        <p>★${bakWord1?if_exists}</p>
                        <div class="qy_firstJiage">
                        	<p>￥<span>${memberPrice?if_exists?replace(".0","")}</span>起</p>
                            <a href="${url?if_exists}"></a>
                        </div>
                    </div>
                </div>
              </@s.iterator>
                    <ul class="qy_list">
                        <@s.iterator value="map.get('${station}_zgjt')" status="st">
                        <li>
                            <span><em>￥</em><b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                            <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                            <p>★${bakWord1?if_exists}</p>
                        </li>
                     </@s.iterator>
                     <@s.iterator value="map.get('${station}_zgqt')" status="st">
                        <li class="more"> <a href="${url?if_exists}" target="_blank">更多&gt;&gt;</a></li>
                     </@s.iterator>
                    </ul>
            </div>
             <@s.iterator value="map.get('${station}_zggl')" status="st">
            <div class="qy_r">
                <h4>【${title?if_exists}】</h4>
                <img src="${imgUrl?if_exists}" width="254" height="144" alt="">
                <p>${bakWord1?if_exists}<a href="${url?if_exists}" target="_blank">[详情]</a></p>
            </div>
            </@s.iterator>
        </div>
    </div>
    
</div>
<div class="xs_box">
    	<h3 class="china_xs_title">意大利馆精推产品</h3>
        <div class="china_xs">
                <ul class="china_xs_list">
                     <@s.iterator value="map.get('${station}_zgtp')" status="st">
                    <li style="display:block">
                    	<img src="${imgUrl?if_exists}" width="693" height="360" alt="">
                        <div class="text">
                            <h4><a href="${url?if_exists}" target="_blank">${title?if_exists}</a></h4>
                            <p>${bakWord1?if_exists}</p>
                        </div>
                    </li>
            </@s.iterator>

                </ul>
                 <ul class="xs_r">
                    <@s.iterator value="map.get('${station}_zgtp')" status="st">
                    <li><img src="${imgUrl?if_exists}" width="221" height="109" alt=""><span></span></li>
            </@s.iterator>
                </ul>
             
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

	$('.jp_list_r h4').hover(function(){
		var _title = $(this).find('a').text();
		$(this).find('a').attr("title",_title);
	}); 
	
	$('.xs_r li').click(function(){ 
		var _num = $(this).index();
		$(this).addClass('xs_r_li').siblings().removeClass('xs_r_li');
		$('.china_xs_list li').eq(_num).show().siblings().hide();
	});

});

</script>

<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
