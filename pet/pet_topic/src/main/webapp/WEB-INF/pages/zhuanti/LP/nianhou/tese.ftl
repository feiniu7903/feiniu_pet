<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>2013特价游推荐_特价游线路报价，价格-驴妈妈旅游网</title>
<meta name="keywords" content="特价游，特色游" />
<meta name="description" content="驴妈妈旅游网推出特色旅游推荐"驴妈妈新概念旅游"这里有2013特价游推荐景点，为您奉上便宜的特价游线路报价，价格，赶快来看一看瞧一瞧吧！" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/zt/hdqq/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
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
    
    <div class="main_left">
    	<div class="nav">
        	<a class="nav_1" target="_self" href="index.html"></a>
        	<a class="nav_2 nav_2_this width_182" target="_self" href="#"></a>
            <a class="nav_3" href="http://www.lvmama.com/zt/lvyou/chujingyou/"></a>
        </div>
        <div class="rxy_box">
        	<h3 class="hd_ing">以下活动火热进行中</h3>
            <a href="http://www.lvmama.com/zt/abroad/"><img class="float_l" src="http://pic.lvmama.com/img/zt/hdqq/xingainian.jpg" width="420" height="352" alt=""></a>
            <a href="http://miyue.lvmama.com/"><img class="float_r" src="http://pic.lvmama.com/img/zt/hdqq/miyue.jpg" width="266" height="423" alt=""></a>
            <div class="hd_xq float_l">
            	<p><img src="http://pic.lvmama.com/img/zt/hdqq/yinhao1.gif" width="24" height="17" alt="">全球各地的热门旅游线路，韩国济州岛缤纷之旅、港澳自由行、阿联酋奢华游、美国东西海岸名城之旅、情迷塞班岛、马尔代夫蜜月之旅、巴厘岛尊爵游、菲律宾长滩岛休闲游，红动全球，轰动全球！<img src="http://pic.lvmama.com/img/zt/hdqq/yinhao2.gif" width="28" height="17" alt=""></p>
                <a class="qidai" target="_self" href="javascript:void(0)"><img src="http://pic.lvmama.com/img/zt/hdqq/fengjing.jpg" width="230" height="400" alt=""></a>
            </div>
            <a href="http://www.lvmama.com/zt/promo/paishe/"><img class="float_r" src="http://pic.lvmama.com/img/zt/hdqq/sheying.jpg" width="266" height="325" alt=""></a>
            
        </div>

    </div>
    
    <div class="main_right">
    	<div class="rxy_right_top">
        	<a href="http://www.lvmama.com/info/lvxingtop/2013-0123-154254.html"><img src="http://pic.lvmama.com/img/zt/hdqq/rxy_r_1.jpg" width="191" height="107" alt=""></a>
            <h3>全球红攻略</h3>
            <p><b>推荐理由：</b>"你就像那冬天里的一把火！"没错，红色绝对是冬天里最受欢迎的颜色！</h1></p>
            <a class="rxy_right_tj" href="http://www.lvmama.com/info/lvxingtop/2013-0123-154254.html">查看详情</a>
        </div>
        <ul class="rxy_right_list">
        	<@s.iterator value="map.get('${station}_tp1')" status="st">
        	<li><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="190" height="210" alt=""></a></li>
           	</@s.iterator>        
        </ul>
    </div>
    

    
    
    
    
    
    
    
    
</div>



<!--热门活动-->
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
		$('.rxy_tab li').click(function(){ 
		var _num = $(this).index();
		$(this).addClass('rxy_tab_li').siblings().removeClass('rxy_tab_li');
		$('.rxy_tab_list').eq(_num).show().siblings('.rxy_tab_list').hide();
		});
		
		$('.cp_name a').hover(function(){
		var _title = $(this).text();
		$(this).attr("title",_title);
		}); 
		
		$('.qidai').click(function(){ 
		alert('敬请期待！')
		});
		
});
</script>

<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
