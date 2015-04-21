<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈浦发银行合作_双人同行立减￥1000元-驴妈妈旅游网</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" href="http://www.lvmama.com/zt/promo/pufabank/css/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery-1.7.js"></script>
<base target="_blank">
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="main_all">
	<div class="banner">
    	<a class="pufa" href="http://www.spdb.com.cn/chpage/c414/"></a>
        <a class="lvmama" href="http://www.lvmama.com/"></a>
    </div>
	<img src="http://www.lvmama.com/zt/promo/pufabank/images/topHuodong.gif" width="1002" height="411" alt="">
    <div class="proboxwrap">
    <@s.iterator value="map.get('${station}_cp')" status="st">
        <div class="probox1">
            <img src="${imgUrl?if_exists}" width="231" height="193" />
            <img src="http://www.lvmama.com/zt/promo/kxlx/images/kxlx_13.gif" height="6" width="231" class="arrow" />
            <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
            <div class="opacity2"></div>
            <p class="opacitytxt2">${bakWord1?if_exists}</p> 
            <div class="jiage">
                <span>500</span><p class="jiageLv">驴妈妈价：<em>&yen;</em><samp>${memberPrice?if_exists?replace(".0","")}</samp>起</p>
            </div>        
          </div> 
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
$(function(){ 
/*智能加title，给a、h、span等标签加title*/
	var hoverA = '..probox1 a' ;       //鼠标指上对应的class，智能给当前元素加title。
////////////////////////////////////////////////////////////
/*智能加title*/
////////////////////////////////////////////////////////////
	$(hoverA).hover(function(){
		var _title = $(this).text();
		$(this).attr("title",_title);
	}); 
});

</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
