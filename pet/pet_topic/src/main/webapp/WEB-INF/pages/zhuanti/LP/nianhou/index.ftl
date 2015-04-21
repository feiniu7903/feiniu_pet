<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>2013出境游线路推荐_出境游线路报价，价格-驴妈妈旅游网</title>
<meta name="keywords" content="出境游" />
<meta name="description" content="驴妈妈推出红动全球专题为广大网友推荐2013出境游线路推荐,出境游线路报价，价格哪里好并且又便宜， 赶快订购吧，错过就是只能明年了。" />
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
        	<a class="nav_1 nav_1_this" target="_self" href="#"></a>
        	<a class="nav_2 width_182" target="_self" href="tese.html"></a>
            <a class="nav_3" href="http://www.lvmama.com/zt/lvyou/chujingyou/"></a>
        </div>
        <div class="rxy_box">
        	<ul class="rxy_tab">
        	<@s.iterator value="map.get('${station}_rx')" status="st">
            <@s.if test="#st.isFirst()">
            	<li class="rxy_tab_li">${title?if_exists}</li>
            </@s.if>
            <@s.else>
                <li>${title?if_exists}</li>
            </@s.else>
        	</@s.iterator>
            </ul>
 		<@s.iterator value="map.get('${station}_rx')" status="st">
            <@s.if test="#st.isFirst()">
            <div class="rxy_tab_list" style="display:block;">
                <ul class="rxy_cp_list">
               		<@s.iterator value="map.get('${station}_rx_${st.index + 1}')" status="sts">
                    <li>
                        <div class="tm_box tm_box_bg"></div>
                        <dl class="tm_box">
                            <dt>${bakWord1?if_exists}</dt>
                            <dd>${bakWord3?if_exists}</dd>
                        </dl>
                        <a href="${bakWord2?if_exists}"><img src="${imgUrl?if_exists}" width="228" height="206" alt=""></a>
                        <p class="cp_name"><span>${memberPrice?if_exists?replace(".0","")}</span><a href="${url?if_exists}">${title?if_exists}</a></p>
                    </li>
                    </@s.iterator>

                </ul>
                <p class="rxy_gd">
                    <a href="${bakWord1?if_exists}">查看更多>></a>
                </p>
            </div>
            </@s.if>
            <@s.else>
            <div class="rxy_tab_list">
                <ul class="rxy_cp_list">
                    <@s.iterator value="map.get('${station}_rx_${st.index + 1}')" status="sts">
                    <li>
                        <div class="tm_box tm_box_bg"></div>
                        <dl class="tm_box">
                            <dt>${bakWord1?if_exists}</dt>
                            <dd>${bakWord3?if_exists}</dd>
                        </dl>
                        <a href="${bakWord2?if_exists}"><img src="${imgUrl?if_exists}" width="228" height="206" alt=""></a>
                        <p class="cp_name"><span>${memberPrice?if_exists?replace(".0","")}</span><a href="${url?if_exists}">${title?if_exists}</a></p>
                    </li>
                    </@s.iterator>
                </ul>
                <p class="rxy_gd">
                    <a href="${bakWord1?if_exists}">查看更多>></a>
                </p>
            </div>
            </@s.else>
        </@s.iterator>  

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
		
});
</script>

<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
