<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>出国旅游推荐_出国旅游线路价格,报价,多少钱_出国旅游去哪里好-驴妈妈旅游网</title>
<meta name="keywords" content="出国旅游，线路" />
<meta name="description" content="有了驴妈妈, 出国旅游推荐想去哪就去哪儿,这里有出国旅游线路价格,精选出国旅游经典旅游线路,告诉您出国旅游去哪里好,我们是出国旅游的保证。" />
<link rel="stylesheet" href="http://www.lvmama.com/zt/promo/qingshen/css/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery-1.7.js"></script>
<script src="http://www.lvmama.com/zt/promo/qingshen/js/index.js"></script>
<base target="_blank">
<!--[if IE 6]> 
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js"></script> 
<script>DD_belatedPNG.fix('.navA_1,.navA_2,.navA_3');</script> 
<![endif]--> 
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="body_bg">
<div class="main_all">
	<div class="banner">
    	<h1></h1>
    </div>
	<div class="main_box">
    	<div class="mainL">
        
        	<div class="nav_box">
                <ul class="nav">
                    <li><a class="navA_1" href="index.html" target="_self"></a></li>
                    <li class="navIndex"><a class="navA_2" href="ts.html" target="_self"></a></li>
                    <li><a class="navA_3" href="http://www.lvmama.com/zt/lvyou/chujingyou/" ></a></li>
                </ul>
                <span class="run"></span>
            </div>
			<div class="mainBorder">
                <div class="tsListBox">
                    <dl class="tsList">
                     	<@s.iterator value="map.get('${station}_sy')" status="st">
                        <dt><a href="${url?if_exists}"><img src="${imgUrl?if_exists}"></a></dt>
                        </@s.iterator>
                        <@s.iterator value="map.get('${station}_syxl')" status="st">
                        <dd>
                            <span><samp>¥<big>${memberPrice?if_exists?replace(".0","")}</big></samp>起</span>
                            <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                            <p>★ ${bakWord1?if_exists}</p>
                        </dd>
                        </@s.iterator>
                    </dl>
                    
                </div>
                <div class="tsListBox">
                    <dl class="tsList">
                        <@s.iterator value="map.get('${station}_my')" status="st">
                        <dt><a href="${url?if_exists}"><img src="${imgUrl?if_exists}"></a></dt>
                        </@s.iterator>
                        <@s.iterator value="map.get('${station}_myxl')" status="st">
                        <dd>
                            <span><samp>¥<big>${memberPrice?if_exists?replace(".0","")}</big></samp>起</span>
                            <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                            <p>★ ${bakWord1?if_exists}</p>
                        </dd>
                        </@s.iterator>
                    </dl>
                </div>
        	</div>
        </div>
        
        <div class="mainR">
        	<div class="rxy_right_top">
        	<@s.iterator value="map.get('${station}_tp2')" status="st">
        	<a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="191" height="107" alt=""></a>
            <h3>${title?if_exists}</h3>
            <p><b>${bakWord1?if_exists}：</b>${bakWord2?if_exists}</p>
            <a class="rxy_right_tj" href="${url?if_exists}">查看详情</a>
            </@s.iterator>
        </div>
        <ul class="rxy_right_list">
        	<@s.iterator value="map.get('${station}_tp1')" status="st">
        	<li><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="190" height="210" alt=""></a></li>
           	</@s.iterator>
        </ul>
        </div>
    </div>
    
    <@s.iterator value="map.get('${station}_tp3')" status="st">
    <a class="imgBot" href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="980" height="90" alt=""></a>
    </@s.iterator>
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
</div>

<script>

</script>

<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
