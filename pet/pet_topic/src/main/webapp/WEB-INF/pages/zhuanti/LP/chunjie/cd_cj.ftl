<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>成都春节出境游线路_成都出发春节出境游线路价格，哪里好-驴妈妈旅游网</title>
<meta name="keywords" content="出境，旅游,成都" />
<meta name="description" content="驴妈妈推出专题向驴友推出成都春节出境游线
路，有些人会问成都出发春节出境游线路价格?以及成都出境游哪里好?驴妈妈为您收罗
了成都出境游美丽景色，一起来旅游吧！" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/zt/chunjie/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<script src="http://pic.lvmama.com/js/zt/chunjie/chunjie.js"></script>
<base target="_blank">
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="body_bg_t">
<div class="main_all">
	<div class="banner">
    	<h1>春节</h1>
    </div>
	<ul class="nav">
        <li class="nav_li1"><a class="nav_1" href="cd_zb.html" target="_self">周边</a></li>
        <li><a class="nav_2" href="cd_cx.html" target="_self">长线</a></li>
        <li class="nav_li"><a class="nav_3" href="cd_cj.html" target="_self">出境</a></li>
    </ul>
    <div class="yc_box">
    	<h2 class="yc_title">迎春节正在热销•••</h2>
        <ul class="rx_list">
         <@s.iterator value="map.get('${station}_cjcd_tm')" status="st">
            <li>
                <a href="${bakWord3?if_exists}"><img src="${imgUrl?if_exists}" width="276" height="196" alt=""></a>
                <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                <p>★${bakWord1?if_exists} </p>
                <div class="jg_yd">
                    ￥<span>${memberPrice?if_exists?replace(".0","")}</span>起
                    <a href="${url?if_exists}">立即预订</a>
                </div>
            </li>
        </@s.iterator>  
        </ul>
        <@s.iterator value="map.get('${station}_cjcd_tm')" status="st">
		<@s.if test="#st.index == 0"> 
        <p class="gengduo1"><a href="${bakWord2?if_exists}">查看更多&gt;&gt;</a></p>
		</@s.if>
        </@s.iterator> 
    </div>

    <div class="diqu_box">
<!--东南亚模块-->
 	<@s.iterator value="map.get('${station}_cjcd')" status="st">
        <h3 class="maodian"><span>${title?if_exists}</span></h3>
        <ul class="cp_list">
        <@s.iterator value="map.get('${station}_cjcd_${st.index + 1}')" status="sts">
        	<li>
            	<a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="230" height="190" alt=""></a>
                <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                <p class="cp_jg"><span>￥<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>★${bakWord1?if_exists}</p>
            </li>
        </@s.iterator>	
        </ul>
        <p class="gengduo2"><a href="${url?if_exists}">查看更多&gt;&gt;</a></p>
     </@s.iterator>   
    </div>


    
</div>
</div>


<div class="main_b ">
	<div class="hd_title">
    	<p></p>
        <h3>以下活动正在进行</h3>
    </div>
    <ul class="hd_list">
    	<@s.iterator value="map.get('${station}_tp_cd')" status="st">
    	<li><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="224" height="160" alt=""></a></li>
		</@s.iterator>    
    </ul>
    
    <script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
</div>


<div class="cfd_box">
	<dl>
    	<dt>出发地：</dt>
        <dd><a href="index.html" target="_self">上海</a></dd>
        <dd><a href="bj_zb.html" target="_self">北京</a></dd>
        <dd><a href="gz_zb.html" target="_self">广州</a></dd>
        <dd class="cfd_box_dd"><a href="cd_zb.html" target="_self">成都</a></dd>
    </dl>
</div>
<div class="cfd_box cfd_box_bg"></div>



<div class="nav_r">
	<div class="nav_bg_b">
        <dl class="nav_r_list">
            <dt>出境</dt>
            <dd>正在特卖</dd>
<@s.iterator value="map.get('${station}_cjcd')" status="st"> 
            <dd>${title?if_exists}</dd>
</@s.iterator>
        </dl>
        <a href="#" target="_self">返回顶部</a>
    </div>
</div>


<script>
$(function(){ 
	$('.nav_r_list dd:gt(0)').click(function(){
		var _num = $(this).index()-2;
		var _list= $('.diqu_box h3').eq(_num).offset().top;
		$('html,body').animate({'scrollTop':_list},500);
	})
	
	$('.nav_r_list dd:first').click(function(){
		var _list= $('.yc_title').offset().top;
		$('html,body').animate({'scrollTop':_list},500);
	})

});
</script>


<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
